package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.review.ReviewItem;

import java.io.Serializable;

import android.content.res.Resources;
import android.text.TextUtils;

/**
 * Represents the 'Weight For Age' Radio Group review item.
 * 
 * Responsible for applying the correct Symptom value to this review item 
 * during the assessSymptom review stage
 * 
 * @author timothyosullivan
 */
public class WeightForAgeReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 352555956347528769L;
	
	/** These symptom values will be cross-referenced 
	*   against the 'classification_rules.xml' by the rule engine
	*/
	private static final String VERY_LOW = "very low";
	private static final String NOT_VERY_LOW = "not very low";
	
    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public WeightForAgeReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
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
    	
    		if (getDisplayValue().equals(resources.getString(R.string.imci_malnutrition_assessment_radio_weight_for_age_very_low))) {
    			// Very Low (Weight For Age)
    			setSymptomValue(VERY_LOW);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.imci_malnutrition_assessment_radio_weight_for_age_not_very_low))) {
    			// Not Very Low (Weight For Age)
    			setSymptomValue(NOT_VERY_LOW);
    		}    		
    	}
    }
	
}
