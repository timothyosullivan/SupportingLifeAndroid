package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.rule.engine.Classification;
import ie.ucc.bis.rule.engine.Diagnostic;
import ie.ucc.bis.rule.engine.enums.ClassificationType;
import ie.ucc.bis.wizard.ui.AssessmentClassificationsFragment;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassificationAdapter extends BaseAdapter {
	private static final int SIMPLE_ITEM_TYPE = 1;
	private List<Diagnostic> patientDiagnostics;
	
	private AssessmentClassificationsFragment assessmentClassificationsFragment;
	
    public ClassificationAdapter(AssessmentClassificationsFragment assessmentClassificationsFragment, List<Diagnostic> patientDiagnostics) {
		super();
		setAssessmentClassificationsFragment(assessmentClassificationsFragment);
		setPatientDiagnostics(patientDiagnostics);
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
        return getPatientDiagnostics().get(position).getClassification();
    }

    public long getItemId(int position) {
        return getPatientDiagnostics().get(position).getClassification().hashCode();
    }

    public View getView(int position, View view, ViewGroup container) {
        int itemType = getItemViewType(position);
               
        switch (itemType) {
        	case SIMPLE_ITEM_TYPE :
        		if (view == null) {
        	        LayoutInflater inflater = LayoutInflater.from(getAssessmentClassificationsFragment().getActivity());
        			view = inflater.inflate(R.layout.classification_list_item_review, container, false);
        		}
                Classification classification = getPatientDiagnostics().get(position).getClassification();
    			((TextView) view.findViewById(R.id.classification_list_item_label)).setText(classification.getName());
    			((TextView) view.findViewById(R.id.classification_list_item_value)).setText(classification.getType());
    			View classificationView = view.findViewById(R.id.classification_list_item);
    			colourCodeClassification(classification, classificationView);
    			break;
        } // end of switch
        return view;
    }

    /**
     * Method: colourCodeClassification
     * 
     * Responsible for colour coding classification based on the 
     * severity of the classification
     * 
     * @param classification
     * @param classificationView
     */
    private void colourCodeClassification(Classification classification, View classificationView) {
    	Drawable background = null;
		if (classification.getType().equalsIgnoreCase(ClassificationType.SEVERE.name())) {
			background = getAssessmentClassificationsFragment().getResources().getDrawable(R.drawable.red_classification_list_item);
			classificationView.setBackground(background);
		}
		else if (classification.getType().equalsIgnoreCase(ClassificationType.MODERATE.name())) {
			background = getAssessmentClassificationsFragment().getResources().getDrawable(R.drawable.yellow_classification_list_item);
			classificationView.setBackground(background);
		}
		else {
			background = getAssessmentClassificationsFragment().getResources().getDrawable(R.drawable.green_classification_list_item);
			classificationView.setBackground(background);
		}
	}

	public int getCount() {
		// null pointer exception was being called here previously periodically as 
		// AssessmentClassificationFragment was null when querying for number of classifications
		// associated with Patient - so now holding this information locally
		return getPatientDiagnostics().size();
    }

	/**
	 * Getter Method: getAssessmentClassificationsFragment()
	 */
	private AssessmentClassificationsFragment getAssessmentClassificationsFragment() {
		return assessmentClassificationsFragment;
	}

	/**
	 * Setter Method: setAssessmentClassificationsFragment()
	 */
	private void setAssessmentClassificationsFragment(AssessmentClassificationsFragment assessmentClassificationsFragment) {
		this.assessmentClassificationsFragment = assessmentClassificationsFragment;
	}

	/**
	 * Getter Method: getPatientDiagnostics()
	 */
	public List<Diagnostic> getPatientDiagnostics() {
		return patientDiagnostics;
	}

	/**
	 * Setter Method: setPatientDiagnostics()
	 */
	public void setPatientDiagnostics(List<Diagnostic> patientDiagnostics) {
		this.patientDiagnostics = patientDiagnostics;
	}
}