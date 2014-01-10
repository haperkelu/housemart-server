package org.housemart.server.dao.entities;

import java.util.Date;

public class HouseInteractionEntity {

	public final static int NORMAL_VALID_TIME = 1000 * 3600 * 24 * 2;
	public final static int MONO_VALID_TIME = 1000 * 3600 * 24 * 15;
	
	protected Integer id;

	protected Integer houseID;
	
	protected Integer agentID;
	
	protected Integer status;
	
	protected Date startDate;
	
	protected Date endDate;

	protected Date addTime;
	
	protected Date updateTime;

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

	public Integer getAgentID() {
		return agentID;
	}

	public void setAgentID(Integer agentID) {
		this.agentID = agentID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	
}