package ie.ucc.bis.imci.ui;

import ie.ucc.bis.assessment.model.AbstractWizardModel;

public interface ReviewFragmentCallbacks {
    public AbstractWizardModel getWizardModel();
    public void onEditScreenAfterReview(String pageKey);
}
