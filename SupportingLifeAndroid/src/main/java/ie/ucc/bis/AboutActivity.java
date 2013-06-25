package ie.ucc.bis;

import android.os.Bundle;


/**
 * 
 * This is the About activity in the Supporting LIFE application.
 * 
 * The purpose of the class is to display information text about the application and
 * also provides a way to get back to the home activity.
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
		setTitleFromActivityLabel(R.id.title_text);
	}
	
}
