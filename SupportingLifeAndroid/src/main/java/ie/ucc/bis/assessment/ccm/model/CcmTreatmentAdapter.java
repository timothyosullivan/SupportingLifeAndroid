package ie.ucc.bis.assessment.ccm.model;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.ccm.ui.CcmAssessmentTreatmentsFragment;
import ie.ucc.bis.rule.engine.CcmTreatmentDiagnosticComparator;
import ie.ucc.bis.rule.engine.Classification;
import ie.ucc.bis.rule.engine.Diagnostic;
import ie.ucc.bis.rule.engine.enums.CcmClassificationType;

import java.util.ArrayList;
import java.util.Collections;
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
 * Class: CcmTreatmentAdapter
 * 
 * @author timothyosullivan
 */

public class CcmTreatmentAdapter extends BaseAdapter {
	
	private static final String LINE_BREAK_ESCAPE_CHARACTER = "\\n";
	private static final int HEADER_ITEM_TYPE = 0;
	private static final int FOOTER_ITEM_TYPE = 1;
	private static final int SIMPLE_ITEM_TYPE = 2;
	private static final int MAX_TYPE_COUNT = 3;
	private static final String DARK_GREEN_RIGHT_ANGLE_QUOTE_SYMBOL = "<font color='#006400'><strong>&#187</strong></font>";
	private static final int TITLE_FLASH_COUNT = 3;
	private static final long TITLE_FLASH_BLINK_DURATION = 300;
	
	private List<Diagnostic> patientDiagnostics;
	
	private CcmAssessmentTreatmentsFragment ccmAssessmentTreatmentsFragment;
	
    public CcmTreatmentAdapter(CcmAssessmentTreatmentsFragment ccmAssessmentTreatmentsFragment, List<Diagnostic> patientDiagnostics) {
		super();
		setCcmAssessmentTreatmentsFragment(ccmAssessmentTreatmentsFragment);
		setPatientDiagnostics(new ArrayList<Diagnostic>());

		for (Diagnostic diagnostic : patientDiagnostics) {
			if (diagnostic.getTreatmentRecommendations().size() != 0 || diagnostic.isTreatmentHeader() || diagnostic.isTreatmentFooter()) {
				getPatientDiagnostics().add(diagnostic);
			}
		}
		
		Collections.sort(getPatientDiagnostics(), new CcmTreatmentDiagnosticComparator());
	}

	@Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getItemViewType(int position) {
    	// need to ascertain if we are dealing with a header
    	// or just a list item
    	Diagnostic diagnostic = getPatientDiagnostics().get(position);
    	if (diagnostic.isTreatmentHeader()) {
    		return HEADER_ITEM_TYPE;
    	}
    	else if (diagnostic.isTreatmentFooter()) {
    		return FOOTER_ITEM_TYPE;
    	}
    	else {
    		return SIMPLE_ITEM_TYPE;
    	}
    }

    @Override
    public int getViewTypeCount() {
        return MAX_TYPE_COUNT;
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
        return getPatientDiagnostics().get(position).hashCode();
    }

    public View getView(int position, View view, ViewGroup container) {
        int itemType = getItemViewType(position);
               
        switch (itemType) {
        	case HEADER_ITEM_TYPE : 
        		if (view == null) {
        	        LayoutInflater inflater = LayoutInflater.from(getCcmAssessmentTreatmentsFragment().getActivity());
        			view = inflater.inflate(R.layout.ccm_treatment_list_item_header, container, false);
        		}
    			// configure severity icon to display
        		ImageView severityImageView = (ImageView) view.findViewById(R.id.treatment_classification_severity);
    			colourCodeTreatment(getPatientDiagnostics().get(position).getClassification(), severityImageView);
    			
    			TextView treatmentsTitle = (TextView) view.findViewById(R.id.treatment_title);
    			
    			// add recommended header treatments 
    			List<String> headerTreatments = getPatientDiagnostics().get(position).getTreatmentRecommendations();
                addBulletedListToTextView(headerTreatments, ((TextView) view.findViewById(R.id.treatment_list_item_desc)));
                
                // animate the header
            	animateSeverityImage(severityImageView);
        	   	animateTreatmentTitle(treatmentsTitle);
    			break;
    			
        	case SIMPLE_ITEM_TYPE :
        		if (view == null) {
        	        LayoutInflater inflater = LayoutInflater.from(getCcmAssessmentTreatmentsFragment().getActivity());
        			view = inflater.inflate(R.layout.ccm_treatment_list_item, container, false);
        		}
        		String classificationTitle = getPatientDiagnostics().get(position).getClassification().getCcmTreatmentDisplayName();   		
        		TextView classificationTitleText = (TextView) view.findViewById(R.id.treatment_list_item_title);
        		classificationTitleText.setText(classificationTitle);
        		
                List<String> treatments = getPatientDiagnostics().get(position).getTreatmentRecommendations();
                addBulletedListToTextView(treatments, ((TextView) view.findViewById(R.id.treatment_list_item_desc)));            
    			break;

        	case FOOTER_ITEM_TYPE :
        		if (view == null) {
        	        LayoutInflater inflater = LayoutInflater.from(getCcmAssessmentTreatmentsFragment().getActivity());
        			view = inflater.inflate(R.layout.ccm_treatment_list_item_footer, container, false);
        		}
                List<String> footerTreatments = getPatientDiagnostics().get(position).getTreatmentRecommendations();
                addBulletedListToTextView(footerTreatments, ((TextView) view.findViewById(R.id.treatment_list_item_desc)));

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
		classificationTitleText.setTextColor(getCcmAssessmentTreatmentsFragment().getResources().getColor(R.color.Black));
		
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(TITLE_FLASH_BLINK_DURATION); //You can manage the time of the blink with this parameter
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(TITLE_FLASH_COUNT);
		
		anim.setAnimationListener(new AnimationListener() {
	        @Override
	        public void onAnimationEnd(Animation animation) {
	        	// revert text colour back to its original green
	        	classificationTitleText.setTextColor(getCcmAssessmentTreatmentsFragment().getResources().getColor(R.color.DarkGreen));   
	        }

			@Override
			public void onAnimationStart(Animation animation) {}

			@Override
			public void onAnimationRepeat(Animation animation) {}
		});
		
		classificationTitleText.startAnimation(anim);
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
    		String[] lineBreakSeparatedTreatment = treatment.split(LINE_BREAK_ESCAPE_CHARACTER);
    		textView.append(Html.fromHtml(DARK_GREEN_RIGHT_ANGLE_QUOTE_SYMBOL));
    		for (String treatmentSegment : lineBreakSeparatedTreatment) {
    			// remove line break escape character
    			treatmentSegment = treatmentSegment.replace(LINE_BREAK_ESCAPE_CHARACTER.toString(), " ");
    			// remove whitespace at start and end of string
    			treatmentSegment = treatmentSegment.trim();
    			textView.append(treatmentSegment + System.getProperty("line.separator"));
    		}
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
	 * Getter Method: getCcmAssessmentTreatmentsFragment()
	 */
	public CcmAssessmentTreatmentsFragment getCcmAssessmentTreatmentsFragment() {
		return ccmAssessmentTreatmentsFragment;
	}

	/**
	 * Setter Method: setCcmAssessmentTreatmentsFragment()
	 */
	public void setCcmAssessmentTreatmentsFragment(CcmAssessmentTreatmentsFragment ccmAssessmentTreatmentsFragment) {
		this.ccmAssessmentTreatmentsFragment = ccmAssessmentTreatmentsFragment;
	}
}