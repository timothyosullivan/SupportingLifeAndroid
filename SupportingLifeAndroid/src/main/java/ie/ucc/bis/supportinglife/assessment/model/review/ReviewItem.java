package ie.ucc.bis.supportinglife.assessment.model.review;

import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a single line item on the final review page.
 *
 * @see ie.ucc.bis.supportinglife.assessment.imci.ui.ReviewFragment
 * 
 * @author timothyosullivan
 */
public class ReviewItem implements Serializable {
    /**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -2027656980188256986L;

	public static final int DEFAULT_WEIGHT = -1;
	
    private int weight;
    private String identifier;
    private String title;
    private String displayValue;
    private String symptomId;
    private String pageKey;
    private boolean headerItem;
    private boolean visible;
    private String symptomValue;
    private List<ReviewItem> dependees;

    /**
     * Constructor for header review items
     * 
     * @param title
     * @param displayValue
     * @param pageKey
     */
    public ReviewItem(String title, String pageKey) {
        this(title, null, null, pageKey, DEFAULT_WEIGHT, null, true);
    }
  
    /**
     * Constructor for non-header, non-symptom review items
     * 
     * @param title
     * @param displayValue
     * @param pageKey
     * @param weight
     * @param identifier (i.e. unique identifier for review item)
     * 
     */
    public ReviewItem(String title, String displayValue, String pageKey, int weight, String identifier) {
    	this(title, displayValue, null, pageKey, weight, identifier, false);
    }

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     * @param identifier (i.e. unique identifier for review item)
     * 
     */
    public ReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, String identifier) {
    	this(title, displayValue, symptomId, pageKey, weight, identifier, false);
    }
        
    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     * @param identifier (i.e. unique identifier for review item)
     * @param headerItem
     */
    protected ReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, String identifier, boolean headerItem) {
        setTitle(title);
        setDisplayValue(displayValue);
        setSymptomId(symptomId);
        setPageKey(pageKey);
        setWeight(weight);
        setHeaderItem(headerItem);
        setSymptomValue(null);
        setIdentifier(identifier);
        setVisible(true);
    }
    
    /**
     * Method: assessSymptom()
     * 
     * Determine the appropriate symptom value based
     * on the user's response. The user's response is
     * captured from the displayValue attribute 
     * 
     * @param supportingLifeBaseActivity 
     *  
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (getDisplayValue() != null) {
    		setSymptomValue(getDisplayValue());
    	}
    }
    
    public String getDisplayValue() {
        return this.displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getPageKey() {
        return this.pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public boolean isHeaderItem() {
		return headerItem;
	}

	public void setHeaderItem(boolean headerItem) {
		this.headerItem = headerItem;
	}

	public String getSymptomId() {
		return symptomId;
	}

	public void setSymptomId(String symptomId) {
		this.symptomId = symptomId;
	}

	public List<ReviewItem> getDependees() {
		return dependees;
	}

	public void setDependees(List<ReviewItem> dependees) {
		this.dependees = dependees;
	}

	public String getSymptomValue() {
		return symptomValue;
	}

	public void setSymptomValue(String symptomValue) {
		this.symptomValue = symptomValue;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
