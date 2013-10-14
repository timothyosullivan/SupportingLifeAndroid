package ie.ucc.bis.ccm.model;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.model.AbstractModel;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.ModelCallbacks;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.ccm.ui.GeneralPatientDetailsCcmFragment;

import java.util.ArrayList;

import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: CCM General Patient Details
 * 
 * Stage in CCM bread-crumb UI Wizard: 1
 * 
 * Responsible for displaying registration form for CCM general patient details
 * 
 * @author timothyosullivan
 */
public class GeneralPatientDetailsCcmPage extends AbstractPage {
    public static final String TODAY_DATE_DATA_KEY = "TODAY_DATE";
    public static final String FIRST_NAME_DATA_KEY = "FIRST_NAME";
    public static final String SURNAME_DATA_KEY = "SURNAME";
    public static final String DATE_OF_BIRTH_DATA_KEY = "DATE_OF_BIRTH";
    public static final String GENDER_DATA_KEY = "GENDER";
    
    private GeneralPatientDetailsCcmFragment generalPatientDetailsCcmFragment;

    public GeneralPatientDetailsCcmPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        setGeneralPatientDetailsCcmFragment(GeneralPatientDetailsCcmFragment.create(getKey()));
        return getGeneralPatientDetailsCcmFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 'general patient details' page.
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
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));	
  
    	// today's date
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_today_date);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(TODAY_DATE_DATA_KEY), getKey(), -1));
    	
    	// first name
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_first_name);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(FIRST_NAME_DATA_KEY), getKey(), -1));
    	
    	// surname
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_surname);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(SURNAME_DATA_KEY), getKey(), -1));
    	
    	// date of birth
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_date_of_birth);
    	reviewItemSymptomId = resources.getString(R.string.ccm_general_patient_details_date_of_birth_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(DATE_OF_BIRTH_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	    	    	
    	// gender
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_gender);
    	reviewItemSymptomId = resources.getString(R.string.ccm_general_patient_details_gender_symptom_id);
    	reviewItemValue = getPageData().getString(GENDER_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));	
    }

    @Override
    public boolean isCompleted() {
     //   return !TextUtils.isEmpty(getPageData().getString(FIRST_NAME_DATA_KEY));
    	return true;
    }

	/**
	 * Getter Method: getGeneralPatientDetailsCcmFragment()
	 * 
	 */    
	public GeneralPatientDetailsCcmFragment getGeneralPatientDetailsCcmFragment() {
		return generalPatientDetailsCcmFragment;
	}

	/**
	 * Setter Method: setGeneralPatientDetailsCcmFragment()
	 * 
	 */		
	public void setGeneralPatientDetailsCcmFragment(GeneralPatientDetailsCcmFragment generalPatientDetailsCcmFragment) {
		this.generalPatientDetailsCcmFragment = generalPatientDetailsCcmFragment;
	}
}
