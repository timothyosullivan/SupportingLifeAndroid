package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import android.os.Bundle;

public class TrainingActivity extends SupportingLifeBaseActivity {

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

		setContentView(R.layout.activity_training);	
		setTitleFromActivityLabel(R.id.action_bar_title_text);
	}


}
