package ie.ucc.bis.supportinglife.dao;

import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.service.SupportingLifeService;

import java.util.List;

public interface PatientAssessmentDao {
	
	public PatientAssessment createPatientAssessment(PatientAssessment patientToAdd, String android_device_id, SupportingLifeService service);
	public void deletePatientAssessment(PatientAssessment patient, SupportingLifeService service);
	public List<PatientAssessment> getAllNonSyncedPatientAssessments(SupportingLifeService service);
	public List<PatientAssessment> getAllPatientAssessments(SupportingLifeService service);
}
