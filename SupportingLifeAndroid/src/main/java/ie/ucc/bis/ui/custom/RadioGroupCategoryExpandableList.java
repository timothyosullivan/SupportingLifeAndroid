package ie.ucc.bis.ui.custom;

import java.util.ArrayList;

import android.widget.RadioGroup;

public class RadioGroupCategoryExpandableList {

	private String categoryLabel;
	private RadioGroup radioGroupCategory;
	private ArrayList<RadioGroupItemExpandableList> radioGroupItems;
	
	/**
	 * Getter Method: getCategoryLabel()
	 */
	public String getCategoryLabel() {
		return categoryLabel;
	}
	
	/**
	 * Setter Method: setCategoryLabel()
	 */	
	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
	}
	
	/**
	 * Getter Method: getRadioGroupCategory()
	 */
	public RadioGroup getRadioGroupCategory() {
		return radioGroupCategory;
	}
	
	/**
	 * Setter Method: setRadioGroupCategory()
	 */	
	public void setRadioGroupCategory(RadioGroup radioGroupCategory) {
		this.radioGroupCategory = radioGroupCategory;
	}
	
	/**
	 * Getter Method: getRadioGroupItems()
	 */
	public ArrayList<RadioGroupItemExpandableList> getRadioGroupItems() {
		return radioGroupItems;
	}
	
	/**
	 * Setter Method: setRadioGroupItems()
	 */	
	public void setRadioGroupItems(ArrayList<RadioGroupItemExpandableList> radioGroupItems) {
		this.radioGroupItems = radioGroupItems;
	}
}
