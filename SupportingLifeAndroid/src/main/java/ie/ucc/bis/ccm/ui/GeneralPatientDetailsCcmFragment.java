package ie.ucc.bis.ccm.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.assessment.model.listener.DatePickerListener;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.ccm.model.GeneralPatientDetailsCcmPage;
import ie.ucc.bis.imci.ui.PageFragmentCallbacks;
import ie.ucc.bis.ui.utilities.DateUtilities;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Responsible for UI fragment to display CCM 
 * general patient details assessment form
 * 
 * @author timothyosullivan
 * 
 */
public class GeneralPatientDetailsCcmFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";
    
    private GeneralPatientDetailsCcmPage generalPatientDetailsCcmPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private TextView todayDateTextView;
    private EditText hsaEditText;
    private EditText firstNameEditText;
    private EditText surnameEditText;
    private EditText dateBirthEditText;
    private RadioGroup genderRadioGroup;
    private EditText caregiverEditText;
    private EditText physicalAddressEditText;
    private EditText villageEditText;
    
    public static GeneralPatientDetailsCcmFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        GeneralPatientDetailsCcmFragment fragment = new GeneralPatientDetailsCcmFragment();
        fragment.setArguments(args);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */
    public GeneralPatientDetailsCcmFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setGeneralPatientDetailsCcmPage((GeneralPatientDetailsCcmPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ccm_page_general_patient_details, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getGeneralPatientDetailsCcmPage().getTitle());

        // today's date
        setTodayDateTextView(((TextView) rootView.findViewById(R.id.ccm_general_patient_details_today_date)));
        
        // Health Surveillance Assistant (HSA)
        setHsaEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_hsa_identifier)));
        getHsaEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.HEALTH_SURVEILLANCE_ASSISTANT_DATA_KEY));
        
        // child's first name
        setFirstNameEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_first_name)));
        getFirstNameEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.FIRST_NAME_DATA_KEY));

        // child's surname
        setSurnameEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_surname)));
        getSurnameEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.SURNAME_DATA_KEY));
        
        // date of birth
        setDateBirthEditText((EditText) rootView.findViewById(R.id.ccm_general_patient_details_date_of_birth));
        getDateBirthEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.DATE_OF_BIRTH_DATA_KEY));
       
        // gender
        setGenderRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_general_patient_details_radio_gender));
        getGenderRadioGroup().check(getGeneralPatientDetailsCcmPage()
        		.getPageData().getInt(GeneralPatientDetailsCcmPage.GENDER_DATA_KEY));
        
        // caregiver
        setCaregiverEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_caregiver)));
        getCaregiverEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.CAREGIVER_DATA_KEY));
     
        // configure the animated view of relationship
        // i.e. Relationship: Other --> Specify Relationship Textfield  
        configureRelationshipAnimatedView(rootView);
        
        // physical address
        setPhysicalAddressEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_physical_address)));
        getPhysicalAddressEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.PHYSICAL_ADDRESS_DATA_KEY));
        
        // village/TA
        setVillageEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_village)));
        getVillageEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.VILLAGE_DATA_KEY));
        
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);

        return rootView;
    }
    
    
	private void configureRelationshipAnimatedView(View rootView) {
		// Fever view
//		setFeverView((View) rootView.findViewById(R.id.imci_fever_assessment_view_fever));
//		
//        // need to add superscript text to the degree Celsius label of a 
//        // fever radio button
//        RadioButton feverTempRadioButton = (RadioButton) rootView.findViewById(R.id.imci_fever_assessment_radio_fever_temperature);
//        feverTempRadioButton.setText(Html.fromHtml(getText(R.string.imci_fever_assessment_radio_fever_temperature) + "<sup>o</sup>" + "C"));
//                
//        // fever
//        setFeverCustomRadioGroup((ToggleButtonGroupTableLayout) rootView.findViewById(R.id.imci_fever_assessment_radio_fever));
//        getFeverCustomRadioGroup().check(getFeverAssessmentPage()
//        		.getPageData().getInt(FeverAssessmentPage.FEVER_DATA_KEY));
//        
//        // fever duration
//        setDurationEditText((EditText) rootView.findViewById(R.id.imci_fever_assessment_fever_duration));
//        // apply min/max data entry filtering to the 'fever duration' UI element
//        getDurationEditText().setFilters(new InputFilter[] {new InputFilterMinMax(MIN_FEVER_DURATION, MAX_FEVER_DURATION)});
//        
//        // fever duration is a dynamic view within the UI
//        setFeverDurationDynamicView(new DynamicView(rootView.findViewById(R.id.imci_fever_assessment_view_fever_duration),
//        									rootView.findViewById(R.id.imci_fever_assessment_fever_duration)));
//        
//        // has fever been present every day
//        setFeverPresentDailyRadioGroup((RadioGroup) rootView.findViewById(R.id.imci_fever_assessment_radio_present_every_day));
//        getFeverPresentDailyRadioGroup().check(getFeverAssessmentPage()
//        		.getPageData().getInt(FeverAssessmentPage.FEVER_PRESENT_EVERY_DAY_DATA_KEY));
//        
//        // get a hold on the top level animated view
//        setAnimatedFeverDurationView(((ViewGroup) rootView.findViewById(R.id.imci_fever_assessment_duration_animated_view)));
        
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

        // today's date
        getTodayDateTextView().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.TODAY_DATE_DATA_KEY));
        getTodayDateTextView().setText(DateUtilities.getTodaysDate());
        
        // Health Surveillance Assistant (HSA)
        getHsaEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.HEALTH_SURVEILLANCE_ASSISTANT_DATA_KEY));
        
        // child's first name
        getFirstNameEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.FIRST_NAME_DATA_KEY));  

        // child's surname
        getSurnameEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.SURNAME_DATA_KEY));
        
        // date of birth
        getDateBirthEditText().setOnFocusChangeListener(new DatePickerListener(this, getGeneralPatientDetailsCcmPage(), 
        		GeneralPatientDetailsCcmPage.DATE_OF_BIRTH_DATA_KEY));
        
        // turn off soft keyboard input method for 'Date of Birth' EditText
        getDateBirthEditText().setInputType(InputType.TYPE_NULL);

        // gender
        getGenderRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getGeneralPatientDetailsCcmPage(),
        				GeneralPatientDetailsCcmPage.GENDER_DATA_KEY));
        
        // caregiver
        getCaregiverEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.CAREGIVER_DATA_KEY));
        
        // physical address
        getPhysicalAddressEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.PHYSICAL_ADDRESS_DATA_KEY));
        
        // village/TA
        getVillageEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.VILLAGE_DATA_KEY));
    }

	/**
	 * Getter Method: getGeneralPatientDetailsCcmPage()
	 */
	public GeneralPatientDetailsCcmPage getGeneralPatientDetailsCcmPage() {
		return generalPatientDetailsCcmPage;
	}

	/**
	 * Setter Method: setGeneralPatientDetailsCcmPage()
	 */   	
	public void setGeneralPatientDetailsCcmPage(GeneralPatientDetailsCcmPage generalPatientDetailsCcmPage) {
		this.generalPatientDetailsCcmPage = generalPatientDetailsCcmPage;
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
	 * Getter Method: getTodayDateTextView()
	 */
	public TextView getTodayDateTextView() {
		return todayDateTextView;
	}

	/**
	 * Setter Method: setTodayDateTextView()
	 */	
	public void setTodayDateTextView(TextView todayDateTextView) {
		this.todayDateTextView = todayDateTextView;
	}

	/**
	 * Getter Method: getHsaEditText()
	 */
	public EditText getHsaEditText() {
		return hsaEditText;
	}

	/**
	 * Setter Method: setHsaEditText()
	 */	
	public void setHsaEditText(EditText hsaEditText) {
		this.hsaEditText = hsaEditText;
	}

	/**
	 * Getter Method: getFirstNameEditText()
	 */
	public EditText getFirstNameEditText() {
		return firstNameEditText;
	}

	/**
	 * Setter Method: setFirstNameEditText()
	 */		
	public void setFirstNameEditText(EditText firstNameEditText) {
		this.firstNameEditText = firstNameEditText;
	}

	/**
	 * Getter Method: getSurnameEditText()
	 */		
	public EditText getSurnameEditText() {
		return surnameEditText;
	}

	/**
	 * Setter Method: setSurnameEditText()
	 */		
	public void setSurnameEditText(EditText surnameEditText) {
		this.surnameEditText = surnameEditText;
	}

	/**
	 * Getter Method: getGenderRadioGroup()
	 */	
	public RadioGroup getGenderRadioGroup() {
		return genderRadioGroup;
	}

	/**
	 * Setter Method: setGenderRadioGroup()
	 */			
	public void setGenderRadioGroup(RadioGroup genderRadioGroup) {
		this.genderRadioGroup = genderRadioGroup;
	}

	/**
	 * Getter Method: getDateBirthEditText()
	 */	
	public EditText getDateBirthEditText() {
		return dateBirthEditText;
	}

	/**
	 * Setter Method: setDateBirthEditText()
	 */	
	public void setDateBirthEditText(EditText dateBirthEditText) {
		this.dateBirthEditText = dateBirthEditText;
	}

	/**
	 * Getter Method: getCaregiverEditText()
	 */
	public EditText getCaregiverEditText() {
		return caregiverEditText;
	}

	/**
	 * Setter Method: setCaregiverEditText()
	 */
	public void setCaregiverEditText(EditText caregiverEditText) {
		this.caregiverEditText = caregiverEditText;
	}

	/**
	 * Getter Method: getPhysicalAddressEditText()
	 */
	public EditText getPhysicalAddressEditText() {
		return physicalAddressEditText;
	}

	/**
	 * Setter Method: setPhysicalAddressEditText()
	 */
	public void setPhysicalAddressEditText(EditText physicalAddressEditText) {
		this.physicalAddressEditText = physicalAddressEditText;
	}

	/**
	 * Getter Method: getVillageEditText()
	 */
	public EditText getVillageEditText() {
		return villageEditText;
	}

	/**
	 * Setter Method: setVillageEditText()
	 */
	public void setVillageEditText(EditText villageEditText) {
		this.villageEditText = villageEditText;
	}
}
