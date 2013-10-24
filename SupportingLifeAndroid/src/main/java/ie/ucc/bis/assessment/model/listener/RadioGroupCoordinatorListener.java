package ie.ucc.bis.assessment.model.listener;

import ie.ucc.bis.assessment.imci.model.DynamicView;
import ie.ucc.bis.assessment.model.AbstractPage;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
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
	public static final String DEFAULT_RADIO_BUTTON_ANIMATE_UP_TEXT = "No";

	private AbstractPage page;
	private String dataKey;
	private List<String> radioButtonAnimateUpText;
	private List<DynamicView> dynamicViews;
	private ViewGroup parentView;
	private View animatedView;

	public RadioGroupCoordinatorListener(AbstractPage page, String dataKey, List<DynamicView> dynamicViews, 
			ViewGroup parentView, View animatedView) {
		setPage(page);
		setDataKey(dataKey);
		setDynamicViews(dynamicViews);
		setParentView(parentView);

		List<String> animateUpRadioButtonTextTriggers = new ArrayList<String>();
		animateUpRadioButtonTextTriggers.add(DEFAULT_RADIO_BUTTON_ANIMATE_UP_TEXT);
		setRadioButtonAnimateUpText(animateUpRadioButtonTextTriggers);
		
		setAnimatedView(animatedView);
	}

	public RadioGroupCoordinatorListener(AbstractPage page, String dataKey, List<DynamicView> dynamicViews, 
			ViewGroup parentView, View animatedView, List<String> animateUpTextTriggers) {
		setPage(page);
		setDataKey(dataKey);
		setDynamicViews(dynamicViews);
		setParentView(parentView);
		setAnimatedView(animatedView);
		setRadioButtonAnimateUpText(animateUpTextTriggers);
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {

		int indexPosition = getParentView().indexOfChild(getAnimatedView()) + 1;

		RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
		// firstly add text label of radio button
		// needed for displaying review UI screen
		getPage().getPageData().putString(dataKey + RADIO_BUTTON_TEXT_DATA_KEY, radioButton.getText().toString());

		// secondly add the id of the radio button
		// needed for onCreateView() in relevant Fragment to re-display
		// a view from page data
		getPage().getPageData().putInt(dataKey, checkedId);

		// Thirdly, need to handle the View(s) that this RadioButton is controlling
		if (radioButtonAnimateUpEvent(radioButton.getText().toString())) {
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
		else { // e.g. user has selected 'YES'
			for (int counter = 0; counter < getDynamicViews().size(); counter++) {
				getParentView().addView(getDynamicViews().get(counter).getWrappedView(), indexPosition + counter);
			}
		}
		getPage().notifyDataChanged();
	}

	/**
	 * Method responsible for determining whether an animate up event should occur 
	 * on the dynamic view given the radio button selected
	 * 
	 * 
	 * @param string
	 * @return - boolean (if this constitutes an animateUpEvent)
	 */
	private boolean radioButtonAnimateUpEvent(String radioButtonClickedText) {

		for (String animateUpText : getRadioButtonAnimateUpText()) {
			if (animateUpText.equalsIgnoreCase(radioButtonClickedText)) {
				return true;
			}
		}
		return false;
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
	 * Getter Method: getRadioButtonAnimateUpText()
	 */
	public List<String> getRadioButtonAnimateUpText() {
		return radioButtonAnimateUpText;
	}

	/**
	 * Setter Method: setRadioButtonAnimateUpText()
	 */
	public void setRadioButtonAnimateUpText(List<String> radioButtonAnimateUpText) {
		this.radioButtonAnimateUpText = radioButtonAnimateUpText;
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
	 * Getter Method: getAnimatedView()
	 */
	public View getAnimatedView() {
		return animatedView;
	}

	/**
	 * Setter Method: setAnimatedView()
	 */
	public void setAnimatedView(View animatedView) {
		this.animatedView = animatedView;
	}

}
