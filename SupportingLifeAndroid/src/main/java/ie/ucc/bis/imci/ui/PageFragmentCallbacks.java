package ie.ucc.bis.imci.ui;

import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.AbstractWizardModel;

public interface PageFragmentCallbacks {
	AbstractWizardModel getWizardModel();
    AbstractPage getPage(String key);
}
