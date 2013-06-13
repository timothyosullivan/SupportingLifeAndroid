package ie.ucc.bis.domain;

import java.io.Serializable;


public class Patient implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long patientId;
	
	private String firstName;

	private String surname;	
	
	public Patient() {}

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Patient(String firstName, String surname) {
		this.setFirstName(firstName);
		this.setSurname(surname);
	}	
	
	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Patient(long patientId, String firstName, String surname) {
		this.setPatientId(patientId);
		this.setFirstName(firstName);
		this.setSurname(surname);
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}	
}
