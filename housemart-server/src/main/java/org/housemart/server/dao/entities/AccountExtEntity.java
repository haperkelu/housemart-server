package org.housemart.server.dao.entities;

public class AccountExtEntity extends AccountEntity {

	protected Integer countHouse = 0;
	protected Integer countHouseInteraction = 0;
	protected Integer countHouseMono= 0;
	
	public Integer getCountHouse() {
		return countHouse;
	}


	public Integer getCountHouseInteraction() {
		return countHouseInteraction;
	}


	public Integer getCountHouseMono() {
		return countHouseMono;
	}
	
}