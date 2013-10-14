package ie.ucc.bis.rule.engine.enums;

/**
 * 
 * @author timothyosullivan
 */

public enum ClassificationType {
	SEVERE(1), MODERATE(2), LOW(3);
	
	private int priority;
 
	private ClassificationType(int priority) {
		this.priority = priority;
	}
 
	public int getClassificationsRequired() {
		return priority;
	}
}
