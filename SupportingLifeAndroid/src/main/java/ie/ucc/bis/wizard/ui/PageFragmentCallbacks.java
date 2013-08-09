package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.wizard.model.AbstractPage;
import ie.ucc.bis.wizard.model.AbstractWizardModel;

public interface PageFragmentCallbacks {
	AbstractWizardModel getWizardModel();
    AbstractPage getPage(String key);
}
