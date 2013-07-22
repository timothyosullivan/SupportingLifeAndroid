package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.model.AssessmentWizardRadioGroupListener;
import ie.ucc.bis.wizard.model.FeverAssessmentPage;
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
    private RadioGroup feverRadioGroup;
    private RadioGroup malariaRiskRadioGroup;
    
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

        // fever
        setFeverRadioGroup((RadioGroup) rootView.findViewById(R.id.radio_fever));
        getFeverRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.FEVER_DATA_KEY));
        
        // malaria risk
        setMalariaRiskRadioGroup((RadioGroup) rootView.findViewById(R.id.radio_malaria_risk));
        getMalariaRiskRadioGroup().check(getFeverAssessmentPage()
        		.getPageData().getInt(FeverAssessmentPage.MALARIA_RISK_DATA_KEY));
        
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

        // add listener to fever radio group
        getFeverRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.FEVER_DATA_KEY));        
        
        // add listener to malaria risk radio group
        getMalariaRiskRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getFeverAssessmentPage(),
        				FeverAssessmentPage.MALARIA_RISK_DATA_KEY));
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
	 * Getter Method: getFeverRadioGroup()
	 */	
	public RadioGroup getFeverRadioGroup() {
		return feverRadioGroup;
	}

	/**
	 * Setter Method: setFeverRadioGroup()
	 */	
	public void setFeverRadioGroup(RadioGroup feverRadioGroup) {
		this.feverRadioGroup = feverRadioGroup;
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
}
