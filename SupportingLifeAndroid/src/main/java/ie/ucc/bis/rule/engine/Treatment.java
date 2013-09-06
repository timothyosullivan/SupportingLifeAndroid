package ie.ucc.bis.rule.engine;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Class: Treatment
 * 
 * Encapsulates a Treatment as defined by 
 * treatment_rules.xml e.g.
 * 
 * <Treatment>
 * 		<CriteriaList rule="any">
 * 			<SymptomCriteria value="some">malnutrition_assessment_palmar_pallor_symptom_id</SymptomCriteria>
 * 			<SymptomCriteria value="severe">malnutrition_assessment_palmar_pallor_symptom_id</SymptomCriteria>
 * 		</CriteriaList>
 * 		<CriteriaList rule="all">
 * 			<SymptomCriteria value="high malaria risk">fever_assessment_malaria_risk_symptom_id</SymptomCriteria>
 * 		</CriteriaList>
 * 		<Recommendation>Give Oral Antimalarial</Recommendation>
 * </Treatment>
 * 
 * @author TOSullivan
 *
 */

public class Treatment implements Serializable {

	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 2969585100135738023L;

	private List<TreatmentCriterion> treatmentCriterion;
	private String recommendation;

 
	/**
	 * Constructor
	 * 
	 */
	public Treatment() {
		setTreatmentCriterion(new ArrayList<TreatmentCriterion>());
	}
		

	/**
	 * Getter Method: getTreatmentCriterion()
	 */
	public List<TreatmentCriterion> getTreatmentCriterion() {
		return treatmentCriterion;
	}

	/**
	 * Setter Method: setTreatmentCriterion()
	 */
	public void setTreatmentCriterion(List<TreatmentCriterion> treatmentCriterion) {
		this.treatmentCriterion = treatmentCriterion;
	}

	/**
	 * Getter Method: getRecommendation()
	 */
	public String getRecommendation() {
		return recommendation;
	}

	/**
	 * Setter Method: setRecommendation()
	 */
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	/**
	 * 
	 * Provides debug output of a treatment
	 * 
	 */
	public String debugOutput() {
		StringBuffer debugOutput = new StringBuffer();

		debugOutput.append("------------------------------------ \n");

		for (TreatmentCriterion treatmentCriterion : getTreatmentCriterion()) {
			debugOutput.append(treatmentCriterion.debugOutput());
		}

		debugOutput.append("Recommendation: " + getRecommendation() + "\n");
		
		return debugOutput.toString();
	}

}
