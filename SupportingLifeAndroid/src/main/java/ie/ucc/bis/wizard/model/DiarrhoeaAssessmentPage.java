package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.model.listener.RadioGroupListener;
import ie.ucc.bis.wizard.model.review.DiarrhoeaDurationIndicatorReviewItem;
import ie.ucc.bis.wizard.model.review.FluidReviewItem;
import ie.ucc.bis.wizard.model.review.ReviewItem;
import ie.ucc.bis.wizard.model.review.SkinPinchReviewItem;
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
    public static final String DIARRHOEA_DURATION_DATA_KEY = "DIARRHOEA_DURATION";
    public static final String BLOOD_STOOLS_DATA_KEY = "BLOOD_STOOLS";
    public static final String SUNKEN_EYES_DATA_KEY = "SUNKEN_EYES";
    public static final String LETHARGIC_OR_UNCONSCIOUS_DATA_KEY = "LETHARGIC_OR_UNCONSCIOUS";
    public static final String RESTLESS_IRRITABLE_DATA_KEY = "RESTLESS_IRRITABLE"; 
    public static final String CHOLERA_IN_AREA_DATA_KEY = "CHOLERA_IN_AREA";
    public static final String CHILD_FLUID_DATA_KEY = "CHILD_FLUID";
    public static final String SKIN_PINCH_DATA_KEY = "SKIN_PINCH";
    
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
    	String reviewItemSymptomId = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));	

    	// diarrhoea
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_diarrhoea);
    	reviewItemValue = getPageData().getString(DIARRHOEA_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.diarrhoea_assessment_diarrhoea_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// for how long? (days) - diarrhoea duration
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_diarrhoea_duration);
    	reviewItemSymptomId = resources.getString(R.string.diarrhoea_assessment_diarrhoea_duration_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(DIARRHOEA_DURATION_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	
    	// diarrhoea duration indicator -> diarrhoea duration >= 14 days
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_diarrhoea_duration);
    	reviewItemSymptomId = resources.getString(R.string.diarrhoea_assessment_diarrhoea_duration_fourteen_days_symptom_id);
    	ReviewItem diarrhoeaDurationIndicatoReviewItem = new DiarrhoeaDurationIndicatorReviewItem(null, 
    			getPageData().getString(DIARRHOEA_DURATION_DATA_KEY), 
    			reviewItemSymptomId, getKey(), -1);
    	diarrhoeaDurationIndicatoReviewItem.setVisible(false);
    	reviewItems.add(diarrhoeaDurationIndicatoReviewItem);
    	
    	// blood in the stools
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_blood_stools);
    	reviewItemValue = getPageData().getString(BLOOD_STOOLS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.diarrhoea_assessment_blood_stools_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// sunken eyes
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_sunken_eyes);
    	reviewItemValue = getPageData().getString(SUNKEN_EYES_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.diarrhoea_assessment_sunken_eyes_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// lethargic or unconscious
    	// ** we only wish for a single instance of this symptom to be present when parsing
    	//    the review items i.e. from the 'general danger signs' page - so set the symptom
    	//    id to be null in this case so it won't be picked up
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_lethargic_or_unconscious);
    	reviewItemValue = getPageData().getString(LETHARGIC_OR_UNCONSCIOUS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, null, getKey(), -1));
    	
    	// restless / irritable
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_restless_irritable);
    	reviewItemValue = getPageData().getString(RESTLESS_IRRITABLE_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.diarrhoea_assessment_restless_irritable_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// cholera in area
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_cholera_in_area);
    	reviewItemValue = getPageData().getString(CHOLERA_IN_AREA_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.diarrhoea_assessment_cholera_in_area_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// offer the child fluid
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_child_fluid);
    	reviewItemValue = getPageData().getString(CHILD_FLUID_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.diarrhoea_assessment_child_fluid_symptom_id);
    	reviewItems.add(new FluidReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// skin pinch
    	reviewItemLabel = resources.getString(R.string.diarrhoea_assessment_review_skin_pinch);
    	reviewItemValue = getPageData().getString(SKIN_PINCH_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.diarrhoea_assessment_skin_pinch_symptom_id);
    	reviewItems.add(new SkinPinchReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
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
