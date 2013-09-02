package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.rule.engine.Classification;
import ie.ucc.bis.rule.engine.enums.ClassificationType;
import ie.ucc.bis.wizard.ui.AssessmentTreatmentsFragment;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TreatmentAdapter extends BaseAdapter {
	private static final int SIMPLE_ITEM_TYPE = 1;
	private static final String BULLET_SYMBOL = "&#8226";
	
	private Patient patient;
	
	private AssessmentTreatmentsFragment assessmentTreatmentsFragment;
	
    public TreatmentAdapter(AssessmentTreatmentsFragment assessmentTreatmentsFragment, Patient patient) {
		super();
		setAssessmentTreatmentsFragment(assessmentTreatmentsFragment);
		setPatient(patient);
	}

	@Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getItemViewType(int position) {
   		return SIMPLE_ITEM_TYPE;
    }

    @Override
    public int getViewTypeCount() {
    	return 1;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    public Object getItem(int position) {
        return getPatient().getDiagnostics().get(position).getClassification();
    }

    public long getItemId(int position) {
        return getPatient().getDiagnostics().get(position).getClassification().hashCode();
    }

    public View getView(int position, View view, ViewGroup container) {
        int itemType = getItemViewType(position);
               
        switch (itemType) {
        	case SIMPLE_ITEM_TYPE :
        		if (view == null) {
        	        LayoutInflater inflater = LayoutInflater.from(getAssessmentTreatmentsFragment().getActivity());
        			view = inflater.inflate(R.layout.treatment_list_item_review, container, false);
        		}
        		String classificationTitle = getPatient().getDiagnostics().get(position).getClassification().getName();
        		((TextView) view.findViewById(R.id.treatment_list_item_title)).setText(classificationTitle);
                List<String> treatments = getPatient().getDiagnostics().get(position).getTreatmentRecommendations();
                addBulletedListToTextView(treatments, ((TextView) view.findViewById(R.id.treatment_list_item_desc)));
    		//	View treatmentView = view.findViewById(R.id.treatment_list_item);
    		//	colourCodeTreatment(getPatient().getDiagnostics().get(position).getClassification(), treatmentView);
    			break;
        } // end of switch
        return view;
    }

	/**
     * Method: addBulletedListToTextView
     * 
     * Responsible for adding a bulleted list to a textview
     * 
     * @param treatments
     * @param textView
     */
    private void addBulletedListToTextView(List<String> treatments, TextView textView) {
    	textView.setText("");
    	for(String treatment : treatments) {
    	    textView.append(Html.fromHtml(BULLET_SYMBOL + treatment)
                    + System.getProperty("line.separator"));
    	}
	}

	/**
     * Method: colourCodeTreatment
     * 
     * Responsible for colour coding classification based on the 
     * severity of the classification
     * 
     * @param classification
     * @param classificationView
     */
    private void colourCodeTreatment(Classification classification, View classificationView) {
    	Drawable background = null;
		if (classification.getType().equalsIgnoreCase(ClassificationType.SEVERE.name())) {
			background = getAssessmentTreatmentsFragment().getResources().getDrawable(R.drawable.red_classification_list_item);
			classificationView.setBackground(background);
		}
		else if (classification.getType().equalsIgnoreCase(ClassificationType.MODERATE.name())) {
			background = getAssessmentTreatmentsFragment().getResources().getDrawable(R.drawable.yellow_classification_list_item);
			classificationView.setBackground(background);
		}
		else {
			background = getAssessmentTreatmentsFragment().getResources().getDrawable(R.drawable.green_classification_list_item);
			classificationView.setBackground(background);
		}
	}

	public int getCount() {
		// null pointer exception was being called here previously periodically as 
		// AssessmentClassificationFragment was null when querying for number of classifications
		// associated with Patient - so now holding this information locally
		return getPatient().getDiagnostics().size();
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
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Getter Method: getAssessmentTreatmentsFragment()
	 */
	public AssessmentTreatmentsFragment getAssessmentTreatmentsFragment() {
		return assessmentTreatmentsFragment;
	}

	/**
	 * Setter Method: setAssessmentTreatmentsFragment()
	 */
	public void setAssessmentTreatmentsFragment(AssessmentTreatmentsFragment assessmentTreatmentsFragment) {
		this.assessmentTreatmentsFragment = assessmentTreatmentsFragment;
	}
}