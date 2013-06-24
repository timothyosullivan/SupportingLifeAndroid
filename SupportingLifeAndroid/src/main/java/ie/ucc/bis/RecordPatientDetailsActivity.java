package ie.ucc.bis;

import ie.ucc.bis.domain.Patient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class RecordPatientDetailsActivity extends Activity {

	public final static String EXTRA_MESSAGE = "ie.ucc.bis.supportinglife.MESSAGE";
	
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_patient_details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(ie.ucc.bis.R.menu.main, menu);
	return true;
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	// Ensure Screen is refreshed if back button is pressed
    	setContentView(R.layout.activity_record_patient_details);
    	
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