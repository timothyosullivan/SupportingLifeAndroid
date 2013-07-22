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
		 * 4. Diarrhoea Assessment Page
		 * 5. Fever Assessment Page
		 */	
		return new PageList(new GeneralPatientDetailsPage(this, 
				"Patient Details").setRequired(true),
		new GeneralDangerSignsPage(this, 
				"Danger Signs").setRequired(true),
		new BreathingAssessmentPage(this, 
				"Breathing Assessment").setRequired(true),
		new DiarrhoeaAssessmentPage(this,
				"Diarrhoea Assessment").setRequired(true),
		new FeverAssessmentPage(this, 
				"Fever Assessment").setRequired(true));		
	}
}
