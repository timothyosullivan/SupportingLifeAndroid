package ie.ucc.bis.supportinglife.service;

import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.dao.ClassificationDao;
import ie.ucc.bis.supportinglife.dao.ClassificationDaoImpl;
import ie.ucc.bis.supportinglife.dao.DatabaseHandler;
import ie.ucc.bis.supportinglife.dao.PatientAssessmentDao;
import ie.ucc.bis.supportinglife.dao.PatientAssessmentDaoImpl;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Service Interface layer for coordinating
 * DAO access
 * 
 * @author TOSullivan
 *
 */
public class SupportingLifeService implements SupportingLifeServiceInf {

	// DAOs
	private PatientAssessmentDao patientAssessmentDao;
	private ClassificationDao classificationDao;
	
	// Database fields
	private SQLiteDatabase database;
	private DatabaseHandler databaseHandler;
	
	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public SupportingLifeService(Context context) {
		setDatabaseHandler(new DatabaseHandler(context));
		setPatientAssessmentDao(new PatientAssessmentDaoImpl());
		setClassificationDao(new ClassificationDaoImpl());
	}
	
	/*******************************************************************************/
	/*******************************PATIENT ASSESSMENTS*****************************/
	/*******************************************************************************/
	@Override
	public void createPatientAssessment(PatientAssessment patientToAdd, String android_device_id) {
		
		// the combination of the the 'unique android id'  and the current time in ms will
		// allow the device to create a unique 'patient assessment identifier'
		long timestamp = System.currentTimeMillis();
		String uniquePatientAssessmentIdentifier = android_device_id + "_" + timestamp;
		
		// add the patient assessment
		getPatientAssessmentDao().createPatientAssessmentComms(patientToAdd, uniquePatientAssessmentIdentifier, this);
		
		// now add the associated 'patient assessment' classifications
		getClassificationDao().createPatientClassifications(patientToAdd, uniquePatientAssessmentIdentifier, this);
		
		// now add the associated 'patient assessment' treatments
		
	}

	@Override
	public void deletePatientAssessment(PatientAssessment patient) {
		getPatientAssessmentDao().deletePatientAssessment(patient, this);
	}

	@Override
	public List<PatientAssessmentComms> getAllNonSyncedPatientAssessmentComms() {
		List<PatientAssessmentComms> patientAssessmentComms = new ArrayList<PatientAssessmentComms>();
		
		// 1. pull back all non-synced records from the 'patient assessment' table
		patientAssessmentComms = getPatientAssessmentDao().getAllNonSyncedPatientAssessmentComms(this);
		
		// 2. For the patient assessments pulled back, pull back the associated classifications
		getClassificationDao().populatePatientClassifications(patientAssessmentComms, this);
		
		return patientAssessmentComms;
	}

	@Override
	public List<PatientAssessmentComms> getAllPatientAssessmentComms() {
		return getPatientAssessmentDao().getAllPatientAssessments(this);
	}

	public int setPatientAssessmentToSynced(String deviceGeneratedAssessmentId) {
		return getPatientAssessmentDao().setPatientAssessmentToSynced(deviceGeneratedAssessmentId, this);	
	}
	
	/*******************************************************************************/
	/***********************GENERAL DATABASE MANAGEMENT*****************************/
	/*******************************************************************************/
	@Override
	public void open() throws SQLException {
		setDatabase(databaseHandler.getWritableDatabase());
	}

	@Override
	public void close() {
		getDatabaseHandler().close();
	}
	
	public PatientAssessmentDao getPatientAssessmentDao() {
		return patientAssessmentDao;
	}

	public void setPatientAssessmentDao(PatientAssessmentDao patientAssessmentDao) {
		this.patientAssessmentDao = patientAssessmentDao;
	}

	public ClassificationDao getClassificationDao() {
		return classificationDao;
	}

	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao = classificationDao;
	}

	@Override
	public SQLiteDatabase getDatabase() {
		return database;
	}

	@Override
	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	@Override
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
}
