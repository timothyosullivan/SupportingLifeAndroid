package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.wizard.model.AssessmentWizardRadioGroupListener;
import ie.ucc.bis.wizard.model.AssessmentWizardTextWatcher;
import ie.ucc.bis.wizard.model.EarAssessmentPage;
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
 * ear assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class EarAssessmentFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";

    private EarAssessmentPage earAssessmentPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private RadioGroup earProblemRadioGroup;
    private RadioGroup earPainRadioGroup;
    private RadioGroup earDischargeRadioGroup;
    private EditText earDischargeDurationEditText;
    private RadioGroup tenderSwellingRadioGroup;
    
    public static EarAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        EarAssessmentFragment fragment = new EarAssessmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */
    public EarAssessmentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setEarAssessmentPage((EarAssessmentPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_page_ear_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getEarAssessmentPage().getTitle());
                
        // ear problem
        setEarProblemRadioGroup((RadioGroup) rootView.findViewById(R.id.ear_assessment_radio_ear_problem));
        getEarProblemRadioGroup().check(getEarAssessmentPage()
        		.getPageData().getInt(EarAssessmentPage.EAR_PROBLEM_DATA_KEY));
        
        // ear pain
        setEarPainRadioGroup((RadioGroup) rootView.findViewById(R.id.ear_assessment_radio_ear_pain));
        getEarPainRadioGroup().check(getEarAssessmentPage()
        		.getPageData().getInt(EarAssessmentPage.EAR_PAIN_DATA_KEY));
        
        // ear discharge
        setEarDischargeRadioGroup((RadioGroup) rootView.findViewById(R.id.ear_assessment_radio_ear_discharge));
        getEarDischargeRadioGroup().check(getEarAssessmentPage()
        		.getPageData().getInt(EarAssessmentPage.EAR_DISCHARGE_DATA_KEY));
        
        // ear discharge duration
        setEarDischargeDurationEditText((EditText) rootView.findViewById(R.id.ear_assessment_ear_discharge_duration));
        getEarDischargeDurationEditText().setText(getEarAssessmentPage().getPageData().getString(EarAssessmentPage.EAR_DISCHARGE_DURATION_DATA_KEY));
        
        // tender swelling
        setTenderSwellingRadioGroup((RadioGroup) rootView.findViewById(R.id.ear_assessment_radio_tender_swelling));
        getTenderSwellingRadioGroup().check(getEarAssessmentPage()
        		.getPageData().getInt(EarAssessmentPage.TENDER_SWELLING_DATA_KEY));
        
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

        // add listener to ear problem radio group
        getEarProblemRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getEarAssessmentPage(),
        				EarAssessmentPage.EAR_PROBLEM_DATA_KEY));
        
        // add listener to ear pain radio group
        getEarPainRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getEarAssessmentPage(),
        				EarAssessmentPage.EAR_PAIN_DATA_KEY));
        
        // add listener to ear discharge radio group
        getEarDischargeRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getEarAssessmentPage(),
        				EarAssessmentPage.EAR_DISCHARGE_DATA_KEY));
        
        // add listener to ear discharge duration edit text
        getEarDischargeDurationEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getEarAssessmentPage(), 
        				EarAssessmentPage.EAR_DISCHARGE_DURATION_DATA_KEY));
        
        // add listener to tender swelling radio group
        getTenderSwellingRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getEarAssessmentPage(),
        				EarAssessmentPage.TENDER_SWELLING_DATA_KEY));
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
	 * Getter Method: getEarAssessmentPage()
	 */			
	public EarAssessmentPage getEarAssessmentPage() {
		return earAssessmentPage;
	}
	
	/**
	 * Setter Method: setEarAssessmentPage()
	 */
	public void setEarAssessmentPage(EarAssessmentPage earAssessmentPage) {
		this.earAssessmentPage = earAssessmentPage;
	}

	/**
	 * Getter Method: getEarProblemRadioGroup()
	 */		
	public RadioGroup getEarProblemRadioGroup() {
		return earProblemRadioGroup;
	}

	/**
	 * Setter Method: setEarProblemRadioGroup()
	 */
	public void setEarProblemRadioGroup(RadioGroup earProblemRadioGroup) {
		this.earProblemRadioGroup = earProblemRadioGroup;
	}

	/**
	 * Getter Method: getEarPainRadioGroup()
	 */		
	public RadioGroup getEarPainRadioGroup() {
		return earPainRadioGroup;
	}

	/**
	 * Setter Method: setEarPainRadioGroup()
	 */
	public void setEarPainRadioGroup(RadioGroup earPainRadioGroup) {
		this.earPainRadioGroup = earPainRadioGroup;
	}

	/**
	 * Getter Method: getEarDischargeRadioGroup()
	 */			
	public RadioGroup getEarDischargeRadioGroup() {
		return earDischargeRadioGroup;
	}

	/**
	 * Setter Method: setEarDischargeRadioGroup()
	 */
	public void setEarDischargeRadioGroup(RadioGroup earDischargeRadioGroup) {
		this.earDischargeRadioGroup = earDischargeRadioGroup;
	}

	/**
	 * Getter Method: getEarDischargeDurationEditText()
	 */	
	private EditText getEarDischargeDurationEditText() {
		return earDischargeDurationEditText;
	}

	/**
	 * Setter Method: setEarDischargeDurationEditText()
	 */
	private void setEarDischargeDurationEditText(EditText earDischargeDurationEditText) {
		this.earDischargeDurationEditText = earDischargeDurationEditText;
	}

	/**
	 * Getter Method: getTenderSwellingRadioGroup()
	 */	
	private RadioGroup getTenderSwellingRadioGroup() {
		return tenderSwellingRadioGroup;
	}

	/**
	 * Setter Method: setTenderSwellingRadioGroup()
	 */
	private void setTenderSwellingRadioGroup(RadioGroup tenderSwellingRadioGroup) {
		this.tenderSwellingRadioGroup = tenderSwellingRadioGroup;
	}
}
