package ie.ucc.bis.imci.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.imci.model.GeneralPatientDetailsPage;
import ie.ucc.bis.imci.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.imci.model.listener.DatePickerListener;
import ie.ucc.bis.imci.model.listener.RadioGroupListener;
import ie.ucc.bis.ui.custom.InputFilterMinMax;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Responsible for UI fragment to display general 
 * patient details assessment form
 * 
 * @author timothyosullivan
 * 
 */
public class GeneralPatientDetailsFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";
    
    private static final int MIN_WEIGHT = 0;
    private static final int MAX_WEIGHT = 100;
    private static final int MIN_TEMPERATURE = 0;
    private static final int MAX_TEMPERATURE = 50;

    private GeneralPatientDetailsPage generalPatientDetailsPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private EditText firstNameEditText;
    private EditText surnameEditText;
    private EditText dateBirthEditText;
    private EditText weightEditText;
    private EditText temperatureEditText;
    private EditText problemsEditText;
    private RadioGroup genderRadioGroup;
    
    public static GeneralPatientDetailsFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        GeneralPatientDetailsFragment fragment = new GeneralPatientDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */
    public GeneralPatientDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setGeneralPatientDetailsPage((GeneralPatientDetailsPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_page_general_patient_details, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getGeneralPatientDetailsPage().getTitle());

        // first name
        setFirstNameEditText(((EditText) rootView.findViewById(R.id.general_patient_details_first_name)));
        getFirstNameEditText().setText(getGeneralPatientDetailsPage().getPageData().getString(GeneralPatientDetailsPage.FIRST_NAME_DATA_KEY));

        // surname
        setSurnameEditText(((EditText) rootView.findViewById(R.id.general_patient_details_surname)));
        getSurnameEditText().setText(getGeneralPatientDetailsPage().getPageData().getString(GeneralPatientDetailsPage.SURNAME_DATA_KEY));
        
        // date of birth
        setDateBirthEditText((EditText) rootView.findViewById(R.id.general_patient_details_date_of_birth));
        getDateBirthEditText().setText(getGeneralPatientDetailsPage().getPageData().getString(GeneralPatientDetailsPage.DATE_OF_BIRTH_DATA_KEY));

        // weight
        setWeightEditText((EditText) rootView.findViewById(R.id.general_patient_details_weight));
        getWeightEditText().setText(getGeneralPatientDetailsPage().getPageData().getString(GeneralPatientDetailsPage.WEIGHT_DATA_KEY));
        // apply min/max data entry filtering to the 'weight' UI element
        getWeightEditText().setFilters(new InputFilter[] {new InputFilterMinMax(MIN_WEIGHT, MAX_WEIGHT)});
        
        // temperature label - add degree celsius postfix
        Spanned temperatureHeading = Html.fromHtml(getResources().getString(R.string.general_patient_details_temperature) + " (<sup>o</sup>" + "C)");
        TextView temperatureLabel = (TextView) rootView.findViewById(R.id.general_patient_details_temperature_label);
        temperatureLabel.setText(temperatureHeading);
        
        // temperature textfield
        setTemperatureEditText((EditText) rootView.findViewById(R.id.general_patient_details_temperature));
        getTemperatureEditText().setText(getGeneralPatientDetailsPage().getPageData().getString(GeneralPatientDetailsPage.TEMPERATURE_DATA_KEY));
        // apply min/max data entry filtering to the 'weight' UI element
        getTemperatureEditText().setFilters(new InputFilter[] {new InputFilterMinMax(MIN_TEMPERATURE, MAX_TEMPERATURE)});
        
        // gender
        setGenderRadioGroup((RadioGroup) rootView.findViewById(R.id.general_patient_details_radio_gender));
        getGenderRadioGroup().check(getGeneralPatientDetailsPage()
        		.getPageData().getInt(GeneralPatientDetailsPage.GENDER_DATA_KEY));
        
        // what are the child's problems
        setProblemsEditText((EditText) rootView.findViewById(R.id.general_patient_details_problems));
        getProblemsEditText().setText(getGeneralPatientDetailsPage().getPageData().getString(GeneralPatientDetailsPage.PROBLEMS_DATA_KEY));
        
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
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsPage(), 
        				GeneralPatientDetailsPage.FIRST_NAME_DATA_KEY));  

        getSurnameEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsPage(), 
        				GeneralPatientDetailsPage.SURNAME_DATA_KEY));
        
        getDateBirthEditText().setOnFocusChangeListener(new DatePickerListener(this, getGeneralPatientDetailsPage(), 
				GeneralPatientDetailsPage.DATE_OF_BIRTH_DATA_KEY));
        
        // turn off soft keyboard input method for 'Date of Birth' EditText
        getDateBirthEditText().setInputType(InputType.TYPE_NULL);
        
        getWeightEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsPage(), 
        				GeneralPatientDetailsPage.WEIGHT_DATA_KEY));
        
        getTemperatureEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsPage(), 
        				GeneralPatientDetailsPage.TEMPERATURE_DATA_KEY));
      
        getGenderRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getGeneralPatientDetailsPage(),
        				GeneralPatientDetailsPage.GENDER_DATA_KEY));
        
        getProblemsEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsPage(), 
        				GeneralPatientDetailsPage.PROBLEMS_DATA_KEY));
    }

	/**
	 * Getter Method: getGeneralPatientDetailsPage()
	 */
	public GeneralPatientDetailsPage getGeneralPatientDetailsPage() {
		return generalPatientDetailsPage;
	}

	/**
	 * Setter Method: setGeneralPatientDetailsPage()
	 */   	
	public void setGeneralPatientDetailsPage(GeneralPatientDetailsPage generalPatientDetailsPage) {
		this.generalPatientDetailsPage = generalPatientDetailsPage;
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
	 * Getter Method: getWeightEditText()
	 */
	public EditText getWeightEditText() {
		return weightEditText;
	}

	/**
	 * Setter Method: setWeightEditText()
	 */
	public void setWeightEditText(EditText weightEditText) {
		this.weightEditText = weightEditText;
	}

	/**
	 * Getter Method: getTemperatureEditText()
	 */
	public EditText getTemperatureEditText() {
		return temperatureEditText;
	}

	/**
	 * Setter Method: setTemperatureEditText()
	 */
	public void setTemperatureEditText(EditText temperatureEditText) {
		this.temperatureEditText = temperatureEditText;
	}

	/**
	 * Getter Method: getProblemsEditText()
	 */
	public EditText getProblemsEditText() {
		return problemsEditText;
	}

	/**
	 * Setter Method: setProblemsEditText()
	 */
	public void setProblemsEditText(EditText problemsEditText) {
		this.problemsEditText = problemsEditText;
	}
}
