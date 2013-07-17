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
import android.widget.RadioGroup;
import android.widget.TextView;

public class GeneralPatientDetailsFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";

    private GeneralPatientDetailsPage generalPatientDetailsPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private TextView firstNameTextView;
    private TextView surnameTextView;
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
        setFirstNameTextView(((TextView) rootView.findViewById(R.id.first_name)));
        getFirstNameTextView().setText(getGeneralPatientDetailsPage().getPageData().getString(GeneralPatientDetailsPage.FIRST_NAME_DATA_KEY));

        // surname
        setSurnameTextView(((TextView) rootView.findViewById(R.id.surname)));
        getSurnameTextView().setText(getGeneralPatientDetailsPage().getPageData().getString(GeneralPatientDetailsPage.SURNAME_DATA_KEY));
        
        // gender
        setGenderRadioGroup((RadioGroup) rootView.findViewById(R.id.radio_gender));
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

        getFirstNameTextView().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsPage(), 
        				GeneralPatientDetailsPage.FIRST_NAME_DATA_KEY));  

        getSurnameTextView().addTextChangedListener(
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
	 * Getter Method: getFirstNameTextView()
	 */		
	public TextView getFirstNameTextView() {
		return firstNameTextView;
	}

	/**
	 * Setter Method: setFirstNameTextView()
	 */		
	public void setFirstNameTextView(TextView firstNameTextView) {
		this.firstNameTextView = firstNameTextView;
	}

	/**
	 * Getter Method: getSurnameTextView()
	 */		
	public TextView getSurnameTextView() {
		return surnameTextView;
	}

	/**
	 * Setter Method: setSurnameTextView()
	 */		
	public void setSurnameTextView(TextView surnameTextView) {
		this.surnameTextView = surnameTextView;
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
