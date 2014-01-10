/**
 * Created on 2013-5-2
 * 
 */
package org.housemart.server.service.enums;

public enum RentSortType {

	onboardTime(1), price(2), area(3);

	private int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	private RentSortType(int type) {
		this.type = type;
	}

	public static RentSortType typeOf(int i) {
		RentSortType sort = null;
		for (RentSortType type : RentSortType.values()) {
			if (type.getType() == i) {
				sort = type;
				break;
			}
		}
		return sort;
	}

}
