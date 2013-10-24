package ie.ucc.bis.assessment.imci.model;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.imci.ui.ImciAssessmentTreatmentsFragment;
import ie.ucc.bis.rule.engine.Classification;
import ie.ucc.bis.rule.engine.Diagnostic;
import ie.ucc.bis.rule.engine.enums.ClassificationType;

import java.util.List;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author timothyosullivan
 */

public class TreatmentAdapter extends BaseAdapter {
	private static final int SIMPLE_ITEM_TYPE = 1;
	private static final String BULLET_SYMBOL = "&#8226";
	private static final int TITLE_FLASH_COUNT = 3;
	private static final long TITLE_FLASH_BLINK_DURATION = 300;
	
	private List<Diagnostic> patientDiagnostics;
	
	private ImciAssessmentTreatmentsFragment assessmentTreatmentsFragment;
	
    public TreatmentAdapter(ImciAssessmentTreatmentsFragment assessmentTreatmentsFragment, List<Diagnostic> patientDiagnostics) {
		super();
		setAssessmentTreatmentsFragment(assessmentTreatmentsFragment);
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

    @Override
    public boolean isEnabled(int position) {
    	return false;
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
        	        LayoutInflater inflater = LayoutInflater.from(getAssessmentTreatmentsFragment().getActivity());
        			view = inflater.inflate(R.layout.treatment_list_item_review, container, false);
        		}
        		String classificationTitle = getPatientDiagnostics().get(position).getClassification().getName();
        		TextView classificationTitleText = (TextView) view.findViewById(R.id.imci_treatment_list_item_title);
        		classificationTitleText.setText(classificationTitle);
                List<String> treatments = getPatientDiagnostics().get(position).getTreatmentRecommendations();
                addBulletedListToTextView(treatments, ((TextView) view.findViewById(R.id.imci_treatment_list_item_desc)));
                
    			ImageView severityImageView = (ImageView) view.findViewById(R.id.imci_treatment_list_item_classification_severity);
    			colourCodeTreatment(getPatientDiagnostics().get(position).getClassification(), severityImageView);
                
                // animate the title of the treatment if the user has selected the treatment from the classifications tab
                if (classificationTitle.equalsIgnoreCase(getAssessmentTreatmentsFragment().getClassificationTitleSelected())) {
        	    	animateTreatmentTitle(classificationTitleText);
        	    	animateSeverityImage(severityImageView);
                }
    			break;
        } // end of switch
        return view;
    }

	/**
     * Method: animateTreatmentTitle
     * 
     * Responsible for flashing the title of a treatment list item
	 * 
	 * @param classificationTitleText
	 */
	private void animateTreatmentTitle(final TextView classificationTitleText) {
		classificationTitleText.setTextColor(getAssessmentTreatmentsFragment().getResources().getColor(R.color.Black));
		
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(TITLE_FLASH_BLINK_DURATION); //You can manage the time of the blink with this parameter
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(TITLE_FLASH_COUNT);
		
		anim.setAnimationListener(new AnimationListener() {
	        @Override
	        public void onAnimationEnd(Animation animation) {
	        	// revert text colour back to its original green
	        	classificationTitleText.setTextColor(getAssessmentTreatmentsFragment().getResources().getColor(R.color.DarkGreen));   
	        }

			@Override
			public void onAnimationStart(Animation animation) {}

			@Override
			public void onAnimationRepeat(Animation animation) {}
		});
		
		classificationTitleText.startAnimation(anim);
		getAssessmentTreatmentsFragment().setClassificationTitleSelected(null);
	}
	
	/**
     * Method: animateSeverityImage
     * 
     * Responsible for flashing the severity image of a treatment list item
	 * 
	 * @param severityImageView
	 */
	private void animateSeverityImage(final ImageView severityImageView) {

		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(TITLE_FLASH_BLINK_DURATION); //You can manage the time of the blink with this parameter
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(TITLE_FLASH_COUNT);
		
		severityImageView.startAnimation(anim);
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
     * @param severityImageView
     */
    private void colourCodeTreatment(Classification classification, ImageView severityImageView) {
		if (classification.getType().equalsIgnoreCase(ClassificationType.SEVERE.name())) {
			severityImageView.setImageResource(R.drawable.ic_severe_notification);
		}
		else if (classification.getType().equalsIgnoreCase(ClassificationType.MODERATE.name())) {
			severityImageView.setImageResource(R.drawable.ic_moderate_notification);
		}
		else {
			severityImageView.setImageResource(R.drawable.ic_low_notification);
		}
	}

	public int getCount() {
		// null pointer exception was being called here previously periodically as 
		// AssessmentClassificationFragment was null when querying for number of classifications
		// associated with Patient - so now holding this information locally
		return getPatientDiagnostics().size();
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
	/**
	 * Getter Method: getAssessmentTreatmentsFragment()
	 */
	public ImciAssessmentTreatmentsFragment getAssessmentTreatmentsFragment() {
		return assessmentTreatmentsFragment;
	}

	/**
	 * Setter Method: setAssessmentTreatmentsFragment()
	 */
	public void setAssessmentTreatmentsFragment(ImciAssessmentTreatmentsFragment assessmentTreatmentsFragment) {
		this.assessmentTreatmentsFragment = assessmentTreatmentsFragment;
	}
}