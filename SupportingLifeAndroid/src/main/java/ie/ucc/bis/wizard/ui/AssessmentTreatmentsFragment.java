package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.AssessmentResultsActivity;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.wizard.model.TreatmentAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Class: AssessmentTreatmentsFragment
 * 
 * Responsible for displaying the assessment 
 * treatments of a patient
 * 
 * @author TOSullivan
 *
 */
public class AssessmentTreatmentsFragment extends ListFragment {
    
    private TreatmentAdapter treatmentAdapter;
    private Patient patient;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      
        // obtain a reference to the patient being dealt with...
        setPatient(((AssessmentResultsActivity) getActivity()).getPatient());          
        setTreatmentAdapter(new TreatmentAdapter(this, getPatient()));
        setListAdapter(getTreatmentAdapter());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	  View myFragmentView = inflater.inflate(R.layout.fragment_assessment_results_treatment_tab, container, false);
          
          ListView listView = (ListView) myFragmentView.findViewById(android.R.id.list);         
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

	/**
	 * Getter Method: getTreatmentAdapter()
	 */
	public TreatmentAdapter getTreatmentAdapter() {
		return treatmentAdapter;
	}
	
	/**
	 * Setter Method: setTreatmentAdapter()
	 */
	public void setTreatmentAdapter(TreatmentAdapter treatmentAdapter) {
		this.treatmentAdapter = treatmentAdapter;
	}
}
