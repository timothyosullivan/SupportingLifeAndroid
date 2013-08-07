package ie.ucc.bis.rule.engine;


import java.io.Serializable;


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
	private SymptomRule symptomRule;
 
	/**
	 * Constructor
	 * 
	 */
	public Classification() {
		setSymptomRule(new SymptomRule());
	}
	
	/**
	 * Constructor
	 * 
	 * @param category
	 * @param name
	 * @param type
	 */
	public Classification(String category, String name, String type) {
		setCategory(category);
		setName(name);
		setType(type);
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
	 * Provides debug output of classification rule
	 * 
	 */
	public String debugOutput() {
		StringBuffer debugOutput = new StringBuffer();

		debugOutput.append("Category: " + getCategory() + "\n");
		debugOutput.append("Name: " + getName() + "\n");
		debugOutput.append("Type: " + getType() + "\n");
		debugOutput.append(getSymptomRule().debugOutput());
		
		return debugOutput.toString();
	}
}
