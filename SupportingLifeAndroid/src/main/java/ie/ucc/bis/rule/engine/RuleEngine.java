package ie.ucc.bis.rule.engine;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.domain.Patient;
import ie.ucc.bis.wizard.model.ReviewItem;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Log;


/**
 * Class: RuleEngine
 * 
 * Responsible for parsing classification and treatment xml rules and
 * determining approproriate classifications and treatment for patient.
 * 
 * @author TOSullivan
 *
 */

public class RuleEngine {

	private static final String CLASSIFICATION_ELEMENT = "Classification";
	private static final String CLASSIFICATION_CATEGORY = "Category";
	private static final String CLASSIFICATION_NAME = "Name";
	private static final String CLASSIFICATION_TYPE = "Type";
	private static final String CLASSIFICATION_SYMPTOM_RULE = "SymptomRule";
	private static final String CLASSIFICATION_SYMPTOM_RULE_ATTRIB = "rule";
	private static final String CLASSIFICATION_SYMPTOM = "Symptom";
	
	// Symptom Rules
	private static final String ANY_SYMPTOM_RULE = "ANY_SYMPTOM";
	private static final String TWO_SYMPTOMS_REQUIRED_RULE = "TWO_SYMPTOMS_REQUIRED";
	private static final int ONE_SYMPTOM_REQUIRED = 1;
	private static final int TWO_SYMPTOMS_REQUIRED = 2;	
	
	
	private static final String LOG_TAG = "ie.ucc.bis.rule.engine.RuleEngine";
	
	private static ArrayList<Classification> classificationRules;
	
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

		setClassificationRules(new ArrayList<Classification>());
		parseClassificationRules(supportingLifeBaseActivity);
		determinePatientClassifications(reviewItems, patient);
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
			Classification classificationRule = null;
			SymptomRule symptomRule = null;
			String symptomRuleAttrib = null;
			String symptomId = null;
			String symptomName = null;
			XmlResourceParser xmlParser = supportingLifeBaseActivity.getResources().getXml(R.xml.classification_rules);
			
			int eventType = xmlParser.next();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
					case XmlPullParser.START_TAG:
						elemName = xmlParser.getName();
						if (CLASSIFICATION_ELEMENT.equalsIgnoreCase(elemName)) {
							// <Classification>
							classificationRule = new Classification();
						}
						else if (CLASSIFICATION_CATEGORY.equalsIgnoreCase(elemName)) {
							// <Category>
							classificationRule.setCategory(xmlParser.nextText());
						}					
						else if (CLASSIFICATION_NAME.equalsIgnoreCase(elemName)) {
							// <Name>
							classificationRule.setName(xmlParser.nextText());
						}
						else if (CLASSIFICATION_TYPE.equalsIgnoreCase(elemName)) {
							// <Type>
							classificationRule.setType(xmlParser.nextText());
						}
						else if (CLASSIFICATION_SYMPTOM_RULE.equalsIgnoreCase(elemName)) {
							// <SymptomRule>
							symptomRuleAttrib = xmlParser.getAttributeValue(null, CLASSIFICATION_SYMPTOM_RULE_ATTRIB);
							symptomRule = new SymptomRule(symptomRuleAttrib);
						}
						else if (CLASSIFICATION_SYMPTOM.equalsIgnoreCase(elemName)) {
							// <Symptom>
							symptomId = xmlParser.nextText();
							int identifier = supportingLifeBaseActivity.getResources().getIdentifier(symptomId, "string", "ie.ucc.bis");
							symptomName = supportingLifeBaseActivity.getResources().getString(identifier);
							symptomRule.getSymptoms().add(symptomName);
						}						
						break;
					case XmlPullParser.END_TAG:
						if(CLASSIFICATION_ELEMENT.equalsIgnoreCase(xmlParser.getName())) {
							// </Classification>
							// shallow copy of symptoms into ClassificationRule - fine as strings are immutable
							classificationRule.setSymptomRule(symptomRule);
							getClassificationRules().add(classificationRule);
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
		Log.i(LOG_TAG, captureClassificationRulesDebugOutput());
	}
	
	/**
	 * 
	 * Responsible for determining patient classifications
	 * 
	 * @param reviewItems
	 * @param patient 
	 * 
	 */
	private void determinePatientClassifications(ArrayList<ReviewItem> reviewItems, Patient patient) {
		// iterate over all classifications to determine if any fit the patient
		// assessment
		boolean classificationApplies = false;
		Classification classificationMatch = new Classification();
		for (Classification classification : getClassificationRules()) {
			classificationApplies = patientHasClassification(classification, reviewItems, classificationMatch);
			if (classificationApplies) {
				patient.getClassifications().add(classificationMatch);
				classificationApplies = false;
				classificationMatch = new Classification();
			}
		}
		// DEBUG OUTPUT
		Log.i(LOG_TAG, captureClassificationDebugOutput(patient.getClassifications()));
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
		if (classification.getSymptomRule().getRule().equalsIgnoreCase(ANY_SYMPTOM_RULE)) {
			hasClassification = checkSymptomRule(classification, reviewItems, classificationMatch, ONE_SYMPTOM_REQUIRED);
		}
		return hasClassification;
	}

	/**
	 * 
	 * Determines whether a symptom rule applies to a patient
	 * 
	 * @param classification - i.e. classification to be checked
	 * @param reviewItems
	 * @param classificationMatch 
	 * @param symptomNumberRequired
	 * 
	 */
	private boolean checkSymptomRule(Classification classification, ArrayList<ReviewItem> reviewItems, Classification classificationMatch, int symptomNumberRequired) {
		int symptomCounter = 0;
		boolean symptomRuleApplies = false;
		
		SYMPTOM_RULE_CHECK:
		for (String symptom : classification.getSymptomRule().getSymptoms()) {
			for (ReviewItem reviewItem : reviewItems) {
				if (symptom.equalsIgnoreCase(reviewItem.getSymptomId())) {
					classificationMatch.getSymptomRule().getSymptoms().add(symptom);
					symptomCounter++;
					if (symptomCounter == symptomNumberRequired) {
						classificationMatch.setName(classification.getName());
						classificationMatch.setType(classification.getType());
						classificationMatch.setCategory(classification.getCategory());
						symptomRuleApplies = true;
						break SYMPTOM_RULE_CHECK;
					}
				}
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
	private String captureClassificationDebugOutput(ArrayList<Classification> classifications) {
		StringBuffer debugOutput = new StringBuffer();
		
		for (Classification classification : classifications){
			debugOutput.append(classification.debugOutput());
		}
		
		return debugOutput.toString();
	}	
	
	/**
	 * 
	 * Provides debug output of all classification rules held in memory
	 * 
	 */
	private String captureClassificationRulesDebugOutput() {
		return captureClassificationDebugOutput(getClassificationRules());
	}

	/**
	 * Getter Method: getClassificationRules()
	 */	
	public ArrayList<Classification> getClassificationRules() {
		return RuleEngine.classificationRules;
	}

	/**
	 * Setter Method: setClassificationRules()
	 */
	public void setClassificationRules(ArrayList<Classification> classificationRules) {
		RuleEngine.classificationRules = classificationRules;
	}
}
