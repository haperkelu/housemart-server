/**
 * Created on 2012-11-18
 * 
 * Copyright (c) Comprice, Inc. 2010. All rights reserved.
 */
package org.housemart.server.dao.entities;

import java.util.Date;

/**
 * @author pqin
 */
public class HouseRent {
	Integer count = 0;// 5555, //总记录条数
	Integer id = 0;// 3333, //houseID
	String type = "";// ‘高层’, //房屋类型
	String roomType = "";// ‘两室一厅一卫’ //标题
	String picURL = "";// ’http://xxxxxxx’, //房源图片地址
	String price = "";// ’1000/月’, //价格
	Float priceValue;
	Integer floor = 0;// 5, //楼层
	String direction = "";// ‘南北’, //朝向
	String decorating = "1";// ‘毛坯’, //装修情况
	Boolean combinedRent = false;// true, //是否合租
	Integer fansCount = 0;// 3333, //关注
	Integer askedCount = 0;// 5555, // 问询次数
	Boolean isEmergent = false;// true, //是否急租
	Boolean isNew = false;// true, //是否新
	Boolean isRecommend = false; // 是否推荐
	Boolean isFollow = false;// false //当前用户是否关注
	int rentStatus; // 状态
	String tagList = ""; //标签ID列表，逗号分隔
	String EquipmentList = ""; //租房配置
	Date dealTime;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getPicURL() {
		return picURL;
	}
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDecorating() {
		return decorating;
	}
	public void setDecorating(String decorating) {
		this.decorating = decorating;
	}
	public Boolean getCombinedRent() {
		return combinedRent;
	}
	public void setCombinedRent(Boolean combinedRent) {
		this.combinedRent = combinedRent;
	}
	public Integer getFansCount() {
		return fansCount;
	}
	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}
	public Integer getAskedCount() {
		return askedCount;
	}
	public void setAskedCount(Integer askedCount) {
		this.askedCount = askedCount;
	}
	public Boolean getIsEmergent() {
		return isEmergent;
	}
	public void setIsEmergent(Boolean isEmergent) {
		this.isEmergent = isEmergent;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	public Boolean getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Boolean getIsFollow() {
		return isFollow;
	}
	public void setIsFollow(Boolean isFollow) {
		this.isFollow = isFollow;
	}
	public int getRentStatus() {
		return rentStatus;
	}
	public void setRentStatus(int rentStatus) {
		this.rentStatus = rentStatus;
	}
	public Float getPriceValue() {
		return priceValue;
	}
	public void setPriceValue(Float priceValue) {
		this.priceValue = priceValue;
	}
	public String getTagList() {
		return tagList;
	}
	public void setTagList(String tagList) {
		this.tagList = tagList;
	}
	public String getEquipmentList() {
		return EquipmentList;
	}
	public void setEquipmentList(String equipmentList) {
		EquipmentList = equipmentList;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	
}
