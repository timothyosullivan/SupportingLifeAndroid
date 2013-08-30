package ie.ucc.bis.domain;

import ie.ucc.bis.rule.engine.Classification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Patient implements Serializable {
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 7692081022011255176L;
	
	
	private long patientId;
	private String firstName;
	private String surname;
	private List<Classification> classifications;
	
	public Patient() {
		setClassifications(new ArrayList<Classification>());
	}

	/**
	 * Constructor
	 * 
	 * @param firstName
	 * @param surname
	 */
	public Patient(String firstName, String surname) {
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
		this.setPatientId(patientId);
		this.setFirstName(firstName);
		this.setSurname(surname);
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
	 * Getter Method: getClassifications()
	 */
	public List<Classification> getClassifications() {
		return classifications;
	}

	/**
	 * Setter Method: setClassifications()
	 */
	public void setClassifications(List<Classification> classifications) {
		this.classifications = classifications;
	}	
}
