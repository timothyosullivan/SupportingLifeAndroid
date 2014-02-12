package ie.ucc.bis.supportinglife.assessment.ccm.model.review;

import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.rule.engine.enums.Response;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * Represents the CCM 'Cough Duration' TextField review item.
 * 
 * Responsible for determining if, based on the user input,
 * whether the cough discharge duration is greater\less than 21 days.
 * 
 * @author timothyosullivan
 */
public class CoughDurationCcmReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 6971935969236595260L;
	
	private static final int TWENTY_ONE_DAYS = 21;

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
    public CoughDurationCcmReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, String identifier) {
    	super(title, displayValue, symptomId, pageKey, weight, identifier);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the cough duration is greater\less than 21 days.
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		int coughDuration = Integer.parseInt(getDisplayValue());
    		if (coughDuration >= TWENTY_ONE_DAYS) {
    			setSymptomValue(Response.YES.name());
    		}
    		else {
    			setSymptomValue(Response.NO.name());
    		}
    	}
    	else {
    		setSymptomValue(Response.NO.name());
    	}
    }
	
}
