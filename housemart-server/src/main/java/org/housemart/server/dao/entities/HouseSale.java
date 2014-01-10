/**
 * Created on 2012-11-18
 * 
 * Copyright (c) Comprice, Inc. 2010. All rights reserved.
 */
package org.housemart.server.dao.entities;

/**
 * @author pqin
 */
public class HouseSale {
	Integer count = 0;// 5555, //总记录条数
	Integer id = 0;// 3333, //houseID
	String type = "";// ‘高层’, //房屋类型
	String roomType = "";// ‘两室一厅一卫’ //标题
	String picURL = ""; // ’http://xxxxxxx’, //房源图片地址
	String price = "";// ’125万’, //总价
	Float priceValue;
	String avg = "";// ‘2万/平米’, //均价
	Integer floor = 0;// 5, //楼层
	String direction = "";// ‘南北’, //朝向
	Integer fansCount = 0;// 333, //关注
	Integer askedCount = 0;// 5555, // 问询次数
	Boolean isHot = false;// true, //是否热门
	Boolean isEmergent = false;// //是否急售
	Boolean isFollow = false;// false //当前用户是否关注
	String dealTime = "";// ‘xxx’, //交易时间
	String dealPrice = "";// ‘xxx’ //交易价格
	int saleStatus; // 售房状态
	String tagList = "";
	Integer source;

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
	public String getAvg() {
		return avg;
	}
	public void setAvg(String avg) {
		this.avg = avg;
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
	public Boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public Boolean getIsEmergent() {
		return isEmergent;
	}
	public void setIsEmergent(Boolean isEmergent) {
		this.isEmergent = isEmergent;
	}
	public Boolean getIsFollow() {
		return isFollow;
	}
	public void setIsFollow(Boolean isFollow) {
		this.isFollow = isFollow;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(String dealPrice) {
		this.dealPrice = dealPrice;
	}
	public int getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(int saleStatus) {
		this.saleStatus = saleStatus;
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
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	
}
