package ie.ucc.bis.ccm.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.assessment.model.listener.DatePickerListener;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.ccm.model.GeneralPatientDetailsCcmPage;
import ie.ucc.bis.imci.model.GeneralPatientDetailsPage;
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
    private EditText firstNameEditText;
    private EditText surnameEditText;
    private EditText dateBirthEditText;
    private RadioGroup genderRadioGroup;
    
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
        getTodayDateTextView().setText(DateUtilities.getTodaysDate());        
        
        // first name
        setFirstNameEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_first_name)));
        getFirstNameEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsPage.FIRST_NAME_DATA_KEY));

        // surname
        setSurnameEditText(((EditText) rootView.findViewById(R.id.ccm_general_patient_details_surname)));
        getSurnameEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsPage.SURNAME_DATA_KEY));
        
        // date of birth
        setDateBirthEditText((EditText) rootView.findViewById(R.id.ccm_general_patient_details_date_of_birth));
        getDateBirthEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsPage.DATE_OF_BIRTH_DATA_KEY));
       
        // gender
        setGenderRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_general_patient_details_radio_gender));
        getGenderRadioGroup().check(getGeneralPatientDetailsCcmPage()
        		.getPageData().getInt(GeneralPatientDetailsPage.GENDER_DATA_KEY));

		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);

        return rootView;
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

        getFirstNameEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsPage.FIRST_NAME_DATA_KEY));  

        getSurnameEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsPage.SURNAME_DATA_KEY));
        
        getDateBirthEditText().setOnFocusChangeListener(new DatePickerListener(this, getGeneralPatientDetailsCcmPage(), 
				GeneralPatientDetailsPage.DATE_OF_BIRTH_DATA_KEY));
        
        // turn off soft keyboard input method for 'Date of Birth' EditText
        getDateBirthEditText().setInputType(InputType.TYPE_NULL);
      
        getGenderRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getGeneralPatientDetailsCcmPage(),
        				GeneralPatientDetailsPage.GENDER_DATA_KEY));
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
}
