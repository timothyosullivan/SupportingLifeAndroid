package ie.ucc.bis.wizard.model;

import ie.ucc.bis.wizard.ui.DatePickerDialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
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
		// update data record associated with this page
		getDatePickerDialogFragment().getPage().getPageData().putString(getDatePickerDialogFragment().getDataKey(),
                (Integer.toString(dayOfMonth) != null) ? Integer.toString(dayOfMonth) : null);
		getDatePickerDialogFragment().getPage().notifyDataChanged();
		
		// update view
		getDatePickerDialogFragment().getDateEditText().setText(Integer.toString(dayOfMonth) + " " + Integer.toString(monthOfYear));
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
