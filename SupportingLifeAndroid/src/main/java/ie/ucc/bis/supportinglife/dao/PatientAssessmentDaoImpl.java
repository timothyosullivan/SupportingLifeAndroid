package ie.ucc.bis.supportinglife.dao;

import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.service.SupportingLifeService;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

/**
 * Class: PatientAssessmentDao
 * 
 * This class maintains the database connection and supports 
 * adding new patient assessments and fetching all patient
 * assessments.
 * 
 * @author TOSullivan
 */
public class PatientAssessmentDaoImpl implements PatientAssessmentDao {
	
	private final String LOG_TAG = "ie.ucc.bis.supportinglife.dao.PatientAssessmentDaoImpl";

	private String[] allColumns = { PatientAssessmentTable.TABLE_PATIENT_COLUMN_ID,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_ASSESSMENT_ID,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_NATIONAL_ID,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_NATIONAL_HEALTH_ID,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_HSA_USER_ID,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_CHILD_FIRST_NAME,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_CHILD_SURNAME,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_BIRTH_DATE,						
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_GENDER,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_CAREGIVER_NAME,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_RELATIONSHIP,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_PHYSICAL_ADDRESS,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_VILLAGE_TA,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_VISIT_DATE,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_CHEST_INDRAWING,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_BREATHS_PER_MINUTE,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_SLEEPY_UNCONSCIOUS,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_PALMAR_PALLOR,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_MUAC_TAPE_COLOUR,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_SWELLING_BOTH_FEET,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_PROBLEM,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_COUGH,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_COUGH_DURATION,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIARRHOEA,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIARRHOEA_DURATION,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_BLOOD_IN_STOOL,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_FEVER,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_FEVER_DURATION,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_CONVULSIONS,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIFFICULTY_DRINKING,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_UNABLE_TO_DRINK,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_VOMITING,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_VOMITS_EVERYTHING,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_RED_EYE,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_RED_EYE_DURATION,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING_DURATION,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_CANNOT_TREAT,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_CANNOT_TREAT_DETAILS,
									PatientAssessmentTable.TABLE_PATIENT_COLUMN_SYNCED};

	public PatientAssessmentDaoImpl() {
	}

	/**
	 * Responsible for adding 'PatientAssessment' record to the database on the 
	 * android device
	 * 
	 * @param patientToAdd
	 * @param uniquePatientAssessmentIdentifier
	 * 
	 * @return
	 */
	@Override
	public PatientAssessment createPatientAssessment(PatientAssessment patientToAdd, String uniquePatientAssessmentIdentifier, SupportingLifeService service) {		

		// show the number of patient assessments in debug logger
		debugOutputShowPatientAssessmentCount(service);

		ContentValues values = new ContentValues();
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_ASSESSMENT_ID, uniquePatientAssessmentIdentifier);
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_NATIONAL_ID, patientToAdd.getNationalId());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_NATIONAL_HEALTH_ID, patientToAdd.getNationalHealthId());	
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_HSA_USER_ID, patientToAdd.getHsaUserId());		
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_CHILD_FIRST_NAME, patientToAdd.getChildFirstName());					
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_CHILD_SURNAME, patientToAdd.getChildSurname());						
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_GENDER, patientToAdd.getGender());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_CAREGIVER_NAME, patientToAdd.getCaregiverName());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_RELATIONSHIP, patientToAdd.getRelationship());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_PHYSICAL_ADDRESS, patientToAdd.getPhysicalAddress());	
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_VILLAGE_TA, patientToAdd.getVillageTa());							
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_CHEST_INDRAWING, String.valueOf(patientToAdd.isChestIndrawing()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_BREATHS_PER_MINUTE, patientToAdd.getBreathsPerMinute());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_SLEEPY_UNCONSCIOUS, String.valueOf(patientToAdd.isSleepyUnconscious()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_PALMAR_PALLOR, String.valueOf(patientToAdd.isPalmarPallor()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_MUAC_TAPE_COLOUR, patientToAdd.getMuacTapeColour());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_SWELLING_BOTH_FEET, String.valueOf(patientToAdd.isSwellingBothFeet()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_PROBLEM, patientToAdd.getProblem());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_COUGH, String.valueOf(patientToAdd.isCough()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_COUGH_DURATION, patientToAdd.getCoughDuration());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIARRHOEA, String.valueOf(patientToAdd.isDiarrhoea()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIARRHOEA_DURATION, patientToAdd.getDiarrhoeaDuration());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_BLOOD_IN_STOOL, String.valueOf(patientToAdd.isBloodInStool()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_FEVER, String.valueOf(patientToAdd.isFever()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_FEVER_DURATION, patientToAdd.getFeverDuration());
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_CONVULSIONS, String.valueOf(patientToAdd.isConvulsions()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIFFICULTY_DRINKING, String.valueOf(patientToAdd.isDifficultyDrinkingOrFeeding()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_UNABLE_TO_DRINK, String.valueOf(patientToAdd.isUnableToDrinkOrFeed()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_VOMITING, String.valueOf(patientToAdd.isVomiting()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_VOMITS_EVERYTHING, String.valueOf(patientToAdd.isVomitsEverything()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_RED_EYE, String.valueOf(patientToAdd.isRedEye()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_RED_EYE_DURATION, patientToAdd.getRedEyeDuration());	
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING, String.valueOf(patientToAdd.isDifficultySeeing()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING_DURATION, patientToAdd.getDifficultySeeingDuration());		
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_CANNOT_TREAT, String.valueOf(patientToAdd.isCannotTreatProblem()));
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_CANNOT_TREAT_DETAILS, patientToAdd.getCannotTreatProblemDetails());	
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_SYNCED, Boolean.valueOf(false).toString());	
		
	
		try {
			values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_BIRTH_DATE, service.getDatabaseHandler().formatDate(patientToAdd.getBirthDate()));	
			values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_VISIT_DATE, service.getDatabaseHandler().formatDate(patientToAdd.getVisitDate()));
		} catch (ParseException e) {
			LoggerUtils.i(LOG_TAG, "Parse Exception thrown whilst extracting patient assessment dates");
			e.printStackTrace();
		}
		
		
		// add the patient row
		long insertId = service.getDatabase().insert(PatientAssessmentTable.TABLE_PATIENT, null, values);
		
		LoggerUtils.i(LOG_TAG, "Patient Assessment Record Added: " + insertId);
		
		Cursor cursor = service.getDatabase().query(PatientAssessmentTable.TABLE_PATIENT, allColumns, 
										PatientAssessmentTable.TABLE_PATIENT_COLUMN_ID + " = " + insertId, 
										null, null, null, null);
		cursor.moveToFirst();
		PatientAssessment patientAssessment = cursorToPatientAssessment(cursor);
		cursor.close();
		
		// show the number of patient assessments in debug logger
		debugOutputShowPatientAssessmentCount(service);
		
		return patientAssessment;
	}

	/**
	 * Show the number of patient assessments in debug logger
	 * 
	 * @param service
	 */
	private void debugOutputShowPatientAssessmentCount(SupportingLifeService service) {
		SQLiteStatement assessmentRowCountQuery = service.getDatabase().compileStatement("select count(*) from " + PatientAssessmentTable.TABLE_PATIENT);
		long assessmentRowCount = assessmentRowCountQuery.simpleQueryForLong();
		
		LoggerUtils.i(LOG_TAG, "Current Patient Assessment Row Count: " + assessmentRowCount);
	}

	@Override
	public void deletePatientAssessment(PatientAssessment patient, SupportingLifeService service) {
		long id = patient.getId();
		System.out.println("Patient deleted with id: " + id);
		service.getDatabase().delete(PatientAssessmentTable.TABLE_PATIENT, PatientAssessmentTable.TABLE_PATIENT_COLUMN_ID
				+ " = " + id, null);
	}

	@Override
	public List<PatientAssessment> getAllNonSyncedPatientAssessments(SupportingLifeService service) {
		List<PatientAssessment> patients = new ArrayList<PatientAssessment>();

		Cursor cursor = service.getDatabase().query(PatientAssessmentTable.TABLE_PATIENT,
				allColumns, PatientAssessmentTable.TABLE_PATIENT_COLUMN_SYNCED + " = '" + Boolean.valueOf(false) + "'", 
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
	public List<PatientAssessment> getAllPatientAssessments(SupportingLifeService service) {
		List<PatientAssessment> patients = new ArrayList<PatientAssessment>();

		Cursor cursor = service.getDatabase().query(PatientAssessmentTable.TABLE_PATIENT,
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

//		Param Line 1: 	Integer id (0), String deviceGeneratedAssessmentId (1), String nationalId (2), String nationalHealthId (3), String nationalHealthId (4),
//		Param Line 2: 	String childFirstName (5), String childSurname (6), String birthDate (7),
//		Param Line 3: 	String gender (8), String caregiverName (9), String relationship (10), String physicalAddress (11),
//		Param Line 4: 	String villageTa (12), String visitDate (13), String chestIndrawing (14), Integer breathsPerMinute (15),
//		Param Line 5: 	String sleepyUnconscious (16), String palmarPallor (17), String muacTapeColour (18), 
//		Param Line 6: 	String swellingBothFeet (19), String problem (20), String cough (21), Integer coughDuration (22),
//		Param Line 7: 	String diarrhoea (23), Integer diarrhoeaDuration (24), String bloodInStool (25), String fever (26),
//		Param Line 8: 	Integer feverDuration (27), String convulsions (28), String difficultyDrinkingOrFeeding (29),
//		Param Line 9: 	String unableToDrinkOrFeed (30), String vomiting (31), String vomitsEverything (32),
//		Param Line 10: 	String redEye (33), Integer redEyeDuration (34), String difficultySeeing (35),
//		Param Line 11:	Integer difficultySeeingDuration (36), String cannotTreatProblem (37), 
//		Param Line 12: 	String cannotTreatProblemDetails (38)
		
		PatientAssessment patientAssessment = new PatientAssessment(DaoUtilities.readInt(cursor, 0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), 
													cursor.getString(5), cursor.getString(6), cursor.getString(7),
													cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),
													cursor.getString(12), cursor.getString(13), cursor.getString(14), DaoUtilities.readInt(cursor, 15),
													cursor.getString(16), cursor.getString(17), cursor.getString(18),
													cursor.getString(19), cursor.getString(20), cursor.getString(21), DaoUtilities.readInt(cursor, 22),
													cursor.getString(23), DaoUtilities.readInt(cursor, 24), cursor.getString(25), cursor.getString(26),
													DaoUtilities.readInt(cursor, 27), cursor.getString(28), cursor.getString(29),
													cursor.getString(30), cursor.getString(31), cursor.getString(32),
													cursor.getString(33), DaoUtilities.readInt(cursor, 34), cursor.getString(35),
													DaoUtilities.readInt(cursor, 36), cursor.getString(37),
													cursor.getString(38));
		return patientAssessment;
	}

	public int setPatientAssessmentToSynced(String deviceGeneratedAssessmentId, SupportingLifeService service) {
		
		ContentValues values = new ContentValues();
		values.put(PatientAssessmentTable.TABLE_PATIENT_COLUMN_SYNCED, "true");
		
		int rowCount = service.getDatabase().update(PatientAssessmentTable.TABLE_PATIENT, values, 
				PatientAssessmentTable.TABLE_PATIENT_COLUMN_ASSESSMENT_ID + " = '" + deviceGeneratedAssessmentId + "'", 
				null);
		
		return rowCount;
	}
} 
