package ie.ucc.bis;

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
	 * onDestroy method is the final method called before the activity is destroyed.
	 * 
	 * This can happen either because the activity is finishing (someone called
	 * finish() on it, or because the system is temporarily destroying this
	 * instance of the activity to save space). These two scenarios can be
	 * distinguished with the isFinishing() method.
	 * 
	 */
	@Override
	protected void onDestroy () {
		super.onDestroy ();
	}

	/**
	 * onPause method is called when the system is about to start resuming a previous
	 * activity.
	 * 
	 * This method is typically used to commit unsaved changes to persistent data, stop
	 * animations and anything else that may be consuming CPU, etc.
	 * 
	 * Implementations of this method must be very quick because the next activity will
	 * not be resumed until this method returns.
	 * 
	 * This method is followed by either onResume() if the activity returns back to the
	 * front, or onStop() if the activity becomes invisible to the user.
	 * 
	 */
	@Override
	protected void onPause () {
		super.onPause ();
	}

	/**
	 * onRestart method is called when the activity will start interacting with the user.
	 * 
	 * At this point, the activity is at the top of the activity stack, with user input
	 * going into it.
	 * 
	 * This method is always followed by onPause().
	 * 	
	 */
	@Override
	protected void onRestart ()	{
		super.onRestart ();
	}

	/**
	 * onResume method is called when the activity will start interacting with the user.
	 * 
	 * At this point your activity is at the top of the activity stack, with user input going to it.
	 * 
	 * This method is always followed by onPause().
	 *
	 */
	@Override
	protected void onResume () {
		super.onResume ();
	}

	/**
	 * onStart method is called when the activity is becoming visible to the user.
	 * 
	 * This method is followed by onResume() if the activity comes to the foreground.
	 * 
	 */
	@Override
	protected void onStart () {
		super.onStart ();
	}

	/**
	 * onStop method is called when the activity is no longer visible to the user
	 * because another activity has been resumed and is covering this one.
	 * 
	 * This may happen either because a new activity is being started, an existing one 
	 * is being brought in front of this one, or this one is being destroyed.
	 *
	 * This method is followed by either onRestart() if this activity is coming back to interact with the user, 
	 * or onDestroy() if this activity is going away.
	 */
	@Override
	protected void onStop () {
		super.onStop ();
	}
	
	/**
	 * Click Handler: Handler the click of a feature button
	 * 
	 * @param view View
	 * @return void
	 */
	public void onClickFeature(View view) {
		int id = view.getId();
		switch(id) {
			case R.id.home_btn_record_patient_details_feature :
				startActivity(new Intent(getApplicationContext(), RecordPatientDetailsActivity.class));
				break;
			case R.id.home_btn_about_feature :
				startActivity(new Intent(getApplicationContext(), AboutActivity.class));
				break;
			default : 
				break;
		} // end of switch
	}	
} // end class
