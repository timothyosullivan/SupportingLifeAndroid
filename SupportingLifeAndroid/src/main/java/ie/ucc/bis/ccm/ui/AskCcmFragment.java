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
    private EditText coughDurationEditText;
    private ViewGroup animatedTopLevelView;
    private View coughView;
    private DynamicView coughDurationDynamicView;
    private Boolean animatedCoughDurationViewInVisibleState;
    
    public static AskCcmFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        AskCcmFragment fragment = new AskCcmFragment();
        fragment.setArguments(args);
        fragment.setAnimatedCoughDurationViewInVisibleState(false);
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
      if (getAnimatedTopLevelView().indexOfChild(getCoughDurationDynamicView().getWrappedView()) != -1) {
    	  // Animated Cough Duration view is visible
    	  savedInstanceState.putBoolean("animatedCoughDurationViewInVisibleState", true);
      }
      else {
    	  // Animated Cough Duration view is invisible
    	  savedInstanceState.putBoolean("animatedCoughDurationViewInVisibleState", false);
      } 
      super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
    	super.onViewStateRestored(savedInstanceState);
    	if (savedInstanceState != null) {
    		setAnimatedCoughDurationViewInVisibleState(savedInstanceState.getBoolean("animatedCoughDurationViewInVisibleState"));
    	}

    	if (!isAnimatedCoughDurationViewInVisibleState()) {
    		ViewGroupUtilities.removeDynamicViews(getAnimatedTopLevelView(), Arrays.asList(getCoughDurationDynamicView()));
    	}

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ccm_page_ask_assessment, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getAskCcmPage().getTitle());

        // child's problems
        setProblemsEditText(((EditText) rootView.findViewById(R.id.ccm_ask_assessment_problems)));
        getProblemsEditText().setText(getAskCcmPage().getPageData().getString(AskCcmPage.PROBLEMS_DATA_KEY));
        
        // cough
        configureCoughDurationAnimatedView(rootView);
                
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);

        return rootView;
    }
      
	private void configureCoughDurationAnimatedView(View rootView) {
		// Cough view
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
        
        // get a hold on the top level animated view
        setAnimatedTopLevelView(((ViewGroup) rootView.findViewById(R.id.ccm_ask_assessment_animated_top_level_view)));
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
}