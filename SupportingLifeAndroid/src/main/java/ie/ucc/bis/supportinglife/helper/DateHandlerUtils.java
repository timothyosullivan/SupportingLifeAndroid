package ie.ucc.bis.supportinglife.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHandlerUtils {

	public static final String DATE_TIME_CUSTOM_FORMAT = "dd MMMM yyyy";
	public static final Locale LOCALE = Locale.UK;
	
	/**
	 * Parse and format string value representing date
	 * 
	 * @param string
	 * 
	 * @return Date
	 * @throws ParseException 
	 */
	public static Date parseDate(String dateValue) throws ParseException {
		if (dateValue != null) {
			Date dateInstance = new SimpleDateFormat(DATE_TIME_CUSTOM_FORMAT, LOCALE)
										.parse(dateValue);
			return dateInstance;
		}
		else {
			return null;
		}
	}
	
}
