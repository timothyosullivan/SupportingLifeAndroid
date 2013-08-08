package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.ui.ReviewListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReviewAssessmentAdapter extends BaseAdapter {
	private static final int HEADER_ITEM_TYPE = 0;
	private static final int SIMPLE_ITEM_TYPE = 1;
	private static final int MAX_TYPE_COUNT = 2;
	private static final String DEFAULT_ITEM_VALUE = "--------";
	
	private ReviewListFragment reviewListFragment;
	
    public ReviewAssessmentAdapter(ReviewListFragment reviewListFragment) {
		super();
		setReviewListFragment(reviewListFragment);
	}

	@Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getItemViewType(int position) {
    	// need to ascertain if we are dealing with a header
    	// or just a simple list item
    	ReviewItem reviewItem = getReviewListFragment().getCurrentReviewItems().get(position);
    	if (reviewItem.isHeaderItem()) {
    		return HEADER_ITEM_TYPE;
    	}
    	else {
    		return SIMPLE_ITEM_TYPE;
    	}
    }

    @Override
    public int getViewTypeCount() {
        return MAX_TYPE_COUNT;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    public Object getItem(int position) {
        return getReviewListFragment().getCurrentReviewItems().get(position);
    }

    public long getItemId(int position) {
        return getReviewListFragment().getCurrentReviewItems().get(position).hashCode();
    }

    public View getView(int position, View view, ViewGroup container) {
        LayoutInflater inflater = LayoutInflater.from(getReviewListFragment().getActivity());
        int itemType = getItemViewType(position);
        View rootView = null;
        String displayItemValue = null;
        
        ReviewItem reviewItem = getReviewListFragment().getCurrentReviewItems().get(position);
        
        switch (itemType) {
        	case HEADER_ITEM_TYPE : 
        			rootView = inflater.inflate(R.layout.list_item_header_review, container, false);
        			((TextView) rootView.findViewById(R.id.review_list_header)).setText(reviewItem.getTitle());
        			break;
        			
        	case SIMPLE_ITEM_TYPE :
    			rootView = inflater.inflate(R.layout.list_item_review, container, false);
    			((TextView) rootView.findViewById(R.id.review_list_item_label)).setText(reviewItem.getTitle());
    			
    			displayItemValue = reviewItem.getDisplayValue();
                if (TextUtils.isEmpty(displayItemValue)) {
                	displayItemValue = DEFAULT_ITEM_VALUE;
                }
    			((TextView) rootView.findViewById(R.id.review_list_item_value)).setText(displayItemValue);
    			break;
        } // end of switch
        return rootView;
    }

    public int getCount() {
        return getReviewListFragment().getCurrentReviewItems().size();
    }

	/**
	 * Getter Method: getReviewListFragment()
	 */
	private ReviewListFragment getReviewListFragment() {
		return reviewListFragment;
	}

	/**
	 * Setter Method: setReviewListFragment()
	 */
	private void setReviewListFragment(ReviewListFragment reviewListFragment) {
		this.reviewListFragment = reviewListFragment;
	}
}