package ie.ucc.bis.supportinglife.assessment.ccm.model.review;

import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.rule.engine.enums.Response;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * Represents the CCM 'Red Eyes Duration' TextField review item.
 * 
 * Responsible for determining if, based on the user input,
 * whether the fever duration is greater\less than 4 days.
 * 
 * @author timothyosullivan
 */
public class RedEyesDurationCcmReviewItem extends ReviewItem implements Serializable {

	private static final long serialVersionUID = 941475367816942426L;
	/**
	 *  Generated Serial ID
	 */

	private static final int FOUR_DAYS = 4;

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public RedEyesDurationCcmReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the 'Red Eyes Duration' is greater\less than 4 days.
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		int feverDuration = Integer.parseInt(getDisplayValue());
    		if (feverDuration >= FOUR_DAYS) {
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
