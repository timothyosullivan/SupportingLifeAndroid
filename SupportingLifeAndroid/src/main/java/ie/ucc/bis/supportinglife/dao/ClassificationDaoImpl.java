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

	protected static final String TABLE_CLASSIFICATION_COLUMN_ID = "_id";
	protected static final String TABLE_CLASSIFICATION_COLUMN_ASSESSMENT_ID = "assessment_id";
	protected static final String TABLE_CLASSIFICATION_COLUMN_CLASSIFICATION_IDENTIFIER = "identifier";
	protected static final String TABLE_CLASSIFICATION_COLUMN_CLASSIFICATION_NAME = "name";
	
	private String[] allColumns = { ClassificationTable.TABLE_CLASSIFICATION_COLUMN_ID,
									ClassificationTable.TABLE_CLASSIFICATION_COLUMN_ASSESSMENT_ID,
									ClassificationTable.TABLE_CLASSIFICATION_COLUMN_CLASSIFICATION_IDENTIFIER,
									ClassificationTable.TABLE_CLASSIFICATION_COLUMN_CLASSIFICATION_NAME};

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
