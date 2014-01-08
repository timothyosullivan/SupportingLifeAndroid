package ie.ucc.bis.supportinglife.assessment.imci.ui;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.imci.model.DynamicView;
import ie.ucc.bis.supportinglife.assessment.imci.model.EarAssessmentPage;
import ie.ucc.bis.supportinglife.assessment.model.listener.DynamicViewListenerUtilities;
import ie.ucc.bis.supportinglife.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.supportinglife.ui.custom.InputFilterMinMax;
import ie.ucc.bis.supportinglife.ui.utilities.ViewGroupUtilities;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Responsible for UI fragment to display the 
 * ear assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class EarAssessmentFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";
    
    private static final int MIN_EAR_DISCHARGE_DURATION = 1;
    private static final int MAX_EAR_DISCHARGE_DURATION = 365;

    private EarAssessmentPage earAssessmentPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private RadioGroup earProblemRadioGroup;
    private RadioGroup earPainRadioGroup;
    private RadioGroup earDischargeRadioGroup;
    private EditText earDischargeDurationEditText;
    private RadioGroup tenderSwellingRadioGroup;
    private DynamicView earDischargeDurationDynamicView;
    private View earDischargeView;
    private ViewGroup animatedView;
    private Boolean animatedViewInVisibleState;
    
    public static EarAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        EarAssessmentFragment fragment = new EarAssessmentFragment();
        fragment.setArguments(args);
        fragment.setAnimatedViewInVisibleState(false);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */
    public EarAssessmentFragment() {}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
      if (getAnimatedView().indexOfChild(getEarDischargeDurationDynamicView().getWrappedView()) != -1) {
    	  // Animated view is visible
    	  savedInstanceState.putBoolean("animatedViewInVisibleState", true);
      }
      else {
    	  // Animated view is invisible
    	  savedInstanceState.putBoolean("animatedViewInVisibleState", false);
      }
      super.onSaveInstanceState(savedInstanceState);
    }
    
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
      super.onViewStateRestored(savedInstanceState);
      if (savedInstanceState != null) {
    	  setAnimatedViewInVisibleState(savedInstanceState.getBoolean("animatedViewInVisibleState"));
      }
      
      if (!isAnimatedViewInVisibleState()) {
	        ViewGroupUtilities.removeDynamicViews(getAnimatedView(), Arrays.asList(getEarDischargeDurationDynamicView()));
      }
      
    }
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	setEarAssessmentPage((EarAssessmentPage) getPageFragmentCallbacks().getPage(getPageKey()));
    	
        View rootView = inflater.inflate(R.layout.fragment_imci_page_ear_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getEarAssessmentPage().getTitle());
                
        // ear problem
        setEarProblemRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_ear_assessment_radio_ear_problem));
        getEarProblemRadioGroup().check(getEarAssessmentPage()
        		.getPageData().getInt(EarAssessmentPage.EAR_PROBLEM_DATA_KEY));
        
        // ear pain
        setEarPainRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_ear_assessment_radio_ear_pain));
        getEarPainRadioGroup().check(getEarAssessmentPage()
        		.getPageData().getInt(EarAssessmentPage.EAR_PAIN_DATA_KEY));
        
        // configure the animated view of ear discharge 
        // i.e. ear discharge --> ear discharge duration
        configureEarDischargeAnimatedView(rootView);
        
        // tender swelling
        setTenderSwellingRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_ear_assessment_radio_tender_swelling));
        getTenderSwellingRadioGroup().check(getEarAssessmentPage()
        		.getPageData().getInt(EarAssessmentPage.TENDER_SWELLING_DATA_KEY));
        
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);
        
        return rootView;
    }
    
	private void configureEarDischargeAnimatedView(View rootView) {
		// ear discharge view
		setEarDischargeView((View) rootView.findViewById(R.id.imci_ear_assessment_view_ear_discharge));
		
        // ear discharge radio group
        setEarDischargeRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_ear_assessment_radio_ear_discharge));
        getEarDischargeRadioGroup().check(getEarAssessmentPage()
        		.getPageData().getInt(EarAssessmentPage.EAR_DISCHARGE_DATA_KEY));
        
        // ear discharge duration
        setEarDischargeDurationEditText((EditText) rootView.findViewById(R.id.imci_ear_assessment_ear_discharge_duration));
        getEarDischargeDurationEditText().setText(getEarAssessmentPage().getPageData().getString(EarAssessmentPage.EAR_DISCHARGE_DURATION_DATA_KEY));
        // apply min/max data entry filtering to the 'ear discharge duration' UI element
        getEarDischargeDurationEditText().setFilters(new InputFilter[] {new InputFilterMinMax(MIN_EAR_DISCHARGE_DURATION, MAX_EAR_DISCHARGE_DURATION)});
		       
        //  ear discharge duration is a dynamic view within the UI
        setEarDischargeDurationDynamicView(new DynamicView(rootView.findViewById(R.id.imci_ear_assessment_view_ear_discharge_duration),
        									rootView.findViewById(R.id.imci_ear_assessment_ear_discharge_duration)));
                
        // get a hold on the top level animated view
        setAnimatedView(((ViewGroup) rootView.findViewById(R.id.imci_ear_assessment_discharge_animated_view)));
	}


	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }
        
        setPageFragmentCallbacks((PageFragmentCallbacks) activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setPageFragmentCallbacks(null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // add listener to ear problem radio group
        getEarProblemRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getEarAssessmentPage(),
        				EarAssessmentPage.EAR_PROBLEM_DATA_KEY));
        
        // add listener to ear pain radio group
        getEarPainRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getEarAssessmentPage(),
        				EarAssessmentPage.EAR_PAIN_DATA_KEY));
        
        // add dynamic view listener to ear discharge radio group
        addEarDischargeDynamicViewListener();  
                
        // add listener to tender swelling radio group
        getTenderSwellingRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getEarAssessmentPage(),
        				EarAssessmentPage.TENDER_SWELLING_DATA_KEY));
    }
    
	/**
	 * addEarDischargeDynamicViewListener()
	 * 
	 * Responsible for adding a listener to the Ear Discharge view
	 * 
	 */
	private void addEarDischargeDynamicViewListener() {
		// add dynamic view listener to ear discharge radio group
		DynamicViewListenerUtilities.addGenericDynamicViewListeners(getEarDischargeView(), 
				getEarDischargeDurationDynamicView(),
				getAnimatedView(),
				getEarDischargeRadioGroup(), getEarDischargeDurationEditText(),
				EarAssessmentPage.EAR_DISCHARGE_DATA_KEY,
				EarAssessmentPage.EAR_DISCHARGE_DURATION_DATA_KEY,
				getResources(), getEarAssessmentPage());
	}    
    

	/**
	 * Getter Method: getPageFragmentCallbacks()
	 */
	public PageFragmentCallbacks getPageFragmentCallbacks() {
		return pageFragmentCallbacks;
	}

	/**
	 * Setter Method: setPageFragmentCallbacks()
	 */
	public void setPageFragmentCallbacks(PageFragmentCallbacks pageFragmentCallbacks) {
		this.pageFragmentCallbacks = pageFragmentCallbacks;
	}
	
	/**
	 * Getter Method: getPageKey()
	 */	
	public String getPageKey() {
		return pageKey;
	}

	/**
	 * Setter Method: setPageKey()
	 */	
	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}

	/**
	 * Getter Method: getEarAssessmentPage()
	 */			
	public EarAssessmentPage getEarAssessmentPage() {
		return earAssessmentPage;
	}
	
	/**
	 * Setter Method: setEarAssessmentPage()
	 */
	public void setEarAssessmentPage(EarAssessmentPage earAssessmentPage) {
		this.earAssessmentPage = earAssessmentPage;
	}

	/**
	 * Getter Method: getEarProblemRadioGroup()
	 */		
	public RadioGroup getEarProblemRadioGroup() {
		return earProblemRadioGroup;
	}

	/**
	 * Setter Method: setEarProblemRadioGroup()
	 */
	public void setEarProblemRadioGroup(RadioGroup earProblemRadioGroup) {
		this.earProblemRadioGroup = earProblemRadioGroup;
	}

	/**
	 * Getter Method: getEarPainRadioGroup()
	 */		
	public RadioGroup getEarPainRadioGroup() {
		return earPainRadioGroup;
	}

	/**
	 * Setter Method: setEarPainRadioGroup()
	 */
	public void setEarPainRadioGroup(RadioGroup earPainRadioGroup) {
		this.earPainRadioGroup = earPainRadioGroup;
	}

	/**
	 * Getter Method: getEarDischargeRadioGroup()
	 */			
	public RadioGroup getEarDischargeRadioGroup() {
		return earDischargeRadioGroup;
	}

	/**
	 * Setter Method: setEarDischargeRadioGroup()
	 */
	public void setEarDischargeRadioGroup(RadioGroup earDischargeRadioGroup) {
		this.earDischargeRadioGroup = earDischargeRadioGroup;
	}

	/**
	 * Getter Method: getEarDischargeDurationEditText()
	 */	
	private EditText getEarDischargeDurationEditText() {
		return earDischargeDurationEditText;
	}

	/**
	 * Setter Method: setEarDischargeDurationEditText()
	 */
	private void setEarDischargeDurationEditText(EditText earDischargeDurationEditText) {
		this.earDischargeDurationEditText = earDischargeDurationEditText;
	}

	/**
	 * Getter Method: getTenderSwellingRadioGroup()
	 */	
	private RadioGroup getTenderSwellingRadioGroup() {
		return tenderSwellingRadioGroup;
	}

	/**
	 * Setter Method: setTenderSwellingRadioGroup()
	 */
	private void setTenderSwellingRadioGroup(RadioGroup tenderSwellingRadioGroup) {
		this.tenderSwellingRadioGroup = tenderSwellingRadioGroup;
	}

	/**
	 * Getter Method: getEarDischargeDurationDynamicView()
	 */	
	private DynamicView getEarDischargeDurationDynamicView() {
		return earDischargeDurationDynamicView;
	}

	/**
	 * Setter Method: setEarDischargeDurationDynamicView()
	 */
	private void setEarDischargeDurationDynamicView(DynamicView earDischargeDurationDynamicView) {
		this.earDischargeDurationDynamicView = earDischargeDurationDynamicView;
	}

	/**
	 * Getter Method: getEarDischargeView()
	 */	
	private View getEarDischargeView() {
		return earDischargeView;
	}

	/**
	 * Setter Method: setEarDischargeView()
	 */
	private void setEarDischargeView(View earDischargeView) {
		this.earDischargeView = earDischargeView;
	}

	/**
	 * Getter Method: getAnimatedView()
	 */	
	private ViewGroup getAnimatedView() {
		return animatedView;
	}

	/**
	 * Setter Method: setAnimatedView()
	 */
	private void setAnimatedView(ViewGroup animatedView) {
		this.animatedView = animatedView;
	}

	/**
	 * Getter Method: isAnimatedViewInVisibleState()
	 */
	private Boolean isAnimatedViewInVisibleState() {
		return animatedViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedViewInVisibleState()
	 */
	private void setAnimatedViewInVisibleState(Boolean animatedViewInVisibleState) {
		this.animatedViewInVisibleState = animatedViewInVisibleState;
	}
}
