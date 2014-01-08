package ie.ucc.bis.supportinglife.assessment.model.listener;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.imci.model.DynamicView;
import ie.ucc.bis.supportinglife.assessment.model.AbstractPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

public class DynamicViewListenerUtilities {

	/**
	 * addGenericDynamicViewListeners()
	 * 
	 * Responsible for adding listeners to a RadioGroup and an associated
	 * EditText. Additionally, this will control the visibility of the EditText
	 * depending on the option selected within the RadioGroup. 
	 * 
	 * @param animatedView - View to be animated (e.g. View encompassing 'Diarrhoea' radio group)
	 * @param dynamicView - View to be displayed depending on radio button selection (e.g. View encompassing 'Diarrhoea Duration' EditText)
	 * @param topLevelView - View encompassing all elements from initial animated element to the end of the layout
	 * @param radioGroup - RadioGroup dictating EditText visibility
	 * @param editText - EditText to be displayed depending on radio button selection
	 * @param radioGroupDataKey - To be used to facilitate display on Review Page
	 * @param editTextDataKey - To be used to facilitate display on Review Page
	 * @param resources
	 * @param page
	 * 
	 */
	public static void addGenericDynamicViewListeners(View animatedView, DynamicView dynamicView, ViewGroup topLevelView,
			RadioGroup radioGroup, EditText editText,
			String radioGroupDataKey, String editTextDataKey,
			Resources resources, AbstractPage page) {

		List<String> animateUpRadioButtonTextTriggers = new ArrayList<String>();
		animateUpRadioButtonTextTriggers.add(resources.getString(R.string.assessment_wizard_radio_no));

		// add listener to 'RadioGroup'
		radioGroup.setOnCheckedChangeListener(
				new RadioGroupCoordinatorListener(page,
						radioGroupDataKey, 
						Arrays.asList(dynamicView),
						topLevelView,
						animatedView,
						animateUpRadioButtonTextTriggers));

		// add listener to 'EditText'
		editText.addTextChangedListener(
				new AssessmentWizardTextWatcher(page, 
						editTextDataKey));	
	}

	
}
