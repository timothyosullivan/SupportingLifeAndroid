package ie.ucc.bis.supportinglife.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class: TreatmentTable
 * 
 * This class represents the the treatment table in the device
 * database. It provides support for creating and upgrading the table.
 * 
 * @author TOSullivan
 */
public class TreatmentTable {

	// Table
	protected static final String TABLE_TREATMENT = "treatment";
	
	// 'Treatment' Table Columns
	protected static final String COLUMN_ID = "_id";
	protected static final String COLUMN_ASSESSMENT_ID = "assessment_id";
	protected static final String COLUMN_TREATMENT_IDENTIFIER = "identifier";
	protected static final String COLUMN_TREATMENT_DESCRIPTION = "description";
	
	// Treatment Table creation SQL statement
	private static final String DATABASE_TREATMENT_TABLE_CREATE = "CREATE TABLE "
								+ TABLE_TREATMENT + "(" +COLUMN_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
								+ COLUMN_ASSESSMENT_ID							+ " TEXT, "
								+ COLUMN_TREATMENT_IDENTIFIER 					+ " TEXT, "
								+ COLUMN_TREATMENT_DESCRIPTION 					+ " TEXT);";
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_TREATMENT_TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(DatabaseHandler.class.getName(),
				"Upgrading database treatment table from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TREATMENT_TABLE_CREATE);
		onCreate(database);
	}
	
}
