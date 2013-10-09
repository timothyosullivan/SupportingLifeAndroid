package ie.ucc.bis.imci.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.ImciAssessmentResultsActivity;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.imci.model.TreatmentAdapter;
import ie.ucc.bis.rule.engine.Diagnostic;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    private String classificationTitleSelected;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      
        // obtain a reference to the patient being dealt with...
        setPatient(((ImciAssessmentResultsActivity) getActivity()).getPatient());          
        setTreatmentAdapter(new TreatmentAdapter(this, new ArrayList<Diagnostic>(getPatient().getDiagnostics())));
        setListAdapter(getTreatmentAdapter());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	  View myFragmentView = inflater.inflate(R.layout.fragment_assessment_results_treatment_tab, container, false);
          
          ListView listView = (ListView) myFragmentView.findViewById(android.R.id.list);         
          listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    
    	  return myFragmentView;
    }

	/**
	 * Scroll to the relevant position in the list
	 * and highlight temporarily
	 * 
	 * @param position 
	 */
    public void scrollToRelatedElement(int position, String classificationTitle) {
    	
    	setClassificationTitleSelected(classificationTitle);
    	// scroll to relevant element
    	getListView().setSelection(position);    	
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

	/**
	 * Getter Method: getClassificationTitleSelected()
	 */
	public String getClassificationTitleSelected() {
		return classificationTitleSelected;
	}

	/**
	 * Setter Method: setClassificationTitleSelected()
	 */
	public void setClassificationTitleSelected(String classificationTitleSelected) {
		this.classificationTitleSelected = classificationTitleSelected;
	}
}
