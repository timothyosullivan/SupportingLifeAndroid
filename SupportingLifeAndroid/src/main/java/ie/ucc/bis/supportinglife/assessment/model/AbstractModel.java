package ie.ucc.bis.supportinglife.assessment.model;

import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.os.Bundle;

/**
 * Represents an assessment model, including the pages/steps in the assessment, their dependencies, and their
 * currently populated choices/values/selections.
 *
 * To create the SupportingLIFE breadcrumb UI assessment, extend this class 
 * and implement {@link #configurePageList()}.
 * 
 * @author timothyosullivan
 */

public abstract class AbstractModel implements ModelCallbacks {
	private Context applicationContext;
	private List<ModelCallbacks> modelListeners = new ArrayList<ModelCallbacks>();
    private PageList assessmentPages;

	/**
	 * Abstract Method: configurePageList
	 * 
	 * Override this method to define a new wizard model.
	 */
    protected abstract PageList configurePageList();
    
	/**
	 * Constructor
	 * 
	 * @param context Context
	 */
    public AbstractModel(Context context) {
    	setApplicationContext(context);
        setAssessmentPages(configurePageList());
    }

	/**
	 * onPageDataChanged method
	 * 
	 * Notify model listeners of a 'pageDataChanged' event
	 * 
	 * @param page : Page
	 */
    public void onPageDataChanged(AbstractPage page) {
    	for (ModelCallbacks modelCallback : getModelListeners()) {
    		modelCallback.onPageDataChanged(page);
    	}
    }
    
	/**
	 * onPageTreeChanged method
	 * 
	 * Notify model listeners of a 'pageTreeChanged' event
	 * 
	 */    
    public void onPageTreeChanged() {
    	for (ModelCallbacks modelCallback : getModelListeners()) {
    		modelCallback.onPageTreeChanged();
        }
    }

	/**
	 * findPageByKey method
	 * 
	 * Utility method to retrieve a bread-crumb UI Wizard
	 * page based on the key
	 * 
	 * @param key : String
	 * @return Page
	 *  
	 */ 
    public AbstractPage findPageByKey(String key) {
        return getAssessmentPages().findPageByKey(key);
    }

	/**
	 * load method
	 * 
	 * TODO: Add description
	 * 
	 * @param savedValues : Bundle
	 * @return void
	 *  
	 */     
    public void load(Bundle savedValues) {
        for (String key : savedValues.keySet()) {
        	getAssessmentPages().findPageByKey(key).resetPageData(savedValues.getBundle(key));
        }
    }

	/**
	 * save method
	 * 
	 * Invoked by the 'AssessmentWizardActivity' when saving instance
	 * state. Method will capture the data associated with each page
	 * in the wizard and store in bundle.
	 * 
	 * @return Bundle
	 *  
	 */       
    public Bundle save() {
        Bundle bundle = new Bundle();
        for (AbstractPage page : getPageSequence()) {
            bundle.putBundle(page.getKey(), page.getPageData());
        }
        return bundle;
    }
    
	/**
	 * registerListener method
	 * 
	 * Facilitates the registration of model listeners
	 * (e.g. AssessmentWizardActivity)
	 * 
	 * This ensures the model listener will be informed of 
	 * 'onPageDataChanged' and 'onPageTreeChanged' events
	 * 
	 * @param listener : ModelCallbacks
	 */
    public void registerListener(ModelCallbacks listener) {
        getModelListeners().add(listener);
    }

	/**
	 * unregisterListener method
	 * 
	 * Facilitates the de-registering of model listeners
	 * (e.g. AssessmentWizardActivity)
	 * 
	 * This ensures the model listener will no longer be informed of 
	 * 'onPageDataChanged' and 'onPageTreeChanged' events
	 * 
	 * @param listener : ModelCallbacks
	 */ 
    public void unregisterListener(ModelCallbacks listener) {
    	getModelListeners().remove(listener);
    }    
    
    /**
     * Gets the list of wizard steps
     */
    public List<AbstractPage> getPageSequence() {
        ArrayList<AbstractPage> flattened = new ArrayList<AbstractPage>();
        getAssessmentPages().flattenCurrentPageSequence(flattened);
        return flattened;
    }

    /**
     * Gets the current list of review items, associated with each assessment page, as
     * entered by the user
     * 
     * @return ArrayList<ReviewItem>
     */
	public ArrayList<ReviewItem> gatherAssessmentReviewItems() {
		ArrayList<ReviewItem> reviewItems = new ArrayList<ReviewItem>();
        for (AbstractPage page : getPageSequence()) {
            page.getReviewItems(reviewItems);
        }
        
        Collections.sort(reviewItems, new Comparator<ReviewItem>() {
            public int compare(ReviewItem a, ReviewItem b) {
                return a.getWeight() > b.getWeight() ? +1 : a.getWeight() < b.getWeight() ? -1 : 0;
            }
        });
		return reviewItems;
	}
    
	/**
	 * Getter Method: getApplicationContext()
	 * 
	 */
    public Context getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Setter Method: setApplicationContext()
	 * 
	 */    
	public void setApplicationContext(Context applicationContext) {
		this.applicationContext = applicationContext;
	}
    
	/**
	 * Getter Method: getAssessmentPages()
	 * 
	 */	
	public PageList getAssessmentPages() {
		return assessmentPages;
	}

	/**
	 * Setter Method: setAssessmentPages()
	 * 
	 */    	
	public void setAssessmentPages(PageList assessmentPages) {
		this.assessmentPages = assessmentPages;
	}

	/**
	 * Getter Method: getModelListeners()
	 * 
	 */		
	public List<ModelCallbacks> getModelListeners() {
		return modelListeners;
	}

	/**
	 * Setter Method: setModelListeners()
	 * 
	 */   	
	public void setModelListeners(List<ModelCallbacks> modelListeners) {
		this.modelListeners = modelListeners;
	}
}
