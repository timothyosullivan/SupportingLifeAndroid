package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.AssessmentResultsActivity;
import ie.ucc.bis.wizard.model.review.ReviewAssessmentAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Class: AssessmentResultsReviewFragment
 * 
 * Responsible for displaying the assessment 
 * review items list  
 * 
 * @author TOSullivan
 *
 */
public class AssessmentResultsReviewFragment extends ReviewListFragment {
    
    private ReviewAssessmentAdapter reviewAssessmentAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        setReviewAssessmentAdapter(new ReviewAssessmentAdapter(this));
        // capture all the review items associated with each assessment page
        setCurrentReviewItems(((AssessmentResultsActivity) getActivity()).getReviewItems());
        setListAdapter(getReviewAssessmentAdapter());
    }    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	  View myFragmentView = inflater.inflate(R.layout.fragment_assessment_results_review_tab, container, false);
          
          ListView listView = (ListView) myFragmentView.findViewById(android.R.id.list);        
          listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	     	 
    	  return myFragmentView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {   
    //        ListAdapter listAdapter = getListAdapter();
    //        LocationModel locationModel = (LocationModel) listAdapter.getItem(position);
            
            // Display a simple Toast to demonstrate that the click event is working. Notice that Fragments have a
            // getString() method just like an Activity, so that you can quickly access your localized Strings.
            Toast.makeText(activity, "TEST ITEM CLICK", Toast.LENGTH_SHORT).show();
        }
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
}
