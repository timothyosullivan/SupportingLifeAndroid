package ie.ucc.bis.wizard.model.review;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;

import java.io.Serializable;

import android.content.res.Resources;
import android.text.TextUtils;

/**
 * Represents the 'Malaria' Radio Group review item.
 * 
 * Responsible for applying the correct Symptom value to this review item 
 * during the assessSymptom review stage
 * 
 */
public class MalariaReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 7898020781370077154L;
	
	/** These symptom values will be cross-referenced 
	*   against the 'classification_rules.xml' by the rule engine
	*/
	private static final String HIGH_MALARIA_RISK = "high malaria risk";
	private static final String LOW_MALARIA_RISK = "low malaria risk";
	private static final String NO_MALARIA_RISK = "no malaria risk";
	
    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public MalariaReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
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
    	
    		if (getDisplayValue().equals(resources.getString(R.string.fever_assessment_radio_malaria_risk_high))) {
    			// High Malaria Risk
    			setSymptomValue(HIGH_MALARIA_RISK);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.fever_assessment_radio_malaria_risk_low))) {
    			// Low Malaria Risk
    			setSymptomValue(LOW_MALARIA_RISK);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.fever_assessment_radio_malaria_risk_no))) {
    			// No Malaria Risk
    			setSymptomValue(NO_MALARIA_RISK);
    		}
    		
    	}
    }
	
}
