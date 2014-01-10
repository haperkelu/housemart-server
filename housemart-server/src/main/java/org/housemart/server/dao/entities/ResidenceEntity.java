package org.housemart.server.dao.entities;

import org.housemart.server.beans.MonthTrend;

public class ResidenceEntity {
  
  private int id;
  private String address = "";
  private int cityId;
  private int plateId;
  private String residenceName = "";
  private String picURL = ""; // 逗号分隔，?house_pic where type = 1
  private double price; // ?
  // private double trend; // 本月平均挂牌价-上月平均挂牌价/上月平均挂牌价
  private double lastMonthAvgPrice;
  private double thisMonthAvgPrice;
  private MonthTrend[] monthTrend = new MonthTrend[0]; // ?
  private int onSaleCount;
  // private String priceRange = "";
  private int minPrice;
  private int maxPrice;
  private int lastYearDealCount;
  private int lastMonthDealCount; // ?
  private int lastSeasonDealCount; // ?
  private int onRentCount;
  // private String rentRange = "";
  private int minRentPrice;
  private int maxRentPrice;
  private int lastSeasonRentCount; // ?
  private String houseHold; // 总户数headcount from residence
  private Double greenRate = (double) 0;
  private String parking = "";
  private Double volumeRate = (double) 0;
  private String propertyType = "";
  private String propertyFee = "";
  private String developer = "";
  private String finishedTime = "";
  private Boolean isFollow = false; // ?// 是否关注小区，应该取消这个字段了，不用管
  private int fansCount; // ?// 关注数，取消了，不用管
  private String lat = "";
  private String lng = "";
  
  private String regionName = "";
  private String plateName = "";
  
  private double rentRevenue = 0; // 租金回报
  private String annualTurnoverRate = "";// 年换手率
  private double annualTurnoverPercent = 0;// 年换手率
  private double annualPriceIncrement = 0; // 年度涨幅
  
  private int forceShow = 0;
  private int zombie = 0;
  
  public static enum ForceShowEnum {
    Default(0), Show(1);
    public int value;
    ForceShowEnum(int forceShow) {
      this.value = forceShow;
    }
  }
  
  public static enum ZombieEnum {
    Default(0), Zombie(1);
    public int value;
    ZombieEnum(int zombie) {
      this.value = zombie;
    }
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public int getCityId() {
    return cityId;
  }
  
  public void setCityId(int cityId) {
    this.cityId = cityId;
  }
  
  public int getPlateId() {
    return plateId;
  }
  
  public void setPlateId(int plateId) {
    this.plateId = plateId;
  }
  
  public String getResidenceName() {
    return residenceName;
  }
  
  public void setResidenceName(String residenceName) {
    this.residenceName = residenceName;
  }
  
  public String getPicURL() {
    return picURL;
  }
  
  public void setPicURL(String picURL) {
    this.picURL = picURL;
  }
  
  public double getPrice() {
    return price;
  }
  
  public void setPrice(double price) {
    this.price = price;
  }
  
  public MonthTrend[] getMonthTrend() {
    return monthTrend;
  }
  
  public void setMonthTrend(MonthTrend[] monthTrend) {
    this.monthTrend = monthTrend;
  }
  
  public int getOnSaleCount() {
    return onSaleCount;
  }
  
  public void setOnSaleCount(int onSaleCount) {
    this.onSaleCount = onSaleCount;
  }
  
  public int getLastYearDealCount() {
    return lastYearDealCount;
  }
  
  public void setLastYearDealCount(int lastYearDealCount) {
    this.lastYearDealCount = lastYearDealCount;
  }
  
  public int getLastMonthDealCount() {
    return lastMonthDealCount;
  }
  
  public void setLastMonthDealCount(int lastMonthDealCount) {
    this.lastMonthDealCount = lastMonthDealCount;
  }
  
  public int getLastSeasonDealCount() {
    return lastSeasonDealCount;
  }
  
  public void setLastSeasonDealCount(int lastSeasonDealCount) {
    this.lastSeasonDealCount = lastSeasonDealCount;
  }
  
  public int getOnRentCount() {
    return onRentCount;
  }
  
  public void setOnRentCount(int onRentCount) {
    this.onRentCount = onRentCount;
  }
  
  public int getLastSeasonRentCount() {
    return lastSeasonRentCount;
  }
  
  public void setLastSeasonRentCount(int lastSeasonRentCount) {
    this.lastSeasonRentCount = lastSeasonRentCount;
  }
  
  /**
   * @return the houseHold
   */
  public String getHouseHold() {
    return houseHold;
  }
  
  /**
   * @param houseHold
   *          the houseHold to set
   */
  public void setHouseHold(String houseHold) {
    this.houseHold = houseHold;
  }
  
  public Double getGreenRate() {
    return greenRate;
  }
  
  public void setGreenRate(Double greenRate) {
    this.greenRate = greenRate;
  }
  
  public String getParking() {
    return parking;
  }
  
  public void setParking(String parking) {
    this.parking = parking;
  }
  
  public Double getVolumeRate() {
    return volumeRate;
  }
  
  public void setVolumeRate(Double volumeRate) {
    this.volumeRate = volumeRate;
  }
  
  public String getPropertyType() {
    return propertyType;
  }
  
  public void setPropertyType(String type) {
    this.propertyType = type;
  }
  
  public String getPropertyFee() {
    return propertyFee;
  }
  
  public void setPropertyFee(String propertyFee) {
    this.propertyFee = propertyFee;
  }
  
  public String getDeveloper() {
    return developer;
  }
  
  public void setDeveloper(String developer) {
    this.developer = developer;
  }
  
  public String getFinishedTime() {
    return finishedTime;
  }
  
  public void setFinishedTime(String finishedTime) {
    this.finishedTime = finishedTime;
  }
  
  public Boolean getIsFollow() {
    return isFollow;
  }
  
  public void setIsFollow(Boolean isFollow) {
    this.isFollow = isFollow;
  }
  
  public int getFansCount() {
    return fansCount;
  }
  
  public void setFansCount(int fansCount) {
    this.fansCount = fansCount;
  }
  
  public String getLat() {
    return lat;
  }
  
  public void setLat(String lat) {
    this.lat = lat;
  }
  
  public String getLng() {
    return lng;
  }
  
  public void setLng(String lng) {
    this.lng = lng;
  }
  
  public double getLastMonthAvgPrice() {
    return lastMonthAvgPrice;
  }
  
  public void setLastMonthAvgPrice(double lastMonthAvgPrice) {
    this.lastMonthAvgPrice = lastMonthAvgPrice;
  }
  
  public double getThisMonthAvgPrice() {
    return thisMonthAvgPrice;
  }
  
  public void setThisMonthAvgPrice(double thisMonthAvgPrice) {
    this.thisMonthAvgPrice = thisMonthAvgPrice;
  }
  
  public int getMinPrice() {
    return minPrice;
  }
  
  public void setMinPrice(int minPrice) {
    this.minPrice = minPrice;
  }
  
  public int getMaxPrice() {
    return maxPrice;
  }
  
  public void setMaxPrice(int maxPrice) {
    this.maxPrice = maxPrice;
  }
  
  public int getMinRentPrice() {
    return minRentPrice;
  }
  
  public void setMinRentPrice(int minRentPrice) {
    this.minRentPrice = minRentPrice;
  }
  
  public int getMaxRentPrice() {
    return maxRentPrice;
  }
  
  public void setMaxRentPrice(int maxRentPrice) {
    this.maxRentPrice = maxRentPrice;
  }
  
  public String getRegionName() {
    return regionName;
  }
  
  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }
  
  public String getPlateName() {
    return plateName;
  }
  
  public void setPlateName(String plateName) {
    this.plateName = plateName;
  }
  
  public double getRentRevenue() {
    return rentRevenue;
  }
  
  public void setRentRevenue(double rentRevenue) {
    this.rentRevenue = rentRevenue;
  }
  
  public String getAnnualTurnoverRate() {
    return annualTurnoverRate;
  }

  public void setAnnualTurnoverRate(String annualTurnoverRate) {
    this.annualTurnoverRate = annualTurnoverRate;
  }

  public double getAnnualTurnoverPercent() {
    return annualTurnoverPercent;
  }

  public void setAnnualTurnoverPercent(double annualTurnoverPercent) {
    this.annualTurnoverPercent = annualTurnoverPercent;
  }

  public double getAnnualPriceIncrement() {
    return annualPriceIncrement;
  }
  
  public void setAnnualPriceIncrement(double annualPriceIncrement) {
    this.annualPriceIncrement = annualPriceIncrement;
  }

  public int getForceShow() {
    return forceShow;
  }

  public void setForceShow(int forceShow) {
    this.forceShow = forceShow;
  }

  public int getZombie() {
    return zombie;
  }

  public void setZombie(int zombie) {
    this.zombie = zombie;
  }

}
