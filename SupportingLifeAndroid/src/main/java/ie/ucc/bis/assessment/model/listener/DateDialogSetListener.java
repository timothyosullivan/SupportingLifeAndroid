package ie.ucc.bis.assessment.model.listener;

import ie.ucc.bis.assessment.imci.ui.DatePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;

/**
 * 
 * @author timothyosullivan
 */

public class DateDialogSetListener implements OnDateSetListener {

	public static final String DATE_TIME_CUSTOM_FORMAT = "dd MMMM yyyy";
	public static final Locale LOCALE = Locale.UK;
	
	private DatePickerDialogFragment datePickerDialogFragment;
	
	/**
	 * Constructor
	 * 
	 * @param dateEditText
	 */
	public DateDialogSetListener(DatePickerDialogFragment datePickerDialogFragment) {
		setDatePickerDialogFragment(datePickerDialogFragment);
	}


	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, monthOfYear, dayOfMonth);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_CUSTOM_FORMAT, LOCALE);
				
		String dateString = simpleDateFormat.format(calendar.getTime());
		
		// update data record associated with this page
		getDatePickerDialogFragment().getPage().getPageData().putString(getDatePickerDialogFragment().getDataKey(),
                (dateString != null) ? dateString : null);
		getDatePickerDialogFragment().getPage().notifyDataChanged();
		
		// update view with date selected and toggle focus off
		getDatePickerDialogFragment().getDateEditText().setText(dateString);
		getDatePickerDialogFragment().getDateEditText().clearFocus();
	}

	/**
	 * Getter Method: getDatePickerDialogFragment()
	 */
	public DatePickerDialogFragment getDatePickerDialogFragment() {
		return datePickerDialogFragment;
	}

	/**
	 * Setter Method: setDatePickerDialogFragment()
	 */
	public void setDatePickerDialogFragment(DatePickerDialogFragment datePickerDialogFragment) {
		this.datePickerDialogFragment = datePickerDialogFragment;
	}

}
