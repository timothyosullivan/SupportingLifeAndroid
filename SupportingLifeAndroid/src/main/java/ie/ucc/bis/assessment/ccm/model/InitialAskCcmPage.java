package ie.ucc.bis.assessment.ccm.model;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.ccm.model.review.CoughDurationCcmReviewItem;
import ie.ucc.bis.assessment.ccm.model.review.DiarrhoeaDurationCcmReviewItem;
import ie.ucc.bis.assessment.ccm.model.review.DiarrhoeaZincDosageCcmReviewItem;
import ie.ucc.bis.assessment.ccm.model.review.FeverDurationCcmReviewItem;
import ie.ucc.bis.assessment.ccm.model.review.FeverLaDosageCcmReviewItem;
import ie.ucc.bis.assessment.ccm.model.review.FeverParacetamolDosageCcmReviewItem;
import ie.ucc.bis.assessment.ccm.ui.InitialAskCcmFragment;
import ie.ucc.bis.assessment.model.AbstractModel;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.ModelCallbacks;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.ui.utilities.ReviewItemUtilities;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: CCM Ask Assessment
 * 
 * Stage in CCM bread-crumb UI Wizard: 2
 * 
 * Responsible for displaying form for CCM Initial 'Ask' assessment patient details
 * 
 * @author timothyosullivan
 */
public class InitialAskCcmPage extends AbstractPage {
	public static final String PROBLEMS_DATA_KEY = "PROBLEMS";
    public static final String COUGH_DATA_KEY = "COUGH";
    public static final String COUGH_DURATION_DATA_KEY = "COUGH_DURATION";
    public static final String DIARRHOEA_DATA_KEY = "DIARRHOEA";
    public static final String DIARRHOEA_DURATION_DATA_KEY = "DIARRHOEA_DURATION";
    public static final String BLOOD_IN_STOOL_DATA_KEY = "BLOOD_IN_STOOL";
    public static final String FEVER_DATA_KEY = "FEVER";
    public static final String FEVER_DURATION_DATA_KEY = "FEVER_DURATION";
    public static final String CONVULSIONS_DATA_KEY = "CONVULSIONS";
    public static final String DRINK_OR_FEED_DIFFICULTY_DATA_KEY = "DRINK_OR_FEED_DIFFICULTY";
    public static final String UNABLE_TO_DRINK_OR_FEED_DATA_KEY = "UNABLE_TO_DRINK_OR_FEED";   
    
    private InitialAskCcmFragment initialAskCcmFragment;

    public InitialAskCcmPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        setInitialAskCcmFragment(InitialAskCcmFragment.create(getKey()));
        return getInitialAskCcmFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 'ask assessment' page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	Resources resources = ((AbstractModel) getModelCallbacks()).getApplicationContext().getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	String reviewItemSymptomId = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));	
      	
        // child's problems
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_problems);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_problems_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(PROBLEMS_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	    	    	    	
    	// cough
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_cough);
    	reviewItemValue = getPageData().getString(COUGH_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_cough_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// cough duration
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_cough_duration);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_cough_duration_twenty_one_days_symptom_id);
    	reviewItems.add(new CoughDurationCcmReviewItem(reviewItemLabel, getPageData().getString(COUGH_DURATION_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	
    	// diarrhoea
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_diarrhoea);
    	reviewItemValue = getPageData().getString(DIARRHOEA_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_diarrhoea_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// diarrhoea duration
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_diarrhoea_duration);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_diarrhoea_duration_fourteen_days_symptom_id);
    	reviewItems.add(new DiarrhoeaDurationCcmReviewItem(reviewItemLabel, getPageData().getString(DIARRHOEA_DURATION_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	
    	// diarrhoea zinc dosage
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_diarrhoea_zinc_dosage_symptom_id);
    	// note: In assessing the dosage for 'diarrhoea zinc dosage' assessment,
    	//       the date of birth child needs to be captured to facilitate the decision logic.
    	String birthDateSymptomId = resources.getString(R.string.ccm_general_patient_details_date_of_birth_symptom_id);
    	ReviewItem birthDateReviewItem = ReviewItemUtilities.findReviewItemBySymptomId(birthDateSymptomId, reviewItems);
    	reviewItems.add(new DiarrhoeaZincDosageCcmReviewItem(null, null, 
    			reviewItemSymptomId, getKey(), -1, Arrays.asList(birthDateReviewItem)));
	
    	// blood in stool
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_blood_in_stool);
    	reviewItemValue = getPageData().getString(BLOOD_IN_STOOL_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_blood_in_stool_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// fever
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_fever);
    	reviewItemValue = getPageData().getString(FEVER_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_fever_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// fever duration
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_fever_duration);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_fever_duration_seven_days_symptom_id);
    	reviewItems.add(new FeverDurationCcmReviewItem(reviewItemLabel, getPageData().getString(FEVER_DURATION_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	
    	// fever LA dosage
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_fever_la_dosage_age_symptom_id);
    	// note: In assessing the LA dosage for fever assessment,
    	//       the date of birth child needs to be captured to facilitate the decision logic.
    	reviewItems.add(new FeverLaDosageCcmReviewItem(null, null, 
    			reviewItemSymptomId, getKey(), -1, Arrays.asList(birthDateReviewItem)));
    	
    	// fever paracetamol dosage
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_fever_paracetamol_dosage_age_symptom_id);
    	// note: In assessing the paracetamol dosage for fever assessment,
    	//       the date of birth child needs to be captured to facilitate the decision logic.
    	reviewItems.add(new FeverParacetamolDosageCcmReviewItem(null, null, 
    			reviewItemSymptomId, getKey(), -1, Arrays.asList(birthDateReviewItem)));
    	
    	// convulsions
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_convulsions);
    	reviewItemValue = getPageData().getString(CONVULSIONS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_convulsions_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// difficulty drinking or feeding
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_drink_or_feed_difficulty);
    	reviewItemValue = getPageData().getString(DRINK_OR_FEED_DIFFICULTY_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_drink_or_feed_difficulty_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// unable to drink of feed
    	reviewItemLabel = resources.getString(R.string.ccm_ask_initial_assessment_review_unable_to_drink_or_feed);
    	reviewItemValue = getPageData().getString(UNABLE_TO_DRINK_OR_FEED_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_unable_to_drink_or_feed_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
     //   return !TextUtils.isEmpty(getPageData().getString(FIRST_NAME_DATA_KEY));
    	return true;
    }

	/**
	 * Getter Method: getInitialAskCcmFragment()
	 */    
	public InitialAskCcmFragment getInitialAskCcmFragment() {
		return initialAskCcmFragment;
	}

	/**
	 * Setter Method: setInitialAskCcmFragment()
	 */		
	public void setInitialAskCcmFragment(InitialAskCcmFragment initialAskCcmFragment) {
		this.initialAskCcmFragment = initialAskCcmFragment;
	}
}
