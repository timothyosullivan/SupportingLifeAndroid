package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import android.os.Bundle;


/**
 * 
 * This is the About activity in the Supporting LIFE application.
 * 
 * The purpose of the activity is to display textual and pictorial content
 * about the application.
 * 
 * @author timothyosullivan
 *
 */
public class AboutActivity extends SupportingLifeBaseActivity {

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

		setContentView(R.layout.activity_about);		
		setTitleFromActivityLabel(R.id.action_bar_title_text);
		
		// configure animation of Supporting LIFE images
		/* configureAndRunImageAnimator(); */
	}
	
	/**
	 * Method: configureAndRunImageAnimator
	 * 
	 * Responsible for configuration and running animation of 
	 * Supporting LIFE images on the About Screen.
	 * 
	 * Image animation is handled in a separate thread.
	 * 
	 */	
/*	private void configureAndRunImageAnimator() {
		// Load the ImageView that will host the supporting life 
		// image animation and set its background to our 
		// AnimationDrawable XML resource.
		final ImageView supportingLifeImageView = (ImageView)findViewById(R.id.spinning_wheel_image);
		// set ImageView to visible
		supportingLifeImageView.setVisibility(View.VISIBLE);
		supportingLifeImageView.setBackgroundResource(R.anim.spin_animation);

		supportingLifeImageView.post(new Runnable() {
			public void run() {
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) supportingLifeImageView.getBackground();

				// Start the animation (looped playback by default).
				frameAnimation.start();		 
			} // end of run() method
		});	// end of Runnable inner class
	}
*/
}
