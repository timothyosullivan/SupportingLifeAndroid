package ie.ucc.bis.wizard.model;

import java.util.ArrayList;

/**
 * Represents a list of wizard pages.
 */
public class PageList extends ArrayList<Page> implements PageTreeNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1934134441792428239L;

	public PageList(Page... pages) {
        for (Page page : pages) {
            add(page);
        }
    }

    public Page findByKey(String key) {
        for (Page childPage : this) {
            Page found = childPage.findByKey(key);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    public void flattenCurrentPageSequence(ArrayList<Page> dest) {
        for (Page childPage : this) {
            childPage.flattenCurrentPageSequence(dest);
        }
    }
}
