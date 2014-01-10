package org.housemart.server.dao.entities;

import java.util.Date;

public class ClientRegisterEntity {

	protected Integer id;

	protected String clientID;
	protected String clientToken;
	protected String lastAPNSSign;
	
	protected String device;
	protected String version;
	protected String systemInfo;
	
	protected Date addTime;
	protected Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getClientToken() {
		return clientToken;
	}
	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}
	public String getLastAPNSSign() {
		return lastAPNSSign;
	}
	public void setLastAPNSSign(String lastAPNSSign) {
		this.lastAPNSSign = lastAPNSSign;
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
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSystemInfo() {
		return systemInfo;
	}
	public void setSystemInfo(String systemInfo) {
		this.systemInfo = systemInfo;
	}
	
	
}