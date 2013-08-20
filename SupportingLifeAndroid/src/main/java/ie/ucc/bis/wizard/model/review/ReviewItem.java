package ie.ucc.bis.wizard.model.review;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a single line item on the final review page.
 *
 * @see ie.ucc.bis.wizard.ui.ReviewFragment
 */
public class ReviewItem implements Serializable {
    /**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -2027656980188256986L;

	public static final int DEFAULT_WEIGHT = -1;
	
	protected final String YES_RESPONSE = "yes";
	protected final String NO_RESPONSE = "no";

    private int weight;
    private String title;
    private String displayValue;
    private String symptomId;
    private String pageKey;
    private boolean headerItem;
    private boolean positiveSymptom;
    private String negativeSymptomCriteria;
    private List<ReviewItem> dependees;

    /**
     * Constructor for header review items
     * 
     * @param title
     * @param displayValue
     * @param pageKey
     */
    public ReviewItem(String title, String pageKey) {
        this(title, null, null, pageKey, DEFAULT_WEIGHT, true);
    }
    
    /**
     * Constructor for non-header, non-symptom review items
     * 
     * @param title
     * @param displayValue
     * @param pageKey
     * @param weight
     */
    public ReviewItem(String title, String displayValue, String pageKey, int weight) {
    	this(title, displayValue, null, pageKey, weight, false);
    }

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public ReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	this(title, displayValue, symptomId, pageKey, weight, false);
    }
        
    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     * @param headerItem
     */
    protected ReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, boolean headerItem) {
        setTitle(title);
        setDisplayValue(displayValue);
        setSymptomId(symptomId);
        setPageKey(pageKey);
        setWeight(weight);
        setHeaderItem(headerItem);
        setPositiveSymptom(false);
    }
    
    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     * @param headerItem
     * @param negativeSymptomCriteria
     */
    protected ReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight, boolean headerItem, String negativeSymptomCriteria) {
    	this(title, displayValue, symptomId, pageKey, weight, false);
    	setNegativeSymptomCriteria(negativeSymptomCriteria);
    }
    
    /**
     * Method: assessSymptom()
     * 
     * Determine if, based on the user input,
     * the symptom applies to the patient being assessed.
     * 
     * For generic review items which apply in the case of header items,
     * the assessment of the symptom always defaults to false
     *  
     */
    public void assessSymptom() {
    	setPositiveSymptom(false);
    }
    
	/**
	 * Getter Method: getDisplayValue()
	 */
    public String getDisplayValue() {
        return this.displayValue;
    }

	/**
	 * Setter Method: setDisplayValue()
	 */
    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

	/**
	 * Getter Method: getPageKey()
	 */
    public String getPageKey() {
        return this.pageKey;
    }

	/**
	 * Setter Method: setPageKey()
	 */
    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

	/**
	 * Getter Method: getTitle()
	 */
    public String getTitle() {
        return this.title;
    }

	/**
	 * Setter Method: setTitle()
	 */
    public void setTitle(String title) {
        this.title = title;
    }

	/**
	 * Getter Method: getWeight()
	 */
    public int getWeight() {
        return this.weight;
    }

	/**
	 * Setter Method: setWeight()
	 */
    public void setWeight(int weight) {
        this.weight = weight;
    }

	/**
	 * Getter Method: isHeaderItem()
	 */
	public boolean isHeaderItem() {
		return headerItem;
	}

	/**
	 * Setter Method: setHeaderItem()
	 */
	public void setHeaderItem(boolean headerItem) {
		this.headerItem = headerItem;
	}

	/**
	 * Getter Method: getSymptomId()
	 */
	public String getSymptomId() {
		return symptomId;
	}

	/**
	 * Setter Method: setSymptomId()
	 */
	public void setSymptomId(String symptomId) {
		this.symptomId = symptomId;
	}

	/**
	 * Getter Method: isPositiveSymptom()
	 */
	public boolean isPositiveSymptom() {
		return positiveSymptom;
	}

	/**
	 * Setter Method: setPositiveSymptom()
	 */
	public void setPositiveSymptom(boolean positiveSymptom) {
		this.positiveSymptom = positiveSymptom;
	}

	/**
	 * Getter Method: getNegativeSymptomCriteria()
	 */
	public String getNegativeSymptomCriteria() {
		return negativeSymptomCriteria;
	}

	/**
	 * Setter Method: setNegativeSymptomCriteria()
	 */
	private void setNegativeSymptomCriteria(String negativeSymptomCriteria) {
		this.negativeSymptomCriteria = negativeSymptomCriteria;
	}

	/**
	 * Getter Method: getDependees()
	 */
	public List<ReviewItem> getDependees() {
		return dependees;
	}

	/**
	 * Setter Method: setDependees()
	 */
	public void setDependees(List<ReviewItem> dependees) {
		this.dependees = dependees;
	}
}
