package ie.ucc.bis.ui.utilities;

import ie.ucc.bis.assessment.model.listener.DateDialogSetListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * Class providing Date/Time utility methods 
 * 
 * @author timothyosullivan
 */
public class DateUtilities {
	
	private static final int NUMBER_OF_MONTHS_IN_YEAR = 12;
	private static final int MILLISECONDS_IN_A_SECOND = 1000;
	private static final int SECONDS_IN_A_MINUTE = 60;
	private static final int MINUTES_IN_A_HOUR = 60;
	private static final int HOURS_IN_A_DAY = 24;

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
	
	/**
	 * Utility method to retrieve the number of days difference between
	 * two dates
	 * 
	 * @param earliestDate
	 * @param latestDate
	 * @return
	 */
	public static int getDiffDays(Date earliestDate, Date latestDate) {
		return Long.valueOf((latestDate.getTime() - earliestDate.getTime())
				/ (MILLISECONDS_IN_A_SECOND * SECONDS_IN_A_MINUTE * MINUTES_IN_A_HOUR * HOURS_IN_A_DAY)).intValue();
	}	
	
	/**
	 * Utility method to obtain a TimeStamp of a number of years ago from today
	 * 
	 * @param years
	 * @return long - TimeStamp in milliseconds
	 */
	public static long retrieveTimeStampWithYearsSubtracted(int years) {
	    Calendar cal = Calendar.getInstance(Locale.UK);
	    // convert years to negative value
	    cal.add(Calendar.YEAR, (years * -1));
	    return cal.getTimeInMillis();
	}
	
	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance(Locale.UK);
	    cal.setTime(date);
	    return cal;
	}
	
	/**
	 * Utility method to return today's date
	 * 
	 * @return String - TimeStamp in milliseconds
	 */
	public static String getTodaysDate() {
		Calendar cal = Calendar.getInstance(Locale.UK);
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateDialogSetListener.DATE_TIME_CUSTOM_FORMAT, DateDialogSetListener.LOCALE);
				
		return dateFormat.format(cal.getTime());
	}
	
}
