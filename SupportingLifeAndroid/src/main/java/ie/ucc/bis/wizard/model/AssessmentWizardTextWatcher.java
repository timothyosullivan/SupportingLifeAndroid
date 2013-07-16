package ie.ucc.bis.wizard.model;

import android.text.Editable;
import android.text.TextWatcher;

public class AssessmentWizardTextWatcher implements TextWatcher {

	private AbstractPage page;
	private String dataKey;
	
	public AssessmentWizardTextWatcher(AbstractPage page, String dataKey) {
		setPage(page);
		setDataKey(dataKey);
	}
	
	
	public void afterTextChanged(Editable editable) {
    	getPage().getPageData().putString(dataKey,
                (editable != null) ? editable.toString() : null);
    	getPage().notifyDataChanged();
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	public void onTextChanged(CharSequence s, int start, int before, int count) {}
	
	/**
	 * Getter Method: getPage()
	 * 
	 */
	public AbstractPage getPage() {
		return page;
	}

	/**
	 * Setter Method: setPage()
	 * 
	 */
	public void setPage(AbstractPage page) {
		this.page = page;
	}

	/**
	 * Getter Method: getDataKey()
	 * 
	 */
	public String getDataKey() {
		return dataKey;
	}

	/**
	 * Setter Method: setDataKey()
	 * 
	 */
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
}
