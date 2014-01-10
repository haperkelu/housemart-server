package org.housemart.server.dao.entities;

import java.util.Date;

public class AccountEntity {

	protected Integer id;

	protected String loginName;
	protected String password;	
	protected String name;
	protected String identityID;
	protected Integer gender;
	protected String contactInfo1;
	protected String contactInfo2;
	protected String emergency;
	protected String emergencyContact;
	protected Integer status;
	
	protected String positionType;
	protected Integer type;
	
	protected Integer managerID;
	
	protected Date addTime;
	protected Date updateTime;
	
	protected String picURL;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String value) {
		this.loginName = value;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getIdentityID() {
		return identityID;
	}
	public void setIdentityID(String value) {
		this.identityID = value;
	}
	
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer value) {
		this.gender = value;
	}
	
	public String getContactInfo1() {
		return contactInfo1;
	}
	public void setContactInfo1(String value) {
		this.contactInfo1 = value;
	}
	
	public String getContactInfo2() {
		return contactInfo2;
	}
	public void setContactInfo2(String value) {
		this.contactInfo2 = value;
	}
	
	public String getEmergency() {
		return emergency;
	}
	public void setEmergency(String value) {
		this.emergency = value;
	}
	
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String value) {
		this.emergencyContact = value;
	}
	
	public String getPositionType() {
		return positionType;
	}
	public void setPositionType(String value) {
		this.positionType = value;
	}
	
	public Integer getManagerID() {
		return managerID;
	}
	public void setManagerID(Integer value) {
		this.managerID = value;
	}
	
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date value) {
		this.addTime = value;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPicURL() {
		return picURL;
	}
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	
}