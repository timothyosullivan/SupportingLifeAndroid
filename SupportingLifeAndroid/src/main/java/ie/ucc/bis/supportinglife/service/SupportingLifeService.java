package ie.ucc.bis.supportinglife.service;

import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.dao.ClassificationDao;
import ie.ucc.bis.supportinglife.dao.ClassificationDaoImpl;
import ie.ucc.bis.supportinglife.dao.DatabaseHandler;
import ie.ucc.bis.supportinglife.dao.PatientAssessmentDao;
import ie.ucc.bis.supportinglife.dao.PatientAssessmentDaoImpl;
import ie.ucc.bis.supportinglife.dao.TreatmentDao;
import ie.ucc.bis.supportinglife.dao.TreatmentDaoImpl;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

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
	
	private final String LOG_TAG = "ie.ucc.bis.supportinglife.service.SupportingLifeService";

	// DAOs
	private PatientAssessmentDao patientAssessmentDao;
	private ClassificationDao classificationDao;
	private TreatmentDao treatmentDao;
	
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
		setTreatmentDao(new TreatmentDaoImpl());
	}
	
	/*******************************************************************************/
	/*******************************PATIENT ASSESSMENTS*****************************/
	/*******************************************************************************/
	@Override
	public void createPatientAssessment(PatientAssessment patientToAdd, String android_device_id) {
		
		// updating multiple tables so wrap in a transaction
		getDatabase().beginTransaction();
		
		try {
			// the combination of the the 'unique android id'  and the current time in ms will
			// allow the device to create a unique 'patient assessment identifier'
			long timestamp = System.currentTimeMillis();
			String uniquePatientAssessmentIdentifier = android_device_id + "_" + timestamp;
			
			// add the patient assessment
			getPatientAssessmentDao().createPatientAssessmentComms(patientToAdd, uniquePatientAssessmentIdentifier, this);
			
			// now add the associated 'patient assessment' classifications
			getClassificationDao().createPatientClassifications(patientToAdd, uniquePatientAssessmentIdentifier, this);
			
			// now add the associated 'patient assessment' treatments
			getTreatmentDao().createPatientTreatments(patientToAdd, uniquePatientAssessmentIdentifier, this);
			
			// commit the transaction
			getDatabase().setTransactionSuccessful();
		} catch (Exception ex) {
			LoggerUtils.i(LOG_TAG, "SupportingLifeService: createPatientAssessment -- Exception");
			LoggerUtils.i(LOG_TAG, "SupportingLifeService: createPatientAssessment -- " + ex.getMessage());
		}
		finally {
			// end the database transaction
			getDatabase().endTransaction();
		}		
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
		
		// only need to pull back classifications and treatments if there are 
		// patients assessments requiring syncing
		if (patientAssessmentComms.size() > 0) {		
			// 2. For the patient assessments pulled back, pull back the associated classifications
			getClassificationDao().populatePatientClassifications(patientAssessmentComms, this);
			
			// 3. For the patient assessments pulled back, pull back the associated classifications
			getTreatmentDao().populatePatientTreatments(patientAssessmentComms, this);
		}
		
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

	public TreatmentDao getTreatmentDao() {
		return treatmentDao;
	}

	public void setTreatmentDao(TreatmentDao treatmentDao) {
		this.treatmentDao = treatmentDao;
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
