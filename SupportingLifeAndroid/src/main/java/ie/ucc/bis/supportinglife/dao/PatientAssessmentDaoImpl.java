package ie.ucc.bis.supportinglife.dao;

import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * Class: PatientAssessmentDao
 * 
 * This class maintains the database connection and supports 
 * adding new patients and fetching all patients.
 * 
 * @author TOSullivan
 */
public class PatientAssessmentDaoImpl implements PatientAssessmentDao {
	
	private final String LOG_TAG = "ie.ucc.bis.supportinglife.dao.PatientAssessmentDao";

	// Database fields
	private SQLiteDatabase database;
	private DatabaseHandler databaseHandler;
	private String[] allColumns = { DatabaseHandler.TABLE_PATIENT_COLUMN_ID,
									DatabaseHandler.TABLE_PATIENT_COLUMN_ASSESSMENT_ID,
									DatabaseHandler.TABLE_PATIENT_COLUMN_NATIONAL_ID,
									DatabaseHandler.TABLE_PATIENT_COLUMN_NATIONAL_HEALTH_ID,
									DatabaseHandler.TABLE_PATIENT_COLUMN_HSA_USER_ID,
									DatabaseHandler.TABLE_PATIENT_COLUMN_CHILD_FIRST_NAME,
									DatabaseHandler.TABLE_PATIENT_COLUMN_CHILD_SURNAME,
									DatabaseHandler.TABLE_PATIENT_COLUMN_BIRTH_DATE,						
									DatabaseHandler.TABLE_PATIENT_COLUMN_GENDER,
									DatabaseHandler.TABLE_PATIENT_COLUMN_CAREGIVER_NAME,
									DatabaseHandler.TABLE_PATIENT_COLUMN_RELATIONSHIP,
									DatabaseHandler.TABLE_PATIENT_COLUMN_PHYSICAL_ADDRESS,
									DatabaseHandler.TABLE_PATIENT_COLUMN_VILLAGE_TA,
									DatabaseHandler.TABLE_PATIENT_COLUMN_VISIT_DATE,
									DatabaseHandler.TABLE_PATIENT_COLUMN_CHEST_INDRAWING,
									DatabaseHandler.TABLE_PATIENT_COLUMN_BREATHS_PER_MINUTE,
									DatabaseHandler.TABLE_PATIENT_COLUMN_SLEEPY_UNCONSCIOUS,
									DatabaseHandler.TABLE_PATIENT_COLUMN_PALMAR_PALLOR,
									DatabaseHandler.TABLE_PATIENT_COLUMN_MUAC_TAPE_COLOUR,
									DatabaseHandler.TABLE_PATIENT_COLUMN_SWELLING_BOTH_FEET,
									DatabaseHandler.TABLE_PATIENT_COLUMN_PROBLEM,
									DatabaseHandler.TABLE_PATIENT_COLUMN_COUGH,
									DatabaseHandler.TABLE_PATIENT_COLUMN_COUGH_DURATION,
									DatabaseHandler.TABLE_PATIENT_COLUMN_DIARRHOEA,
									DatabaseHandler.TABLE_PATIENT_COLUMN_DIARRHOEA_DURATION,
									DatabaseHandler.TABLE_PATIENT_COLUMN_BLOOD_IN_STOOL,
									DatabaseHandler.TABLE_PATIENT_COLUMN_FEVER,
									DatabaseHandler.TABLE_PATIENT_COLUMN_FEVER_DURATION,
									DatabaseHandler.TABLE_PATIENT_COLUMN_CONVULSIONS,
									DatabaseHandler.TABLE_PATIENT_COLUMN_DIFFICULTY_DRINKING,
									DatabaseHandler.TABLE_PATIENT_COLUMN_UNABLE_TO_DRINK,
									DatabaseHandler.TABLE_PATIENT_COLUMN_VOMITING,
									DatabaseHandler.TABLE_PATIENT_COLUMN_VOMITS_EVERYTHING,
									DatabaseHandler.TABLE_PATIENT_COLUMN_RED_EYE,
									DatabaseHandler.TABLE_PATIENT_COLUMN_RED_EYE_DURATION,
									DatabaseHandler.TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING,
									DatabaseHandler.TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING_DURATION,
									DatabaseHandler.TABLE_PATIENT_COLUMN_CANNOT_TREAT,
									DatabaseHandler.TABLE_PATIENT_COLUMN_CANNOT_TREAT_DETAILS,
									DatabaseHandler.TABLE_PATIENT_COLUMN_SYNCED};

	public PatientAssessmentDaoImpl(Context context) {
		setDatabaseHandler(new DatabaseHandler(context));
	}

	@Override
	public void open() throws SQLException {
		setDatabase(databaseHandler.getWritableDatabase());
	}

	@Override
	public void close() {
		getDatabaseHandler().close();
	}

	/**
	 * Responsible for adding 'PatientAssessment' record to the database on the 
	 * android device
	 * 
	 * @param patientToAdd
	 * @param android_device_id
	 * 
	 * @return
	 */
	@Override
	public PatientAssessment createPatientAssessment(PatientAssessment patientToAdd, String android_device_id) {		

		SQLiteStatement assessmentRowCountQuery = database.compileStatement("select count(*) from " + DatabaseHandler.TABLE_PATIENT);
		long assessmentRowCount = assessmentRowCountQuery.simpleQueryForLong();
		
		LoggerUtils.i(LOG_TAG, "Current Patient Assessment Row Count: " + assessmentRowCount);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_ASSESSMENT_ID, android_device_id + "_" + Long.valueOf(assessmentRowCount).toString());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_NATIONAL_ID, patientToAdd.getNationalId());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_NATIONAL_HEALTH_ID, patientToAdd.getNationalHealthId());	
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_HSA_USER_ID, patientToAdd.getHsaUserId());		
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_CHILD_FIRST_NAME, patientToAdd.getChildFirstName());					
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_CHILD_SURNAME, patientToAdd.getChildSurname());						
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_GENDER, patientToAdd.getGender());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_CAREGIVER_NAME, patientToAdd.getCaregiverName());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_RELATIONSHIP, patientToAdd.getRelationship());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_PHYSICAL_ADDRESS, patientToAdd.getPhysicalAddress());	
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_VILLAGE_TA, patientToAdd.getVillageTa());							
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_CHEST_INDRAWING, String.valueOf(patientToAdd.isChestIndrawing()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_BREATHS_PER_MINUTE, patientToAdd.getBreathsPerMinute());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_SLEEPY_UNCONSCIOUS, String.valueOf(patientToAdd.isSleepyUnconscious()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_PALMAR_PALLOR, String.valueOf(patientToAdd.isPalmarPallor()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_MUAC_TAPE_COLOUR, patientToAdd.getMuacTapeColour());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_SWELLING_BOTH_FEET, String.valueOf(patientToAdd.isSwellingBothFeet()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_PROBLEM, patientToAdd.getProblem());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_COUGH, String.valueOf(patientToAdd.isCough()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_COUGH_DURATION, patientToAdd.getCoughDuration());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_DIARRHOEA, String.valueOf(patientToAdd.isDiarrhoea()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_DIARRHOEA_DURATION, patientToAdd.getDiarrhoeaDuration());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_BLOOD_IN_STOOL, String.valueOf(patientToAdd.isBloodInStool()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_FEVER, String.valueOf(patientToAdd.isFever()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_FEVER_DURATION, patientToAdd.getFeverDuration());
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_CONVULSIONS, String.valueOf(patientToAdd.isConvulsions()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_DIFFICULTY_DRINKING, String.valueOf(patientToAdd.isDifficultyDrinkingOrFeeding()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_UNABLE_TO_DRINK, String.valueOf(patientToAdd.isUnableToDrinkOrFeed()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_VOMITING, String.valueOf(patientToAdd.isVomiting()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_VOMITS_EVERYTHING, String.valueOf(patientToAdd.isVomitsEverything()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_RED_EYE, String.valueOf(patientToAdd.isRedEye()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_RED_EYE_DURATION, patientToAdd.getRedEyeDuration());	
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING, String.valueOf(patientToAdd.isDifficultySeeing()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING_DURATION, patientToAdd.getDifficultySeeingDuration());		
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_CANNOT_TREAT, String.valueOf(patientToAdd.isCannotTreatProblem()));
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_CANNOT_TREAT_DETAILS, patientToAdd.getCannotTreatProblemDetails());	
		values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_SYNCED, Boolean.valueOf(false).toString());	
		
	
		try {
			values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_BIRTH_DATE, getDatabaseHandler().formatDate(patientToAdd.getBirthDate()));	
			values.put(DatabaseHandler.TABLE_PATIENT_COLUMN_VISIT_DATE, getDatabaseHandler().formatDate(patientToAdd.getVisitDate()));
		} catch (ParseException e) {
			LoggerUtils.i(LOG_TAG, "Parse Exception thrown whilst extracting patient assessment dates");
			e.printStackTrace();
		}
		
		
		// add the patient row
		long insertId = database.insert(DatabaseHandler.TABLE_PATIENT, null, values);
		
		Cursor cursor = database.query(DatabaseHandler.TABLE_PATIENT, allColumns, 
										DatabaseHandler.TABLE_PATIENT_COLUMN_ID + " = " + insertId, 
										null, null, null, null);
		cursor.moveToFirst();
		PatientAssessment patientAssessment = cursorToPatientAssessment(cursor);
		cursor.close();
		return patientAssessment;
	}

	@Override
	public void deletePatientAssessment(PatientAssessment patient) {
		long id = patient.getId();
		System.out.println("Patient deleted with id: " + id);
		getDatabase().delete(DatabaseHandler.TABLE_PATIENT, DatabaseHandler.TABLE_PATIENT_COLUMN_ID
				+ " = " + id, null);
	}

	@Override
	public List<PatientAssessment> getAllNonSyncedPatientAssessments() {
		List<PatientAssessment> patients = new ArrayList<PatientAssessment>();

		Cursor cursor = database.query(DatabaseHandler.TABLE_PATIENT,
				allColumns, DatabaseHandler.TABLE_PATIENT_COLUMN_SYNCED + " = '" + Boolean.valueOf(false) + "'", 
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PatientAssessment patient = cursorToPatientAssessment(cursor);
			patients.add(patient);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return patients;
	}
	
	@Override
	public List<PatientAssessment> getAllPatientAssessments() {
		List<PatientAssessment> patients = new ArrayList<PatientAssessment>();

		Cursor cursor = database.query(DatabaseHandler.TABLE_PATIENT,
				allColumns, "", null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PatientAssessment patient = cursorToPatientAssessment(cursor);
			patients.add(patient);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return patients;
	}

	private PatientAssessment cursorToPatientAssessment(Cursor cursor) {

//		Param Line 1: 	Integer id, String deviceGeneratedAssessmentId, String hsaUserId, String nationalId, String nationalHealthId,
//		Param Line 2: 	String childFirstName, String childSurname, String birthDate,
//		Param Line 3: 	String gender, String caregiverName, String relationship, String physicalAddress,
//		Param Line 4: 	String villageTa, String visitDate, String chestIndrawing, Integer breathsPerMinute,
//		Param Line 5: 	String sleepyUnconscious, String palmarPallor, String muacTapeColour, 
//		Param Line 6: 	String swellingBothFeet, String problem, String cough, Integer coughDuration,
//		Param Line 7: 	String diarrhoea, Integer diarrhoeaDuration, String bloodInStool, String fever,
//		Param Line 8: 	Integer feverDuration, String convulsions, String difficultyDrinkingOrFeeding,
//		Param Line 9: 	String unableToDrinkOrFeed, String vomiting, String vomitsEverything,
//		Param Line 10: 	String redEye, Integer redEyeDuration, String difficultySeeing,
//		Param Line 11:	Integer difficultySeeingDuration, String cannotTreatProblem, 
//		Param Line 12: 	String cannotTreatProblemDetails
		
		PatientAssessment patientAssessment = new PatientAssessment(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), 
													cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
													cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),
													cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getInt(15),
													cursor.getString(16), cursor.getString(17), cursor.getString(18),
													cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getInt(22),
													cursor.getString(23), cursor.getInt(24), cursor.getString(25), cursor.getString(26),
													cursor.getInt(27), cursor.getString(28), cursor.getString(29),
													cursor.getString(30), cursor.getString(31), cursor.getString(32),
													cursor.getString(33), cursor.getInt(34), cursor.getString(35),
													cursor.getInt(36), cursor.getString(37),
													cursor.getString(38));
		return patientAssessment;
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
