package ie.ucc.bis.supportinglife.dao;

import ie.ucc.bis.supportinglife.communication.PatientAssessmentComms;
import ie.ucc.bis.supportinglife.domain.PatientAssessment;
import ie.ucc.bis.supportinglife.domain.TreatmentAssessment;
import ie.ucc.bis.supportinglife.helper.PatientHandlerUtils;
import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;
import ie.ucc.bis.supportinglife.rule.engine.TreatmentRecommendation;
import ie.ucc.bis.supportinglife.service.SupportingLifeService;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

public class TreatmentDaoImpl implements TreatmentDao {
	
	private final String LOG_TAG = "ie.ucc.bis.supportinglife.dao.TreatmentDaoImpl";
	
	private String[] allColumns = { TreatmentTable.COLUMN_ID,
									TreatmentTable.COLUMN_ASSESSMENT_ID,
									TreatmentTable.COLUMN_TREATMENT_IDENTIFIER,
									TreatmentTable.COLUMN_TREATMENT_DESCRIPTION};

	public TreatmentDaoImpl() {}

	/**
	 * Responsible for adding 'Treatment' record to the database on the 
	 * android device
	 * 
	 * @param classificationToAdd
	 * @param service - SupportingLifeService
	 * 
	 * @return
	 */
	@Override
	public void createPatientTreatments(PatientAssessment patientToAdd, String uniquePatientAssessmentIdentifier, SupportingLifeService service) {
		// show the number of treatments in debug logger
		debugOutputShowTreatmentCount(service);

		ContentValues values = new ContentValues();
		
		for (Diagnostic diagnostic : patientToAdd.getDiagnostics()){
			for (TreatmentRecommendation treatmentRecommendation : diagnostic.getTreatmentRecommendations()) {
				values.put(TreatmentTable.COLUMN_ASSESSMENT_ID, uniquePatientAssessmentIdentifier);
				values.put(TreatmentTable.COLUMN_TREATMENT_IDENTIFIER, treatmentRecommendation.getTreatmentIdentifier());
				values.put(TreatmentTable.COLUMN_TREATMENT_DESCRIPTION, treatmentRecommendation.getTreatmentDescription());	
				
				// add the patient 'recommended treatment' row
				long insertId = service.getDatabase().insert(TreatmentTable.TABLE_TREATMENT, null, values);
					
				LoggerUtils.i(LOG_TAG, "Patient Assessment Treatment Added: " + insertId);
			}
		}
		// show the number of treatments in debug logger
		debugOutputShowTreatmentCount(service);
	}
	
	/**
	 * Responsible for populating the 'patient assessments' received with
	 * their associated treatments from the 'treatment' table
	 * 
	 * @param patientAssessments - List<PatientAssessment>
	 * @param service - SupportingLifeService
	 * 
	 * @return
	 */
	@Override
	public void populatePatientTreatments(List<PatientAssessmentComms> patientAssessmentComms, SupportingLifeService service) {
		Cursor cursor = null;
		
		// iterate over all of the patient assessments that we have to retrieve
		// for which we have to retrieve treatments
		for (PatientAssessmentComms patientAssessmentComm : patientAssessmentComms) {
			cursor = service.getDatabase().query(TreatmentTable.TABLE_TREATMENT,
					allColumns, TreatmentTable.COLUMN_ASSESSMENT_ID + " = '" + patientAssessmentComm.getDeviceGeneratedAssessmentId() + "'", 
					null, null, null, null);
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				TreatmentAssessment treatmentAssessment = cursorToTreatmentAssessment(cursor);
				
				// capture this treatment
				patientAssessmentComm.getTreatments().put(treatmentAssessment.getTreatmentIdentifier(), 
														  PatientHandlerUtils.removeEscapeCharacters(treatmentAssessment.getTreatmentDescription()));
				cursor.moveToNext();
			}
		}
		
		// make sure to close the cursor
		if (cursor != null) {
			cursor.close();
		}
	}
	
	private TreatmentAssessment cursorToTreatmentAssessment(Cursor cursor) {

		//Param Line 1: Integer id (0), String deviceGeneratedAssessmentId (1), String treatmentIdentifier (2), String treatmentDescription (3)
	
		TreatmentAssessment treatmentAssessment = new TreatmentAssessment(DaoUtilities.readInt(cursor, 0), 
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		
		return treatmentAssessment;
	}

	
	
	/**
	 * Show the number of treatments in debug logger
	 * 
	 * @param service
	 */
	private void debugOutputShowTreatmentCount(SupportingLifeService service) {
		SQLiteStatement assessmentRowCountQuery = service.getDatabase().compileStatement("select count(*) from " + TreatmentTable.TABLE_TREATMENT);
		long assessmentRowCount = assessmentRowCountQuery.simpleQueryForLong();
		
		LoggerUtils.i(LOG_TAG, "Current Treatment Row Count: " + assessmentRowCount);
	}

} 
