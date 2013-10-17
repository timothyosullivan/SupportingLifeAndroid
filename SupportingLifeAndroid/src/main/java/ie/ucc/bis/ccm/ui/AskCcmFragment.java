package ie.ucc.bis.ccm.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.assessment.model.listener.AssessmentWizardTextWatcher;
import ie.ucc.bis.assessment.model.listener.RadioGroupListener;
import ie.ucc.bis.ccm.model.AskCcmPage;
import ie.ucc.bis.imci.model.DynamicView;
import ie.ucc.bis.imci.ui.PageFragmentCallbacks;
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
    private ViewGroup animatedRelationshipSpecifiedView;
    private View relationshipView;
    private DynamicView relationshipSpecifiedDynamicView;
    private Boolean animatedRelationshipViewInVisibleState;
    
    public static AskCcmFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        AskCcmFragment fragment = new AskCcmFragment();
        fragment.setArguments(args);
        fragment.setAnimatedRelationshipViewInVisibleState(false);
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
/*      if (getAnimatedRelationshipSpecifiedView().indexOfChild(getRelationshipSpecifiedDynamicView().getWrappedView()) != -1) {
    	  // Animated Fever view is visible
    	  savedInstanceState.putBoolean("animatedRelationshipViewInVisibleState", true);
      }
      else {
    	  // Animated Fever view is invisible
    	  savedInstanceState.putBoolean("animatedRelationshipViewInVisibleState", false);
      } */
      super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
    	super.onViewStateRestored(savedInstanceState);
/*    	if (savedInstanceState != null) {
    		setAnimatedRelationshipViewInVisibleState(savedInstanceState.getBoolean("animatedRelationshipViewInVisibleState"));
    	}

    	if (!isAnimatedRelationshipViewInVisibleState()) {
    		ViewGroupUtilities.removeDynamicViews(getAnimatedRelationshipSpecifiedView(), Arrays.asList(getRelationshipSpecifiedDynamicView()));
    	}
*/
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
        setCoughRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_ask_assessment_radio_cough));
        getCoughRadioGroup().check(getAskCcmPage()
        		.getPageData().getInt(AskCcmPage.COUGH_DATA_KEY));
        
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);

        return rootView;
    }
    
    
/*	private void configureRelationshipAnimatedView(View rootView) {
		// Relationship view
		setRelationshipView((View) rootView.findViewById(R.id.ccm_general_patient_details_view_relationship));

        // relationship
        setRelationshipRadioGroup((RadioGroup) rootView.findViewById(R.id.ccm_general_patient_details_radio_relationship));
        getRelationshipRadioGroup().check(getGeneralPatientDetailsCcmPage()
        		.getPageData().getInt(GeneralPatientDetailsCcmPage.RELATIONSHIP_DATA_KEY));
        
        // specify relationship
        setRelationshipSpecifiedEditText((EditText) rootView.findViewById(R.id.ccm_general_patient_details_relationship_specified));
        getRelationshipSpecifiedEditText().setText(getGeneralPatientDetailsCcmPage().getPageData().getString(GeneralPatientDetailsCcmPage.RELATIONSHIP_SPECIFIED_DATA_KEY));
        
        // 'specify relationship' is a dynamic view within the UI
        setRelationshipSpecifiedDynamicView(new DynamicView(rootView.findViewById(R.id.ccm_general_patient_details_view_relationship_specified),
        									rootView.findViewById(R.id.ccm_general_patient_details_relationship_specified)));
        
        // get a hold on the top level animated view
        setAnimatedRelationshipSpecifiedView(((ViewGroup) rootView.findViewById(R.id.ccm_general_patient_details_relationship_animated_view)));
	} */

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

        // cough
        getCoughRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupListener(getAskCcmPage(),
        				AskCcmPage.COUGH_DATA_KEY));

        
        // add dynamic view listener to relationship radio group
  //      addRelationshipDynamicViewListener();  

    }

	/**
	 * addRelationshipDynamicViewListener()
	 * 
	 * Responsible for adding a listener to the Relationship view
	 * 
	 */
/*	private void addRelationshipDynamicViewListener() {
        int indexPosition = getAnimatedRelationshipSpecifiedView().indexOfChild(getRelationshipView()) + 1;
        
		List<String> animateUpRadioButtonTextTriggers = new ArrayList<String>();
		animateUpRadioButtonTextTriggers.add(getResources().getString(R.string.ccm_general_patient_details_radio_relationship_mother));
		animateUpRadioButtonTextTriggers.add(getResources().getString(R.string.ccm_general_patient_details_radio_relationship_father));
        
        getRelationshipRadioGroup().setOnCheckedChangeListener(
        		new RadioGroupCoordinatorListener(getGeneralPatientDetailsCcmPage(),
        				GeneralPatientDetailsCcmPage.RELATIONSHIP_DATA_KEY, 
        				Arrays.asList(getRelationshipSpecifiedDynamicView()),
        				getAnimatedRelationshipSpecifiedView(),
        				indexPosition,
        				animateUpRadioButtonTextTriggers));
        
        // add listener to 'specify relationship'
        getRelationshipSpecifiedEditText().addTextChangedListener(
        		new AssessmentWizardTextWatcher(getGeneralPatientDetailsCcmPage(), 
        				GeneralPatientDetailsCcmPage.RELATIONSHIP_SPECIFIED_DATA_KEY));
	}   */
    
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
	 * Getter Method: getAnimatedRelationshipSpecifiedView()
	 */
	public ViewGroup getAnimatedRelationshipSpecifiedView() {
		return animatedRelationshipSpecifiedView;
	}

	/**
	 * Setter Method: setAnimatedRelationshipSpecifiedView()
	 */
	public void setAnimatedRelationshipSpecifiedView(ViewGroup animatedRelationshipSpecifiedView) {
		this.animatedRelationshipSpecifiedView = animatedRelationshipSpecifiedView;
	}

	/**
	 * Getter Method: getRelationshipView()
	 */
	public View getRelationshipView() {
		return relationshipView;
	}

	/**
	 * Setter Method: setRelationshipView()
	 */
	public void setRelationshipView(View relationshipView) {
		this.relationshipView = relationshipView;
	}

	/**
	 * Getter Method: getRelationshipSpecifiedDynamicView()
	 */
	public DynamicView getRelationshipSpecifiedDynamicView() {
		return relationshipSpecifiedDynamicView;
	}

	/**
	 * Setter Method: setRelationshipSpecifiedDynamicView()
	 */
	public void setRelationshipSpecifiedDynamicView(DynamicView relationshipOtherDynamicView) {
		this.relationshipSpecifiedDynamicView = relationshipOtherDynamicView;
	}

	/**
	 * Getter Method: isAnimatedRelationshipViewInVisibleState()
	 */
	public Boolean isAnimatedRelationshipViewInVisibleState() {
		return animatedRelationshipViewInVisibleState;
	}

	/**
	 * Setter Method: setAnimatedRelationshipViewInVisibleState()
	 */
	public void setAnimatedRelationshipViewInVisibleState(Boolean animatedRelationshipViewInVisibleState) {
		this.animatedRelationshipViewInVisibleState = animatedRelationshipViewInVisibleState;
	}
}
