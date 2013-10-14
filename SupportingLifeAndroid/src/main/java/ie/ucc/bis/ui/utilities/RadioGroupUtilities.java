package ie.ucc.bis.ui.utilities;

import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 
 * @author timothyosullivan
 */
public class RadioGroupUtilities {

	/**
	 * Utility method to uncheck all radio buttons in 
	 * radio group
	 */
	public static void toggleRadioButtonsEnabledState(RadioGroup radioGroup, boolean enabled) {
		for (int radioButtonCounter = 0; radioButtonCounter < radioGroup.getChildCount(); radioButtonCounter++) {
			RadioButton radioButton = (RadioButton) radioGroup.getChildAt(radioButtonCounter);
			radioButton.setEnabled(enabled);
		}
	}
	
}
