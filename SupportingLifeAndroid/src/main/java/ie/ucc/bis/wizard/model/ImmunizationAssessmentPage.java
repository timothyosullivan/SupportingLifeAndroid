package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.model.listener.RadioGroupListener;
import ie.ucc.bis.wizard.ui.ImmunizationAssessmentFragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: Immunization Status Assessment
 * 
 * Stage in bread-crumb UI Wizard: 8
 * 
 * Responsible for displaying form to record the immunization
 * status assessment of a patient
 */
public class ImmunizationAssessmentPage extends AbstractPage {
    public static final String BCG_VACCINE_DATA_KEY = "BCG_VACCINE";
    public static final String MEASLES_VACCINE_DATA_KEY = "MEASLES_VACCINE";

    private ImmunizationAssessmentFragment immunizationAssessmentFragment;

    public ImmunizationAssessmentPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
    	setImmunizationAssessmentFragment(ImmunizationAssessmentFragment.create(getKey()));
        return getImmunizationAssessmentFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 
	 * 'immunization status assessment' page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	Context appContext = ((AbstractWizardModel) getModelCallbacks()).applicationContext;
    	Resources resources = appContext.getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	
    	// review header
    	reviewItemLabel = resources.getString(R.string.immunization_assessment_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));
    	
    	// BCG vaccine
    	reviewItemLabel = resources.getString(R.string.immunization_assessment_review_vaccine_bcg);
    	reviewItemValue = getPageData().getString(BCG_VACCINE_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, null, getKey(), -1));
    	
    	// Measles vaccine
    	reviewItemLabel = resources.getString(R.string.immunization_assessment_review_vaccine_measles);
    	reviewItemValue = getPageData().getString(MEASLES_VACCINE_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, null, getKey(), -1));	
    }

	/**
	 * Getter Method: getImmunizationAssessmentFragment()
	 * 
	 */	
	public ImmunizationAssessmentFragment getImmunizationAssessmentFragment() {
		return immunizationAssessmentFragment;
	}

	/**
	 * Setter Method: setImmunizationAssessmentFragment()
	 * 
	 */
	public void setImmunizationAssessmentFragment(ImmunizationAssessmentFragment immunizationAssessmentFragment) {
		this.immunizationAssessmentFragment = immunizationAssessmentFragment;
	}
}
