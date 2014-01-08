package ie.ucc.bis.supportinglife.assessment.ccm.model;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.ccm.ui.CcmAssessmentClassificationsFragment;
import ie.ucc.bis.supportinglife.rule.engine.Classification;
import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;
import ie.ucc.bis.supportinglife.rule.engine.enums.CcmClassificationType;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class: CcmClassificationAdapter
 * 
 * @author timothyosullivan
 */

public class CcmClassificationAdapter extends BaseAdapter {
	private static final int SIMPLE_ITEM_TYPE = 1;
	private List<Diagnostic> patientDiagnostics;
	
	private CcmAssessmentClassificationsFragment ccmAssessmentClassificationsFragment;
	
    public CcmClassificationAdapter(CcmAssessmentClassificationsFragment ccmAssessmentClassificationsFragment, List<Diagnostic> patientDiagnostics) {
		super();
		setCcmAssessmentClassificationsFragment(ccmAssessmentClassificationsFragment);
		
    	// ignore diagnostic which have a treatmentHeader/treatmentFooter associated
    	// - These are just placeholders for giving general treatment quidance to a 
    	// 	 patient who has either a 'DANGER SIGN' or 'SICK' classification
		setPatientDiagnostics(new ArrayList<Diagnostic>());
		for (Diagnostic diagnostic : patientDiagnostics) {
			if (diagnostic.isTreatmentHeader() != true && diagnostic.isTreatmentFooter() != true) {
				getPatientDiagnostics().add(diagnostic);
			}
		}
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
        	        LayoutInflater inflater = LayoutInflater.from(getCcmAssessmentClassificationsFragment().getActivity());
        			view = inflater.inflate(R.layout.classification_list_item_review, container, false);
        		}
                Classification classification = getPatientDiagnostics().get(position).getClassification();
    			((TextView) view.findViewById(R.id.classification_list_item_label)).setText(classification.getName());
    			
    			ImageView severityImageView = (ImageView) view.findViewById(R.id.classification_list_item_classification_severity);
    			colourCodeTreatment(getPatientDiagnostics().get(position).getClassification(), severityImageView);
    			break;
        } // end of switch
        return view;
    }

    
	/**
     * Method: colourCodeTreatment
     * 
     * Responsible for colour coding classification based on the 
     * severity of the classification
     * 
     * @param classification
     * @param severityImageView
     */
    private void colourCodeTreatment(Classification classification, ImageView severityImageView) {
		if (classification.getType().equalsIgnoreCase(CcmClassificationType.DANGER_SIGN.name())) {
			severityImageView.setImageResource(R.drawable.ic_severe_notification);
		}
		else if (classification.getType().equalsIgnoreCase(CcmClassificationType.REFER.name())) {
			severityImageView.setImageResource(R.drawable.ic_refer_notification);
		}		
		else if (classification.getType().equalsIgnoreCase(CcmClassificationType.SICK.name())) {
			severityImageView.setImageResource(R.drawable.ic_moderate_notification);
		}
	}
    
	public int getCount() {
		// null pointer exception was being called here previously periodically as 
		// AssessmentClassificationFragment was null when querying for number of classifications
		// associated with Patient - so now holding this information locally
		return getPatientDiagnostics().size();
    }

	/**
	 * Getter Method: getCcmAssessmentClassificationsFragment()
	 */
	private CcmAssessmentClassificationsFragment getCcmAssessmentClassificationsFragment() {
		return ccmAssessmentClassificationsFragment;
	}

	/**
	 * Setter Method: setCcmAssessmentClassificationsFragment()
	 */
	private void setCcmAssessmentClassificationsFragment(CcmAssessmentClassificationsFragment ccmAssessmentClassificationsFragment) {
		this.ccmAssessmentClassificationsFragment = ccmAssessmentClassificationsFragment;
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