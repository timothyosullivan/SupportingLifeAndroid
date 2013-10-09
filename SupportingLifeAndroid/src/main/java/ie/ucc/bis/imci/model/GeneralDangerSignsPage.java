package ie.ucc.bis.imci.model;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.AbstractModel;
import ie.ucc.bis.assessment.model.ModelCallbacks;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.imci.ui.GeneralDangerSignsFragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

/**
 * Page Title: General Danger Signs
 * 
 * Stage in bread-crumb UI Wizard: 2
 * 
 * Responsible for displaying form to record general dangers
 * of a patient
 */
public class GeneralDangerSignsPage extends AbstractPage {
    public static final String DRINK_BREASTFEED_DATA_KEY = "DRINK_BREASTFEED";
    public static final String VOMITS_EVERYTHING_DATA_KEY = "VOMITS_EVERYTHING";
    public static final String HISTORY_OF_CONVULSIONS_DATA_KEY = "HISTORY_OF_CONVULSIONS";
    public static final String LETHARGIC_OR_UNCONSCIOUS_DATA_KEY = "LETHARGIC_OR_UNCONSCIOUS";
    public static final String CONVULSING_NOW_DATA_KEY = "CONVULSING_NOW";
    
    private GeneralDangerSignsFragment generalDangerSignsFragment;

    public GeneralDangerSignsPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
    	setGeneralDangerSignsFragment(GeneralDangerSignsFragment.create(getKey()));
        return getGeneralDangerSignsFragment();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the 'general danger signs' page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	Context appContext = ((AbstractModel) getModelCallbacks()).getApplicationContext();
    	Resources resources = appContext.getResources();
    	String reviewItemLabel = null;
    	String reviewItemValue = null;
    	String reviewItemSymptomId = null;

    	// review header
    	reviewItemLabel = resources.getString(R.string.general_danger_signs_title);
    	reviewItems.add(new ReviewItem(reviewItemLabel, getKey()));
    	
    	// not able to drink or breastfeed
    	reviewItemLabel = resources.getString(R.string.general_danger_signs_review_drink_breastfeed);
    	reviewItemValue = getPageData().getString(DRINK_BREASTFEED_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.general_danger_signs_drink_breastfeed_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));    	
    	
    	// vomits everythings
    	reviewItemLabel = resources.getString(R.string.general_danger_signs_review_vomits);
    	reviewItemValue = getPageData().getString(VOMITS_EVERYTHING_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
    	reviewItemSymptomId = resources.getString(R.string.general_danger_signs_vomits_everything_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// history of convulsions
    	reviewItemLabel = resources.getString(R.string.general_danger_signs_review_convulsions);
    	reviewItemValue = getPageData().getString(HISTORY_OF_CONVULSIONS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
     	reviewItemSymptomId = resources.getString(R.string.general_danger_signs_convulsions_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// lethargic or unconscious
    	reviewItemLabel = resources.getString(R.string.general_danger_signs_review_lethargic_or_unconscious);
    	reviewItemValue = getPageData().getString(LETHARGIC_OR_UNCONSCIOUS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
     	reviewItemSymptomId = resources.getString(R.string.general_danger_signs_lethargic_or_unconscious_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    	
    	// convulsing now
    	reviewItemLabel = resources.getString(R.string.general_danger_signs_review_convulsing_now);
    	reviewItemValue = getPageData().getString(CONVULSING_NOW_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY);
     	reviewItemSymptomId = resources.getString(R.string.general_danger_signs_convulsing_now_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, getKey(), -1));
    }

	/**
	 * Getter Method: getGeneralDangerSignsFragment()
	 * 
	 */    
	public GeneralDangerSignsFragment getGeneralDangerSignsFragment() {
		return generalDangerSignsFragment;
	}

	/**
	 * Setter Method: setGeneralDangerSignsFragment()
	 * 
	 */		
	public void setGeneralDangerSignsFragment(GeneralDangerSignsFragment generalDangerSignsFragment) {
		this.generalDangerSignsFragment = generalDangerSignsFragment;
	}
}
