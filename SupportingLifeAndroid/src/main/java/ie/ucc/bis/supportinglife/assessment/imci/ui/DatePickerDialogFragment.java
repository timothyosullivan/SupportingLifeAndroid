package ie.ucc.bis.supportinglife.assessment.imci.ui;

import ie.ucc.bis.supportinglife.assessment.model.AbstractPage;
import ie.ucc.bis.supportinglife.assessment.model.listener.DateDialogSetListener;
import ie.ucc.bis.supportinglife.ui.utilities.DateUtilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

/**
 * 
 * @author timothyosullivan
 */

public class DatePickerDialogFragment extends DialogFragment {
	
	private static final int MAX_AGE_OF_PATIENT = 5;

	private AbstractPage page;
	private String dataKey;
	private EditText dateEditText;
	
	/**
	 * Constructor
	 *
	 */        
    public DatePickerDialogFragment() {}

    public static DatePickerDialogFragment create(EditText dateEditText, AbstractPage abstractPage, String dataKey) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        
        fragment.setDateEditText(dateEditText);
        fragment.setPage(abstractPage);
        fragment.setDataKey(dataKey);

        return fragment;
    }    
    
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int year, month, day;
 
		// Use the current date as the default date if a date has not been 
        // previously configured by the picker
		final Calendar cal = Calendar.getInstance();
		String datePreviouslyChosen = getPage().getPageData().getString(getDataKey());
		if (datePreviouslyChosen != null) {
			try {
				Date previousDate = new SimpleDateFormat(DateDialogSetListener.DATE_TIME_CUSTOM_FORMAT, DateDialogSetListener.LOCALE).parse(datePreviouslyChosen);
				cal.setTime(previousDate);	
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DateDialogSetListener(this), year, month, day);
		// add constraint to date picker such that the user cannot pick a date further ahead in time than today
		datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
		// add constraint to date picker such that the user cannot pick a date more than 5 years ago
		datePickerDialog.getDatePicker().setMinDate(DateUtilities.retrieveTimeStampWithYearsSubtracted(MAX_AGE_OF_PATIENT));
		
		return datePickerDialog;
	}

	/**
	 * Getter Method: getDateEditText()
	 */
	public EditText getDateEditText() {
		return dateEditText;
	}

	/**
	 * Setter Method: setDateEditText()
	 */ 
	public void setDateEditText(EditText dateEditText) {
		this.dateEditText = dateEditText;
	}

	/**
	 * Getter Method: getPage()
	 */
	public AbstractPage getPage() {
		return page;
	}

	/**
	 * Setter Method: setPage()
	 */
	public void setPage(AbstractPage page) {
		this.page = page;
	}

	/**
	 * Getter Method: getDataKey()
	 */
	public String getDataKey() {
		return dataKey;
	}

	/**
	 * Setter Method: setDataKey()
	 */
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
}