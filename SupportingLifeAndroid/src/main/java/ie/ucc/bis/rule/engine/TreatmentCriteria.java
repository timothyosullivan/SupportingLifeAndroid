package ie.ucc.bis.rule.engine;

import java.io.Serializable;

/**
 * Class: TreatmentCriteria
 * 
 * Encapsulates a TreatmentCriteria as defined by 
 * treatment_rules.xml e.g.
 * 
 * 	<TreatmentCriteria value="yes">treatment_criteria_patient_is_two_years_or_older</TreatmentCriterion>
 * 
 * @author TOSullivan
 *
 */
public class TreatmentCriteria implements Serializable {

	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -8729326497091398713L;

	private String identifier;
	private String value;
		
	/**
	 * Constructor
	 */
	public TreatmentCriteria(String identifier, String value) {
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
	 * Provides debug output of the treatment criteria
	 * 
	 */
	public String debugOutput() {
		StringBuilder debugOutput = new StringBuilder();
		
		debugOutput.append("Treatment Criteria: " + getIdentifier() + "\n");
		debugOutput.append(" ----> Value: " + getValue() + "\n");

		return debugOutput.toString();
	}
	
}
