package ie.ucc.bis.domain;

public class Patient {

	private long patientId;
	private String name;
	
	public Patient() {
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Patient(long patientId, String name) {
		this.setPatientId(patientId);
		this.setName(name);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	
	
}
