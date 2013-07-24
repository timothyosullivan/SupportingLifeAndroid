package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.ui.GeneralPatientDetailsFragment;

import java.util.ArrayList;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

/**
 * Page Title: General Patient Details
 * 
 * Stage in bread-crumb UI Wizard: 1
 * 
 * Responsible for displaying registration form for general patient details
 */
public class GeneralPatientDetailsPage extends AbstractPage {
    public static final String FIRST_NAME_DATA_KEY = "FIRST_NAME";
    public static final String SURNAME_DATA_KEY = "SURNAME";
    public static final String GENDER_DATA_KEY = "GENDER";
    
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
    	Resources resources = ((AbstractWizardModel) getModelCallbacks()).applicationContext.getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	
    	// first name
    	reviewItemLabel = resources.getString(R.string.general_patient_details_review_first_name);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(FIRST_NAME_DATA_KEY), getKey(), -1));
    	
    	// surname
    	reviewItemLabel = resources.getString(R.string.general_patient_details_review_surname);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(SURNAME_DATA_KEY), getKey(), -1));
    	
    	// gender
    	reviewItemLabel = resources.getString(R.string.general_patient_details_review_gender);
    	reviewItemValue = getPageData().getString(GENDER_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(getPageData().getString(FIRST_NAME_DATA_KEY));
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
