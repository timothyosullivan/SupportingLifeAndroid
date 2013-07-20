package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.ui.BreathingAssessmentFragment;

import java.util.ArrayList;

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
    public static final String CHEST_INDRAWING_DATA_KEY = "CHEST_INDRAWING";
    
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
    	Resources resources = ((AbstractWizardModel) getModelCallbacks()).applicationContext.getResources();
    	
    	// does the child have cough or difficult breathing?
    	String coughDifficultBreathingLabel = resources.getString(R.string.breathing_assessment_review_cough_difficult_breathing);
    	String coughDifficultBreathingValue = getPageData().getString(COUGH_DIFFICULT_BREATHING_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(coughDifficultBreathingLabel, coughDifficultBreathingValue, getKey(), -1));    	
    	
    	// chest indrawing
    	String chestIndrawingLabel = resources.getString(R.string.breathing_assessment_review_chest_indrawing);
    	String chestIndrawingValue = getPageData().getString(CHEST_INDRAWING_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(chestIndrawingLabel, chestIndrawingValue, getKey(), -1));    	
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
