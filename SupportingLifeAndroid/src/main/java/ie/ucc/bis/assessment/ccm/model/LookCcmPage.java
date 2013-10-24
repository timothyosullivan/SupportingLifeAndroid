package ie.ucc.bis.assessment.ccm.model;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.ccm.ui.LookCcmFragment;
import ie.ucc.bis.assessment.model.AbstractModel;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.ModelCallbacks;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.assessment.model.review.FastBreathingReviewItem;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.ui.utilities.ReviewItemUtilities;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: CCM Look Assessment
 * 
 * Stage in CCM bread-crumb UI Wizard: 4
 * 
 * Responsible for displaying form for CCM 'Look' assessment patient details
 * 
 * @author timothyosullivan
 */
public class LookCcmPage extends AbstractPage {
	public static final String CHEST_INDRAWING_DATA_KEY = "CHEST_INDRAWING";
    public static final String BREATHS_PER_MINUTE_DATA_KEY = "BREATHS_PER_MINUTE";
    public static final String VERY_SLEEPY_OR_UNCONSCIOUS_DATA_KEY = "VERY_SLEEPY_OR_UNCONSCIOUS";
    public static final String PALMAR_PALLOR_DATA_KEY = "PALMAR_PALLOR";
    public static final String MUAC_TAPE_COLOUR_DATA_KEY = "MUAC_TAPE_COLOUR";
    public static final String SWELLING_OF_BOTH_FEET_DATA_KEY = "SWELLING_OF_BOTH_FEET"; 
    
    private LookCcmFragment lookCcmFragment;

    public LookCcmPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
    	setLookCcmFragment(LookCcmFragment.create(getKey()));
        return getLookCcmFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 'look assessment' page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	Resources resources = ((AbstractModel) getModelCallbacks()).getApplicationContext().getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	String reviewItemSymptomId = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.ccm_look_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));	
      	    	    	    	    	
    	// chest indrawing
    	reviewItemLabel = resources.getString(R.string.ccm_look_assessment_review_chest_indrawing);
    	reviewItemValue = getPageData().getString(CHEST_INDRAWING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_look_assessment_chest_indrawing_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// breaths per minute
    	reviewItemLabel = resources.getString(R.string.ccm_look_assessment_review_breaths_per_minute);
    	reviewItemSymptomId = resources.getString(R.string.ccm_look_assessment_fast_breathing_symptom_id);
    	// note: In assessing whether the 'fast breathing' symptom applies when interpreting the 'breaths per minute',
    	//       the age of the child is a determining factor. Therefore the date of birth child needs to capture to
    	//       facilitate the decision logic.
    	String birthDateSymptomId = resources.getString(R.string.ccm_general_patient_details_date_of_birth_symptom_id);
    	ReviewItem birthDateReviewItem = ReviewItemUtilities.findReviewItemBySymptomId(birthDateSymptomId, reviewItems);
    	reviewItems.add(new FastBreathingReviewItem(reviewItemLabel, getPageData().getString(BREATHS_PER_MINUTE_DATA_KEY), 
    			reviewItemSymptomId, getKey(), -1, Arrays.asList(birthDateReviewItem)));

    	// very sleepy or unconscious
    	reviewItemLabel = resources.getString(R.string.ccm_look_assessment_review_very_sleepy_or_unconscious);
    	reviewItemValue = getPageData().getString(VERY_SLEEPY_OR_UNCONSCIOUS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_look_assessment_very_sleepy_or_unconscious_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// palmar pallor
    	reviewItemLabel = resources.getString(R.string.ccm_look_assessment_review_palmar_pallor);
    	reviewItemValue = getPageData().getString(PALMAR_PALLOR_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_look_assessment_palmar_pallor_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// muac tape colour
    	reviewItemLabel = resources.getString(R.string.ccm_look_assessment_review_muac_tape_colour);
    	reviewItemValue = getPageData().getString(MUAC_TAPE_COLOUR_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_look_assessment_muac_tape_colour_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// swelling of both feet
    	reviewItemLabel = resources.getString(R.string.ccm_look_assessment_review_swelling_of_both_feet);
    	reviewItemValue = getPageData().getString(SWELLING_OF_BOTH_FEET_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_look_assessment_swelling_of_both_feet_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
     //   return !TextUtils.isEmpty(getPageData().getString(FIRST_NAME_DATA_KEY));
    	return true;
    }

	/**
	 * Getter Method: getLookCcmFragment()
	 */    
	public LookCcmFragment getLookCcmFragment() {
		return lookCcmFragment;
	}

	/**
	 * Setter Method: setLookCcmFragment()
	 */		
	public void setLookCcmFragment(LookCcmFragment lookCcmFragment) {
		this.lookCcmFragment = lookCcmFragment;
	}
}
