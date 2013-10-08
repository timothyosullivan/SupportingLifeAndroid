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
 * OR
 * 
 * 	<Classification>
 * 		<Category>persistent_diarrhoea</Category>
 * 		<Name>Severe Persistent Diarrhoea</Name>
 * 		<Type>SEVERE</Type>
 * 		<Priority>1</Priority>
 * 		<ClassificationRule rule="ANY_CLASSIFICATION">
 * 			<ClassificationDiagnosed value="true">Severe Dehydration</ClassificationDiagnosed>			<!-- Severe Dehyration (Classification) : TRUE -->
 * 			<ClassificationDiagnosed value="true">Some Dehydration</ClassificationDiagnosed>			<!-- Some Dehyration (Classification) : TRUE -->
 * 		</ClassificationRule>
 * </Classification>
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
	private List<ClassificationRule> classificationRules;
 
	/**
	 * Constructor
	 * 
	 */
	public Classification() {
		setSymptomRules(new ArrayList<SymptomRule>());
		setClassificationRules(new ArrayList<ClassificationRule>());
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Classification)) {
			return false;
		}
		Classification other = (Classification) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
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
	 * Getter Method: getClassificationRules()
	 */
	public List<ClassificationRule> getClassificationRules() {
		return classificationRules;
	}

	/**
	 * Setter Method: setClassificationRules()
	 */
	public void setClassificationRules(List<ClassificationRule> classificationRules) {
		this.classificationRules = classificationRules;
	}

	/**
	 * 
	 * Provides debug output of classification rule
	 * 
	 */
	public String debugOutput() {
		StringBuilder debugOutput = new StringBuilder();

		debugOutput.append("------------------------------------ \n");
		debugOutput.append("Category: " + getCategory() + "\n");
		debugOutput.append("Name: " + getName() + "\n");
		debugOutput.append("Type: " + getType() + "\n");
		
		if (getSymptomRules().size() != 0) {
			for (SymptomRule symptomRule : getSymptomRules()) {
				debugOutput.append(symptomRule.debugOutput());
			}
		}
		if (getClassificationRules().size() != 0) {
			for (ClassificationRule classificationRule : getClassificationRules()) {
				debugOutput.append(classificationRule.debugOutput());
			}		
		}
		
		return debugOutput.toString();
	}

}
