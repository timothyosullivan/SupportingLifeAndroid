package ie.ucc.bis.imci.model;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.AbstractWizardModel;
import ie.ucc.bis.assessment.model.ModelCallbacks;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.imci.model.review.PalmarPallorReviewItem;
import ie.ucc.bis.imci.model.review.WeightForAgeReviewItem;
import ie.ucc.bis.imci.ui.MalnutritionAssessmentFragment;

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
    public static final String PALMAR_PALLOR_DATA_KEY = "PALMAR_PALLOR";
    public static final String MEBENDAZOLE_DOSE_DATA_KEY = "MEBENDAZOLE_DOSE";
    
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
    	Context appContext = ((AbstractWizardModel) getModelCallbacks()).getApplicationContext();
    	Resources resources = appContext.getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	String reviewItemSymptomId = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.malnutrition_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));
    	
    	// oedema of both feet
    	reviewItemLabel = resources.getString(R.string.malnutrition_assessment_review_oedema);
    	reviewItemValue = getPageData().getString(OEDEMA_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.malnutrition_assessment_oedema_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// weight for age
    	reviewItemLabel = resources.getString(R.string.malnutrition_assessment_review_weight_for_age);
    	reviewItemValue = getPageData().getString(WEIGHT_FOR_AGE_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.malnutrition_assessment_weight_for_age_symptom_id);
    	reviewItems.add(new WeightForAgeReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// visible severe wasting
    	reviewItemLabel = resources.getString(R.string.malnutrition_assessment_review_visible_severe_wasting);
    	reviewItemValue = getPageData().getString(VISIBLE_SEVERE_WASTING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.malnutrition_assessment_visible_severe_wasting_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// palmar pallor
    	reviewItemLabel = resources.getString(R.string.malnutrition_assessment_review_palmar_pallor);
    	reviewItemValue = getPageData().getString(PALMAR_PALLOR_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.malnutrition_assessment_palmar_pallor_symptom_id);
    	reviewItems.add(new PalmarPallorReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));    	
    	
    	// mebendazole dose
    	reviewItemLabel = resources.getString(R.string.malnutrition_assessment_review_mebendazole_dose);
    	reviewItemValue = getPageData().getString(MEBENDAZOLE_DOSE_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.malnutrition_assessment_mebendazole_dose_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));      	
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
