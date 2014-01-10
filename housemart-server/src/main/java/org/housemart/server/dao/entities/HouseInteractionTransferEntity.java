package org.housemart.server.dao.entities;

import java.util.Date;

public class HouseInteractionTransferEntity {
	
	protected Integer id;

	protected Integer houseID;
	
	protected Integer fromBrokerID;
	protected Integer toBrokerID;
	protected Integer finalBrokerID;
	
	protected Integer status;

	protected Date addTime;
	
	protected Date updateTime;
	
	protected String notes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHouseID() {
		return houseID;
	}

	public void setHouseID(Integer houseID) {
		this.houseID = houseID;
	}

	public Integer getFromBrokerID() {
		return fromBrokerID;
	}

	public void setFromBrokerID(Integer fromBrokerID) {
		this.fromBrokerID = fromBrokerID;
	}

	public Integer getToBrokerID() {
		return toBrokerID;
	}

	public void setToBrokerID(Integer toBrokerID) {
		this.toBrokerID = toBrokerID;
	}

	
	public Integer getFinalBrokerID() {
		return finalBrokerID;
	}

	public void setFinalBrokerID(Integer finalBrokerID) {
		this.finalBrokerID = finalBrokerID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	
}