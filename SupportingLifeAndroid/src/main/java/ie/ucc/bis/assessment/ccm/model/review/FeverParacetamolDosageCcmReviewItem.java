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
 * whether the fever paracetamol dose to prescribe to a patient based on
 * their age.
 * 
 * RULE - Dose for Paracetamol:
 * 			Age 2 months up to 3 years: 1/4 tablet (total 3 tabs)
 * 			Age 3 years up to 5 years: 1/2 tablet (total 6 tabs)
 * 
 * @author timothyosullivan
 */
public class FeverParacetamolDosageCcmReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 7445126108871125634L;


	private static final String BETWEEN_2_MONTHS_AND_3_YEARS = "BETWEEN_2_MONTHS_AND_3_YEARS";
	private static final String BETWEEN_3_YEARS_AND_5_YEARS = "BETWEEN_3_YEARS_AND_5_YEARS";
		
	private static final int TWO_MONTHS = 2;
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
    public FeverParacetamolDosageCcmReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, List<ReviewItem> dependeeReviewItems) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    	setDependees(dependeeReviewItems);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the fever paracetamol dose to prescribe to a patient based on
     * their age.
     * 
     * RULE - Dose for Paracetamol:
     * 			Age 2 months up to 3 years: 1/4 tablet (total 3 tabs)
     * 			Age 3 years up to 5 years: 1/2 tablet (total 6 tabs)
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
				 * Age 2 months up to 3 years: 1/4 tablet (total 3 tabs)
				 */
				if ((monthsDifference >= TWO_MONTHS) && (monthsDifference <= THREE_YEARS_IN_MONTHS)) {
					setSymptomValue(BETWEEN_2_MONTHS_AND_3_YEARS);
				}
				/* Rule:
				 * Age 3 years up to 5 years: 1/2 tablet (total 6 tabs)
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
