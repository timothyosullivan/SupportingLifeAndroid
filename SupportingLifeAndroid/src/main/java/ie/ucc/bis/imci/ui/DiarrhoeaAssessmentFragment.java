package ie.ucc.bis.imci.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.AbstractModel;
import ie.ucc.bis.assessment.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.assessment.model.listener.RadioGroupCoordinatorListener;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.imci.model.ImciAssessmentModel;
import ie.ucc.bis.imci.model.DiarrhoeaAssessmentPage;
import ie.ucc.bis.imci.model.DynamicView;
import ie.ucc.bis.imci.model.GeneralDangerSignsPage;
import ie.ucc.bis.ui.custom.InputFilterMinMax;
import ie.ucc.bis.ui.custom.ToggleButtonGroupTableLayout;
import ie.ucc.bis.ui.utilities.RadioGroupUtilities;
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
 * Responsible for UI fragment to display the 
 * diarrhoea assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class DiarrhoeaAssessmentFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";
    
    private static final int MIN_DIARRHOEA_DURATION = 1;
    private static final int MAX_DIARRHOEA_DURATION = 365;

    private AbstractModel wizardModel;
    
    private DiarrhoeaAssessmentPage diarrhoeaAssessmentPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private RadioGroup diarrhoeaRadioGroup;
    private EditText diarrhoeaDurationEditText;
    private RadioGroup bloodStoolsRadioGroup;
    private RadioGroup sunkenEyesRadioGroup;
    private RadioGroup lethargicUnconsciousRadioGroup;
    private RadioGroup restlessIrritableRadioGroup;
    private RadioGroup choleraInAreaRadioGroup;
    private ToggleButtonGroupTableLayout childFluidRadioGroup;
    private ToggleButtonGroupTableLayout skinPinchRadioGroup;
    private DynamicView diarrhoeaDurationDynamicView;
    private View diarrhoeaView;
    private ViewGroup animatedView;
    private Boolean animatedViewInVisibleState;
    
    public static DiarrhoeaAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        DiarrhoeaAssessmentFragment fragment = new DiarrhoeaAssessmentFragment();
        fragment.setArguments(args);
        fragment.setAnimatedViewInVisibleState(false);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */        
    public DiarrhoeaAssessmentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setDiarrhoeaAssessmentPage((DiarrhoeaAssessmentPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	if (getAnimatedView().indexOfChild(getDiarrhoeaDurationDynamicView().getWrappedView()) != -1) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_imci_page_diarrhoea_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getDiarrhoeaAssessmentPage().getTitle());

        // configure the animated view of diarrhoea duration 
        // i.e. Diarrhoea --> diarrhoea duration
        configureDiarrhoeaDurationAnimatedView(rootView);
               
        // blood in the stools
        setBloodStoolsRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_diarrhoea_assessment_radio_blood_stools));
        getBloodStoolsRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.BLOOD_STOOLS_DATA_KEY));
        
        // sunken eyes
        setSunkenEyesRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_diarrhoea_assessment_radio_sunken_eyes));
        getSunkenEyesRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.SUNKEN_EYES_DATA_KEY));
        
        // lethargic or unconscious - ** This is fed from the 'General Danger Signs' Page / review item
        // will be set via onViewStateRestored(..) callback method
        setLethargicUnconsciousRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_diarrhoea_assessment_radio_lethargic_or_unconscious));
        // disable user interaction with this radio group selection
        RadioGroupUtilities.toggleRadioButtonsEnabledState(getLethargicUnconsciousRadioGroup(), false);
      
        // restless / irritable
        setRestlessIrritableRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_diarrhoea_assessment_radio_restless_irritable));
        getRestlessIrritableRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.RESTLESS_IRRITABLE_DATA_KEY));
 
        // cholera in area
        setCholeraInAreaRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_diarrhoea_assessment_radio_cholera_in_area));
        getCholeraInAreaRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.CHOLERA_IN_AREA_DATA_KEY));
        
        // offer the child fluid
        setChildFluidRadioGroup((ToggleButtonGroupTableLayout) rootView.findViewById(R.id.imci_diarrhoea_assessment_radio_child_fluid));
        getChildFluidRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.CHILD_FLUID_DATA_KEY));
       
        // skin pinch
        setSkinPinchRadioGroup((ToggleButtonGroupTableLayout) rootView.findViewById(R.id.imci_diarrhoea_assessment_radio_skin_pinch));
        getSkinPinchRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.SKIN_PINCH_DATA_KEY));
        
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);
        
        return rootView;
    }
    
	private void configureDiarrhoeaDurationAnimatedView(View rootView) {
		// diarrhoea view
		setDiarrhoeaView((View) rootView.findViewById(R.id.imci_diarrhoea_assessment_view_diarrhoea));
		
        // diarrhoea
        setDiarrhoeaRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_diarrhoea_assessment_radio_diarrhoea));
        getDiarrhoeaRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.DIARRHOEA_DATA_KEY));
        
        // for how long? (days) - diarrhoea duration
        setDiarrhoeaDurationEditText((EditText) rootView.findViewById(R.id.imci_diarrhoea_assessment_diarrhoea_duration));
        getDiarrhoeaDurationEditText().setText(getDiarrhoeaAssessmentPage().getPageData().getString(DiarrhoeaAssessmentPage.DIARRHOEA_DURATION_DATA_KEY));
        // apply min/max data entry filtering to the 'diarrhoea duration' UI element
        getDiarrhoeaDurationEditText().setFilters(new InputFilter[] {new InputFilterMinMax(MIN_DIARRHOEA_DURATION, MAX_DIARRHOEA_DURATION)});
		       
        // diarrhoea duration is a dynamic view within the UI
        setDiarrhoeaDurationDynamicView(new DynamicView(rootView.findViewById(R.id.imci_diarrhoea_assessment_view_diarrhoea_duration),
        									rootView.findViewById(R.id.imci_diarrhoea_assessment_diarrhoea_duration)));
                
        // get a hold on the top level animated view
        setAnimatedView(((ViewGroup) rootView.findViewById(R.id.imci_diarrhoea_assessment_diarrhoea_animated_view)));
	}
    
	/**
	 * Method: configureLethargicUnconsciousRadioGroup
	 * 
	 * Responsible for configuring the Lethargic/Unconscious Radio Group based on the
	 * 'Diarrhoea Assessment' page based on the corresponding radio group value on the 
	 * 'General Danger Signs' page
	 * 
	 */   
	private void configureLethargicUnconsciousRadioGroup() {
		// 1. retrieve the relevant page and ui element
		//    where the radio group 'lethargic or unconscious' is being referenced
        AbstractPage dangerSignsAssessmentPage = getWizardModel().findPageByKey(ImciAssessmentModel.DANGER_SIGNS_PAGE_TITLE);
        String uiReferenceElement = GeneralDangerSignsPage.LETHARGIC_OR_UNCONSCIOUS_DATA_KEY + RadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY;
        
        // 2. retrieve the textual value (yes/no) of the radio group checked option
        String radioGroupSetting = dangerSignsAssessmentPage.getPageData().getString(uiReferenceElement);
        
        if (radioGroupSetting != null) {        
	        // 3. set this value on the lethargic/unconscious radio grouping on the diarrhoea assessment page
	        if (radioGroupSetting.equals(getResources().getString(R.string.assessment_wizard_radio_yes))) {
	        	getLethargicUnconsciousRadioGroup().check(R.id.imci_diarrhoea_assessment_radio_lethargic_or_unconscious_yes);
	        }
	        else if (radioGroupSetting.equals(getResources().getString(R.string.assessment_wizard_radio_no))) {
	        	getLethargicUnconsciousRadioGroup().check(R.id.imci_diarrhoea_assessment_radio_lethargic_or_unconscious_no);
	        }
        }
	}
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }
        
        setPageFragmentCallbacks((PageFragmentCallbacks) activity);
        setWizardModel(getPageFragmentCallbacks().getWizardModel());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setPageFragmentCallbacks(null);
    }
    
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        
    	if (savedInstanceState != null) {
    		setAnimatedViewInVisibleState(savedInstanceState.getBoolean("animatedViewInVisibleState"));
    	}
    	if (!isAnimatedViewInVisibleState()) {
    		ViewGroupUtilities.removeDynamicViews(getAnimatedView(), Arrays.asList(getDiarrhoeaDurationDynamicView()));
    	}
        
        configureLethargicUnconsciousRadioGroup();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // add dynamic view listener to diarrhoea radio group
        addDiarrhoeaDynamicViewListener();
        
        // for how long? (days) - diarrhoea duration
        getDiarrhoeaDurationEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getDiarrhoeaAssessmentPage(), 
        				DiarrhoeaAssessmentPage.DIARRHOEA_DURATION_DATA_KEY));
        
        // blood in the stools
        getBloodStoolsRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.BLOOD_STOOLS_DATA_KEY));
        
        // sunken eyes
        getSunkenEyesRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.SUNKEN_EYES_DATA_KEY));
        
        // lethargic or unconscious
        getLethargicUnconsciousRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.LETHARGIC_OR_UNCONSCIOUS_DATA_KEY));
        
        // restless / irritable
        getRestlessIrritableRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.RESTLESS_IRRITABLE_DATA_KEY));
        
        // cholera in area
        getCholeraInAreaRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.CHOLERA_IN_AREA_DATA_KEY));
        
        // offer the child fluid
        // configure page and data key for radio button listener in 'custom fever toggle group'
        getChildFluidRadioGroup().setPage(getDiarrhoeaAssessmentPage());
        getChildFluidRadioGroup().setDataKey(DiarrhoeaAssessmentPage.CHILD_FLUID_DATA_KEY);
        
        // skin pinch
        // configure page and data key for radio button listener in 'custom fever toggle group'
        getSkinPinchRadioGroup().setPage(getDiarrhoeaAssessmentPage());
        getSkinPinchRadioGroup().setDataKey(DiarrhoeaAssessmentPage.SKIN_PINCH_DATA_KEY);
    }

	/**
	 * addDiarrhoeaDynamicViewListener()
	 * 
	 * Responsible for adding a listener to the Diarrhoea view
	 * 
	 */
	private void addDiarrhoeaDynamicViewListener() {
        int indexPosition = getAnimatedView().indexOfChild(getDiarrhoeaView()) + 1;
        
        getDiarrhoeaRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupCoordinatorListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.DIARRHOEA_DATA_KEY, 
        				Arrays.asList(getDiarrhoeaDurationDynamicView()),
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
	 * Getter Method: getDiarrhoeaAssessmentPage()
	 */		
	public DiarrhoeaAssessmentPage getDiarrhoeaAssessmentPage() {
		return diarrhoeaAssessmentPage;
	}

	/**
	 * Setter Method: setDiarrhoeaAssessmentPage()
	 */			
	public void setDiarrhoeaAssessmentPage(DiarrhoeaAssessmentPage diarrhoeaAssessmentPage) {
		this.diarrhoeaAssessmentPage = diarrhoeaAssessmentPage;
	}

	/**
	 * Getter Method: getDiarrhoeaRadioGroup()
	 */		
	public RadioGroup getDiarrhoeaRadioGroup() {
		return diarrhoeaRadioGroup;
	}

	/**
	 * Setter Method: setDiarrhoeaRadioGroup()
	 */				
	public void setDiarrhoeaRadioGroup(RadioGroup diarrhoeaRadioGroup) {
		this.diarrhoeaRadioGroup = diarrhoeaRadioGroup;
	}

	/**
	 * Getter Method: getBloodStoolsRadioGroup()
	 */
	public RadioGroup getBloodStoolsRadioGroup() {
		return bloodStoolsRadioGroup;
	}

	/**
	 * Setter Method: setBloodStoolsRadioGroup()
	 */			
	public void setBloodStoolsRadioGroup(RadioGroup bloodStoolsRadioGroup) {
		this.bloodStoolsRadioGroup = bloodStoolsRadioGroup;
	}

	/**
	 * Getter Method: getDiarrhoeaDurationEditText()
	 */
	private EditText getDiarrhoeaDurationEditText() {
		return diarrhoeaDurationEditText;
	}

	/**
	 * Setter Method: setDiarrhoeaDurationEditText()
	 */	
	private void setDiarrhoeaDurationEditText(EditText diarrhoeaDurationEditText) {
		this.diarrhoeaDurationEditText = diarrhoeaDurationEditText;
	}

	/**
	 * Getter Method: getSunkenEyesRadioGroup()
	 */
	private RadioGroup getSunkenEyesRadioGroup() {
		return sunkenEyesRadioGroup;
	}

	/**
	 * Setter Method: setSunkenEyesRadioGroup()
	 */	
	private void setSunkenEyesRadioGroup(RadioGroup sunkenEyesRadioGroup) {
		this.sunkenEyesRadioGroup = sunkenEyesRadioGroup;
	}

	/**
	 * Getter Method: getLethargicUnconsciousRadioGroup()
	 */
	private RadioGroup getLethargicUnconsciousRadioGroup() {
		return lethargicUnconsciousRadioGroup;
	}

	/**
	 * Setter Method: setLethargicUnconsciousRadioGroup()
	 */	
	private void setLethargicUnconsciousRadioGroup(RadioGroup lethargicUnconsciousRadioGroup) {
		this.lethargicUnconsciousRadioGroup = lethargicUnconsciousRadioGroup;
	}

	/**
	 * Getter Method: getRestlessIrritableRadioGroup()
	 */
	private RadioGroup getRestlessIrritableRadioGroup() {
		return restlessIrritableRadioGroup;
	}

	/**
	 * Setter Method: setRestlessIrritableRadioGroup()
	 */	
	private void setRestlessIrritableRadioGroup(RadioGroup restlessIrritableRadioGroup) {
		this.restlessIrritableRadioGroup = restlessIrritableRadioGroup;
	}

	/**
	 * Getter Method: getCholeraInAreaRadioGroup()
	 */
	private RadioGroup getCholeraInAreaRadioGroup() {
		return choleraInAreaRadioGroup;
	}

	/**
	 * Setter Method: setCholeraInAreaRadioGroup()
	 */	
	private void setCholeraInAreaRadioGroup(RadioGroup choleraInAreaRadioGroup) {
		this.choleraInAreaRadioGroup = choleraInAreaRadioGroup;
	}

	/**
	 * Getter Method: getChildFluidRadioGroup()
	 */
	private ToggleButtonGroupTableLayout getChildFluidRadioGroup() {
		return childFluidRadioGroup;
	}

	/**
	 * Setter Method: setChildFluidRadioGroup()
	 */	
	private void setChildFluidRadioGroup(ToggleButtonGroupTableLayout childFluidRadioGroup) {
		this.childFluidRadioGroup = childFluidRadioGroup;
	}

	/**
	 * Getter Method: getSkinPinchRadioGroup()
	 */
	private ToggleButtonGroupTableLayout getSkinPinchRadioGroup() {
		return skinPinchRadioGroup;
	}

	/**
	 * Setter Method: setSkinPinchRadioGroup()
	 */	
	private void setSkinPinchRadioGroup(ToggleButtonGroupTableLayout skinPinchRadioGroup) {
		this.skinPinchRadioGroup = skinPinchRadioGroup;
	}

	/**
	 * Getter Method: getWizardModel()
	 */
	public AbstractModel getWizardModel() {
		return wizardModel;
	}

	/**
	 * Setter Method: setWizardModel()
	 */	
	public void setWizardModel(AbstractModel wizardModel) {
		this.wizardModel = wizardModel;
	}

	/**
	 * Getter Method: getDiarrhoeaDurationDynamicView()
	 */
	public DynamicView getDiarrhoeaDurationDynamicView() {
		return diarrhoeaDurationDynamicView;
	}

	/**
	 * Setter Method: setDiarrhoeaDurationDynamicView()
	 */	
	public void setDiarrhoeaDurationDynamicView(DynamicView diarrhoeaDurationDynamicView) {
		this.diarrhoeaDurationDynamicView = diarrhoeaDurationDynamicView;
	}

	/**
	 * Getter Method: getDiarrhoeaView()
	 */
	public View getDiarrhoeaView() {
		return diarrhoeaView;
	}

	/**
	 * Setter Method: setDiarrhoeaView()
	 */	
	public void setDiarrhoeaView(View diarrhoeaView) {
		this.diarrhoeaView = diarrhoeaView;
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
