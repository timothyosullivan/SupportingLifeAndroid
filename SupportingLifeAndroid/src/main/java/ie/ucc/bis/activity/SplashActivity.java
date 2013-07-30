package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

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
	private Thread splashThread;

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

		// thread for displaying the SplashScreen
		splashThread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized(this) {
						wait(SPLASH_DELAY);
					} // end of sync
				} catch (InterruptedException interruptExp) {}
				finally {
					startActivity(new Intent(getApplicationContext(), HomeActivity.class));
					// configure the activity animation transition effect
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					// call finish on SplashActivity to prevent user from using
					// back button to navigate back to Splash screen
					finish();
				} // end of finally
			}};
		splashThread.start();
	} // end of onCreate(..) method
	
	/**
	 * onTouchEvent method
	 * 
	 * Allow a user to bypass the touch screen via a touch event
	 * 
	 * @param event MotionEvent
	 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized(splashThread){
                    splashThread.notifyAll();
            }
        } // end of if
        return true;
    }


}
