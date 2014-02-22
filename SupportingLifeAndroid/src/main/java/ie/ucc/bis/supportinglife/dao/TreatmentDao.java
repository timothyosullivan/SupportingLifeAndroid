package ie.ucc.bis.supportinglife.dao;

import java.util.List;

import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.service.SupportingLifeService;

/**
 * Class: TreatmentDao
 * 
 * This class maintains the database connection and supports 
 * adding new treatments and fetching treatments related
 * to patient assessments.
 * 
 * @author TOSullivan
 */
public interface TreatmentDao {
	
	public void createPatientTreatments(PatientAssessment patientToAdd, String uniquePatientAssessmentIdentifier, SupportingLifeService service);

	public void populatePatientTreatments(List<PatientAssessmentComms> patientAssessmentComms, SupportingLifeService service);
}
