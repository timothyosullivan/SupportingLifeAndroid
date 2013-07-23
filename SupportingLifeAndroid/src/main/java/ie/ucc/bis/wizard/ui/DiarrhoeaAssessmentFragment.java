package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.wizard.model.AssessmentWizardRadioGroupListener;
import ie.ucc.bis.wizard.model.DiarrhoeaAssessmentPage;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private DiarrhoeaAssessmentPage diarrhoeaAssessmentPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private RadioGroup diarrhoeaRadioGroup;
    private RadioGroup bloodStoolsRadioGroup;
    
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
        
        // blood in the stools
        setBloodStoolsRadioGroup((RadioGroup) rootView.findViewById(R.id.diarrhoea_assessment_radio_blood_stools));
        getBloodStoolsRadioGroup().check(getDiarrhoeaAssessmentPage()
        		.getPageData().getInt(DiarrhoeaAssessmentPage.BLOOD_STOOLS_DATA_KEY));
        
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

        getDiarrhoeaRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.DIARRHOEA_DATA_KEY));        
        
        getBloodStoolsRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getDiarrhoeaAssessmentPage(),
        				DiarrhoeaAssessmentPage.BLOOD_STOOLS_DATA_KEY));
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
}
