package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.AssessmentResultsActivity;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.wizard.model.ClassificationAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Class: AssessmentClassificationsFragment
 * 
 * Responsible for displaying the assessment 
 * classifications of a patient
 * 
 * @author TOSullivan
 *
 */
public class AssessmentClassificationsFragment extends ListFragment {
    
    private ClassificationAdapter classificationAdapter;
    private Patient patient;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // obtain a reference to the patient being dealt with...
        setPatient(((AssessmentResultsActivity) getActivity()).getPatient());
        
        setClassificationAdapter(new ClassificationAdapter(this));
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	  View myFragmentView = inflater.inflate(R.layout.fragment_assessment_results_review_tab, container, false);
          
          ListView listView = (ListView) myFragmentView.findViewById(android.R.id.list);
          setListAdapter(getClassificationAdapter());
          listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	     	 
    	  return myFragmentView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {   
            // Display a simple Toast to demonstrate that the click event is working. Notice that Fragments have a
            // getString() method just like an Activity, so that you can quickly access your localized Strings.
            Toast.makeText(activity, "TEST CLASSIFICATION CLICK", Toast.LENGTH_SHORT).show();
        }
    }
    
	/**
	 * Getter Method: getClassificationAdapter()
	 */	
	private ClassificationAdapter getClassificationAdapter() {
		return classificationAdapter;
	}

	/**
	 * Setter Method: setClassificationAdapter()
	 */
	private void setClassificationAdapter(ClassificationAdapter classificationAdapter) {
		this.classificationAdapter = classificationAdapter;
	}

	/**
	 * Getter Method: getPatient()
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * Setter Method: setPatient()
	 */
	private void setPatient(Patient patient) {
		this.patient = patient;
	}
}
