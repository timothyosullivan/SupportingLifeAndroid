package ie.ucc.bis.supportinglife.domain;

import java.io.Serializable;

/**
 * 
 * @author timothyosullivan
 */

public class TreatmentAssessment implements Serializable {
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 3705492409992405517L;

	
	private int id;
	private String deviceGeneratedAssessmentId;
	private String treatmentIdentifier;
	private String treatmentDescription;

	
	public TreatmentAssessment(Integer id, String deviceGeneratedAssessmentId, String treatmentIdentifier, String treatmentDescription) {
		setId(id);
		setDeviceGeneratedAssessmentId(deviceGeneratedAssessmentId);
		setTreatmentIdentifier(treatmentIdentifier);
		setTreatmentDescription(treatmentDescription);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceGeneratedAssessmentId() {
		return deviceGeneratedAssessmentId;
	}

	public void setDeviceGeneratedAssessmentId(String deviceGeneratedAssessmentId) {
		this.deviceGeneratedAssessmentId = deviceGeneratedAssessmentId;
	}

	public String getTreatmentIdentifier() {
		return treatmentIdentifier;
	}

	public void setTreatmentIdentifier(String treatmentIdentifier) {
		this.treatmentIdentifier = treatmentIdentifier;
	}

	public String getTreatmentDescription() {
		return treatmentDescription;
	}

	public void setTreatmentDescription(String treatmentDescription) {
		this.treatmentDescription = treatmentDescription;
	}
}
