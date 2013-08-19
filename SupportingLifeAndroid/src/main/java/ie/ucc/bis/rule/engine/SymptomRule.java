package ie.ucc.bis.rule.engine;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class: SymptomRule
 * 
 * Encapsulates a Symptom Rule as defined by 
 * classification_rules.xml e.g.
 * 
 * <SymptomRule rule="ANY_SYMPTOM">
 * 		<Symptom>breathing_assessment_chest_indrawing_symptom_id</Symptom> 				<!-- Chest Indrawing -->
 * 		<Symptom>breathing_assessment_stridor_symptom_id</Symptom> 						<!-- Stridor -->
 * 		<!-- Any general danger sign -->
 * 		<Symptom>general_danger_signs_drink_breastfeed_symptom_id</Symptom> 			<!-- Not Able to Drink or Breastfeed -->
 * 		<Symptom>general_danger_signs_vomits_everything_symptom_id</Symptom> 			<!-- Vomits Everything -->
 * 		<Symptom>general_danger_signs_convulsions_symptom_id</Symptom> 					<!-- History of Convulsions -->
 * 		<Symptom>general_danger_signs_lethargic_or_unconscious_symptom_id</Symptom>		<!-- Lethargic or Unconscious -->
 * 		<Symptom>general_danger_signs_convulsing_now_symptom_id</Symptom>				<!-- Convulsing Now -->
 * </SymptomRule>
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
				
		return debugOutput.toString();
	}
	
	
	
}
