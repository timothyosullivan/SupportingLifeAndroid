package ie.ucc.bis.wizard.model;

/**
 * Callback interface connecting {@link AbstractPage}, {@link AbstractWizardModel}, and model container
 * objects (e.g. {@link ie.ucc.bis.activity.RecordPatientDetailsWizardActivity}.
 */
public interface ModelCallbacks {
    void onPageDataChanged(AbstractPage page);
    void onPageTreeChanged();
}
