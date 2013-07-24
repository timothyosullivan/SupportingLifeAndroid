package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.ui.EarAssessmentFragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: Fever Assessment
 * 
 * Stage in bread-crumb UI Wizard: 6
 * 
 * Responsible for displaying form to record the ear
 * assessment of a patient
 */
public class EarAssessmentPage extends AbstractPage {
    public static final String EAR_PROBLEM_DATA_KEY = "EAR_PROBLEM";
    public static final String EAR_PAIN_DATA_KEY = "EAR_PAIN";
    public static final String EAR_DISCHARGE_DATA_KEY = "EAR_DISCHARGE";
    
    private EarAssessmentFragment earAssessmentFragment;

    public EarAssessmentPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
    	setEarAssessmentFragment(EarAssessmentFragment.create(getKey()));
        return getEarAssessmentFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 'ear assessment' page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	Context appContext = ((AbstractWizardModel) getModelCallbacks()).applicationContext;
    	Resources resources = appContext.getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	
    	// ear problem
    	reviewItemLabel = resources.getString(R.string.ear_assessment_review_ear_problem);
    	reviewItemValue = getPageData().getString(EAR_PROBLEM_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));
    	
    	// ear pain
    	reviewItemLabel = resources.getString(R.string.ear_assessment_review_ear_pain);
    	reviewItemValue = getPageData().getString(EAR_PAIN_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));
    	
    	// ear discharge
    	reviewItemLabel = resources.getString(R.string.ear_assessment_review_ear_discharge);
    	reviewItemValue = getPageData().getString(EAR_DISCHARGE_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));    	
    }

	/**
	 * Getter Method: getEarAssessmentFragment()
	 * 
	 */	
	public EarAssessmentFragment getEarAssessmentFragment() {
		return earAssessmentFragment;
	}

	/**
	 * Setter Method: setEarAssessmentFragment()
	 * 
	 */		
	public void setEarAssessmentFragment(EarAssessmentFragment earAssessmentFragment) {
		this.earAssessmentFragment = earAssessmentFragment;
	}
}
