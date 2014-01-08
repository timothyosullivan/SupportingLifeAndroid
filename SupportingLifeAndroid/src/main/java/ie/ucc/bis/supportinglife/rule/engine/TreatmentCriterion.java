package ie.ucc.bis.supportinglife.rule.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Class: TreatmentCriterion
 * 
 * Encapsulates treatment criteria for a particular treatment
 * as specified in treatment_rules.xml e.g.
 * 
 * <CriteriaList rule="all">
 * 		<SymptomCriteria value="yes">diarrhoea_assessment_cholera_in_area_symptom_id</SymptomCriteria>
 * 		<TreatmentCriteria value="yes">treatment_criteria_patient_is_two_years_or_older</TreatmentCriterion>
 * </CriteriaList>
 * 
 * @author TOSullivan
 *
 */

public class TreatmentCriterion implements Serializable {

	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 2175782496476894436L;
	
	private String rule;
	private List<Symptom> symptomCriteria;
	private List<TreatmentCriteria> treatmentCriteria;
	
	/**
	 * Constructor
	 * 
	 */	
	public TreatmentCriterion() {
		setSymptomCriteria(new ArrayList<Symptom>());
		setTreatmentCriteria(new ArrayList<TreatmentCriteria>());
	}
	
	/**
	 * Constructor
	 * 
	 */	
	public TreatmentCriterion(String rule) {
		setRule(rule);
		setSymptomCriteria(new ArrayList<Symptom>());
		setTreatmentCriteria(new ArrayList<TreatmentCriteria>());
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
	 * Getter Method: getSymptomCriteria()
	 */
	public List<Symptom> getSymptomCriteria() {
		return symptomCriteria;
	}

	/**
	 * Setter Method: setSymptomCriteria()
	 */
	public void setSymptomCriteria(List<Symptom> symptomCriteria) {
		this.symptomCriteria = symptomCriteria;
	}

	/**
	 * Getter Method: getTreatmentCriteria()
	 */
	public List<TreatmentCriteria> getTreatmentCriteria() {
		return treatmentCriteria;
	}

	/**
	 * Setter Method: setTreatmentCriteria()
	 */
	public void setTreatmentCriteria(List<TreatmentCriteria> treatmentCriteria) {
		this.treatmentCriteria = treatmentCriteria;
	}

	/**
	 * 
	 * Provides debug output of symptom rule
	 * 
	 */
	public String debugOutput() {
		StringBuilder debugOutput = new StringBuilder();

		debugOutput.append("Rule: " + getRule() + "\n");
		for (Symptom symptom : getSymptomCriteria()) {
			debugOutput.append(symptom.debugOutput());
		}
		
		for (TreatmentCriteria treatmentCriteria : getTreatmentCriteria()) {
			debugOutput.append(treatmentCriteria.debugOutput());
		}
				
		return debugOutput.toString();
	}
	
	
	
}
