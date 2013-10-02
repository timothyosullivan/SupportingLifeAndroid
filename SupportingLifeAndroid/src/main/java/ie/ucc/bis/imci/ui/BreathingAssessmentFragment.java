package ie.ucc.bis.imci.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.imci.model.BreathingAssessmentPage;
import ie.ucc.bis.imci.model.DynamicView;
import ie.ucc.bis.imci.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.imci.model.listener.RadioGroupCoordinatorListener;
import ie.ucc.bis.imci.model.listener.RadioGroupListener;
import ie.ucc.bis.ui.custom.InputFilterMinMax;
import ie.ucc.bis.ui.utilities.ViewGroupUtilities;

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
 * Responsible for UI fragment to display breathing / 
 * cough assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class BreathingAssessmentFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";
    
    private static final int MIN_BREATHS_PER_MINUTE = 1;
    private static final int MAX_BREATHS_PER_MINUTE = 100;
    private static final int MIN_COUGH_DURATION = 1;
    private static final int MAX_COUGH_DURATION = 365;

    private BreathingAssessmentPage breathingAssessmentPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private RadioGroup coughDifficultBreathingRadioGroup;
    private EditText coughDurationEditText;
    private EditText breathsPerMinuteEditText;
    private RadioGroup chestIndrawingRadioGroup;
    private RadioGroup stridorRadioGroup;
    private DynamicView coughDifficultBreathingDurationDynamicView;
    private View coughDifficultBreathingView;
    private ViewGroup animatedView;
    private Boolean animatedViewInVisibleState;

    
    public static BreathingAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        BreathingAssessmentFragment fragment = new BreathingAssessmentFragment();
        fragment.setArguments(args);
        fragment.setAnimatedViewInVisibleState(false);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */        
    public BreathingAssessmentFragment() {}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	if (getAnimatedView().indexOfChild(getCoughDifficultBreathingDurationDynamicView().getWrappedView()) != -1) {
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
    		ViewGroupUtilities.removeDynamicViews(getAnimatedView(), Arrays.asList(getCoughDifficultBreathingDurationDynamicView()));
    	}
    }
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setBreathingAssessmentPage((BreathingAssessmentPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_page_cough_breathing_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getBreathingAssessmentPage().getTitle());

        // configure the animated view of cough / difficult breathing duration 
        // i.e. cough / difficult breathing --> cough / difficult breathing duration
        configureCoughDifficultBreathingDurationAnimatedView(rootView);
                
        // breaths per minute
        setBreathsPerMinuteEditText((EditText) rootView.findViewById(R.id.breathing_assessment_breaths_per_minute));
        getBreathsPerMinuteEditText().setText(getBreathingAssessmentPage().getPageData().getString(BreathingAssessmentPage.BREATHS_PER_MINUTE_DATA_KEY));
        // apply min/max data entry filtering to the 'breaths per minute' UI element
        getBreathsPerMinuteEditText().setFilters(new InputFilter[] {new InputFilterMinMax(MIN_BREATHS_PER_MINUTE, MAX_BREATHS_PER_MINUTE)});
        
        // chest indrawing
        setChestIndrawingRadioGroup((RadioGroup) rootView.findViewById(R.id.breathing_assessment_radio_chest_indrawing));
        getChestIndrawingRadioGroup().check(getBreathingAssessmentPage()
        		.getPageData().getInt(BreathingAssessmentPage.CHEST_INDRAWING_DATA_KEY));
        
        // stridor
        setStridorRadioGroup((RadioGroup) rootView.findViewById(R.id.breathing_assessment_radio_stridor));
        getStridorRadioGroup().check(getBreathingAssessmentPage()
        		.getPageData().getInt(BreathingAssessmentPage.STRIDOR_DATA_KEY));
        
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);
        
        return rootView;
    }
    
	private void configureCoughDifficultBreathingDurationAnimatedView(View rootView) {
		// cough / difficult breathing view
		setCoughDifficultBreathingView((View) rootView.findViewById(R.id.breathing_assessment_view_cough_difficult_breathing));
		
        // does the child have cough or difficult breathing?
        setCoughDifficultBreathingRadioGroup((RadioGroup) rootView.findViewById(R.id.breathing_assessment_radio_cough_difficult_breathing));
        getCoughDifficultBreathingRadioGroup().check(getBreathingAssessmentPage()
        		.getPageData().getInt(BreathingAssessmentPage.COUGH_DIFFICULT_BREATHING_DATA_KEY));
        
        // for how long? (days) - cough duration
        setCoughDurationEditText((EditText) rootView.findViewById(R.id.breathing_assessment_cough_duration));
        getCoughDurationEditText().setText(getBreathingAssessmentPage().getPageData().getString(BreathingAssessmentPage.COUGH_DURATION_DATA_KEY));
        // apply min/max data entry filtering to the 'cough duration' UI element
        getCoughDurationEditText().setFilters(new InputFilter[] {new InputFilterMinMax(MIN_COUGH_DURATION, MAX_COUGH_DURATION)});
		       
        //  cough / difficult breathing duration is a dynamic view within the UI
        setCoughDifficultBreathingDurationDynamicView(new DynamicView(rootView.findViewById(R.id.breathing_assessment_view_cough_duration),
        									rootView.findViewById(R.id.breathing_assessment_cough_duration)));
                
        // get a hold on the top level animated view
        setAnimatedView(((ViewGroup) rootView.findViewById(R.id.breathing_assessment_cough_difficult_breathing_animated_view)));
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

        // add dynamic view listener to cough difficult breathing radio group
        addCoughDifficultBreathingDynamicViewListener();  
        
        getCoughDurationEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getBreathingAssessmentPage(), 
        				BreathingAssessmentPage.COUGH_DURATION_DATA_KEY));
        
        getBreathsPerMinuteEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getBreathingAssessmentPage(), 
        				BreathingAssessmentPage.BREATHS_PER_MINUTE_DATA_KEY));
        
        getChestIndrawingRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getBreathingAssessmentPage(),
        				BreathingAssessmentPage.CHEST_INDRAWING_DATA_KEY));
        
        getStridorRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getBreathingAssessmentPage(),
        				BreathingAssessmentPage.STRIDOR_DATA_KEY));
    }
    
	/**
	 * addCoughDifficultBreathingDynamicViewListener()
	 * 
	 * Responsible for adding a listener to the Cough Difficult Breathing view
	 * 
	 */
	private void addCoughDifficultBreathingDynamicViewListener() {
        int indexPosition = getAnimatedView().indexOfChild(getCoughDifficultBreathingView()) + 1;
        
        getCoughDifficultBreathingRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupCoordinatorListener(getBreathingAssessmentPage(),
        				BreathingAssessmentPage.COUGH_DIFFICULT_BREATHING_DATA_KEY, 
        				Arrays.asList(getCoughDifficultBreathingDurationDynamicView()),
        				getAnimatedView(),
        				indexPosition));
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
	 * Getter Method: getBreathingAssessmentPage()
	 */	
	public BreathingAssessmentPage getBreathingAssessmentPage() {
		return breathingAssessmentPage;
	}

	/**
	 * Setter Method: setBreathingAssessmentPage()
	 */
	public void setBreathingAssessmentPage(
			BreathingAssessmentPage breathingAssessmentPage) {
		this.breathingAssessmentPage = breathingAssessmentPage;
	}

	/**
	 * Getter Method: getCoughDifficultBreathingRadioGroup()
	 */		
	public RadioGroup getCoughDifficultBreathingRadioGroup() {
		return coughDifficultBreathingRadioGroup;
	}

	/**
	 * Setter Method: setCoughDifficultBreathingRadioGroup()
	 */	
	public void setCoughDifficultBreathingRadioGroup(
			RadioGroup coughDifficultBreathingRadioGroup) {
		this.coughDifficultBreathingRadioGroup = coughDifficultBreathingRadioGroup;
	}

	/**
	 * Getter Method: getChestIndrawingRadioGroup()
	 */		
	public RadioGroup getChestIndrawingRadioGroup() {
		return chestIndrawingRadioGroup;
	}

	/**
	 * Setter Method: setChestIndrawingRadioGroup()
	 */	
	public void setChestIndrawingRadioGroup(RadioGroup chestIndrawingRadioGroup) {
		this.chestIndrawingRadioGroup = chestIndrawingRadioGroup;
	}

	/**
	 * Getter Method: getStridorRadioGroup()
	 */	
	private RadioGroup getStridorRadioGroup() {
		return stridorRadioGroup;
	}

	/**
	 * Setter Method: setStridorRadioGroup()
	 */	
	private void setStridorRadioGroup(RadioGroup stridorRadioGroup) {
		this.stridorRadioGroup = stridorRadioGroup;
	}

	/**
	 * Getter Method: getCoughDurationEditText()
	 */	
	private EditText getCoughDurationEditText() {
		return coughDurationEditText;
	}

	/**
	 * Setter Method: setCoughDurationEditText()
	 */	
	private void setCoughDurationEditText(EditText coughDurationEditText) {
		this.coughDurationEditText = coughDurationEditText;
	}

	/**
	 * Getter Method: getBreathsPerMinuteEditText()
	 */	
	private EditText getBreathsPerMinuteEditText() {
		return breathsPerMinuteEditText;
	}

	/**
	 * Setter Method: setBreathsPerMinuteEditText()
	 */	
	private void setBreathsPerMinuteEditText(EditText breathsPerMinuteEditText) {
		this.breathsPerMinuteEditText = breathsPerMinuteEditText;
	}

	/**
	 * Getter Method: getCoughDifficultBreathingDurationDynamicView()
	 */	
	public DynamicView getCoughDifficultBreathingDurationDynamicView() {
		return coughDifficultBreathingDurationDynamicView;
	}

	/**
	 * Setter Method: setCoughDifficultBreathingDurationDynamicView()
	 */	
	public void setCoughDifficultBreathingDurationDynamicView(DynamicView coughDifficultBreathingDurationDynamicView) {
		this.coughDifficultBreathingDurationDynamicView = coughDifficultBreathingDurationDynamicView;
	}

	/**
	 * Getter Method: getCoughDifficultBreathingView()
	 */	
	public View getCoughDifficultBreathingView() {
		return coughDifficultBreathingView;
	}

	/**
	 * Setter Method: setCoughDifficultBreathingView()
	 */	
	public void setCoughDifficultBreathingView(View coughDifficultBreathingView) {
		this.coughDifficultBreathingView = coughDifficultBreathingView;
	}	
	
	/**
	 * Getter Method: getAnimatedView()
	 */	
	public ViewGroup getAnimatedView() {
		return animatedView;
	}

	/**
	 * Setter Method: setAnimatedView()
	 */	
	public void setAnimatedView(ViewGroup animatedView) {
		this.animatedView = animatedView;
	}

	/**
	 * Getter Method: isAnimatedViewInVisibleState()
	 */	
	public Boolean isAnimatedViewInVisibleState() {
		return animatedViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedViewInVisibleState()
	 */	
	public void setAnimatedViewInVisibleState(Boolean animatedViewInVisibleState) {
		this.animatedViewInVisibleState = animatedViewInVisibleState;
	}
}
