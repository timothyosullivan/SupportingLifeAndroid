package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.rule.engine.Classification;
import ie.ucc.bis.wizard.ui.AssessmentClassificationsFragment;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassificationAdapter extends BaseAdapter {
	private static final int SIMPLE_ITEM_TYPE = 1;
	
	private AssessmentClassificationsFragment assessmentClassificationsFragment;
	
    public ClassificationAdapter(AssessmentClassificationsFragment assessmentClassificationsFragment) {
		super();
		setAssessmentClassificationsFragment(assessmentClassificationsFragment);
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
        return getAssessmentClassificationsFragment().getPatient().getClassifications().get(position);
    }

    public long getItemId(int position) {
        return getAssessmentClassificationsFragment().getPatient().getClassifications().get(position).hashCode();
    }

    public View getView(int position, View view, ViewGroup container) {
        LayoutInflater inflater = LayoutInflater.from(getAssessmentClassificationsFragment().getActivity());
        int itemType = getItemViewType(position);
        View rootView = null;
        
        Classification classification = getAssessmentClassificationsFragment().getPatient().getClassifications().get(position);
        
        switch (itemType) {
        	case SIMPLE_ITEM_TYPE :
    			rootView = inflater.inflate(R.layout.classification_list_item_review, container, false);
    			((TextView) rootView.findViewById(R.id.classification_list_item_label)).setText(classification.getName());
    			((TextView) rootView.findViewById(R.id.classification_list_item_value)).setText(classification.getType());
    			View classificationView = rootView.findViewById(R.id.classification_list_item);
    			colourCodeClassification(classification, classificationView);
    			break;
        } // end of switch
        return rootView;
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
		if (classification.getType().equalsIgnoreCase(Classification.SEVERE_CLASSIFICATION_TYPE)) {
			background = getAssessmentClassificationsFragment().getResources().getDrawable(R.drawable.red_classification_list_item);
			classificationView.setBackground(background);
		}
		else if (classification.getType().equalsIgnoreCase(Classification.MODERATE_CLASSIFICATION_TYPE)) {
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
		return getAssessmentClassificationsFragment().getPatient().getClassifications().size();
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
}