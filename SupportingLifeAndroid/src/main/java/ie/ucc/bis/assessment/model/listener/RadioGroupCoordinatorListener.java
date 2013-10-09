package ie.ucc.bis.assessment.model.listener;

import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.imci.model.DynamicView;

import java.util.Arrays;
import java.util.List;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * This listener class is responsible for listening to a particular 
 * radio group, taking record of the option selected within the 
 * radio group, and also controlling the visibility of a 
 * dynamic view(s) on the UI depending on the user selection.
 * 
 * @author timothyosullivan
 *
 */
public class RadioGroupCoordinatorListener implements OnCheckedChangeListener {
	
	public static final String RADIO_BUTTON_TEXT_DATA_KEY = "RadioButtonText";
	
	private AbstractPage page;
	private String dataKey;
	private List<DynamicView> dynamicViews;
	private ViewGroup parentView;
	private final int indexPosition;

	public RadioGroupCoordinatorListener(AbstractPage page, String dataKey, DynamicView dynamicView, 
			ViewGroup parentView, int indexPosition) {
		setPage(page);
		setDataKey(dataKey);
		setDynamicViews(Arrays.asList(dynamicView));
		setParentView(parentView);
		this.indexPosition = indexPosition;
	}
	
	public RadioGroupCoordinatorListener(AbstractPage page, String dataKey, List<DynamicView> dynamicViews, 
			ViewGroup parentView, int indexPosition) {
		setPage(page);
		setDataKey(dataKey);
		setDynamicViews(dynamicViews);
		setParentView(parentView);
		this.indexPosition = indexPosition;
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
		
		// Thirdly, need to handle the View(s) that this RadioButton is controlling		
		if (radioButton.getText().toString().equals("No")) {
			for (DynamicView dynamicView : getDynamicViews()) {
				if (dynamicView.getControlledElement() instanceof RadioGroup) {
					((RadioGroup) dynamicView.getControlledElement()).clearCheck();
				}
				else if (dynamicView.getControlledElement() instanceof EditText) {
					((EditText) dynamicView.getControlledElement()).setText(null);
				}
			
				int index = getParentView().indexOfChild(dynamicView.getWrappedView());
				if (index != -1) {				
					getParentView().removeViewAt(index);
				}
			}
		}	
		else { // user has selected 'YES'
			for (int counter = 0; counter < getDynamicViews().size(); counter++) {
				getParentView().addView(getDynamicViews().get(counter).getWrappedView(), getIndexPosition() + counter);
			}
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

	/**
	 * Getter Method: getDynamicViews()
	 */
	private List<DynamicView> getDynamicViews() {
		return dynamicViews;
	}

	/**
	 * Setter Method: setDynamicViews()
	 */
	private void setDynamicViews(List<DynamicView> dynamicViews) {
		this.dynamicViews = dynamicViews;
	}

	/**
	 * Getter Method: getParentView()
	 */
	private ViewGroup getParentView() {
		return parentView;
	}

	/**
	 * Setter Method: setParentView()
	 */
	private void setParentView(ViewGroup parentView) {
		this.parentView = parentView;
	}

	/**
	 * Getter Method: getIndexPosition()
	 * 
	 * Not: indexPosition is final
	 * 
	 */
	private int getIndexPosition() {
		return indexPosition;
	}
	
}
