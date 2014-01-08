package ie.ucc.bis.supportinglife.rule.engine.enums;

/**
 * Enum: ImciClassificationType
 * 
 * @author timothyosullivan
 */

public enum ImciClassificationType {
	SEVERE(1), MODERATE(2), LOW(3);
	
	private int priority;
 
	private ImciClassificationType(int priority) {
		this.priority = priority;
	}
 
	public int getClassificationsRequired() {
		return priority;
	}
}
