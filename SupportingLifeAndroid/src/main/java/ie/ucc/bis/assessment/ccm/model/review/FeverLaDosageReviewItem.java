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
 * whether the fever LA dose to prescribe to a patient based on
 * their age.
 * 
 * RULE - Dose for LA:
 * 			Age up 5 months: Not recommended
 * 			Age 5 months up to 3 years: 1 tablet
 * 			Age 3 years up to 5 years: 2 tablets
 * 
 * @author timothyosullivan
 */
public class FeverLaDosageReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = -5837072315820647232L;

	private static final String UP_TO_5_MONTHS = "UP_TO_5_MONTHS";
	private static final String BETWEEN_5_MONTHS_AND_3_YEARS = "BETWEEN_5_MONTHS_AND_3_YEARS";
	private static final String BETWEEN_3_YEARS_AND_5_YEARS = "BETWEEN_3_YEARS_AND_5_YEARS";
		
	private static final int FIVE_MONTHS = 5;
	private static final int THREE_YEARS_IN_MONTHS = 36;
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
    public FeverLaDosageReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, List<ReviewItem> dependeeReviewItems) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    	setDependees(dependeeReviewItems);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the fever dose to prescribe to a patient based on
     * their age.
     * 
     * RULE - Dose for LA:
     * 			Age up 5 months: Not recommended
     * 			Age 5 months up to 3 years: 1 tablet
     * 			Age 3 years up to 5 years: 2 tablets
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	ReviewItem birthDateReviewItem = getDependees().get(0);
    	if (birthDateReviewItem.getDisplayValue() != null) {	
			try {
				Date birthDate = new SimpleDateFormat(DateDialogSetListener.DATE_TIME_CUSTOM_FORMAT, DateDialogSetListener.LOCALE).parse(birthDateReviewItem.getDisplayValue());
				Calendar cal = Calendar.getInstance();
					
				int monthsDifference = DateUtilities.getDiffMonths(birthDate, cal.getTime());

				/* Rule:
				 * Age up 5 months : UP_TO_5_MONTHS
				 */
				if (monthsDifference < FIVE_MONTHS) {
					setSymptomValue(UP_TO_5_MONTHS);
				}
				/* Rule:
				 * Age 5 months up to 3 years: 1 tablet
				 */
				else if ((monthsDifference > FIVE_MONTHS) && (monthsDifference <= THREE_YEARS_IN_MONTHS)) {
					setSymptomValue(BETWEEN_5_MONTHS_AND_3_YEARS);
				}
				/* Rule:
				 * Age 3 years up to 5 years: 2 tablets
				 */
				else if ((monthsDifference > THREE_YEARS_IN_MONTHS) && (monthsDifference <= FIVE_YEARS_IN_MONTHS)) {
					setSymptomValue(BETWEEN_3_YEARS_AND_5_YEARS);
				}
	    	} catch (ParseException e) {
	    		e.printStackTrace();
	   		}
    	}
    }
}
