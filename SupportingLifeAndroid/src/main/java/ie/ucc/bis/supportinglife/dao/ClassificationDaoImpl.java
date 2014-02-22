package ie.ucc.bis.supportinglife.dao;

import ie.ucc.bis.supportinglife.domain.ClassificationAssessment;
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
public class ClassificationDaoImpl implements ClassificationDao {
	
	private final String LOG_TAG = "ie.ucc.bis.supportinglife.dao.ClassificationDaoImpl";
	
	private String[] allColumns = { ClassificationTable.COLUMN_ID,
									ClassificationTable.COLUMN_ASSESSMENT_ID,
									ClassificationTable.COLUMN_CLASSIFICATION_IDENTIFIER,
									ClassificationTable.COLUMN_CLASSIFICATION_NAME};

	public ClassificationDaoImpl() {
	}

	/**
	 * Responsible for adding 'Classification' record to the database on the 
	 * android device
	 * 
	 * @param classificationToAdd
	 * @param service - SupportingLifeService
	 * 
	 * @return
	 */
	@Override
	public ClassificationAssessment createClassificationAssessment(ClassificationAssessment classificationToAdd, SupportingLifeService service) {
		// TODO Auto-generated method stub
		return null;
	}
} 
