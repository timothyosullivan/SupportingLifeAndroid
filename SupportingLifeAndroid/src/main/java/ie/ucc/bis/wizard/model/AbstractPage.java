package ie.ucc.bis.wizard.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

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
    protected Bundle mData = new Bundle();
    protected String mTitle;
    protected boolean mRequired = false;
    protected String mParentKey;

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
        mTitle = title;
    }

    public Bundle getData() {
        return mData;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isRequired() {
        return mRequired;
    }

    void setParentKey(String parentKey) {
        mParentKey = parentKey;
    }
    
    public AbstractPage findPageByKey(String key) {
        return getKey().equals(key) ? this : null;
    }

    public void flattenCurrentPageSequence(ArrayList<AbstractPage> dest) {
        dest.add(this);
    }

    public abstract Fragment createFragment();

    public String getKey() {
        return (mParentKey != null) ? mParentKey + ":" + mTitle : mTitle;
    }

    public boolean isCompleted() {
        return true;
    }

    public void resetData(Bundle data) {
        mData = data;
        notifyDataChanged();
    }

    public void notifyDataChanged() {
    	getModelCallbacks().onPageDataChanged(this);
    }

    public AbstractPage setRequired(boolean required) {
        mRequired = required;
        return this;
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
