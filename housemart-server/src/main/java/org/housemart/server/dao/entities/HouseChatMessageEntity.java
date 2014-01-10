package org.housemart.server.dao.entities;

import java.util.Date;

public class HouseChatMessageEntity {

	protected Integer id;

	protected Integer brokerID;
	protected String brokerName;
	protected String brokerPicURL;
	protected Integer realBrokerID;
	protected Integer houseID;	
	protected String clientUID;
	protected Integer type;
	protected Integer status;
	protected String content;
	protected Integer fromTo;
	protected Integer format;
	
	protected Date addTime;
	protected Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBrokerID() {
		return brokerID;
	}
	public void setBrokerID(Integer brokerID) {
		this.brokerID = brokerID;
	}
	public String getBrokerName() {
		return brokerName;
	}
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	public Integer getRealBrokerID() {
		return realBrokerID;
	}
	public void setRealBrokerID(Integer realBrokerID) {
		this.realBrokerID = realBrokerID;
	}
	public Integer getHouseID() {
		return houseID;
	}
	public void setHouseID(Integer houseID) {
		this.houseID = houseID;
	}
	public String getClientUID() {
		return clientUID;
	}
	public void setClientUID(String clientUID) {
		this.clientUID = clientUID;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getFromTo() {
		return fromTo;
	}
	public void setFromTo(Integer fromTo) {
		this.fromTo = fromTo;
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
	public Integer getFormat() {
		return format;
	}
	public void setFormat(Integer format) {
		this.format = format;
	}
	public String getBrokerPicURL() {
		return brokerPicURL == null ? "" : brokerPicURL;
	}
	public void setBrokerPicURL(String brokerPicURL) {
		this.brokerPicURL = brokerPicURL;
	}
	
	
}