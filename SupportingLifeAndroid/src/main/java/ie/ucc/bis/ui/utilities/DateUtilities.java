package ie.ucc.bis.ui.utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtilities {
	
	private static final int NUMBER_OF_MONTHS_IN_YEAR = 12;

	/**
	 * Utility method to retrieve the number of months difference between
	 * two dates
	 * 
	 * @param earliestDate
	 * @param latestDate
	 * @return
	 */
	public static int getDiffMonths(Date earliestDate, Date latestDate) {
	    Calendar earliestCalendar = getCalendar(earliestDate);
	    Calendar latestCalendar = getCalendar(latestDate);
	    
	    int monthsDifference = (latestCalendar.get(Calendar.YEAR) - earliestCalendar.get(Calendar.YEAR)) * NUMBER_OF_MONTHS_IN_YEAR;
	    
	    if (latestCalendar.get(Calendar.MONTH) > earliestCalendar.get(Calendar.MONTH)) {
	    	monthsDifference += latestCalendar.get(Calendar.MONTH) - earliestCalendar.get(Calendar.MONTH);
	    }
	    else if (earliestCalendar.get(Calendar.MONTH) > latestCalendar.get(Calendar.MONTH)) {
	    	monthsDifference -= earliestCalendar.get(Calendar.MONTH) - latestCalendar.get(Calendar.MONTH);
	    }
	    return monthsDifference;
	}

	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance(Locale.UK);
	    cal.setTime(date);
	    return cal;
	}
	
}
