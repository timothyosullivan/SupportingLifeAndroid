package ie.ucc.bis.domain;

import ie.ucc.bis.rule.engine.Diagnostic;
import ie.ucc.bis.rule.engine.Symptom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author timothyosullivan
 */

public class Patient implements Serializable {
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 7692081022011255176L;
	
	
	private long patientId;
	private String firstName;
	private String surname;
	private List<Symptom> symptoms;
	private List<Diagnostic> diagnostics;
	
	public Patient() {
		setSymptoms(new ArrayList<Symptom>());
		setDiagnostics(new ArrayList<Diagnostic>());
	}

	/**
	 * Constructor
	 * 
	 * @param firstName
	 * @param surname
	 */
	public Patient(String firstName, String surname) {
		this();
		this.setFirstName(firstName);
		this.setSurname(surname);
	}	
	
	/**
	 * Constructor
	 * 
	 * @param patientId
	 * @param firstName
	 * @param surname
	 */
	public Patient(long patientId, String firstName, String surname) {
		this(firstName, surname);
		this.setPatientId(patientId);
	}

	/**
	 * Getter Method: getPatientId()
	 */
	public long getPatientId() {
		return patientId;
	}

	/**
	 * Setter Method: setPatientId()
	 */
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	
	/**
	 * Getter Method: getFirstName()
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter Method: setFirstName()
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter Method: getSurname()
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Setter Method: setSurname()
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Getter Method: getSymptoms()
	 */
	public List<Symptom> getSymptoms() {
		return symptoms;
	}

	/**
	 * Setter Method: setSymptoms()
	 */
	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	/**
	 * Getter Method: getDiagnostics()
	 */
	public List<Diagnostic> getDiagnostics() {
		return diagnostics;
	}

	/**
	 * Setter Method: setDiagnostics()
	 */
	public void setDiagnostics(List<Diagnostic> diagnostics) {
		this.diagnostics = diagnostics;
	}	
}
