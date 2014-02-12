package ie.ucc.bis.supportinglife.assessment.imci.model.review;

import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.rule.engine.enums.Response;
import ie.ucc.bis.supportinglife.ui.utilities.DateUtilities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.text.TextUtils;

/**
 * Responsible for determining if, based on the user input,
 * whether the age of the patient is two years or more.
 *
 * @author timothyosullivan
 */
public class AgeIndicatorReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 2349779612568891193L;

	private static final int TWO_YEARS_IN_DAYS = 730;
    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public AgeIndicatorReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, null);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
	 * whether the age of the patient is two years or more.
	 * 
     * Will determine the appropriate symptom value based
     * on the user's response.
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
				try {
					Date birthDate = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).parse(getDisplayValue());
					Calendar cal = Calendar.getInstance();
					
					int daysDifference = DateUtilities.getDiffDays(birthDate, cal.getTime());

					if (daysDifference >= TWO_YEARS_IN_DAYS) {
						setSymptomValue(Response.YES.name());
					}
					else {
						setSymptomValue(Response.NO.name());
					}
	    		} catch (ParseException e) {
	    			e.printStackTrace();
	    		}
    	}
    	else {
    		setSymptomValue(Response.NO.name());
    	}
    }
	
}
