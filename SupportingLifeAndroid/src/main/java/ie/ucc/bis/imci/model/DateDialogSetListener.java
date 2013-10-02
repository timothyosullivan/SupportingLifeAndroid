package ie.ucc.bis.imci.model;

import ie.ucc.bis.imci.ui.DatePickerDialogFragment;

import java.util.Calendar;

import android.app.DatePickerDialog.OnDateSetListener;
import java.text.DateFormat;
import android.widget.DatePicker;

public class DateDialogSetListener implements OnDateSetListener {

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
		
		DateFormat dateFormt = android.text.format.DateFormat.getLongDateFormat(getDatePickerDialogFragment().getActivity().getApplicationContext());
		
		String dateString = dateFormt.format(calendar.getTime()).toString();
		
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
