package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.rule.engine.enums.Response;

import java.io.Serializable;

import android.content.res.Resources;
import android.text.TextUtils;

/**
 * Represents the 'Fever' Radio Group review item.
 * 
 * Responsible for applying the correct Symptom value to this review item 
 * during the assessSymptom review stage
 * 
 * @author timothyosullivan
 */
public class FeverReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = -3235895868182428370L;
	
	/** These symptom values will be cross-referenced 
	*   against the 'classification_rules.xml' by the rule engine
	*/
	private static final String HISTORY = "history";
	private static final String FEELS_HOT = "feels hot";
	private static final String HIGH_TEMPERATURE = "high temperature";
	
    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public FeverReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for applying the correct Symptom value to 
     * this review item 
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		Resources resources = supportingLifeBaseActivity.getResources();
    	
    		if (getDisplayValue().equals(resources.getString(R.string.assessment_wizard_radio_no))) {
    			// No
    			setSymptomValue(Response.NO.name());
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.imci_fever_assessment_radio_fever_history))) {
    			// History
    			setSymptomValue(HISTORY);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.imci_fever_assessment_radio_fever_feels_hot))) {
    			// Feels Hot
    			setSymptomValue(FEELS_HOT);
    		}
    		else if (getDisplayValue().contains(resources.getString(R.string.imci_fever_assessment_radio_fever_temperature))) {
    			// High Temperature
    			setSymptomValue(HIGH_TEMPERATURE);
    		}    		
    	}
    }
	
}
