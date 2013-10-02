package ie.ucc.bis.imci.ui;

import ie.ucc.bis.imci.model.AbstractPage;
import ie.ucc.bis.imci.model.AbstractWizardModel;

public interface PageFragmentCallbacks {
	AbstractWizardModel getWizardModel();
    AbstractPage getPage(String key);
}
