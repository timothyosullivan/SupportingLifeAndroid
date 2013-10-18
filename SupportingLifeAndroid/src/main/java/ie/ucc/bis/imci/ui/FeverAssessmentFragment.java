package ie.ucc.bis.imci.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.assessment.model.listener.RadioGroupCoordinatorListener;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.imci.model.DynamicView;
import ie.ucc.bis.imci.model.FeverAssessmentPage;
import ie.ucc.bis.ui.custom.InputFilterMinMax;
import ie.ucc.bis.ui.custom.ToggleButtonGroupTableLayout;
import ie.ucc.bis.ui.utilities.ViewGroupUtilities;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Responsible for UI fragment to display the 
 * fever assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class FeverAssessmentFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";
    
    private static final int MIN_FEVER_DURATION = 1;
    private static final int MAX_FEVER_DURATION = 365;

    private FeverAssessmentPage feverAssessmentPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private ToggleButtonGroupTableLayout feverCustomRadioGroup;
    private RadioGroup malariaRiskRadioGroup;
    private EditText durationEditText;
    private RadioGroup feverPresentDailyRadioGroup;
    private RadioGroup measlesRadioGroup;
    private RadioGroup stiffNeckRadioGroup;
    private RadioGroup runnyNoseRadioGroup;
    private RadioGroup generalisedRashRadioGroup;
    private RadioGroup coughRadioGroup;
    private RadioGroup redEyesRadioGroup;
    private RadioGroup mouthUlcersRadioGroup;
    private RadioGroup deepMouthUlcersRadioGroup;
    private RadioGroup extensiveMouthUlcersRadioGroup;
    private RadioGroup pusDrainingRadioGroup;
    private RadioGroup corneaCloudingRadioGroup;
    private RadioGroup bulgingFontanelRadioGroup;
    private DynamicView deepMouthUlcersDynamicView;
    private DynamicView extensiveMouthUlcersDynamicView;
    private View mouthUlcersView;
    private DynamicView feverDurationDynamicView;
    private View feverView;
    private ViewGroup animatedFeverDurationView;
    private ViewGroup animatedView;
    private Boolean animatedViewInVisibleState;
    private Boolean animatedFeverViewInVisibleState;

	public static FeverAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        FeverAssessmentFragment fragment = new FeverAssessmentFragment();
        fragment.setArguments(args);
        fragment.setAnimatedViewInVisibleState(false);
        fragment.setAnimatedFeverViewInVisibleState(false);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */
    public FeverAssessmentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setFeverAssessmentPage((FeverAssessmentPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
      if (getAnimatedView().indexOfChild(getDeepMouthUlcersDynamicView().getWrappedView()) != -1) {
    	  // Animated view is visible
    	  savedInstanceState.putBoolean("animatedViewInVisibleState", true);
      }
      else {
    	  // Animated view is invisible
    	  savedInstanceState.putBoolean("animatedViewInVisibleState", false);
      }
      
      if (getAnimatedFeverDurationView().indexOfChild(getFeverDurationDynamicView().getWrappedView()) != -1) {
    	  // Animated Fever view is visible
    	  savedInstanceState.putBoolean("animatedFeverViewInVisibleState", true);
      }
      else {
    	  // Animated Fever view is invisible
    	  savedInstanceState.putBoolean("animatedFeverViewInVisibleState", false);
      }
      super.onSaveInstanceState(savedInstanceState);
    }
    
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
    	super.onViewStateRestored(savedInstanceState);
    	if (savedInstanceState != null) {
    		setAnimatedViewInVisibleState(savedInstanceState.getBoolean("animatedViewInVisibleState"));
    		setAnimatedFeverViewInVisibleState(savedInstanceState.getBoolean("animatedFeverViewInVisibleState"));
    	}

    	if (!isAnimatedViewInVisibleState()) {
    		ViewGroupUtilities.removeDynamicViews(getAnimatedView(), Arrays.asList(getDeepMouthUlcersDynamicView(), getExtensiveMouthUlcersDynamicView()));
    	}

    	if (!isAnimatedFeverViewInVisibleState()) {
    		ViewGroupUtilities.removeDynamicViews(getAnimatedFeverDurationView(), Arrays.asList(getFeverDurationDynamicView()));
    	}    
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_imci_page_fever_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getFeverAssessmentPage().getTitle());

        // configure the animated view of fever duration 
        // i.e. Fever --> Fever Duration
        configureFeverDurationAnimatedView(rootView);
                        
        // malaria risk
        setMalariaRiskRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_malaria_risk));
        getMalariaRiskRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.MALARIA_RISK_DATA_KEY));
        
        // measles
        setMeaslesRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_measles));
        getMeaslesRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.MEASLES_DATA_KEY));
        
        // stiff neck
        setStiffNeckRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_stiff_neck));
        getStiffNeckRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.STIFF_NECK_DATA_KEY));
        
        // runny nose
        setRunnyNoseRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_runny_nose));
        getRunnyNoseRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.RUNNY_NOSE_DATA_KEY));
        
        // generalised rash
        setGeneralisedRashRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_generalised_rash));
        getGeneralisedRashRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.GENERALISED_RASH_DATA_KEY));

        // cough
        setCoughRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_cough));
        getCoughRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.COUGH_DATA_KEY));
        
        // red eyes
        setRedEyesRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_red_eyes));
        getRedEyesRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.RED_EYES_DATA_KEY));
        

        // configure the animated view of mouth ulcers 
        // i.e. mouth ulcers --> deep mouth ulcers + extended mouth ulcers
        configureMouthUlcersAnimatedView(rootView);
        
        // pus draining from the eye
        setPusDrainingRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_pus_draining));
        getPusDrainingRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.PUS_DRAINING_DATA_KEY));     
                       
        // clouding of the cornea
        setCorneaCloudingRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_cornea_clouding));
        getCorneaCloudingRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.CORNEA_CLOUDING_DATA_KEY));
        
        // bulging fontanel
        setBulgingFontanelRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_bulging_fontanel));
        getBulgingFontanelRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.BULGING_FONTANEL_DATA_KEY));
                
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);
        
        return rootView;
    }

	private void configureFeverDurationAnimatedView(View rootView) {
		// Fever view
		setFeverView((View) rootView.findViewById(R.id.imci_fever_assessment_view_fever));
		
        // need to add superscript text to the degree Celsius label of a 
        // fever radio button
        RadioButton feverTempRadioButton = (RadioButton) rootView.findViewById(R.id.imci_fever_assessment_radio_fever_temperature);
        feverTempRadioButton.setText(Html.fromHtml(getText(R.string.imci_fever_assessment_radio_fever_temperature) + "<sup>o</sup>" + "C"));
                
        // fever
        setFeverCustomRadioGroup((ToggleButtonGroupTableLayout) rootView.findViewById(R.id.imci_fever_assessment_radio_fever));
        getFeverCustomRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.FEVER_DATA_KEY));
        
        // fever duration
        setDurationEditText((EditText) rootView.findViewById(R.id.imci_fever_assessment_fever_duration));
        // apply min/max data entry filtering to the 'fever duration' UI element
        getDurationEditText().setFilters(new InputFilter[] {new InputFilterMinMax(MIN_FEVER_DURATION, MAX_FEVER_DURATION)});
        
        // fever duration is a dynamic view within the UI
        setFeverDurationDynamicView(new DynamicView(rootView.findViewById(R.id.imci_fever_assessment_view_fever_duration),
        									rootView.findViewById(R.id.imci_fever_assessment_fever_duration)));
        
        // has fever been present every day
        setFeverPresentDailyRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_present_every_day));
        getFeverPresentDailyRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.FEVER_PRESENT_EVERY_DAY_DATA_KEY));
        
        // get a hold on the top level animated view
        setAnimatedFeverDurationView(((ViewGroup) rootView.findViewById(R.id.imci_fever_assessment_duration_animated_view)));
	}
    
    
	private void configureMouthUlcersAnimatedView(View rootView) {
		// mouth ulcers
        setMouthUlcersView((View) rootView.findViewById(R.id.imci_fever_assessment_view_mouth_ulcers));
                
        setMouthUlcersRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_mouth_ulcers));
        getMouthUlcersRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.MOUTH_ULCERS_DATA_KEY));
        
        // deep mouth ulcers
        setDeepMouthUlcersRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_deep_mouth_ulcers));
        getDeepMouthUlcersRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.DEEP_MOUTH_ULCERS_DATA_KEY));
        
        // deep mouth ulcers is a dynamic view within the UI
        setDeepMouthUlcersDynamicView(new DynamicView(rootView.findViewById(R.id.imci_fever_assessment_view_deep_mouth_ulcers),
        									rootView.findViewById(R.id.imci_fever_assessment_radio_deep_mouth_ulcers)));
                
        // extensive mouth ulcers
        setExtensiveMouthUlcersRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_extensive_mouth_ulcers));
        getExtensiveMouthUlcersRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.EXTENSIVE_MOUTH_ULCERS_DATA_KEY));

        // extensive mouth ulcers is a dynamic view within the UI
        setExtensiveMouthUlcersDynamicView(new DynamicView(rootView.findViewById(R.id.imci_fever_assessment_view_extensive_mouth_ulcers),
        									rootView.findViewById(R.id.imci_fever_assessment_radio_extensive_mouth_ulcers)));
        
        // get a hold on the top level animated view
        setAnimatedView(((ViewGroup) rootView.findViewById(R.id.imci_fever_assessment_animated_view)));
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

        // add listener to custom fever radio group
        addFeverDynamicViewListener();
        
        // add listener to malaria risk radio group
        getMalariaRiskRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.MALARIA_RISK_DATA_KEY));
        
        // listener to duration
        getDurationEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getFeverAssessmentPage(), 
        				FeverAssessmentPage.DURATION_DATA_KEY)); 
        
        // add listener to 'fever present every day' radio group
        getFeverPresentDailyRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.FEVER_PRESENT_EVERY_DAY_DATA_KEY));
        
        // add listener to measles radio group
        getMeaslesRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.MEASLES_DATA_KEY));

        // add listener to stiff neck radio group
        getStiffNeckRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.STIFF_NECK_DATA_KEY));    
 
        // add listener to runny nose radio group
        getRunnyNoseRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.RUNNY_NOSE_DATA_KEY));
        
        // add listener to generalised rash radio group
        getGeneralisedRashRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.GENERALISED_RASH_DATA_KEY));
        
        // add listener to cough radio group
        getCoughRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.COUGH_DATA_KEY));
        
        // add listener to red eyes radio group
        getRedEyesRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.RED_EYES_DATA_KEY));  
      
        // add dynamic view listener to mouth ulcers radio group
        addMouthUlcersDynamicViewListener();       
        
        // add listener to deep mouth ulcers radio group
        getDeepMouthUlcersRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.DEEP_MOUTH_ULCERS_DATA_KEY));
        
        // add listener to extensive mouth ulcers radio group
        getExtensiveMouthUlcersRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.EXTENSIVE_MOUTH_ULCERS_DATA_KEY));
        
        // add listener to pus draining from the eye radio group
        getPusDrainingRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.PUS_DRAINING_DATA_KEY));
        
        // add listener to clouding of the cornea radio group
        getCorneaCloudingRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.CORNEA_CLOUDING_DATA_KEY));
        
        // add listener to bulging fontanel radio group
        getBulgingFontanelRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.BULGING_FONTANEL_DATA_KEY));       
    }

	/**
	 * addFeverDynamicViewListener()
	 * 
	 * Responsible for adding a listener to the Fever view
	 * 
	 */
	private void addFeverDynamicViewListener() {
		
        // configure page and data key for radio button listener in 'custom fever toggle group'
        getFeverCustomRadioGroup().setPage(getFeverAssessmentPage());
        getFeverCustomRadioGroup().setDataKey(FeverAssessmentPage.FEVER_DATA_KEY);
		
        int indexPosition = getAnimatedFeverDurationView().indexOfChild(getFeverView()) + 1;
        
        getFeverCustomRadioGroup().configureDynamicView(getFeverDurationDynamicView(),
        				getAnimatedFeverDurationView(),
        				indexPosition);
	}   
    
	/**
	 * addMouthUlcersDynamicViewListener()
	 * 
	 * Responsible for adding a listener to the Mouth Ulcers view
	 * 
	 */
	private void addMouthUlcersDynamicViewListener() {

        getMouthUlcersRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupCoordinatorListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.MOUTH_ULCERS_DATA_KEY, 
        				Arrays.asList(getDeepMouthUlcersDynamicView(), getExtensiveMouthUlcersDynamicView()),
        				getAnimatedView(),
        				getMouthUlcersView()));
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
	 * Getter Method: getFeverAssessmentPage()
	 */
	public FeverAssessmentPage getFeverAssessmentPage() {
		return feverAssessmentPage;
	}

	/**
	 * Setter Method: setFeverAssessmentPage()
	 */
	public void setFeverAssessmentPage(FeverAssessmentPage feverAssessmentPage) {
		this.feverAssessmentPage = feverAssessmentPage;
	}

	/**
	 * Getter Method: getFeverCustomRadioGroup()
	 */	
	public ToggleButtonGroupTableLayout getFeverCustomRadioGroup() {
		return feverCustomRadioGroup;
	}

	/**
	 * Setter Method: setFeverCustomRadioGroup()
	 */	
	public void setFeverCustomRadioGroup(ToggleButtonGroupTableLayout feverCustomRadioGroup) {
		this.feverCustomRadioGroup = feverCustomRadioGroup;
	}

	/**
	 * Getter Method: getMalariaRiskRadioGroup()
	 */	
	public RadioGroup getMalariaRiskRadioGroup() {
		return malariaRiskRadioGroup;
	}

	/**
	 * Setter Method: setMalariaRiskRadioGroup()
	 */	
	public void setMalariaRiskRadioGroup(RadioGroup malariaRiskRadioGroup) {
		this.malariaRiskRadioGroup = malariaRiskRadioGroup;
	}

	/**
	 * Getter Method: getDurationEditText()
	 */		
	public EditText getDurationEditText() {
		return durationEditText;
	}

	/**
	 * Setter Method: setDurationEditText()
	 */			
	public void setDurationEditText(EditText durationEditText) {
		this.durationEditText = durationEditText;
	}

	/**
	 * Getter Method: getFeverPresentDailyRadioGroup()
	 */
	private RadioGroup getFeverPresentDailyRadioGroup() {
		return feverPresentDailyRadioGroup;
	}

	/**
	 * Setter Method: setFeverPresentDailyRadioGroup()
	 */	
	private void setFeverPresentDailyRadioGroup(RadioGroup feverPresentDailyRadioGroup) {
		this.feverPresentDailyRadioGroup = feverPresentDailyRadioGroup;
	}

	/**
	 * Getter Method: getMeaslesRadioGroup()
	 */
	private RadioGroup getMeaslesRadioGroup() {
		return measlesRadioGroup;
	}

	/**
	 * Setter Method: setMeaslesRadioGroup()
	 */	
	private void setMeaslesRadioGroup(RadioGroup measlesRadioGroup) {
		this.measlesRadioGroup = measlesRadioGroup;
	}

	/**
	 * Getter Method: getStiffNeckRadioGroup()
	 */
	private RadioGroup getStiffNeckRadioGroup() {
		return stiffNeckRadioGroup;
	}

	/**
	 * Setter Method: setStiffNeckRadioGroup()
	 */	
	private void setStiffNeckRadioGroup(RadioGroup stiffNeckRadioGroup) {
		this.stiffNeckRadioGroup = stiffNeckRadioGroup;
	}

	/**
	 * Getter Method: getRunnyNoseRadioGroup()
	 */
	private RadioGroup getRunnyNoseRadioGroup() {
		return runnyNoseRadioGroup;
	}

	/**
	 * Setter Method: setRunnyNoseRadioGroup()
	 */	
	private void setRunnyNoseRadioGroup(RadioGroup runnyNoseRadioGroup) {
		this.runnyNoseRadioGroup = runnyNoseRadioGroup;
	}

	/**
	 * Getter Method: getGeneralisedRashRadioGroup()
	 */
	private RadioGroup getGeneralisedRashRadioGroup() {
		return generalisedRashRadioGroup;
	}

	/**
	 * Setter Method: setGeneralisedRashRadioGroup()
	 */	
	private void setGeneralisedRashRadioGroup(RadioGroup generalisedRashRadioGroup) {
		this.generalisedRashRadioGroup = generalisedRashRadioGroup;
	}

	/**
	 * Getter Method: getCoughRadioGroup()
	 */
	private RadioGroup getCoughRadioGroup() {
		return coughRadioGroup;
	}

	/**
	 * Setter Method: setCoughRadioGroup()
	 */	
	private void setCoughRadioGroup(RadioGroup coughRadioGroup) {
		this.coughRadioGroup = coughRadioGroup;
	}

	/**
	 * Getter Method: getRedEyesRadioGroup()
	 */
	private RadioGroup getRedEyesRadioGroup() {
		return redEyesRadioGroup;
	}

	/**
	 * Setter Method: setRedEyesRadioGroup()
	 */	
	private void setRedEyesRadioGroup(RadioGroup redEyesRadioGroup) {
		this.redEyesRadioGroup = redEyesRadioGroup;
	}

	/**
	 * Getter Method: getMouthUlcersRadioGroup()
	 */
	private RadioGroup getMouthUlcersRadioGroup() {
		return mouthUlcersRadioGroup;
	}

	/**
	 * Setter Method: setMouthUlcersRadioGroup()
	 */	
	private void setMouthUlcersRadioGroup(RadioGroup mouthUlcersRadioGroup) {
		this.mouthUlcersRadioGroup = mouthUlcersRadioGroup;
	}

	/**
	 * Getter Method: getDeepMouthUlcersRadioGroup()
	 */
	private RadioGroup getDeepMouthUlcersRadioGroup() {
		return deepMouthUlcersRadioGroup;
	}

	/**
	 * Setter Method: setDeepMouthUlcersRadioGroup()
	 */	
	private void setDeepMouthUlcersRadioGroup(RadioGroup deepMouthUlcersRadioGroup) {
		this.deepMouthUlcersRadioGroup = deepMouthUlcersRadioGroup;
	}
	
	/**
	 * Getter Method: getExtensiveMouthUlcersRadioGroup()
	 */
	private RadioGroup getExtensiveMouthUlcersRadioGroup() {
		return extensiveMouthUlcersRadioGroup;
	}

	/**
	 * Setter Method: setExtensiveMouthUlcersRadioGroup()
	 */	
	private void setExtensiveMouthUlcersRadioGroup(RadioGroup extensiveMouthUlcersRadioGroup) {
		this.extensiveMouthUlcersRadioGroup = extensiveMouthUlcersRadioGroup;
	}

	/**
	 * Getter Method: getPusDrainingRadioGroup()
	 */
	private RadioGroup getPusDrainingRadioGroup() {
		return pusDrainingRadioGroup;
	}

	/**
	 * Setter Method: setPusDrainingRadioGroup()
	 */	
	private void setPusDrainingRadioGroup(RadioGroup pusDrainingRadioGroup) {
		this.pusDrainingRadioGroup = pusDrainingRadioGroup;
	}

	/**
	 * Getter Method: getCorneaCloudingRadioGroup()
	 */
	private RadioGroup getCorneaCloudingRadioGroup() {
		return corneaCloudingRadioGroup;
	}

	/**
	 * Setter Method: setCorneaCloudingRadioGroup()
	 */	
	private void setCorneaCloudingRadioGroup(RadioGroup corneaCloudingRadioGroup) {
		this.corneaCloudingRadioGroup = corneaCloudingRadioGroup;
	}

	/**
	 * Getter Method: getBulgingFontanelRadioGroup()
	 */
	private RadioGroup getBulgingFontanelRadioGroup() {
		return bulgingFontanelRadioGroup;
	}

	/**
	 * Setter Method: setBulgingFontanelRadioGroup()
	 */	
	private void setBulgingFontanelRadioGroup(RadioGroup bulgingFontanelRadioGroup) {
		this.bulgingFontanelRadioGroup = bulgingFontanelRadioGroup;
	}
	
	/**
	 * Getter Method: getDeepMouthUlcersDynamicView()
	 */
    private DynamicView getDeepMouthUlcersDynamicView() {
		return deepMouthUlcersDynamicView;
	}

	/**
	 * Setter Method: setDeepMouthUlcersDynamicView()
	 */	
	private void setDeepMouthUlcersDynamicView(DynamicView deepMouthUlcersDynamicView) {
		this.deepMouthUlcersDynamicView = deepMouthUlcersDynamicView;
	}

	/**
	 * Getter Method: getExtensiveMouthUlcersDynamicView()
	 */
	private DynamicView getExtensiveMouthUlcersDynamicView() {
		return extensiveMouthUlcersDynamicView;
	}

	/**
	 * Setter Method: setExtensiveMouthUlcersDynamicView()
	 */	
	private void setExtensiveMouthUlcersDynamicView(DynamicView extensiveMouthUlcersDynamicView) {
		this.extensiveMouthUlcersDynamicView = extensiveMouthUlcersDynamicView;
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
	 * Getter Method: getAnimatedFeverDurationView()
	 */
	public ViewGroup getAnimatedFeverDurationView() {
		return animatedFeverDurationView;
	}

	/**
	 * Setter Method: setAnimatedFeverDurationView()
	 */	
	public void setAnimatedFeverDurationView(ViewGroup animatedFeverDurationView) {
		this.animatedFeverDurationView = animatedFeverDurationView;
	}

	/**
	 * Getter Method: getMouthUlcersView()
	 */
	private View getMouthUlcersView() {
		return mouthUlcersView;
	}

	/**
	 * Setter Method: setMouthUlcersView()
	 */	
	private void setMouthUlcersView(View mouthUlcersView) {
		this.mouthUlcersView = mouthUlcersView;
	}

	/**
	 * Getter Method: getFeverDurationDynamicView()
	 */
	public DynamicView getFeverDurationDynamicView() {
		return feverDurationDynamicView;
	}

	/**
	 * Setter Method: setFeverDurationDynamicView()
	 */	
	public void setFeverDurationDynamicView(DynamicView feverDurationDynamicView) {
		this.feverDurationDynamicView = feverDurationDynamicView;
	}

	/**
	 * Getter Method: getFeverView()
	 */
	public View getFeverView() {
		return feverView;
	}

	/**
	 * Setter Method: setFeverView()
	 */	
	public void setFeverView(View feverView) {
		this.feverView = feverView;
	}

	/**
	 * Getter Method: isAnimatedFeverViewInVisibleState()
	 */
	public Boolean isAnimatedFeverViewInVisibleState() {
		return animatedFeverViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedFeverViewInVisibleState()
	 */
	public void setAnimatedFeverViewInVisibleState(Boolean animatedFeverViewInVisibleState) {
		this.animatedFeverViewInVisibleState = animatedFeverViewInVisibleState;
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