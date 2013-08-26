package ie.ucc.bis.ui.utilities;

import ie.ucc.bis.rule.engine.Classification;

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
			List<Classification> classifications) {
		
		int priority = -1;
		Classification highestClassification = null;
		
		for (Classification classification : classifications) {
			if (classification.getCategory().equals(classificationCategoryId)) {
				if (highestClassification == null || classification.getPriority() < priority) {
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
	public static boolean containsClassificationCategoryId(List<Classification> classificationList, String classificationCategoryId) {
		
		for (Classification classification : classificationList) {
			if (classification.getCategory() != null && classification.getCategory().equals(classificationCategoryId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Utility method to determine whether a classification list contains
	 * a particular classification (identified by it's name)
	 * 
	 * @param classificationName
	 * @param classificationList
	 * 
	 * @return: True - if classification is in list
	 */
	public static boolean containsClassificationName(String classificationName, List<Classification> classificationList) {
		for (Classification classification : classificationList) {
			if (classification.getName().equals(classificationName)) {
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
		classificationMatch.setPriority(classification.getPriority());
	}
}
