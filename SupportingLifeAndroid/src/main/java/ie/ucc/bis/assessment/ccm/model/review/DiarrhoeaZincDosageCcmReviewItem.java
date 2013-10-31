package ie.ucc.bis.assessment.ccm.model.review;

import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.listener.DateDialogSetListener;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.ui.utilities.DateUtilities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * Responsible for determining if, based on the user input,
 * whether the diarrhoea zinc dose to prescribe to a patient based on
 * their age.
 * 
 * RULE - Dose for zinc supplement:
 * 			Age 2 months up to 6 months: 1/2 tablet
 *			Age 12 months up to 5 years: 1 tablet
 * 
 * @author timothyosullivan
 */
public class DiarrhoeaZincDosageCcmReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 8457780004226103954L;

	private static final String BETWEEN_2_MONTHS_AND_6_MONTHS = "BETWEEN_2_MONTHS_AND_6_MONTHS";
	private static final String BETWEEN_6_MONTHS_AND_5_YEARS = "BETWEEN_6_MONTHS_AND_5_YEARS";
		
	private static final int TWO_MONTHS = 2;
	private static final int SIX_MONTHS = 6;
	private static final int FIVE_YEARS_IN_MONTHS = 60;

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public DiarrhoeaZincDosageCcmReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, List<ReviewItem> dependeeReviewItems) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    	setDependees(dependeeReviewItems);
    	// turn off visibility of this review item
    	setVisible(false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the diarrhoea zinc dose to prescribe to a patient based on
     * their age.
     * 
     * RULE - Dose for zinc supplement:
     * 			Age 2 months up to 6 months: 1/2 tablet
     *			Age 12 months up to 5 years: 1 tablet
     * 
     * @param supportingLifeBaseActivity 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	ReviewItem birthDateReviewItem = getDependees().get(0);
    	if (birthDateReviewItem.getDisplayValue() != null) {	
			try {
				Date birthDate = new SimpleDateFormat(DateDialogSetListener.DATE_TIME_CUSTOM_FORMAT, DateDialogSetListener.LOCALE).parse(birthDateReviewItem.getDisplayValue());
				Calendar cal = Calendar.getInstance();
					
				int monthsDifference = DateUtilities.getDiffMonths(birthDate, cal.getTime());

				/* Rule:
				 * Age 2 months up to 6 months: 1/2 tablet
				 */
				if ((monthsDifference >= TWO_MONTHS) && (monthsDifference <= SIX_MONTHS)) {
					setSymptomValue(BETWEEN_2_MONTHS_AND_6_MONTHS);
				}
				/* Rule:
				 * Age 12 months up to 5 years: 1 tablet
				 */
				else if ((monthsDifference > SIX_MONTHS) && (monthsDifference <= FIVE_YEARS_IN_MONTHS)) {
					setSymptomValue(BETWEEN_6_MONTHS_AND_5_YEARS);
				}
	    	} catch (ParseException e) {
	    		e.printStackTrace();
	   		}
    	}
    }
}
