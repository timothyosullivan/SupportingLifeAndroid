package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.communication.PatientAssessmentResponseComms;
import ie.ucc.bis.supportinglife.service.SupportingLifeService;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
	private Button syncButton;
	private ProgressDialog progressDialog;
	
	private ProgressBar circularProgressBar;
	private ProgressBar horizontalProgressBar;
	private TextView circularProgressBarText;
	
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
		
		// get a handle on the synchronisation button
		setSyncButton((Button) findViewById(R.id.sync_button));
		
		// add click listener to the 'Sync' button
		getSyncButton().setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	LoggerUtils.i(LOG_TAG, "SyncButton: onClick -- Sync Button on Synchronisation Clicked!");
            	
            	// show circular progress bar until connection established
            	getCircularProgressBar().setVisibility(View.VISIBLE);
            	getCircularProgressBarText().setVisibility(View.VISIBLE);
            	           	
            	// retrieve non-synced patient assessment from the DB
        		List<PatientAssessmentComms> nonSyncedPatientAssessmentComms = getSupportingLifeService().getAllNonSyncedPatientAssessmentComms();
        		LoggerUtils.i(LOG_TAG, "SyncButton: onClick -- Number of non-synced patient assessments to be synced ~ " + nonSyncedPatientAssessmentComms.size());
            	
            	///////////// TEMP START

                setProgressDialog(new ProgressDialog(SyncActivity.this));          
                getProgressDialog().setMessage("Syncing Patient Assessments....");
                getProgressDialog().setTitle("Please Wait..");
                getProgressDialog().setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                getProgressDialog().setProgress(0);
                getProgressDialog().setMax(30);
                getProgressDialog().setMax(nonSyncedPatientAssessmentComms.size());
                getProgressDialog().show();
 
                final IncomingMessageHandler handler = new IncomingMessageHandler(getProgressDialog());
                
                new Thread(new Runnable() {
                    @Override
                     public void run() {
                    	
                		// Looper is used to run a message loop for a thread.  Threads by default do
                		// not have a message loop associated with them; to create one, call
                		// prepare() in the thread that is to run the loop, and then
                		// loop() to have it process messages until the loop is stopped
                		Looper.prepare();                     	
                		
                    	try {
                    		while (getProgressDialog().getProgress() <= getProgressDialog().getMax()) {
                    			Thread.sleep(1000);
                    			handler.sendMessage(handler.obtainMessage());
                             			
                    			if(getProgressDialog().getProgress() == getProgressDialog().getMax()) {
                    				getProgressDialog().dismiss();
                    			}
                    		}
                    		Looper.loop(); 
                    	} catch(InterruptedException interruptException) {
                    		LoggerUtils.i(LOG_TAG, "SyncButton: onClick -- Interrupt Exception Thrown!");
                    		LoggerUtils.i(LOG_TAG, "SyncButton: onClick -- Interrupt Exception Details: " + interruptException.getMessage());
                    	}
                     }
               }).start(); 
	
            	///////////// TEMP END
       		
        		// transmit non-synced patient assessments
        		setNetworkCommsTask(new NetworkCommunicationAsyncTask());
       			getNetworkCommsTask().execute(nonSyncedPatientAssessmentComms.toArray(new PatientAssessmentComms[nonSyncedPatientAssessmentComms.size()]));
            }
        });	
	}
	
	private static class IncomingMessageHandler extends Handler {
	    private final WeakReference<ProgressDialog> dialog; 

	    public IncomingMessageHandler(ProgressDialog progressDialog) {
	    	dialog = new WeakReference<ProgressDialog>(progressDialog);
	    }
	    
	    @Override
	    public void handleMessage(Message msg)
	    {
	    	super.handleMessage(msg);
	    	ProgressDialog pDialog = getDialog().get();
	    	if (pDialog != null) {
	    		pDialog.incrementProgressBy(5);
	    	}
	    }

		public WeakReference<ProgressDialog> getDialog() {
			return dialog;
		}
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
				LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- PATIENT SYNCING SUCCESSFUL");
			}
			else {
				LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- PATIENT SYNCING UNSUCCESSFUL!");
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(PatientAssessmentResponseComms... values) {	
        	// connection established so remove circular progress bar
        	getCircularProgressBar().setVisibility(View.GONE);
        	getCircularProgressBarText().setVisibility(View.GONE);
			
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
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- National ID");
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- " + patientAssessment.getNationalId());
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- National Health ID");
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- " + patientAssessment.getNationalHealthId());
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- Child First Name");
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- " + patientAssessment.getChildFirstName());
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- Child Surname");
			LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- " + patientAssessment.getChildSurname());
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

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public void setProgressDialog(ProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}
	
} // end of SyncActivity class