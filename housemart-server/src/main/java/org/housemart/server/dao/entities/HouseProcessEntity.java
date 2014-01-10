/**
 * Created on 2012-10-15
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.dao.entities;

import java.util.Date;

/**
 * @author pqin
 */
public class HouseProcessEntity {
	private Integer Id;
	private Integer houseId;
	private String memo;
	private Date addTime;
	private Integer brokerId;
	private String brokerName;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(Integer brokerId) {
		this.brokerId = brokerId;
	}
	public String getBrokerName() {
		return brokerName;
	}
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	
}
