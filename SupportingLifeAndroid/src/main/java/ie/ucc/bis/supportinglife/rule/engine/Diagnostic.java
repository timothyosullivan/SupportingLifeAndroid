package ie.ucc.bis.supportinglife.rule.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates a diagnostic element of the 
 * medical results of a patient assessment. This diagnostic
 * element incorporates the following:
 * 
 * - Classification
 * - Corresponding Treatment Recommendations(s)
 * 
 * @author timothyosullivan
 */
public class Diagnostic implements Serializable {

	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -3354248616709394162L;
	
	private Classification classification;
	private boolean treatmentHeader;
	private boolean treatmentFooter;
	private List<TreatmentRecommendation> treatmentRecommendations;
	
	/**
	 * Constructor
	 */
	public Diagnostic() {
		setTreatmentHeader(false);
		setTreatmentFooter(false);
		setTreatmentRecommendations(new ArrayList<TreatmentRecommendation>());
	}

	/**
	 * Constructor
	 */
	public Diagnostic(Classification classification) {
		this();
		setClassification(classification);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((classification == null) ? 0 : classification.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Diagnostic))
			return false;
		Diagnostic other = (Diagnostic) obj;
		if (classification == null) {
			if (other.classification != null)
				return false;
		} else if (!classification.equals(other.classification))
			return false;
		return true;
	}

	/**
	 * Getter Method: getClassification()
	 */
	public Classification getClassification() {
		return classification;
	}

	/**
	 * Setter Method: setClassification()
	 */
	public void setClassification(Classification classification) {
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
	 * Getter Method: getTreatmentRecommendations()
	 */
	public List<TreatmentRecommendation> getTreatmentRecommendations() {
		return treatmentRecommendations;
	}

	/**
	 * Setter Method: setTreatmentRecommendations()
	 */
	public void setTreatmentRecommendations(List<TreatmentRecommendation> treatmentRecommendations) {
		this.treatmentRecommendations = treatmentRecommendations;
	}
}
