package ie.ucc.bis.wizard.model.listener;

import ie.ucc.bis.wizard.model.AbstractPage;
import ie.ucc.bis.wizard.ui.DatePickerDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

/**
 * Listener for DatePicker EditText UI Component. Upon receipt of a
 * click event, the listener will activate a date picker dialog.
 * 
 * @author TOSullivan
 *
 */
public class DatePickerListener implements OnFocusChangeListener {

	private Fragment invokingFragment;
	private AbstractPage page;
	private String dataKey;

	public DatePickerListener(Fragment fragment, AbstractPage page, String dataKey) {
		setInvokingFragment(fragment);
		setPage(page);
		setDataKey(dataKey);
	}

	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			FragmentTransaction fragmentTransaction = getInvokingFragment().getFragmentManager().beginTransaction();
			DialogFragment dialogFragment = DatePickerDialogFragment.create((EditText) v, getPage(), getDataKey());		
			dialogFragment.show(fragmentTransaction, "date_dialog");
		}
	}
	
	/**
	 * Getter Method: getInvokingFragment()
	 */
	public Fragment getInvokingFragment() {
		return invokingFragment;
	}

	/**
	 * Setter Method: setInvokingFragment()
	 */   
	public void setInvokingFragment(Fragment invokingFagment) {
		this.invokingFragment = invokingFagment;
	}
	
	/**
	 * Getter Method: getPage()
	 * 
	 */
	public AbstractPage getPage() {
		return page;
	}

	/**
	 * Setter Method: setPage()
	 * 
	 */
	public void setPage(AbstractPage page) {
		this.page = page;
	}

	/**
	 * Getter Method: getDataKey()
	 * 
	 */
	public String getDataKey() {
		return dataKey;
	}

	/**
	 * Setter Method: setDataKey()
	 * 
	 */
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
}
