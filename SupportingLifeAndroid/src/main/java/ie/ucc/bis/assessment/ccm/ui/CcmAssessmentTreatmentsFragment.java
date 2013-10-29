package ie.ucc.bis.assessment.ccm.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.AssessmentResultsActivity;
import ie.ucc.bis.assessment.ccm.model.CcmTreatmentAdapter;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.rule.engine.Diagnostic;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Class: CcmAssessmentTreatmentsFragment
 * 
 * Responsible for displaying the assessment 
 * treatments of a patient
 * 
 * @author TOSullivan
 *
 */
public class CcmAssessmentTreatmentsFragment extends ListFragment {
    
    private CcmTreatmentAdapter ccmTreatmentAdapter;
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
        setCcmTreatmentAdapter(new CcmTreatmentAdapter(this, new ArrayList<Diagnostic>(getPatient().getDiagnostics())));
        setListAdapter(getCcmTreatmentAdapter());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	  View myFragmentView = inflater.inflate(R.layout.fragment_assessment_results_treatment_tab, container, false);
          
          ListView listView = (ListView) myFragmentView.findViewById(android.R.id.list);         
          listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    
    	  return myFragmentView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {}
    
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
	 * Getter Method: getCcmTreatmentAdapter()
	 */
	public CcmTreatmentAdapter getCcmTreatmentAdapter() {
		return ccmTreatmentAdapter;
	}
	
	/**
	 * Setter Method: setCcmTreatmentAdapter()
	 */
	public void setCcmTreatmentAdapter(CcmTreatmentAdapter ccmTreatmentAdapter) {
		this.ccmTreatmentAdapter = ccmTreatmentAdapter;
	}
}
