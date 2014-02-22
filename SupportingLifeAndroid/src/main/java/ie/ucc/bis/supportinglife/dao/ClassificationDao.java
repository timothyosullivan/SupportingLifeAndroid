package ie.ucc.bis.supportinglife.dao;

import ie.ucc.bis.supportinglife.domain.ClassificationAssessment;
import ie.ucc.bis.supportinglife.service.SupportingLifeService;

public interface ClassificationDao {
	
	public ClassificationAssessment createClassificationAssessment(ClassificationAssessment classificationToAdd, SupportingLifeService service);
}
