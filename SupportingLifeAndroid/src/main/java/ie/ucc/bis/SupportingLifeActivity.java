package ie.ucc.bis;

import android.app.Activity;
import android.os.Bundle;

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
}