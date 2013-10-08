package ie.ucc.bis.training;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;


/**
 * Class: TrainingTutorialParser
 * 
 * Responsible for parsing training tutorial details
 * 
 * @author TOSullivan
 *
 */

public class TrainingTutorialParser {

	private static final String TUTORIAL_ELEMENT = "Tutorial";
	private static final String TUTORIAL_TITLE = "Title";
	private static final String TUTORIAL_TYPE = "Type";
	private static final String TUTORIAL_AVAILABILITY = "Availability";
	private static final String TUTORIAL_CREATED_DATE = "CreatedDate";
	private static final String TUTORIAL_CREATED_BY = "CreatedBy";
	private static final String TUTORIAL_DESCRIPTION = "Description";
	private static final String TUTORIAL_IMAGE = "TitleImageName";
	private static final String TUTORIAL_VIDEO = "VideoName";
	
//	private static final String LOG_TAG = "ie.ucc.bis.training.TrainingTutorialParser";
	private static final String VIDEO_IMAGE_RELATIVE_LOCATION = "";
	private static final String VIDEO_RELATIVE_LOCATION = "";
	
	private static ArrayList<Tutorial> tutorials;
	
	/**
	 * Constructor
	 */
	public TrainingTutorialParser() {
		setTutorials(new ArrayList<Tutorial>());
	}
	
	/**
	 * 
	 * Responsible for parsing xml-based training tutorials
	 * 
	 * @param supportingLifeBaseActivity 
	 * 
	 */
	public void parseTrainingTutorials(SupportingLifeBaseActivity supportingLifeBaseActivity) {
		try {
			String elemName = null;
			Tutorial tutorial = null;

			XmlResourceParser xmlParser = supportingLifeBaseActivity.getResources().getXml(R.xml.training_tutorials);
			
			int eventType = xmlParser.next();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
					case XmlPullParser.START_TAG:
						elemName = xmlParser.getName();
						if (TUTORIAL_ELEMENT.equalsIgnoreCase(elemName)) {
							// <Tutorial>
							tutorial = new Tutorial();
						}
						else if (TUTORIAL_TITLE.equalsIgnoreCase(elemName)) {
							// <Title>
							tutorial.setTitle(xmlParser.nextText());
						}					
						else if (TUTORIAL_TYPE.equalsIgnoreCase(elemName)) {
							// <Type>
							tutorial.setType(xmlParser.nextText());
						}
						else if (TUTORIAL_AVAILABILITY.equalsIgnoreCase(elemName)) {
							// <Availability>
							tutorial.setAvailability(xmlParser.nextText());
						}
						else if (TUTORIAL_CREATED_DATE.equalsIgnoreCase(elemName)) {
							// <CreatedDate>
							tutorial.setCreatedDate(xmlParser.nextText());
						}
						else if (TUTORIAL_CREATED_BY.equalsIgnoreCase(elemName)) {
							// <CreatedBy>
							tutorial.setCreatedBy(xmlParser.nextText());
						}
						else if (TUTORIAL_DESCRIPTION.equalsIgnoreCase(elemName)) {
							// <Description>
							tutorial.setDescription(new StringBuilder(xmlParser.nextText()));
						}
						else if (TUTORIAL_IMAGE.equalsIgnoreCase(elemName)) {
							// <TitleImageName>
							tutorial.setTitleImageName(VIDEO_IMAGE_RELATIVE_LOCATION + xmlParser.nextText());
						}
						else if (TUTORIAL_VIDEO.equalsIgnoreCase(elemName)) {
							// <VideoName>
							tutorial.setVideoName(VIDEO_RELATIVE_LOCATION + xmlParser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if(TUTORIAL_ELEMENT.equalsIgnoreCase(xmlParser.getName())) {
							// </Tutorial>
							getTutorials().add(tutorial);
						}
						break;
				} // end of switch				
				eventType = xmlParser.next();
			} // end of while			
		} catch (XmlPullParserException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		// DEBUG OUTPUT
//		LoggerUtils.i(LOG_TAG, captureTutorialsDebugOutput());
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
//		LoggerUtils.i(LOG_TAG, "--------------------------------------");
	}
	

	/**
	 * 
	 * Provides debug output of all tutorials passed to
	 * the method
	 * 
	 * @param classifications
	 * 
	 */
	private StringBuilder captureTutorialDebugOutput(List<Tutorial> tutorials) {
		StringBuilder debugOutput = new StringBuilder();
		
		for (Tutorial tutorial : tutorials){
			debugOutput.append(tutorial.debugOutput());
		}
		
		return debugOutput;
	}	
	
	/**
	 * 
	 * Provides debug output of all tutorials held in memory
	 * 
	 */
	public StringBuilder captureTutorialsDebugOutput() {
		return captureTutorialDebugOutput(getTutorials());
	}

	/**
	 * Getter Method: getTutorials()
	 */	
	public ArrayList<Tutorial> getTutorials() {
		return tutorials;
	}

	/**
	 * Setter Method: setTutorials()
	 */
	public void setTutorials(ArrayList<Tutorial> tutorials) {
		TrainingTutorialParser.tutorials = tutorials;
	}
}
