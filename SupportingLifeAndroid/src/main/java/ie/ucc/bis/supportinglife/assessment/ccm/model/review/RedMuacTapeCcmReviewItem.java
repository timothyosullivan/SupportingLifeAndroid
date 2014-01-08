package ie.ucc.bis.supportinglife.assessment.ccm.model.review;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.model.listener.DateDialogSetListener;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.rule.engine.enums.Response;
import ie.ucc.bis.supportinglife.ui.utilities.DateUtilities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.res.Resources;
import android.text.TextUtils;

/**
 * Responsible for determining if, based on the user input,
 * whether:
 * (1) - Child aged between 6 months and 5 years
 * (2) - Red Colour on MUAC Tape 
 * 
 * @author timothyosullivan
 */
public class RedMuacTapeCcmReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = -3501970374373692467L;

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
	public RedMuacTapeCcmReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, List<ReviewItem> dependeeReviewItems) {
		super(title, displayValue, symptomId, pageKey, weight, false);
		setDependees(dependeeReviewItems);
	}

	/**
	 * Method: assessSymptom()
	 * 
	 * Responsible for determining if, based on the user input,
	 * whether:
	 * (1) - Child aged between 6 months and 5 years
	 * (2) - Red Colour on MUAC Tape 
	 * 
	 * @param supportingLifeBaseActivity 
	 * 
	 */
	public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
		if (!TextUtils.isEmpty(getDisplayValue())) {
			Resources resources = supportingLifeBaseActivity.getResources();
			ReviewItem birthDateReviewItem = getDependees().get(0);

			if (birthDateReviewItem.getDisplayValue() != null) {	
				try {
					Date birthDate = new SimpleDateFormat(DateDialogSetListener.DATE_TIME_CUSTOM_FORMAT, DateDialogSetListener.LOCALE).parse(birthDateReviewItem.getDisplayValue());
					Calendar cal = Calendar.getInstance();

					int monthsDifference = DateUtilities.getDiffMonths(birthDate, cal.getTime());

					/* Rule:
					 * 	Child aged between 6 months and 5 years
					 * 					AND
					 * 			Red Colour on MUAC Tape
					 */
					if ((monthsDifference >= SIX_MONTHS) && (monthsDifference < FIVE_YEARS_IN_MONTHS)
							&& (getDisplayValue().equals(resources.getString(R.string.ccm_look_assessment_radio_muac_tape_colour_red)))) {
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
