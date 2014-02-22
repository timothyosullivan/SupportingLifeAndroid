package ie.ucc.bis.supportinglife.dao;

import ie.ucc.bis.supportinglife.assessment.model.listener.DateDialogSetListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "supportinglife.db";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		// create patient assessment table
		PatientAssessmentTable.onCreate(database);
		// create classification table
		ClassificationTable.onCreate(database);
		// create treatment table
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// upgrade patient assessment table
		PatientAssessmentTable.onUpgrade(database, oldVersion, newVersion);
		// upgrade classification table
		ClassificationTable.onUpgrade(database, oldVersion, newVersion);
		// create treatment table
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
