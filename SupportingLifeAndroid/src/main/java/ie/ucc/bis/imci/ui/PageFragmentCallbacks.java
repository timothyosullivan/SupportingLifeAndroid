package ie.ucc.bis.imci.ui;

import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.AbstractModel;

public interface PageFragmentCallbacks {
	AbstractModel getWizardModel();
    AbstractPage getPage(String key);
}
