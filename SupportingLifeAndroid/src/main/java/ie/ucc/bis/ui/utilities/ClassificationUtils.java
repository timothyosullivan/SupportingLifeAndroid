package ie.ucc.bis.ui.utilities;

import ie.ucc.bis.rule.engine.Classification;
import ie.ucc.bis.rule.engine.Diagnostic;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author timothyosullivan
 */
public class ClassificationUtils {

	/**
	 * Utility method to retrieve the highest priority classification for a
	 * type of classification category from a list of classifications
	 *  
	 * @param classificationCategoryId
	 * @param diagnostics
	 * 
	 * @return Classification
	 */
	public static Classification retrieveHighestPriorityClassification(String classificationCategoryId, 
			List<Diagnostic> diagnostics) {
		
		int priority = -1;
		Classification highestClassification = null;
		
		for (Diagnostic diagnostic : diagnostics) {
			Classification classification = diagnostic.getClassification();
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
	 * Utility method to determine whether a diagnostic list contains
	 * a particular classification Category Id
	 * 
	 * @param diagnosticList
	 * @param classificationCategoryId
	 * 
	 * @return: True - if classification is in list
	 */
	public static boolean containsClassificationCategoryId(Set<Diagnostic> diagnosticList, String classificationCategoryId) {
		
		for (Diagnostic diagnostic : diagnosticList) {
			Classification classification = diagnostic.getClassification();
			if (classification.getCategory() != null && classification.getCategory().equals(classificationCategoryId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Utility method to determine whether a diagnostic list contains
	 * a particular classification (identified by it's name)
	 * 
	 * @param classificationName
	 * @param diagnosticList
	 * 
	 * @return: True - if classification is in list
	 */
	public static boolean containsClassificationName(String classificationName, List<Diagnostic> diagnosticList) {

		for (Diagnostic diagnostic : diagnosticList) {
			Classification classification = diagnostic.getClassification();
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
		classificationMatch.setCcmTreatmentDisplayName(classification.getCcmTreatmentDisplayName());
		classificationMatch.setType(classification.getType());
		classificationMatch.setCategory(classification.getCategory());
		classificationMatch.setPriority(classification.getPriority());
	}
}
