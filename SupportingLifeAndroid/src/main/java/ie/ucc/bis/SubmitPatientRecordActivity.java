package ie.ucc.bis;

import ie.ucc.bis.domain.Patient;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

public class SubmitPatientRecordActivity extends Activity {
	
	private NetworkCommunicationAsyncTask task;
	private Patient submittedPatient;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_patient_record);

		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		// extract the patient record sent by the main activity
		Intent intent = getIntent();
		Patient patient= (Patient) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);

		// instigate network communication to retrieve patient record
		task = new NetworkCommunicationAsyncTask();
		task.execute(patient);
	}
	

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	    // cancel the network communication task if its still running
		task.cancel(true);
	}
	
	/* 
	 * Handles the behaviour for the action bar's Up behaviour
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class NetworkCommunicationAsyncTask extends AsyncTask<Patient, Void, Patient> {

		private static final String AMAZON_WEB_SERVICE_URL = "http://ec2-54-226-94-252.compute-1.amazonaws.com/SupportingLife/patients/add";
		
		@Override
		protected Patient doInBackground(Patient... params) {

			RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			
			Patient patient = (Patient) params[0];
			try {
				// The default timeout was resulting in the call to the 'restTemplate.postForObject(..)' method
				// call sometimes returning a null object and sometimes returning a correctly populated object.
				// Doubling the read timeout led to more reliability in obtaining a correctly populated object.
				// default timeout is 60 * 1000
				((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(120 * 1000);
				restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
				
				submittedPatient = restTemplate.postForObject(AMAZON_WEB_SERVICE_URL, patient, Patient.class);
				return submittedPatient;
			} catch (ResourceAccessException ex) {
				System.out.println("OFF");
			} catch (RestClientException ex) {
				System.out.println("Error");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Patient patient) {
			TextView txt = (TextView) findViewById(R.id.network_comm_result);
			if (patient != null) {
			txt.setText("Patient Id: " + patient.getPatientId() + "\n");
			txt.append("Patient Name: " + patient.getFirstName() + " " + patient.getSurname());
			}
			else {
				txt.setText("COMMUNICATION ERROR!");
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}       

}