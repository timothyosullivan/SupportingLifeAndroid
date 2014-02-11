package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.domain.Patient;
import ie.ucc.bis.supportinglife.rule.engine.ClassificationRuleEngine;
import ie.ucc.bis.supportinglife.rule.engine.TreatmentRuleEngine;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.os.Bundle;
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
	
	private ViewPager ViewPager;
	private TabsAdapter TabsAdapter;
	private ArrayList<ReviewItem> reviewItems;
	private Patient patient;
	private ClassificationRuleEngine classificationRuleEngine;
	private TreatmentRuleEngine treatmentRuleEngine;

	/* 
	 * Method: onCreate() 
	 * 
	 * Perform initialisation of all fragments and loaders.
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
	protected void setReviewItems(ArrayList<ReviewItem> reviewItems) {
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
	protected void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Getter Method: getClassificationRuleEngine()
	 */	
	public ClassificationRuleEngine getClassificationRuleEngine() {
		return classificationRuleEngine;
	}

	/**
	 * Setter Method: setClassificationRuleEngine()
	 */
	public void setClassificationRuleEngine(ClassificationRuleEngine classificationRuleEngine) {
		this.classificationRuleEngine = classificationRuleEngine;
	}

	/**
	 * Getter Method: getTreatmentRuleEngine()
	 */	
	public TreatmentRuleEngine getTreatmentRuleEngine() {
		return treatmentRuleEngine;
	}

	/**
	 * Setter Method: setTreatmentRuleEngine()
	 */
	public void setTreatmentRuleEngine(TreatmentRuleEngine treatmentRuleEngine) {
		this.treatmentRuleEngine = treatmentRuleEngine;
	}
}

