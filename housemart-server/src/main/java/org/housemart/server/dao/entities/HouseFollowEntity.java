package org.housemart.server.dao.entities;

import java.util.Date;

public class HouseFollowEntity {
	
	private int id;
	private int houseId;
	private int type;
	private String clientId;
	private Date addTime;
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}	
	
}
