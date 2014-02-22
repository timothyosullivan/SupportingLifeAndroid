package ie.ucc.bis.supportinglife.service;

import ie.ucc.bis.supportinglife.dao.DatabaseHandler;
import ie.ucc.bis.supportinglife.dao.PatientAssessmentDaoImpl;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;

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
	private PatientAssessmentDaoImpl patientAssessmentDaoImpl;
	
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
		setPatientAssessmentDaoImpl(new PatientAssessmentDaoImpl());
	}
	
	/*******************************************************************************/
	/*******************************PATIENT ASSESSMENTS*****************************/
	/*******************************************************************************/
	@Override
	public PatientAssessment createPatientAssessment(PatientAssessment patientToAdd, String android_device_id) {
		
		// the combination of the the 'unique android id'  and the current time in ms will
		// allow the device to create a unique 'patient assessment identifier'
		long timestamp = System.currentTimeMillis();
		String uniquePatientAssessmentIdentifier = android_device_id + "_" + timestamp;
		
		return getPatientAssessmentDaoImpl().createPatientAssessment(patientToAdd, uniquePatientAssessmentIdentifier, this);
	}

	@Override
	public void deletePatientAssessment(PatientAssessment patient) {
		getPatientAssessmentDaoImpl().deletePatientAssessment(patient, this);
	}

	@Override
	public List<PatientAssessment> getAllNonSyncedPatientAssessments() {
		return getPatientAssessmentDaoImpl().getAllNonSyncedPatientAssessments(this);
	}

	@Override
	public List<PatientAssessment> getAllPatientAssessments() {
		return getPatientAssessmentDaoImpl().getAllPatientAssessments(this);
	}

	public int setPatientAssessmentToSynced(String deviceGeneratedAssessmentId) {
		return getPatientAssessmentDaoImpl().setPatientAssessmentToSynced(deviceGeneratedAssessmentId, this);	
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
	
	public PatientAssessmentDaoImpl getPatientAssessmentDaoImpl() {
		return patientAssessmentDaoImpl;
	}

	public void setPatientAssessmentDaoImpl(PatientAssessmentDaoImpl patientAssessmentDaoImpl) {
		this.patientAssessmentDaoImpl = patientAssessmentDaoImpl;
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
