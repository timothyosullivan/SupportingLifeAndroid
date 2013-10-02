package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.rule.engine.enums.Response;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * Represents the 'Cough Duration' TextField review item.
 * 
 * Responsible for determining if, based on the user input,
 * whether the cough duration is greater\less than 30 days.
 *
 */
public class CoughDurationReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 8051830093795501517L;
	
	private static final int THIRTY_DAYS = 30;

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public CoughDurationReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the cough duration is greater\less than 30 days.
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		int coughDuration = Integer.parseInt(getDisplayValue());
    		if (coughDuration > THIRTY_DAYS) {
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
