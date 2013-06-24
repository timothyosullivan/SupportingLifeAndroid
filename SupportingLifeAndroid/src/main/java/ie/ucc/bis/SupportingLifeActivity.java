package ie.ucc.bis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the base class for activities in the Supporting LIFE
 * application.
 * 
 * It implements methods that are useful to all top level activities.
 * This includes the following:
 * 
 * (1) Stub methods for all the activity lifecycle methods.
 * (2) onClick methods for clicks on home, initial patient visit, patient search, etc.
 * (3) A method for displaying a message to the screen via the Toast class.
 * 
 * @author Tim O Sullivan
 *
 */
public abstract class SupportingLifeActivity extends Activity {
	
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	protected void onDestroy() {
		super.onDestroy();
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
	protected void onPause() {
		super.onPause();
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
	protected void onRestart() {
		super.onRestart();
	}
	
	/**
	 * onStart method is called when the activity is becoming visible to the user.
	 * 
	 * This method is followed by onResume() if the activity comes to the foreground.
	 * 
	 */	
	protected void onStart() {
		super.onStart();
	}

	/**
	 * onStop method is called when the activity is becoming visible to the user.
	 * 
	 * This method is followed by onResume() if the activity comes to the foreground.
	 * 
	 */	
	protected void onStop() {
		super.onStop();
	}
	
	/**
	 * Method to return to the Home activity
	 * 
	 * @param context Context
	 * @return void
	 */
	public void goHome(Context context) {
		final Intent intent = new Intent(context, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
	
	/**
	 * Click Handler: Handle the click on the home button
	 * 
	 * @param view View
	 * @return void
	 */
	public void onClickHome(View view) {
		goHome(this);
	}
	
	/**
	 * Click Handler: Handle the click on the search button
	 * 
	 * @param view View
	 * @return void
	 */
	public void onClickSearch(View view) {
//		startActivity(new Intent(getApplicationContext(), SearchActivity.class));
	}
	
	/**
	 * Click Handler: Handle the click on the about button
	 * 
	 * @param view View
	 * @return void
	 */
	public void onClickAbout(View view) {
		startActivity(new Intent(getApplicationContext(), AboutActivity.class));
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
			case R.id.home_btn_about_feature :
				startActivity(new Intent(getApplicationContext(), AboutActivity.class));
			default : 
				break;
		} // end of switch
	}
	
	
	/**
	 * Method to use the activity label to set the text in the activity's title text view.
	 * The argument gives the name of the view.
	 * 
	 * This method is needed because we have a custom title bar rather than the default 
	 * Android title bar.
	 * 
	 * @param textViewId int
	 * @return void
	 */
	public void setTitleFromActivityLabel(int textViewId) {
		TextView textView = (TextView) findViewById(textViewId);
		if (textView != null) {
			textView.setText(getTitle());
		}
	}
	
	/**
	 * Utility method to show a string on the screen via Toast
	 * @param msg String
	 * @return void
	 */
	public void toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Utility method to send a message to the debug log and display it using Toast
	 * @param msg String
	 * @return void
	 */
	public void trace(String msg) {
		Log.d("Supporting LIFE", msg);
		toast(msg);
	}
}