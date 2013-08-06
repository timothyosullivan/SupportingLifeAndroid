package ie.ucc.bis.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Classification implements Serializable {

    /**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 2250655542678899484L;

	private String category;
	private String name;
	private String type;
	private ArrayList<String> symptoms;
 
	/**
	 * Constructor
	 * 
	 */
	public Classification() {}
	
	/**
	 * Constructor
	 * 
	 * @param category
	 * @param name
	 * @param type
	 */
	public Classification(String category, String name, String type) {
		setCategory(category);
		setName(name);
		setType(type);
	}
	
	/**
	 * Getter Method: getCategory()
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Setter Method: setCategory()
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Getter Method: getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter Method: setName()
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter Method: getType()
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter Method: setType()
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter Method: getSymptoms()
	 */
	public ArrayList<String> getSymptoms() {
		return symptoms;
	}

	/**
	 * Setter Method: setSymptoms()
	 */
	public void setSymptoms(ArrayList<String> symptoms) {
		this.symptoms = symptoms;
	}
}
