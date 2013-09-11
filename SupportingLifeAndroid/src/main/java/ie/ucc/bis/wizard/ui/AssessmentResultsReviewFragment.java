package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.AssessmentResultsActivity;
import ie.ucc.bis.wizard.model.AssessmentAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    
    private AssessmentAdapter assessmentAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        setAssessmentAdapter(new AssessmentAdapter(this));
        // capture all the review items associated with each assessment page
        setCurrentReviewItems(((AssessmentResultsActivity) getActivity()).getReviewItems());
        setListAdapter(getAssessmentAdapter());
    }    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	  View myFragmentView = inflater.inflate(R.layout.fragment_assessment_results_review_tab, container, false);
          
          ListView listView = (ListView) myFragmentView.findViewById(android.R.id.list);        
          listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	     	 
    	  return myFragmentView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {}
    
	/**
	 * Getter Method: getAssessmentAdapter()
	 */	
	private AssessmentAdapter getAssessmentAdapter() {
		return assessmentAdapter;
	}

	/**
	 * Setter Method: setAssessmentAdapter()
	 */
	private void setAssessmentAdapter(AssessmentAdapter assessmentAdapter) {
		this.assessmentAdapter = assessmentAdapter;
	}
}
