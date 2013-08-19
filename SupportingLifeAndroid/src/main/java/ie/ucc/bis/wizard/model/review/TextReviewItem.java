package ie.ucc.bis.wizard.model.review;

import java.io.Serializable;
import android.text.TextUtils;

/**
 * Represents a single 'generic' TextField review item.
 * 
 * Responsible for determining if, based on the user input,
 * whether the symptom applies to the patient being assessed.
 *
 */
public class TextReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 3173057953023920926L;

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public TextReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Determine if, based on the user input,
     * the symptom applies to the patient being assessed.
     */
    public void assessSymptom() {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		setPositiveSymptom(true);
    	}
    	else {
    		setPositiveSymptom(false);
    	}
    }
	
}
