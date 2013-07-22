package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
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

    	// fever
    	String feverLabel = resources.getString(R.string.fever_assessment_review_fever);
    	String feverValue = getPageData().getString(FEVER_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(feverLabel, feverValue, getKey(), -1));    	
    	
    	// malaria risk
    	String malariaRiskLabel = resources.getString(R.string.fever_assessment_review_malaria_risk);
    	String malariaRiskValue = getPageData().getString(MALARIA_RISK_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(malariaRiskLabel, malariaRiskValue, getKey(), -1));
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
