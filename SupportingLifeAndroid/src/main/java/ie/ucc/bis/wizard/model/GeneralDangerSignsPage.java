package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.ui.GeneralDangerSignsFragment;

import java.util.ArrayList;

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
    	Resources resources = ((AbstractWizardModel) getModelCallbacks()).applicationContext.getResources();

    	// not able to drink or breastfeed
    	String drinkBreastfeedLabel = resources.getString(R.string.general_danger_signs_review_drink_breastfeed);
    	reviewItems.add(new ReviewItem(drinkBreastfeedLabel, getPageData().getString(DRINK_BREASTFEED_DATA_KEY), getKey(), -1));    	
    	
    	// vomits everythings
    	String vomitsLabel = resources.getString(R.string.general_danger_signs_review_vomits);
    	reviewItems.add(new ReviewItem(vomitsLabel, getPageData().getString(VOMITS_EVERYTHING_DATA_KEY), getKey(), -1));
    	
    	// history of convulsions
    	String convulsionsLabel = resources.getString(R.string.general_danger_signs_review_convulsions);
    	reviewItems.add(new ReviewItem(convulsionsLabel, getPageData().getString(HISTORY_OF_CONVULSIONS_DATA_KEY), getKey(), -1));
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
