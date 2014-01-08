package ie.ucc.bis.supportinglife.assessment.imci.ui;

import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;

import java.util.List;

import android.support.v4.app.ListFragment;

/**
 * 
 * @author timothyosullivan
 */

public class ReviewListFragment extends ListFragment {
    private List<ReviewItem> currentReviewItems;
    
	/**
	 * Getter Method: getCurrentReviewItems()
	 */	
	public List<ReviewItem> getCurrentReviewItems() {
		return currentReviewItems;
	}

	/**
	 * Setter Method: setCurrentReviewItems()
	 */
	public void setCurrentReviewItems(List<ReviewItem> currentReviewItems) {
		this.currentReviewItems = currentReviewItems;
	}
}
