package ie.ucc.bis.wizard.model;

import ie.ucc.bis.wizard.ui.CustomerInfoFragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

/**
 * A page asking for a name and an email.
 */
public class CustomerInfoPage extends AbstractPage {
    public static final String NAME_DATA_KEY = "name";
    public static final String EMAIL_DATA_KEY = "email";

    public CustomerInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return CustomerInfoFragment.create(getKey());
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the CustomerInfo page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */      
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	reviewItems.add(new ReviewItem("Your name", mData.getString(NAME_DATA_KEY), getKey(), -1));
    	reviewItems.add(new ReviewItem("Your email", mData.getString(EMAIL_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY));
    }
}
