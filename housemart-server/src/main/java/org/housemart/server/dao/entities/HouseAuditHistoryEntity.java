/**
 * Created on 2012-10-21
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.dao.entities;

import java.util.Date;

/**
 * @author pqin
 */
public class HouseAuditHistoryEntity {
	private Integer id;
	private Integer houseId;
	private Integer managerId;
	private Integer type;
	private Integer committerId;
	private String preContent;
	private String postContent;
	private Integer result;
	private Date addTime;
	private Date updateTime;
	public enum ResultEnum {
		Default(0), Agree(1), Reject(2);
		private int value;
		ResultEnum(int value) {
			this.setValue(value);
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getCommitterId() {
		return committerId;
	}
	public void setCommitterId(Integer committerId) {
		this.committerId = committerId;
	}
	public String getPreContent() {
		return preContent;
	}
	public void setPreContent(String preContent) {
		this.preContent = preContent;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
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
