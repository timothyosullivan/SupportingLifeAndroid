package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.wizard.model.AbstractWizardModel;

public interface ReviewFragmentCallbacks {
    public AbstractWizardModel getWizardModel();
    public void onEditScreenAfterReview(String pageKey);
}
