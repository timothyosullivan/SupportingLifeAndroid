package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import android.os.Bundle;


/**
 * 
 * This is the 'Patient Assessment' Synchronisation activity 
 * of the Supporting LIFE application.
 * 
 * The purpose of the activity is to provide an interface to 
 * facilitate the user to sync/upload the patient assessment
 * records held on the device database to the web server.
 * 
 * @author timothyosullivan
 *
 */
public class SyncActivity extends SupportingLifeBaseActivity {

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

		setContentView(R.layout.activity_sync);		
		setTitleFromActivityLabel(R.id.action_bar_title_text);
	}
}