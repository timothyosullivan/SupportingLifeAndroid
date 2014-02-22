package ie.ucc.bis.supportinglife.assessment.imci.ui;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.model.AbstractModel;
import ie.ucc.bis.supportinglife.assessment.model.AbstractPage;
import ie.ucc.bis.supportinglife.assessment.model.ModelCallbacks;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewAssessmentAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ReviewFragment extends ReviewListFragment implements ModelCallbacks {
    private ReviewFragmentCallbacks reviewFragmentCallbacks;
    private AbstractModel wizardModel;
    private ReviewAssessmentAdapter reviewAssessmentAdapter;

    public ReviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        TextView titleView = (TextView) rootView.findViewById(android.R.id.title);
        titleView.setText(R.string.assessment_wizard_review_title);
        
        titleView.setTextColor(getResources().getColor(R.color.DarkGreen));

        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return rootView;
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		// add long click listener to the list so that we can determine when
		// to invoke a review item edit and so we will navigate back to relevant
		// assessment fragment more gracefully - ref. JIRA SL-101
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				getReviewFragmentCallbacks().onEditScreenAfterReview(getReviewAssessmentAdapter().getFilteredReviewItems().get(position).getPageKey());
				return true;
			}
		});
	}
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof ReviewFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement fragment's callbacks");
        }

        if (activity != null) {
        	setReviewFragmentCallbacks((ReviewFragmentCallbacks) activity);
        }
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	
    	setWizardModel(getReviewFragmentCallbacks().getWizardModel());
    	getWizardModel().registerListener(this);
    	
    	setCurrentReviewItems(getWizardModel().gatherAssessmentReviewItems());
    	setReviewAssessmentAdapter(new ReviewAssessmentAdapter(this));
    	setListAdapter(getReviewAssessmentAdapter());
    	
    	onPageTreeChanged();
    }
    
    public void onPageTreeChanged() {
        onPageDataChanged(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setReviewFragmentCallbacks(null);

        getWizardModel().unregisterListener(this);
    }

    public void onPageDataChanged(AbstractPage changedPage) {
       setCurrentReviewItems(getWizardModel().gatherAssessmentReviewItems());

        if (getReviewAssessmentAdapter() != null) {
        	getReviewAssessmentAdapter().getFilter().filter(null);// apply filter to remove review items which we indicated should be invisible
    		
        	getReviewAssessmentAdapter().notifyDataSetChanged();
        }
    }  
    

	/**
	 * Getter Method: getReviewFragmentCallbacks()
	 */		 
	public ReviewFragmentCallbacks getReviewFragmentCallbacks() {
		return reviewFragmentCallbacks;
	}

	/**
	 * Setter Method: setReviewFragmentCallbacks()
	 */  	
	public void setReviewFragmentCallbacks(ReviewFragmentCallbacks reviewFragmentCallbacks) {
		this.reviewFragmentCallbacks = reviewFragmentCallbacks;
	}

	/**
	 * Getter Method: getReviewAssessmentAdapter()
	 */	
	private ReviewAssessmentAdapter getReviewAssessmentAdapter() {
		return reviewAssessmentAdapter;
	}

	/**
	 * Setter Method: setReviewAssessmentAdapter()
	 */
	private void setReviewAssessmentAdapter(ReviewAssessmentAdapter reviewAssessmentAdapter) {
		this.reviewAssessmentAdapter = reviewAssessmentAdapter;
	}

	/**
	 * Getter Method: getWizardModel()
	 */	
	private AbstractModel getWizardModel() {
		return wizardModel;
	}

	/**
	 * Setter Method: setWizardModel()
	 */
	private void setWizardModel(AbstractModel wizardModel) {
		this.wizardModel = wizardModel;
	}
}
