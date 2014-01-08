package ie.ucc.bis.supportinglife.rule.engine;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Class: TreatmentRule
 * 
 * Encapsulates a Treatment Rule as defined by 
 * treatment_rules.xml e.g.
 * 
 * <TreatmentRule>
 * 		<Type>SEVERE</Type>
 * 		<Classification>Severe Dehydration</Classification>
 * 		<Treatment>
 * 			<CriteriaList rule="all">
 * 				<TreatmentCriterion value="no">imci_treatment_criteria_severe_dehydration_is_only_severe_classification</TreatmentCriterion>
 * 			</CriteriaList>
 * 			<Recommendation>Refer URGENTLY to Hospital with mother giving frequent sips of ORS</Recommendation>
 * 		</Treatment>
 * 		<Treatment>
 * 			<CriteriaList rule="all">
 * 				<TreatmentCriterion value="yes">imci_treatment_criteria_severe_dehydration_is_only_severe_classification</TreatmentCriterion>
 * 			</CriteriaList>
 * 			<Recommendation>Give fluid for severe dehydration (Plan C)</Recommendation>
 * 		</Treatment>
 * 		<Treatment>
 * 			<CriteriaList rule="all">
 * 				<SymptomCriteria value="yes">diarrhoea_assessment_cholera_in_area_symptom_id</SymptomCriteria>
 * 				<TreatmentCriterion value="yes">treatment_criteria_patient_is_two_years_or_older</TreatmentCriterion>
 * 			</CriteriaList>
 * 			<Recommendation>Give antibiotic for cholera</Recommendation>
 * 		</Treatment>
 * </TreatmentRule>
 * 
 * @author TOSullivan
 *
 */

public class TreatmentRule implements Serializable {

	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 2969585100135738023L;

	private String classification;
	private boolean treatmentHeader;
	private boolean treatmentFooter;
	private List<Treatment> treatments;
 
	/**
	 * Constructor
	 */
	public TreatmentRule() {
		setTreatments(new ArrayList<Treatment>());
	}
		
	/**
	 * Getter Method: getClassification()
	 */
	public String getClassification() {
		return classification;
	}

	/**
	 * Setter Method: setClassification()
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	/**
	 * Getter Method: isTreatmentHeader()
	 */
	public boolean isTreatmentHeader() {
		return treatmentHeader;
	}

	/**
	 * Setter Method: setTreatmentHeader()
	 */
	public void setTreatmentHeader(boolean treatmentHeader) {
		this.treatmentHeader = treatmentHeader;
	}

	/**
	 * Getter Method: isTreatmentFooter()
	 */
	public boolean isTreatmentFooter() {
		return treatmentFooter;
	}

	/**
	 * Setter Method: setTreatmentFooter()
	 */
	public void setTreatmentFooter(boolean treatmentFooter) {
		this.treatmentFooter = treatmentFooter;
	}

	/**
	 * Getter Method: getTreatments()
	 */
	public List<Treatment> getTreatments() {
		return treatments;
	}

	/**
	 * Setter Method: setTreatments()
	 */
	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}

	/**
	 * Provides debug output of a treatment rule
	 */
	public String debugOutput() {
		StringBuilder debugOutput = new StringBuilder();

		debugOutput.append("------------------------------------ \n");
		debugOutput.append("Classification: " + getClassification() + "\n");
		
		if (getTreatments().size() != 0) {
			for (Treatment treatment : getTreatments()) {
				debugOutput.append(treatment.debugOutput());
			}
		}		
		return debugOutput.toString();
	}
}
