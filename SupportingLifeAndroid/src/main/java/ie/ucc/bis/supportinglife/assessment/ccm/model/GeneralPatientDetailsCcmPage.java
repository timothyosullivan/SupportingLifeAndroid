package ie.ucc.bis.supportinglife.assessment.ccm.model;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.ccm.ui.GeneralPatientDetailsCcmFragment;
import ie.ucc.bis.supportinglife.assessment.model.AbstractModel;
import ie.ucc.bis.supportinglife.assessment.model.AbstractPage;
import ie.ucc.bis.supportinglife.assessment.model.ModelCallbacks;
import ie.ucc.bis.supportinglife.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;

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
	public static final String HEALTH_SURVEILLANCE_ASSISTANT_DATA_KEY = "HEALTH_SURVEILLANCE_ASSISTANT";
	public static final String NATIONAL_ID_DATA_KEY = "NATIONAL_ID";
	public static final String NATIONAL_HEALTH_ID_DATA_KEY = "NATIONAL_HEALTH_ID";
    public static final String FIRST_NAME_DATA_KEY = "FIRST_NAME";
    public static final String SURNAME_DATA_KEY = "SURNAME";
    public static final String DATE_OF_BIRTH_DATA_KEY = "DATE_OF_BIRTH";
    public static final String GENDER_DATA_KEY = "GENDER";
	public static final String CAREGIVER_DATA_KEY = "CAREGIVER_DATA_KEY";
	public static final String PHYSICAL_ADDRESS_DATA_KEY = "PHYSICAL_ADDRESS";
	public static final String VILLAGE_DATA_KEY = "VILLAGE";
	public static final String RELATIONSHIP_DATA_KEY = "RELATIONSHIP_DATA_KEY";
	public static final String RELATIONSHIP_SPECIFIED_DATA_KEY = "RELATIONSHIP_SPECIFIED_DATA_KEY";

    
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
    	String reviewItemIdentifier = null;
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	String reviewItemSymptomId = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));	
  
    	// today's date
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_visit_date_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_today_date);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(TODAY_DATE_DATA_KEY), getKey(), -1, reviewItemIdentifier));
    	
        // Health Surveillance Assistant (HSA)
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_hsa_user_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_hsa_identifier);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(HEALTH_SURVEILLANCE_ASSISTANT_DATA_KEY), getKey(), -1, reviewItemIdentifier));

    	// national id
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_national_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_national_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(NATIONAL_ID_DATA_KEY), getKey(), -1, reviewItemIdentifier));
  
    	// national health id
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_national_health_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_national_health_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(NATIONAL_HEALTH_ID_DATA_KEY), getKey(), -1, reviewItemIdentifier));
    	
    	// child's first name
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_child_first_name_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_first_name);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(FIRST_NAME_DATA_KEY), getKey(), -1, reviewItemIdentifier));
    	
    	// child's surname
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_child_surname_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_surname);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(SURNAME_DATA_KEY), getKey(), -1, reviewItemIdentifier));
    	
    	// date of birth
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_date_of_birth_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_date_of_birth);
    	reviewItemSymptomId = resources.getString(R.string.ccm_general_patient_details_date_of_birth_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(DATE_OF_BIRTH_DATA_KEY), reviewItemSymptomId, getKey(), -1, reviewItemIdentifier));
    	    	    	
    	// gender
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_gender_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_gender);
    	reviewItemSymptomId = resources.getString(R.string.ccm_general_patient_details_gender_symptom_id);
    	reviewItemValue = getPageData().getString(GENDER_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1, reviewItemIdentifier));
    	
    	// caregiver
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_caregiver_name_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_caregiver);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(CAREGIVER_DATA_KEY), getKey(), -1, reviewItemIdentifier));
    	
    	// relationship / specify relationship
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_relationship_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_relationship);
    	reviewItemValue = getPageData().getString(RELATIONSHIP_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	// need to determine if 'Other' was selected as the relationship type
    	if ((reviewItemValue != null) && 
    			(reviewItemValue.equalsIgnoreCase(resources.getString(R.string.ccm_general_patient_details_radio_relationship_other)))) {
    		// in this case we relationship type to the text entered on the 'Specify Relationship' textfield
    		reviewItemValue = getPageData().getString(RELATIONSHIP_SPECIFIED_DATA_KEY);
    	}
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, getKey(), -1, reviewItemIdentifier)); 	
    	
        // physical address
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_physical_address_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_physical_address);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(PHYSICAL_ADDRESS_DATA_KEY), getKey(), -1, reviewItemIdentifier));
        
        // village/TA
    	reviewItemIdentifier = resources.getString(R.string.ccm_general_patient_details_village_ta_id);
    	reviewItemLabel = resources.getString(R.string.ccm_general_patient_details_review_village);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getPageData().getString(VILLAGE_DATA_KEY), getKey(), -1, reviewItemIdentifier));
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
