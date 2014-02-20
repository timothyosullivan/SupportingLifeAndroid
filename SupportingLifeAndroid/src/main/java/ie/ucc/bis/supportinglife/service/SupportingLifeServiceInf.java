package ie.ucc.bis.supportinglife.service;

import java.util.List;

import ie.ucc.bis.supportinglife.dao.DatabaseHandler;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
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
	public PatientAssessment createPatientAssessment(PatientAssessment patientToAdd, String android_device_id);
	public void deletePatientAssessment(PatientAssessment patient);
	public List<PatientAssessment> getAllNonSyncedPatientAssessments();
	public List<PatientAssessment> getAllPatientAssessments();
	
	
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
