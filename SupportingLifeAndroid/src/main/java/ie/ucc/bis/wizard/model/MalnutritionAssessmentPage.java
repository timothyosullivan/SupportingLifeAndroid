package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.ui.MalnutritionAssessmentFragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: Malnutrition and Anaemia Assessment
 * 
 * Stage in bread-crumb UI Wizard: 7
 * 
 * Responsible for displaying form to record the malnutrition
 * and anaemia assessment of a patient
 */
public class MalnutritionAssessmentPage extends AbstractPage {
    public static final String OEDEMA_DATA_KEY = "OEDEMA";
    public static final String WEIGHT_FOR_AGE_DATA_KEY = "WEIGHT_FOR_AGE";
    public static final String VISIBLE_SEVERE_WASTING_DATA_KEY = "VISIBLE_SEVERE_WASTING";
    
    private MalnutritionAssessmentFragment malnutritionAssessmentFragment;

    public MalnutritionAssessmentPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
    	setMalnutritionAssessmentFragment(MalnutritionAssessmentFragment.create(getKey()));
        return getMalnutritionAssessmentFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 
	 * 'malnutrition and anaemia assessment' page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	Context appContext = ((AbstractWizardModel) getModelCallbacks()).applicationContext;
    	Resources resources = appContext.getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	
    	// oedema of both feet
    	reviewItemLabel = resources.getString(R.string.malnutrition_assessment_review_oedema);
    	reviewItemValue = getPageData().getString(OEDEMA_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));
    	
    	// weight for age
    	reviewItemLabel = resources.getString(R.string.malnutrition_assessment_review_weight_for_age);
    	reviewItemValue = getPageData().getString(WEIGHT_FOR_AGE_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));
    	
    	// visible severe wasting
    	reviewItemLabel = resources.getString(R.string.malnutrition_assessment_review_visible_severe_wasting);
    	reviewItemValue = getPageData().getString(VISIBLE_SEVERE_WASTING_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));    	
    }

	/**
	 * Getter Method: getMalnutritionAssessmentFragment()
	 * 
	 */	
	public MalnutritionAssessmentFragment getMalnutritionAssessmentFragment() {
		return malnutritionAssessmentFragment;
	}

	/**
	 * Setter Method: setMalnutritionAssessmentFragment()
	 * 
	 */		
	public void setMalnutritionAssessmentFragment(MalnutritionAssessmentFragment malnutritionAssessmentFragment) {
		this.malnutritionAssessmentFragment = malnutritionAssessmentFragment;
	}
}