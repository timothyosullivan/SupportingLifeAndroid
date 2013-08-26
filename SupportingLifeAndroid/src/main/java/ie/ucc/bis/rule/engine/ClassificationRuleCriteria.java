package ie.ucc.bis.rule.engine;

public enum ClassificationRuleCriteria {
	ANY_CLASSIFICATION(1);
	
	private int classificationsRequired;
 
	private ClassificationRuleCriteria(int classificationsRequired) {
		this.classificationsRequired = classificationsRequired;
	}
 
	public int getClassificationsRequired() {
		return classificationsRequired;
	}
}
