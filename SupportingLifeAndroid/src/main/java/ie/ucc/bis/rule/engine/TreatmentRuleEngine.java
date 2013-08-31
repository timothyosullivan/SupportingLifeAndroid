package ie.ucc.bis.rule.engine;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.domain.Patient;
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

	private static final String LOG_TAG = "ie.ucc.bis.rule.engine.TreatmentRuleEngine";
	
	private static ArrayList<TreatmentRule> systemTreatmentRules;
	
	/**
	 * 
	 * Responsible for determining patient treatments based on 
	 * assessment
	 * 
	 * @param supportingLifeBaseActivity 
	 * @param reviewItems
	 * @param classifications
	 * @param patient 
	 * 
	 */
	public void determineTreatments(SupportingLifeBaseActivity supportingLifeBaseActivity, ArrayList<ReviewItem> reviewItems, 
			ArrayList<Classification> classifications, Patient patient) {

		setSystemTreatmentRules(new ArrayList<TreatmentRule>());
		parseTreatmentRules(supportingLifeBaseActivity);
		determinePatientTreatments(supportingLifeBaseActivity, reviewItems, patient);
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
							symptomCriteria = new Symptom(ruleAttrib, xmlParser.nextText());
							treatment.getTreatmentCriterion().getSymptomCriteria().add(symptomCriteria);
						}
						else if (TREATMENT_CRITERIA.equalsIgnoreCase(elemName)) {
							// <TreatmentCriteria>
							ruleAttrib = xmlParser.getAttributeValue(null, VALUE_ATTRIB);
							treatmentCriteria = new TreatmentCriteria(ruleAttrib, xmlParser.nextText());
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
			ArrayList<ReviewItem> reviewItems, Patient patient) {

		// 1. iterate over all patient classifications and assign 
		//	  appropriate treatment(s)
		for (Diagnostic diagnostic : patient.getDiagnostics()) {
			for (TreatmentRule treatmentRule : getSystemTreatmentRules()) {
				if (diagnostic.getClassification().getName().equalsIgnoreCase(treatmentRule.getClassification())) {
					// classification match found so determine if all associated 
					// treatments apply
					for (Treatment treatment : treatmentRule.getTreatments()) {
						if (treatment.getTreatmentCriterion().getSymptomCriteria() != null) {
							// TODO
						}
						if (treatment.getTreatmentCriterion().getTreatmentCriteria() != null) {
							// TODO
						}
						// add the recommended treatment to patient classification
						diagnostic.getTreatmentRecommendations().add(treatment.getRecommendation());
					}
				}
			} // end of for (TreatmentRule treatmentRule ...
		}
		
		// DEBUG OUTPUT
		LoggerUtils.i(LOG_TAG, captureTreatmentsDebugOutput(patient.getDiagnostics()));
	}

	/**
	 * 
	 * Cross-references a patient record against a specific classification rule.
	 * If a match is found, then the associated classification will be added to the
	 * patient record.
	 * 
	 * @param patient - i.e. patient record to be check
	 * @param ClassificationRule - system classification rule to be checked
	 * 
	 * 
	 */
/*	private void checkClassificationRuleAgainstPatientRecord(Patient patient, Classification classification) {
		Classification classificationMatch;
		for(ClassificationRule classificationRule : classification.getClassificationRules()) {
			for (ClassificationDiagnosed classificationDiagnosed : classificationRule.getClassificationsDiagnosed()) {
				if (ClassificationUtils.containsClassificationName(classificationDiagnosed.getIdentifier(), patient.getClassifications())) {
					classificationMatch = new Classification();
					ClassificationUtils.copyClassificationHeadlineDetails(classification, classificationMatch);
					ClassificationDiagnosed classificationDiagnosedMatch = new ClassificationDiagnosed(classificationDiagnosed.getIdentifier(), 
							classificationDiagnosed.getValue());
					
					classificationMatch.getClassificationRules().add(new ClassificationRule(classificationRule.getRule()));
					classificationMatch.getClassificationRules().get(0).getClassificationsDiagnosed().add(classificationDiagnosedMatch);
					patient.getClassifications().add(classificationMatch);
				}
			}
		}
	}
*/
	/**
	 * 
	 * Determines whether a patient has a specific classification
	 * 
	 * @param classification - i.e. classification to be checked
	 * @param reviewItems
	 * @param classificationMatch 
	 * 
	 */
/*	private boolean patientHasClassification(Classification classification, ArrayList<ReviewItem> reviewItems, Classification classificationMatch) {
		boolean hasClassification = false;
		
		// determine the rule constraints on this classification
		// TODO - Create Enum and Switch to Case Statment
		ClassificationUtils.copyClassificationHeadlineDetails(classification, classificationMatch);
		
		for (SymptomRule symptomRule : classification.getSymptomRules()) {
			if (symptomRule.getRule().equalsIgnoreCase(SymptomRuleCriteria.ANY_SYMPTOM.name())) {
				hasClassification = checkSymptomRule(symptomRule, reviewItems, classificationMatch, SymptomRuleCriteria.ANY_SYMPTOM.getSymptomsRequired());
			}
			else if (symptomRule.getRule().equalsIgnoreCase(SymptomRuleCriteria.TWO_SYMPTOMS_REQUIRED.name())) {
				hasClassification = checkSymptomRule(symptomRule, reviewItems, classificationMatch, SymptomRuleCriteria.TWO_SYMPTOMS_REQUIRED.getSymptomsRequired());
			}
			if (!hasClassification) {
				break;
			}
		}
		return hasClassification;
	}
*/	
	/**
	 * 
	 * Determines whether a symptom rule applies to a patient
	 * 
	 * @param SymptomRule - i.e. SymptomRule to be checked
	 * @param reviewItems
	 * @param classificationMatch 
	 * @param symptomNumberRequired
	 * 
	 */
/*	private boolean checkSymptomRule(SymptomRule symptomRule, ArrayList<ReviewItem> reviewItems, Classification classificationMatch, int symptomNumberRequired) {
		int symptomCounter = 0;
		boolean symptomRuleApplies = false;
		
		// get current number of symptom rules associated with the classification
		classificationMatch.getSymptomRules().add(new SymptomRule());
		int symptomRuleCounter = classificationMatch.getSymptomRules().size() - 1;
		
		SYMPTOM_RULE_CHECK:
		for (Symptom symptom : symptomRule.getSymptoms()) {
			for (ReviewItem reviewItem : reviewItems) {
				if (reviewItem.getSymptomValue() != null){
					// symptom id match?
					if (symptom.getIdentifier().equalsIgnoreCase(reviewItem.getSymptomId())) {
						// symptom value match?
						if (symptom.getValue().equalsIgnoreCase(reviewItem.getSymptomValue())) {
							classificationMatch.getSymptomRules().get(symptomRuleCounter).getSymptoms().add(symptom);
							symptomCounter++;
							if (symptomCounter == symptomNumberRequired) {
								symptomRuleApplies = true;
								break SYMPTOM_RULE_CHECK;
							}
						} // end of if (symptom.getValue().equalsIgnoreCase(... 
					} // end of if (symptom.getIdentifier().equalsIgnoreCase...
				} // end of if (reviewItem.isPositiveSymptom())
			}
		}
		return symptomRuleApplies;
	}
*/	
	/**
	 * Returns a subset of those system classifications which contain
	 * a classification rule e.g.
	 * 
	 * <Classification>
	 * 	<Category>persistent_diarrhoea</Category>
	 * 	<Name>Severe Persistent Diarrhoea</Name>
	 * 	<Type>SEVERE</Type>
	 * 	<Priority>1</Priority>
	 * 	<ClassificationRule rule="ANY_CLASSIFICATION">
	 * 		<ClassificationDiagnosed value="true">Severe Dehydration</ClassificationDiagnosed>			<!-- Severe Dehyration (Classification) : TRUE -->
	 * 		<ClassificationDiagnosed value="true">Some Dehydration</ClassificationDiagnosed>			<!-- Some Dehyration (Classification) : TRUE -->
	 * 	</ClassificationRule>
	 * </Classification>
	 * 
	 * @return List<Classification>
	 * 
	 */
/*	private List<Classification> retrieveSystemClassificationWithClassificationRule() {
		List<Classification> classifications = new ArrayList<Classification>();
		
		for (Classification classification : getSystemClassifications()) {
			if (classification.getClassificationRules().size() != 0) {
				// there is a <ClassificationRule> associated with this Classification
				classifications.add(classification);
			}
		}
		return classifications;
	}
*/
	
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
