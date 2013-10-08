package ie.ucc.bis.imci.ui;

import ie.ucc.bis.imci.model.review.ReviewItem;

import java.util.List;

import android.support.v4.app.ListFragment;

public class ReviewListFragment extends ListFragment {
    private List<ReviewItem> currentReviewItems;
    private List<ReviewItem> filteredReviewItems;
    
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
	
	/**
	 * Getter Method: getFilteredReviewItems()
	 */
	public List<ReviewItem> getFilteredReviewItems() {
		return filteredReviewItems;
	}

	/**
	 * Setter Method: setFilteredReviewItems()
	 */
	public void setFilteredReviewItems(List<ReviewItem> filteredReviewItems) {
		this.filteredReviewItems = filteredReviewItems;
	}
}
