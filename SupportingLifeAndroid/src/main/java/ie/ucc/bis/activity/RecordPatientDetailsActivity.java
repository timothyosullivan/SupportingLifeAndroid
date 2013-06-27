package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import ie.ucc.bis.domain.Patient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RecordPatientDetailsActivity extends SupportingLifeBaseActivity {

	public final static String EXTRA_MESSAGE = "ie.ucc.bis.supportinglife.MESSAGE";
	
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_patient_details);
        setTitleFromActivityLabel(R.id.title_text);
    }
    
    /**
     * Method invoked when the user clicks the Submit button
     * 
     * @param view
     */
    public void sendMessage(View view) {
        // start DisplayMessageActivity
    	Intent intent = new Intent(this, SubmitPatientRecordActivity.class);
    	
    	// attach contents of completed Patient text entries in data bundle to DisplayMessageActivity    	
    	// patient first name 	
    	EditText editText = (EditText) findViewById(R.id.first_name_text);
    	String firstName = editText.getText().toString();
    	
    	// patient surname
    	editText = (EditText) findViewById(R.id.surname_text);
    	String surname = editText.getText().toString();
    	
    	Patient patient = new Patient(firstName, surname);
    	intent.putExtra(EXTRA_MESSAGE, patient);
    	startActivity(intent);    	
    }   
}