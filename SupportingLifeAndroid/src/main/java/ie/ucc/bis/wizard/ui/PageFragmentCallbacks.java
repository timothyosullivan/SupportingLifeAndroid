package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.wizard.model.AbstractPage;

public interface PageFragmentCallbacks {
    AbstractPage onGetPage(String key);
}
