package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.ui.GeneralPatientDetailsFragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.RadioButton;

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
    	// first name
    	String firstNameLabel = getGeneralPatientDetailsFragment().getResources().getString(R.string.general_patient_details_review_first_name);
    	reviewItems.add(new ReviewItem(firstNameLabel, getPageData().getString(FIRST_NAME_DATA_KEY), getKey(), -1));
    	
    	// surname
    	String surnameLabel = getGeneralPatientDetailsFragment().getResources().getString(R.string.general_patient_details_review_surname);
    	reviewItems.add(new ReviewItem(surnameLabel, getPageData().getString(SURNAME_DATA_KEY), getKey(), -1));
    	
    	// gender
    	String genderLabel = getGeneralPatientDetailsFragment().getResources().getString(R.string.general_patient_details_review_gender);
    	String genderType = null;
    	if (getPageData().containsKey(GENDER_DATA_KEY)) {
			RadioButton radioButton = (RadioButton) getGeneralPatientDetailsFragment().getView().findViewById(getPageData().getInt(GENDER_DATA_KEY));
	    	genderType = radioButton.getText().toString();
    	}
    	reviewItems.add(new ReviewItem(genderLabel, genderType, getKey(), -1));
    	
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
