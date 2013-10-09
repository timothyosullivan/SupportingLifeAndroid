package ie.ucc.bis.imci.model;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.AbstractWizardModel;
import ie.ucc.bis.assessment.model.ModelCallbacks;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.imci.model.review.CoughDurationReviewItem;
import ie.ucc.bis.imci.model.review.FastBreathingReviewItem;
import ie.ucc.bis.imci.ui.BreathingAssessmentFragment;
import ie.ucc.bis.ui.utilities.ReviewItemUtilities;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: Cough / Breathing Assessment
 * 
 * Stage in bread-crumb UI Wizard: 3
 * 
 * Responsible for displaying form to record breathing & 
 * cough assessment of a patient
 * 
 */
public class BreathingAssessmentPage extends AbstractPage {
    public static final String COUGH_DIFFICULT_BREATHING_DATA_KEY = "COUGH_DIFFICULT_BREATHING";
    public static final String COUGH_DURATION_DATA_KEY = "COUGH_DURATION";
    public static final String BREATHS_PER_MINUTE_DATA_KEY = "BREATHS_PER_MINUTE";
    public static final String CHEST_INDRAWING_DATA_KEY = "CHEST_INDRAWING";
    public static final String STRIDOR_DATA_KEY = "STRIDOR";
    
    private BreathingAssessmentFragment breathingAssessmentFragment;

    public BreathingAssessmentPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
    	setBreathingAssessmentFragment(BreathingAssessmentFragment.create(getKey()));
        return getBreathingAssessmentFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 'general danger signs' page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	Resources resources = ((AbstractWizardModel) getModelCallbacks()).getApplicationContext().getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	String reviewItemSymptomId = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.breathing_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));	
    	
    	// does the child have cough or difficult breathing?
    	reviewItemLabel = resources.getString(R.string.breathing_assessment_review_cough_difficult_breathing);
    	reviewItemValue = getPageData().getString(COUGH_DIFFICULT_BREATHING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.breathing_assessment_cough_difficult_breathing_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// for how long? (days) - cough duration
    	reviewItemLabel = resources.getString(R.string.breathing_assessment_review_cough_duration);
    	reviewItemSymptomId = resources.getString(R.string.breathing_assessment_cough_duration_symptom_id);
    	reviewItems.add(new CoughDurationReviewItem(reviewItemLabel, getPageData().getString(COUGH_DURATION_DATA_KEY), reviewItemSymptomId, getKey(), -1));
 
    	// breaths per minute
    	reviewItemLabel = resources.getString(R.string.breathing_assessment_review_breaths_per_minute);
    	reviewItemSymptomId = resources.getString(R.string.breathing_assessment_fast_breathing_symptom_id);
    	// note: In assessing whether the 'fast breathing' symptom applies when interpreting the 'breaths per minute',
    	//       the age of the child is a determining factor. Therefore the date of birth child needs to capture to
    	//       facilitate the decision logic.
    	String birthDateSymptomId = resources.getString(R.string.general_patient_details_date_of_birth_symptom_id);
    	ReviewItem birthDateReviewItem = ReviewItemUtilities.findReviewItemBySymptomId(birthDateSymptomId, reviewItems);
    	reviewItems.add(new FastBreathingReviewItem(reviewItemLabel, getPageData().getString(BREATHS_PER_MINUTE_DATA_KEY), 
    			reviewItemSymptomId, getKey(), -1, Arrays.asList(birthDateReviewItem)));
    	
    	// chest indrawing
    	reviewItemLabel = resources.getString(R.string.breathing_assessment_review_chest_indrawing);
    	reviewItemValue = getPageData().getString(CHEST_INDRAWING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.breathing_assessment_chest_indrawing_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// stridor
    	reviewItemLabel = resources.getString(R.string.breathing_assessment_review_stridor);
    	reviewItemValue = getPageData().getString(STRIDOR_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.breathing_assessment_stridor_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));   
    }


	/**
	 * Getter Method: getGeneralDangerSignsFragment()
	 * 
	 */ 	
	public BreathingAssessmentFragment getBreathingAssessmentFragment() {
		return breathingAssessmentFragment;
	}

	/**
	 * Setter Method: setGeneralDangerSignsFragment()
	 * 
	 */			
	public void setBreathingAssessmentFragment(
			BreathingAssessmentFragment breathingAssessmentFragment) {
		this.breathingAssessmentFragment = breathingAssessmentFragment;
	}
}
