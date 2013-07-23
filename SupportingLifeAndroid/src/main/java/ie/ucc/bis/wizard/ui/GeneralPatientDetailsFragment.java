package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.model.AssessmentWizardRadioGroupListener;
import ie.ucc.bis.wizard.model.AssessmentWizardTextWatcher;
import ie.ucc.bis.wizard.model.GeneralPatientDetailsPage;
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
 * Responsible for UI fragment to display general 
 * patient details assessment form
 * 
 * @author timothyosullivan
 * 
 */
public class GeneralPatientDetailsFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";

    private GeneralPatientDetailsPage generalPatientDetailsPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private EditText firstNameEditText;
    private EditText surnameEditText;
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
        
        // gender
        setGenderRadioGroup((RadioGroup) rootView.findViewById(R.id.general_patient_details_radio_gender));
        getGenderRadioGroup().check(getGeneralPatientDetailsPage()
        		.getPageData().getInt(GeneralPatientDetailsPage.GENDER_DATA_KEY));

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
        
        getGenderRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getGeneralPatientDetailsPage(),
        				GeneralPatientDetailsPage.GENDER_DATA_KEY));
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
}
