package ie.ucc.bis.supportinglife.assessment.imci.model.review;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;

import java.io.Serializable;

import android.content.res.Resources;
import android.text.TextUtils;

/**
 * Represents the 'Palmar Pallor' Radio Group review item.
 * 
 * Responsible for applying the correct Symptom value to this review item 
 * during the assessSymptom review stage
 * 
 * @author timothyosullivan
 */
public class PalmarPallorReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 487744492503481764L;

	
	/** These symptom values will be cross-referenced 
	*   against the 'classification_rules.xml' by the rule engine
	*/
	private static final String SEVERE_PALMAR_PALLOR = "severe";
	private static final String SOME_PALMAR_PALLOR = "some";
	private static final String NO_PALMAR_PALLOR = "no";
	
    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     * @param identifier
     */
    public PalmarPallorReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, String identifier) {
    	super(title, displayValue, symptomId, pageKey, weight, identifier);
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
    	
    		if (getDisplayValue().equals(resources.getString(R.string.imci_malnutrition_assessment_radio_palmar_pallor_severe))) {
    			// Severe Palmar Pallor
    			setSymptomValue(SEVERE_PALMAR_PALLOR);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.imci_malnutrition_assessment_radio_palmar_pallor_some))) {
    			// Some Palmar Pallor
    			setSymptomValue(SOME_PALMAR_PALLOR);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.assessment_wizard_radio_no))) {
    			// No Palmar Pallor
    			setSymptomValue(NO_PALMAR_PALLOR);
    		}
    		
    	}
    }
	
}
