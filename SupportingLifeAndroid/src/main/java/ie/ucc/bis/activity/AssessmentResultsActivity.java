package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.rule.engine.ClassificationRuleEngine;
import ie.ucc.bis.rule.engine.TreatmentRuleEngine;
import ie.ucc.bis.wizard.model.review.ReviewItem;
import ie.ucc.bis.wizard.ui.AssessmentClassificationsFragment;
import ie.ucc.bis.wizard.ui.AssessmentResultsReviewFragment;
import ie.ucc.bis.wizard.ui.AssessmentTreatmentsFragment;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.BaseAdapter;

/**
 * Class: AssessmentResultsActivity
 * 
 * Responsible for displaying e-IMCI assessment results.
 * 
 * The results shown comprise of the following:
 * 
 * 1. Assessment Review Items Tab
 * 2. Assessment Classifications Tab
 * 3. Assessment Recommended Treatments Tab
 * 
 * @author TOSullivan
 *
 */
public class AssessmentResultsActivity extends SupportingLifeBaseActivity {
	
	private ViewPager ViewPager;
	private TabsAdapter TabsAdapter;
	private ArrayList<ReviewItem> reviewItems;
	private Patient patient;

	/* 
	 * Method: onCreate() 
	 * 
	 * Perform initialisation of all fragments and loaders.
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assessment_results);
        setTitleFromActivityLabel(R.id.action_bar_title_text);
        setViewPager((ViewPager) findViewById(R.id.assessment_results_pager));
        
        // extract the assessment page data sent by the assessment bread-crumb wizard
		Intent intent = getIntent();
        setReviewItems((ArrayList<ReviewItem>) intent.getSerializableExtra(AssessmentWizardActivity.ASSESSMENT_REVIEW_ITEMS));
        
        // classify symptoms
        setPatient(new Patient());
        ClassificationRuleEngine classificationRuleEngine = new ClassificationRuleEngine();
        classificationRuleEngine.determineClassifications(this, getReviewItems(), getPatient());
        
        // identify treatments
        TreatmentRuleEngine treatmentRuleEngine = new TreatmentRuleEngine();
        treatmentRuleEngine.determineTreatments(this, reviewItems, classificationRuleEngine.getSystemClassifications(), getPatient());
 
        // create a new Action bar and set title to strings.xml
        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // attach the Tabs to the fragment classes and set the tab title.
        setTabsAdapter(new TabsAdapter(this, getViewPager()));
        
        // add assessment review items tab
        getTabsAdapter().addTab(bar.newTab().setText(R.string.assessment_results_review_tab_title),
        		AssessmentResultsReviewFragment.class, null);
       
        // add classifications tab
        getTabsAdapter().addTab(bar.newTab().setText(R.string.assessment_results_classifications_tab_title),
        		AssessmentClassificationsFragment.class, null);
        
        // add treatments tab
        getTabsAdapter().addTab(bar.newTab().setText(R.string.assessment_results_treatments_tab_title),
        		AssessmentTreatmentsFragment.class, null);
 
        // open on classifications tab by default
        getTabsAdapter().setDefaultTab();

       if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
	}
	
	/**
	 * Display the treatments tab and scroll to the
	 * relevant classification
	 * @param classificationTitle 
	 * 
	 * @param classificationName 
	 */
	public void displayTreatmentTab(int position, String classificationTitle) {
		getTabsAdapter().displayTreatmentTab();
		AssessmentTreatmentsFragment treatmentsFragment = (AssessmentTreatmentsFragment) 
				getSupportFragmentManager().getFragments().get(TabsAdapter.TREATMENT_TAB_INDEX);

		if (treatmentsFragment != null) {
			// refresh adpater data set - gets view redrawn
			((BaseAdapter) treatmentsFragment.getTreatmentAdapter()).notifyDataSetChanged();
			treatmentsFragment.scrollToRelatedElement(position, classificationTitle);
		}
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
		
		private final int CLASSIFICATION_TAB_INDEX = 1;
		private final int TREATMENT_TAB_INDEX = 2;
				
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
	 * Getter Method: getViewPager()
	 */	
	public ViewPager getViewPager() {
		return ViewPager;
	}

	/**
	 * Setter Method: setViewPager()
	 */  
	public void setViewPager(ViewPager viewPager) {
		ViewPager = viewPager;
	}

	/**
	 * Getter Method: getTabsAdapter()
	 */	
	public TabsAdapter getTabsAdapter() {
		return TabsAdapter;
	}

	/**
	 * Setter Method: setTabsAdapter()
	 */
	public void setTabsAdapter(TabsAdapter tabsAdapter) {
		TabsAdapter = tabsAdapter;
	}
	
	/**
	 * Getter Method: getReviewItems()
	 */	
	public ArrayList<ReviewItem> getReviewItems() {
		return reviewItems;
	}

	/**
	 * Setter Method: setReviewItems()
	 */
	private void setReviewItems(ArrayList<ReviewItem> reviewItems) {
		this.reviewItems = reviewItems;
	}

	/**
	 * Getter Method: getPatient()
	 */	
	public Patient getPatient() {
		return patient;
	}

	/**
	 * Setter Method: setPatient()
	 */
	private void setPatient(Patient patient) {
		this.patient = patient;
	}
}

