package ie.ucc.bis.supportinglife.dao;

import ie.ucc.bis.supportinglife.assessment.model.listener.DateDialogSetListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "supportinglife.db";

	// Tables
	protected static final String TABLE_PATIENT = "patient";
	
	// 'Patient' Table Columns
	protected static final String TABLE_PATIENT_COLUMN_ID = "_id";
	protected static final String TABLE_PATIENT_COLUMN_ASSESSMENT_ID = "assessment_id";
	protected static final String TABLE_PATIENT_COLUMN_NATIONAL_ID = "national_id";
	protected static final String TABLE_PATIENT_COLUMN_NATIONAL_HEALTH_ID = "national_health_id";
	protected static final String TABLE_PATIENT_COLUMN_HSA_USER_ID = "hsa_user_id";
	protected static final String TABLE_PATIENT_COLUMN_CHILD_FIRST_NAME = "child_first_name";
	protected static final String TABLE_PATIENT_COLUMN_CHILD_SURNAME = "child_surname";
	protected static final String TABLE_PATIENT_COLUMN_BIRTH_DATE = "birth_date";
	protected static final String TABLE_PATIENT_COLUMN_GENDER = "gender";
	protected static final String TABLE_PATIENT_COLUMN_CAREGIVER_NAME = "caregiver";
	protected static final String TABLE_PATIENT_COLUMN_RELATIONSHIP = "relationship";
	protected static final String TABLE_PATIENT_COLUMN_PHYSICAL_ADDRESS = "physical_address";
	protected static final String TABLE_PATIENT_COLUMN_VILLAGE_TA = "village_ta";
	protected static final String TABLE_PATIENT_COLUMN_VISIT_DATE = "visit_date";
	protected static final String TABLE_PATIENT_COLUMN_CHEST_INDRAWING = "chest_indrawing";
	protected static final String TABLE_PATIENT_COLUMN_BREATHS_PER_MINUTE = "breaths_per_minute";
	protected static final String TABLE_PATIENT_COLUMN_SLEEPY_UNCONSCIOUS = "sleepy_unconscious";
	protected static final String TABLE_PATIENT_COLUMN_PALMAR_PALLOR = "palmar_pallor";
	protected static final String TABLE_PATIENT_COLUMN_MUAC_TAPE_COLOUR = "muac_tape_colour";
	protected static final String TABLE_PATIENT_COLUMN_SWELLING_BOTH_FEET = "swelling_both_feet";
	protected static final String TABLE_PATIENT_COLUMN_PROBLEM = "problem";
	protected static final String TABLE_PATIENT_COLUMN_COUGH = "cough";
	protected static final String TABLE_PATIENT_COLUMN_COUGH_DURATION = "cough_duration";
	protected static final String TABLE_PATIENT_COLUMN_DIARRHOEA = "diarrhoea";
	protected static final String TABLE_PATIENT_COLUMN_DIARRHOEA_DURATION = "diarrhoea_duration";
	protected static final String TABLE_PATIENT_COLUMN_BLOOD_IN_STOOL = "blood_in_stool";
	protected static final String TABLE_PATIENT_COLUMN_FEVER= "fever";
	protected static final String TABLE_PATIENT_COLUMN_FEVER_DURATION = "fever_duration";
	protected static final String TABLE_PATIENT_COLUMN_CONVULSIONS = "convulsions";
	protected static final String TABLE_PATIENT_COLUMN_DIFFICULTY_DRINKING= "difficulty_drinking";
	protected static final String TABLE_PATIENT_COLUMN_UNABLE_TO_DRINK = "unable_to_drink";
	protected static final String TABLE_PATIENT_COLUMN_VOMITING = "vomiting";	
	protected static final String TABLE_PATIENT_COLUMN_VOMITS_EVERYTHING= "vomits_everything";
	protected static final String TABLE_PATIENT_COLUMN_RED_EYE = "red_eye";
	protected static final String TABLE_PATIENT_COLUMN_RED_EYE_DURATION = "red_eye_duration";	
	protected static final String TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING= "difficulty_seeing";
	protected static final String TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING_DURATION = "difficulty_seeing_duration";
	protected static final String TABLE_PATIENT_COLUMN_CANNOT_TREAT = "cannot_treat";		
	protected static final String TABLE_PATIENT_COLUMN_CANNOT_TREAT_DETAILS = "cannot_treat_details";
	protected static final String TABLE_PATIENT_COLUMN_SYNCED = "synced";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ TABLE_PATIENT + "(" 	+ TABLE_PATIENT_COLUMN_ID							+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
									+ TABLE_PATIENT_COLUMN_ASSESSMENT_ID				+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_NATIONAL_ID 					+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_NATIONAL_HEALTH_ID 			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_HSA_USER_ID 					+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_CHILD_FIRST_NAME 			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_CHILD_SURNAME 				+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_BIRTH_DATE 					+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_GENDER 						+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_CAREGIVER_NAME 				+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_RELATIONSHIP 				+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_PHYSICAL_ADDRESS 			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_VILLAGE_TA 					+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_VISIT_DATE 					+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_CHEST_INDRAWING 				+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_BREATHS_PER_MINUTE 			+ " INTEGER, "
									+ TABLE_PATIENT_COLUMN_SLEEPY_UNCONSCIOUS 			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_PALMAR_PALLOR 				+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_MUAC_TAPE_COLOUR 			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_SWELLING_BOTH_FEET 			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_PROBLEM 						+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_COUGH 						+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_COUGH_DURATION 				+ " INTEGER, "
									+ TABLE_PATIENT_COLUMN_DIARRHOEA 					+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_DIARRHOEA_DURATION			+ " INTEGER, "
									+ TABLE_PATIENT_COLUMN_BLOOD_IN_STOOL				+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_FEVER						+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_FEVER_DURATION				+ " INTEGER, "
									+ TABLE_PATIENT_COLUMN_CONVULSIONS					+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_DIFFICULTY_DRINKING			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_UNABLE_TO_DRINK				+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_VOMITING						+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_VOMITS_EVERYTHING			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_RED_EYE						+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_RED_EYE_DURATION				+ " INTEGER, "
									+ TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_DIFFICULTY_SEEING_DURATION	+ " INTEGER, "
									+ TABLE_PATIENT_COLUMN_CANNOT_TREAT					+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_CANNOT_TREAT_DETAILS			+ " TEXT, "
									+ TABLE_PATIENT_COLUMN_SYNCED						+ " TEXT);";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(DatabaseHandler.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
		onCreate(database);
	}
	
	/**
	 * Convert a Date instance value to it's  
	 * string value equivalent
	 * 
	 * @param Date
	 * 
	 * @return Date
	 * @throws ParseException 
	 */
	public String formatDate(Date date) throws ParseException {
		if (date != null) {
			String dateString = new SimpleDateFormat(DateDialogSetListener.DATE_TIME_CUSTOM_FORMAT, DateDialogSetListener.LOCALE)
										.format(date);
			return dateString;
		}
		else {
			return null;
		}
	}

}
