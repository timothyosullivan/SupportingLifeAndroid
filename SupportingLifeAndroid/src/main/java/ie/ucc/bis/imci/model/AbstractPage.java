package ie.ucc.bis.imci.model;

import ie.ucc.bis.imci.model.review.ReviewItem;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Represents a single page in the bread-crumb UI wizard.
 */
public abstract class AbstractPage implements PageTreeNode {
    /**
     * The key into {@link #getData()} used for wizards with simple (single) values.
     */
    public static final String SIMPLE_DATA_KEY = "_";

    protected ModelCallbacks modelCallbacks;

    /**
     * Current wizard values/selections.
     */
    protected Bundle pageData = new Bundle();
    protected String title;
    protected boolean required = false;
    protected String parentKey;

	/**
	 * Abstract Method: getReviewItems
	 * 
	 * Override this method to define the review items
	 * associated with the page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */
    public abstract void getReviewItems(ArrayList<ReviewItem> reviewItems);

	/**
	 * Constructor
	 * 
	 * @param context Context
	 */    
    protected AbstractPage(ModelCallbacks callbacks, String title) {
    	setModelCallbacks(callbacks);
        setTitle(title);
    }
    
    public AbstractPage findPageByKey(String key) {
        return getKey().equals(key) ? this : null;
    }

    public void flattenCurrentPageSequence(ArrayList<AbstractPage> pages) {
    	pages.add(this);
    }

    public abstract Fragment createFragment();

    public String getKey() {
        return (getParentKey() != null) ? getParentKey() + ":" + getTitle() : getTitle();
    }

    public boolean isCompleted() {
        return true;
    }

    public void resetPageData(Bundle pageData) {
        setPageData(pageData);
        notifyDataChanged();
    }

    public void notifyDataChanged() {
    	getModelCallbacks().onPageDataChanged(this);
    }
    
	/**
	 * Setter Method: setData()
	 * 
	 */      
    public void setPageData(Bundle pageData) {
        this.pageData = pageData;
    }

	/**
	 * Getter Method: getData()
	 * 
	 */	   
    public Bundle getPageData() {
        return this.pageData;
    }

	/**
	 * Setter Method: setTitle()
	 * 
	 */      
    public void setTitle(String title) {
    	this.title = title;
    }
    
	/**
	 * Getter Method: getTitle()
	 * 
	 */	      
    public String getTitle() {
        return this.title;
    }

	/**
	 * Setter Method: setParentKey()
	 * 
	 */  	     
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

	/**
	 * Getter Method: getParentKey()
	 * 
	 */	    
    public String getParentKey() {
        return this.parentKey;
    }

	/**
	 * Setter Method: setRequired()
	 * 
	 */  	    
    public AbstractPage setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public boolean isRequired() {
        return this.required;
    }    
    
	/**
	 * Getter Method: getModelCallbacks()
	 * 
	 */		    
	public ModelCallbacks getModelCallbacks() {
		return modelCallbacks;
	}

	/**
	 * Setter Method: setModelCallbacks()
	 * 
	 */  	
	public void setModelCallbacks(ModelCallbacks modelCallbacks) {
		this.modelCallbacks = modelCallbacks;
	}
}
