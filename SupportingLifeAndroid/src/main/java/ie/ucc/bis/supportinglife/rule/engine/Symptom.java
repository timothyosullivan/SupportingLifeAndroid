package ie.ucc.bis.supportinglife.rule.engine;

import java.io.Serializable;

/**
 * Class: Symptom
 * 
 * Encapsulates a Symptom defined by 
 * classification_rules.xml e.g.
 * 
 * 	<Symptom value="yes">breathing_assessment_chest_indrawing_symptom_id</Symptom> 				<!-- Chest Indrawing -->
 * 
 * @author TOSullivan
 *
 */
public class Symptom implements Serializable {

	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 7913629697084438822L;

	private String identifier;
	private String value;
	
	/**
	 * Constructor
	 */
	public Symptom(String identifier, String value) {
		setIdentifier(identifier);
		setValue(value);
	}

	/**
	 * Getter Method: getIdentifier()
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Setter Method: setIdentifier()
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Getter Method: getValue()
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter Method: setValue()
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 * Provides debug output of a symptom
	 * 
	 */
	public String debugOutput() {
		StringBuilder debugOutput = new StringBuilder();
		
		debugOutput.append("Symptom: " + getIdentifier() + "\n");
		debugOutput.append(" ----> Symptom Value: " + getValue() + "\n");

		return debugOutput.toString();
	}
	
}
