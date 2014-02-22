package ie.ucc.bis.supportinglife.dao;

import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.domain.ClassificationAssessment;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;
import ie.ucc.bis.supportinglife.service.SupportingLifeService;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

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

	protected static final String COLUMN_ID = "_id";
	protected static final String COLUMN_ASSESSMENT_ID = "assessment_id";
	protected static final String COLUMN_CLASSIFICATION_IDENTIFIER = "identifier";
	protected static final String COLUMN_CLASSIFICATION_NAME = "name";
	
	
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
	public void createPatientClassifications(PatientAssessment patientToAdd, String uniquePatientAssessmentIdentifier, SupportingLifeService service) {
		// show the number of classifications in debug logger
		debugOutputShowClassificationCount(service);

		ContentValues values = new ContentValues();
		
		for (Diagnostic diagnostic : patientToAdd.getDiagnostics()){
			
			// ** Need to ignore those classifications which were added
			//    to just hold CCM-related Classification Type treatments
			//    (i.e. Treatment Header / Treatment Footer)
			if (diagnostic.getClassification().getIdentifier() != null) {
				values.put(ClassificationTable.COLUMN_ASSESSMENT_ID, uniquePatientAssessmentIdentifier);
				values.put(ClassificationTable.COLUMN_CLASSIFICATION_IDENTIFIER, diagnostic.getClassification().getIdentifier());
				values.put(ClassificationTable.COLUMN_CLASSIFICATION_NAME, diagnostic.getClassification().getName());	
			
				// add the patient classification row
				long insertId = service.getDatabase().insert(ClassificationTable.TABLE_CLASSIFICATION, null, values);
				
				LoggerUtils.i(LOG_TAG, "Patient Assessment Classification Added: " + insertId);
			}
		}
		
		// show the number of classifications in debug logger
		debugOutputShowClassificationCount(service);
	}
	
	/**
	 * Responsible for populating the 'patient assessments' received with
	 * their associated classifications from the 'classification' table
	 * 
	 * @param patientAssessments - List<PatientAssessment>
	 * @param service - SupportingLifeService
	 * 
	 * @return
	 */
	@Override
	public void populatePatientClassifications(List<PatientAssessmentComms> patientAssessmentComms, SupportingLifeService service) {
		Cursor cursor = null;
		
		// iterate over all of the patient assessments that we have to retrieve
		// for which we have to retrieve classifications
		for (PatientAssessmentComms patientAssessmentComm : patientAssessmentComms) {
			cursor = service.getDatabase().query(ClassificationTable.TABLE_CLASSIFICATION,
					allColumns, ClassificationTable.COLUMN_ASSESSMENT_ID + " = '" + patientAssessmentComm.getDeviceGeneratedAssessmentId() + "'", 
					null, null, null, null);
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				ClassificationAssessment classificationAssessment = cursorToClassificationAssessment(cursor);
				
				// create a diagnostic element to hold this classification
				patientAssessmentComm.getClassifications().put(classificationAssessment.getClassificationIdentifier(), classificationAssessment.getClassificationName());
				cursor.moveToNext();
			}
		}
		
		// make sure to close the cursor
		cursor.close();
	}
	
	private ClassificationAssessment cursorToClassificationAssessment(Cursor cursor) {

		//Param Line 1: Integer id (0), String deviceGeneratedAssessmentId (1), String classificationIdentifier (2), String classificationName (3)
	
		ClassificationAssessment classificationAssessment = new ClassificationAssessment(DaoUtilities.readInt(cursor, 0), 
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		
		return classificationAssessment;
	}

	
	
	/**
	 * Show the number of classifications in debug logger
	 * 
	 * @param service
	 */
	private void debugOutputShowClassificationCount(SupportingLifeService service) {
		SQLiteStatement assessmentRowCountQuery = service.getDatabase().compileStatement("select count(*) from " + ClassificationTable.TABLE_CLASSIFICATION);
		long assessmentRowCount = assessmentRowCountQuery.simpleQueryForLong();
		
		LoggerUtils.i(LOG_TAG, "Current Classification Row Count: " + assessmentRowCount);
	}

} 
