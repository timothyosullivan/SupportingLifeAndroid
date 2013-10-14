package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.rule.engine.enums.Response;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * Represents the 'Fever Duration' TextField review item.
 * 
 * Responsible for determining if, based on the user input,
 * whether the fever duration is greater\less than 7 days.
 * 
 * @author timothyosullivan
 */
public class FeverDurationReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = -2572538871649367919L;
	
	private static final int SEVEN_DAYS = 7;

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public FeverDurationReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the fever duration is greater\less than 7 days.
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		int pusDischargeDuration = Integer.parseInt(getDisplayValue());
    		if (pusDischargeDuration > SEVEN_DAYS) {
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
