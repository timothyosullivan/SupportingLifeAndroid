package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.model.AssessmentWizardRadioGroupListener;
import ie.ucc.bis.wizard.model.GeneralDangerSignsPage;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Responsible for UI fragment to display general 
 * danger signs assessment of a patient
 * 
 * @author timothyosullivan
 * 
 */
public class GeneralDangerSignsFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";

    private GeneralDangerSignsPage generalDangerSignsPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private RadioGroup drinkBreastfeedRadioGroup;
    private RadioGroup vomitsRadioGroup;
    private RadioGroup convulsionsRadioGroup;
    
    public static GeneralDangerSignsFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        GeneralDangerSignsFragment fragment = new GeneralDangerSignsFragment();
        fragment.setArguments(args);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */        
    public GeneralDangerSignsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setGeneralDangerSignsPage((GeneralDangerSignsPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wizard_page_general_danger_signs, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getGeneralDangerSignsPage().getTitle());

        // not able to drink or breastfeed
        setDrinkBreastfeedRadioGroup((RadioGroup) rootView.findViewById(R.id.general_danger_signs_radio_drink_breastfeed));
        getDrinkBreastfeedRadioGroup().check(getGeneralDangerSignsPage()
        		.getPageData().getInt(GeneralDangerSignsPage.DRINK_BREASTFEED_DATA_KEY));
        
        // vomits everything
        setVomitsRadioGroup((RadioGroup) rootView.findViewById(R.id.general_danger_signs_radio_vomits));
        getVomitsRadioGroup().check(getGeneralDangerSignsPage()
        		.getPageData().getInt(GeneralDangerSignsPage.VOMITS_EVERYTHING_DATA_KEY));
        
        // history of convulsions
        setConvulsionsRadioGroup((RadioGroup) rootView.findViewById(R.id.general_danger_signs_radio_convulsions));
        getConvulsionsRadioGroup().check(getGeneralDangerSignsPage()
        		.getPageData().getInt(GeneralDangerSignsPage.HISTORY_OF_CONVULSIONS_DATA_KEY));

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

        getDrinkBreastfeedRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getGeneralDangerSignsPage(),
        				GeneralDangerSignsPage.DRINK_BREASTFEED_DATA_KEY));        
        
        getVomitsRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getGeneralDangerSignsPage(),
        				GeneralDangerSignsPage.VOMITS_EVERYTHING_DATA_KEY));
        
        getConvulsionsRadioGroup().setOnCheckedChangeListener(
        		new AssessmentWizardRadioGroupListener(getGeneralDangerSignsPage(),
        				GeneralDangerSignsPage.HISTORY_OF_CONVULSIONS_DATA_KEY));
    }

	/**
	 * Getter Method: getGeneralDangerSignsPage()
	 */
	public GeneralDangerSignsPage getGeneralDangerSignsPage() {
		return generalDangerSignsPage;
	}

	/**
	 * Setter Method: setGeneralDangerSignsPage()
	 */   	
	public void setGeneralDangerSignsPage(GeneralDangerSignsPage generalDangerSignsPage) {
		this.generalDangerSignsPage = generalDangerSignsPage;
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
	 * Getter Method: getDrinkBreastfeedRadioGroup()
	 */		
	public RadioGroup getDrinkBreastfeedRadioGroup() {
		return drinkBreastfeedRadioGroup;
	}

	/**
	 * Setter Method: setDrinkBreastfeedRadioGroup()
	 */		
	public void setDrinkBreastfeedRadioGroup(RadioGroup drinkBreastfeedRadioGroup) {
		this.drinkBreastfeedRadioGroup = drinkBreastfeedRadioGroup;
	}

	/**
	 * Getter Method: getVomitsRadioGroup()
	 */			
	public RadioGroup getVomitsRadioGroup() {
		return vomitsRadioGroup;
	}

	/**
	 * Setter Method: setVomitsRadioGroup()
	 */			
	public void setVomitsRadioGroup(RadioGroup vomitsRadioGroup) {
		this.vomitsRadioGroup = vomitsRadioGroup;
	}

	/**
	 * Getter Method: getConvulsionsRadioGroup()
	 */			
	public RadioGroup getConvulsionsRadioGroup() {
		return convulsionsRadioGroup;
	}

	/**
	 * Setter Method: setConvulsionsRadioGroup()
	 */		
	public void setConvulsionsRadioGroup(RadioGroup convulsionsRadioGroup) {
		this.convulsionsRadioGroup = convulsionsRadioGroup;
	}
}
