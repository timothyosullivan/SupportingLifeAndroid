package ie.ucc.bis.wizard.model;

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
		return new PageList(new GeneralPatientDetailsPage(this, "Patient Details").setRequired(true),
							new GeneralPatientDetailsPage(this, "Second Info").setRequired(true));
	}
}
