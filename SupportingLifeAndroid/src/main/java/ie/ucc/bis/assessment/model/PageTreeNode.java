package ie.ucc.bis.assessment.model;

import java.util.ArrayList;

/**
 * Represents a node in the page tree. 
 * Can either be a single page, or a page container.
 */
public interface PageTreeNode {
    public AbstractPage findPageByKey(String key);
    public void flattenCurrentPageSequence(ArrayList<AbstractPage> dest);
}
