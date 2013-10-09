package ie.ucc.bis.imci.ui;

import ie.ucc.bis.assessment.model.AbstractModel;

public interface ReviewFragmentCallbacks {
    public AbstractModel getWizardModel();
    public void onEditScreenAfterReview(String pageKey);
}
