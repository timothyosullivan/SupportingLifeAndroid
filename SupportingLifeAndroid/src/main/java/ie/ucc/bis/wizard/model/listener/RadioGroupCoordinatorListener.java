package ie.ucc.bis.wizard.model.listener;

import ie.ucc.bis.wizard.model.AbstractPage;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RadioGroupCoordinatorListener implements OnCheckedChangeListener {
	
	public static final String RADIO_BUTTON_TEXT_DATA_KEY = "RadioButtonText";
	
	private AbstractPage page;
	private String dataKey;
	private RadioGroup manipulatedRadioGroup;
	
	public RadioGroupCoordinatorListener(AbstractPage page, String dataKey, RadioGroup manipulatedRadioGroup) {
		setPage(page);
		setDataKey(dataKey);
		setManipulatedRadioGroup(manipulatedRadioGroup);
	}
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
		// firstly add text label of radio button
		// needed for displaying review UI screen
    	getPage().getPageData().putString(dataKey + RADIO_BUTTON_TEXT_DATA_KEY, radioButton.getText().toString());
    	
    	// secondly add the id of the radio button
    	// needed for onCreateView() in relevant Fragment to re-display
    	// a view from page data
		getPage().getPageData().putInt(dataKey, checkedId);
		
		if (radioButton.getText().toString().equals("No")) {
			getManipulatedRadioGroup().setVisibility(View.GONE);
			getManipulatedRadioGroup().invalidate();
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

	public RadioGroup getManipulatedRadioGroup() {
		return manipulatedRadioGroup;
	}

	public void setManipulatedRadioGroup(RadioGroup manipulatedRadioGroup) {
		this.manipulatedRadioGroup = manipulatedRadioGroup;
	}

	
}
