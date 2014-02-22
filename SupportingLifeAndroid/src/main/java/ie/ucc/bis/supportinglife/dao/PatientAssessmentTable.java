package ie.ucc.bis.supportinglife.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class: PatientAssessmentTable
 * 
 * This class represents the the patient assessment table in the device
 * database. It provides support for creating and upgrading the table.
 * 
 * @author TOSullivan
 */
public class PatientAssessmentTable {

	// Table
	protected static final String TABLE_PATIENT_ASSESSMENT = "patient_assessment";
	
	// 'PatientAssessment' Table Columns
	protected static final String COLUMN_ID = "_id";
	protected static final String COLUMN_ASSESSMENT_ID = "assessment_id";
	protected static final String COLUMN_NATIONAL_ID = "national_id";
	protected static final String COLUMN_NATIONAL_HEALTH_ID = "national_health_id";
	protected static final String COLUMN_HSA_USER_ID = "hsa_user_id";
	protected static final String COLUMN_CHILD_FIRST_NAME = "child_first_name";
	protected static final String COLUMN_CHILD_SURNAME = "child_surname";
	protected static final String COLUMN_BIRTH_DATE = "birth_date";
	protected static final String COLUMN_GENDER = "gender";
	protected static final String COLUMN_CAREGIVER_NAME = "caregiver";
	protected static final String COLUMN_RELATIONSHIP = "relationship";
	protected static final String COLUMN_PHYSICAL_ADDRESS = "physical_address";
	protected static final String COLUMN_VILLAGE_TA = "village_ta";
	protected static final String COLUMN_VISIT_DATE = "visit_date";
	protected static final String COLUMN_CHEST_INDRAWING = "chest_indrawing";
	protected static final String COLUMN_BREATHS_PER_MINUTE = "breaths_per_minute";
	protected static final String COLUMN_SLEEPY_UNCONSCIOUS = "sleepy_unconscious";
	protected static final String COLUMN_PALMAR_PALLOR = "palmar_pallor";
	protected static final String COLUMN_MUAC_TAPE_COLOUR = "muac_tape_colour";
	protected static final String COLUMN_SWELLING_BOTH_FEET = "swelling_both_feet";
	protected static final String COLUMN_PROBLEM = "problem";
	protected static final String COLUMN_COUGH = "cough";
	protected static final String COLUMN_COUGH_DURATION = "cough_duration";
	protected static final String COLUMN_DIARRHOEA = "diarrhoea";
	protected static final String COLUMN_DIARRHOEA_DURATION = "diarrhoea_duration";
	protected static final String COLUMN_BLOOD_IN_STOOL = "blood_in_stool";
	protected static final String COLUMN_FEVER= "fever";
	protected static final String COLUMN_FEVER_DURATION = "fever_duration";
	protected static final String COLUMN_CONVULSIONS = "convulsions";
	protected static final String COLUMN_DIFFICULTY_DRINKING= "difficulty_drinking";
	protected static final String COLUMN_UNABLE_TO_DRINK = "unable_to_drink";
	protected static final String COLUMN_VOMITING = "vomiting";	
	protected static final String COLUMN_VOMITS_EVERYTHING= "vomits_everything";
	protected static final String COLUMN_RED_EYE = "red_eye";
	protected static final String COLUMN_RED_EYE_DURATION = "red_eye_duration";	
	protected static final String COLUMN_DIFFICULTY_SEEING= "difficulty_seeing";
	protected static final String COLUMN_DIFFICULTY_SEEING_DURATION = "difficulty_seeing_duration";
	protected static final String COLUMN_CANNOT_TREAT = "cannot_treat";		
	protected static final String COLUMN_CANNOT_TREAT_DETAILS = "cannot_treat_details";
	protected static final String COLUMN_SYNCED = "synced";
	
	// Patient Assessment Table creation SQL statement
	private static final String DATABASE_PATIENT_ASSESSMENT_TABLE_CREATE = "CREATE TABLE "
			+ TABLE_PATIENT_ASSESSMENT + "(" + COLUMN_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
									+ COLUMN_ASSESSMENT_ID				+ " TEXT, "
									+ COLUMN_NATIONAL_ID 				+ " TEXT, "
									+ COLUMN_NATIONAL_HEALTH_ID 		+ " TEXT, "
									+ COLUMN_HSA_USER_ID 				+ " TEXT, "
									+ COLUMN_CHILD_FIRST_NAME 			+ " TEXT, "
									+ COLUMN_CHILD_SURNAME 				+ " TEXT, "
									+ COLUMN_BIRTH_DATE 				+ " TEXT, "
									+ COLUMN_GENDER 					+ " TEXT, "
									+ COLUMN_CAREGIVER_NAME 			+ " TEXT, "
									+ COLUMN_RELATIONSHIP 				+ " TEXT, "
									+ COLUMN_PHYSICAL_ADDRESS 			+ " TEXT, "
									+ COLUMN_VILLAGE_TA 				+ " TEXT, "
									+ COLUMN_VISIT_DATE 				+ " TEXT, "
									+ COLUMN_CHEST_INDRAWING 			+ " TEXT, "
									+ COLUMN_BREATHS_PER_MINUTE 		+ " INTEGER, "
									+ COLUMN_SLEEPY_UNCONSCIOUS 		+ " TEXT, "
									+ COLUMN_PALMAR_PALLOR 				+ " TEXT, "
									+ COLUMN_MUAC_TAPE_COLOUR 			+ " TEXT, "
									+ COLUMN_SWELLING_BOTH_FEET 		+ " TEXT, "
									+ COLUMN_PROBLEM 					+ " TEXT, "
									+ COLUMN_COUGH 						+ " TEXT, "
									+ COLUMN_COUGH_DURATION 			+ " INTEGER, "
									+ COLUMN_DIARRHOEA 					+ " TEXT, "
									+ COLUMN_DIARRHOEA_DURATION			+ " INTEGER, "
									+ COLUMN_BLOOD_IN_STOOL				+ " TEXT, "
									+ COLUMN_FEVER						+ " TEXT, "
									+ COLUMN_FEVER_DURATION				+ " INTEGER, "
									+ COLUMN_CONVULSIONS				+ " TEXT, "
									+ COLUMN_DIFFICULTY_DRINKING		+ " TEXT, "
									+ COLUMN_UNABLE_TO_DRINK			+ " TEXT, "
									+ COLUMN_VOMITING					+ " TEXT, "
									+ COLUMN_VOMITS_EVERYTHING			+ " TEXT, "
									+ COLUMN_RED_EYE					+ " TEXT, "
									+ COLUMN_RED_EYE_DURATION			+ " INTEGER, "
									+ COLUMN_DIFFICULTY_SEEING			+ " TEXT, "
									+ COLUMN_DIFFICULTY_SEEING_DURATION	+ " INTEGER, "
									+ COLUMN_CANNOT_TREAT				+ " TEXT, "
									+ COLUMN_CANNOT_TREAT_DETAILS		+ " TEXT, "
									+ COLUMN_SYNCED						+ " TEXT);";
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_PATIENT_ASSESSMENT_TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(DatabaseHandler.class.getName(),
				"Upgrading database classification table from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + DATABASE_PATIENT_ASSESSMENT_TABLE_CREATE);
		onCreate(database);
	}
	
}
