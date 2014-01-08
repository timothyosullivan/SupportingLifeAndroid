package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import android.os.Bundle;


/**
 * 
 * This is the Help activity in the Supporting LIFE application.
 * 
 * The purpose of the activity is to provide a help console for the
 * user to assist them in understanding terminology within IMCI and CCM
 * 
 * @author timothyosullivan
 *
 */
public class HelpActivity extends SupportingLifeBaseActivity {

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

		setContentView(R.layout.activity_help);		
		setTitleFromActivityLabel(R.id.action_bar_title_text);
	}
}
