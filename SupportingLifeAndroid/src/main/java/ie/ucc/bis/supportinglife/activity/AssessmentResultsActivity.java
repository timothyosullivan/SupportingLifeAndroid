package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.dao.PatientAssessmentDao;
import ie.ucc.bis.supportinglife.dao.PatientAssessmentDaoImpl;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.helper.PatientHandlerUtils;
import ie.ucc.bis.supportinglife.rule.engine.ClassificationRuleEngine;
import ie.ucc.bis.supportinglife.rule.engine.TreatmentRuleEngine;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Class: AssessmentResultsActivity
 * 
 * Base class responsible for displaying assessment results
 * (i.e. IMCI and CCM)
 * 
 * @author TOSullivan
 *
 */
public class AssessmentResultsActivity extends SupportingLifeBaseActivity {
	
	private final String LOG_TAG = "ie.ucc.bis.supportinglife.activity.CcmAssessmentResultsActivity";
	
	private ViewPager ViewPager;
	private TabsAdapter TabsAdapter;
	private ArrayList<ReviewItem> reviewItems;
	private PatientAssessment patientAssessment;
	private ClassificationRuleEngine classificationRuleEngine;
	private TreatmentRuleEngine treatmentRuleEngine;
	
	private PatientAssessmentDao patientAssessmentDao;

	/* 
	 * Method: onCreate() 
	 * 
	 * Perform initialisation of all fragments and loaders.
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPatientAssessmentDao(new PatientAssessmentDaoImpl(this));
        getPatientAssessmentDao().open();
	}
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex()); 
	}
		
	/**
	 * Static Class: TabsAdapter
	 * 
	 * Responsible for creating tabs and configuring behaviour
	 * 
	 * @author TOSullivan
	 */
	public static class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
		
		protected static final int CLASSIFICATION_TAB_INDEX = 1;
		protected static final int TREATMENT_TAB_INDEX = 2;
				
		private final Context context;
		private final ActionBar actionBar;
		private final ViewPager viewPager;
		private final ArrayList<TabInfo> tabs = new ArrayList<TabInfo>();
	 
		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;
			
			TabInfo(Class<?> _class, Bundle _args) {
				clss = _class;
				args = _args;
			} // end of constructor
		} // end of TabInfo static class
		
		/**
		 * Constructor
		 * 
		 * @param activity
		 * @param pager
		 * 
		 */
		public TabsAdapter(FragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			this.context= activity;
			this.actionBar = ((FragmentActivity) getContext()).getActionBar();
			this.viewPager = pager;
			getViewPager().setAdapter(this);
			getViewPager().setOnPageChangeListener(this);
		 }
		
		
		/**
		 * Set the classifications tab to be the default tab
		 */
		public void setDefaultTab() {
			getActionBar().selectTab(getActionBar().getTabAt(CLASSIFICATION_TAB_INDEX));	
		}
		
		/**
		 * Display the treatments tab
		 * 
		 */
		public void displayTreatmentTab() {
			getActionBar().selectTab(getActionBar().getTabAt(TREATMENT_TAB_INDEX));
		}

		/**
		 * Responsible for adding the tabs and their associated
		 * fragments
		 * 
		 * @param tab
		 * @param fragmentClass
		 * @param args
		 */
		public void addTab(ActionBar.Tab tab, Class<?> fragmentClass, Bundle args) {
			TabInfo info = new TabInfo(fragmentClass, args);
			tab.setTag(info);
			tab.setTabListener(this);
			getTabs().add(info);
			getActionBar().addTab(tab);
			notifyDataSetChanged();
		}
		 
		@Override
		public void onPageScrollStateChanged(int state) {
		   // TODO Auto-generated method stub
		}
		 
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
		}
		 
		/* 
		 * Invoked when a new page becomes selected.
		 * 
		 * (non-Javadoc)
		 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
		 */
		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			getActionBar().setSelectedNavigationItem(position);
		}
		 
		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
		 
		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			Object tag = tab.getTag();
			for (int count=0; count<getTabs().size(); count++) {
				if (getTabs().get(count) == tag) {
					getViewPager().setCurrentItem(count);
				}
			}
		}
		 
		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}	
		
		/* 
		 * Return the Fragment associated with a specified position
		 * 
		 * (non-Javadoc)
		 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int position) {
			TabInfo info = getTabs().get(position);
			return Fragment.instantiate(getContext(), info.clss.getName(), info.args);
		}
		 
		/*
		 * Return the number of views available 
		 * 
		 * (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			return getTabs().size();
		}
	
		/**
		 * Getter Method: getContext()
		 */
		public Context getContext() {
			return context;
		}
		  
		/**
		 * Getter Method: getActionBar()
		 */
		public ActionBar getActionBar() {
			return actionBar;
		}
	
		/**
		 * Getter Method: getViewPager()
		 */
		public ViewPager getViewPager() {
			return viewPager;
		}
	
		/**
		 * Getter Method: getTabs()
		 */
		public ArrayList<TabInfo> getTabs() {
			return tabs;
		}
	} // end of static TabsAdapter class

	/**
	 * Responsible for capturing the patient assessment data
	 * entered by the user and constructing the patient instance
	 */	
	protected void contructPatientInstance() {
		Resources resources = getApplicationContext().getResources();
	
    	// constuct the patient instance
        try {
        	setPatientAssessment((new PatientHandlerUtils()).populateCcmPatientDetails(resources, getReviewItems()));	
		} catch (ParseException e) {
			LoggerUtils.i(LOG_TAG, "Parse Exception thrown whilst constructing patient instance");
			e.printStackTrace();
		}
	}
	
	/**
	 * Responsible for recording the patient visit in the SQLite DB
	 * 
	 */	
	protected void recordPatientVisit() {

		// obtain unique android id for the device
		String android_device_id = Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID); 
		
		// add the patient record to the DB
		getPatientAssessmentDao().createPatientAssessment(getPatientAssessment(), android_device_id);

		// check patient has been added correctly
		List<PatientAssessment> patientsRetrieved = getPatientAssessmentDao().getAllNonSyncedPatientAssessments();

		System.out.println("test");
	}
	
	
	/**
	 * Click Handler: Handle the click on the home button
	 * 
	 * @param view View
	 * @return void
	 */
    @Override
    public void onClickHome(View view) {
    	// if user is performing an IMCI or CCM assessment then 
    	// display a confirmation dialog to confirm that the user wishes 
    	// to exit the patient assessment
    	exitAssessmentDialogHandler();
    }
    
	/**
	 * Click Handler: Handle the back button click event such that if user 
	 * is performing an IMCI or CCM assessment then a confirmation dialog 
	 * will be displayed to confirm that the user wishes to exit the 
	 * patient assessment
	 * 
	 * @param view View
	 * @return void
	 */
    @Override
    public void onBackPressed() {
    	// Handle the back button click event such that if user 
    	// is performing an IMCI or CCM assessment then a confirmation dialog 
    	// will be displayed to confirm that the user wishes to exit the 
    	// patient assessment
    	exitAssessmentDialogHandler();
    }

    
    @Override
    protected void onResume() {
    	getPatientAssessmentDao().open();
    	super.onResume();
    }

    @Override
    protected void onPause() {
    	getPatientAssessmentDao().close();
    	super.onPause();
    }
    
    
	public ViewPager getViewPager() {
		return ViewPager;
	}

	public void setViewPager(ViewPager viewPager) {
		ViewPager = viewPager;
	}

	public TabsAdapter getTabsAdapter() {
		return TabsAdapter;
	}

	public void setTabsAdapter(TabsAdapter tabsAdapter) {
		TabsAdapter = tabsAdapter;
	}

	public ArrayList<ReviewItem> getReviewItems() {
		return reviewItems;
	}

	protected void setReviewItems(ArrayList<ReviewItem> reviewItems) {
		this.reviewItems = reviewItems;
	}

	public PatientAssessment getPatientAssessment() {
		return patientAssessment;
	}

	protected void setPatientAssessment(PatientAssessment patientAssessment) {
		this.patientAssessment = patientAssessment;
	}

	public ClassificationRuleEngine getClassificationRuleEngine() {
		return classificationRuleEngine;
	}

	public void setClassificationRuleEngine(ClassificationRuleEngine classificationRuleEngine) {
		this.classificationRuleEngine = classificationRuleEngine;
	}

	public TreatmentRuleEngine getTreatmentRuleEngine() {
		return treatmentRuleEngine;
	}

	public void setTreatmentRuleEngine(TreatmentRuleEngine treatmentRuleEngine) {
		this.treatmentRuleEngine = treatmentRuleEngine;
	}

	protected PatientAssessmentDao getPatientAssessmentDao() {
		return patientAssessmentDao;
	}

	protected void setPatientAssessmentDao(PatientAssessmentDao patientAssessmentDao) {
		this.patientAssessmentDao = patientAssessmentDao;
	}

}

