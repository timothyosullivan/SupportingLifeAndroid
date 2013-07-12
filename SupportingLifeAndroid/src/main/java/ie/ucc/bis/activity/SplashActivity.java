package ie.ucc.bis.activity;

import ie.ucc.bis.R;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;


/**
 * 
 * This is the Splash activity in the Supporting LIFE application.
 * 
 * The purpose of the activity is to display a splash screen when
 * launching the application.
 * 
 * @author timothyosullivan
 *
 */
public class SplashActivity extends SupportingLifeBaseActivity {

	private static final long SPLASH_DELAY = 2000; // 2 seconds

	/**
	 * onCreate method
	 * 
	 * Called when the activity is first created.
	 * Method is always followed by onStart() method.
	 * 
	 * @param savedInstanceState Bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		TimerTask task = new TimerTask()
		{

			@Override
			public void run() {
				// call finish on SplashActivity to prevent user from using
				// back button to navigate back to Splash screen
				finish();
				Intent dashboardIntent = new Intent().setClass(SplashActivity.this, HomeActivity.class);
				startActivity(dashboardIntent);
				// configure the activity animation transition effect
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}

		};

		Timer timer = new Timer();
		timer.schedule(task, SPLASH_DELAY);
	}
}
