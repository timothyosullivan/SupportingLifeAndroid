package ie.ucc.bis.ccm.model;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.model.AbstractModel;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.ModelCallbacks;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.ccm.ui.AskCcmFragment;

import java.util.ArrayList;

import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: CCM Ask Assessment
 * 
 * Stage in CCM bread-crumb UI Wizard: 2
 * 
 * Responsible for displaying form for CCM 'Ask' assessment patient details
 * 
 * @author timothyosullivan
 */
public class AskCcmPage extends AbstractPage {
	public static final String PROBLEMS_DATA_KEY = "PROBLEMS";
    public static final String COUGH_DATA_KEY = "COUGH";
    public static final String COUGH_DURATION_DATA_KEY = "COUGH_DURATION";
    
    private AskCcmFragment askCcmFragment;

    public AskCcmPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        setAskCcmFragment(AskCcmFragment.create(getKey()));
        return getAskCcmFragment();
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
    	reviewItemLabel = resources.getString(R.string.ccm_ask_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));	
      	
        // child's problems
    	reviewItemLabel = resources.getString(R.string.ccm_ask_assessment_review_problems);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_assessment_problems_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(PROBLEMS_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	    	    	    	
    	// cough
    	reviewItemLabel = resources.getString(R.string.ccm_ask_assessment_review_cough);
    	reviewItemValue = getPageData().getString(COUGH_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_assessment_cough_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));

    	// cough duration
    	reviewItemLabel = resources.getString(R.string.ccm_ask_assessment_review_cough_duration);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_assessment_cough_duration_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(COUGH_DURATION_DATA_KEY), reviewItemSymptomId, getKey(), -1));    	
    	

    }

    @Override
    public boolean isCompleted() {
     //   return !TextUtils.isEmpty(getPageData().getString(FIRST_NAME_DATA_KEY));
    	return true;
    }

	/**
	 * Getter Method: getAskCcmFragment()
	 */    
	public AskCcmFragment getAskCcmFragment() {
		return askCcmFragment;
	}

	/**
	 * Setter Method: setAskCcmFragment()
	 */		
	public void setAskCcmFragment(AskCcmFragment askCcmFragment) {
		this.askCcmFragment = askCcmFragment;
	}
}
