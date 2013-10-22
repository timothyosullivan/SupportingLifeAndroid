package ie.ucc.bis.ccm.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.listener.DynamicViewListenerUtilities;
import ie.ucc.bis.assessment.model.listener.RadioGroupCoordinatorListener;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.ccm.model.SecondaryAskCcmPage;
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
 * Secondary 'Ask' assessment form
 * 
 * @author timothyosullivan
 * 
 */
public class SecondaryAskCcmFragment extends Fragment {

	private static final String ARG_PAGE_KEY = "PAGE_KEY";

	private SecondaryAskCcmPage secondaryAskCcmPage;    
	private PageFragmentCallbacks pageFragmentCallbacks;
	private String pageKey;
	private RadioGroup vomitingRadioGroup;
	private RadioGroup vomitsEverythingRadioGroup;
	private RadioGroup redEyesRadioGroup;
	private RadioGroup seeingDifficultyRadioGroup;
	private RadioGroup cannotTreatProblemsRadioGroup;
	private EditText redEyesDurationEditText;
	private EditText seeingDifficultyDurationEditText;
	private EditText cannotTreatProblemsDetailsEditText;
	private ViewGroup animatedTopLevelView;
	private View vomitingView;
	private View redEyesView;
	private View seeingDifficultyView;
	private View cannotTreatProblemsView;
	private DynamicView vomitingDynamicView;
	private DynamicView redEyesDurationDynamicView;
	private DynamicView seeingDifficultyDurationDynamicView;
	private DynamicView cannotTreatProblemsDetailsDynamicView;
	private Boolean animatedVomitsEverythingViewInVisibleState;
	private Boolean animatedRedEyesDurationViewInVisibleState;
	private Boolean animatedSeeingDifficultyDurationViewInVisibleState;
	private Boolean animatedCannotTreatProblemsDetailsViewInVisibleState;

	public static SecondaryAskCcmFragment create(String pageKey) {
		Bundle args = new Bundle();
		args.putString(ARG_PAGE_KEY, pageKey);

		SecondaryAskCcmFragment fragment = new SecondaryAskCcmFragment();
		fragment.setArguments(args);
		fragment.setAnimatedVomitsEverythingViewInVisibleState(false);
		fragment.setAnimatedRedEyesDurationViewInVisibleState(false);
		fragment.setAnimatedSeeingDifficultyDurationViewInVisibleState(false);
		fragment.setAnimatedCannotTreatProblemsDetailsViewInVisibleState(false);
		return fragment;
	}

	/**
	 * Constructor
	 *
	 */
	public SecondaryAskCcmFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		setPageKey(args.getString(ARG_PAGE_KEY));
		setSecondaryAskCcmPage((SecondaryAskCcmPage) getPageFragmentCallbacks().getPage(getPageKey()));
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// take record of visibility of 'Vomits Everything' view
		if (getAnimatedTopLevelView().indexOfChild(getVomitingDynamicView().getWrappedView()) != -1) {
			savedInstanceState.putBoolean("animatedVomitsEverythingViewInVisibleState", true);
		}
		else {
			savedInstanceState.putBoolean("animatedVomitsEverythingViewInVisibleState", false);
		}

		// take record of visibility of 'Red Eyes Duration' view
		if (getAnimatedTopLevelView().indexOfChild(getRedEyesDurationDynamicView().getWrappedView()) != -1) {
			savedInstanceState.putBoolean("animatedRedEyesDurationViewInVisibleState", true);
		}
		else {
			savedInstanceState.putBoolean("animatedRedEyesDurationViewInVisibleState", false);
		}

		// take record of visibility of 'Seeing Difficulty Duration' view
		if (getAnimatedTopLevelView().indexOfChild(getSeeingDifficultyDurationDynamicView().getWrappedView()) != -1) {
			savedInstanceState.putBoolean("animatedSeeingDifficultyDurationViewInVisibleState", true);
		}
		else {
			savedInstanceState.putBoolean("animatedSeeingDifficultyDurationViewInVisibleState", false);
		}
		
		// take record of visibility of 'Seeing Difficulty Duration' view
		if (getAnimatedTopLevelView().indexOfChild(getSeeingDifficultyDurationDynamicView().getWrappedView()) != -1) {
			savedInstanceState.putBoolean("animatedSeeingDifficultyDurationViewInVisibleState", true);
		}
		else {
			savedInstanceState.putBoolean("animatedSeeingDifficultyDurationViewInVisibleState", false);
		}
		
		// take record of visibility of 'Cannot Treat Problems Details' view
		if (getAnimatedTopLevelView().indexOfChild(getCannotTreatProblemsDetailsDynamicView().getWrappedView()) != -1) {
			savedInstanceState.putBoolean("animatedCannotTreatProblemsDetailsViewInVisibleState", true);
		}
		else {
			savedInstanceState.putBoolean("animatedCannotTreatProblemsDetailsViewInVisibleState", false);
		}
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null) {
			setAnimatedVomitsEverythingViewInVisibleState(savedInstanceState.getBoolean("animatedVomitsEverythingViewInVisibleState"));
			setAnimatedRedEyesDurationViewInVisibleState(savedInstanceState.getBoolean("animatedRedEyesDurationViewInVisibleState"));
			setAnimatedSeeingDifficultyDurationViewInVisibleState(savedInstanceState.getBoolean("animatedSeeingDifficultyDurationViewInVisibleState"));
			setAnimatedCannotTreatProblemsDetailsViewInVisibleState(savedInstanceState.getBoolean("animatedCannotTreatProblemsDetailsViewInVisibleState"));
		}

		// restore visibility of 'Vomits Everything' view
		if (!isAnimatedVomitsEverythingViewInVisibleState()) {
			ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getVomitingDynamicView()));
		}

		// restore visibility of 'Red Eyes Duration' view 
		if (!isAnimatedRedEyesDurationViewInVisibleState()) {
			ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getRedEyesDurationDynamicView()));
		}

		// restore visibility of 'Seeing Difficulty Duration' view 
		if (!isAnimatedSeeingDifficultyDurationViewInVisibleState()) {
			ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getSeeingDifficultyDurationDynamicView()));
		}
		
		// restore visibility of 'Cannot Treat Problems Details' view
		if (!isAnimatedCannotTreatProblemsDetailsViewInVisibleState()) {
			ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getCannotTreatProblemsDetailsDynamicView()));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ccm_page_secondary_ask_assessment, container, false);
		((TextView) rootView.findViewById(android.R.id.title)).setText(getSecondaryAskCcmPage().getTitle());

		// get a hold on the top level animated view
		setAnimatedTopLevelView(((ViewGroup) rootView.findViewById(R.id.ccm_ask_secondary_assessment_animated_top_level_view)));

		// vomiting
		configureVomitingAnimateView(rootView);

		// red eyes
		configureRedEyesDurationAnimateView(rootView);

		// seeing difficulty
		configureSeeingDifficultyDurationAnimateView(rootView);
		
		// any other problems I cannot treat
		configureCannotTreatProblemsDetailsAnimateView(rootView);

		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);

		return rootView;
	}

	/**
	 * configureVomitingAnimateView(View rootView)
	 * 
	 * Method responsible for configuring a the dynamic animation of 
	 * 'vomits everything' radio group
	 * relating to the 'vomiting' radio button click event.
	 * 
	 * @param rootView
	 * 
	 */
	private void configureVomitingAnimateView(View rootView) {
		// 'vomiting' view
		setVomitingView((View) rootView.findViewById(R.id.ccm_ask_secondary_assessment_view_vomiting));

		// 'vomiting' radio group
		setVomitingRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_secondary_assessment_radio_vomiting));
		getVomitingRadioGroup().check(getSecondaryAskCcmPage()
				.getPageData().getInt(SecondaryAskCcmPage.VOMITING_DATA_KEY));

		// 'vomits everything' radio group
		setVomitsEverythingRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_secondary_assessment_radio_vomits_everything));
		getVomitsEverythingRadioGroup().check(getSecondaryAskCcmPage()
				.getPageData().getInt(SecondaryAskCcmPage.VOMITS_EVERYTHING_DATA_KEY));

		// 'vomits everything' radio group is a dynamic view within the UI
		setVomitingDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_ask_secondary_assessment_view_vomits_everything),
				rootView.findViewById(R.id.ccm_ask_secondary_assessment_radio_vomits_everything)));
	}

	/**
	 * configureRedEyesDurationAnimateView(View rootView)
	 * 
	 * Method responsible for configuring a the dynamic animation of 'red eyes duration'
	 * relating to the 'red eyes' radio button click event.
	 * 
	 * @param rootView
	 * 
	 */
	private void configureRedEyesDurationAnimateView(View rootView) {
		// red eyes view
		setRedEyesView((View) rootView.findViewById(R.id.ccm_ask_secondary_assessment_view_red_eyes));

		// red eyes radio group
		setRedEyesRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_secondary_assessment_radio_red_eyes));
		getRedEyesRadioGroup().check(getSecondaryAskCcmPage()
				.getPageData().getInt(SecondaryAskCcmPage.RED_EYES_DATA_KEY));

		// red eyes duration
		setRedEyesDurationEditText((EditText) rootView.findViewById(R.id.ccm_ask_secondary_assessment_red_eyes_duration));
		getRedEyesDurationEditText().setText(getSecondaryAskCcmPage().getPageData().getString(SecondaryAskCcmPage.RED_EYES_DURATION_DATA_KEY));

		// 'red eyes duration' is a dynamic view within the UI
		setRedEyesDurationDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_ask_secondary_assessment_view_red_eyes_duration),
				rootView.findViewById(R.id.ccm_ask_secondary_assessment_red_eyes_duration)));
	}

	/**
	 * configureSeeingDifficultyDurationAnimateView(View rootView)
	 * 
	 * Method responsible for configuring a the dynamic animation of 'difficulty seeing duration'
	 * relating to the 'difficulty in seeing' radio button click event.
	 * 
	 * @param rootView
	 * 
	 */
	private void configureSeeingDifficultyDurationAnimateView(View rootView) {
		// difficulty seeing view
		setSeeingDifficultyView((View) rootView.findViewById(R.id.ccm_ask_secondary_assessment_view_seeing_difficulty));

		// difficulty seeing radio group
		setSeeingDifficultyRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_secondary_assessment_radio_seeing_difficulty));
		getSeeingDifficultyRadioGroup().check(getSecondaryAskCcmPage()
				.getPageData().getInt(SecondaryAskCcmPage.SEEING_DIFFICULTY_DATA_KEY));

		// difficulty seeing duration
		setSeeingDifficultyDurationEditText((EditText) rootView.findViewById(R.id.ccm_ask_secondary_assessment_seeing_difficulty_duration));
		getSeeingDifficultyDurationEditText().setText(getSecondaryAskCcmPage().getPageData().getString(SecondaryAskCcmPage.SEEING_DIFFICULTY_DURATION_DATA_KEY));

		// 'difficulty seeing duration' is a dynamic view within the UI
		setSeeingDifficultyDurationDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_ask_secondary_assessment_view_seeing_difficulty_duration),
				rootView.findViewById(R.id.ccm_ask_secondary_assessment_seeing_difficulty_duration)));
	}

	/**
	 * configureCannotTreatProblemsDetailsAnimateView(View rootView)
	 * 
	 * Method responsible for configuring a the dynamic animation of 'any other problems I cannot treat'
	 * details textfield relating to the 'any other problems I cannot treat'
	 * radio button click event.
	 * 
	 * @param rootView
	 * 
	 */
	private void configureCannotTreatProblemsDetailsAnimateView(View rootView) {
		// 'any other problems I cannot treat' view
		setCannotTreatProblemsView((View) rootView.findViewById(R.id.ccm_ask_secondary_assessment_view_cannot_treat_problems));

		// 'any other problems I cannot treat' radio group
		setCannotTreatProblemsRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_secondary_assessment_radio_cannot_treat_problems));
		getCannotTreatProblemsRadioGroup().check(getSecondaryAskCcmPage()
				.getPageData().getInt(SecondaryAskCcmPage.CANNOT_TREAT_PROBLEMS_DATA_KEY));

		// 'any other problems I cannot treat' details
		setCannotTreatProblemsDetailsEditText((EditText) rootView.findViewById(R.id.ccm_ask_secondary_assessment_cannot_treat_problems_details));
		getCannotTreatProblemsDetailsEditText().setText(getSecondaryAskCcmPage().getPageData().getString(SecondaryAskCcmPage.CANNOT_TREAT_PROBLEMS_DETAILS_DATA_KEY));

		// 'any other problems I cannot treat' details is a dynamic view within the UI
		setCannotTreatProblemsDetailsDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_ask_secondary_assessment_view_cannot_treat_problems_details),
				rootView.findViewById(R.id.ccm_ask_secondary_assessment_cannot_treat_problems_details)));
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

		// add dynamic view listener to 'vomiting' radio group
		getVomitingRadioGroup().setOnCheckedChangeListener(
				new RadioGroupCoordinatorListener(getSecondaryAskCcmPage(),
						SecondaryAskCcmPage.VOMITING_DATA_KEY, 
						Arrays.asList(getVomitingDynamicView()),
						getAnimatedTopLevelView(),
						getVomitingView()));

		// add listener to 'vomits everything' radio group
		getVomitsEverythingRadioGroup().setOnCheckedChangeListener(
				new RadioGroupListener(getSecondaryAskCcmPage(),
						SecondaryAskCcmPage.VOMITS_EVERYTHING_DATA_KEY));

		// add dynamic view listener to 'red eyes' radio group
		DynamicViewListenerUtilities.addGenericDynamicViewListeners(getRedEyesView(), getRedEyesDurationDynamicView(),
				getAnimatedTopLevelView(),
				getRedEyesRadioGroup(), getRedEyesDurationEditText(),
				SecondaryAskCcmPage.RED_EYES_DATA_KEY, SecondaryAskCcmPage.RED_EYES_DURATION_DATA_KEY,
				getResources(), getSecondaryAskCcmPage());

		// add dynamic view listener to 'seeing difficulty' radio group
		DynamicViewListenerUtilities.addGenericDynamicViewListeners(getSeeingDifficultyView(), getSeeingDifficultyDurationDynamicView(),
				getAnimatedTopLevelView(),
				getSeeingDifficultyRadioGroup(), getSeeingDifficultyDurationEditText(),
				SecondaryAskCcmPage.SEEING_DIFFICULTY_DATA_KEY, SecondaryAskCcmPage.SEEING_DIFFICULTY_DURATION_DATA_KEY,
				getResources(), getSecondaryAskCcmPage());
		
		// add dynamic view listener to 'any other problems I cannot treat' radio group
		DynamicViewListenerUtilities.addGenericDynamicViewListeners(getCannotTreatProblemsView(), getCannotTreatProblemsDetailsDynamicView(),
				getAnimatedTopLevelView(),
				getCannotTreatProblemsRadioGroup(), getCannotTreatProblemsDetailsEditText(),
				SecondaryAskCcmPage.CANNOT_TREAT_PROBLEMS_DATA_KEY, SecondaryAskCcmPage.CANNOT_TREAT_PROBLEMS_DETAILS_DATA_KEY,
				getResources(), getSecondaryAskCcmPage());
	}

	/**
	 * Getter Method: getSecondaryAskCcmPage()
	 */
	public SecondaryAskCcmPage getSecondaryAskCcmPage() {
		return secondaryAskCcmPage;
	}

	/**
	 * Setter Method: setSecondaryAskCcmPage()
	 */   	
	public void setSecondaryAskCcmPage(SecondaryAskCcmPage secondaryAskCcmPage) {
		this.secondaryAskCcmPage = secondaryAskCcmPage;
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
	 * Getter Method: getRedEyesDurationEditText()
	 */
	public EditText getRedEyesDurationEditText() {
		return redEyesDurationEditText;
	}

	/**
	 * Setter Method: setRedEyesDurationEditText()
	 */
	public void setRedEyesDurationEditText(EditText redEyesDurationEditText) {
		this.redEyesDurationEditText = redEyesDurationEditText;
	}

	/**
	 * Getter Method: getSeeingDifficultyDurationEditText()
	 */
	public EditText getSeeingDifficultyDurationEditText() {
		return seeingDifficultyDurationEditText;
	}

	/**
	 * Setter Method: setSeeingDifficultyDurationEditText()
	 */
	public void setSeeingDifficultyDurationEditText(EditText seeingDifficultyDurationEditText) {
		this.seeingDifficultyDurationEditText = seeingDifficultyDurationEditText;
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
	 * Getter Method: getSeeingDifficultyView()
	 */
	public View getSeeingDifficultyView() {
		return seeingDifficultyView;
	}

	/**
	 * Setter Method: setSeeingDifficultyView()
	 */
	public void setSeeingDifficultyView(View seeingDifficultyView) {
		this.seeingDifficultyView = seeingDifficultyView;
	}

	/**
	 * Getter Method: getVomitingDynamicView()
	 */
	public DynamicView getVomitingDynamicView() {
		return vomitingDynamicView;
	}

	/**
	 * Setter Method: setVomitingDynamicView()
	 */
	public void setVomitingDynamicView(DynamicView vomitingDynamicView) {
		this.vomitingDynamicView = vomitingDynamicView;
	}

	/**
	 * Getter Method: getRedEyesDurationDynamicView()
	 */
	public DynamicView getRedEyesDurationDynamicView() {
		return redEyesDurationDynamicView;
	}

	/**
	 * Setter Method: setRedEyesDurationDynamicView()
	 */
	public void setRedEyesDurationDynamicView(DynamicView redEyesDurationDynamicView) {
		this.redEyesDurationDynamicView = redEyesDurationDynamicView;
	}

	/**
	 * Getter Method: getSeeingDifficultyDurationDynamicView()
	 */
	public DynamicView getSeeingDifficultyDurationDynamicView() {
		return seeingDifficultyDurationDynamicView;
	}

	/**
	 * Setter Method: setSeeingDifficultyDurationDynamicView()
	 */
	public void setSeeingDifficultyDurationDynamicView(DynamicView seeingDifficultyDurationDynamicView) {
		this.seeingDifficultyDurationDynamicView = seeingDifficultyDurationDynamicView;
	}

	/**
	 * Getter Method: getRedEyesRadioGroup()
	 */
	public RadioGroup getRedEyesRadioGroup() {
		return redEyesRadioGroup;
	}

	/**
	 * Setter Method: setRedEyesRadioGroup()
	 */
	public void setRedEyesRadioGroup(RadioGroup redEyesRadioGroup) {
		this.redEyesRadioGroup = redEyesRadioGroup;
	}

	/**
	 * Getter Method: getVomitingRadioGroup()
	 */
	public RadioGroup getVomitingRadioGroup() {
		return vomitingRadioGroup;
	}

	/**
	 * Setter Method: setVomitingRadioGroup()
	 */
	public void setVomitingRadioGroup(RadioGroup vomitingRadioGroup) {
		this.vomitingRadioGroup = vomitingRadioGroup;
	}

	/**
	 * Getter Method: getVomitsEverythingRadioGroup()
	 */
	public RadioGroup getVomitsEverythingRadioGroup() {
		return vomitsEverythingRadioGroup;
	}

	/**
	 * Setter Method: setVomitsEverythingRadioGroup()
	 */
	public void setVomitsEverythingRadioGroup(RadioGroup vomitsEverythingRadioGroup) {
		this.vomitsEverythingRadioGroup = vomitsEverythingRadioGroup;
	}

	/**
	 * Getter Method: getSeeingDifficultyRadioGroup()
	 */
	public RadioGroup getSeeingDifficultyRadioGroup() {
		return seeingDifficultyRadioGroup;
	}

	/**
	 * Setter Method: setSeeingDifficultyRadioGroup()
	 */
	public void setSeeingDifficultyRadioGroup(RadioGroup seeingDifficultyRadioGroup) {
		this.seeingDifficultyRadioGroup = seeingDifficultyRadioGroup;
	}

	/**
	 * Getter Method: getVomitingView()
	 */
	public View getVomitingView() {
		return vomitingView;
	}

	/**
	 * Setter Method: setVomitingView()
	 */
	public void setVomitingView(View vomitingView) {
		this.vomitingView = vomitingView;
	}

	/**
	 * Getter Method: getRedEyesView()
	 */
	public View getRedEyesView() {
		return redEyesView;
	}

	/**
	 * Setter Method: setRedEyesView()
	 */
	public void setRedEyesView(View redEyesView) {
		this.redEyesView = redEyesView;
	}

	/**
	 * Getter Method: isAnimatedVomitsEverythingViewInVisibleState()
	 */
	public Boolean isAnimatedVomitsEverythingViewInVisibleState() {
		return animatedVomitsEverythingViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedVomitsEverythingViewInVisibleState()
	 */
	public void setAnimatedVomitsEverythingViewInVisibleState(Boolean animatedVomitsEverythingViewInVisibleState) {
		this.animatedVomitsEverythingViewInVisibleState = animatedVomitsEverythingViewInVisibleState;
	}

	/**
	 * Getter Method: isAnimatedRedEyesDurationViewInVisibleState()
	 */
	public Boolean isAnimatedRedEyesDurationViewInVisibleState() {
		return animatedRedEyesDurationViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedRedEyesDurationViewInVisibleState()
	 */
	public void setAnimatedRedEyesDurationViewInVisibleState(Boolean animatedRedEyesDurationViewInVisibleState) {
		this.animatedRedEyesDurationViewInVisibleState = animatedRedEyesDurationViewInVisibleState;
	}

	/**
	 * Getter Method: isAnimatedSeeingDifficultyDurationViewInVisibleState()
	 */
	public Boolean isAnimatedSeeingDifficultyDurationViewInVisibleState() {
		return animatedSeeingDifficultyDurationViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedSeeingDifficultyDurationViewInVisibleState()
	 */
	public void setAnimatedSeeingDifficultyDurationViewInVisibleState(Boolean animatedSeeingDifficultyDurationViewInVisibleState) {
		this.animatedSeeingDifficultyDurationViewInVisibleState = animatedSeeingDifficultyDurationViewInVisibleState;
	}

	/**
	 * Getter Method: getCannotTreatProblemsRadioGroup()
	 */
	public RadioGroup getCannotTreatProblemsRadioGroup() {
		return cannotTreatProblemsRadioGroup;
	}

	/**
	 * Setter Method: setCannotTreatProblemsRadioGroup()
	 */
	public void setCannotTreatProblemsRadioGroup(RadioGroup cannotTreatProblemsRadioGroup) {
		this.cannotTreatProblemsRadioGroup = cannotTreatProblemsRadioGroup;
	}

	/**
	 * Getter Method: getCannotTreatProblemsDetailsEditText()
	 */
	public EditText getCannotTreatProblemsDetailsEditText() {
		return cannotTreatProblemsDetailsEditText;
	}

	/**
	 * Setter Method: setCannotTreatProblemsDetailsEditText()
	 */
	public void setCannotTreatProblemsDetailsEditText(EditText cannotTreatProblemsDetailsEditText) {
		this.cannotTreatProblemsDetailsEditText = cannotTreatProblemsDetailsEditText;
	}

	/**
	 * Getter Method: getCannotTreatProblemsView()
	 */
	public View getCannotTreatProblemsView() {
		return cannotTreatProblemsView;
	}

	/**
	 * Setter Method: setCannotTreatProblemsView()
	 */
	public void setCannotTreatProblemsView(View cannotTreatProblemsView) {
		this.cannotTreatProblemsView = cannotTreatProblemsView;
	}

	/**
	 * Getter Method: getCannotTreatProblemsDetailsDynamicView()
	 */
	public DynamicView getCannotTreatProblemsDetailsDynamicView() {
		return cannotTreatProblemsDetailsDynamicView;
	}

	/**
	 * Setter Method: setCannotTreatProblemsDetailsDynamicView()
	 */
	public void setCannotTreatProblemsDetailsDynamicView(DynamicView cannotTreatProblemsDetailsDynamicView) {
		this.cannotTreatProblemsDetailsDynamicView = cannotTreatProblemsDetailsDynamicView;
	}

	/**
	 * Getter Method: isAnimatedCannotTreatProblemsDetailsViewInVisibleState()
	 */
	public Boolean isAnimatedCannotTreatProblemsDetailsViewInVisibleState() {
		return animatedCannotTreatProblemsDetailsViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedCannotTreatProblemsDetailsViewInVisibleState()
	 */
	public void setAnimatedCannotTreatProblemsDetailsViewInVisibleState(
			Boolean animatedCannotTreatProblemsDetailsViewInVisibleState) {
		this.animatedCannotTreatProblemsDetailsViewInVisibleState = animatedCannotTreatProblemsDetailsViewInVisibleState;
	}
}