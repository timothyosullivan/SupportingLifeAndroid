package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.ui.custom.ToggleButtonGroupTableLayout;
import ie.ucc.bis.wizard.ui.FeverAssessmentFragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: Fever Assessment
 * 
 * Stage in bread-crumb UI Wizard: 5
 * 
 * Responsible for displaying form to record the fever
 * assessment of a patient
 */
public class FeverAssessmentPage extends AbstractPage {
    public static final String FEVER_DATA_KEY = "FEVER";
    public static final String MALARIA_RISK_DATA_KEY = "MALARIA_RISK";
    public static final String DURATION_DATA_KEY = "DURATION";
    
    private FeverAssessmentFragment feverAssessmentFragment;

    public FeverAssessmentPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
    	setFeverAssessmentFragment(FeverAssessmentFragment.create(getKey()));
        return getFeverAssessmentFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 'fever assessment' page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	Context appContext = ((AbstractWizardModel) getModelCallbacks()).applicationContext;
    	Resources resources = appContext.getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.fever_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, null, getKey(), -1, true));

    	// fever
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_fever);
    	reviewItemValue = getPageData().getString(FEVER_DATA_KEY + ToggleButtonGroupTableLayout.TOGGLE_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));    	
    	
    	// malaria risk
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_malaria_risk);
    	reviewItemValue = getPageData().getString(MALARIA_RISK_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));
    	
    	// duration
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_duration);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(DURATION_DATA_KEY), getKey(), -1));
    }

	/**
	 * Getter Method: getFeverAssessmentFragment()
	 * 
	 */	
	public FeverAssessmentFragment getFeverAssessmentFragment() {
		return feverAssessmentFragment;
	}

	/**
	 * Setter Method: setFeverAssessmentFragment()
	 * 
	 */	
	public void setFeverAssessmentFragment(FeverAssessmentFragment feverAssessmentFragment) {
		this.feverAssessmentFragment = feverAssessmentFragment;
	}
}
