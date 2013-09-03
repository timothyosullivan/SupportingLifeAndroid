package ie.ucc.bis.wizard.model.review;

import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.rule.engine.enums.Response;
import ie.ucc.bis.ui.utilities.DateUtilities;

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
 */
public class AgeIndicatorReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 2349779612568891193L;

	private static final int TWO_YEARS_IN_MONTHS = 24;
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
    	super(title, displayValue, symptomId, pageKey, weight, false);
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
					
					int monthsDifference = DateUtilities.getDiffMonths(birthDate, cal.getTime());

					if (monthsDifference > TWO_YEARS_IN_MONTHS) {
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
