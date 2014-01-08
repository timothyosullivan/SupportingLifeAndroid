package ie.ucc.bis.supportinglife.rule.engine;

import java.util.Comparator;

public class DiagnosticComparator implements Comparator<Diagnostic> {

	/**
	 * Method Name: compare
	 * 
	 * Utility method to order the diagnostic elements such that the highest priority 
	 * diagnostics (as dicatated by the severity of their classifications)
	 * are highest in the list.
	 * 
	 * Note: Priority ordering is as follows:
	 * 
	 * 		1 -> SEVERE
	 * 		2 -> MODERATE
	 * 		3 -> LOW
	 * 
	 * @author timothyosullivan
	 */
	@Override
	public int compare(Diagnostic firstDiagnostic, Diagnostic secondDiagnostic) {
		if (firstDiagnostic.getClassification().getPriority() < secondDiagnostic.getClassification().getPriority()) {
			return -1; // firstDiagnostic should be higher in list
		}
		else if (firstDiagnostic.getClassification().getPriority() > secondDiagnostic.getClassification().getPriority()) {
			return 1; // firstDiagnostic should be lower in list
		}
		else {
			return 0; // diagnostics are equal in priority
		}
	}

}
