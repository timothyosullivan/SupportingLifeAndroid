package ie.ucc.bis.supportinglife.ui.utilities;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.domain.Patient;

import java.util.List;

import android.content.res.Resources;

public class PatientHandlerUtils {

    public static Patient populateCcmPatientDetails(Resources resources, List<ReviewItem> reviewItems) {
    	Patient patient = new Patient();
    	
    	for (ReviewItem reviewItem : reviewItems) {
    		if (reviewItem.getSymptomId() != null && reviewItem.getSymptomId().equalsIgnoreCase(resources.getString(R.string.ccm_general_patient_details_gender_symptom_id))) {
    			patient.setGender(reviewItem.getDisplayValue());
    		}
    	}
    	
    	return patient;
    }
	
}
