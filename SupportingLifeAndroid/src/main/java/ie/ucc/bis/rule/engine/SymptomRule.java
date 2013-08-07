package ie.ucc.bis.rule.engine;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class: SymptomRule
 * 
 * Encapsulates a Symptom Rule as defined by 
 * classification_rules.xml e.g.
 * 
 *	<SymptomRule rule="ANY">
 *		<Symptom>chest_indrawing</Symptom>
 *		<Symptom>stridor</Symptom>
 *		<!-- Any general danger sign -->
 *		<Symptom>not_able_to_drink_or_breastfeed</Symptom>
 *		<Symptom>vomits_everything</Symptom>
 *		<Symptom>history_of_convulsions</Symptom>
 *		<Symptom>lethargic_or_unconscious</Symptom>
 *		<Symptom>convulsing_now</Symptom>
 *	</SymptomRule>
 * 
 * @author TOSullivan
 *
 */

public class SymptomRule implements Serializable {

	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 2175782496476894436L;
	
	private String rule;
	private ArrayList<String> symptoms;
	private SymptomRule symptomRule; // recursive class member
	
	/**
	 * Constructor
	 * 
	 */	
	public SymptomRule() {
		setSymptoms(new ArrayList<String>());
	}
	
	/**
	 * Constructor
	 * 
	 */	
	public SymptomRule(String rule) {
		setRule(rule);
		setSymptoms(new ArrayList<String>());
	}

	/**
	 * Getter Method: getRule()
	 */
	public String getRule() {
		return rule;
	}

	/**
	 * Setter Method: setRule()
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}

	/**
	 * Getter Method: getSymptoms()
	 */
	public ArrayList<String> getSymptoms() {
		return symptoms;
	}

	/**
	 * Setter Method: setSymptoms()
	 */
	public void setSymptoms(ArrayList<String> symptoms) {
		this.symptoms = symptoms;
	}

	/**
	 * Getter Method: getSymptomRule()
	 */
	public SymptomRule getSymptomRule() {
		return symptomRule;
	}

	/**
	 * Setter Method: setSymptomRule()
	 */
	public void setSymptomRule(SymptomRule symptomRule) {
		this.symptomRule = symptomRule;
	}

	/**
	 * 
	 * Provides debug output of symptom rule
	 * 
	 */
	public String debugOutput() {
		StringBuffer debugOutput = new StringBuffer();

		debugOutput.append("Rule: " + getRule() + "\n");
		for (String symptom : getSymptoms()) {
			debugOutput.append("Symptom: " + symptom + "\n");
		}
		
		if (getSymptomRule() != null) {
			debugOutput.append(getSymptomRule().debugOutput()); // recursive call
		}
		
		return debugOutput.toString();
	}
	
	
	
}
