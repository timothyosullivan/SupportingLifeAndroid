package ie.ucc.bis.rule.engine;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.domain.Patient;

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

	public static final String CLASSIFICATION_ELEMENT = "Classification";
	public static final String CLASSIFICATION_CATEGORY = "Category";
	public static final String CLASSIFICATION_NAME = "Name";
	public static final String CLASSIFICATION_TYPE = "Type";
	public static final String CLASSIFICATION_SYMPTOM_RULE = "SymptomRule";
	public static final String CLASSIFICATION_SYMPTOM_RULE_ATTRIB = "rule";
	public static final String CLASSIFICATION_SYMPTOM = "Symptom";
	
	private static final String LOG_TAG = "ie.ucc.bis.rule.engine.RuleEngine";
	
	private static ArrayList<Classification> classificationRules;
	
	/**
	 * 
	 * Responsible for determining patient classifications based on 
	 * assessment
	 * 
	 * @param supportingLifeBaseActivity 
	 * @param patient 
	 * 
	 */
	public static void determineClassifications(SupportingLifeBaseActivity supportingLifeBaseActivity, Patient patient) {

		setClassificationRules(new ArrayList<Classification>());
		
		try {
			String elemName = null;
			Classification classificationRule = null;
			SymptomRule symptomRule = null;
			String symptomRuleAttrib = null;
			XmlResourceParser xmlParser = supportingLifeBaseActivity.getResources().getXml(R.xml.classification_rules);
			
			// pass header <?xml ...
			xmlParser.next();
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
							symptomRule.getSymptoms().add(xmlParser.nextText());
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
		Log.i(LOG_TAG, captureClassificationRulesDebugOutput());
	}
	
	/**
	 * 
	 * Provides debug output of all classification rules held in memory
	 * 
	 */
	private static String captureClassificationRulesDebugOutput() {
		StringBuffer debugOutput = new StringBuffer();
		
		for (Classification classification : getClassificationRules()){
			debugOutput.append(classification.debugOutput());
		}
		
		return debugOutput.toString();
	}

	/**
	 * Getter Method: getClassificationRules()
	 */	
	public static ArrayList<Classification> getClassificationRules() {
		return RuleEngine.classificationRules;
	}

	/**
	 * Setter Method: setClassificationRules()
	 */
	public static void setClassificationRules(ArrayList<Classification> classificationRules) {
		RuleEngine.classificationRules = classificationRules;
	}
}
