package ie.ucc.bis.rule.engine;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class: ClassificationRule
 * 
 * Encapsulates a Classification Rule as defined by 
 * classification_rules.xml e.g.
 * 
 * <ClassificationRule rule="ANY_CLASSIFICATION">
 * 		<ClassificationDiagnosed value="true">Severe Dehydration</ClassificationDiagnosed>			<!-- Severe Dehyration (Classification) : TRUE -->
 * 		<ClassificationDiagnosed value="true">Some Dehydration</ClassificationDiagnosed>			<!-- Some Dehyration (Classification) : TRUE -->
 * </ClassificationRule>
 * 
 * @author TOSullivan
 *
 */

public class ClassificationRule implements Serializable {

	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -8080285974391822553L;

	
	private String rule;
	private ArrayList<ClassificationDiagnosed> classificationsDiagnosed;
	
	/**
	 * Constructor
	 * 
	 */	
	public ClassificationRule() {
		setClassificationsDiagnosed(new ArrayList<ClassificationDiagnosed>());
	}
	
	/**
	 * Constructor
	 * 
	 */	
	public ClassificationRule(String rule) {
		setRule(rule);
		setClassificationsDiagnosed(new ArrayList<ClassificationDiagnosed>());
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
	 * Getter Method: getClassificationsDiagnosed()
	 */
	public ArrayList<ClassificationDiagnosed> getClassificationsDiagnosed() {
		return classificationsDiagnosed;
	}

	/**
	 * Setter Method: setClassificationsDiagnosed()
	 */
	public void setClassificationsDiagnosed(ArrayList<ClassificationDiagnosed> classificationsDiagnosed) {
		this.classificationsDiagnosed = classificationsDiagnosed;
	}

	/**
	 * 
	 * Provides debug output of classification rule
	 * 
	 */
	public String debugOutput() {
		StringBuffer debugOutput = new StringBuffer();

		debugOutput.append("Classification Rule: " + getRule() + "\n");
		for (ClassificationDiagnosed classificationDiagnosed : getClassificationsDiagnosed()) {
			debugOutput.append("Classification Diagnosed: " + classificationDiagnosed.getIdentifier() + "\n");
			debugOutput.append(" ----> Classification Diagnosed Value: " + classificationDiagnosed.getValue() + "\n");
		}
				
		return debugOutput.toString();
	}
	
	
	
}
