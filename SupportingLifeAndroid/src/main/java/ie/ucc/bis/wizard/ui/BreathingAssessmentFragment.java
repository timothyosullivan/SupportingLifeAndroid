package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.model.AssessmentWizardRadioGroupListener;
import ie.ucc.bis.wizard.model.BreathingAssessmentPage;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Responsible for UI fragment to display breathing / 
 * cough assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class BreathingAssessmentFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";

    private BreathingAssessmentPage breathingAssessmentPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private RadioGroup coughDifficultBreathingRadioGroup;
    private RadioGroup chestIndrawingRadioGroup;

    
    public static BreathingAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        BreathingAssessmentFragment fragment = new BreathingAssessmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */        
    public BreathingAssessmentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setBreathingAssessmentPage((BreathingAssessmentPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_page_cough_breathing_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getBreathingAssessmentPage().getTitle());

        // does the child have cough or difficult breathing?
        setCoughDifficultBreathingRadioGroup((RadioGroup) rootView.findViewById(R.id.breathing_assessment_radio_cough_difficult_breathing));
        getCoughDifficultBreathingRadioGroup().check(getBreathingAssessmentPage()
        		.getPageData().getInt(BreathingAssessmentPage.COUGH_DIFFICULT_BREATHING_DATA_KEY));
        
        // chest indrawing
        setChestIndrawingRadioGroup((RadioGroup) rootView.findViewById(R.id.breathing_assessment_radio_chest_indrawing));
        getChestIndrawingRadioGroup().check(getBreathingAssessmentPage()
        		.getPageData().getInt(BreathingAssessmentPage.CHEST_INDRAWING_DATA_KEY));
        
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

        getCoughDifficultBreathingRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getBreathingAssessmentPage(),
        				BreathingAssessmentPage.COUGH_DIFFICULT_BREATHING_DATA_KEY));        
        
        getChestIndrawingRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getBreathingAssessmentPage(),
        				BreathingAssessmentPage.CHEST_INDRAWING_DATA_KEY));
        
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
	 * Getter Method: getBreathingAssessmentPage()
	 */	
	public BreathingAssessmentPage getBreathingAssessmentPage() {
		return breathingAssessmentPage;
	}

	/**
	 * Setter Method: setBreathingAssessmentPage()
	 */
	public void setBreathingAssessmentPage(
			BreathingAssessmentPage breathingAssessmentPage) {
		this.breathingAssessmentPage = breathingAssessmentPage;
	}

	/**
	 * Getter Method: getCoughDifficultBreathingRadioGroup()
	 */		
	public RadioGroup getCoughDifficultBreathingRadioGroup() {
		return coughDifficultBreathingRadioGroup;
	}

	/**
	 * Setter Method: setCoughDifficultBreathingRadioGroup()
	 */	
	public void setCoughDifficultBreathingRadioGroup(
			RadioGroup coughDifficultBreathingRadioGroup) {
		this.coughDifficultBreathingRadioGroup = coughDifficultBreathingRadioGroup;
	}

	/**
	 * Getter Method: getChestIndrawingRadioGroup()
	 */		
	public RadioGroup getChestIndrawingRadioGroup() {
		return chestIndrawingRadioGroup;
	}

	/**
	 * Setter Method: setChestIndrawingRadioGroup()
	 */	
	public void setChestIndrawingRadioGroup(RadioGroup chestIndrawingRadioGroup) {
		this.chestIndrawingRadioGroup = chestIndrawingRadioGroup;
	}
}
