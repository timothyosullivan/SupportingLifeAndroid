package ie.ucc.bis.supportinglife.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class: ClassificationTable
 * 
 * This class represents the the classification table in the device
 * database. It provides support for creating and upgrading the table.
 * 
 * @author TOSullivan
 */
public class ClassificationTable {

	// Table
	protected static final String TABLE_CLASSIFICATION = "classification";
	
	// 'Classification' Table Columns
	protected static final String TABLE_CLASSIFICATION_COLUMN_ID = "_id";
	protected static final String TABLE_CLASSIFICATION_COLUMN_ASSESSMENT_ID = "assessment_id";
	protected static final String TABLE_CLASSIFICATION_COLUMN_CLASSIFICATION_IDENTIFIER = "identifier";
	protected static final String TABLE_CLASSIFICATION_COLUMN_CLASSIFICATION_NAME = "name";
	
	// Classification Table creation SQL statement
	private static final String DATABASE_CLASSIFICATION_TABLE_CREATE = "CREATE TABLE "
								+ TABLE_CLASSIFICATION + "(" + TABLE_CLASSIFICATION_COLUMN_ID					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
								+ TABLE_CLASSIFICATION_COLUMN_ASSESSMENT_ID										+ " TEXT, "
								+ TABLE_CLASSIFICATION_COLUMN_CLASSIFICATION_IDENTIFIER 						+ " TEXT, "
								+ TABLE_CLASSIFICATION_COLUMN_CLASSIFICATION_NAME 								+ " TEXT);";
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CLASSIFICATION_TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(DatabaseHandler.class.getName(),
				"Upgrading database classification table from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + DATABASE_CLASSIFICATION_TABLE_CREATE);
		onCreate(database);
	}
	
}
