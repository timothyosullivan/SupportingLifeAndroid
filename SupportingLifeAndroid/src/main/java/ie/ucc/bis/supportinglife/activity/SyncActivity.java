package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.communication.PatientAssessmentResponseComms;
import ie.ucc.bis.supportinglife.service.SupportingLifeService;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


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

	private final String LOG_TAG = "ie.ucc.bis.supportinglife.activity.SyncActivity";
	
	private NetworkCommunicationAsyncTask networkCommsTask;
	private SupportingLifeService supportingLifeService;
	
	private TextView syncRecordsRequiredTextView;
	private Button syncButton;
	private ProgressBar circularProgressBar;
	private TextView circularProgressBarText;
	private ProgressBar horizontalProgressBar;
	private TextView horizontalProgressBarTextView;
	
	private Integer unsyncedRecords;
	private int progressCounter;
	
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
		
		// configure visibility of circular & horizontal progress bars
		initialiseProgressBars();
		
		// initialise SupportingLifeService
        setSupportingLifeService(new SupportingLifeService(this));
        getSupportingLifeService().open();
        
		setSyncRecordsRequiredTextView((TextView) findViewById(R.id.sync_records_outstanding));
        // determine the number of records requiring sync
		updateSyncRecordCountDisplay();
        
		// get a handle on the synchronisation button
		setSyncButton((Button) findViewById(R.id.sync_button));
		
		// get a handle on the horizontal progress counter
		setHorizontalProgressBarTextView((TextView) findViewById(R.id.horizontal_progress_update_text));
		getHorizontalProgressBarTextView().setVisibility(View.GONE);
		
		
		// add click listener to the 'Sync' button
		getSyncButton().setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	LoggerUtils.i(LOG_TAG, "SyncButton: onClick -- Sync Button on Synchronisation Clicked!");
            	
            	// disable sync button until syncing operation complete to avoid repeat clicking by John
            	getSyncButton().setEnabled(false);
            	
            	// show circular progress bar until connection established
            	getCircularProgressBar().setVisibility(View.VISIBLE);
            	getCircularProgressBarText().setVisibility(View.VISIBLE);
            	           	            	       		
        		List<PatientAssessmentComms> nonSyncedPatientAssessmentComms = getSupportingLifeService().getAllNonSyncedPatientAssessmentComms();
        		LoggerUtils.i(LOG_TAG, "SyncButton: onClick -- Number of non-synced patient assessments to be synced ~ " + nonSyncedPatientAssessmentComms.size());
            	
        		// transmit non-synced patient assessments
        		if (nonSyncedPatientAssessmentComms.size() != 0) {
        			setNetworkCommsTask(new NetworkCommunicationAsyncTask());
        			getHorizontalProgressBar().setMax(nonSyncedPatientAssessmentComms.size());
        			getNetworkCommsTask().execute(nonSyncedPatientAssessmentComms.toArray(new PatientAssessmentComms[nonSyncedPatientAssessmentComms.size()]));
        		}
        		else {
                	// not records to sync so remove circular progress bar
                	getCircularProgressBar().setVisibility(View.GONE);
                	getCircularProgressBarText().setVisibility(View.GONE);
                	// re-enable sync button
                	getSyncButton().setEnabled(true);
        		}
            }
        });	
	}
	
    @Override
    protected void onResume() {
    	getSupportingLifeService().open();
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	getSupportingLifeService().close();
    	super.onPause();
    }
	
	private class NetworkCommunicationAsyncTask extends AsyncTask<PatientAssessmentComms, PatientAssessmentResponseComms, Boolean> {

		private static final String AMAZON_WEB_SERVICE_URL = "http://supportinglife.elasticbeanstalk.com/patientvisits/add";
		
		@Override
		protected Boolean doInBackground(PatientAssessmentComms... params) {		
			RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());		
			for (PatientAssessmentComms patientAssessmentComm : params) {
			
				try {
					// The default timeout was resulting in the call to the 'restTemplate.postForObject(..)' method
					// call sometimes returning a null object and sometimes returning a correctly populated object.
					// Doubling the read timeout led to more reliability in obtaining a correctly populated object.
					// default timeout is 60 * 1000
					((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(120 * 1000);
					((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(120 * 1000);
					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
				
					PatientAssessmentResponseComms assessmentResponse = restTemplate.postForObject(AMAZON_WEB_SERVICE_URL, patientAssessmentComm, PatientAssessmentResponseComms.class);
					
					publishProgress(assessmentResponse);
				} catch (ResourceAccessException ex) {
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: doInBackground -- ResourceAccessException");
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: doInBackground -- " + ex.getMessage());
				} catch (RestClientException ex) {
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: doInBackground -- RestClientException");
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: doInBackground -- " + ex.getMessage());
				}
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean success) {
			if (success) {
				LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- PATIENT SYNCING OPERATION SUCCESSFUL");
			}
			else {
				LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- PATIENT SYNCING OPERATION UNSUCCESSFUL!");
			}
			
			// remove horizontal progress bar from view
			getHorizontalProgressBar().setVisibility(View.GONE);		
			getHorizontalProgressBarTextView().setVisibility(View.GONE);
			
	        // update the display of the number of records requiring sync
			updateSyncRecordCountDisplay();	
			
			// re-enable sync button
			getSyncButton().setEnabled(true);
		}

		@Override
		protected void onPreExecute() {
			getHorizontalProgressBar().setVisibility(View.VISIBLE);
			getHorizontalProgressBarTextView().setVisibility(View.VISIBLE);
			setProgressCounter(0);
			updateHorizontalProgressText(0);
		}

		@Override
		protected void onProgressUpdate(PatientAssessmentResponseComms... values) {	
        	// connection established so remove circular progress bar
        	getCircularProgressBar().setVisibility(View.GONE);
        	getCircularProgressBarText().setVisibility(View.GONE);
        	
        	getHorizontalProgressBar().setVisibility(View.VISIBLE);
        	getHorizontalProgressBarTextView().setVisibility(View.VISIBLE);
        	setProgressCounter(getProgressCounter() + 1);
        	getHorizontalProgressBar().setProgress(getProgressCounter());     	
        	updateHorizontalProgressText(getProgressCounter());
        				
			// update the sync column for the patient record to indicate that it has 
			// now been synchronised
			int rowCount = getSupportingLifeService().setPatientAssessmentToSynced(values[0].getDeviceGeneratedAssessmentId());
			if (rowCount == 1) {
				LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- Single Patient Record Synced Succesfully ~ " 
						+ values[0].getDeviceGeneratedAssessmentId());
			}
			else {
				LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- EXPECTED PATIENT RECORD ROW TO BE SYNCED!!!!");
			}
        	
			generateAssessmentResponseDebugOutput(values[0]);
		}
				
		/**
		 * Produces debug output of the patient assessment response received from
		 * the server
		 * 
		 * @param patientAssessment
		 */
		private void generateAssessmentResponseDebugOutput(PatientAssessmentResponseComms patientAssessment) {
			
			LoggerUtils.i(LOG_TAG, "===================================================================================");
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- PATIENT ASSESSMENT RESPONSE SUCCESS");
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- Device Generated Assessment ID");
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- " + patientAssessment.getDeviceGeneratedAssessmentId());
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- Patient Visit ID");
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- " + patientAssessment.getPatientVisitId());
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- Patient ID");
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- " + patientAssessment.getPatientId());
		}
		
	} // end of inner class 'NetworkCommunicationAsyncTask'

	/**
	 * Obtain handles on circular & horizontal progress bars and
	 * configure their initial visibility
	 * 
	 */
	private void initialiseProgressBars() {
		setCircularProgressBar((ProgressBar) findViewById(R.id.circular_progress_bar));
		getCircularProgressBar().setVisibility(View.GONE);
		setCircularProgressBarText((TextView) findViewById(R.id.circular_progress_bar_text));
		getCircularProgressBarText().setVisibility(View.GONE);
		setHorizontalProgressBar((ProgressBar) findViewById(R.id.horizontal_progress_bar));
		getHorizontalProgressBar().setVisibility(View.GONE);
	}
	
	/**
	 * Update the display on screen indicating the number of
	 * records requiring synchronisation
	 * 
	 */
	private void updateSyncRecordCountDisplay() {
		// firstly retrieve the record count if we don't have this value
		setUnsyncedRecords(getSupportingLifeService().getAllNonSyncedPatientAssessmentComms().size());
		
		// update the text view
		if (getSyncRecordsRequiredTextView() != null) {
				getSyncRecordsRequiredTextView().setText(getUnsyncedRecords().toString());
		}
	}
	
	/**
	 * Update the display on screen indicating the number of
	 * records which have so far been synced i.e. capture the
	 * progress of the horizontal progress bar
	 * 
	 */
	private void updateHorizontalProgressText(int currentSyncCount) {
		if (getHorizontalProgressBarTextView() != null) {
			getHorizontalProgressBarTextView().setText(Integer.valueOf(currentSyncCount).toString() + "\\" + getUnsyncedRecords().toString());
		}
	}
	
	public NetworkCommunicationAsyncTask getNetworkCommsTask() {
		return networkCommsTask;
	}

	public void setNetworkCommsTask(NetworkCommunicationAsyncTask networkCommsTask) {
		this.networkCommsTask = networkCommsTask;
	}

	public SupportingLifeService getSupportingLifeService() {
		return supportingLifeService;
	}

	public void setSupportingLifeService(SupportingLifeService supportingLifeService) {
		this.supportingLifeService = supportingLifeService;
	}

	public TextView getSyncRecordsRequiredTextView() {
		return syncRecordsRequiredTextView;
	}

	public void setSyncRecordsRequiredTextView(TextView syncRecordsRequiredTextView) {
		this.syncRecordsRequiredTextView = syncRecordsRequiredTextView;
	}

	public Button getSyncButton() {
		return syncButton;
	}

	public void setSyncButton(Button syncButton) {
		this.syncButton = syncButton;
	}

	public ProgressBar getCircularProgressBar() {
		return circularProgressBar;
	}

	public void setCircularProgressBar(ProgressBar circularProgressBar) {
		this.circularProgressBar = circularProgressBar;
	}

	public ProgressBar getHorizontalProgressBar() {
		return horizontalProgressBar;
	}

	public void setHorizontalProgressBar(ProgressBar horizontalProgressBar) {
		this.horizontalProgressBar = horizontalProgressBar;
	}

	public TextView getCircularProgressBarText() {
		return circularProgressBarText;
	}

	public void setCircularProgressBarText(TextView circularProgressBarText) {
		this.circularProgressBarText = circularProgressBarText;
	}

	public TextView getHorizontalProgressBarTextView() {
		return horizontalProgressBarTextView;
	}

	public void setHorizontalProgressBarTextView(TextView horizontalProgressBarTextView) {
		this.horizontalProgressBarTextView = horizontalProgressBarTextView;
	}

	public Integer getUnsyncedRecords() {
		return unsyncedRecords;
	}

	public void setUnsyncedRecords(Integer unsyncedRecords) {
		this.unsyncedRecords = unsyncedRecords;
	}

	public int getProgressCounter() {
		return progressCounter;
	}

	public void setProgressCounter(int progressCounter) {
		this.progressCounter = progressCounter;
	}

} // end of SyncActivity class