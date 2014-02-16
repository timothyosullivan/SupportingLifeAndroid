package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.ccm.ui.CcmAssessmentClassificationsFragment;
import ie.ucc.bis.supportinglife.assessment.ccm.ui.CcmAssessmentTreatmentsFragment;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.assessment.ui.AssessmentResultsReviewFragment;
import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.rule.engine.ClassificationRuleEngine;
import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;
import ie.ucc.bis.supportinglife.rule.engine.TreatmentRecommendation;
import ie.ucc.bis.supportinglife.rule.engine.TreatmentRuleEngine;

import java.util.ArrayList;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.BaseAdapter;

/**
 * Class: CcmAssessmentResultsActivity
 * 
 * Responsible for displaying CCM assessment results.
 * 
 * The results shown comprise of the following:
 * 
 * 1. Assessment Review Items Tab
 * 2. Classifications Tab
 * 3. Recommended Treatments Tab
 * 4. Vaccine Tab
 * 5. Follow-up Tab
 * 
 * @author TOSullivan
 *
 */
public class CcmAssessmentResultsActivity extends AssessmentResultsActivity {

	
	private NetworkCommunicationAsyncTask task;
	
	/* 
	 * Method: onCreate() 
	 * 
	 * Perform initialisation of all fragments and loaders.
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assessment_results);
        setTitleFromActivityLabel(R.id.action_bar_title_text);
        setViewPager((ViewPager) findViewById(R.id.assessment_results_pager));
        
        // extract the assessment page data sent by the assessment bread-crumb wizard
		Intent intent = getIntent();
        setReviewItems((ArrayList<ReviewItem>) intent.getSerializableExtra(CcmAssessmentActivity.ASSESSMENT_REVIEW_ITEMS));
        
        // capture the patient assessment data
        // entered by the user and construct
        // the patient instance
        contructPatientInstance();
        
        // resolve CCM classifications based on assessed symptoms        
        setClassificationRuleEngine(new ClassificationRuleEngine());
        getClassificationRuleEngine().readCcmClassificationRules((SupportingLifeBaseActivity) this);
        getClassificationRuleEngine().determinePatientClassifications(this, getReviewItems(), getPatient(), getClassificationRuleEngine().getSystemCcmClassifications());
        
        // identify CCM treatments
        setTreatmentRuleEngine(new TreatmentRuleEngine());
        getTreatmentRuleEngine().readCcmTreatmentRules((SupportingLifeBaseActivity) this);
        getTreatmentRuleEngine().determineCcmTreatments(this, getReviewItems(), getPatient());
 
        // record the patient visit in the DB
        recordPatientVisit();
        
        // transmit the patient visit
        // START
        	// instigate network communication to transmit patient record
			task = new NetworkCommunicationAsyncTask();
			task.execute(getPatient());
        // END
        
        // create a new Action bar and set title to strings.xml
        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // attach the Tabs to the fragment classes and set the tab title.
        setTabsAdapter(new TabsAdapter(this, getViewPager()));
        
        // add assessment review items tab
        getTabsAdapter().addTab(bar.newTab().setText(R.string.ccm_assessment_results_review_tab_title),
        		AssessmentResultsReviewFragment.class, null);
       
        // add classifications tab
        getTabsAdapter().addTab(bar.newTab().setText(R.string.ccm_assessment_results_classifications_tab_title),
        		CcmAssessmentClassificationsFragment.class, null);
        
        // add treatments tab
        getTabsAdapter().addTab(bar.newTab().setText(R.string.ccm_assessment_results_treatments_tab_title),
        		CcmAssessmentTreatmentsFragment.class, null);
 
        // open on classifications tab by default
        getTabsAdapter().setDefaultTab();

       if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
	}

	/**
	 * Display the treatments tab and scroll to the
	 * relevant classification if applicable, otherwise flash header 
	 * 
	 * @param classificationName 
	 */
	public void displayTreatmentTab(String classificationTitle) {
		getTabsAdapter().displayTreatmentTab();
		CcmAssessmentTreatmentsFragment treatmentsFragment = (CcmAssessmentTreatmentsFragment) 
				getSupportFragmentManager().getFragments().get(TabsAdapter.TREATMENT_TAB_INDEX);

		if (treatmentsFragment != null) {
			// refresh adapter data set - gets view redrawn
			((BaseAdapter) treatmentsFragment.getCcmTreatmentAdapter()).notifyDataSetChanged();
		}
	}
	
	private class NetworkCommunicationAsyncTask extends AsyncTask<PatientAssessment, Void, Long> {

		private static final String AMAZON_WEB_SERVICE_URL = "http://supportinglife.elasticbeanstalk.com/patientvisits/add";
		
		@Override
		protected Long doInBackground(PatientAssessment... params) {

			RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			
			PatientAssessment patient = (PatientAssessment) params[0];
			
			//********** TEMP START
			PatientAssessmentComms patientTransmit = new PatientAssessmentComms(patient.getHsaUserId(), patient.getNationalId(), patient.getNationalHealthId(), 
					patient.getChildFirstName(), patient.getChildSurname(), patient.getBirthDate(),
					patient.getGender(), patient.getCaregiverName(), patient.getRelationship(), patient.getPhysicalAddress(),
					patient.getVillageTa(), patient.getVisitDate(), patient.isChestIndrawing(), patient.getBreathsPerMinute(),
					patient.isSleepyUnconscious(), patient.isPalmarPallor(), patient.getMuacTapeColour(), 
					patient.isSwellingBothFeet(), patient.getProblem(), patient.isCough(), patient.getCoughDuration(),
					patient.isDiarrhoea(), patient.getDiarrhoeaDuration(), patient.isBloodInStool(), patient.isFever(),
					patient.getFeverDuration(), patient.isConvulsions(), patient.isDifficultyDrinkingOrFeeding(),
					patient.isUnableToDrinkOrFeed(), patient.isVomiting(), patient.isVomitsEverything(),
					patient.isRedEye(), patient.getRedEyeDuration(), patient.isDifficultySeeing(),
					patient.getDifficultySeeingDuration(), patient.isCannotTreatProblem(), 
					patient.getCannotTreatProblemDetails());
			
			for (Diagnostic diagnostic : patient.getDiagnostics()) {
				// extract classifications
				if (diagnostic.getClassification().getCategory() != null) {
					patientTransmit.getClassifications().put(diagnostic.getClassification().getCategory(), diagnostic.getClassification().getName());
				}
				
				// extract treatments
				for (TreatmentRecommendation treatmentRecommendation : diagnostic.getTreatmentRecommendations()) {
					patientTransmit.getTreatments().put(treatmentRecommendation.getTreatmentIdentifier(), treatmentRecommendation.getTreatmentDescription());
				}
				
			}
			
			
			//********** TEMP END
			
			
			try {
				// The default timeout was resulting in the call to the 'restTemplate.postForObject(..)' method
				// call sometimes returning a null object and sometimes returning a correctly populated object.
				// Doubling the read timeout led to more reliability in obtaining a correctly populated object.
				// default timeout is 60 * 1000
				((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(120 * 1000);
				((HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(120 * 1000);
				restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
				
				Long patientId = restTemplate.postForObject(AMAZON_WEB_SERVICE_URL, patientTransmit, Long.class);
				return patientId;
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
		protected void onPostExecute(Long slPatientId) {
			if (slPatientId != null) {
				System.out.println("TEST PATIENT DETAILS");
				System.out.println(slPatientId.longValue());
			}
			else {
				System.out.println("COMMUNICATION ERROR!");
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

