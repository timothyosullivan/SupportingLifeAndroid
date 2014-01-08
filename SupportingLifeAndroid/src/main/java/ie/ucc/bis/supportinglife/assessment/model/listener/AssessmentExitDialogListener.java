package ie.ucc.bis.supportinglife.assessment.model.listener;

import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import android.content.DialogInterface;

/**
 * Class: AssessmentExitDialogListener
 * 
 * Provides OnClick handler functionality for Home Button
 * and Back Button click event on an assessment activity
 * 
 * 		i.e. ImciAssessmentActivity / 
 * 			 CcmAssessmentActivity /
 * 			 ImciAssessmentResultsActivity /
 * 			 CcmAssessmentResultsActivity
 * 
 * @author TOSullivan
 *
 */
public final class AssessmentExitDialogListener implements DialogInterface.OnClickListener {

	private SupportingLifeBaseActivity supportingLifeBaseActivity;

	/**
	 * Constructor
	 */
	public AssessmentExitDialogListener(SupportingLifeBaseActivity supportingLifeBaseActivity) {
		this.supportingLifeBaseActivity = supportingLifeBaseActivity;
	}

	public void onClick(DialogInterface dialog, int which) {
		supportingLifeBaseActivity.goHome(supportingLifeBaseActivity);
	}
	
} // end of class