package ie.ucc.bis.supportinglife.communication;

import java.io.Serializable;

/**
 * Patient Assessment Response Entity
 *  - Will be returned to device upon addition of patient assessment record
 * 
 * @author timothyosullivan
 */

public class PatientAssessmentResponseComms implements Serializable {
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -3142311331511726120L;

	// patient assessment identifiers
	private String deviceGeneratedAssessmentId;
	private Long patientVisitId;
	private Long patientId;
	
	// patient identifiers
	private String nationalId;
	private String nationalHealthId;
	private String childFirstName;
	private String childSurname;

	/**
	 * Constructor
	 */
	public PatientAssessmentResponseComms() {}
	
	/**
	 * Constructor
	 */
	public PatientAssessmentResponseComms(String deviceGeneratedAssessmentId, Long patientVisitId, Long patientId, 
							String nationalId, String nationalHealthId, String childFirstName, String childSurname) {
		setDeviceGeneratedAssessmentId(deviceGeneratedAssessmentId);
		setPatientVisitId(patientVisitId);
		setPatientId(patientId);
		setNationalId(nationalId);
		setNationalHealthId(nationalHealthId);
		setChildFirstName(childFirstName);
		setChildSurname(childSurname);
	}

	public String getDeviceGeneratedAssessmentId() {
		return deviceGeneratedAssessmentId;
	}

	public void setDeviceGeneratedAssessmentId(String deviceGeneratedAssessmentId) {
		this.deviceGeneratedAssessmentId = deviceGeneratedAssessmentId;
	}

	public Long getPatientVisitId() {
		return patientVisitId;
	}

	public void setPatientVisitId(Long patientVisitId) {
		this.patientVisitId = patientVisitId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getNationalHealthId() {
		return nationalHealthId;
	}

	public void setNationalHealthId(String nationalHealthId) {
		this.nationalHealthId = nationalHealthId;
	}

	public String getChildFirstName() {
		return childFirstName;
	}

	public void setChildFirstName(String childFirstName) {
		this.childFirstName = childFirstName;
	}

	public String getChildSurname() {
		return childSurname;
	}

	public void setChildSurname(String childSurname) {
		this.childSurname = childSurname;
	}


}
