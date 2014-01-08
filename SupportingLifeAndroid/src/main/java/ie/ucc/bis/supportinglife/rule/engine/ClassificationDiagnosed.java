package ie.ucc.bis.supportinglife.rule.engine;

import java.io.Serializable;

/**
 * Class: ClassificationDiagnosed
 * 
 * Encapsulates a ClassificationDiagnosed defined by 
 * classification_rules.xml e.g.
 * 
 * 	<ClassificationDiagnosed value="true">Severe Dehydration</ClassificationDiagnosed>			<!-- Severe Dehyration (Classification) : TRUE -->
 * 
 * @author TOSullivan
 *
 */
public class ClassificationDiagnosed implements Serializable {

	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 3175527057468079486L;

	private String identifier;
	private String value;
	
	/**
	 * Constructor
	 */
	public ClassificationDiagnosed(String identifier, String value) {
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
	
}
