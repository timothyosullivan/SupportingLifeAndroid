package ie.ucc.bis.ccm.model;

import ie.ucc.bis.assessment.model.AbstractModel;
import ie.ucc.bis.assessment.model.PageList;
import android.content.Context;

/**
 * 
 * @author timothyosullivan
 */

public class CcmAssessmentModel extends AbstractModel {

	public static final String CCM_GENERAL_PATIENT_DETAILS_PAGE_TITLE = "Patient Details";
	public static final String CCM_INITIAL_ASK_ASSESSMENT_PAGE_TITLE = "Ask Assessment (1)";
	public static final String CCM_SECONDARY_ASK_ASSESSMENT_PAGE_TITLE = "Ask Assessment (2)";
	public static final String LOOK_ASSESSMENT_PAGE_TITLE = "Look Assessment";
	
	/**
	 * Constructor
	 * 
	 * @param context Context
	 */
	public CcmAssessmentModel(Context context) {
		super(context);
	}

	@Override
	protected PageList configurePageList() {
		/*
		 * CCM Assessment Pages are as follows:
		 * 
		 * 1. General Patient Details CCM Page
		 * 2. Initial Ask Assessment CCM Page
		 * 3. Secondary Ask Assessment CCM Page
		 * 4. Look Assessment CCM Page
		 * 
		 */

		return new PageList(new GeneralPatientDetailsCcmPage(this, 
				CCM_GENERAL_PATIENT_DETAILS_PAGE_TITLE).setRequired(true),
				new InitialAskCcmPage(this,
						CCM_INITIAL_ASK_ASSESSMENT_PAGE_TITLE).setRequired(true),
				new SecondaryAskCcmPage(this,
						CCM_SECONDARY_ASK_ASSESSMENT_PAGE_TITLE).setRequired(true),
				new LookCcmPage(this,
								LOOK_ASSESSMENT_PAGE_TITLE).setRequired(true));
		
	}
}
