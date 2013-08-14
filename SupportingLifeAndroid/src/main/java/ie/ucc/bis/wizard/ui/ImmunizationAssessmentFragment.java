package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.wizard.model.ImmunizationAssessmentPage;
import ie.ucc.bis.wizard.model.listener.RadioGroupListener;
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
 * immunization status assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class ImmunizationAssessmentFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";

    private ImmunizationAssessmentPage immunizationAssessmentPage;
	private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private RadioGroup bcgVaccineRadioGroup;
    private RadioGroup measlesVaccineRadioGroup;
    
    public static ImmunizationAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        ImmunizationAssessmentFragment fragment = new ImmunizationAssessmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */
    public ImmunizationAssessmentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setImmunizationAssessmentPage((ImmunizationAssessmentPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_page_immunization_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getImmunizationAssessmentPage().getTitle());
                
        // Vaccine: BCG
        setBcgVaccineRadioGroup((RadioGroup) rootView.findViewById(R.id.immunization_assessment_radio_vaccine_bcg));
        getBcgVaccineRadioGroup().check(getImmunizationAssessmentPage()
        		.getPageData().getInt(ImmunizationAssessmentPage.BCG_VACCINE_DATA_KEY));
        
        // Vaccine: Measles
        setMeaslesVaccineRadioGroup((RadioGroup) rootView.findViewById(R.id.immunization_assessment_radio_vaccine_measles));
        getMeaslesVaccineRadioGroup().check(getImmunizationAssessmentPage()
        		.getPageData().getInt(ImmunizationAssessmentPage.MEASLES_VACCINE_DATA_KEY));
                
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

        // add listener to BCG Vaccine radio group
        getBcgVaccineRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getImmunizationAssessmentPage(),
        				ImmunizationAssessmentPage.BCG_VACCINE_DATA_KEY));
        
        // add listener to Measles Vaccine radio group
        getMeaslesVaccineRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getImmunizationAssessmentPage(),
        				ImmunizationAssessmentPage.MEASLES_VACCINE_DATA_KEY));
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
	 * Getter Method: getImmunizationAssessmentPage()
	 */	
    public ImmunizationAssessmentPage getImmunizationAssessmentPage() {
		return immunizationAssessmentPage;
	}

	/**
	 * Setter Method: setImmunizationAssessmentPage()
	 */
	public void setImmunizationAssessmentPage(ImmunizationAssessmentPage immunizationAssessmentPage) {
		this.immunizationAssessmentPage = immunizationAssessmentPage;
	}

	/**
	 * Getter Method: getBcgVaccineRadioGroup()
	 */	
	public RadioGroup getBcgVaccineRadioGroup() {
		return bcgVaccineRadioGroup;
	}

	/**
	 * Setter Method: setBcgVaccineRadioGroup()
	 */
	public void setBcgVaccineRadioGroup(RadioGroup bcgVaccineRadioGroup) {
		this.bcgVaccineRadioGroup = bcgVaccineRadioGroup;
	}

	/**
	 * Getter Method: getMeaslesVaccineRadioGroup()
	 */	
	public RadioGroup getMeaslesVaccineRadioGroup() {
		return measlesVaccineRadioGroup;
	}

	/**
	 * Setter Method: setMeaslesVaccineRadioGroup()
	 */
	public void setMeaslesVaccineRadioGroup(RadioGroup measlesVaccineRadioGroup) {
		this.measlesVaccineRadioGroup = measlesVaccineRadioGroup;
	}
}
