package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.imci.ui.ReviewListFragment;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class ReviewAssessmentAdapter extends BaseAdapter implements Filterable {
	private static final int HEADER_ITEM_TYPE = 0;
	private static final int SIMPLE_ITEM_TYPE = 1;
	private static final int MAX_TYPE_COUNT = 2;
	private static final String DEFAULT_ITEM_VALUE = "--------";
	
	private ReviewListFragment reviewListFragment;
	private List<ReviewItem> filteredReviewItems;
	
    public ReviewAssessmentAdapter(ReviewListFragment reviewListFragment) {
		super();
		setReviewListFragment(reviewListFragment);
		// apply filter to remove review items which we indicated should be invisible
		getFilter().filter(null);
	}

	@Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getItemViewType(int position) {
    	// need to ascertain if we are dealing with a header
    	// or just a simple list item
    	ReviewItem reviewItem = getFilteredReviewItems().get(position);
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
        return getFilteredReviewItems().get(position);
    }

    public long getItemId(int position) {
        return getFilteredReviewItems().get(position).hashCode();
    }

    public View getView(int position, View view, ViewGroup container) {
        int itemType = getItemViewType(position);
    	ReviewItem reviewItem = getFilteredReviewItems().get(position);
      
        switch (itemType) {
        	case HEADER_ITEM_TYPE : 
            	if (view == null) {
            		LayoutInflater inflater = LayoutInflater.from(getReviewListFragment().getActivity());
        			view = inflater.inflate(R.layout.list_item_header_review, container, false);
            	}
	
        		((TextView) view.findViewById(R.id.review_list_header)).setText(reviewItem.getTitle());
        		break;
	        			
        	case SIMPLE_ITEM_TYPE :
            	if (view == null) {
            		LayoutInflater inflater = LayoutInflater.from(getReviewListFragment().getActivity());
            		view = inflater.inflate(R.layout.list_item_review, container, false);
            	}

            	((TextView) view.findViewById(R.id.review_list_item_label)).setText(reviewItem.getTitle());
	    			
	   			String displayItemValue = reviewItem.getDisplayValue();
	   			if (TextUtils.isEmpty(displayItemValue)) {
	   				displayItemValue = DEFAULT_ITEM_VALUE;
	   			}
	    		((TextView) view.findViewById(R.id.review_list_item_value)).setText(displayItemValue);
	    		break;
        } // end of switch
        return view;
    }

    public int getCount() {
        return getFilteredReviewItems().size();
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

	@Override
	public Filter getFilter() {
		Filter customFilter = new Filter() {
				/* 
				 * Responsible for filtering review items such that those items 
				 * marked 'not visible' will be filtered out i.e. the adapter will
				 * not fetch a view for those review items.
				 * 
				 * (non-Javadoc)
				 * @see android.widget.Filter#performFiltering(java.lang.CharSequence)
				 */
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults filterResults = new FilterResults();
					setFilteredReviewItems(new ArrayList<ReviewItem>());
					
					for (ReviewItem reviewItem : getReviewListFragment().getCurrentReviewItems()) {
						if (reviewItem.isVisible()) {
							getFilteredReviewItems().add(reviewItem);
						}
					}
					
					filterResults.values = getFilteredReviewItems();
					filterResults.count = getFilteredReviewItems().size();
					return filterResults;	
				}
	
				@SuppressWarnings("unchecked")
				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					// Now we have to inform the adapter about the new filtered list 
				    if (results.count == 0)
				        notifyDataSetInvalidated();
				    else {
				    	setFilteredReviewItems((List<ReviewItem>) results.values);
				        notifyDataSetChanged();
				    }
	            }};
            
		return customFilter;
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