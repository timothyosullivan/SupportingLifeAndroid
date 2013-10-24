package ie.ucc.bis.assessment.imci.ui;

import ie.ucc.bis.assessment.model.AbstractModel;

/**
 * 
 * @author timothyosullivan
 */

public interface ReviewFragmentCallbacks {
    public AbstractModel getWizardModel();
    public void onEditScreenAfterReview(String pageKey);
}
