package ie.ucc.bis.supportinglife.assessment.ccm.ui;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.ccm.model.GeneralPatientDetailsCcmPage;
import ie.ucc.bis.supportinglife.assessment.imci.model.DynamicView;
import ie.ucc.bis.supportinglife.assessment.imci.ui.PageFragmentCallbacks;
import ie.ucc.bis.supportinglife.assessment.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.supportinglife.assessment.model.listener.DatePickerListener;
import ie.ucc.bis.supportinglife.assessment.model.listener.RadioGroupCoordinatorListener;
import ie.ucc.bis.supportinglife.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.supportinglife.ui.utilities.DateUtilities;
import ie.ucc.bis.supportinglife.ui.utilities.ViewGroupUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private EditText nationalIdEditText;
    private EditText nationalHealthIdEditText;
    private EditText firstNameEditText;
    private EditText surnameEditText;
    private EditText dateBirthEditText;
    private RadioGroup genderRadioGroup;
    private EditText caregiverEditText;
    private RadioGroup relationshipRadioGroup;
    private EditText relationshipSpecifiedEditText;
    private EditText physicalAddressEditText;
    private EditText villageEditText;
    private ViewGroup animatedRelationshipSpecifiedView;
    private View relationshipView;
    private DynamicView relationshipSpecifiedDynamicView;
    private Boolean animatedRelationshipViewInVisibleState;
    
    public static GeneralPatientDetailsCcmFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        GeneralPatientDetailsCcmFragment fragment = new GeneralPatientDetailsCcmFragment();
        fragment.setArguments(args);
        fragment.setAnimatedRelationshipViewInVisibleState(false);
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
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {      
      if (getAnimatedRelationshipSpecifiedView().indexOfChild(getRelationshipSpecifiedDynamicView().getWrappedView()) != -1) {
    	  // Animated Fever view is visible
    	  savedInstanceState.putBoolean("animatedRelationshipViewInVisibleState", true);
      }
      else {
    	  // Animated Fever view is invisible
    	  savedInstanceState.putBoolean("animatedRelationshipViewInVisibleState", false);
      }
      super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
    	super.onViewStateRestored(savedInstanceState);
    	if (savedInstanceState != null) {
    		setAnimatedRelationshipViewInVisibleState(savedInstanceState.getBoolean("animatedRelationshipViewInVisibleState"));
    	}

    	if (!isAnimatedRelationshipViewInVisibleState()) {
    		ViewGroupUtilities.removeDynamicViews(getAnimatedRelationshipSpecifiedView(), Arrays.asList(getRelationshipSpecifiedDynamicView()));
    	}

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        setGeneralPatientDetailsCcmPage((GeneralPatientDetailsCcmPage) getPageFragmentCallbacks().getPage(getPageKey()));
        
        View rootView = inflater.inflate(R.layout.fragment_ccm_page_general_patient_details, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getGeneralPatientDetailsCcmPage().getTitle());

        // today's date
        setTodayDateTextView(((TextView) rootView.findViewById(R.id.ccm_general_patient_details_today_date)));
        
        // Health Surveillance Assistant (HSA)
        setHsaEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_hsa_identifier)));
        getHsaEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.HEALTH_SURVEILLANCE_ASSISTANT_DATA_KEY));
        
        // National Id
        setNationalIdEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_national_id)));
        getNationalIdEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.NATIONAL_ID_DATA_KEY));       
  
        // National Health Id
        setNationalHealthIdEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_national_health_id)));
        getNationalHealthIdEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.NATIONAL_HEALTH_ID_DATA_KEY));
        
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
		// Relationship view
		setRelationshipView((View) rootView.findViewById(R.id.ccm_general_patient_details_view_relationship));

        // relationship
        setRelationshipRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_general_patient_details_radio_relationship));
        getRelationshipRadioGroup().check(getGeneralPatientDetailsCcmPage()
        		.getPageData().getInt(GeneralPatientDetailsCcmPage.RELATIONSHIP_DATA_KEY));
        
        // specify relationship
        setRelationshipSpecifiedEditText((EditText) rootView.findViewById(R.id.ccm_general_patient_details_relationship_specified));
        getRelationshipSpecifiedEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.RELATIONSHIP_SPECIFIED_DATA_KEY));
        
        // 'specify relationship' is a dynamic view within the UI
        setRelationshipSpecifiedDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_general_patient_details_view_relationship_specified),
        									rootView.findViewById(R.id.ccm_general_patient_details_relationship_specified)));
        
        // get a hold on the top level animated view
        setAnimatedRelationshipSpecifiedView(((ViewGroup) rootView.findViewById(R.id.ccm_general_patient_details_relationship_animated_view)));
        
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
        
        // TODO - To be removed once HSA User Login is supported
        getHsaEditText().setText("hsauser1");
    
        // national id
        getNationalIdEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.NATIONAL_ID_DATA_KEY));  
        
        // national health id
        getNationalHealthIdEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.NATIONAL_HEALTH_ID_DATA_KEY));  
        
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
        
        // add dynamic view listener to relationship radio group
        addRelationshipDynamicViewListener();  
                
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
	 * addRelationshipDynamicViewListener()
	 * 
	 * Responsible for adding a listener to the Relationship view
	 * 
	 */
	private void addRelationshipDynamicViewListener() {
        
		List<String> animateUpRadioButtonTextTriggers = new ArrayList<String>();
		animateUpRadioButtonTextTriggers.add(getResources().getString(R.string.ccm_general_patient_details_radio_relationship_mother));
		animateUpRadioButtonTextTriggers.add(getResources().getString(R.string.ccm_general_patient_details_radio_relationship_father));
        
        getRelationshipRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupCoordinatorListener(getGeneralPatientDetailsCcmPage(),
        				GeneralPatientDetailsCcmPage.RELATIONSHIP_DATA_KEY, 
        				Arrays.asList(getRelationshipSpecifiedDynamicView()),
        				getAnimatedRelationshipSpecifiedView(),
        				getRelationshipView(),
        				animateUpRadioButtonTextTriggers));
        
        // add listener to 'specify relationship'
        getRelationshipSpecifiedEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.RELATIONSHIP_SPECIFIED_DATA_KEY));
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

	public EditText getNationalIdEditText() {
		return nationalIdEditText;
	}

	public void setNationalIdEditText(EditText nationalIdEditText) {
		this.nationalIdEditText = nationalIdEditText;
	}

	public EditText getNationalHealthIdEditText() {
		return nationalHealthIdEditText;
	}

	public void setNationalHealthIdEditText(EditText nationalHealthIdEditText) {
		this.nationalHealthIdEditText = nationalHealthIdEditText;
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
	 * Getter Method: getRelationshipRadioGroup()
	 */
	public RadioGroup getRelationshipRadioGroup() {
		return relationshipRadioGroup;
	}

	/**
	 * Setter Method: setRelationshipRadioGroup()
	 */
	public void setRelationshipRadioGroup(RadioGroup relationshipRadioGroup) {
		this.relationshipRadioGroup = relationshipRadioGroup;
	}

	/**
	 * Getter Method: getRelationshipSpecifiedEditText()
	 */
	public EditText getRelationshipSpecifiedEditText() {
		return relationshipSpecifiedEditText;
	}

	/**
	 * Setter Method: setRelationshipSpecifiedEditText()
	 */
	public void setRelationshipSpecifiedEditText(EditText relationshipSpecifiedEditText) {
		this.relationshipSpecifiedEditText = relationshipSpecifiedEditText;
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

	/**
	 * Getter Method: getAnimatedRelationshipSpecifiedView()
	 */
	public ViewGroup getAnimatedRelationshipSpecifiedView() {
		return animatedRelationshipSpecifiedView;
	}

	/**
	 * Setter Method: setAnimatedRelationshipSpecifiedView()
	 */
	public void setAnimatedRelationshipSpecifiedView(ViewGroup animatedRelationshipSpecifiedView) {
		this.animatedRelationshipSpecifiedView = animatedRelationshipSpecifiedView;
	}

	/**
	 * Getter Method: getRelationshipView()
	 */
	public View getRelationshipView() {
		return relationshipView;
	}

	/**
	 * Setter Method: setRelationshipView()
	 */
	public void setRelationshipView(View relationshipView) {
		this.relationshipView = relationshipView;
	}

	/**
	 * Getter Method: getRelationshipSpecifiedDynamicView()
	 */
	public DynamicView getRelationshipSpecifiedDynamicView() {
		return relationshipSpecifiedDynamicView;
	}

	/**
	 * Setter Method: setRelationshipSpecifiedDynamicView()
	 */
	public void setRelationshipSpecifiedDynamicView(DynamicView relationshipOtherDynamicView) {
		this.relationshipSpecifiedDynamicView = relationshipOtherDynamicView;
	}

	/**
	 * Getter Method: isAnimatedRelationshipViewInVisibleState()
	 */
	public Boolean isAnimatedRelationshipViewInVisibleState() {
		return animatedRelationshipViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedRelationshipViewInVisibleState()
	 */
	public void setAnimatedRelationshipViewInVisibleState(Boolean animatedRelationshipViewInVisibleState) {
		this.animatedRelationshipViewInVisibleState = animatedRelationshipViewInVisibleState;
	}
}
