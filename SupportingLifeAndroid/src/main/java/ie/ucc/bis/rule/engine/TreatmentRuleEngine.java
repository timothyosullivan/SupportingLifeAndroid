package ie.ucc.bis.rule.engine;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.rule.engine.enums.ClassificationType;
import ie.ucc.bis.rule.engine.enums.CriteriaRule;
import ie.ucc.bis.rule.engine.enums.Response;
import ie.ucc.bis.ui.utilities.LoggerUtils;
import ie.ucc.bis.wizard.model.review.ReviewItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;


/**
 * Class: TreatmentRuleEngine
 * 
 * Responsible for parsing treatment xml rules and
 * determining appropriate treatments for patient.
 * 
 * @author TOSullivan
 *
 */

public class TreatmentRuleEngine {

	private static final String TREATMENT_RULE = "TreatmentRule";
	private static final String CLASSIFICATION_CATEGORY = "Category";
	private static final String CLASSIFICATION_NAME = "Classification";
	private static final String TREATMENT = "Treatment";
	private static final String CRITERIA_LIST = "CriteriaList";
	private static final String TREATMENT_CRITERIA = "TreatmentCriteria";
	private static final String SYMPTOM_CRITERIA = "SymptomCriteria";
	private static final String RECOMMENDATION = "Recommendation";
	private static final String RULE_ATTRIB = "rule";
	private static final String VALUE_ATTRIB = "value";

	private static final String SEVERE_DEHYDRATION_CLASSIFICATION = "Severe Dehydration";
	
	private static final String LOG_TAG = "ie.ucc.bis.rule.engine.TreatmentRuleEngine";
	
	private static ArrayList<TreatmentRule> systemTreatmentRules;
	
	/**
	 * Responsible for determining patient treatments based on 
	 * assessment
	 * 
	 * @param supportingLifeBaseActivity 
	 * @param reviewItems
	 * @param classifications
	 * @param patient 
	 * 
	 */
	public void determineTreatments(SupportingLifeBaseActivity supportingLifeBaseActivity, List<ReviewItem> reviewItems, 
			List<Classification> classifications, Patient patient) {

		setSystemTreatmentRules(new ArrayList<TreatmentRule>());
		parseTreatmentRules(supportingLifeBaseActivity);
		addTreatmentCriteriaToReviewItems(supportingLifeBaseActivity, reviewItems, patient.getDiagnostics());
		determinePatientTreatments(supportingLifeBaseActivity, reviewItems, patient);
	}

	/**
	 * Responsible for adding treatment criteria items to the review item list
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patientDiagnostics
	 * 
	 */
	private void addTreatmentCriteriaToReviewItems(SupportingLifeBaseActivity supportingLifeBaseActivity,
			List<ReviewItem> reviewItems, List<Diagnostic> patientDiagnostics) {
		
		// assess whether patient has at least one severe classification
		severeClassificationTreatmentCriteriaCheck(supportingLifeBaseActivity, reviewItems, patientDiagnostics);
		
		// assess whether 'severe dehydration' is the only severe classification
		severeDehydrationTreatmentCriteriaCheck(supportingLifeBaseActivity, reviewItems, patientDiagnostics);
	}

	/**
	 * Responsible for determining whether patient has one severe classification
	 * with respect to the patient assessment i.e.
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
	private void severeClassificationTreatmentCriteriaCheck(SupportingLifeBaseActivity supportingLifeBaseActivity,
			List<ReviewItem> reviewItems, List<Diagnostic> patientDiagnostics) {

		boolean hasSevereClassification = false;
		
		for (Diagnostic diagnostic : patientDiagnostics) {
			if (diagnostic.getClassification().getType().equalsIgnoreCase(ClassificationType.SEVERE.name())) {
				hasSevereClassification = true;
				break;
			}
		}
		
		String symptomId = supportingLifeBaseActivity.getResources().getString(R.string.treatment_criteria_severe_classification_present);
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
	 * 		<TreatmentCriteria value="no">treatment_criteria_severe_dehydration_is_only_severe_classification</TreatmentCriteria>
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
			if (diagnostic.getClassification().getType().equalsIgnoreCase(ClassificationType.SEVERE.name()) 
					&& !diagnostic.getClassification().getName().equalsIgnoreCase(SEVERE_DEHYDRATION_CLASSIFICATION)) {
				onlySevereDehydration = false;
				break;
			}
		}
		
		String symptomId = supportingLifeBaseActivity.getResources().getString(R.string.treatment_criteria_severe_dehydration_is_only_severe_classification);
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
	 * 
	 * Responsible for parsing xml-based treatment rules
	 * 
	 * @param supportingLifeBaseActivity 
	 * 
	 */
	private void parseTreatmentRules(SupportingLifeBaseActivity supportingLifeBaseActivity) {
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
			
			XmlResourceParser xmlParser = supportingLifeBaseActivity.getResources().getXml(R.xml.treatment_rules);
			
			int eventType = xmlParser.next();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
					case XmlPullParser.START_TAG:
						elemName = xmlParser.getName();
						if (TREATMENT_RULE.equalsIgnoreCase(elemName)) {
							// <TreatmentRule>
							treatmentRule = new TreatmentRule();
						}
						else if (CLASSIFICATION_CATEGORY.equalsIgnoreCase(elemName)) {
							// <Category>
							treatmentRule.setCategory(xmlParser.nextText());
						}					
						else if (CLASSIFICATION_NAME.equalsIgnoreCase(elemName)) {
							// <Classification>
							treatmentRule.setClassification(xmlParser.nextText());
						}
						else if (TREATMENT.equalsIgnoreCase(elemName)) {
							// <Treatment>
							treatment = new Treatment();
						}
						else if (CRITERIA_LIST.equalsIgnoreCase(elemName)) {
							// <CriteriaList>
							ruleAttrib = xmlParser.getAttributeValue(null, RULE_ATTRIB);
							treatmentCriterion = new TreatmentCriterion(ruleAttrib);
							treatment.setTreatmentCriterion(treatmentCriterion);
						}
						else if (SYMPTOM_CRITERIA.equalsIgnoreCase(elemName)) {
							// <SymptomCriteria>
							ruleAttrib = xmlParser.getAttributeValue(null, VALUE_ATTRIB);
							
							symptomId = xmlParser.nextText();
							int identifier = supportingLifeBaseActivity.getResources().getIdentifier(symptomId, "string", "ie.ucc.bis");
							symptomName = supportingLifeBaseActivity.getResources().getString(identifier);			
							
							symptomCriteria = new Symptom(symptomName, ruleAttrib);
							treatment.getTreatmentCriterion().getSymptomCriteria().add(symptomCriteria);
						}
						else if (TREATMENT_CRITERIA.equalsIgnoreCase(elemName)) {
							// <TreatmentCriteria>
							ruleAttrib = xmlParser.getAttributeValue(null, VALUE_ATTRIB);
							
							treatmentName = xmlParser.nextText();
							treatmentCriteria = new TreatmentCriteria(treatmentName, ruleAttrib);
							treatment.getTreatmentCriterion().getTreatmentCriteria().add(treatmentCriteria);
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
							getSystemTreatmentRules().add(treatmentRule);
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
		// DEBUG OUTPUT
//		LoggerUtils.i(LOG_TAG, captureTreatmentRulesDebugOutput(getSystemTreatmentRules()));
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
		LoggerUtils.i(LOG_TAG, "--------------------------------------");
	}
	
	/**
	 * 
	 * Responsible for determining patient treatments
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patient 
	 * 
	 */
	private void determinePatientTreatments(SupportingLifeBaseActivity supportingLifeBaseActivity, 
			List<ReviewItem> reviewItems, Patient patient) {
		
		// 1. iterate over all patient classifications and assign 
		//	  appropriate treatment(s)
		for (Diagnostic diagnostic : patient.getDiagnostics()) {
			for (TreatmentRule treatmentRule : getSystemTreatmentRules()) {
				if (diagnostic.getClassification().getName().equalsIgnoreCase(treatmentRule.getClassification())) {				
					// classification match found so determine if all associated 
					// treatments apply
					for (Treatment treatment : treatmentRule.getTreatments()) {
						boolean symptomCriteriaPasses = false;
						boolean treatmentCriteriaPasses = false;
						
						// check symptom criteria
						List<Symptom> symptomCriterion = treatment.getTreatmentCriterion().getSymptomCriteria();
						if (symptomCriterion.size() != 0) {
							if (CriteriaRule.ALL.name().equalsIgnoreCase(treatment.getTreatmentCriterion().getRule())) {
								symptomCriteriaPasses = checkSymptomCriteria(symptomCriterion, reviewItems, patient, symptomCriterion.size());
							}
							else if (CriteriaRule.ANY.name().equalsIgnoreCase(treatment.getTreatmentCriterion().getRule())) {
								// only need a single symptom in order for the treatment to apply
								symptomCriteriaPasses = checkSymptomCriteria(symptomCriterion, reviewItems, patient, 1);
							}
						}
						else {
							symptomCriteriaPasses = true;
						}
						
						// check treatment criteria
						List<TreatmentCriteria> treatmentCriterion = treatment.getTreatmentCriterion().getTreatmentCriteria();
						if (symptomCriteriaPasses && (treatmentCriterion.size() != 0)) {
							if (CriteriaRule.ALL.name().equalsIgnoreCase(treatment.getTreatmentCriterion().getRule())) {
								treatmentCriteriaPasses = checkTreatmentCriteria(treatmentCriterion, reviewItems, patient, treatmentCriterion.size());
							}
						}
						else {
							treatmentCriteriaPasses = true;
						}
						
						if (symptomCriteriaPasses && treatmentCriteriaPasses) {
							// add the recommended treatment to patient classification
							diagnostic.getTreatmentRecommendations().add(treatment.getRecommendation());
						}						
					} // end of for (Treatment treatment  ....
				}
			} // end of for (TreatmentRule treatmentRule ...
		} // end of for (Diagnostic diagnostic ...
		
		// DEBUG OUTPUT
		LoggerUtils.i(LOG_TAG, captureTreatmentsDebugOutput(patient.getDiagnostics()));
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
	private StringBuilder captureTreatmentsDebugOutput(List<Diagnostic> diagnostics) {
		StringBuilder debugOutput = new StringBuilder();
		
		for (Diagnostic diagnostic : diagnostics) {
			debugOutput.append(diagnostic.getClassification().getName() + "\n");
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
	private StringBuilder captureTreatmentRuleDebugOutput(List<TreatmentRule> treatmentRules) {
		StringBuilder debugOutput = new StringBuilder();
		
		for (TreatmentRule treatmentRule : treatmentRules){
			debugOutput.append(treatmentRule.debugOutput());
		}
		
		return debugOutput;
	}	
	

	/**
	 * Getter Method: getSystemTreatmentRules()
	 */	
	public static ArrayList<TreatmentRule> getSystemTreatmentRules() {
		return systemTreatmentRules;
	}

	/**
	 * Setter Method: setSystemTreatmentRules()
	 */
	public static void setSystemTreatmentRules(ArrayList<TreatmentRule> systemTreatmentRules) {
		TreatmentRuleEngine.systemTreatmentRules = systemTreatmentRules;
	}
}
