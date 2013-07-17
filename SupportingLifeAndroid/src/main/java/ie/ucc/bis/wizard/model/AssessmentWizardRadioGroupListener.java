package ie.ucc.bis.wizard.model;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AssessmentWizardRadioGroupListener implements OnCheckedChangeListener {

	private AbstractPage page;
	private String dataKey;	
	
	public AssessmentWizardRadioGroupListener(AbstractPage page, String dataKey) {
		setPage(page);
		setDataKey(dataKey);
	}
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
    	getPage().getPageData().putInt(dataKey, checkedId);
    	getPage().notifyDataChanged();
	}

	/**
	 * Getter Method: getPage()
	 */
	public AbstractPage getPage() {
		return page;
	}

	/**
	 * Setter Method: setPage()
	 */
	public void setPage(AbstractPage page) {
		this.page = page;
	}

	/**
	 * Getter Method: getDataKey()
	 */
	public String getDataKey() {
		return dataKey;
	}

	/**
	 * Setter Method: setDataKey()
	 */
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
}
