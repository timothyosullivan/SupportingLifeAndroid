package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.AssessmentWizardActivity;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.wizard.model.AbstractPage;
import ie.ucc.bis.wizard.model.AbstractWizardModel;
import ie.ucc.bis.wizard.model.AssessmentWizardModel;
import ie.ucc.bis.wizard.model.AssessmentWizardRadioGroupListener;
import ie.ucc.bis.wizard.model.AssessmentWizardTextWatcher;
import ie.ucc.bis.wizard.model.DiarrhoeaAssessmentPage;
import ie.ucc.bis.wizard.model.GeneralDangerSignsPage;
import ie.ucc.bis.wizard.model.ModelCallbacks;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private AbstractWizardModel wizardModel;
    
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
    private RadioGroup childFluidRadioGroup;
    private RadioGroup skinPinchRadioGroup;
    
    public static DiarrhoeaAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        DiarrhoeaAssessmentFragment fragment = new DiarrhoeaAssessmentFragment();
        fragment.setArguments(args);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_page_diarrhoea_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getDiarrhoeaAssessmentPage().getTitle());

        // diarrhoea
        setDiarrhoeaRadioGroup((RadioGroup) rootView.findViewById(R.id.diarrhoea_assessment_radio_diarrhoea));
        getDiarrhoeaRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.DIARRHOEA_DATA_KEY));
        
        // for how long? (days) - diarrhoea duration
        setDiarrhoeaDurationEditText((EditText) rootView.findViewById(R.id.diarrhoea_assessment_diarrhoea_duration));
        getDiarrhoeaDurationEditText().setText(getDiarrhoeaAssessmentPage().getPageData().getString(DiarrhoeaAssessmentPage.DIARRHOEA_DURATION_DATA_KEY));
        
        // blood in the stools
        setBloodStoolsRadioGroup((RadioGroup) rootView.findViewById(R.id.diarrhoea_assessment_radio_blood_stools));
        getBloodStoolsRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.BLOOD_STOOLS_DATA_KEY));
        
        // sunken eyes
        setSunkenEyesRadioGroup((RadioGroup) rootView.findViewById(R.id.diarrhoea_assessment_radio_sunken_eyes));
        getSunkenEyesRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.SUNKEN_EYES_DATA_KEY));
        
        // lethargic or unconscious - ** This is fed from the 'General Danger Signs' Page / review item
        setLethargicUnconsciousRadioGroup((RadioGroup) rootView.findViewById(R.id.diarrhoea_assessment_radio_lethargic_or_unconscious));
        String uiReferenceElement = GeneralDangerSignsPage.LETHARGIC_OR_UNCONSCIOUS_DATA_KEY + AssessmentWizardRadioGroupListener.RADIO_BUTTON_TEXT_DATA_KEY;
        configureYesNoRadioButtonSetting(AssessmentWizardModel.DANGER_SIGNS_PAGE_TITLE, uiReferenceElement);
      
        // restless / irritable
        setRestlessIrritableRadioGroup((RadioGroup) rootView.findViewById(R.id.diarrhoea_assessment_radio_restless_irritable));
        getRestlessIrritableRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.RESTLESS_IRRITABLE_DATA_KEY));
 
        // cholera in area
        setCholeraInAreaRadioGroup((RadioGroup) rootView.findViewById(R.id.diarrhoea_assessment_radio_cholera_in_area));
        getCholeraInAreaRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.CHOLERA_IN_AREA_DATA_KEY));
        
        // offer the child fluid
        setChildFluidRadioGroup((RadioGroup) rootView.findViewById(R.id.diarrhoea_assessment_radio_child_fluid));
        getChildFluidRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.CHILD_FLUID_DATA_KEY));
       
        // skin pinch
        setSkinPinchRadioGroup((RadioGroup) rootView.findViewById(R.id.diarrhoea_assessment_radio_skin_pinch));
        getSkinPinchRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.SKIN_PINCH_DATA_KEY));
        
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);
        
        return rootView;
    }

	private void configureYesNoRadioButtonSetting(String pageTitle, String uiReferenceElement) {
		// 1. retrieve the relevant page where the radio group 'lethargic or unconscious' is being referenced
        AbstractPage dangerSignsAssessmentPage = getWizardModel().findPageByKey(pageTitle);
        // 2. retrieve the textual value (yes/no) of the radio group checked option
        String radioGroupSetting = dangerSignsAssessmentPage.getPageData().getString(uiReferenceElement);
        // 3. set this value on the lethargic/unconscious radio grouping on the diarrhoea assessment page
        if (radioGroupSetting.equals(getResources().getString(R.string.assessment_wizard_radio_yes))) {
        	getLethargicUnconsciousRadioGroup().check(R.id.diarrhoea_assessment_radio_lethargic_or_unconscious_yes);
        	getDiarrhoeaDurationEditText().setText(getResources().getString(R.string.assessment_wizard_radio_yes));
        }
        else if (radioGroupSetting.equals(getResources().getString(R.string.assessment_wizard_radio_no))) {
        	// getLethargicUnconsciousRadioGroup().check(R.id.diarrhoea_assessment_radio_lethargic_or_unconscious_no);
        	getLethargicUnconsciousRadioGroup().clearCheck();
        	getDiarrhoeaDurationEditText().setText(getResources().getString(R.string.assessment_wizard_radio_no));
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
        
        configureYesNoRadioButtonSetting();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // diarrhoea
        getDiarrhoeaRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.DIARRHOEA_DATA_KEY));

        // for how long? (days) - diarrhoea duration
        getDiarrhoeaDurationEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getDiarrhoeaAssessmentPage(), 
        				DiarrhoeaAssessmentPage.DIARRHOEA_DURATION_DATA_KEY));
        
        // blood in the stools
        getBloodStoolsRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.BLOOD_STOOLS_DATA_KEY));
        
        // sunken eyes
        getSunkenEyesRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.SUNKEN_EYES_DATA_KEY));
        
        // restless / irritable
        getRestlessIrritableRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.RESTLESS_IRRITABLE_DATA_KEY));
        
        // cholera in area
        getCholeraInAreaRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.CHOLERA_IN_AREA_DATA_KEY));
        
        // offer the child fluid
        getChildFluidRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.CHILD_FLUID_DATA_KEY));
        
        // skin pinch
        getSkinPinchRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.SKIN_PINCH_DATA_KEY));
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
	private RadioGroup getChildFluidRadioGroup() {
		return childFluidRadioGroup;
	}

	/**
	 * Setter Method: setChildFluidRadioGroup()
	 */	
	private void setChildFluidRadioGroup(RadioGroup childFluidRadioGroup) {
		this.childFluidRadioGroup = childFluidRadioGroup;
	}

	/**
	 * Getter Method: getSkinPinchRadioGroup()
	 */
	private RadioGroup getSkinPinchRadioGroup() {
		return skinPinchRadioGroup;
	}

	/**
	 * Setter Method: setSkinPinchRadioGroup()
	 */	
	private void setSkinPinchRadioGroup(RadioGroup skinPinchRadioGroup) {
		this.skinPinchRadioGroup = skinPinchRadioGroup;
	}

	/**
	 * Getter Method: getWizardModel()
	 */
	public AbstractWizardModel getWizardModel() {
		return wizardModel;
	}

	/**
	 * Setter Method: setWizardModel()
	 */	
	public void setWizardModel(AbstractWizardModel wizardModel) {
		this.wizardModel = wizardModel;
	}
}
