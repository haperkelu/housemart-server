package org.housemart.server.beans;

import java.util.Date;

public class AuditStatusAndContentBean {

	private int id;
	private int houseId;
	private String plateName;
	private String residenceName;
	private String roomNo;
	private int saleStatus;
	private int rentStatus;
	private float area;
	private String contactName;
	private String contactMobile;
	private String content;
	private Date addTime;

	//
	private int roomType;
	private Date buildTime;
	private String houseType;
	private float occupiedArea;
	private int decorating;
	private float salePrice;
	private float saleBasePrice;
	private String dealType;
	private int saleRec;
	private String saleTagList;
	private String saleMemo;
	private int rentType;
	private int rentRec;
	private float rentPrice;
	private float rentBasePrice;
	private String rentMemo;
	private String rentTagList;
	private int viewHouseType;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public String getPlateName() {
		return plateName;
	}
	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}
	public String getResidenceName() {
		return residenceName;
	}
	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(int saleStatus) {
		this.saleStatus = saleStatus;
	}
	public int getRentStatus() {
		return rentStatus;
	}
	public void setRentStatus(int rentStatus) {
		this.rentStatus = rentStatus;
	}
	public float getArea() {
		return area;
	}
	public void setArea(float area) {
		this.area = area;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public int getRoomType() {
		return roomType;
	}
	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}
	public Date getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public float getOccupiedArea() {
		return occupiedArea;
	}
	public void setOccupiedArea(float occupiedArea) {
		this.occupiedArea = occupiedArea;
	}
	public int getDecorating() {
		return decorating;
	}
	public void setDecorating(int decorating) {
		this.decorating = decorating;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public int getSaleRec() {
		return saleRec;
	}
	public void setSaleRec(int saleRec) {
		this.saleRec = saleRec;
	}
	public int getRentType() {
		return rentType;
	}
	public void setRentType(int rentType) {
		this.rentType = rentType;
	}
	public int getRentRec() {
		return rentRec;
	}
	public void setRentRec(int rentRec) {
		this.rentRec = rentRec;
	}
	public int getViewHouseType() {
		return viewHouseType;
	}
	public void setViewHouseType(int viewHouseType) {
		this.viewHouseType = viewHouseType;
	}
	public float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}
	public float getSaleBasePrice() {
		return saleBasePrice;
	}
	public void setSaleBasePrice(float saleBasePrice) {
		this.saleBasePrice = saleBasePrice;
	}
	public String getSaleTagList() {
		return saleTagList;
	}
	public void setSaleTagList(String saleTagList) {
		this.saleTagList = saleTagList;
	}
	public String getSaleMemo() {
		return saleMemo;
	}
	public void setSaleMemo(String saleMemo) {
		this.saleMemo = saleMemo;
	}
	public float getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(float rentPrice) {
		this.rentPrice = rentPrice;
	}
	public float getRentBasePrice() {
		return rentBasePrice;
	}
	public void setRentBasePrice(float rentBasePrice) {
		this.rentBasePrice = rentBasePrice;
	}
	public String getRentMemo() {
		return rentMemo;
	}
	public void setRentMemo(String rentMemo) {
		this.rentMemo = rentMemo;
	}
	public String getRentTagList() {
		return rentTagList;
	}
	public void setRentTagList(String rentTagList) {
		this.rentTagList = rentTagList;
	}

}
