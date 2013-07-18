package ie.ucc.bis.wizard.model;

import ie.ucc.bis.R;
import android.content.Context;

public class AssessmentWizardModel extends AbstractWizardModel {

	/**
	 * Constructor
	 * 
	 * @param context Context
	 */
	public AssessmentWizardModel(Context context) {
		super(context);
	}

	@Override
	protected PageList configurePageList() {
		return new PageList(new GeneralPatientDetailsPage(this, 
				getApplicationContext().getResources().getString(R.string.general_patient_details_title)).setRequired(true),
							new GeneralDangerSignsPage(this, 
									getApplicationContext().getResources().getString(R.string.general_danger_signs_title)).setRequired(true));
	}
}
