package org.housemart.server.service.enums;


public enum AuditTypeEnum {
	LoggingAudit(1), CommitterUpdateAudit(2), InvalidLoggingAudit(3), StatusAudit(4), ContentUpdateAudit(5),
	OffboardAudit(6);
	private int value;
	AuditTypeEnum(int value) {
		this.setValue(value);
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}