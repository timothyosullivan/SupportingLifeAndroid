package ie.ucc.bis.supportinglife.rule.engine;


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
 * 		<Category>red_eye_for_4_days_or_more</Category>
 * 		<Name>Red Eye for 4 Days or more</Name>
 * 		<Identifier>CCM_RED_EYE_FOR_4_DAYS_OR_MORE_CLASSIFICATION</Identifier>
 * 		<CcmTreatmentDisplayName>Red Eye</CcmTreatmentDisplayName>
 * 		<Type>DANGER_SIGN</Type>
 * 		<Priority>1</Priority>
 * 		<SymptomRule rule="ANY_SYMPTOM">
 * 			<Symptom value="yes">ccm_ask_secondary_assessment_red_eyes_symptom_id</Symptom>							<!-- Red Eyes : YES -->
 * 		</SymptomRule>
 * 		<SymptomRule rule="ANY_SYMPTOM">
 * 			<Symptom value="yes">ccm_ask_initial_assessment_red_eyes_duration_four_days_symptom_id</Symptom>		<!-- Red Eyes for 4 Days or more : YES -->
 * 		</SymptomRule>
 * </Classification>
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
	private String identifier;
	private String type;
	private String ccmTreatmentDisplayName;
	private int priority;
	private List<SymptomRule> symptomRules;
	private List<ClassificationRule> classificationRules;

	/**
	 * Constructor
	 */
	public Classification() {
		setSymptomRules(new ArrayList<SymptomRule>());
		setClassificationRules(new ArrayList<ClassificationRule>());
	}

	/**
	 * Constructor
	 */
	public Classification(String name, String type) {
		this();
		setName(name);
		setType(type);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + 
				((name == null) ? 0 : name.hashCode()) + 
				((type == null) ? 0 : type.hashCode()) +
				((ccmTreatmentDisplayName == null) ? 0 : ccmTreatmentDisplayName.hashCode());
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
	 * Getter Method: getCcmTreatmentDisplayName()
	 */
	public String getCcmTreatmentDisplayName() {
		return ccmTreatmentDisplayName;
	}

	/**
	 * Setter Method: setCcmTreatmentDisplayName()
	 */
	public void setCcmTreatmentDisplayName(String ccmTreatmentDisplayName) {
		this.ccmTreatmentDisplayName = ccmTreatmentDisplayName;
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
