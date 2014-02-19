package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;

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
	public static class MyPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener
	{
		private static final String LOG_TAG = "ie.ucc.bis.supportinglife.activity.SettingsActivity.MyPreferenceFragment";
		
		@Override
		public void onCreate(final Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
			getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onResume() {
			super.onResume();
			for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
				Preference preference = getPreferenceScreen().getPreference(i);
				if (preference instanceof PreferenceGroup) {
					PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
					for (int j = 0; j < preferenceGroup.getPreferenceCount(); ++j) {
						updatePreference(preferenceGroup.getPreference(j));
					}
				} else {
					updatePreference(preference);
				}
			}
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			// need to wait for activity to attach
			if (getActivity() != null) {
				updatePreference(findPreference(key));
			}
		}

		private void updatePreference(Preference preference) {
			if (preference instanceof ListPreference) {
				ListPreference listPreference = (ListPreference) preference;
				
				// check if this relates to the language selection preference
				if (listPreference.getKey().equalsIgnoreCase(LANGUAGE_SELECTION_KEY)) {
					((SupportingLifeBaseActivity) getActivity()).setLocale(listPreference.getValue());
					LoggerUtils.i(LOG_TAG, "User Language Preference changed to: " + listPreference.getValue());
				}
				
				listPreference.setSummary(listPreference.getEntry());
			}
			else if (preference instanceof EditTextPreference) {
				EditTextPreference editTextPref = (EditTextPreference) preference;
				editTextPref.setSummary(editTextPref.getText());
			}
		}
		
	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);	        
	    }
	} // end of 'MyPreferenceFragment' static class
	
} // end of class
