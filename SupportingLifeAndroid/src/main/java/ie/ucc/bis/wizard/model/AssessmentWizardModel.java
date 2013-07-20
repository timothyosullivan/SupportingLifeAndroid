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
		/*
		 * Assessment Wizard Pages are as follows:
		 * 
		 * 1. General Patient Details Page
		 * 2. General Danger Signs Page
		 * 3. Cough / Breathing Assessment Page
		 */
//		return new PageList(new GeneralPatientDetailsPage(this, 
//									getApplicationContext().getResources().getString(R.string.general_patient_details_title)).setRequired(true),
//							new GeneralDangerSignsPage(this, 
//									getApplicationContext().getResources().getString(R.string.general_danger_signs_title)).setRequired(true));

//		return new PageList(new GeneralPatientDetailsPage(this, 
//				getApplicationContext().getResources().getString(R.string.general_patient_details_title)).setRequired(true),
//		new GeneralDangerSignsPage(this, 
//				getApplicationContext().getResources().getString(R.string.general_danger_signs_title)).setRequired(true),
//		new BreathingAssessmentPage(this, 
//				getApplicationContext().getResources().getString(R.string.breathing_assessment_title)).setRequired(true));				
		
		return new PageList(new GeneralPatientDetailsPage(this, 
				"Patient Details").setRequired(true),
		new GeneralDangerSignsPage(this, 
				"Danger Signs").setRequired(true),
		new BreathingAssessmentPage(this, 
				"Breathing Assessment").setRequired(true));		
	}
}
