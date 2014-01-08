package ie.ucc.bis.supportinglife.assessment.model;


/**
 * Callback interface connecting {@link AbstractPage}, {@link AbstractModel}, and model container
 * objects (e.g. {@link ie.ucc.bis.supportinglife.activity.ImciAssessmentActivity}.
 * 
 * @author timothyosullivan
 */
public interface ModelCallbacks {
    void onPageDataChanged(AbstractPage page);
    void onPageTreeChanged();
}
