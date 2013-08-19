package ie.ucc.bis.ui.utilities;

import ie.ucc.bis.rule.engine.Classification;

import java.util.ArrayList;
import java.util.List;

public class ClassificationUtils {

	/**
	 * Utility method to retrieve the highest priority classification for a
	 * type of classification category from a list of classifications
	 *  
	 * @param classificationCategoryId
	 * @param classifications
	 * 
	 * @return Classification
	 */
	public static Classification retrieveHighestPriorityClassification(String classificationCategoryId, 
			ArrayList<Classification> classifications) {
		
		int priority = -1;
		Classification highestClassification = new Classification();
		
		for (Classification classification : classifications) {
			if (classification.getCategory().equals(classificationCategoryId)) {
				if (classification.getPriority() > priority) {
					highestClassification = classification;
					priority = classification.getPriority();
				}
			}
		}
		return highestClassification;
	}

	/**
	 * Utility method to determine whether a classification list contains
	 * a particular classification Category Id
	 * 
	 * @param classificationList
	 * @param classificationCategoryId
	 * 
	 * @return: True - if classification is in list
	 */
	public static boolean containsClassificationCategoryId(List<Classification> classificationList,
			String classificationCategoryId) {
		
		for (Classification classification : classificationList) {
			if (classification.getCategory().equals(classificationCategoryId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method Name: copyClassificationHeadlineDetails
	 * 
	 * Utility method for copying the headline details of a classification
	 * 
	 * @param classification - i.e. classification to be checked
	 * @param classificationMatch 
	 * 
	 */
	public static void copyClassificationHeadlineDetails(Classification classification, Classification classificationMatch) {
		classificationMatch.setName(classification.getName());
		classificationMatch.setType(classification.getType());
		classificationMatch.setCategory(classification.getCategory());
	}

}
