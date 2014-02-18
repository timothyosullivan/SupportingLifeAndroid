package ie.ucc.bis.supportinglife.assessment.imci.ui;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.activity.AssessmentResultsActivity;
import ie.ucc.bis.supportinglife.activity.ImciAssessmentResultsActivity;
import ie.ucc.bis.supportinglife.assessment.imci.model.ImciClassificationAdapter;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
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
 * Class: ImciAssessmentClassificationsFragment
 * 
 * Responsible for displaying the IMCI Assessment 
 * Classifications of a patient
 * 
 * @author TOSullivan
 *
 */
public class ImciAssessmentClassificationsFragment extends ListFragment {
    
    private ImciClassificationAdapter classificationAdapter;
    private PatientAssessment patient;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      
        // obtain a reference to the patient being dealt with...
        setPatient(((AssessmentResultsActivity) getActivity()).getPatientAssessment());          
        setClassificationAdapter(new ImciClassificationAdapter(this, new ArrayList<Diagnostic>(getPatient().getDiagnostics())));
        setListAdapter(getClassificationAdapter());
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
			((ImciAssessmentResultsActivity) getActivity()).displayTreatmentTab(position, classificationTitle);
        }
    }
    
	/**
	 * Getter Method: getClassificationAdapter()
	 */	
	private ImciClassificationAdapter getClassificationAdapter() {
		return classificationAdapter;
	}

	/**
	 * Setter Method: setClassificationAdapter()
	 */
	private void setClassificationAdapter(ImciClassificationAdapter classificationAdapter) {
		this.classificationAdapter = classificationAdapter;
	}

	/**
	 * Getter Method: getPatient()
	 */
	public PatientAssessment getPatient() {
		return patient;
	}

	/**
	 * Setter Method: setPatient()
	 */
	private void setPatient(PatientAssessment patient) {
		this.patient = patient;
	}
}
