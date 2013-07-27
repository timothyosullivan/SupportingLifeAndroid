package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.model.AbstractWizardModel;
import ie.ucc.bis.wizard.model.ModelCallbacks;
import ie.ucc.bis.wizard.model.AbstractPage;
import ie.ucc.bis.wizard.model.ReviewItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ReviewFragment extends ListFragment implements ModelCallbacks {
    private ReviewFragmentCallbacks reviewFragmentCallbacks;
    private AbstractWizardModel mWizardModel;
    private List<ReviewItem> mCurrentReviewItems;

    private ReviewAdapter mReviewAdapter;

    public ReviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviewAdapter = new ReviewAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        TextView titleView = (TextView) rootView.findViewById(android.R.id.title);
        titleView.setText(R.string.assessment_wizard_review_title);
        
        titleView.setTextColor(getResources().getColor(R.color.DarkGreen));

        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        setListAdapter(mReviewAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof ReviewFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement fragment's callbacks");
        }

        setReviewFragmentCallbacks((ReviewFragmentCallbacks) activity);

        mWizardModel = getReviewFragmentCallbacks().getWizardModel();
        mWizardModel.registerListener(this);
        onPageTreeChanged();
    }

   
    public void onPageTreeChanged() {
        onPageDataChanged(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setReviewFragmentCallbacks(null);

        mWizardModel.unregisterListener(this);
    }

    public void onPageDataChanged(AbstractPage changedPage) {
        ArrayList<ReviewItem> reviewItems = new ArrayList<ReviewItem>();
        for (AbstractPage page : mWizardModel.getCurrentPageSequence()) {
            page.getReviewItems(reviewItems);
        }
        
        Collections.sort(reviewItems, new Comparator<ReviewItem>() {
            public int compare(ReviewItem a, ReviewItem b) {
                return a.getWeight() > b.getWeight() ? +1 : a.getWeight() < b.getWeight() ? -1 : 0;
            }
        });
        mCurrentReviewItems = reviewItems;

        if (mReviewAdapter != null) {
            mReviewAdapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        getReviewFragmentCallbacks().onEditScreenAfterReview(mCurrentReviewItems.get(position).getPageKey());
    }


    private class ReviewAdapter extends BaseAdapter {
    	private static final int HEADER_ITEM_TYPE = 0;
    	private static final int SIMPLE_ITEM_TYPE = 1;
    	private static final int MAX_TYPE_COUNT = 2;
    	private static final String DEFAULT_ITEM_VALUE = "--------";
    	
        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public int getItemViewType(int position) {
        	// need to ascertain if we are dealing with a header
        	// or just a simple list item
        	ReviewItem reviewItem = mCurrentReviewItems.get(position);
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
            return mCurrentReviewItems.get(position);
        }

        public long getItemId(int position) {
            return mCurrentReviewItems.get(position).hashCode();
        }

        public View getView(int position, View view, ViewGroup container) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            int itemType = getItemViewType(position);
            View rootView = null;
            String displayItemValue = null;
            
            ReviewItem reviewItem = mCurrentReviewItems.get(position);
            
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
            return mCurrentReviewItems.size();
        }
    }

	/**
	 * Getter Method: getReviewFragmentCallbacks()
	 * 
	 */		 
	public ReviewFragmentCallbacks getReviewFragmentCallbacks() {
		return reviewFragmentCallbacks;
	}

	/**
	 * Setter Method: setReviewFragmentCallbacks()
	 * 
	 */  	
	public void setReviewFragmentCallbacks(ReviewFragmentCallbacks reviewFragmentCallbacks) {
		this.reviewFragmentCallbacks = reviewFragmentCallbacks;
	}
}
