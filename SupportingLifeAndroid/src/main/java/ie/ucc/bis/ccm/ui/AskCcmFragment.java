package ie.ucc.bis.ccm.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.assessment.model.listener.RadioGroupCoordinatorListener;
import ie.ucc.bis.ccm.model.AskCcmPage;
import ie.ucc.bis.imci.model.DynamicView;
import ie.ucc.bis.imci.ui.PageFragmentCallbacks;
import ie.ucc.bis.ui.utilities.ViewGroupUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private EditText coughDurationEditText;
	private EditText diarrhoeaDurationEditText;
	private ViewGroup animatedTopLevelView;
	private View coughView;
	private View diarrhoeaView;
	private DynamicView coughDurationDynamicView;
	private DynamicView diarrhoeaDurationDynamicView;
	private Boolean animatedCoughDurationViewInVisibleState;
	private Boolean animatedDiarrhoeaDurationViewInVisibleState;

	public static AskCcmFragment create(String pageKey) {
		Bundle args = new Bundle();
		args.putString(ARG_PAGE_KEY, pageKey);

		AskCcmFragment fragment = new AskCcmFragment();
		fragment.setArguments(args);
		fragment.setAnimatedCoughDurationViewInVisibleState(false);
		fragment.setAnimatedDiarrhoeaDurationViewInVisibleState(false);
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
		 super.onSaveInstanceState(savedInstanceState);
	 }

	 @Override
	 public void onViewStateRestored(Bundle savedInstanceState) {
		 super.onViewStateRestored(savedInstanceState);
		 if (savedInstanceState != null) {
			 setAnimatedCoughDurationViewInVisibleState(savedInstanceState.getBoolean("animatedCoughDurationViewInVisibleState"));
			 setAnimatedDiarrhoeaDurationViewInVisibleState(savedInstanceState.getBoolean("animatedDiarrhoeaDurationViewInVisibleState"));
		 }

		 // restore visibility of 'Cough Duration' view
		 if (!isAnimatedCoughDurationViewInVisibleState()) {
			 ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getCoughDurationDynamicView()));
		 }
		 
		 // restore visibility of 'Diarrhoea Duration' view 
		 if (!isAnimatedDiarrhoeaDurationViewInVisibleState()) {
			 ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getDiarrhoeaDurationDynamicView()));
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
		 addCoughDynamicViewListener();  

		 // add dynamic view listener to diarrhoea radio group
		 addDiarrhoeaDynamicViewListener();  
		 
	 }

	 /**
	  * addCoughDynamicViewListener()
	  * 
	  * Responsible for adding a listener to the Cough Radio Group
	  * 
	  */
	 private void addCoughDynamicViewListener() {
		 int indexPosition = getAnimatedTopLevelView().indexOfChild(getCoughView()) + 1;

		 List<String> animateUpRadioButtonTextTriggers = new ArrayList<String>();
		 animateUpRadioButtonTextTriggers.add(getResources().getString(R.string.assessment_wizard_radio_no));

		 // add listener to 'cough'
		 getCoughRadioGroup().setOnCheckedChangeListener(
				 new RadioGroupCoordinatorListener(getAskCcmPage(),
						 AskCcmPage.COUGH_DATA_KEY, 
						 Arrays.asList(getCoughDurationDynamicView()),
						 getAnimatedTopLevelView(),
						 indexPosition,
						 animateUpRadioButtonTextTriggers));

		 // add listener to 'cough duration'
		 getCoughDurationEditText().addTextChangedListener(
				 new AssessmentWizardTextWatcher(getAskCcmPage(), 
						 AskCcmPage.COUGH_DURATION_DATA_KEY));
	 }

	 /**
	  * addDiarrhoeaDynamicViewListener()
	  * 
	  * Responsible for adding a listener to the Diarrhoea Radio Group
	  * 
	  */
	 private void addDiarrhoeaDynamicViewListener() {
		 int indexPosition = getAnimatedTopLevelView().indexOfChild(getDiarrhoeaView()) + 1;

		 List<String> animateUpRadioButtonTextTriggers = new ArrayList<String>();
		 animateUpRadioButtonTextTriggers.add(getResources().getString(R.string.assessment_wizard_radio_no));
	 
		 // add listener to 'diarrhoea'
		 getDiarrhoeaRadioGroup().setOnCheckedChangeListener(
				 new RadioGroupCoordinatorListener(getAskCcmPage(),
						 AskCcmPage.DIARRHOEA_DATA_KEY, 
						 Arrays.asList(getDiarrhoeaDurationDynamicView()),
						 getAnimatedTopLevelView(),
						 indexPosition,
						 animateUpRadioButtonTextTriggers));

		 // add listener to 'diarrhoea duration'
		 getDiarrhoeaDurationEditText().addTextChangedListener(
				 new AssessmentWizardTextWatcher(getAskCcmPage(), 
						 AskCcmPage.DIARRHOEA_DURATION_DATA_KEY));
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
}