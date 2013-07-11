package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
public abstract class SupportingLifeBaseActivity extends FragmentActivity {
	
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
	@Override
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
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	/**
	 * onStart method is called when the activity is becoming visible to the user.
	 * 
	 * This method is followed by onResume() if the activity comes to the foreground.
	 * 
	 */
	@Override
	protected void onStart() {
		super.onStart();
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
	 * Method to return to the Home activity
	 * 
	 * @param context Context
	 * @return void
	 */
	public void goHome(Context context) {
		final Intent intent = new Intent(context, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		
		// configure the activity animation transition effect
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
		
		// configure the activity animation transition effect
		// overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	
	/**
	 * Click Handler: Handle the click on the about button
	 * 
	 * @param view View
	 * @return void
	 */
	public void onClickAbout(View view) {
		startActivity(new Intent(getApplicationContext(), AboutActivity.class));
		
		// configure the activity animation transition effect
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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