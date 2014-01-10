package org.housemart.server.beans;

public class ResidenceDetail {
  
  private int id;
  private String address = "";
  private int cityId;
  private int plateId;
  private String residenceName = "";
  private String[] picURL = new String[0];
  private String[] picURLWithSize = new String[0];
  private String[] picURLWithOriginSize = new String[0];
  private String price;
  private String rentPrice;
  private double trend;
  private MonthTrend[] monthTrend = new MonthTrend[0];
  private int onSaleCount;
  private String priceRange = "";
  private int lastMonthDealCount;
  private int lastSeasonDealCount;
  private int onRentCount;
  private String rentRange = "";
  private int lastSeasonRentCount;
  private String houseHold;
  private double greenRate;
  private String parking = "";
  private double volumnRate;
  private String type = "";
  private String propertyFee = "";
  private String developer = "";
  private String finishedTime = "";
  private Boolean isFollow = false;
  
  private String annualPriceIncreasement = "0.0%"; // 年度涨幅
  private String annualTurnoverRate = "0.0%"; // 年换手率
  private String rentRevenue = "0.0%"; // 租金回报
  
  private int forceShow = 0;
  private int zombie = 0;
  
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
  
  public double getTrend() {
    return trend;
  }
  
  public void setTrend(double trend) {
    this.trend = trend;
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
  
  public String getPriceRange() {
    return priceRange;
  }
  
  public void setPriceRange(String priceRange) {
    this.priceRange = priceRange;
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
  
  public String getRentRange() {
    return rentRange;
  }
  
  public void setRentRange(String rentRange) {
    this.rentRange = rentRange;
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
  
  public double getGreenRate() {
    return greenRate;
  }
  
  public void setGreenRate(double greenRate) {
    this.greenRate = greenRate;
  }
  
  public String getParking() {
    return parking;
  }
  
  public void setParking(String parking) {
    this.parking = parking;
  }
  
  public double getVolumnRate() {
    return volumnRate;
  }
  
  public void setVolumnRate(double volumnRate) {
    this.volumnRate = volumnRate;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
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
  
  public String getRentPrice() {
    return rentPrice;
  }
  
  public void setRentPrice(String rentPrice) {
    this.rentPrice = rentPrice;
  }
  
  public String[] getPicURLWithSize() {
    return picURLWithSize;
  }
  
  public void setPicURLWithSize(String[] picURLWithSize) {
    this.picURLWithSize = picURLWithSize;
  }
  
  public String[] getPicURLWithOriginSize() {
    return picURLWithOriginSize;
  }
  
  public void setPicURLWithOriginSize(String[] picURLWithOriginSize) {
    this.picURLWithOriginSize = picURLWithOriginSize;
  }
  
  public int getLastMonthDealCount() {
    return lastMonthDealCount;
  }
  
  public void setLastMonthDealCount(int lastMonthDealCount) {
    this.lastMonthDealCount = lastMonthDealCount;
  }
  
  public String getAnnualPriceIncreasement() {
    return annualPriceIncreasement;
  }
  
  public void setAnnualPriceIncreasement(String annualPriceIncreasement) {
    this.annualPriceIncreasement = annualPriceIncreasement;
  }
  
  public String getAnnualTurnoverRate() {
    return annualTurnoverRate;
  }
  
  public void setAnnualTurnoverRate(String annualTurnoverRate) {
    this.annualTurnoverRate = annualTurnoverRate;
  }
  
  public String getRentRevenue() {
    return rentRevenue;
  }
  
  public void setRentRevenue(String rentRevenue) {
    this.rentRevenue = rentRevenue;
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
