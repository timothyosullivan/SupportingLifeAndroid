package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.ui.custom.ToggleButtonGroupTableLayout;
import ie.ucc.bis.wizard.model.AssessmentWizardRadioGroupListener;
import ie.ucc.bis.wizard.model.AssessmentWizardTextWatcher;
import ie.ucc.bis.wizard.model.FeverAssessmentPage;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Responsible for UI fragment to display the 
 * fever assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class FeverAssessmentFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";

    private FeverAssessmentPage feverAssessmentPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private ToggleButtonGroupTableLayout feverCustomRadioGroup;
    private RadioGroup malariaRiskRadioGroup;
    private EditText durationEditText;
    
    public static FeverAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        FeverAssessmentFragment fragment = new FeverAssessmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */
    public FeverAssessmentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setFeverAssessmentPage((FeverAssessmentPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_page_fever_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getFeverAssessmentPage().getTitle());
        
        // need to add superscript text to the degree Celsius label of a 
        // fever radio button
        RadioButton feverTempRadioButton = (RadioButton) rootView.findViewById(R.id.fever_assessment_radio_fever_temperature);
        feverTempRadioButton.setText(Html.fromHtml(getText(R.string.fever_assessment_radio_fever_temperature) + "<sup>o</sup>" + "C"));
                
        // fever
        setFeverCustomRadioGroup((ToggleButtonGroupTableLayout) rootView.findViewById(R.id.fever_assessment_radio_fever));
        getFeverCustomRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.FEVER_DATA_KEY));
        
        // malaria risk
        setMalariaRiskRadioGroup((RadioGroup) rootView.findViewById(R.id.fever_assessment_radio_malaria_risk));
        getMalariaRiskRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.MALARIA_RISK_DATA_KEY));
        
        // duration
        setDurationEditText((EditText) rootView.findViewById(R.id.fever_assessment_text_duration));
        
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

        // configure page and data key for radio button listener in 'custom fever toggle group'
        getFeverCustomRadioGroup().setPage(getFeverAssessmentPage());
        getFeverCustomRadioGroup().setDataKey(FeverAssessmentPage.FEVER_DATA_KEY);

        // add listener to malaria risk radio group
        getMalariaRiskRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.MALARIA_RISK_DATA_KEY));
        
        // listener to duration
        getDurationEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getFeverAssessmentPage(), 
        				FeverAssessmentPage.DURATION_DATA_KEY));  
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
	 * Getter Method: getFeverAssessmentPage()
	 */
	public FeverAssessmentPage getFeverAssessmentPage() {
		return feverAssessmentPage;
	}

	/**
	 * Setter Method: setFeverAssessmentPage()
	 */
	public void setFeverAssessmentPage(FeverAssessmentPage feverAssessmentPage) {
		this.feverAssessmentPage = feverAssessmentPage;
	}

	/**
	 * Getter Method: getFeverCustomRadioGroup()
	 */	
	public ToggleButtonGroupTableLayout getFeverCustomRadioGroup() {
		return feverCustomRadioGroup;
	}

	/**
	 * Setter Method: setFeverCustomRadioGroup()
	 */	
	public void setFeverCustomRadioGroup(ToggleButtonGroupTableLayout feverCustomRadioGroup) {
		this.feverCustomRadioGroup = feverCustomRadioGroup;
	}

	/**
	 * Getter Method: getMalariaRiskRadioGroup()
	 */	
	public RadioGroup getMalariaRiskRadioGroup() {
		return malariaRiskRadioGroup;
	}

	/**
	 * Setter Method: setMalariaRiskRadioGroup()
	 */	
	public void setMalariaRiskRadioGroup(RadioGroup malariaRiskRadioGroup) {
		this.malariaRiskRadioGroup = malariaRiskRadioGroup;
	}

	/**
	 * Getter Method: getDurationEditText()
	 */		
	public EditText getDurationEditText() {
		return durationEditText;
	}

	/**
	 * Setter Method: setDurationEditText()
	 */			
	public void setDurationEditText(EditText durationEditText) {
		this.durationEditText = durationEditText;
	}
}
