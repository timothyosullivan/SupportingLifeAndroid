package ie.ucc.bis.supportinglife.assessment.imci.model;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.imci.model.review.AgeIndicatorReviewItem;
import ie.ucc.bis.supportinglife.assessment.imci.ui.GeneralPatientDetailsFragment;
import ie.ucc.bis.supportinglife.assessment.model.AbstractModel;
import ie.ucc.bis.supportinglife.assessment.model.AbstractPage;
import ie.ucc.bis.supportinglife.assessment.model.ModelCallbacks;
import ie.ucc.bis.supportinglife.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;

import java.util.ArrayList;

import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: General Patient Details
 * 
 * Stage in bread-crumb UI Wizard: 1
 * 
 * Responsible for displaying registration form for general patient details
 * 
 * @author timothyosullivan
 */
public class GeneralPatientDetailsPage extends AbstractPage {
    public static final String FIRST_NAME_DATA_KEY = "FIRST_NAME";
    public static final String SURNAME_DATA_KEY = "SURNAME";
    public static final String DATE_OF_BIRTH_DATA_KEY = "DATE_OF_BIRTH";
    public static final String WEIGHT_DATA_KEY = "WEIGHT";
    public static final String TEMPERATURE_DATA_KEY = "TEMPERATURE";
    public static final String GENDER_DATA_KEY = "GENDER";
    public static final String PROBLEMS_DATA_KEY = "PROBLEMS";
    
    private GeneralPatientDetailsFragment generalPatientDetailsFragment;

    public GeneralPatientDetailsPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        setGeneralPatientDetailsFragment(GeneralPatientDetailsFragment.create(getKey()));
        return getGeneralPatientDetailsFragment();
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
    	reviewItemLabel = resources.getString(R.string.imci_general_patient_details_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));	
    	
    	// first name
    	reviewItemLabel = resources.getString(R.string.imci_general_patient_details_review_first_name);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(FIRST_NAME_DATA_KEY), getKey(), -1));
    	
    	// surname
    	reviewItemLabel = resources.getString(R.string.imci_general_patient_details_review_surname);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(SURNAME_DATA_KEY), getKey(), -1));
    	
    	// date of birth
    	reviewItemLabel = resources.getString(R.string.imci_general_patient_details_review_date_of_birth);
    	reviewItemSymptomId = resources.getString(R.string.imci_general_patient_details_date_of_birth_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(DATE_OF_BIRTH_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	
    	// review item to indicate whether patient is two years or older
    	reviewItemSymptomId = resources.getString(R.string.imci_general_patient_details_patient_two_years_or_older_symptom_id);
    	ReviewItem ageIndicatorReviewItem = new AgeIndicatorReviewItem(null, getPageData().getString(DATE_OF_BIRTH_DATA_KEY), 
    			reviewItemSymptomId, getKey(), -1);
    	ageIndicatorReviewItem.setVisible(false);
    	reviewItems.add(ageIndicatorReviewItem);
    	
    	// weight
    	reviewItemLabel = resources.getString(R.string.imci_general_patient_details_review_weight);
    	reviewItemSymptomId = resources.getString(R.string.imci_general_patient_details_weight_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(WEIGHT_DATA_KEY), reviewItemSymptomId, getKey(), -1));	
    	
    	// temperature
    	reviewItemLabel = resources.getString(R.string.imci_general_patient_details_review_temperature);
    	reviewItemSymptomId = resources.getString(R.string.imci_general_patient_details_temperature_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(TEMPERATURE_DATA_KEY), reviewItemSymptomId, getKey(), -1));
    	
    	// gender
    	reviewItemLabel = resources.getString(R.string.imci_general_patient_details_review_gender);
    	reviewItemSymptomId = resources.getString(R.string.imci_general_patient_details_gender_symptom_id);
    	reviewItemValue = getPageData().getString(GENDER_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// problems
    	reviewItemLabel = resources.getString(R.string.imci_general_patient_details_review_problems);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(PROBLEMS_DATA_KEY), getKey(), -1));	    
    	
    }

    @Override
    public boolean isCompleted() {
     //   return !TextUtils.isEmpty(getPageData().getString(FIRST_NAME_DATA_KEY));
    	return true;
    }

	/**
	 * Getter Method: getGeneralPatientDetailsFragment()
	 * 
	 */    
	public GeneralPatientDetailsFragment getGeneralPatientDetailsFragment() {
		return generalPatientDetailsFragment;
	}

	/**
	 * Setter Method: setGeneralPatientDetailsFragment()
	 * 
	 */		
	public void setGeneralPatientDetailsFragment(GeneralPatientDetailsFragment generalPatientDetailsFragment) {
		this.generalPatientDetailsFragment = generalPatientDetailsFragment;
	}
}
