package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.ui.custom.ToggleButtonGroupTableLayout;
import ie.ucc.bis.wizard.model.listener.RadioGroupListener;
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
    public static final String FEVER_PRESENT_EVERY_DAY_DATA_KEY = "FEVER_PRESENT_EVERY_DAY";
    public static final String MEASLES_DATA_KEY = "MEASLES";
    public static final String STIFF_NECK_DATA_KEY = "STIFF_NECK";
    public static final String RUNNY_NOSE_DATA_KEY = "RUNNY_NOSE";
    public static final String GENERALISED_RASH_DATA_KEY = "GENERALISED_RASH";
    public static final String COUGH_DATA_KEY = "COUGH";
    public static final String RED_EYES_DATA_KEY = "RED_EYES";
    public static final String MOUTH_ULCERS_DATA_KEY = "MOUTH_ULCERS";
    public static final String DEEP_MOUTH_ULCERS_DATA_KEY = "DEEP_MOUTH_ULCERS";
    public static final String EXTENSIVE_MOUTH_ULCERS_DATA_KEY = "EXTENSIVE_MOUTH_ULCERS";
    public static final String PUS_DRAINING_DATA_KEY = "PUS_DRAINING";
    public static final String CORNEA_CLOUDING_DATA_KEY = "CORNEA_CLOUDING";
    public static final String BULGING_FONTANEL_DATA_KEY = "BULGING_FONTANEL"; 
    
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
    	String reviewItemSymptomId = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.fever_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));

    	// fever
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_fever);
    	reviewItemValue = getPageData().getString(FEVER_DATA_KEY + ToggleButtonGroupTableLayout.TOGGLE_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_fever_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// malaria risk
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_malaria_risk);
    	reviewItemValue = getPageData().getString(MALARIA_RISK_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_malaria_risk_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// duration
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_duration);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_duration_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(DURATION_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	
    	// 'fever present every day'
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_present_every_day);
    	reviewItemValue = getPageData().getString(FEVER_PRESENT_EVERY_DAY_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_pus_draining_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));    	

    	// measles
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_measles);
    	reviewItemValue = getPageData().getString(MEASLES_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_measles_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));      

    	// stiff neck
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_stiff_neck);
    	reviewItemValue = getPageData().getString(STIFF_NECK_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_stiff_neck_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// runny nose
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_runny_nose);
    	reviewItemValue = getPageData().getString(RUNNY_NOSE_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_runny_nose_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// generalised rash
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_generalised_rash);
    	reviewItemValue = getPageData().getString(GENERALISED_RASH_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_generalised_rash_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// cough
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_cough);
    	reviewItemValue = getPageData().getString(COUGH_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_cough_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// red eyes
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_red_eyes);
    	reviewItemValue = getPageData().getString(RED_EYES_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_red_eyes_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// mouth ulcers
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_mouth_ulcers);
    	reviewItemValue = getPageData().getString(MOUTH_ULCERS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_mouth_ulcers_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// deep mouth ulcers
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_deep_mouth_ulcers);
    	reviewItemValue = getPageData().getString(DEEP_MOUTH_ULCERS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_deep_mouth_ulcers_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// extensive mouth ulcers
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_extensive_mouth_ulcers);
    	reviewItemValue = getPageData().getString(EXTENSIVE_MOUTH_ULCERS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_extensive_mouth_ulcers_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// pus draining from the eye
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_pus_draining);
    	reviewItemValue = getPageData().getString(PUS_DRAINING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_pus_draining_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// clouding of the cornea
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_cornea_clouding);
    	reviewItemValue = getPageData().getString(CORNEA_CLOUDING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_cornea_clouding_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// bulging fontanel
    	reviewItemLabel = resources.getString(R.string.fever_assessment_review_bulging_fontanel);
    	reviewItemValue = getPageData().getString(BULGING_FONTANEL_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.fever_assessment_bulging_fontanel_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    }

	/**
	 * Getter Method: getFeverAssessmentFragment()
	 */	
	public FeverAssessmentFragment getFeverAssessmentFragment() {
		return feverAssessmentFragment;
	}

	/**
	 * Setter Method: setFeverAssessmentFragment()
	 */	
	public void setFeverAssessmentFragment(FeverAssessmentFragment feverAssessmentFragment) {
		this.feverAssessmentFragment = feverAssessmentFragment;
	}
}
