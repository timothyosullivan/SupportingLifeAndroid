package ie.ucc.bis.supportinglife.helper;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.model.listener.DateDialogSetListener;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.domain.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.res.Resources;

public class PatientHandlerUtils {

	private static final String POSITIVE_ANSWER = "Yes";
	
	/**
	 * Handler method to populate a patient instance with the following information:
	 * 
	 * - General Patient Details (user entered)
	 * - Look Symptoms (user entered)
	 * - Ask & Look Symptoms (user entered)
	 * - Classifications (system generated)
	 * - Treatments (system generated)
	 * 
	 * @param resources
	 * @param reviewItems
	 * 
	 * @return Patient
	 * @throws ParseException 
	 */
	public Patient populateCcmPatientDetails(Resources resources, List<ReviewItem> reviewItems) throws ParseException {
		Patient patient = new Patient();

		// firstly create hash map of review items for quick look-up and
		// subsequent population of patient instance
		Map<String, String> reviewItemMap = new HashMap<String, String>();
		for (ReviewItem reviewItem : reviewItems) {
			reviewItemMap.put(reviewItem.getTitle(), reviewItem.getDisplayValue());
		}

		retrieveGeneralPatientDetails(patient, resources, reviewItemMap);
		retrieveLookSymptoms(patient, resources, reviewItemMap);

		return patient;
	}

	/**
	 * Method to populate a patient instance with the following:
	 * 
	 * - General Patient Details (user entered)
	 * 
	 * @param patient
	 * @param resources
	 * @param reviewItems
	 * 
	 * @throws ParseException 
	 */
	private void retrieveGeneralPatientDetails(Patient patient, Resources resources, Map<String, String> reviewItemMap) throws ParseException {

		// TODO NationalId and NationalHealthId
		
		// hsa user id
		patient.setHsaUserId(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_hsa_identifier)));

		// child first name
		patient.setChildFirstName(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_first_name)));

		// child surname
		patient.setChildSurname(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_surname)));

		// birthdate
		patient.setBirthDate(assessDatePatientSymptom(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_date_of_birth))));

		// gender
		patient.setGender(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_gender)));

		// caregiver name
		patient.setCaregiverName(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_caregiver)));

		// relationship
		patient.setRelationship(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_relationship)));

		// physical address
		patient.setPhysicalAddress(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_physical_address)));

		// village TA
		patient.setVillageTa(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_village)));

		// visit date
		patient.setVisitDate(assessDatePatientSymptom(reviewItemMap.get(resources.getString(R.string.ccm_general_patient_details_review_today_date))));
	}

	/**
	 * Method to populate a patient instance with the following:
	 * 
	 * - Look Symptoms (user entered)
	 * 
	 * @param patient
	 * @param resources
	 * @param reviewItems
	 * 
	 * @throws ParseException 
	 */
	private void retrieveLookSymptoms(Patient patient, Resources resources, Map<String, String> reviewItemMap) throws ParseException {

		// chest indrawing
		patient.setChestIndrawing(assessBooleanPatientSymptom(reviewItemMap.get(resources.getString(R.string.ccm_look_assessment_review_chest_indrawing))));

		// breaths per minute
		patient.setBreathsPerMinute(assessIntegerPatientSymptom(reviewItemMap.get(resources.getString(R.string.ccm_look_assessment_review_breaths_per_minute))));

		// sleepy unconscious
		patient.setSleepyUnconscious(assessBooleanPatientSymptom(reviewItemMap.get(resources.getString(R.string.ccm_look_assessment_review_very_sleepy_or_unconscious))));

		// palmar pallor
		patient.setPalmarPallor(assessBooleanPatientSymptom(reviewItemMap.get(resources.getString(R.string.ccm_look_assessment_review_palmar_pallor))));

		// muac tape colour
		patient.setMuacTapeColour(reviewItemMap.get(resources.getString(R.string.ccm_look_assessment_review_muac_tape_colour)));

		// swelling of both feet
		patient.setSwellingBothFeet(assessBooleanPatientSymptom(reviewItemMap.get(resources.getString(R.string.ccm_look_assessment_review_swelling_of_both_feet))));
	}

	/**
	 * Assess whether boolean symptom review item is true or false
	 * 
	 * @param string
	 * 
	 * @return boolean
	 */
	private boolean assessBooleanPatientSymptom(String symptomValue) {
		if (symptomValue != null && symptomValue.equalsIgnoreCase(POSITIVE_ANSWER)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Assess whether date symptom review item is null and 
	 * also correctly format returned date
	 * 
	 * @param string
	 * 
	 * @return Date
	 * @throws ParseException 
	 */
	private Date assessDatePatientSymptom(String dateValue) throws ParseException {
		if (dateValue != null) {
			Date dateInstance = new SimpleDateFormat(DateDialogSetListener.DATE_TIME_CUSTOM_FORMAT, DateDialogSetListener.LOCALE)
										.parse(dateValue);
			return dateInstance;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Assess whether a symptom review item is null and 
	 * if not returns return integer related value
	 * 
	 * @param integerValue
	 * 
	 * @return Integer
	 */

	private Integer assessIntegerPatientSymptom(String integerValue) {
		if (integerValue != null) {
			return Integer.valueOf(integerValue);
		}
		else {
			return null;
		}
	}
	
}