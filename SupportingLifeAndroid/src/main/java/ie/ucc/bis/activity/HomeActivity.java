package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This is a simple activity that demonstrates the Supporting LIFE dashboard user interface.
 *
 */

public class HomeActivity extends SupportingLifeBaseActivity {

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
		setContentView(R.layout.activity_home);
	}
	
	/**
	 * Click Handler: Handler the click of a dashboard button
	 * 
	 * @param view View
	 * @return void
	 */
	public void onClickDashboardButton(View view) {
		int id = view.getId();
		switch(id) {
			case R.id.dashboard_assessment_button :
				startActivity(new Intent(getApplicationContext(), RecordPatientDetailsActivity.class));
				break;
			case R.id.dashboard_about_button :
				startActivity(new Intent(getApplicationContext(), AboutActivity.class));
				break;
			case R.id.dashboard_assessment_wizard_button :
				startActivity(new Intent(getApplicationContext(), AssessmentWizardActivity.class));
				break;
			case R.id.dashboard_training_button :
				startActivity(new Intent(getApplicationContext(), TrainingActivity.class));
			default : 
				break;
		} // end of switch
		
		// configure the activity animation transition effect
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}	
} // end class
