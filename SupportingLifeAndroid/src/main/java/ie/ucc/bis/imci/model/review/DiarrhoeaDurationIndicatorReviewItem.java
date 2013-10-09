package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.rule.engine.enums.Response;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * Responsible for determining if, based on the user input,
 * whether the patient has had diarrhoea for 14 days or more.
 *
 */
public class DiarrhoeaDurationIndicatorReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 1302709118714804226L;


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
    public DiarrhoeaDurationIndicatorReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the patient has had diarrhoea for 14 days or more.
	 * 
     * Will determine the appropriate symptom value based
     * on the user's response.
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		int diarrhoeaDischargeDuration = Integer.parseInt(getDisplayValue());
    		if (diarrhoeaDischargeDuration >= FOURTEEN_DAYS) {
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
