package ie.ucc.bis.assessment.ccm.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.ccm.model.LookCcmPage;
import ie.ucc.bis.assessment.imci.ui.PageFragmentCallbacks;
import ie.ucc.bis.assessment.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
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
 * Responsible for UI fragment to display CCM 
 * 'Look' assessment form
 * 
 * @author timothyosullivan
 * 
 */
public class LookCcmFragment extends Fragment {

	private static final String ARG_PAGE_KEY = "PAGE_KEY";

	private LookCcmPage lookCcmPage;    
	private PageFragmentCallbacks pageFragmentCallbacks;
	private String pageKey;
	private RadioGroup chestIndrawingRadioGroup;
	private RadioGroup verySleepyOrUnconsciousRadioGroup;
	private RadioGroup palmarPallorRadioGroup;
	private RadioGroup muacTapeColourRadioGroup;
	private RadioGroup swellingOfBothFeetRadioGroup;
	private EditText breathsPerMinuteEditText;
	
	public static LookCcmFragment create(String pageKey) {
		Bundle args = new Bundle();
		args.putString(ARG_PAGE_KEY, pageKey);

		LookCcmFragment fragment = new LookCcmFragment();
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Constructor
	 *
	 */
	public LookCcmFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		setPageKey(args.getString(ARG_PAGE_KEY));
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setLookCcmPage((LookCcmPage) getPageFragmentCallbacks().getPage(getPageKey()));
		
		View rootView = inflater.inflate(R.layout.fragment_ccm_page_look_assessment, container, false);
		((TextView) rootView.findViewById(android.R.id.title)).setText(getLookCcmPage().getTitle());

		// chest indrawing
		setChestIndrawingRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_look_assessment_radio_chest_indrawing));
		getChestIndrawingRadioGroup().check(getLookCcmPage().getPageData().getInt(LookCcmPage.CHEST_INDRAWING_DATA_KEY));

		// breaths per minute
		setBreathsPerMinuteEditText(((EditText) rootView.findViewById(R.id.ccm_look_assessment_breaths_per_minute)));
		getBreathsPerMinuteEditText().setText(getLookCcmPage().getPageData().getString(LookCcmPage.CHEST_INDRAWING_DATA_KEY));

		// very sleepy or unconscious
		setVerySleepyOrUnconsciousRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_look_assessment_radio_very_sleepy_or_unconscious));
		getVerySleepyOrUnconsciousRadioGroup().check(getLookCcmPage().getPageData().getInt(LookCcmPage.VERY_SLEEPY_OR_UNCONSCIOUS_DATA_KEY));

		// palmar pallor
		setPalmarPallorRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_look_assessment_radio_palmar_pallor));
		getPalmarPallorRadioGroup().check(getLookCcmPage().getPageData().getInt(LookCcmPage.PALMAR_PALLOR_DATA_KEY));
		
		// muac tape colour
		setMuacTapeColourRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_look_assessment_radio_muac_tape_colour));
		getMuacTapeColourRadioGroup().check(getLookCcmPage().getPageData().getInt(LookCcmPage.MUAC_TAPE_COLOUR_DATA_KEY));
		
		// swelling of both feet
		setSwellingOfBothFeetRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_look_assessment_radio_swelling_of_both_feet));
		getSwellingOfBothFeetRadioGroup().check(getLookCcmPage().getPageData().getInt(LookCcmPage.SWELLING_OF_BOTH_FEET_DATA_KEY));
		
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

		// add listener to chest indrawing radio group
		getChestIndrawingRadioGroup().setOnCheckedChangeListener(
				new RadioGroupListener(getLookCcmPage(),
						LookCcmPage.CHEST_INDRAWING_DATA_KEY));

		// add listener to 'breaths per minute' textfield
		getBreathsPerMinuteEditText().addTextChangedListener(
				new AssessmentWizardTextWatcher(getLookCcmPage(), 
						LookCcmPage.BREATHS_PER_MINUTE_DATA_KEY));		

		// add listener to 'very sleepy or unconscious' radio group
		getVerySleepyOrUnconsciousRadioGroup().setOnCheckedChangeListener(
				new RadioGroupListener(getLookCcmPage(),
						LookCcmPage.VERY_SLEEPY_OR_UNCONSCIOUS_DATA_KEY));
		
		// add listener to palmar pallor radio group
		getPalmarPallorRadioGroup().setOnCheckedChangeListener(
				new RadioGroupListener(getLookCcmPage(),
						LookCcmPage.PALMAR_PALLOR_DATA_KEY));
		
		// add listener to 'muac tape colour' radio group
		getMuacTapeColourRadioGroup().setOnCheckedChangeListener(
				new RadioGroupListener(getLookCcmPage(),
						LookCcmPage.MUAC_TAPE_COLOUR_DATA_KEY));
		
		// add listener to 'swelling of both feet' radio group
		getSwellingOfBothFeetRadioGroup().setOnCheckedChangeListener(
				new RadioGroupListener(getLookCcmPage(),
						LookCcmPage.SWELLING_OF_BOTH_FEET_DATA_KEY));		
	}

	/**
	 * Getter Method: getLookCcmPage()
	 */
	public LookCcmPage getLookCcmPage() {
		return lookCcmPage;
	}

	/**
	 * Setter Method: setLookCcmPage()
	 */   	
	public void setLookCcmPage(LookCcmPage lookCcmPage) {
		this.lookCcmPage = lookCcmPage;
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

	/**
	 * Getter Method: getVerySleepyOrUnconsciousRadioGroup()
	 */	
	public RadioGroup getVerySleepyOrUnconsciousRadioGroup() {
		return verySleepyOrUnconsciousRadioGroup;
	}

	/**
	 * Setter Method: setVerySleepyOrUnconsciousRadioGroup()
	 */	
	public void setVerySleepyOrUnconsciousRadioGroup(RadioGroup verySleepyOrUnconsciousRadioGroup) {
		this.verySleepyOrUnconsciousRadioGroup = verySleepyOrUnconsciousRadioGroup;
	}

	/**
	 * Getter Method: getPalmarPallorRadioGroup()
	 */	
	public RadioGroup getPalmarPallorRadioGroup() {
		return palmarPallorRadioGroup;
	}

	/**
	 * Setter Method: setPalmarPallorRadioGroup()
	 */	
	public void setPalmarPallorRadioGroup(RadioGroup palmarPallorRadioGroup) {
		this.palmarPallorRadioGroup = palmarPallorRadioGroup;
	}

	/**
	 * Getter Method: getMuacTapeColourRadioGroup()
	 */	
	public RadioGroup getMuacTapeColourRadioGroup() {
		return muacTapeColourRadioGroup;
	}

	/**
	 * Setter Method: setMuacTapeColourRadioGroup()
	 */	
	public void setMuacTapeColourRadioGroup(RadioGroup muacTapeColourRadioGroup) {
		this.muacTapeColourRadioGroup = muacTapeColourRadioGroup;
	}

	/**
	 * Getter Method: getSwellingOfBothFeetRadioGroup()
	 */	
	public RadioGroup getSwellingOfBothFeetRadioGroup() {
		return swellingOfBothFeetRadioGroup;
	}

	/**
	 * Setter Method: setSwellingOfBothFeetRadioGroup()
	 */	
	public void setSwellingOfBothFeetRadioGroup(RadioGroup swellingOfBothFeetRadioGroup) {
		this.swellingOfBothFeetRadioGroup = swellingOfBothFeetRadioGroup;
	}

	/**
	 * Getter Method: getBreathsPerMinuteEditText()
	 */	
	public EditText getBreathsPerMinuteEditText() {
		return breathsPerMinuteEditText;
	}

	/**
	 * Setter Method: setBreathsPerMinuteEditText()
	 */	
	public void setBreathsPerMinuteEditText(EditText breathsPerMinuteEditText) {
		this.breathsPerMinuteEditText = breathsPerMinuteEditText;
	}


}