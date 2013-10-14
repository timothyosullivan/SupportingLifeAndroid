package ie.ucc.bis.rule.engine.enums;

/**
 * 
 * @author timothyosullivan
 */

public enum SymptomRuleCriteria {
	ANY_SYMPTOM(1), TWO_SYMPTOMS_REQUIRED(2);
	
	private int symptomsRequired;
 
	private SymptomRuleCriteria(int symptomsRequired) {
		this.symptomsRequired = symptomsRequired;
	}
 
	public int getSymptomsRequired() {
		return symptomsRequired;
	}
}
