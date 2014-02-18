package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * 
 * This is the Settings activity in the Supporting LIFE application.
 * 
 * The purpose of the activity is to provide the user with the capability
 * to configure preferences according to their intended usage.
 * 
 * Example preferences / settings include:
 * 
 * 1. Language Selection
 * 2. Synchronise Patient Assessment Records with Server Infrastructure
 * 
 * @author timothyosullivan
 *
 */
public class SettingsActivity extends SupportingLifeBaseActivity {

	/**
	 * onCreate method
	 * 
	 * Called when the activity is first created.
	 * Method is always followed by onStart() method.
	 * 
	 * @param savedInstanceState Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {       
		super.onCreate(savedInstanceState);       
		onCreatePreferenceFragment();   
	}

	/**
	 * Wraps {@link #onCreate(Bundle)} code for Android >= 3 (i.e. API lvl >=
	 * 11).
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void onCreatePreferenceFragment() {
		getFragmentManager().beginTransaction()
		.replace(android.R.id.content, new MyPreferenceFragment())
		.commit();
	}

	// static class
	public static class MyPreferenceFragment extends PreferenceFragment
	{
		@Override
		public void onCreate(final Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
		}
	}
}
