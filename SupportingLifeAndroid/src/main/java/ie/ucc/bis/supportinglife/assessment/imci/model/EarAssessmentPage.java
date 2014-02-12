package ie.ucc.bis.supportinglife.assessment.imci.model;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.imci.model.review.EarDischargeDurationReviewItem;
import ie.ucc.bis.supportinglife.assessment.imci.ui.EarAssessmentFragment;
import ie.ucc.bis.supportinglife.assessment.model.AbstractModel;
import ie.ucc.bis.supportinglife.assessment.model.AbstractPage;
import ie.ucc.bis.supportinglife.assessment.model.ModelCallbacks;
import ie.ucc.bis.supportinglife.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;

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
 * 
 * @author timothyosullivan
 */
public class EarAssessmentPage extends AbstractPage {
    public static final String EAR_PROBLEM_DATA_KEY = "EAR_PROBLEM";
    public static final String EAR_PAIN_DATA_KEY = "EAR_PAIN";
    public static final String EAR_DISCHARGE_DATA_KEY = "EAR_DISCHARGE";
    public static final String EAR_DISCHARGE_DURATION_DATA_KEY = "EAR_DISCHARGE_DURATION";
    public static final String TENDER_SWELLING_DATA_KEY = "TENDER_SWELLING";
    
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
    	Context appContext = ((AbstractModel) getModelCallbacks()).getApplicationContext();
    	Resources resources = appContext.getResources();
    	String reviewItemIdentifier = null;
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	String reviewItemSymptomId = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.imci_ear_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));
    	
    	// ear problem
    	reviewItemIdentifier = resources.getString(R.string.imci_ear_assessment_ear_problem_id);
    	reviewItemLabel = resources.getString(R.string.imci_ear_assessment_review_ear_problem);
    	reviewItemValue = getPageData().getString(EAR_PROBLEM_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_ear_assessment_ear_problem_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewItemIdentifier));
    	
    	// ear pain
    	reviewItemIdentifier = resources.getString(R.string.imci_ear_assessment_ear_pain_id);
    	reviewItemLabel = resources.getString(R.string.imci_ear_assessment_review_ear_pain);
    	reviewItemValue = getPageData().getString(EAR_PAIN_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_ear_assessment_ear_pain_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewItemIdentifier));
    	
    	// ear discharge
    	reviewItemIdentifier = resources.getString(R.string.imci_ear_assessment_ear_discharge_id);
    	reviewItemLabel = resources.getString(R.string.imci_ear_assessment_review_ear_discharge);
    	reviewItemValue = getPageData().getString(EAR_DISCHARGE_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_ear_assessment_ear_discharge_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewItemIdentifier));
    	
    	// for how long? (days) - ear discharge duration
    	reviewItemIdentifier = resources.getString(R.string.imci_ear_assessment_ear_discharge_duration_id);
    	reviewItemLabel = resources.getString(R.string.imci_ear_assessment_review_ear_discharge_duration);
    	reviewItemSymptomId = resources.getString(R.string.imci_ear_assessment_ear_discharge_duration_symptom_id);
    	reviewItems.add(new EarDischargeDurationReviewItem(reviewItemLabel, getPageData().getString(EAR_DISCHARGE_DURATION_DATA_KEY), reviewItemSymptomId, getKey(), -1, reviewItemIdentifier));
    	
    	// tender swelling
    	reviewItemIdentifier = resources.getString(R.string.imci_ear_assessment_tender_swelling_id);
    	reviewItemLabel = resources.getString(R.string.imci_ear_assessment_review_tender_swelling);
    	reviewItemValue = getPageData().getString(TENDER_SWELLING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.imci_ear_assessment_tender_swelling_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewItemIdentifier));
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
