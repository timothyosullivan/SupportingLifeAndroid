package ie.ucc.bis.wizard.model;

/**
 * Callback interface connecting {@link Page}, {@link AbstractWizardModel}, and model container
 * objects (e.g. {@link com.example.android.wizardpager.MainActivity}.
 */
public interface ModelCallbacks {
    void onPageDataChanged(Page page);
    void onPageTreeChanged();
}
