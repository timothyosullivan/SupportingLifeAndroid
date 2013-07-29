package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.ui.TabControllerFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

/**
 * Class: AssessmentResultsActivity
 * 
 * Responsible for displaying e-IMCI assessment results.
 * 
 * The results shown comprise of the following:
 * 
 * 1. Assessment Details Tab
 * 2. Classifications Tab
 * 3. Recommended Treatments Tab
 * 
 * @author TOSullivan
 *
 */
public class AssessmentResultsActivity extends SupportingLifeBaseActivity {

	/**
	 * OnCreate method is called when the activity is first created.
	 * 
	 * This is where all of the normal static set up should occur
	 * e.g. create views, bind data to lists, etc.
	 * 
	 * The method also provides a Bundle parameter containing the activity's
	 * previously frozen state (if there was one).
	 * 
	 * This method is always followed by onStart().
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assessment_results);
		
		setTitleFromActivityLabel(R.id.title_text);

		// Grab the instance of TabFragmentController that was included with the layout and have it
		// launch the initial tab.
		FragmentManager fragmentManager = getSupportFragmentManager();
		TabControllerFragment tabFragment = (TabControllerFragment) fragmentManager.findFragmentById(R.id.assessment_results_fragment_tabs);
		
		tabFragment.displayAssessmentClassificationsView();
	}
}

