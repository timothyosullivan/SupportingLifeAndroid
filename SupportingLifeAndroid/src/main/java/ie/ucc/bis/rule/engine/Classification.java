package ie.ucc.bis.rule.engine;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Class: Classification
 * 
 * Encapsulates a Classification Rules as defined by 
 * classification_rules.xml e.g.
 * 
 * 	<Classification>
 *		<Category>cough_difficult_breathing</Category>
 *		<Name>Severe Pneumonia or Very Severe Disease</Name>
 *		<Type>SEVERE</Type>
 *		<SymptomRule rule="ANY">
 *			<Symptom>chest_indrawing</Symptom>
 *			<Symptom>stridor</Symptom>
 *			<!-- Any general danger sign -->
 *			<Symptom>not_able_to_drink_or_breastfeed</Symptom>
 *			<Symptom>vomits_everything</Symptom>
 *			<Symptom>history_of_convulsions</Symptom>
 *			<Symptom>lethargic_or_unconscious</Symptom>
 *			<Symptom>convulsing_now</Symptom>
 *		</SymptomRule>
 *	</Classification>
 * 
 * @author TOSullivan
 *
 */

public class Classification implements Serializable {

    /**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 2250655542678899484L;

	private String category;
	private String name;
	private String type;
	private int priority;
	private List<SymptomRule> symptomRules;
 
	/**
	 * Constructor
	 * 
	 */
	public Classification() {
		setSymptomRules(new ArrayList<SymptomRule>());
	}
		
	/**
	 * Getter Method: getCategory()
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Setter Method: setCategory()
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Getter Method: getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter Method: setName()
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter Method: getType()
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter Method: setType()
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter Method: getPriority()
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Setter Method: setPriority()
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Getter Method: getSymptomRules()
	 */
	public List<SymptomRule> getSymptomRules() {
		return symptomRules;
	}

	/**
	 * Setter Method: setSymptomRules()
	 */
	public void setSymptomRules(List<SymptomRule> symptomRules) {
		this.symptomRules = symptomRules;
	}

	/**
	 * 
	 * Provides debug output of classification rule
	 * 
	 */
	public String debugOutput() {
		StringBuffer debugOutput = new StringBuffer();

		debugOutput.append("------------------------------------ \n");
		debugOutput.append("Category: " + getCategory() + "\n");
		debugOutput.append("Name: " + getName() + "\n");
		debugOutput.append("Type: " + getType() + "\n");
		for (SymptomRule symptomRule : getSymptomRules()) {
			debugOutput.append(symptomRule.debugOutput());
		}
		
		return debugOutput.toString();
	}
}
