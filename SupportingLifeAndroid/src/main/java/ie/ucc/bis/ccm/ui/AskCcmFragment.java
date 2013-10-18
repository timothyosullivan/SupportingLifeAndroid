package ie.ucc.bis.ccm.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.assessment.model.listener.DynamicViewListenerUtilities;
import ie.ucc.bis.assessment.model.listener.RadioGroupCoordinatorListener;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.ccm.model.AskCcmPage;
import ie.ucc.bis.imci.model.DynamicView;
import ie.ucc.bis.imci.ui.PageFragmentCallbacks;
import ie.ucc.bis.ui.utilities.ViewGroupUtilities;

import java.util.Arrays;

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
 * 'Ask' assessment form
 * 
 * @author timothyosullivan
 * 
 */
public class AskCcmFragment extends Fragment {

	private static final String ARG_PAGE_KEY = "PAGE_KEY";

	private AskCcmPage askCcmPage;    
	private PageFragmentCallbacks pageFragmentCallbacks;
	private String pageKey;
	private EditText problemsEditText;
	private RadioGroup coughRadioGroup;
	private RadioGroup diarrhoeaRadioGroup;
	private RadioGroup bloodInStoolRadioGroup;
	private RadioGroup feverRadioGroup;
	private RadioGroup convulsionsRadioGroup;
	private RadioGroup drinkFeedDifficultyRadioGroup;
	private RadioGroup unableDrinkFeedRadioGroup;
	private EditText coughDurationEditText;
	private EditText diarrhoeaDurationEditText;
	private EditText feverDurationEditText;
	private ViewGroup animatedTopLevelView;
	private View coughView;
	private View diarrhoeaView;
	private View feverView;
	private View drinkFeedView;
	private DynamicView coughDurationDynamicView;
	private DynamicView diarrhoeaDurationDynamicView;
	private DynamicView feverDurationDynamicView;
	private DynamicView drinkFeedDynamicView;
	private Boolean animatedCoughDurationViewInVisibleState;
	private Boolean animatedDiarrhoeaDurationViewInVisibleState;
	private Boolean animatedFeverDurationViewInVisibleState;
	private Boolean animatedUnableDrinkFeedViewInVisibleState;

	public static AskCcmFragment create(String pageKey) {
		Bundle args = new Bundle();
		args.putString(ARG_PAGE_KEY, pageKey);

		AskCcmFragment fragment = new AskCcmFragment();
		fragment.setArguments(args);
		fragment.setAnimatedCoughDurationViewInVisibleState(false);
		fragment.setAnimatedDiarrhoeaDurationViewInVisibleState(false);
		fragment.setAnimatedFeverDurationViewInVisibleState(false);
		fragment.setAnimatedUnableDrinkFeedViewInVisibleState(false);
		return fragment;
	}

	/**
	 * Constructor
	 *
	 */
	public AskCcmFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		setPageKey(args.getString(ARG_PAGE_KEY));
		setAskCcmPage((AskCcmPage) getPageFragmentCallbacks().getPage(getPageKey()));
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// take record of visibility of 'Cough Duration' view
		if (getAnimatedTopLevelView().indexOfChild(getCoughDurationDynamicView().getWrappedView()) != -1) {
			savedInstanceState.putBoolean("animatedCoughDurationViewInVisibleState", true);
		}
		else {
			savedInstanceState.putBoolean("animatedCoughDurationViewInVisibleState", false);
		}

		// take record of visibility of 'Diarrhoea Duration' view
		if (getAnimatedTopLevelView().indexOfChild(getDiarrhoeaDurationDynamicView().getWrappedView()) != -1) {
			savedInstanceState.putBoolean("animatedDiarrhoeaDurationViewInVisibleState", true);
		}
		else {
			savedInstanceState.putBoolean("animatedDiarrhoeaDurationViewInVisibleState", false);
		}
		
		// take record of visibility of 'Fever Duration' view
		if (getAnimatedTopLevelView().indexOfChild(getFeverDurationDynamicView().getWrappedView()) != -1) {
			savedInstanceState.putBoolean("animatedFeverDurationViewInVisibleState", true);
		}
		else {
			savedInstanceState.putBoolean("animatedFeverDurationViewInVisibleState", false);
		}
		
		// take record of visibility of 'Not Able to Drink or Feed' view
		if (getAnimatedTopLevelView().indexOfChild(getDrinkFeedDynamicView().getWrappedView()) != -1) {
			savedInstanceState.putBoolean("animatedUnableDrinkFeedViewInVisibleState", true);
		}
		else {
			savedInstanceState.putBoolean("animatedUnableDrinkFeedViewInVisibleState", false);
		}
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null) {
			setAnimatedCoughDurationViewInVisibleState(savedInstanceState.getBoolean("animatedCoughDurationViewInVisibleState"));
			setAnimatedDiarrhoeaDurationViewInVisibleState(savedInstanceState.getBoolean("animatedDiarrhoeaDurationViewInVisibleState"));
			setAnimatedFeverDurationViewInVisibleState(savedInstanceState.getBoolean("animatedFeverDurationViewInVisibleState"));
			setAnimatedUnableDrinkFeedViewInVisibleState(savedInstanceState.getBoolean("animatedUnableDrinkFeedViewInVisibleState"));
		}

		// restore visibility of 'Cough Duration' view
		if (!isAnimatedCoughDurationViewInVisibleState()) {
			ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getCoughDurationDynamicView()));
		}

		// restore visibility of 'Diarrhoea Duration' view 
		if (!isAnimatedDiarrhoeaDurationViewInVisibleState()) {
			ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getDiarrhoeaDurationDynamicView()));
		}
		
		// restore visibility of 'Fever Duration' view 
		if (!isAnimatedFeverDurationViewInVisibleState()) {
			ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getFeverDurationDynamicView()));
		}
		
		// restore visibility of 'Not Able to Drink or Feed' view 
		if (!isAnimatedUnableDrinkFeedViewInVisibleState()) {
			ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getDrinkFeedDynamicView()));
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ccm_page_ask_assessment, container, false);
		((TextView) rootView.findViewById(android.R.id.title)).setText(getAskCcmPage().getTitle());

		// get a hold on the top level animated view
		setAnimatedTopLevelView(((ViewGroup) rootView.findViewById(R.id.ccm_ask_assessment_animated_top_level_view)));

		// child's problems
		setProblemsEditText(((EditText) rootView.findViewById(R.id.ccm_ask_assessment_problems)));
		getProblemsEditText().setText(getAskCcmPage().getPageData().getString(AskCcmPage.PROBLEMS_DATA_KEY));

		// cough
		configureCoughDurationAnimatedView(rootView);

		// diarrhoea
		configureDiarrhoeaDurationAnimateView(rootView);

		// blood in stool
		setBloodInStoolRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_assessment_radio_blood_in_stool));
		getBloodInStoolRadioGroup().check(getAskCcmPage()
				.getPageData().getInt(AskCcmPage.BLOOD_IN_STOOL_DATA_KEY));

		// fever
		configureFeverDurationAnimateView(rootView);
		
		// convulsions
		setConvulsionsRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_assessment_radio_convulsions));
		getConvulsionsRadioGroup().check(getAskCcmPage()
				.getPageData().getInt(AskCcmPage.CONVULSIONS_DATA_KEY));
		
		// difficulty drinking or feeding
		configureDrinkFeedAnimateView(rootView);

		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);

		return rootView;
	}

	/**
	 * configureCoughDurationAnimatedView(View rootView)
	 * 
	 * Method responsible for configuring a the dynamic animation of 'cough duration'
	 * relating to the 'cough' radio button click event.
	 * 
	 * @param rootView
	 * 
	 */
	private void configureCoughDurationAnimatedView(View rootView) {
		// cough view
		setCoughView((View) rootView.findViewById(R.id.ccm_ask_assessment_view_cough));

		// cough radio group
		setCoughRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_assessment_radio_cough));
		getCoughRadioGroup().check(getAskCcmPage()
				.getPageData().getInt(AskCcmPage.COUGH_DATA_KEY));

		// cough duration
		setCoughDurationEditText((EditText) rootView.findViewById(R.id.ccm_ask_assessment_cough_duration));
		getCoughDurationEditText().setText(getAskCcmPage().getPageData().getString(AskCcmPage.COUGH_DURATION_DATA_KEY));

		// 'cough duration' is a dynamic view within the UI
		setCoughDurationDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_ask_assessment_view_cough_duration),
				rootView.findViewById(R.id.ccm_ask_assessment_cough_duration)));
	}

	/**
	 * configureDiarrhoeaDurationAnimateView(View rootView)
	 * 
	 * Method responsible for configuring a the dynamic animation of 'diarrhoea duration'
	 * relating to the 'diarrhoea' radio button click event.
	 * 
	 * @param rootView
	 * 
	 */
	private void configureDiarrhoeaDurationAnimateView(View rootView) {
		// diarrhoea view
		setDiarrhoeaView((View) rootView.findViewById(R.id.ccm_ask_assessment_view_diarrhoea));

		// diarrhoea radio group
		setDiarrhoeaRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_assessment_radio_diarrhoea));
		getDiarrhoeaRadioGroup().check(getAskCcmPage()
				.getPageData().getInt(AskCcmPage.DIARRHOEA_DATA_KEY));

		// diarrhoea duration
		setDiarrhoeaDurationEditText((EditText) rootView.findViewById(R.id.ccm_ask_assessment_diarrhoea_duration));
		getDiarrhoeaDurationEditText().setText(getAskCcmPage().getPageData().getString(AskCcmPage.DIARRHOEA_DURATION_DATA_KEY));

		// 'diarrhoea duration' is a dynamic view within the UI
		setDiarrhoeaDurationDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_ask_assessment_view_diarrhoea_duration),
				rootView.findViewById(R.id.ccm_ask_assessment_diarrhoea_duration)));
	}

	/**
	 * configureFeverDurationAnimateView(View rootView)
	 * 
	 * Method responsible for configuring a the dynamic animation of 'fever duration'
	 * relating to the 'fever' radio button click event.
	 * 
	 * @param rootView
	 * 
	 */
	private void configureFeverDurationAnimateView(View rootView) {
		// fever view
		setFeverView((View) rootView.findViewById(R.id.ccm_ask_assessment_view_fever));

		// fever radio group
		setFeverRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_assessment_radio_fever));
		getFeverRadioGroup().check(getAskCcmPage()
				.getPageData().getInt(AskCcmPage.FEVER_DATA_KEY));

		// fever duration
		setFeverDurationEditText((EditText) rootView.findViewById(R.id.ccm_ask_assessment_fever_duration));
		getFeverDurationEditText().setText(getAskCcmPage().getPageData().getString(AskCcmPage.FEVER_DURATION_DATA_KEY));

		// 'fever duration' is a dynamic view within the UI
		setFeverDurationDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_ask_assessment_view_fever_duration),
				rootView.findViewById(R.id.ccm_ask_assessment_fever_duration)));
	}
	
	/**
	 * configureDrinkFeedAnimateView(View rootView)
	 * 
	 * Method responsible for configuring a the dynamic animation of 
	 * 'not able to drink or feed anything' radio group
	 * relating to the 'difficulty drinking or feeding' radio button click event.
	 * 
	 * @param rootView
	 * 
	 */
	private void configureDrinkFeedAnimateView(View rootView) {
		// 'difficulty drinking or feeding' view
		setDrinkFeedView((View) rootView.findViewById(R.id.ccm_ask_assessment_view_drink_or_feed));

		// 'difficulty drinking or feeding' radio group
		setDrinkFeedDifficultyRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_assessment_radio_drink_or_feed_difficulty));
		getDrinkFeedDifficultyRadioGroup().check(getAskCcmPage()
				.getPageData().getInt(AskCcmPage.DRINK_OR_FEED_DIFFICULTY_DATA_KEY));

		// 'not able to drink or feed anything' radio group
		setUnableDrinkFeedRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_assessment_radio_unable_to_drink_or_feed));
		getUnableDrinkFeedRadioGroup().check(getAskCcmPage()
				.getPageData().getInt(AskCcmPage.UNABLE_TO_DRINK_OR_FEED_DATA_KEY));

		// 'not able to drink or feed anything' radio group is a dynamic view within the UI
		setDrinkFeedDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_ask_assessment_view_unable_to_drink_or_feed),
				rootView.findViewById(R.id.ccm_ask_assessment_radio_unable_to_drink_or_feed)));
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

		// child's problems
		getProblemsEditText().addTextChangedListener(
				new AssessmentWizardTextWatcher(getAskCcmPage(), 
						AskCcmPage.PROBLEMS_DATA_KEY));

		// add dynamic view listener to cough radio group
		DynamicViewListenerUtilities.addGenericDynamicViewListeners(getCoughView(), getCoughDurationDynamicView(),
				getAnimatedTopLevelView(),
				getCoughRadioGroup(), getCoughDurationEditText(),
				AskCcmPage.COUGH_DATA_KEY, AskCcmPage.COUGH_DURATION_DATA_KEY, 
				getResources(), getAskCcmPage());

		// add dynamic view listener to diarrhoea radio group
		DynamicViewListenerUtilities.addGenericDynamicViewListeners(getDiarrhoeaView(), getDiarrhoeaDurationDynamicView(),
				getAnimatedTopLevelView(),
				getDiarrhoeaRadioGroup(), getDiarrhoeaDurationEditText(),
				AskCcmPage.DIARRHOEA_DATA_KEY, AskCcmPage.DIARRHOEA_DURATION_DATA_KEY,
				getResources(), getAskCcmPage());

		// add listener to blood in stool radio group
		getBloodInStoolRadioGroup().setOnCheckedChangeListener(
				new RadioGroupListener(getAskCcmPage(),
						AskCcmPage.BLOOD_IN_STOOL_DATA_KEY));		 

		// add dynamic view listener to fever radio group
		DynamicViewListenerUtilities.addGenericDynamicViewListeners(getFeverView(), getFeverDurationDynamicView(),
				getAnimatedTopLevelView(),
				getFeverRadioGroup(), getFeverDurationEditText(),
				AskCcmPage.FEVER_DATA_KEY, AskCcmPage.FEVER_DURATION_DATA_KEY,
				getResources(), getAskCcmPage());
		
		// add listener to convulsions radio group
		getConvulsionsRadioGroup().setOnCheckedChangeListener(
				new RadioGroupListener(getAskCcmPage(),
						AskCcmPage.CONVULSIONS_DATA_KEY));

		// add dynamic view listener to 'difficulty drinking or feeding' radio group
        getDrinkFeedDifficultyRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupCoordinatorListener(getAskCcmPage(),
        				AskCcmPage.DRINK_OR_FEED_DIFFICULTY_DATA_KEY, 
        				Arrays.asList(getDrinkFeedDynamicView()),
        				getAnimatedTopLevelView(),
        				getDrinkFeedView()));
        
		// add listener to 'not able to drink or feed anything' radio group
		getUnableDrinkFeedRadioGroup().setOnCheckedChangeListener(
				new RadioGroupListener(getAskCcmPage(),
						AskCcmPage.UNABLE_TO_DRINK_OR_FEED_DATA_KEY));    
	}

	/**
	 * Getter Method: getAskCcmPage()
	 */
	public AskCcmPage getAskCcmPage() {
		return askCcmPage;
	}

	/**
	 * Setter Method: setAskCcmPage()
	 */   	
	public void setAskCcmPage(AskCcmPage askCcmPage) {
		this.askCcmPage = askCcmPage;
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
	 * Getter Method: getProblemsEditText()
	 */
	public EditText getProblemsEditText() {
		return problemsEditText;
	}

	/**
	 * Setter Method: setProblemsEditText()
	 */
	public void setProblemsEditText(EditText problemsEditText) {
		this.problemsEditText = problemsEditText;
	}

	/**
	 * Getter Method: getCoughRadioGroup()
	 */
	public RadioGroup getCoughRadioGroup() {
		return coughRadioGroup;
	}

	/**
	 * Setter Method: setCoughRadioGroup()
	 */
	public void setCoughRadioGroup(RadioGroup coughRadioGroup) {
		this.coughRadioGroup = coughRadioGroup;
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
	 * Getter Method: getBloodInStoolRadioGroup()
	 */
	public RadioGroup getBloodInStoolRadioGroup() {
		return bloodInStoolRadioGroup;
	}

	/**
	 * Setter Method: setBloodInStoolRadioGroup()
	 */
	public void setBloodInStoolRadioGroup(RadioGroup bloodInStoolRadioGroup) {
		this.bloodInStoolRadioGroup = bloodInStoolRadioGroup;
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
	
	/**
	 * Getter Method: getDrinkFeedDifficultyRadioGroup()
	 */
	public RadioGroup getDrinkFeedDifficultyRadioGroup() {
		return drinkFeedDifficultyRadioGroup;
	}

	/**
	 * Setter Method: setDrinkFeedDifficultyRadioGroup()
	 */
	public void setDrinkFeedDifficultyRadioGroup(RadioGroup drinkFeedDifficultyRadioGroup) {
		this.drinkFeedDifficultyRadioGroup = drinkFeedDifficultyRadioGroup;
	}

	/**
	 * Getter Method: getUnableDrinkFeedRadioGroup()
	 */
	public RadioGroup getUnableDrinkFeedRadioGroup() {
		return unableDrinkFeedRadioGroup;
	}

	/**
	 * Setter Method: setUnableDrinkFeedRadioGroup()
	 */
	public void setUnableDrinkFeedRadioGroup(RadioGroup unableDrinkFeedRadioGroup) {
		this.unableDrinkFeedRadioGroup = unableDrinkFeedRadioGroup;
	}
	
	/**
	 * Getter Method: getCoughDurationEditText()
	 */
	public EditText getCoughDurationEditText() {
		return coughDurationEditText;
	}

	/**
	 * Setter Method: setCoughDurationEditText()
	 */
	public void setCoughDurationEditText(EditText coughDurationEditText) {
		this.coughDurationEditText = coughDurationEditText;
	}

	/**
	 * Getter Method: getDiarrhoeaDurationEditText()
	 */
	public EditText getDiarrhoeaDurationEditText() {
		return diarrhoeaDurationEditText;
	}

	/**
	 * Setter Method: setDiarrhoeaDurationEditText()
	 */
	public void setDiarrhoeaDurationEditText(EditText diarrhoeaDurationEditText) {
		this.diarrhoeaDurationEditText = diarrhoeaDurationEditText;
	}

	/**
	 * Getter Method: getFeverDurationEditText()
	 */
	public EditText getFeverDurationEditText() {
		return feverDurationEditText;
	}

	/**
	 * Setter Method: setFeverDurationEditText()
	 */
	public void setFeverDurationEditText(EditText feverDurationEditText) {
		this.feverDurationEditText = feverDurationEditText;
	}
	
	/**
	 * Getter Method: getAnimatedTopLevelView()
	 */
	public ViewGroup getAnimatedTopLevelView() {
		return animatedTopLevelView;
	}

	/**
	 * Setter Method: setAnimatedTopLevelView()
	 */
	public void setAnimatedTopLevelView(ViewGroup animatedTopLevelView) {
		this.animatedTopLevelView = animatedTopLevelView;
	}

	/**
	 * Getter Method: getCoughView()
	 */
	public View getCoughView() {
		return coughView;
	}

	/**
	 * Setter Method: setCoughView()
	 */
	public void setCoughView(View coughView) {
		this.coughView = coughView;
	}

	/**
	 * Getter Method: getDiarrhoeaView()
	 */
	public View getDiarrhoeaView() {
		return diarrhoeaView;
	}

	/**
	 * Setter Method: setDiarrhoeaView()
	 */
	public void setDiarrhoeaView(View diarrhoeaView) {
		this.diarrhoeaView = diarrhoeaView;
	}
	
	/**
	 * Getter Method: getFeverView()
	 */
	public View getFeverView() {
		return feverView;
	}

	/**
	 * Setter Method: setFeverView()
	 */
	public void setFeverView(View feverView) {
		this.feverView = feverView;
	}

	/**
	 * Getter Method: getDrinkFeedView()
	 */
	public View getDrinkFeedView() {
		return drinkFeedView;
	}

	/**
	 * Setter Method: setDrinkFeedView()
	 */
	public void setDrinkFeedView(View drinkFeedView) {
		this.drinkFeedView = drinkFeedView;
	}
	
	/**
	 * Getter Method: getCoughDurationDynamicView()
	 */
	public DynamicView getCoughDurationDynamicView() {
		return coughDurationDynamicView;
	}

	/**
	 * Setter Method: setCoughDurationDynamicView()
	 */
	public void setCoughDurationDynamicView(DynamicView coughDurationDynamicView) {
		this.coughDurationDynamicView = coughDurationDynamicView;
	}

	/**
	 * Getter Method: getDiarrhoeaDurationDynamicView()
	 */
	public DynamicView getDiarrhoeaDurationDynamicView() {
		return diarrhoeaDurationDynamicView;
	}

	/**
	 * Setter Method: setDiarrhoeaDurationDynamicView()
	 */
	public void setDiarrhoeaDurationDynamicView(DynamicView diarrhoeaDurationDynamicView) {
		this.diarrhoeaDurationDynamicView = diarrhoeaDurationDynamicView;
	}
	
	/**
	 * Getter Method: getFeverDurationDynamicView()
	 */
	public DynamicView getFeverDurationDynamicView() {
		return feverDurationDynamicView;
	}

	/**
	 * Setter Method: setFeverDurationDynamicView()
	 */
	public void setFeverDurationDynamicView(DynamicView feverDurationDynamicView) {
		this.feverDurationDynamicView = feverDurationDynamicView;
	}

	/**
	 * Getter Method: getDrinkFeedDDynamicView()
	 */
	public DynamicView getDrinkFeedDynamicView() {
		return drinkFeedDynamicView;
	}

	/**
	 * Setter Method: setDrinkFeedDynamicView()
	 */
	public void setDrinkFeedDynamicView(DynamicView drinkFeedDynamicView) {
		this.drinkFeedDynamicView = drinkFeedDynamicView;
	}

	
	/**
	 * Getter Method: isAnimatedCoughDurationViewInVisibleState()
	 */
	public Boolean isAnimatedCoughDurationViewInVisibleState() {
		return animatedCoughDurationViewInVisibleState;
	}
	
	/**
	 * Setter Method: setAnimatedCoughDurationViewInVisibleState()
	 */
	public void setAnimatedCoughDurationViewInVisibleState(Boolean animatedCoughDurationViewInVisibleState) {
		this.animatedCoughDurationViewInVisibleState = animatedCoughDurationViewInVisibleState;
	}

	/**
	 * Getter Method: isAnimatedDiarrhoeaDurationViewInVisibleState()
	 */
	public Boolean isAnimatedDiarrhoeaDurationViewInVisibleState() {
		return animatedDiarrhoeaDurationViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedCoughDurationViewInVisibleState()
	 */
	public void setAnimatedDiarrhoeaDurationViewInVisibleState(Boolean animatedDiarrhoeaDurationViewInVisibleState) {
		this.animatedDiarrhoeaDurationViewInVisibleState = animatedDiarrhoeaDurationViewInVisibleState;
	}

	/**
	 * Getter Method: isAnimatedFeverDurationViewInVisibleState()
	 */
	public Boolean isAnimatedFeverDurationViewInVisibleState() {
		return animatedFeverDurationViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedFeverDurationViewInVisibleState()
	 */
	public void setAnimatedFeverDurationViewInVisibleState(Boolean animatedFeverDurationViewInVisibleState) {
		this.animatedFeverDurationViewInVisibleState = animatedFeverDurationViewInVisibleState;
	}

	/**
	 * Getter Method: isAnimatedUnableDrinkFeedViewInVisibleState()
	 */
	public Boolean isAnimatedUnableDrinkFeedViewInVisibleState() {
		return animatedUnableDrinkFeedViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedUnableDrinkFeedViewInVisibleState()
	 */
	public void setAnimatedUnableDrinkFeedViewInVisibleState(Boolean animatedUnableDrinkFeedViewInVisibleState) {
		this.animatedUnableDrinkFeedViewInVisibleState = animatedUnableDrinkFeedViewInVisibleState;
	}
}