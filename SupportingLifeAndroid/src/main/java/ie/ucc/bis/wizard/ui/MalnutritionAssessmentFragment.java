package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.wizard.model.AssessmentWizardRadioGroupListener;
import ie.ucc.bis.wizard.model.MalnutritionAssessmentPage;
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
 * malnutrition and anaemia assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class MalnutritionAssessmentFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";

    private MalnutritionAssessmentPage malnutritionAssessmentPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private RadioGroup oedemaRadioGroup;
    private RadioGroup weightForAgeRadioGroup;
    private RadioGroup visibleSevereWastingRadioGroup;
    private RadioGroup palmarPallorRadioGroup;
    private RadioGroup mebendazoleDoseRadioGroup;    
    
    public static MalnutritionAssessmentFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        MalnutritionAssessmentFragment fragment = new MalnutritionAssessmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */
    public MalnutritionAssessmentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setMalnutritionAssessmentPage((MalnutritionAssessmentPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_page_malnutrition_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getMalnutritionAssessmentPage().getTitle());
                
        // oedema
        setOedemaRadioGroup((RadioGroup) rootView.findViewById(R.id.malnutrition_assessment_radio_oedema));
        getOedemaRadioGroup().check(getMalnutritionAssessmentPage()
        		.getPageData().getInt(MalnutritionAssessmentPage.OEDEMA_DATA_KEY));
        
        // weight for age
        setWeightForAgeRadioGroup((RadioGroup) rootView.findViewById(R.id.malnutrition_assessment_radio_weight_for_age));
        getWeightForAgeRadioGroup().check(getMalnutritionAssessmentPage()
        		.getPageData().getInt(MalnutritionAssessmentPage.WEIGHT_FOR_AGE_DATA_KEY));
        
        // visible severe wasting
        setVisibleSevereWastingRadioGroup((RadioGroup) rootView.findViewById(R.id.malnutrition_assessment_radio_visible_severe_wasting));
        getVisibleSevereWastingRadioGroup().check(getMalnutritionAssessmentPage()
        		.getPageData().getInt(MalnutritionAssessmentPage.VISIBLE_SEVERE_WASTING_DATA_KEY));
        
        // palmar pallor
        setPalmarPallorRadioGroup((RadioGroup) rootView.findViewById(R.id.malnutrition_assessment_radio_palmar_pallor));
        getPalmarPallorRadioGroup().check(getMalnutritionAssessmentPage()
        		.getPageData().getInt(MalnutritionAssessmentPage.PALMAR_PALLOR_DATA_KEY));
        
        // mebendazaloe dose
        setMebendazoleDoseRadioGroup((RadioGroup) rootView.findViewById(R.id.malnutrition_assessment_radio_mebendazole_dose));
        getMebendazoleDoseRadioGroup().check(getMalnutritionAssessmentPage()
        		.getPageData().getInt(MalnutritionAssessmentPage.MEBENDAZOLE_DOSE_DATA_KEY));
        
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

        // add listener to oedema radio group
        getOedemaRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getMalnutritionAssessmentPage(),
        				MalnutritionAssessmentPage.OEDEMA_DATA_KEY));
        
        // add listener to weight for age radio group
        getWeightForAgeRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getMalnutritionAssessmentPage(),
        				MalnutritionAssessmentPage.WEIGHT_FOR_AGE_DATA_KEY));
        
        // add listener to visible severe wasting radio group
        getVisibleSevereWastingRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getMalnutritionAssessmentPage(),
        				MalnutritionAssessmentPage.VISIBLE_SEVERE_WASTING_DATA_KEY));
        
        // add listener to palmar pallor radio group
        getPalmarPallorRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getMalnutritionAssessmentPage(),
        				MalnutritionAssessmentPage.PALMAR_PALLOR_DATA_KEY));
        
        // add listener to visible severe wasting radio group
        getMebendazoleDoseRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getMalnutritionAssessmentPage(),
        				MalnutritionAssessmentPage.MEBENDAZOLE_DOSE_DATA_KEY));
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
	 * Getter Method: getMalnutritionAssessmentPage()
	 */		
	public MalnutritionAssessmentPage getMalnutritionAssessmentPage() {
		return malnutritionAssessmentPage;
	}

	/**
	 * Setter Method: setMalnutritionAssessmentPage()
	 */	
	public void setMalnutritionAssessmentPage(MalnutritionAssessmentPage malnutritionAssessmentPage) {
		this.malnutritionAssessmentPage = malnutritionAssessmentPage;
	}

	/**
	 * Getter Method: getOedemaRadioGroup()
	 */	
	public RadioGroup getOedemaRadioGroup() {
		return oedemaRadioGroup;
	}

	/**
	 * Setter Method: setOedemaRadioGroup()
	 */		
	public void setOedemaRadioGroup(RadioGroup oedemaRadioGroup) {
		this.oedemaRadioGroup = oedemaRadioGroup;
	}

	/**
	 * Getter Method: getWeightForAgeRadioGroup()
	 */		
	public RadioGroup getWeightForAgeRadioGroup() {
		return weightForAgeRadioGroup;
	}

	/**
	 * Setter Method: setWeightForAgeRadioGroup()
	 */			
	public void setWeightForAgeRadioGroup(RadioGroup weightForAgeRadioGroup) {
		this.weightForAgeRadioGroup = weightForAgeRadioGroup;
	}

	/**
	 * Getter Method: getVisibleSevereWastingRadioGroup()
	 */			
	public RadioGroup getVisibleSevereWastingRadioGroup() {
		return visibleSevereWastingRadioGroup;
	}

	/**
	 * Setter Method: setVisibleSevereWastingRadioGroup()
	 */			
	public void setVisibleSevereWastingRadioGroup(RadioGroup visibleSevereWastingRadioGroup) {
		this.visibleSevereWastingRadioGroup = visibleSevereWastingRadioGroup;
	}

	/**
	 * Getter Method: getPalmarPallorRadioGroup()
	 */	
	private RadioGroup getPalmarPallorRadioGroup() {
		return palmarPallorRadioGroup;
	}

	/**
	 * Setter Method: setPalmarPallorRadioGroup()
	 */	
	private void setPalmarPallorRadioGroup(RadioGroup palmarPallorRadioGroup) {
		this.palmarPallorRadioGroup = palmarPallorRadioGroup;
	}

	/**
	 * Getter Method: getMebendazoleDoseRadioGroup()
	 */	
	private RadioGroup getMebendazoleDoseRadioGroup() {
		return mebendazoleDoseRadioGroup;
	}

	/**
	 * Setter Method: setMebendazoleDoseRadioGroup()
	 */	
	private void setMebendazoleDoseRadioGroup(RadioGroup mebendazoleDoseRadioGroup) {
		this.mebendazoleDoseRadioGroup = mebendazoleDoseRadioGroup;
	}
}
