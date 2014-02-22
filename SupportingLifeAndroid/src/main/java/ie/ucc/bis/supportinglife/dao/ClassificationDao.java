package ie.ucc.bis.supportinglife.dao;

import java.util.List;

import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.service.SupportingLifeService;

/**
 * Class: ClassificationDao
 * 
 * This class maintains the database connection and supports 
 * adding new classifications and fetching classifications related
 * to patient assessments.
 * 
 * @author TOSullivan
 */
public interface ClassificationDao {
	
	public void createPatientClassifications(PatientAssessment patientToAdd, String uniquePatientAssessmentIdentifier, SupportingLifeService service);

	public void populatePatientClassifications(List<PatientAssessmentComms> patientAssessmentComms, SupportingLifeService service);
}
