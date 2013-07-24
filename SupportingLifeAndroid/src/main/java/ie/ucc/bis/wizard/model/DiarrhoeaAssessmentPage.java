package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.ui.DiarrhoeaAssessmentFragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: Diarrhoea Assessment
 * 
 * Stage in bread-crumb UI Wizard: 4
 * 
 * Responsible for displaying form to record the diarrhoea
 * assessment of a patient
 */
public class DiarrhoeaAssessmentPage extends AbstractPage {
    public static final String DIARRHOEA_DATA_KEY = "DIARRHOEA";
    public static final String BLOOD_STOOLS_DATA_KEY = "BLOOD_STOOLS";
    
    private DiarrhoeaAssessmentFragment diarrhoeaAssessmentFragment;

    public DiarrhoeaAssessmentPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
    	setDiarrhoeaAssessmentFragment(DiarrhoeaAssessmentFragment.create(getKey()));
        return getDiarrhoeaAssessmentFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 'diarrhoea assessment' page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	Context appContext = ((AbstractWizardModel) getModelCallbacks()).applicationContext;
    	Resources resources = appContext.getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;

    	// diarrhoea
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_diarrhoea);
    	reviewItemValue = getPageData().getString(DIARRHOEA_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));    	
    	
    	// blood in the stools
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_blood_stools);
    	reviewItemValue = getPageData().getString(BLOOD_STOOLS_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));
    }

	/**
	 * Getter Method: getDiarrhoeaAssessmentFragment()
	 * 
	 */    
	public DiarrhoeaAssessmentFragment getDiarrhoeaAssessmentFragment() {
		return diarrhoeaAssessmentFragment;
	}

	/**
	 * Setter Method: setDiarrhoeaAssessmentFragment()
	 * 
	 */		
	public void setDiarrhoeaAssessmentFragment(DiarrhoeaAssessmentFragment diarrhoeaAssessmentFragment) {
		this.diarrhoeaAssessmentFragment = diarrhoeaAssessmentFragment;
	}
}
