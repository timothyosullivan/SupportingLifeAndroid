package ie.ucc.bis.assessment.model;

import java.util.ArrayList;

/**
 * Represents a list (ArrayList) of bread-crumb UI Wizard assessment pages.
 * 
 * @author timothyosullivan
 */
public class PageList extends ArrayList<AbstractPage> implements PageTreeNode {
	private static final long serialVersionUID = 1934134441792428239L;

	/**
	 * Constructor
	 * 
	 * @param pages : Page...
	 */	
	public PageList(AbstractPage... pages) {
        for (AbstractPage page : pages) {
            add(page);
        }
    }

	/**
	 * Method: findPageByKey
	 * 
	 * Utility method to retrieve a bread-crumb UI Wizard
	 * page based on the key
	 * 
	 * @param key : String
	 * @return Page
	 * 
	 */	
    public AbstractPage findPageByKey(String key) {
        for (AbstractPage childPage : this) {
            AbstractPage found = childPage.findPageByKey(key);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

	/**
	 * Method: flattenCurrentPageSequence
	 * 
	 * Utility method to retrieve the current list of wizard steps.
	 * Nested (dependent) pages will be be flattened based on the
	 * user's choices.
	 * 
	 * @param pages : ArrayList<Page>
	 * 
	 */	
    public void flattenCurrentPageSequence(ArrayList<AbstractPage> pages) {
        for (AbstractPage childPage : this) {
            childPage.flattenCurrentPageSequence(pages);
        }
    }
}
