package ie.ucc.bis.imci.model;


/**
 * Callback interface connecting {@link AbstractPage}, {@link AbstractWizardModel}, and model container
 * objects (e.g. {@link ie.ucc.bis.activity.ImciAssessmentActivity}.
 */
public interface ModelCallbacks {
    void onPageDataChanged(AbstractPage page);
    void onPageTreeChanged();
}
