package ie.ucc.bis.supportinglife.rule.engine.enums;

/**
 * Enum: CcmClassificationType
 * 
 * @author timothyosullivan
 */

public enum CcmClassificationType {
	DANGER_SIGN(1), REFER(2), SICK(3);
	
	private int priority;
 
	private CcmClassificationType(int priority) {
		this.priority = priority;
	}
 
	public int getClassificationsRequired() {
		return priority;
	}
}
