package ie.ucc.bis.supportinglife.assessment.imci.ui;

import ie.ucc.bis.supportinglife.assessment.model.AbstractModel;
import ie.ucc.bis.supportinglife.assessment.model.AbstractPage;

/**
 * 
 * @author timothyosullivan
 */

public interface PageFragmentCallbacks {
	AbstractModel getWizardModel();
    AbstractPage getPage(String key);
}
