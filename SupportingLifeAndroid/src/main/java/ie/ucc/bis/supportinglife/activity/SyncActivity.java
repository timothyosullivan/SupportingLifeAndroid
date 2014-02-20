package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.dao.PatientAssessmentDao;
import ie.ucc.bis.supportinglife.dao.PatientAssessmentDaoImpl;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.helper.PatientHandlerUtils;
import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;
import ie.ucc.bis.supportinglife.rule.engine.TreatmentRecommendation;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

import java.util.ArrayList;
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
	private PatientAssessmentDao patientAssessmentDao;
	private Button syncButton;
	
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
		
		// initialise PatientAssessmentDao
        setPatientAssessmentDao(new PatientAssessmentDaoImpl(this));
        getPatientAssessmentDao().open();
		
		// get a handle on the synchronisation button
		setSyncButton((Button) findViewById(R.id.sync_button));
		
		// add click listener to the 'Sync' button
		getSyncButton().setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	LoggerUtils.i(LOG_TAG, "SyncButton: onClick -- Sync Button on Synchronisation Clicked!");
            	
            	// retrieve non-synced patient assessment from the DB
        		List<PatientAssessment> nonSyncedPatientAssessments = getPatientAssessmentDao().getAllNonSyncedPatientAssessments();
        		LoggerUtils.i(LOG_TAG, "SyncButton: onClick -- Number of non-synced patient assessments to be synced ~ " + nonSyncedPatientAssessments.size());
        		
        		// transmit non-synced patient assessments
        		setNetworkCommsTask(new NetworkCommunicationAsyncTask());
       			getNetworkCommsTask().execute(nonSyncedPatientAssessments.toArray(new PatientAssessment[nonSyncedPatientAssessments.size()]));
            }
        });	
	}

    @Override
    protected void onResume() {
    	getPatientAssessmentDao().open();
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	getPatientAssessmentDao().close();
    	super.onPause();
    }
	
	private class NetworkCommunicationAsyncTask extends AsyncTask<PatientAssessment, Void, List<Long>> {

		private static final String AMAZON_WEB_SERVICE_URL = "http://supportinglife.elasticbeanstalk.com/patientvisits/add";
		
		@Override
		protected List<Long> doInBackground(PatientAssessment... params) {

			List<Long> addedPatients = new ArrayList<Long>();
			
			RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

			
			for (PatientAssessment patientAssessment : params) {
				// construct the 'PatientAssessmentComms' instance in preparation
				// from transmission across the network from the device to the web server
				PatientAssessmentComms patientTransmit = createPatientAssessmentCommsInstance(patientAssessment);
			
				try {
					// The default timeout was resulting in the call to the 'restTemplate.postForObject(..)' method
					// call sometimes returning a null object and sometimes returning a correctly populated object.
					// Doubling the read timeout led to more reliability in obtaining a correctly populated object.
					// default timeout is 60 * 1000
					((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(120 * 1000);
					((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(120 * 1000);
					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
				
					Long patientId = restTemplate.postForObject(AMAZON_WEB_SERVICE_URL, patientTransmit, Long.class);
					addedPatients.add(patientId);
				} catch (ResourceAccessException ex) {
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: doInBackground -- ResourceAccessException");
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: doInBackground -- " + ex.getMessage());
				} catch (RestClientException ex) {
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: doInBackground -- RestClientException");
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: doInBackground -- " + ex.getMessage());
				}
			}
			return addedPatients;
		}

		/**
		 * Responsible for constructing the 'PatientAssessmentComms' instance in preparation
		 * from transmission across the network from the device to the web server
		 * 
		 * @param patientAssessment
		 * 
		 * @return PatientAssessmentComms
		 */
		private PatientAssessmentComms createPatientAssessmentCommsInstance(PatientAssessment patientAssessment) {
			
			PatientAssessmentComms patientTransmit = new PatientAssessmentComms(
					patientAssessment.getDeviceGeneratedAssessmentId(), patientAssessment.getHsaUserId(), patientAssessment.getNationalId(), 
					patientAssessment.getNationalHealthId(), patientAssessment.getChildFirstName(), patientAssessment.getChildSurname(), 
					patientAssessment.getBirthDate(), patientAssessment.getGender(), patientAssessment.getCaregiverName(), 
					patientAssessment.getRelationship(), patientAssessment.getPhysicalAddress(), patientAssessment.getVillageTa(), 
					patientAssessment.getVisitDate(), patientAssessment.isChestIndrawing(), patientAssessment.getBreathsPerMinute(),
					patientAssessment.isSleepyUnconscious(), patientAssessment.isPalmarPallor(), patientAssessment.getMuacTapeColour(), 
					patientAssessment.isSwellingBothFeet(), patientAssessment.getProblem(), patientAssessment.isCough(), patientAssessment.getCoughDuration(),
					patientAssessment.isDiarrhoea(), patientAssessment.getDiarrhoeaDuration(), patientAssessment.isBloodInStool(), patientAssessment.isFever(),
					patientAssessment.getFeverDuration(), patientAssessment.isConvulsions(), patientAssessment.isDifficultyDrinkingOrFeeding(),
					patientAssessment.isUnableToDrinkOrFeed(), patientAssessment.isVomiting(), patientAssessment.isVomitsEverything(),
					patientAssessment.isRedEye(), patientAssessment.getRedEyeDuration(), patientAssessment.isDifficultySeeing(),
					patientAssessment.getDifficultySeeingDuration(), patientAssessment.isCannotTreatProblem(), 
					patientAssessment.getCannotTreatProblemDetails());
			
			for (Diagnostic diagnostic : patientAssessment.getDiagnostics()) {
				// extract classifications
				if (diagnostic.getClassification().getIdentifier() != null) {
					patientTransmit.getClassifications().put(diagnostic.getClassification().getIdentifier(), diagnostic.getClassification().getName());
				}
				
				// extract treatments
				for (TreatmentRecommendation treatmentRecommendation : diagnostic.getTreatmentRecommendations()) {
					patientTransmit.getTreatments().put(treatmentRecommendation.getTreatmentIdentifier(), 
							PatientHandlerUtils.removeEscapeCharacters(treatmentRecommendation.getTreatmentDescription()));
				}
				
			}
			return patientTransmit;
		}

		@Override
		protected void onPostExecute(List<Long> addedPatients) {
			
			for (Long slPatientId : addedPatients) {
				if (slPatientId != null) {
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- Patient ID");
					LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- " + slPatientId.longValue());
				}
				else {
				LoggerUtils.i(LOG_TAG, "NetworkCommunicationAsyncTask: onPostExecute -- COMMUNICATION ERROR!");
				}
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	} // end of inner class 'NetworkCommunicationAsyncTask'

	public NetworkCommunicationAsyncTask getNetworkCommsTask() {
		return networkCommsTask;
	}

	public void setNetworkCommsTask(NetworkCommunicationAsyncTask networkCommsTask) {
		this.networkCommsTask = networkCommsTask;
	}

	public PatientAssessmentDao getPatientAssessmentDao() {
		return patientAssessmentDao;
	}

	public void setPatientAssessmentDao(PatientAssessmentDao patientAssessmentDao) {
		this.patientAssessmentDao = patientAssessmentDao;
	}

	public Button getSyncButton() {
		return syncButton;
	}

	public void setSyncButton(Button syncButton) {
		this.syncButton = syncButton;
	}
	
} // end of SyncActivity class