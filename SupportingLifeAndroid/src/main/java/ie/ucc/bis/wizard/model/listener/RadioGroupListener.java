package ie.ucc.bis.wizard.model.listener;

import ie.ucc.bis.wizard.model.AbstractPage;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RadioGroupListener implements OnCheckedChangeListener {
	
	public static final String RADIO_BUTTON_TEXT_DATA_KEY = "RadioButtonText";
	
	private AbstractPage page;
	private String dataKey;
	
	public RadioGroupListener(AbstractPage page, String dataKey) {
		setPage(page);
		setDataKey(dataKey);
	}
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
		
		if (radioButton != null) {
			// firstly add text label of radio button
			// needed for displaying review UI screen
	    	getPage().getPageData().putString(dataKey + RADIO_BUTTON_TEXT_DATA_KEY, radioButton.getText().toString());
	    	
	    	// secondly add the id of the radio button
	    	// needed for onCreateView() in relevant Fragment to re-display
	    	// a view from page data
			getPage().getPageData().putInt(dataKey, checkedId);
		}
		else {
			// indicates that the radio group has been cleared, i.e.
			// no radio button selected, programaticaly. Need to remove
			// key entry from page data
			getPage().getPageData().remove(dataKey + RADIO_BUTTON_TEXT_DATA_KEY);
			getPage().getPageData().remove(dataKey);
		}
    	getPage().notifyDataChanged();
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
