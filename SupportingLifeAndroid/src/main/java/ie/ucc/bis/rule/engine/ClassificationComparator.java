package ie.ucc.bis.rule.engine;

import java.util.Comparator;

public class ClassificationComparator implements Comparator<Classification> {

	/**
	 * Method Name: compare
	 * 
	 * Utility method to order the classifications such that the highest priority 
	 * classifications are highest in the list.
	 * 
	 * Note: Priority ordering is as follows:
	 * 
	 * 		1 -> SEVERE
	 * 		2 -> MODERATE
	 * 		3 -> LOW
	 * 
	 */
	@Override
	public int compare(Classification firstClassification, Classification secondClassification) {
		if (firstClassification.getPriority() < secondClassification.getPriority()) {
			return -1; // firstClassification should be higher in list
		}
		else if (firstClassification.getPriority() > secondClassification.getPriority()) {
			return 1; // firstClassification should be lower in list
		}
		else {
			return 0; // classifications are equal in priority
		}
	}

}
