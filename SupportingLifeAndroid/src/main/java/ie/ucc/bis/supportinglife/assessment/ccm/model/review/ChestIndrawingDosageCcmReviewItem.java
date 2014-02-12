package ie.ucc.bis.supportinglife.assessment.ccm.model.review;

import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.model.listener.DateDialogSetListener;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.ui.utilities.DateUtilities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * Responsible for determining if, based on the user input,
 * whether the chest indrawing dose to prescribe to a patient based on
 * their age.
 * 
 * RULE -  Give first dose of oral antibiotic
 * 		   (Cotrimoxazole adult tablet - 80/400)
 * Note: Dose for oral antibiotic:
 * 		 	Age 2 months up 12 months: 1/2 tablet
 * 		 	Age 12 months up to 5 years: 1 tablet
 * 
 * @author timothyosullivan
 */
public class ChestIndrawingDosageCcmReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	 private static final long serialVersionUID = 2624038411291060717L;


	private static final String BETWEEN_2_MONTHS_AND_12_MONTHS = "BETWEEN_2_MONTHS_AND_12_MONTHS";
	private static final String BETWEEN_12_MONTHS_AND_5_YEARS = "BETWEEN_12_MONTHS_AND_5_YEARS";
		
	private static final int TWO_MONTHS = 2;
	private static final int TWELVE_MONTHS = 12;
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
    public ChestIndrawingDosageCcmReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, List<ReviewItem> dependeeReviewItems) {
    	super(title, displayValue, symptomId, pageKey, weight, null);
    	setDependees(dependeeReviewItems);
    	// turn off visibility of this review item
    	setVisible(false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for determining if, based on the user input,
     * whether the chest indrawing dose to prescribe to a patient based on
     * their age.
     * 
     * RULE -  Give first dose of oral antibiotic
     * 		   (Cotrimoxazole adult tablet - 80/400)
     * Note: Dose for oral antibiotic:
     * 		 	Age 2 months up 12 months: 1/2 tablet
     * 		 	Age 12 months up to 5 years: 1 tablet
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
				 * Age 2 months up 12 months: 1/2 tablet
				 */
				if ((monthsDifference > TWO_MONTHS) && (monthsDifference <= TWELVE_MONTHS)) {
					setSymptomValue(BETWEEN_2_MONTHS_AND_12_MONTHS);
				}
				/* Rule:
				 * Age 12 months up to 5 years: 1 tablet
				 */
				else if ((monthsDifference > TWELVE_MONTHS) && (monthsDifference <= FIVE_YEARS_IN_MONTHS)) {
					setSymptomValue(BETWEEN_12_MONTHS_AND_5_YEARS);
				}
	    	} catch (ParseException e) {
	    		e.printStackTrace();
	   		}
    	}
    }
}
