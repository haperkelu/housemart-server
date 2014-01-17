/**
 * Created on 2012-11-18
 * 
 * Copyright (c) Comprice, Inc. 2010. All rights reserved.
 */
package org.housemart.server.beans.house;

/**
 * @author pqin
 */
public class HouseRentBean {
  private Integer id = 0;// 3333, //houseID
  private String address = "";
  private String type = "";// ‘高层’, //房屋类型
  private String roomType = "";// ‘两室一厅一卫’ //标题
  private String picURL = "";// ’http://xxxxxxx’, //房源图片地址
  private String picURLWithSize = "";// ’http://xxxxxxx’, //房源图片地址
  private String picURLWithOriginSize = "";// ’http://xxxxxxx’, //房源图片地址
  private String[] residencePicURL = new String[0]; //
  private String[] residencePicURLWithSize = new String[0]; //
  private String[] residencePicURLWithOriginSize = new String[0]; //
  private String price = "";// ’1000/月’, //价格
  private String rentPrice = "";// ’1000/月’, //价格
  private Integer floor = 0;// 5, //楼层
  private String floorLevel = "";// 5, //楼层
  private String direction = "";// ‘南北’, //朝向
  private String decorating = "1";// ‘毛坯’, //装修情况
  private String area = "";// ’100米’, //面积
  private Boolean combinedRent = false;// true, //是否合租
  private Integer fansCount = 0;// 3333, //关注
  private Integer askedCount = 0;// 5555, // 问询次数
  private Boolean isEmergent = false;// true, //是否急租
  private Boolean isNew = false;// true, //是否新
  private Boolean isRecommend = false; // 是否推荐
  private Boolean isFollow = false;// false //当前用户是否关注
  private Integer rentStatus = 0;
  private Long onboardTime = (long) 0;// ‘xxx’, //审核通过时间
  private Long dealTimeRent = (long) 0; // 出租时间
  private Double lat = (double) 0; // 纬度
  private Double lng = (double) 0; // 经度
  private Double distance = (double) 0; // 离中心点的距离
  
  private String updateTimeString = "";
  private String onboardTimeString = "";
  private String dealTimeRentString = "";
  private String applyTimeString = "";
  private String auditComments = "";
  
  private String rentRec = "";
  private String plateName = ""; // 板块 小区名称 区域
  private String residenceName = ""; //小区名/房源地址
  
  private Long followTime = (long) 0;// ‘xxx’, //关注时间
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
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
  
  public Integer getRentStatus() {
    return rentStatus;
  }
  
  public void setRentStatus(Integer rentStatus) {
    this.rentStatus = rentStatus;
  }
  
  public void setDealTimeRent(Long dealTimeRent) {
    this.dealTimeRent = dealTimeRent;
  }
  
  public Long getDealTimeRent() {
    return dealTimeRent;
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
  
  public String getOnboardTimeString() {
    return onboardTimeString;
  }
  
  public void setOnboardTimeString(String onboardTimeString) {
    this.onboardTimeString = onboardTimeString;
  }
  
  public String getDealTimeRentString() {
    return dealTimeRentString;
  }
  
  public void setDealTimeRentString(String dealTimeRentString) {
    this.dealTimeRentString = dealTimeRentString;
  }
  
  public String getRentRec() {
    return rentRec;
  }
  
  public void setRentRec(String saleRec) {
    this.rentRec = saleRec;
  }
  
  public String getPicURLWithSize() {
    return picURLWithSize;
  }
  
  public void setPicURLWithSize(String picURLWithSize) {
    this.picURLWithSize = picURLWithSize;
  }

  public String getPlateName() {
    return plateName;
  }

  public void setPlateName(String plateName) {
    this.plateName = plateName;
  }

  public String getPicURLWithOriginSize() {
    return picURLWithOriginSize;
  }

  public void setPicURLWithOriginSize(String picURLWithOriginSize) {
    this.picURLWithOriginSize = picURLWithOriginSize;
  }

  public String[] getResidencePicURLWithSize() {
    return residencePicURLWithSize;
  }

  public void setResidencePicURLWithSize(String[] residencePicURLWithSize) {
    this.residencePicURLWithSize = residencePicURLWithSize;
  }

  public String[] getResidencePicURLWithOriginSize() {
    return residencePicURLWithOriginSize;
  }

  public void setResidencePicURLWithOriginSize(String[] residencePicURLWithOriginSize) {
    this.residencePicURLWithOriginSize = residencePicURLWithOriginSize;
  }

  public String[] getResidencePicURL() {
    return residencePicURL;
  }

  public void setResidencePicURL(String[] residencePicURL) {
    this.residencePicURL = residencePicURL;
  }

  public Long getFollowTime() {
    return followTime;
  }

  public void setFollowTime(Long followTime) {
    this.followTime = followTime;
  }

	public String getResidenceName() {
		return residenceName;
	}
	
	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
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

	public String getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(String rentPrice) {
		this.rentPrice = rentPrice;
	}

}
