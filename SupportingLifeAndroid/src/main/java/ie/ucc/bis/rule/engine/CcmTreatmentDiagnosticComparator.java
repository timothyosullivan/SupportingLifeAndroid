package ie.ucc.bis.rule.engine;

import java.util.Comparator;

public class CcmTreatmentDiagnosticComparator implements Comparator<Diagnostic> {

	/**
	 * Method Name: compare
	 * 
	 * Utility method to order the CCM diagnostic elements such that the header 'treatment'
	 * diagnostic appears first, following by any specific treatments, with the footer 'treatment' 
	 * diagnositc appearing finally in the list.
	 * 
	 * Note: Priority ordering is as follows:
	 * 
	 * 		1 -> HEADER TREATMENT
	 * 		2 -> ANY SPECIFIC TREATMENT
	 * 		3 -> FOOTER TREATMENT
	 * 
	 * @author timothyosullivan
	 */
	@Override
	public int compare(Diagnostic firstDiagnostic, Diagnostic secondDiagnostic) {
		if (firstDiagnostic.isTreatmentHeader() || secondDiagnostic.isTreatmentFooter()) {
			return -1; // firstDiagnostic should be higher in list
		}
		else if (firstDiagnostic.isTreatmentFooter() || secondDiagnostic.isTreatmentHeader()) {
			return 1; // firstDiagnostic should be lower in list
		}
		else {
			return 0; // diagnostics are equal in priority
		}
	}

}
