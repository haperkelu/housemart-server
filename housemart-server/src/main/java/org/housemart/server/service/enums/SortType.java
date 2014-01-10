package org.housemart.server.service.enums;

public enum SortType {
	onboardTime(1), price(2), avg(3), area(4);

	private int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	private SortType(int type) {
		this.type = type;
	}

	public static SortType typeOf(int i) {
		SortType sort = null;
		for (SortType type : SortType.values()) {
			if (type.getType() == i) {
				sort = type;
				break;
			}
		}
		return sort;
	}
}
