package ie.ucc.bis.wizard.model;

import android.content.Context;

public class AssessmentWizardModel extends AbstractWizardModel {

	private static final String GENERAL_PATIENT_DETAILS_PAGE_TITLE = "Patient Details";
	private static final String DANGER_SIGNS_PAGE_TITLE = "Danger Signs";
	private static final String BREATHING_ASSESSMENT_PAGE_TITLE = "Breathing Assessment";
	private static final String DIARRHOEA_ASSESSMENT_PAGE_TITLE = "Diarrhoea Assessment";
	private static final String FEVER_ASSESSMENT_PAGE_TITLE = "Fever Assessment";
	private static final String EAR_ASSESSMENT_PAGE_TITLE = "Ear Assessment";
	
	
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
		 * 6. Ear Assessment Page
		 */	
		return new PageList(new GeneralPatientDetailsPage(this, 
				GENERAL_PATIENT_DETAILS_PAGE_TITLE).setRequired(true),
		new GeneralDangerSignsPage(this, 
				DANGER_SIGNS_PAGE_TITLE).setRequired(true),
		new BreathingAssessmentPage(this, 
				BREATHING_ASSESSMENT_PAGE_TITLE).setRequired(true),
		new DiarrhoeaAssessmentPage(this,
				DIARRHOEA_ASSESSMENT_PAGE_TITLE).setRequired(true),
		new FeverAssessmentPage(this, 
				FEVER_ASSESSMENT_PAGE_TITLE).setRequired(true),
		new EarAssessmentPage(this,
				EAR_ASSESSMENT_PAGE_TITLE).setRequired(true));		
	}
}
