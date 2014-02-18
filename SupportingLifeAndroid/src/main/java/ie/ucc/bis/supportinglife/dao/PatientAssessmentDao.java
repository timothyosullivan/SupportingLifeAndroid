package ie.ucc.bis.supportinglife.dao;

import java.util.List;

import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public interface PatientAssessmentDao {
	
	public PatientAssessment createPatientAssessment(PatientAssessment patientToAdd, String android_device_id);
	public void deletePatientAssessment(PatientAssessment patient);
	public List<PatientAssessment> getAllNonSyncedPatientAssessments();
	public List<PatientAssessment> getAllPatientAssessments();
	public void open() throws SQLException;
	public void close();
	
	/* Getters & Setters */
	public SQLiteDatabase getDatabase();
	public void setDatabase(SQLiteDatabase database);
	public DatabaseHandler getDatabaseHandler();
	public void setDatabaseHandler(DatabaseHandler databaseHandler);
}
