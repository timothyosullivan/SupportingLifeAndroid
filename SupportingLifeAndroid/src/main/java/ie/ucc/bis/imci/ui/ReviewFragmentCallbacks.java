package ie.ucc.bis.imci.ui;

import ie.ucc.bis.imci.model.AbstractWizardModel;

public interface ReviewFragmentCallbacks {
    public AbstractWizardModel getWizardModel();
    public void onEditScreenAfterReview(String pageKey);
}
