package ie.ucc.bis.training;

import java.io.Serializable;

/**
 * Class: Tutorial
 * 
 * Encapsulates a training tutorial as defined by 
 * classification_rules.xml e.g.
 * 
 * <!-- ============================================================ -->
 * <!-- TUTORIAL					 								  -->
 * <!-- ============================================================ -->
 * <!-- ============================================================ -->
 * <!-- Title: IMCI: Checking Danger Signs					  		  -->
 * <!-- ============================================================ -->
 * <Tutorial>
 * 	<Title>IMCI: Checking Danger Signs</Title>
 * 	<Type>Video</Type>
 * 	<Availability>Offline</Availability>
 * 	<CreatedDate>October 2013</CreatedDate>
 * 	<CreatedBy>Supporting Life Consortium</CreatedBy>
 * 	<Description>
 * 		Video provides a step-by-step guide to checking the 
 * 		danger signs of a patient aged between 2 months and
 * 		5 years.
 * 	</Description>
 * 	<TitleImageName></TitleImageName>
 * 	<VideoName></VideoName>
 * </Tutorial>
 * 
 * @author TOSullivan
 *
 */

public class Tutorial implements Serializable {

    /**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -4452587101387415005L;
	
	private String title;
	private String type;
	private String availability;
	private String createdDate;
	private String createdBy;
	private StringBuilder description;
	private String titleImageName;
	private String videoName;
	
	/**
	 * Constructor
	 * 
	 */
	public Tutorial() {}
	
	/**
	 * Getter Method: getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter Method: setTitle()
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * Getter Method: getAvailability()
	 */
	public String getAvailability() {
		return availability;
	}

	/**
	 * Setter Method: setAvailability()
	 */
	public void setAvailability(String availability) {
		this.availability = availability;
	}

	/**
	 * Getter Method: getCreatedDate()
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * Setter Method: setCreatedDate()
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Getter Method: getCreatedBy()
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Setter Method: setCreatedBy()
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Getter Method: getDescription()
	 */
	public StringBuilder getDescription() {
		return description;
	}

	/**
	 * Setter Method: setDescription()
	 */
	public void setDescription(StringBuilder description) {
		this.description = description;
	}

	/**
	 * Getter Method: getTitleImageName()
	 */
	public String getTitleImageName() {
		return titleImageName;
	}

	/**
	 * Setter Method: setTitleImageName()
	 */
	public void setTitleImageName(String titleImageName) {
		this.titleImageName = titleImageName;
	}

	/**
	 * Getter Method: getVideoName()
	 */
	public String getVideoName() {
		return videoName;
	}

	/**
	 * Setter Method: setVideoName()
	 */
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	
	/**
	 * 
	 * Provides debug output of tutorial
	 * 
	 */
	public String debugOutput() {
		StringBuilder debugOutput = new StringBuilder();

		debugOutput.append("------------------------------------ \n");
		debugOutput.append("Title: " + getTitle() + "\n");
		debugOutput.append("Type: " + getType() + "\n");
		debugOutput.append("Availability: " + getAvailability() + "\n");
		debugOutput.append("CreatedDate: " + getCreatedDate() + "\n");
		debugOutput.append("CreatedBy: " + getCreatedBy() + "\n");
		debugOutput.append("Description: " + getDescription() + "\n");
		debugOutput.append("TitleImageName: " + getTitleImageName() + "\n");
		debugOutput.append("VideoName: " + getVideoName() + "\n");		
		
		return debugOutput.toString();
	}
}
