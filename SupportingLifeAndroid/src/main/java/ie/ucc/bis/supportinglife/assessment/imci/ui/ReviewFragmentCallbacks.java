package ie.ucc.bis.supportinglife.assessment.imci.ui;

import ie.ucc.bis.supportinglife.assessment.model.AbstractModel;

/**
 * 
 * @author timothyosullivan
 */

public interface ReviewFragmentCallbacks {
    public AbstractModel getWizardModel();
    public void onEditScreenAfterReview(String pageKey);
}
