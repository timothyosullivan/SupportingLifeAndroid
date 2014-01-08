package ie.ucc.bis.supportinglife.assessment.imci.model;

import ie.ucc.bis.supportinglife.assessment.model.AbstractModel;
import ie.ucc.bis.supportinglife.assessment.model.PageList;
import android.content.Context;

/**
 * 
 * @author timothyosullivan
 */

public class ImciAssessmentModel extends AbstractModel {

	public static final String GENERAL_PATIENT_DETAILS_PAGE_TITLE = "Patient Details";
	public static final String DANGER_SIGNS_PAGE_TITLE = "Danger Signs";
	public static final String BREATHING_ASSESSMENT_PAGE_TITLE = "Breathing Assessment";
	public static final String DIARRHOEA_ASSESSMENT_PAGE_TITLE = "Diarrhoea Assessment";
	public static final String FEVER_ASSESSMENT_PAGE_TITLE = "Fever Assessment";
	public static final String EAR_ASSESSMENT_PAGE_TITLE = "Ear Assessment";
	public static final String MALNUTRITION_ASSESSMENT_PAGE_TITLE = "Malnutrition Assessment";
	public static final String IMMUNIZATION_ASSESSMENT_PAGE_TITLE = "Immunization Status";
	
	
	/**
	 * Constructor
	 * 
	 * @param context Context
	 */
	public ImciAssessmentModel(Context context) {
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
		 * 7. Malnutrition and Anaemia Page
		 * 8. Immunization Status Page
		 * 
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
				EAR_ASSESSMENT_PAGE_TITLE).setRequired(true),
		new MalnutritionAssessmentPage(this,
				MALNUTRITION_ASSESSMENT_PAGE_TITLE).setRequired(true),
		new ImmunizationAssessmentPage(this,
				IMMUNIZATION_ASSESSMENT_PAGE_TITLE).setRequired(true));		
	}
}
