/**
 * Created on 2012-11-18
 * 
 * Copyright (c) Comprice, Inc. 2010. All rights reserved.
 */
package org.housemart.server.beans.house;

/**
 * @author pqin
 */
public class HouseSaleBean {
  private Integer id = 0;// 3333, //houseID
  private String address = "";
  private String type = "";// ‘高层’, //房屋类型
  private String roomType = "";// ‘两室一厅一卫’ //标题
  private String picURL = ""; // ’http://xxxxxxx’, //房源图片地址
  private String picURLWithSize = ""; // ’http://xxxxxxx’, //房源图片地址
  private String picURLWithOriginSize = ""; // ’http://xxxxxxx’, //房源图片地址
  private String[] residencePicURL = new String[0]; //
  private String[] residencePicURLWithSize = new String[0]; //
  private String[] residencePicURLWithOriginSize = new String[0]; //
  private String price = "";// ’125万’, //总价
  private String avg = "";// ‘2万/平米’, //均价
  private Integer floor = 0;// 5, //楼层
  private String floorLevel = "";// 5, //楼层
  private String direction = "";// ‘南北’, //朝向
  private String decorating = "1";// ‘毛坯’, //装修情况
  private String area = "";// ’100米’, //面积
  private Integer fansCount = 0;// 333, //关注
  private Integer askedCount = 0;// 5555, // 问询次数
  private Boolean isHot = false;// true, //是否热门
  private Boolean isEmergent = false;// //是否急售
  private Boolean isRecommend = false;// //是否急售
  private Boolean isFollow = false;// false //当前用户是否关注
  private Long onboardTime = (long) 0;// ‘xxx’, //审核通过时间
  private Long dealTime = (long) 0;// ‘xxx’, //交易时间
  private String dealPrice = "";// ‘xxx’ //交易价格
  private Integer saleStatus = 0;
  private Double lat = (double) 0; // 纬度
  private Double lng = (double) 0; // 经度
  private Double distance = (double) 0; // 离中心点的距离
  private String residenceName = ""; // 小区名称
  
  private String updateTimeString = "";
  private String onboardTimeString = "";
  private String dealTimeString = "";
  private String applyTimeString = "";
  private String auditComments = "";
  
  private String saleRec = "";
  private String plateName = ""; // 板块 小区名称 区域
  private String soldAddress = ""; // 已售房源的地址
  private String buildingNo = ""; // 栋座信息
  private String cellNo = ""; // 单元号
  
  private Long followTime = (long) 0; // 关注时间
  
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
  
  public String getFloorLevel() {
    return floorLevel;
  }
  
  public void setFloorLevel(String floorLevel) {
    this.floorLevel = floorLevel;
  }
  
  public String getDirection() {
    return direction;
  }
  
  public Integer getFloor() {
    return floor;
  }
  
  public void setFloor(Integer floor) {
    this.floor = floor;
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
  
  public Long getDealTime() {
    return dealTime;
  }
  
  public void setDealTime(Long dealTime) {
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
  
  public void setSaleStatus(Integer saleStatus) {
    this.saleStatus = saleStatus;
  }
  
  public Double getLat() {
    return lat;
  }
  
  public void setLat(Double lat) {
    this.lat = lat;
  }
  
  public Double getLng() {
    return lng;
  }
  
  public void setLng(Double lng) {
    this.lng = lng;
  }
  
  public Double getDistance() {
    return distance;
  }
  
  public void setDistance(Double distance) {
    this.distance = distance;
  }
  
  public String getDecorating() {
    return decorating;
  }
  
  public void setDecorating(String decorating) {
    this.decorating = decorating;
  }
  
  public String getArea() {
    return area;
  }
  
  public void setArea(String area) {
    this.area = area;
  }
  
  public Long getOnboardTime() {
    return onboardTime;
  }
  
  public void setOnboardTime(Long onboardTime) {
    this.onboardTime = onboardTime;
  }
  
  public String getResidenceName() {
    return residenceName;
  }
  
  public void setResidenceName(String residenceName) {
    this.residenceName = residenceName;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public String getOnboardTimeString() {
    return onboardTimeString;
  }
  
  public void setOnboardTimeString(String onboardTimeString) {
    this.onboardTimeString = onboardTimeString;
  }
  
  public String getDealTimeString() {
    return dealTimeString;
  }
  
  public void setDealTimeString(String dealTimeString) {
    this.dealTimeString = dealTimeString;
  }
  
  public String getSaleRec() {
    return saleRec;
  }
  
  public void setSaleRec(String saleRec) {
    this.saleRec = saleRec;
  }
  
  public String getPicURLWithSize() {
    return picURLWithSize;
  }
  
  public void setPicURLWithSize(String picURLWithSize) {
    this.picURLWithSize = picURLWithSize;
  }
  
  public String getPicURLWithOriginSize() {
    return picURLWithOriginSize;
  }
  
  public void setPicURLWithOriginSize(String picURLWithOriginSize) {
    this.picURLWithOriginSize = picURLWithOriginSize;
  }
  
  public String getPlateName() {
    return plateName;
  }
  
  public void setPlateName(String plateName) {
    this.plateName = plateName;
  }
  
  public String[] getResidencePicURLWithOriginSize() {
    return residencePicURLWithOriginSize;
  }
  
  public void setResidencePicURLWithOriginSize(String[] residencePicURLWithOriginSize) {
    this.residencePicURLWithOriginSize = residencePicURLWithOriginSize;
  }
  
  public String[] getResidencePicURLWithSize() {
    return residencePicURLWithSize;
  }
  
  public void setResidencePicURLWithSize(String[] residencePicURLWithSize) {
    this.residencePicURLWithSize = residencePicURLWithSize;
  }
  
  public String[] getResidencePicURL() {
    return residencePicURL;
  }
  
  public void setResidencePicURL(String[] residencePicURL) {
    this.residencePicURL = residencePicURL;
  }
  
  public String getSoldAddress() {
    return soldAddress;
  }
  
  public void setSoldAddress(String soldAddress) {
    this.soldAddress = soldAddress;
  }
  
  public String getBuildingNo() {
    return buildingNo;
  }
  
  public void setBuildingNo(String buildingNo) {
    this.buildingNo = buildingNo;
  }

  public String getCellNo() {
    return cellNo;
  }

  public void setCellNo(String cellNo) {
    this.cellNo = cellNo;
  }

  public Long getFollowTime() {
    return followTime;
  }

  public void setFollowTime(Long followTime) {
    this.followTime = followTime;
  }

public String getApplyTimeString() {
	return applyTimeString;
}

public void setApplyTimeString(String applyTimeString) {
	this.applyTimeString = applyTimeString;
}

public String getAuditComments() {
	return auditComments;
}

public void setAuditComments(String auditComments) {
	this.auditComments = auditComments;
}

public String getUpdateTimeString() {
	return updateTimeString;
}

public void setUpdateTimeString(String updateTimeString) {
	this.updateTimeString = updateTimeString;
}

}
