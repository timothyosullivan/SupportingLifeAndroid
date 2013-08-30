package ie.ucc.bis.rule.engine;


import java.io.Serializable;


/**
 * Class: Treatment
 * 
 * Encapsulates a Treatment as defined by 
 * treatment_rules.xml e.g.
 * 
 * <Treatment>
 * 		<CriteriaList rule="all">
 * 			<SymptomCriteria value="yes">diarrhoea_assessment_cholera_in_area_symptom_id</SymptomCriteria>
 * 			<TreatmentCriterion value="yes">treatment_criteria_patient_is_two_years_or_older</TreatmentCriterion>
 * 		</CriteriaList>
 * 		<Recommendation>Give antibiotic for cholera</Recommendation>
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

	private TreatmentCriterion treatmentCriterion;
	private String recommendation;

 
	/**
	 * Constructor
	 * 
	 */
	public Treatment() {
		setTreatmentCriterion(new TreatmentCriterion());
	}
		

	/**
	 * Getter Method: getTreatmentCriterion()
	 */
	public TreatmentCriterion getTreatmentCriterion() {
		return treatmentCriterion;
	}

	/**
	 * Setter Method: setTreatmentCriterion()
	 */
	public void setTreatmentCriterion(TreatmentCriterion treatmentCriterion) {
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

		debugOutput.append(getTreatmentCriterion().debugOutput());
		debugOutput.append("Recommendation: " + getRecommendation() + "\n");
		
		return debugOutput.toString();
	}

}
