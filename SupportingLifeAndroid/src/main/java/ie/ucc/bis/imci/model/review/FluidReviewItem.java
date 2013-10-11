package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.review.ReviewItem;

import java.io.Serializable;

import android.content.res.Resources;
import android.text.TextUtils;

/**
 * Represents the 'Offer the Child Fluid' Radio Group review item.
 * 
 * Responsible for applying the correct Symptom value to this review item 
 * during the assessSymptom review stage
 * 
 */
public class FluidReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 5155038977430937759L;
	
	/** These symptom values will be cross-referenced 
	*   against the 'classification_rules.xml' by the rule engine
	*/
	private static final String NOT_ABLE_TO_DRINK = "not able to drink";
	private static final String DRINKING_POORLY = "drinking poorly";
	private static final String DRINKING_EAGERLY = "drinking eagerly";

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public FluidReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
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
    	
    		if (getDisplayValue().equals(resources.getString(R.string.imci_diarrhoea_assessment_radio_child_fluid_not_able))) {
    			// Not Able To Drink
    			setSymptomValue(NOT_ABLE_TO_DRINK);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.imci_diarrhoea_assessment_radio_child_fluid_drinking_poorly))) {
    			// Drinking Poorly
    			setSymptomValue(DRINKING_POORLY);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.imci_diarrhoea_assessment_radio_child_fluid_drinking_eagerly))) {
    			// Drinking Eagerly
    			setSymptomValue(DRINKING_EAGERLY);
    		}
    		
    	}
    }
	
}
