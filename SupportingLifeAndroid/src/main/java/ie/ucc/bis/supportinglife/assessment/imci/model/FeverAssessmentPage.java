package ie.ucc.bis.supportinglife.assessment.imci.model;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.imci.model.review.FeverDurationReviewItem;
import ie.ucc.bis.supportinglife.assessment.imci.model.review.FeverReviewItem;
import ie.ucc.bis.supportinglife.assessment.imci.model.review.MalariaReviewItem;
import ie.ucc.bis.supportinglife.assessment.imci.ui.FeverAssessmentFragment;
import ie.ucc.bis.supportinglife.assessment.model.AbstractModel;
import ie.ucc.bis.supportinglife.assessment.model.AbstractPage;
import ie.ucc.bis.supportinglife.assessment.model.ModelCallbacks;
import ie.ucc.bis.supportinglife.assessment.model.listener.RadioGroupCoordinatorListener;
import ie.ucc.bis.supportinglife.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;

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
 * 
 * @author timothyosullivan
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
    	Context appContext = ((AbstractModel) getModelCallbacks()).getApplicationContext();
    	Resources resources = appContext.getResources();
    	String reviewIdentifier = null;
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	String reviewItemSymptomId = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));

    	// fever
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_fever_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_fever);
    	reviewItemValue = getPageData().getString(FEVER_DATA_KEY + RadioGroupCoordinatorListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_fever_symptom_id);
    	reviewItems.add(new FeverReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// malaria risk
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_malaria_risk_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_malaria_risk);
    	reviewItemValue = getPageData().getString(MALARIA_RISK_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_malaria_risk_symptom_id);
    	reviewItems.add(new MalariaReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// fever duration
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_duration_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_duration);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_duration_symptom_id);
    	reviewItems.add(new FeverDurationReviewItem(reviewItemLabel, getPageData().getString(DURATION_DATA_KEY), reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// 'fever present every day'
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_fever_present_every_day_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_present_every_day);
    	reviewItemValue = getPageData().getString(FEVER_PRESENT_EVERY_DAY_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_present_every_day_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));    	

    	// measles
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_measles_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_measles);
    	reviewItemValue = getPageData().getString(MEASLES_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_measles_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));      

    	// stiff neck
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_stiff_neck_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_stiff_neck);
    	reviewItemValue = getPageData().getString(STIFF_NECK_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_stiff_neck_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));

    	// runny nose
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_runny_nose_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_runny_nose);
    	reviewItemValue = getPageData().getString(RUNNY_NOSE_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_runny_nose_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// generalised rash
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_generalised_rash_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_generalised_rash);
    	reviewItemValue = getPageData().getString(GENERALISED_RASH_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_generalised_rash_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// cough
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_cough_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_cough);
    	reviewItemValue = getPageData().getString(COUGH_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_cough_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// red eyes
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_red_eyes_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_red_eyes);
    	reviewItemValue = getPageData().getString(RED_EYES_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_red_eyes_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// mouth ulcers
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_mouth_ulcers_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_mouth_ulcers);
    	reviewItemValue = getPageData().getString(MOUTH_ULCERS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_mouth_ulcers_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// deep mouth ulcers
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_deep_mouth_ulcers_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_deep_mouth_ulcers);
    	reviewItemValue = getPageData().getString(DEEP_MOUTH_ULCERS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_deep_mouth_ulcers_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// extensive mouth ulcers
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_extensive_mouth_ulcers_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_extensive_mouth_ulcers);
    	reviewItemValue = getPageData().getString(EXTENSIVE_MOUTH_ULCERS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_extensive_mouth_ulcers_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// pus draining from the eye
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_pus_draining_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_pus_draining);
    	reviewItemValue = getPageData().getString(PUS_DRAINING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_pus_draining_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// clouding of the cornea
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_cornea_clouding_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_cornea_clouding);
    	reviewItemValue = getPageData().getString(CORNEA_CLOUDING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_cornea_clouding_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
    	
    	// bulging fontanel
    	reviewIdentifier = resources.getString(R.string.imci_fever_assessment_bulging_fontanel_id);
    	reviewItemLabel = resources.getString(R.string.imci_fever_assessment_review_bulging_fontanel);
    	reviewItemValue = getPageData().getString(BULGING_FONTANEL_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_fever_assessment_bulging_fontanel_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewIdentifier));
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
