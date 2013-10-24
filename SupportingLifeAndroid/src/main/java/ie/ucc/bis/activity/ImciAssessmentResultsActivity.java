package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.imci.ui.ImciAssessmentClassificationsFragment;
import ie.ucc.bis.assessment.imci.ui.ImciAssessmentTreatmentsFragment;
import ie.ucc.bis.assessment.model.review.ReviewItem;
import ie.ucc.bis.assessment.ui.AssessmentResultsReviewFragment;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.rule.engine.ClassificationRuleEngine;
import ie.ucc.bis.rule.engine.TreatmentRuleEngine;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.BaseAdapter;

/**
 * Class: ImciAssessmentResultsActivity
 * 
 * Responsible for displaying IMCI assessment results.
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
public class ImciAssessmentResultsActivity extends AssessmentResultsActivity {
	
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
        setReviewItems((ArrayList<ReviewItem>) intent.getSerializableExtra(ImciAssessmentActivity.ASSESSMENT_REVIEW_ITEMS));
        
        // resolve imci classifications based on assessed symptoms
        setPatient(new Patient());
        ClassificationRuleEngine classificationRuleEngine = new ClassificationRuleEngine();
        classificationRuleEngine.determineClassifications(this, getReviewItems(), getPatient(), 
        		classificationRuleEngine.getSystemImciClassifications());
        
        // identify imci treatments
        TreatmentRuleEngine treatmentRuleEngine = new TreatmentRuleEngine();
        treatmentRuleEngine.determineTreatments(this, getReviewItems(), classificationRuleEngine.getSystemImciClassifications(), getPatient());
 
        // create a new Action bar and set title to strings.xml
        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // attach the Tabs to the fragment classes and set the tab title.
        setTabsAdapter(new TabsAdapter(this, getViewPager()));
        
        // add assessment review items tab
        getTabsAdapter().addTab(bar.newTab().setText(R.string.imci_assessment_results_review_tab_title),
        		AssessmentResultsReviewFragment.class, null);
       
        // add classifications tab
        getTabsAdapter().addTab(bar.newTab().setText(R.string.imci_assessment_results_classifications_tab_title),
        		ImciAssessmentClassificationsFragment.class, null);
        
        // add treatments tab
        getTabsAdapter().addTab(bar.newTab().setText(R.string.imci_assessment_results_treatments_tab_title),
        		ImciAssessmentTreatmentsFragment.class, null);
 
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
		ImciAssessmentTreatmentsFragment treatmentsFragment = (ImciAssessmentTreatmentsFragment) 
				getSupportFragmentManager().getFragments().get(TabsAdapter.TREATMENT_TAB_INDEX);

		if (treatmentsFragment != null) {
			// refresh adpater data set - gets view redrawn
			((BaseAdapter) treatmentsFragment.getTreatmentAdapter()).notifyDataSetChanged();
			treatmentsFragment.scrollToRelatedElement(position, classificationTitle);
		}
	}
}

