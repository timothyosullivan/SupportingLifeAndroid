package ie.ucc.bis.supportinglife.dao;

import android.database.Cursor;

/**
 * Class: DaoUtilities
 * 
 * Provide utility methods for all DAOs
 * 
 * @author TOSullivan
 */
public class DaoUtilities {
	
	/**
	 * Utility method to check whether a cursor index containing
	 * an int is null and if so, then just return null.
	 * 
	 * If this check was not performed, the call 'cursor.getInt(index)'
	 * would return 0 instead of null, in the cases where the cursor 
	 * index containing an int is actually null - i.e. we need it to 
	 * return null in these cases as otherwise we will have inaccurate
	 * data
	 * 
	 * @param cursor
	 * @param index
	 * @return
	 */
	public static Integer readInt(Cursor cursor, int index) { 
		Integer result  = null; 
		if (!cursor.isNull(index)) { 
			result = cursor.getInt(index); 
		} 
		return result; 
	} 
	
}
