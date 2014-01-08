package ie.ucc.bis.supportinglife.rule.engine;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.domain.Patient;
import ie.ucc.bis.supportinglife.rule.engine.enums.CcmClassificationType;
import ie.ucc.bis.supportinglife.rule.engine.enums.CriteriaRule;
import ie.ucc.bis.supportinglife.rule.engine.enums.ImciClassificationType;
import ie.ucc.bis.supportinglife.rule.engine.enums.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;


/**
 * Class: TreatmentRuleEngine
 * 
 * Responsible for parsing treatment IMCI and CCM XML-based rules and
 * determining appropriate treatments for patient.
 * 
 * @author TOSullivan
 *
 */

public class TreatmentRuleEngine {

	private static final String TREATMENT_RULE = "TreatmentRule";
	private static final String CLASSIFICATION_NAME = "Classification";
	private static final String TREATMENT_HEADER = "TreatmentHeader";
	private static final String TREATMENT_FOOTER = "TreatmentFooter";
	private static final String TREATMENT = "Treatment";
	private static final String CRITERIA_LIST = "CriteriaList";
	private static final String TREATMENT_CRITERIA = "TreatmentCriteria";
	private static final String SYMPTOM_CRITERIA = "SymptomCriteria";
	private static final String RECOMMENDATION = "Recommendation";
	private static final String RULE_ATTRIB = "rule";
	private static final String VALUE_ATTRIB = "value";

	private static final String SEVERE_DEHYDRATION_CLASSIFICATION = "Severe Dehydration";

//	private static final String LOG_TAG = "ie.ucc.bis.rule.engine.TreatmentRuleEngine";

	private static ArrayList<TreatmentRule> systemImciTreatmentRules;
	private static ArrayList<TreatmentRule> systemCcmTreatmentRules;

	private boolean ccmRelatedTreatments;
	private boolean imciRelatedTreatments;

	/**
	 * Responsible for reading IMCI treatment rules from XML into memory
	 * 
	 * @param supportingLifeBaseActivity 
	 */
	public void readImciTreatmentRules(SupportingLifeBaseActivity supportingLifeBaseActivity) {
		XmlResourceParser xmlParser = supportingLifeBaseActivity.getResources().getXml(R.xml.imci_treatment_rules);
		setSystemImciTreatmentRules(new ArrayList<TreatmentRule>());
		parseTreatmentRules(supportingLifeBaseActivity, getSystemImciTreatmentRules(), xmlParser);
	}

	/**
	 * Responsible for reading CCM treatment rules from XML into memory
	 * 
	 * @param supportingLifeBaseActivity 
	 */
	public void readCcmTreatmentRules(SupportingLifeBaseActivity supportingLifeBaseActivity) {
		XmlResourceParser xmlParser = supportingLifeBaseActivity.getResources().getXml(R.xml.ccm_treatment_rules);
		setSystemCcmTreatmentRules(new ArrayList<TreatmentRule>());
		parseTreatmentRules(supportingLifeBaseActivity, getSystemCcmTreatmentRules(), xmlParser);
	}

	/**
	 * Responsible for determining IMCI patient treatments based on 
	 * assessment
	 * 
	 * @param supportingLifeBaseActivity 
	 * @param reviewItems
	 * @param classifications
	 * @param patient 
	 * 
	 */
	public void determineImciTreatments(SupportingLifeBaseActivity supportingLifeBaseActivity, List<ReviewItem> reviewItems, Patient patient) {
		setImciRelatedTreatments(true);
		setCcmRelatedTreatments(false);
		addImciTreatmentCriteriaToReviewItems(supportingLifeBaseActivity, reviewItems, patient.getDiagnostics());
		determinePatientTreatments(supportingLifeBaseActivity, reviewItems, patient, getSystemImciTreatmentRules());
	}

	/**
	 * Responsible for determining CCM patient treatments based on 
	 * assessment
	 * 
	 * @param supportingLifeBaseActivity 
	 * @param reviewItems
	 * @param classifications
	 * @param patient 
	 * 
	 */
	public void determineCcmTreatments(SupportingLifeBaseActivity supportingLifeBaseActivity, List<ReviewItem> reviewItems, Patient patient) {
		setImciRelatedTreatments(false);
		setCcmRelatedTreatments(true);
		addCcmTreatmentCriteriaToReviewItems(supportingLifeBaseActivity, reviewItems, patient.getDiagnostics());
		determinePatientTreatments(supportingLifeBaseActivity, reviewItems, patient, getSystemCcmTreatmentRules());
	}

	/**
	 * Responsible for adding IMCI-related treatment criteria items to the review item list
	 * (IMCI-only)
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patientDiagnostics
	 * 
	 */
	private void addImciTreatmentCriteriaToReviewItems(SupportingLifeBaseActivity supportingLifeBaseActivity,
			List<ReviewItem> reviewItems, List<Diagnostic> patientDiagnostics) {

		// assess whether patient has at least one severe classification
		severeImciClassificationTreatmentCriteriaCheck(supportingLifeBaseActivity, reviewItems, patientDiagnostics);

		// assess whether 'severe dehydration' is the only severe classification
		severeDehydrationTreatmentCriteriaCheck(supportingLifeBaseActivity, reviewItems, patientDiagnostics);
	}
	
	/**
	 * Responsible for adding CCM-related treatment criteria items to the review item list
	 * (CCM-only)
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patientDiagnostics
	 * 
	 */
	private void addCcmTreatmentCriteriaToReviewItems(SupportingLifeBaseActivity supportingLifeBaseActivity,
			List<ReviewItem> reviewItems, List<Diagnostic> patientDiagnostics) {

		// assess whether patient has at least one 'danger sign' classification
		dangerSignCcmClassificationTreatmentCriteriaCheck(supportingLifeBaseActivity, reviewItems, patientDiagnostics);
		
		// assess whether patient has at least one 'refer sign' classification
		referSignCcmClassificationTreatmentCriteriaCheck(supportingLifeBaseActivity, reviewItems, patientDiagnostics);
	}

	/**
	 * Responsible for determining whether patient has one severe classification
	 * with respect to the patient assessment (IMCI-only) i.e.
	 * 
	 * 	<CriteriaList rule="all">
	 * 		<TreatmentCriteria value="yes">treatment_criteria_severe_classification_present</TreatmentCriteria>
	 * 	</CriteriaList>
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patientDiagnostics
	 * 
	 */
	private void severeImciClassificationTreatmentCriteriaCheck(SupportingLifeBaseActivity supportingLifeBaseActivity,
			List<ReviewItem> reviewItems, List<Diagnostic> patientDiagnostics) {

		boolean hasSevereClassification = false;

		for (Diagnostic diagnostic : patientDiagnostics) {
			if (diagnostic.getClassification().getType().equalsIgnoreCase(ImciClassificationType.SEVERE.name())) {
				hasSevereClassification = true;
				break;
			}
		}

		String symptomId = supportingLifeBaseActivity.getResources().getString(R.string.imci_treatment_criteria_severe_classification_present);
		ReviewItem severeClassificationPresentReviewItem = new ReviewItem(null, null, symptomId, null, -1);
		if (hasSevereClassification) {
			severeClassificationPresentReviewItem.setSymptomValue(Response.YES.name());
		}
		else {
			severeClassificationPresentReviewItem.setSymptomValue(Response.NO.name());
		}
		severeClassificationPresentReviewItem.setVisible(false);

		// add review item to list
		reviewItems.add(severeClassificationPresentReviewItem);		
	}

	/**
	 * Responsible for determining whether 'Severe Dehydration' treatment criteria
	 * applies in the case of the patient assessment i.e.
	 * 
	 * 	<CriteriaList rule="all">
	 * 		<TreatmentCriteria value="no">imci_treatment_criteria_severe_dehydration_is_only_severe_classification</TreatmentCriteria>
	 * 	</CriteriaList>
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patientDiagnostics
	 * 
	 */
	private void severeDehydrationTreatmentCriteriaCheck(SupportingLifeBaseActivity supportingLifeBaseActivity,
			List<ReviewItem> reviewItems, List<Diagnostic> patientDiagnostics) {

		// assess whether 'severe dehydration' is the only severe classification
		boolean onlySevereDehydration = true;
		for (Diagnostic diagnostic : patientDiagnostics) {
			if (diagnostic.getClassification().getType().equalsIgnoreCase(ImciClassificationType.SEVERE.name()) 
					&& !diagnostic.getClassification().getName().equalsIgnoreCase(SEVERE_DEHYDRATION_CLASSIFICATION)) {
				onlySevereDehydration = false;
				break;
			}
		}

		String symptomId = supportingLifeBaseActivity.getResources().getString(R.string.imci_treatment_criteria_severe_dehydration_is_only_severe_classification);
		ReviewItem severeDehydrationReviewItem = new ReviewItem(null, null, symptomId, null, -1);
		if (onlySevereDehydration) {
			severeDehydrationReviewItem.setSymptomValue(Response.YES.name());
		}
		else {
			severeDehydrationReviewItem.setSymptomValue(Response.NO.name());
		}
		severeDehydrationReviewItem.setVisible(false);

		// add review item to list
		reviewItems.add(severeDehydrationReviewItem);
	}
	
	/**
	 * Responsible for determining whether patient has one danger sign classification
	 * with respect to the patient assessment (CCM-only) i.e.
	 * 
	 * 	<CriteriaList rule="all">															<!-- Danger sign present -->
	 * 		<TreatmentCriteria value="yes">ccm_treatment_criteria_danger_sign_classification_present</TreatmentCriteria>
	 * 	</CriteriaList>
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patientDiagnostics
	 * 
	 */
	private void dangerSignCcmClassificationTreatmentCriteriaCheck(SupportingLifeBaseActivity supportingLifeBaseActivity,
			List<ReviewItem> reviewItems, List<Diagnostic> patientDiagnostics) {

		boolean hasDangerSignClassification = false;

		for (Diagnostic diagnostic : patientDiagnostics) {
			if (diagnostic.getClassification().getType().equalsIgnoreCase(CcmClassificationType.DANGER_SIGN.name())) {
				hasDangerSignClassification = true;
				break;
			}
		}

		String symptomId = supportingLifeBaseActivity.getResources().getString(R.string.ccm_treatment_criteria_danger_sign_classification_present);
		ReviewItem dangerSignClassificationPresentReviewItem = new ReviewItem(null, null, symptomId, null, -1);
		if (hasDangerSignClassification) {
			dangerSignClassificationPresentReviewItem.setSymptomValue(Response.YES.name());
		}
		else {
			dangerSignClassificationPresentReviewItem.setSymptomValue(Response.NO.name());
		}
		dangerSignClassificationPresentReviewItem.setVisible(false);

		// add review item to list
		reviewItems.add(dangerSignClassificationPresentReviewItem);		
	}

	/**
	 * Responsible for determining whether patient has one refer sign classification
	 * with respect to the patient assessment (CCM-only) i.e.
	 * 
	 * 	<CriteriaList rule="all">															<!-- Refer sign present -->
	 * 		<TreatmentCriteria value="yes">ccm_treatment_criteria_refer_sign_classification_present</TreatmentCriteria>
	 * 	</CriteriaList>
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patientDiagnostics
	 * 
	 */
	private void referSignCcmClassificationTreatmentCriteriaCheck(SupportingLifeBaseActivity supportingLifeBaseActivity,
			List<ReviewItem> reviewItems, List<Diagnostic> patientDiagnostics) {

		boolean hasReferSignClassification = false;

		for (Diagnostic diagnostic : patientDiagnostics) {
			if (diagnostic.getClassification().getType().equalsIgnoreCase(CcmClassificationType.REFER.name())) {
				hasReferSignClassification = true;
				break;
			}
		}

		String symptomId = supportingLifeBaseActivity.getResources().getString(R.string.ccm_treatment_criteria_refer_sign_classification_present);
		ReviewItem referSignClassificationPresentReviewItem = new ReviewItem(null, null, symptomId, null, -1);
		if (hasReferSignClassification) {
			referSignClassificationPresentReviewItem.setSymptomValue(Response.YES.name());
		}
		else {
			referSignClassificationPresentReviewItem.setSymptomValue(Response.NO.name());
		}
		referSignClassificationPresentReviewItem.setVisible(false);

		// add review item to list
		reviewItems.add(referSignClassificationPresentReviewItem);		
	}
	
	/**
	 * 
	 * Responsible for parsing xml-based treatment rules
	 * 
	 * @param supportingLifeBaseActivity 
	 * @param xmlParser 
	 * @param arrayList 
	 * 
	 */
	private void parseTreatmentRules(SupportingLifeBaseActivity supportingLifeBaseActivity, ArrayList<TreatmentRule> systemTreatments, XmlResourceParser xmlParser) {
		try {
			String elemName = null;
			TreatmentRule treatmentRule = null;
			Treatment treatment = null;
			TreatmentCriterion treatmentCriterion = null;
			TreatmentCriteria treatmentCriteria = null;
			String symptomId = null;
			String symptomName = null;
			String treatmentName = null;
			Symptom symptomCriteria = null;
			String ruleAttrib = null;

			int eventType = xmlParser.next();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					elemName = xmlParser.getName();
					if (TREATMENT_RULE.equalsIgnoreCase(elemName)) {
						// <TreatmentRule>
						treatmentRule = new TreatmentRule();
					}
					else if (CLASSIFICATION_NAME.equalsIgnoreCase(elemName)) {
						// <Classification>
						treatmentRule.setClassification(xmlParser.nextText());
					}
					else if (TREATMENT_HEADER.equalsIgnoreCase(elemName)) {
						// <TreatmentHeader>
						treatmentRule.setTreatmentHeader(Boolean.parseBoolean(xmlParser.nextText()));
					}
					else if (TREATMENT_FOOTER.equalsIgnoreCase(elemName)) {
						// <TreatmentFooter>
						treatmentRule.setTreatmentFooter(Boolean.parseBoolean(xmlParser.nextText()));
					}					
					else if (TREATMENT.equalsIgnoreCase(elemName)) {
						// <Treatment>
						treatment = new Treatment();
					}
					else if (CRITERIA_LIST.equalsIgnoreCase(elemName)) {
						// <CriteriaList>
						ruleAttrib = xmlParser.getAttributeValue(null, RULE_ATTRIB);
						treatmentCriterion = new TreatmentCriterion(ruleAttrib);
						treatment.getTreatmentCriterion().add(treatmentCriterion);
					}
					else if (SYMPTOM_CRITERIA.equalsIgnoreCase(elemName)) {
						// <SymptomCriteria>
						ruleAttrib = xmlParser.getAttributeValue(null, VALUE_ATTRIB);

						symptomId = xmlParser.nextText();
						int identifier = supportingLifeBaseActivity.getResources().getIdentifier(symptomId, "string", "ie.ucc.bis.supportinglife");
						symptomName = supportingLifeBaseActivity.getResources().getString(identifier);			

						symptomCriteria = new Symptom(symptomName, ruleAttrib);
						treatment.getTreatmentCriterion().get(treatment.getTreatmentCriterion().size() - 1).getSymptomCriteria().add(symptomCriteria);
					}
					else if (TREATMENT_CRITERIA.equalsIgnoreCase(elemName)) {
						// <TreatmentCriteria>
						ruleAttrib = xmlParser.getAttributeValue(null, VALUE_ATTRIB);

						treatmentName = xmlParser.nextText();
						treatmentCriteria = new TreatmentCriteria(treatmentName, ruleAttrib);
						treatment.getTreatmentCriterion().get(treatment.getTreatmentCriterion().size() - 1).getTreatmentCriteria().add(treatmentCriteria);
					}
					else if (RECOMMENDATION.equalsIgnoreCase(elemName)) {
						// <Recommendation>
						treatment.setRecommendation(xmlParser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if (TREATMENT.equalsIgnoreCase(xmlParser.getName())) {
						// </Treatment>
						treatmentRule.getTreatments().add(treatment);
					}
					else if(TREATMENT_RULE.equalsIgnoreCase(xmlParser.getName())) {
						// </TreatmentRule>
						systemTreatments.add(treatmentRule);
					}
					break;
				} // end of switch				
				eventType = xmlParser.next();
			} // end of while			
		} catch (XmlPullParserException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		/**********************************
		 * Debug Assistance: Capture System Treatment Rules in Log Output
		 **********************************/
//		LoggerUtils.i(LOG_TAG, captureTreatmentRulesDebugOutput(systemTreatments));
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
	}

	/**
	 * Responsible for determining patient treatments
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patient 
	 * @param systemTreatments 
	 * 
	 */
	private void determinePatientTreatments(SupportingLifeBaseActivity supportingLifeBaseActivity, 
			List<ReviewItem> reviewItems, Patient patient, ArrayList<TreatmentRule> systemTreatments) {
		
		// iterate over all patient classifications and assign 
		// appropriate treatment(s)
		for (Diagnostic diagnostic : patient.getDiagnostics()) {
			for (TreatmentRule treatmentRule : systemTreatments) {
				if (diagnostic.getClassification().getName().equalsIgnoreCase(treatmentRule.getClassification())) {				
					// classification match found so determine if all associated 
					// treatments apply
					for (Treatment treatment : treatmentRule.getTreatments()) {
						determineTreatmentRecommendations(reviewItems, patient, diagnostic, treatment);					
					} // end of for (Treatment treatment  ....
				}
			} // end of for (TreatmentRule treatmentRule ...
		} // end of for (Diagnostic diagnostic ...

		// if dealing with a CCM patient, we need to determine if 'Classification Type' treatment
		// recommendations apply
		if (isCcmRelatedTreatments()) {
			checkCcmClassificationTypeTreatments(reviewItems, patient, systemTreatments);
		}
		
		/**********************************
		 * Debug Assistance: Capture Patient Treatments in Log Output
		 **********************************/
		// LoggerUtils.i(LOG_TAG, captureTreatmentsDebugOutput(patient.getDiagnostics()));
	}

	/**
	 * Responsible for determining if CCM-related Classification Type treatments apply
	 * to this patient e.g.
	 * 
	 * <TreatmentRule>
	 * 	<Classification>DANGER SIGN</Classification>
	 *  <ClassificationType>DANGER_SIGN</ClassificationType>
	 *  <Treatment>
	 *  	<Recommendation>REFER URGENTLY to health facility</Recommendation>
	 *  </Treatment>
	 *  <Treatment>
	 *  	<Recommendation>Explain why child needs to go to health facility</Recommendation>
	 *  </Treatment>
	 *  <Treatment>
	 *  	<CriteriaList rule="none">			<!-- Not able to drink or feed anything != Yes -->
	 *  		<SymptomCriteria value="yes">ccm_ask_initial_assessment_unable_to_drink_or_feed_symptom_id</SymptomCriteria>	
	 *  	</CriteriaList>
	 *  	<Recommendation>Advise to give fluids and continue feeding</Recommendation>
	 *  </Treatment>
	 *  </TreatmentRule>
	 * 
	 * @param reviewItems
	 * @param patient
	 * @param systemTreatments
	 */
	private void checkCcmClassificationTypeTreatments(List<ReviewItem> reviewItems, Patient patient,
			ArrayList<TreatmentRule> systemTreatments) {
		
		boolean dangerSignClassificationExists = false;
		boolean sickSignClassificationExists = false;
		boolean referSignClassificationExists = false;
		String classificationName = null;
		String classificationType = null;
		
		// check if a refer sign classification exists for this patient
		referSignClassificationExists = checkClassificationTypeExistence(patient, CcmClassificationType.REFER.name());
	
		// if no refer sign classification found, then check for
		// danger sign classification
		if (!referSignClassificationExists) {
			dangerSignClassificationExists = checkClassificationTypeExistence(patient, CcmClassificationType.DANGER_SIGN.name());
		}
		
		// if no refer sign or danger sign classification found, then check for
		// sick sign classification
		if ((!referSignClassificationExists) && (!dangerSignClassificationExists)) {
			sickSignClassificationExists = checkClassificationTypeExistence(patient, CcmClassificationType.SICK.name());
		}
		

		if (referSignClassificationExists) {
				// fetch all 'standard' treatments to be applied when a classification(s) of type 'Refer'
				// is present in the patient's diagnostic assessment
				classificationName = CcmClassificationType.REFER.name();
				classificationType = CcmClassificationType.REFER.name();
				addTreatmentsWithMatchingName(reviewItems, patient, systemTreatments, classificationName, classificationType);
		}
		else if (dangerSignClassificationExists) {
			// fetch all 'standard' treatments to be applied when a classification(s) of type 'Danger Sign'
			// is present in the patient's diagnostic assessment
			classificationName = CcmClassificationType.DANGER_SIGN.name();
			classificationType = CcmClassificationType.DANGER_SIGN.name();
			addTreatmentsWithMatchingName(reviewItems, patient, systemTreatments, classificationName, classificationType);
		}
		else if (sickSignClassificationExists) {
			// fetch all 'standard' treatments to be applied when a classification(s) of type 'Sick'
			// is present in the patient's diagnostic assessment
			classificationName = CcmClassificationType.SICK.name();
			classificationType = CcmClassificationType.SICK.name();
			addTreatmentsWithMatchingName(reviewItems, patient, systemTreatments, classificationName, classificationType);
		}
	}

	/**
	 * Utility method for to check for the existence of a Classification
	 * Type within the calculated Patient Classifications
	 * 
	 * @param patient
	 * @param classificationType
	 * 
	 */
	private boolean checkClassificationTypeExistence(Patient patient, String classificationType) {
		boolean classificationTypeExists = false;
		
		for (Diagnostic diagnostic : patient.getDiagnostics()) {
			if (diagnostic.getClassification().getType().equalsIgnoreCase(classificationType)) {
				classificationTypeExists = true;
			}
		}
		return classificationTypeExists;
	}

	/**
	 * Utility method for adding all treatments to a patient diagnostic 
	 * record that have the same classification name as the classification name
	 * passed to the method
	 * 
	 * @param reviewItems
	 * @param patient
	 * @param systemTreatments
	 * @param classificationName
	 * @param classificationType
	 * 
	 */
	private void addTreatmentsWithMatchingName(List<ReviewItem> reviewItems, Patient patient, ArrayList<TreatmentRule> systemTreatments, String classificationName, String classificationType) {
		
		for (TreatmentRule treatmentRule : systemTreatments) {
			if (classificationName.equalsIgnoreCase(treatmentRule.getClassification())) {
				Diagnostic patientDiagnostic = new Diagnostic(new Classification(classificationName, classificationType));
				
				// configure treatment recommendation with associated
				// header or footer flag, if applicable
				patientDiagnostic.setTreatmentHeader(treatmentRule.isTreatmentHeader());
				patientDiagnostic.setTreatmentFooter(treatmentRule.isTreatmentFooter());
				
				// classification match found so determine if all associated 
				// treatments apply
				for (Treatment treatment : treatmentRule.getTreatments()) {
					determineTreatmentRecommendations(reviewItems, patient, patientDiagnostic, treatment);					
				} // end of for (Treatment treatment  ....			
				patient.getDiagnostics().add(patientDiagnostic);
			}
		} // end of for (TreatmentRule treatmentRule ...
		
	}

	/**
	 * Responsible for determining the treatment recommendations related to
	 * a single treatment
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patient 
	 * @param systemTreatments 
	 * 
	 */
	private void determineTreatmentRecommendations(List<ReviewItem> reviewItems, Patient patient,
			Diagnostic diagnostic, Treatment treatment) {
		
		boolean symptomCriteriaPasses = true;
		boolean treatmentCriteriaPasses = true;

		// check symptom criteria
		for (TreatmentCriterion treatmentCriterion : treatment.getTreatmentCriterion()) {
			List<Symptom> symptomCriterion = treatmentCriterion.getSymptomCriteria();
			if (symptomCriterion.size() != 0) {
				if (CriteriaRule.ALL.name().equalsIgnoreCase(treatmentCriterion.getRule())) {
					symptomCriteriaPasses = checkSymptomCriteria(symptomCriterion, reviewItems, patient, symptomCriterion.size());
				}
				else if (CriteriaRule.ANY.name().equalsIgnoreCase(treatmentCriterion.getRule())) {
					// only need a single symptom in order for the treatment to apply
					symptomCriteriaPasses = checkSymptomCriteria(symptomCriterion, reviewItems, patient, 1);
				}
				else if (CriteriaRule.NONE.name().equalsIgnoreCase(treatmentCriterion.getRule())) {
					// none of the symptom criteria should apply in order for the treatment to apply
					symptomCriteriaPasses = checkSymptomCriteria(symptomCriterion, reviewItems, patient, 0);
				}
			}
			else {
				symptomCriteriaPasses = true;
			}
			if (!symptomCriteriaPasses) {
				// exit loop if any <CriteriaList> does not pass successfully
				break;
			}
		} // end of for (Treatment treatment ...

		// check treatment criteria
		for (TreatmentCriterion treatmentCriterion : treatment.getTreatmentCriterion()) {
			List<TreatmentCriteria> treatmentCriteria = treatmentCriterion.getTreatmentCriteria();
			if (symptomCriteriaPasses && (treatmentCriteria.size() != 0)) {
				if (CriteriaRule.ALL.name().equalsIgnoreCase(treatmentCriterion.getRule())) {
					treatmentCriteriaPasses = checkTreatmentCriteria(treatmentCriteria, reviewItems, patient, treatmentCriteria.size());
				}
			}
			else {
				treatmentCriteriaPasses = true;
			}
			if (!treatmentCriteriaPasses) {
				// exit loop if any <CriteriaList> does not pass successfully
				break;
			}
		}

		if (symptomCriteriaPasses && treatmentCriteriaPasses) {
			// add the recommended treatment to patient classification
			diagnostic.getTreatmentRecommendations().add(treatment.getRecommendation());
		}
	}

	/**
	 * Determine whether the symptom criteria related to a recommended treatment is met
	 * according to the patient's details
	 * 
	 * @param symptomCriteria
	 * @param reviewItems
	 * @param patient
	 * @param criteriaRequired - number of symptom criteria required to pass 
	 * 
	 * @return boolean
	 */
	private boolean checkSymptomCriteria(List<Symptom> symptomCriterion, List<ReviewItem> reviewItems, Patient patient, int criteriaRequired) {
		int symptomCriteriaCounter = 0;
		boolean symptomCriteriaPasses = false;

		SYMPTOM_CRITERIA_CHECK:
			for (Symptom symptomCriteria : symptomCriterion) {
				for (ReviewItem reviewItem : reviewItems) {
					if (reviewItem.getSymptomValue() != null){
						// symptom id match?
						if (symptomCriteria.getIdentifier().equalsIgnoreCase(reviewItem.getSymptomId())) {
							// symptom value match?
							if (symptomCriteria.getValue().equalsIgnoreCase(reviewItem.getSymptomValue())) {
								symptomCriteriaCounter++;
								if (symptomCriteriaCounter == criteriaRequired) {
									symptomCriteriaPasses = true;
									break SYMPTOM_CRITERIA_CHECK;
								}
							} // end of if (symptom.getValue().equalsIgnoreCase(... 
						} // end of if (symptom.getIdentifier().equalsIgnoreCase...
					} // end of if (reviewItem.getSymptomValue() ....
				} // end of for (ReviewItem reviewItem ...
			} // end of for (Symptom symptomCriteria ...

		// This check is required when we're dealing with the 'NONE' Criteria Rule case
		// i.e. none of the symptoms should have applied in order for the 'NONE' criteria
		//		rule to have been deemed successful
		if (symptomCriteriaCounter == criteriaRequired) {
			symptomCriteriaPasses = true;
		}
		return symptomCriteriaPasses;
	}

	/**
	 * Determine whether the treatment criteria related to a recommended treatment is met
	 * according to the patient's details
	 * 
	 * @param treatmentCriterion
	 * @param reviewItems
	 * @param patient
	 * @param treatmentCriteriaRequired - number of treatment criteria required to pass 
	 * 
	 * @return boolean
	 */
	private boolean checkTreatmentCriteria(List<TreatmentCriteria> treatmentCriterion, List<ReviewItem> reviewItems, Patient patient, int treatmentCriteriaRequired) {
		int treatmentCriteriaCounter = 0;
		boolean treatmentCriteriaPasses = false;

		TREATMENT_CRITERIA_CHECK:
			for (TreatmentCriteria treatmentCriteria : treatmentCriterion) {
				for (ReviewItem reviewItem : reviewItems) {
					if (reviewItem.getSymptomValue() != null){
						// symptom id match?
						if (treatmentCriteria.getIdentifier().equalsIgnoreCase(reviewItem.getSymptomId())) {
							// symptom value match?
							if (treatmentCriteria.getValue().equalsIgnoreCase(reviewItem.getSymptomValue())) {
								treatmentCriteriaCounter++;
								if (treatmentCriteriaCounter == treatmentCriteriaRequired) {
									treatmentCriteriaPasses = true;
									break TREATMENT_CRITERIA_CHECK;
								}
							} // end of if (treatmentCriteria.getValue().equalsIgnoreCase(... 
						} // end of if (treatmentCriteria.getIdentifier().equalsIgnoreCase...
					} // end of if (reviewItem.getSymptomValue() ....
				} // end of for (ReviewItem reviewItem ...
			} // end of for (TreatmentCriteria symptomCriteria ...
		return treatmentCriteriaPasses;
	}

	/**
	 * 
	 * Provides debug output of all treatments incorporated within the
	 * diagnostic elements passed to the method
	 * 
	 * @param diagnostics
	 * @return StringBuilder
	 * 
	 */
	protected StringBuilder captureTreatmentsDebugOutput(List<Diagnostic> diagnostics) {
		StringBuilder debugOutput = new StringBuilder();

		for (Diagnostic diagnostic : diagnostics) {
			debugOutput.append(diagnostic.getClassification().getName() != null ? diagnostic.getClassification().getName() : "" + "\n");
			for (String recommendedTreatment : diagnostic.getTreatmentRecommendations()) {
				debugOutput.append(recommendedTreatment + "\n");
			}
		}

		return debugOutput;
	}

	/**
	 * 
	 * Provides debug output of all treatments rules passed to
	 * the method
	 * 
	 * @param treatmentRules
	 * @return StringBuilder
	 * 
	 */
	protected StringBuilder captureTreatmentRulesDebugOutput(List<TreatmentRule> treatmentRules) {
		StringBuilder debugOutput = new StringBuilder();

		for (TreatmentRule treatmentRule : treatmentRules){
			debugOutput.append(treatmentRule.debugOutput());
		}

		return debugOutput;
	}	


	/**
	 * Getter Method: getSystemImciTreatmentRules()
	 */	
	public static ArrayList<TreatmentRule> getSystemImciTreatmentRules() {
		return systemImciTreatmentRules;
	}

	/**
	 * Setter Method: setSystemImciTreatmentRules()
	 */
	public static void setSystemImciTreatmentRules(ArrayList<TreatmentRule> systemImciTreatmentRules) {
		TreatmentRuleEngine.systemImciTreatmentRules = systemImciTreatmentRules;
	}

	/**
	 * Getter Method: getSystemCcmTreatmentRules()
	 */	
	public static ArrayList<TreatmentRule> getSystemCcmTreatmentRules() {
		return systemCcmTreatmentRules;
	}

	/**
	 * Setter Method: setSystemCcmTreatmentRules()
	 */
	public static void setSystemCcmTreatmentRules(ArrayList<TreatmentRule> systemCcmTreatmentRules) {
		TreatmentRuleEngine.systemCcmTreatmentRules = systemCcmTreatmentRules;
	}

	/**
	 * Getter Method: isCcmRelatedTreatments()
	 */	
	public boolean isCcmRelatedTreatments() {
		return ccmRelatedTreatments;
	}

	/**
	 * Setter Method: setCcmRelatedTreatments()
	 */
	public void setCcmRelatedTreatments(boolean ccmRelatedTreatments) {
		this.ccmRelatedTreatments = ccmRelatedTreatments;
	}

	/**
	 * Getter Method: isImciRelatedTreatments()
	 */	
	public boolean isImciRelatedTreatments() {
		return imciRelatedTreatments;
	}

	/**
	 * Setter Method: setImciRelatedTreatments()
	 */
	public void setImciRelatedTreatments(boolean imciRelatedTreatments) {
		this.imciRelatedTreatments = imciRelatedTreatments;
	}
}
