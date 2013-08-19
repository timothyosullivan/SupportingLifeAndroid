package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.wizard.model.review.ReviewItem;

import java.util.List;

import android.support.v4.app.ListFragment;

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
