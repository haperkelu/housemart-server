package org.housemart.server.dao.entities;

import java.util.Date;

public class ClientNotesEntity {

	protected Integer id;

	protected String clientUId;
	protected int brokerId;
	protected String note;
	protected int type; //0: alias, 1: note
	
	protected Date addTime;
	protected Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClientUId() {
		return clientUId;
	}
	public void setClientUId(String clientUId) {
		this.clientUId = clientUId;
	}
	public int getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(int brokerId) {
		this.brokerId = brokerId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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