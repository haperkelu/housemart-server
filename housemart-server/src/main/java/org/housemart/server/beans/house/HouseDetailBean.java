/**
 * Created on 2012-9-20
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.beans.house;

/**
 * 
 * @author pqin
 */
public class HouseDetailBean {
  private Integer id = 0;// 3333,
  private String title = "";// ’东方曼哈顿两房精装’, //售房标题
  private String rentTitle = ""; // ‘xxxx’ //租房标题
  private Integer cityId = 0; // 222 //城市ID
  private Integer regionId = 0;// 3333 //行政区ID
  private Integer plateId = 0; // 4444 //板块ID
  private Integer residenceId = 0;// 2222 //小区ID
  private String residenceName = ""; // ‘xxxx’, //小区名称
  private String address = "";// ‘xxx’, //地址
  private String soldAddress = "";// ‘xxx’, //已售房源地址
  private String buildingNo = "";// ‘xxx’, //栋座信息
  private String cellNo = "";// ‘xxx’, //单元号
  private String[] picURL = new String[0]; // [’http://xxxxxxx’, xx], //图片地址列表
  private String[] residencePicURL = new String[0]; //
  private String[] picURLWithSize = new String[0];
  private String[] picURLWithOriginSize = new String[0];
  private String[] residencePicURLWithSize = new String[0]; //
  private String[] residencePicURLWithOriginSize = new String[0]; //
  private String price = ""; // ’125万’, //售价
  private String avg = ""; // ‘2万/平米’ //单价
  private String rentPrice = "";// ‘1000/月’, //租金
  private String equipment = "";// ‘xxxxxx’, //租房设备
  private String decorating = "1";// ‘精装’, //装修
  private String area = "";// ’100米’, //面积
  private String roomType = "";// ‘两室一厅一卫’, //房型
  private String direction = "";// ‘南北’, //朝向
  private String type = "";// ‘老公房’, //类型
  private Integer floor = 0;// ‘1楼’, //楼层
  private String floorLevel = "";// ‘1楼’, //楼层
  private Long buildDate = (long) 0; // ‘1900年’, //年代
  private Boolean isEmergent = false;// true, //是否急售
  private Boolean isRecommend = false;// true, //是否推荐
  private String viewHouseType = "";// ’预约看房’ //看房方式
  private String memo = "";// ‘xxx’, //说明
  private String rentMemo = "";// ‘xxx’, //说明
  private String brokerMobile = "";// ‘12332423’, //经纪人联系方式
  private String brokerName = "";// ‘12332423’, //经纪人联系方式
  private Integer brokerId = 0;// ‘12332423’, //经纪人联系方式
  private Boolean isFollow = false;// false //当前用户是否关注
  private Double lat = (double) 0; // 纬度
  private Double lng = (double) 0; // 经度
  private Double distance = (double) 0; // 离中心点的距离
  private Boolean combinedRent = false;// true, //是否合租
  private Long onboardTime = (long) 0;// ‘xxx’, //审核通过时间
  private Long dealTime = (long) 0;// ‘xxx’, //售房入住时间
  private Long dealTimeRent = (long) 0;// ‘xxx’, //租房入住时间
  
  private String buildDateString = "";
  private String onboardTimeString = "";
  private String dealTimeString = "";
  private String dealTimeRentString = "";
  
  private String saleRec = "";
  private String plateName = ""; // 板块 小区名称 区域
  private int interaction = 0;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getRentTitle() {
    return rentTitle;
  }
  
  public void setRentTitle(String rentTitle) {
    this.rentTitle = rentTitle;
  }
  
  public Integer getCityId() {
    return cityId;
  }
  
  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }
  
  public Integer getRegionId() {
    return regionId;
  }
  
  public void setRegionId(Integer regionId) {
    this.regionId = regionId;
  }
  
  public Integer getPlateId() {
    return plateId;
  }
  
  public void setPlateId(Integer plateId) {
    this.plateId = plateId;
  }
  
  public Integer getResidenceId() {
    return residenceId;
  }
  
  public void setResidenceId(Integer residenceId) {
    this.residenceId = residenceId;
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
  
  public String[] getPicURL() {
    return picURL;
  }
  
  public void setPicURL(String[] picURL) {
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
  
  public String getRentPrice() {
    return rentPrice;
  }
  
  public void setRentPrice(String rentPrice) {
    this.rentPrice = rentPrice;
  }
  
  public String getEquipment() {
    return equipment;
  }
  
  public void setEquipment(String equipment) {
    this.equipment = equipment;
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
  
  public String getRoomType() {
    return roomType;
  }
  
  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }
  
  public String getDirection() {
    return direction;
  }
  
  public void setDirection(String direction) {
    this.direction = direction;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getFloorLevel() {
    return floorLevel;
  }
  
  public void setFloorLevel(String floorLevel) {
    this.floorLevel = floorLevel;
  }
  
  public Long getBuildDate() {
    return buildDate;
  }
  
  public Integer getFloor() {
    return floor;
  }
  
  public void setFloor(Integer floor) {
    this.floor = floor;
  }
  
  public void setBuildDate(Long buildDate) {
    this.buildDate = buildDate;
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
  
  public String getViewHouseType() {
    return viewHouseType;
  }
  
  public void setViewHouseType(String viewHouseType) {
    this.viewHouseType = viewHouseType;
  }
  
  public String getMemo() {
    return memo;
  }
  
  public void setMemo(String memo) {
    this.memo = memo;
  }
  
  public String getBrokerMobile() {
    return brokerMobile;
  }
  
  public void setBrokerMobile(String brokerMobile) {
    this.brokerMobile = brokerMobile;
  }
  
  public String getBrokerName() {
    return brokerName;
  }
  
  public void setBrokerName(String brokerName) {
    this.brokerName = brokerName;
  }
  
  public Integer getBrokerId() {
    return brokerId;
  }
  
  public void setBrokerId(Integer brokerId) {
    this.brokerId = brokerId;
  }
  
  public Boolean getIsFollow() {
    return isFollow;
  }
  
  public void setIsFollow(Boolean isFollow) {
    this.isFollow = isFollow;
  }
  
  public void setLng(Double lng) {
    this.lng = lng;
  }
  
  public Double getLng() {
    return lng;
  }
  
  public void setLat(Double lat) {
    this.lat = lat;
  }
  
  public Double getLat() {
    return lat;
  }
  
  public Double getDistance() {
    return distance;
  }
  
  public void setDistance(Double distance) {
    this.distance = distance;
  }
  
  public Boolean getCombinedRent() {
    return combinedRent;
  }
  
  public void setCombinedRent(Boolean combinedRent) {
    this.combinedRent = combinedRent;
  }
  
  public Long getOnboardTime() {
    return onboardTime;
  }
  
  public void setOnboardTime(Long onboardTime) {
    this.onboardTime = onboardTime;
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
  
  public String getRentMemo() {
    return rentMemo;
  }
  
  public void setRentMemo(String rentMemo) {
    this.rentMemo = rentMemo;
  }
  
  public Long getDealTime() {
    return dealTime;
  }
  
  public void setDealTime(Long dealTime) {
    this.dealTime = dealTime;
  }
  
  public Long getDealTimeRent() {
    return dealTimeRent;
  }
  
  public void setDealTimeRent(Long dealTimeRent) {
    this.dealTimeRent = dealTimeRent;
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
  
  public String getDealTimeRentString() {
    return dealTimeRentString;
  }
  
  public void setDealTimeRentString(String dealTimeRentString) {
    this.dealTimeRentString = dealTimeRentString;
  }
  
  public String getBuildDateString() {
    return buildDateString;
  }
  
  public void setBuildDateString(String buildTimeString) {
    this.buildDateString = buildTimeString;
  }
  
  public String getSaleRec() {
    return saleRec;
  }
  
  public void setSaleRec(String saleRec) {
    this.saleRec = saleRec;
  }
  
  public String[] getPicURLWithSize() {
    return picURLWithSize;
  }
  
  public void setPicURLWithSize(String[] picURLWithSize) {
    this.picURLWithSize = picURLWithSize;
  }
  
  public String[] getResidencePicURLWithSize() {
    return residencePicURLWithSize;
  }
  
  public void setResidencePicURLWithSize(String[] residencePicURLWithSize) {
    this.residencePicURLWithSize = residencePicURLWithSize;
  }
  
  public String[] getPicURLWithOriginSize() {
    return picURLWithOriginSize;
  }
  
  public void setPicURLWithOriginSize(String[] picURLWithOriginSize) {
    this.picURLWithOriginSize = picURLWithOriginSize;
  }
  
  public String[] getResidencePicURLWithOriginSize() {
    return residencePicURLWithOriginSize;
  }
  
  public void setResidencePicURLWithOriginSize(String[] residencePicURLWithOriginSize) {
    this.residencePicURLWithOriginSize = residencePicURLWithOriginSize;
  }
  
  public String getPlateName() {
    return plateName;
  }
  
  public void setPlateName(String plateName) {
    this.plateName = plateName;
  }
  
  public int getInteraction() {
    return interaction;
  }
  
  public void setInteraction(int interaction) {
    this.interaction = interaction;
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

}
