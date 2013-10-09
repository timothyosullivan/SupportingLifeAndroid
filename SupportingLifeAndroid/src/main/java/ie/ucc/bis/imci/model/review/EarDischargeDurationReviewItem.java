package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.rule.engine.enums.Response;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * Represents the 'Ear Pus Discharge Duration' TextField review item.
 * 
 * Responsible for determining if, based on the user input,
 * whether the ear pus discharge duration is greater\less than 14 days.
 *
 */
public class EarDischargeDurationReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = -2080321851403569181L;
	
	private static final int FOURTEEN_DAYS = 14;

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public EarDischargeDurationReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the ear pus discharge duration is greater\less than 14 days.
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		int pusDischargeDuration = Integer.parseInt(getDisplayValue());
    		if (pusDischargeDuration >= FOURTEEN_DAYS) {
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
