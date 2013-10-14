package ie.ucc.bis.assessment.model;


/**
 * Callback interface connecting {@link AbstractPage}, {@link AbstractModel}, and model container
 * objects (e.g. {@link ie.ucc.bis.activity.ImciAssessmentActivity}.
 * 
 * @author timothyosullivan
 */
public interface ModelCallbacks {
    void onPageDataChanged(AbstractPage page);
    void onPageTreeChanged();
}
