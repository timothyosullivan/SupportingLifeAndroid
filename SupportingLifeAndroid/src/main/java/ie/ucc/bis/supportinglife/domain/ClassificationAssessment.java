package ie.ucc.bis.supportinglife.domain;

import java.io.Serializable;

/**
 * 
 * @author timothyosullivan
 */

public class ClassificationAssessment implements Serializable {
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 6646226083185156628L;
	
	private int id;
	private String deviceGeneratedAssessmentId;
	private String classificationIdentifier;
	private String classificationName;

	
	public ClassificationAssessment(Integer id, String deviceGeneratedAssessmentId, String classificationIdentifier, String classificationName) {
		setId(id);
		setDeviceGeneratedAssessmentId(deviceGeneratedAssessmentId);
		setClassificationIdentifier(classificationIdentifier);
		setClassificationName(classificationName);
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

	public String getClassificationIdentifier() {
		return classificationIdentifier;
	}

	public void setClassificationIdentifier(String classificationIdentifier) {
		this.classificationIdentifier = classificationIdentifier;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

}
