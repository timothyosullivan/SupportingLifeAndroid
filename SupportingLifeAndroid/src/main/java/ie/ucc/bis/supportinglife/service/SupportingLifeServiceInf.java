package ie.ucc.bis.supportinglife.service;

import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.dao.DatabaseHandler;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;

import java.util.List;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Service Interface layer for coordinating
 * DAO access
 * 
 * 
 * @author TOSullivan
 *
 */
public interface SupportingLifeServiceInf {

	/*******************************************************************************/
	/*******************************PATIENT ASSESSMENTS*****************************/
	/*******************************************************************************/
	public void createPatientAssessment(PatientAssessment patientToAdd, String android_device_id);
	public void deletePatientAssessment(PatientAssessment patient);
	public List<PatientAssessmentComms> getAllNonSyncedPatientAssessmentComms();
	public List<PatientAssessmentComms> getAllPatientAssessmentComms();
	public int setPatientAssessmentToSynced(String deviceGeneratedAssessmentId);
	
	/*******************************************************************************/
	/***********************GENERAL DATABASE MANAGEMENT*****************************/
	/*******************************************************************************/
	public void open() throws SQLException;
	public void close();
	public SQLiteDatabase getDatabase();
	public void setDatabase(SQLiteDatabase database);
	public DatabaseHandler getDatabaseHandler();
	public void setDatabaseHandler(DatabaseHandler databaseHandler);
}
