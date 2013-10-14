package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.listener.DateDialogSetListener;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.rule.engine.enums.Response;
import ie.ucc.bis.ui.utilities.DateUtilities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.text.TextUtils;

/**
 * Represents the 'Breaths per Minute' TextField review item.
 * 
 * Responsible for determining if, based on the user input,
 * whether the fast breathing symptom applies to the patient being assessed.
 * 
 * RULE:
 * Fast Breathing applies if the following criteria is met:
 * 
 * [2 months < Child Age < 12 months] 
 * 		--> 50 breaths per minute or more == Fast Breathing
 * 
 * [12 months < Child Age < 5 years] 
 * 		--> 40 breaths per minute or more == Fast Breathing
 * 
 * @author timothyosullivan
 */
public class FastBreathingReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 3173057953023920926L;
		
	private static final int TWO_MONTHS = 2;
	private static final int TWELVE_MONTHS = 12;
	private static final int FIVE_YEARS_IN_MONTHS = 60;
	private static final int FORTY_BREATHS_PER_MINUTE = 40;
	private static final int FIFTY_BREATHS_PER_MINUTE = 50;

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public FastBreathingReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, List<ReviewItem> dependeeReviewItems) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    	setDependees(dependeeReviewItems);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Determine the appropriate symptom value based
     * on the user's response.
     * 
	 * RULE:
	 * Fast Breathing applies if the following criteria is met:
	 * 
	 * [2 months < Child Age < 12 months] 
	 * 		--> 50 breaths per minute or more == Fast Breathing
	 * 
	 * [12 months < Child Age < 5 years] 
	 * 		--> 40 breaths per minute or more == Fast Breathing
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		
    		ReviewItem birthDateReviewItem = getDependees().get(0);
    		if (birthDateReviewItem.getDisplayValue() != null) {	
				try {
					Date birthDate = new SimpleDateFormat(DateDialogSetListener.DATE_TIME_CUSTOM_FORMAT, DateDialogSetListener.LOCALE).parse(birthDateReviewItem.getDisplayValue());
					Calendar cal = Calendar.getInstance();
					
					int monthsDifference = DateUtilities.getDiffMonths(birthDate, cal.getTime());

					/* Rule:
					 * [2 months < Child Age < 12 months] 
					 * 		--> 50 breaths per minute or more == Fast Breathing
					 */
					if ((monthsDifference > TWO_MONTHS) && (monthsDifference < TWELVE_MONTHS) && (Integer.getInteger(getDisplayValue()).intValue() > FIFTY_BREATHS_PER_MINUTE)) {
						setSymptomValue(Response.YES.name());
					}
					/* Rule:
					 * [12 months < Child Age < 60 months] 
					 * 		--> 40 breaths per minute or more == Fast Breathing
					 */
					else if ((monthsDifference > TWELVE_MONTHS) && (monthsDifference < FIVE_YEARS_IN_MONTHS) && (Integer.parseInt(getDisplayValue()) > FORTY_BREATHS_PER_MINUTE)) {
						setSymptomValue(Response.YES.name());
					}
					else {
						setSymptomValue(Response.NO.name());
					}
	    		} catch (ParseException e) {
	    			e.printStackTrace();
	    		}
    		}
    	}
    	else {
    		setSymptomValue(Response.NO.name());
    	}
    }
	
}
