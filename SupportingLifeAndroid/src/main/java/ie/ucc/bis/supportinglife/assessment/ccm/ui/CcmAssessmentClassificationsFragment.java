package ie.ucc.bis.supportinglife.assessment.ccm.ui;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.activity.AssessmentResultsActivity;
import ie.ucc.bis.supportinglife.activity.CcmAssessmentResultsActivity;
import ie.ucc.bis.supportinglife.assessment.ccm.model.CcmClassificationAdapter;
import ie.ucc.bis.supportinglife.domain.Patient;
import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Class: CcmAssessmentClassificationsFragment
 * 
 * Responsible for displaying the CCM Assessment 
 * Classifications of a patient
 * 
 * @author TOSullivan
 *
 */
public class CcmAssessmentClassificationsFragment extends ListFragment {
    
    private CcmClassificationAdapter ccmClassificationAdapter;
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
        setCcmClassificationAdapter(new CcmClassificationAdapter(this, new ArrayList<Diagnostic>(getPatient().getDiagnostics())));
        setListAdapter(getCcmClassificationAdapter());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	  View myFragmentView = inflater.inflate(R.layout.fragment_assessment_results_classification_tab, container, false);
          
          ListView listView = (ListView) myFragmentView.findViewById(android.R.id.list);         
          listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    	  return myFragmentView;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {   
            // open the treatments tab and scroll to the relevant treatment
        	String classificationTitle = ((TextView) v.findViewById(R.id.classification_list_item_label)).getText().toString();
			((CcmAssessmentResultsActivity) getActivity()).displayTreatmentTab(classificationTitle);
        }
    }
    
	/**
	 * Getter Method: getCcmClassificationAdapter()
	 */	
	private CcmClassificationAdapter getCcmClassificationAdapter() {
		return ccmClassificationAdapter;
	}

	/**
	 * Setter Method: setCcmClassificationAdapter()
	 */
	private void setCcmClassificationAdapter(CcmClassificationAdapter ccmClassificationAdapter) {
		this.ccmClassificationAdapter = ccmClassificationAdapter;
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
