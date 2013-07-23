package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import ie.ucc.bis.domain.Patient;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class SubmitPatientRecordActivity extends SupportingLifeBaseActivity {
	
	private NetworkCommunicationAsyncTask task;
	private Patient submittedPatient;

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_patient_record);
		
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		addSoftKeyboardHandling(this.findViewById(android.R.id.content));		
		
		setTitleFromActivityLabel(R.id.title_text);

		// extract the patient record sent by the main activity
		Intent intent = getIntent();
		Patient patient= (Patient) intent.getSerializableExtra(RecordPatientDetailsActivity.EXTRA_MESSAGE);

		// instigate network communication to retrieve patient record
		task = new NetworkCommunicationAsyncTask();
		task.execute(patient);
	}
	
	
	@Override
	protected void onPause() {
	    super.onPause();
	    // cancel the network communication task if its still running
		task.cancel(true);
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
				((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(120 * 1000);
				((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(120 * 1000);
				restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
				
				submittedPatient = restTemplate.postForObject(AMAZON_WEB_SERVICE_URL, patient, Patient.class);
				return submittedPatient;
			} catch (ResourceAccessException ex) {
				System.out.println("OFF");
				// TODO need to add logging capability to catch stack trace
				ex.printStackTrace();
			} catch (RestClientException ex) {
				System.out.println("Error");
				// TODO need to add logging capability to catch stack trace
				ex.printStackTrace();
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