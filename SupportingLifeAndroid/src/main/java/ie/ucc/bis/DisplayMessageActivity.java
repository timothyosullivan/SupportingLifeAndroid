package ie.ucc.bis;

import ie.ucc.bis.domain.Patient;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends SupportingLifeActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);

		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		// extract the message sent by the main activity
		Intent intent = getIntent();
		String message = intent.getStringExtra(RecordPatientDetailsActivity.EXTRA_MESSAGE);

		// create a TextView to display the message
		TextView textView = new TextView(this);
		textView.setTextSize(300);

		// instigate network communication to retrieve patient record
		new NetworkCommunicator().execute(message);

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

	private class NetworkCommunicator extends AsyncTask<String, Void, String> {

		private static final String AMAZON_WEB_SERVICE_URL = "http://ec2-54-226-94-252.compute-1.amazonaws.com/SupportingLife/patients/{id}";
		
		@Override
		protected String doInBackground(String... params) {

			RestTemplate restTemplate = new RestTemplate();
			
			long patientId = Long.parseLong(params[0]);
			try {
				restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
				Patient patientResult = restTemplate.getForObject(AMAZON_WEB_SERVICE_URL, Patient.class, patientId);
				return patientResult.getFirstName() + " " + patientResult.getSurname();
			} catch (ResourceAccessException ex) {
				System.out.println("OFF");
			} catch (RestClientException ex) {
				System.out.println("Error");
			}
			return null;
		}      

		@Override
		protected void onPostExecute(String result) {
			TextView txt = (TextView) findViewById(R.id.network_comm_result);
			txt.setText("Patient Name:" + " " + result);
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}       

}