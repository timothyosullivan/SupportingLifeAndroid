package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.wizard.model.AbstractPage;
import ie.ucc.bis.wizard.model.DateDialogSetListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class DatePickerDialogFragment extends DialogFragment {

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
				Date previousDate = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).parse(datePreviouslyChosen);
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
		datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
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