package org.housemart.server.dao.entities;

import java.util.Date;

public class NotificationEntity {

	protected Integer id;

	private Integer houseId;
	private Integer residenceId;
	private String clientUId;
	private Integer type; //售: 1, 租: 2, 小区: 3
	
	//1: 在售->不售
	//2: 在租->不租
	//3: 价格变化
	//4: 新增房源
	private Integer action;
	private Integer countSale;
	private Integer countRent;
	
	private Date actionTime;
	
	private Integer houseFollow;
	private Integer residenceFollow;

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

	public String getClientUId() {
		return clientUId;
	}

	public void setClientUId(String clientUId) {
		this.clientUId = clientUId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public Integer getResidenceId() {
		return residenceId;
	}

	public void setResidenceId(Integer residenceId) {
		this.residenceId = residenceId;
	}

	public Integer getCountSale() {
		return countSale;
	}

	public void setCountSale(Integer countSale) {
		this.countSale = countSale;
	}

	public Integer getCountRent() {
		return countRent;
	}

	public void setCountRent(Integer countRent) {
		this.countRent = countRent;
	}

	public Integer getHouseFollow() {
		return houseFollow;
	}

	public void setHouseFollow(Integer houseFollow) {
		this.houseFollow = houseFollow;
	}

	public Integer getResidenceFollow() {
		return residenceFollow;
	}

	public void setResidenceFollow(Integer residenceFollow) {
		this.residenceFollow = residenceFollow;
	}
	
}