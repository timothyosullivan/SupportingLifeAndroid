package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import ie.ucc.bis.rule.engine.Classification;
import ie.ucc.bis.wizard.ui.AssessmentClassificationsFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassificationAdapter extends BaseAdapter {
	private static final int HEADER_ITEM_TYPE = 0;
	private static final int SIMPLE_ITEM_TYPE = 1;
	private static final int MAX_TYPE_COUNT = 2;
	private static final String DEFAULT_ITEM_VALUE = "--------";
	
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
    	// need to ascertain if we are dealing with a header
    	// or just a simple list item
    	Classification classification = getAssessmentClassificationsFragment().getPatient().getClassifications().get(position);
    	// here we could colour code the classification
//    	if (reviewItem.isHeaderItem()) {
//    		return HEADER_ITEM_TYPE;
//    	}
//    	else {
    		return SIMPLE_ITEM_TYPE;
//    	}
    }

    @Override
    public int getViewTypeCount() {
//        return MAX_TYPE_COUNT;
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
//        	case HEADER_ITEM_TYPE : 
//        			rootView = inflater.inflate(R.layout.list_item_header_review, container, false);
//        			((TextView) rootView.findViewById(R.id.review_list_header)).setText(reviewItem.getTitle());
//        			break;
//        			
        	case SIMPLE_ITEM_TYPE :
    			rootView = inflater.inflate(R.layout.list_item_review, container, false);
    			((TextView) rootView.findViewById(R.id.review_list_item_label)).setText(classification.getName());
    			((TextView) rootView.findViewById(R.id.review_list_item_value)).setText(classification.getType());
    			View classificationView = rootView.findViewById(R.id.review_list_item);
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
		if (classification.getType().equalsIgnoreCase(Classification.SEVERE_CLASSIFICATION_TYPE)) {
			classificationView.setBackgroundColor(getAssessmentClassificationsFragment().getResources().getColor(R.color.Red));
		}
		else if (classification.getType().equalsIgnoreCase(Classification.MODERATE_CLASSIFICATION_TYPE)) {
			classificationView.setBackgroundColor(getAssessmentClassificationsFragment().getResources().getColor(R.color.Yellow));
		}
		else {
			classificationView.setBackgroundColor(getAssessmentClassificationsFragment().getResources().getColor(R.color.LightGreen));
		}
	}

	public int getCount() {
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