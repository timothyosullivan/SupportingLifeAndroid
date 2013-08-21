package ie.ucc.bis.wizard.model.review;

import java.io.Serializable;

/**
 * Represents a single radio toggle review item.
 * 
 * Responsible for determining if, based on the user input,
 * whether the symptom applies to the patient being assessed.
 *
 */
public class RadioToggleReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 3173057953023920926L;
	private static final String NEGATIVE_CASE = "NO";

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     * 
     */
    public RadioToggleReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, false, NEGATIVE_CASE);
    }
   
    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     * @param symptomNegativeCriteria
     * 
     */
    public RadioToggleReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, String symptomNegativeCriteria) {
    	super(title, displayValue, symptomId, pageKey, weight, false, symptomNegativeCriteria);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Determine if, based on the user input,
     * the symptom applies to the patient being assessed.
     */
    public void assessSymptom() {
    	if (getDisplayValue() == null || getNegativeSymptomCriteria().equalsIgnoreCase(getDisplayValue())) {
    		setPositiveSymptom(false);
    	}
    	else {
    		setPositiveSymptom(true);
    	}
    }
	
}
