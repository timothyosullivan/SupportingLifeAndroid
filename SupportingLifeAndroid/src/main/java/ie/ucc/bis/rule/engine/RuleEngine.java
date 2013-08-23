package ie.ucc.bis.rule.engine;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.ui.utilities.ClassificationUtils;
import ie.ucc.bis.wizard.model.review.ReviewItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;


/**
 * Class: RuleEngine
 * 
 * Responsible for parsing classification and treatment xml rules and
 * determining appropriate classifications and treatment for patient.
 * 
 * @author TOSullivan
 *
 */

public class RuleEngine {

	private static final String CLASSIFICATION_ELEMENT = "Classification";
	private static final String CLASSIFICATION_CATEGORY = "Category";
	private static final String CLASSIFICATION_NAME = "Name";
	private static final String CLASSIFICATION_TYPE = "Type";
	private static final String CLASSIFICATION_PRIORITY = "Priority";
	private static final String CLASSIFICATION_SYMPTOM_RULE = "SymptomRule";
	private static final String CLASSIFICATION_SYMPTOM_RULE_ATTRIB = "rule";
	private static final String CLASSIFICATION_SYMPTOM = "Symptom";
	private static final String CLASSIFICATION_SYMPTOM_VALUE_ATTRIB = "value";
		
	private static final String LOG_TAG = "ie.ucc.bis.rule.engine.RuleEngine";
	
	private static ArrayList<Classification> systemClassifications;
	
	/**
	 * 
	 * Responsible for determining patient classifications based on 
	 * assessment
	 * 
	 * @param supportingLifeBaseActivity 
	 * @param reviewItems 
	 * @param patient 
	 * 
	 */
	public void determineClassifications(SupportingLifeBaseActivity supportingLifeBaseActivity, ArrayList<ReviewItem> reviewItems, Patient patient) {

		setClassifications(new ArrayList<Classification>());
		parseClassificationRules(supportingLifeBaseActivity);
		determinePatientClassifications(supportingLifeBaseActivity, reviewItems, patient);
	}

	/**
	 * 
	 * Responsible for parsing xml-based classification rules
	 * 
	 * @param supportingLifeBaseActivity 
	 * 
	 */
	private void parseClassificationRules(SupportingLifeBaseActivity supportingLifeBaseActivity) {
		try {
			String elemName = null;
			Classification classification = null;
			SymptomRule symptomRule = null;
			String symptomRuleAttrib = null;
			String symptomId = null;
			String symptomName = null;
			String symptomValueAttrib = null;
			XmlResourceParser xmlParser = supportingLifeBaseActivity.getResources().getXml(R.xml.classification_rules);
			
			int eventType = xmlParser.next();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
					case XmlPullParser.START_TAG:
						elemName = xmlParser.getName();
						if (CLASSIFICATION_ELEMENT.equalsIgnoreCase(elemName)) {
							// <Classification>
							classification = new Classification();
						}
						else if (CLASSIFICATION_CATEGORY.equalsIgnoreCase(elemName)) {
							// <Category>
							classification.setCategory(xmlParser.nextText());
						}					
						else if (CLASSIFICATION_NAME.equalsIgnoreCase(elemName)) {
							// <Name>
							classification.setName(xmlParser.nextText());
						}
						else if (CLASSIFICATION_TYPE.equalsIgnoreCase(elemName)) {
							// <Type>
							classification.setType(xmlParser.nextText());
						}
						else if (CLASSIFICATION_PRIORITY.equalsIgnoreCase(elemName)) {
							// <Priority>
							classification.setPriority(Integer.parseInt(xmlParser.nextText()));
						}
						else if (CLASSIFICATION_SYMPTOM_RULE.equalsIgnoreCase(elemName)) {
							// <SymptomRule>
							symptomRuleAttrib = xmlParser.getAttributeValue(null, CLASSIFICATION_SYMPTOM_RULE_ATTRIB);
							symptomRule = new SymptomRule(symptomRuleAttrib);
						}
						else if (CLASSIFICATION_SYMPTOM.equalsIgnoreCase(elemName)) {
							// <Symptom>
							symptomValueAttrib = xmlParser.getAttributeValue(null, CLASSIFICATION_SYMPTOM_VALUE_ATTRIB);
							symptomId = xmlParser.nextText();
							int identifier = supportingLifeBaseActivity.getResources().getIdentifier(symptomId, "string", "ie.ucc.bis");

							symptomName = supportingLifeBaseActivity.getResources().getString(identifier);							
							Symptom symptom = new Symptom(symptomName, symptomValueAttrib);								
							symptomRule.getSymptoms().add(symptom);
						}						
						break;
					case XmlPullParser.END_TAG:
					if (CLASSIFICATION_SYMPTOM_RULE.equalsIgnoreCase(xmlParser.getName())) {
						// </SymptomRule>
						classification.getSymptomRules().add(symptomRule);
					}						
					else if(CLASSIFICATION_ELEMENT.equalsIgnoreCase(xmlParser.getName())) {
						// </Classification>
						getSystemClassifications().add(classification);
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
//		LoggerUtils.i(LOG_TAG, captureClassificationsDebugOutput());
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
	}
	
	/**
	 * 
	 * Responsible for determining patient classifications
	 * 
	 * @param supportingLifeBaseActivity
	 * @param reviewItems
	 * @param patient 
	 * 
	 */
	private void determinePatientClassifications(SupportingLifeBaseActivity supportingLifeBaseActivity, ArrayList<ReviewItem> reviewItems, Patient patient) {
		// 1. iterate over all review items and perform first rudimentary check in assessing
		//    if the symptom criteria applies
		for (ReviewItem reviewItem : reviewItems) {
			reviewItem.assessSymptom(supportingLifeBaseActivity);
		}
		
		// 2. iterate over all classifications to determine if any fit the patient
		// assessment
		boolean classificationApplies = false;
		Classification classificationMatch = new Classification();
		for (Classification classification : getSystemClassifications()) {
			classificationApplies = patientHasClassification(classification, reviewItems, classificationMatch);
			if (classificationApplies) {
				patient.getClassifications().add(classificationMatch);
			}
			classificationMatch = new Classification();
		}
		
		// 3. Now need to ensure we only report the highest priority classification in each
		//	  classification grouping
		List<Classification> uniqueClassificationGrouping = new ArrayList<Classification>();
		for (Classification classification : patient.getClassifications()) {
			String classificationId = classification.getCategory();
			if (!ClassificationUtils.containsClassificationCategoryId(uniqueClassificationGrouping, classificationId)) {
				Classification highestPriorityClassification = ClassificationUtils.retrieveHighestPriorityClassification(classificationId, patient.getClassifications());
				uniqueClassificationGrouping.add(highestPriorityClassification);
			}
		}
		
		// 4. Finally, we should order the classifications such that the highest priority 
		//    classifications are listed first. This will help alert the HSA to the 
		//    severity of the patient's condition.
		Collections.sort(uniqueClassificationGrouping, new ClassificationComparator());
		patient.setClassifications((ArrayList<Classification>) uniqueClassificationGrouping);
		
		// DEBUG OUTPUT
		// LoggerUtils.i(LOG_TAG, captureClassificationDebugOutput(patient.getClassifications()));
	}

	/**
	 * 
	 * Determines whether a patient has a specific classification
	 * 
	 * @param classification - i.e. classification to be checked
	 * @param reviewItems
	 * @param classificationMatch 
	 * 
	 */
	private boolean patientHasClassification(Classification classification, ArrayList<ReviewItem> reviewItems, Classification classificationMatch) {
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
	private boolean checkSymptomRule(SymptomRule symptomRule, ArrayList<ReviewItem> reviewItems, Classification classificationMatch, int symptomNumberRequired) {
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

	/**
	 * 
	 * Provides debug output of all classifications passed to
	 * the method
	 * 
	 * @param classifications
	 * 
	 */
	private StringBuilder captureClassificationDebugOutput(List<Classification> classifications) {
		StringBuilder debugOutput = new StringBuilder();
		
		for (Classification classification : classifications){
			debugOutput.append(classification.debugOutput());
		}
		
		return debugOutput;
	}	
	
	/**
	 * 
	 * Provides debug output of all classifications held in memory
	 * 
	 */
	private StringBuilder captureClassificationsDebugOutput() {
		return captureClassificationDebugOutput(getSystemClassifications());
	}

	/**
	 * Getter Method: getSystemClassifications()
	 */	
	public ArrayList<Classification> getSystemClassifications() {
		return RuleEngine.systemClassifications;
	}

	/**
	 * Setter Method: setSystemClassifications()
	 */
	public void setClassifications(ArrayList<Classification> systemClassifications) {
		RuleEngine.systemClassifications = systemClassifications;
	}
}
