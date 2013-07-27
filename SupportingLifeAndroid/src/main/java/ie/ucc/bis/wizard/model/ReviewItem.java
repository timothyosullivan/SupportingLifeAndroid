package ie.ucc.bis.wizard.model;

/**
 * Represents a single line item on the final review page.
 *
 * @see ie.ucc.bis.wizard.ui.ReviewFragment
 */
public class ReviewItem {
    public static final int DEFAULT_WEIGHT = 0;

    private int weight;
    private String title;
    private String displayValue;
    private String pageKey;
    private boolean headerItem;

    /**
     * Constructor
     * 
     * @param title
     * @param displayValue
     * @param pageKey
     */
    public ReviewItem(String title, String displayValue, String pageKey) {
        this(title, displayValue, pageKey, DEFAULT_WEIGHT, false);
    }

    /**
     * Constructor
     * 
     * @param title
     * @param displayValue
     * @param pageKey
     * @param weight
     */
    public ReviewItem(String title, String displayValue, String pageKey, int weight) {
    	this(title, displayValue, pageKey, weight, false);
    }
    
    /**
     * Constructor
     * 
     * @param title
     * @param displayValue
     * @param pageKey
     * @param weight
     */
    public ReviewItem(String title, String displayValue, String pageKey, int weight, boolean headerItem) {
        setTitle(title);
        setDisplayValue(displayValue);
        setPageKey(pageKey);
        setWeight(weight);
        setHeaderItem(headerItem);
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

	public boolean isHeaderItem() {
		return headerItem;
	}

	public void setHeaderItem(boolean headerItem) {
		this.headerItem = headerItem;
	}
}
