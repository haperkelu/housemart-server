package org.housemart.server.beans;

public class ResidenceBean {
  
  private int id;
  private Position position = new Position((double) 0, (double) 0);
  private int residenceId;
  private String residenceName = "";
  private String priceRange = "";
  private String rentRange = "";
  private String[] picURL = new String[0]; // ?
  private String[] picURLWithSize = new String[0]; // ?
  private String price = ""; // 小区均价
  private String rentPrice = ""; // ?
  private double trend; // ?
  private int onSaleCount; // ?
  private int onRentCount; // ?
  private int fansCount; // ?
  private Boolean isFollow = false; // ?
  private String distance = "";
  
  private String plateName; // 板块 小区名称 区域
  private int lastMonthDealCount;
  private int lastSeasonDealCount;
  
  private Long followTime = (long) 0;// ‘xxx’, //关注时间

  private String annualPriceIncreasement = "0.0%"; // 年度涨幅
  private String annualTurnoverRate = "0.0%"; // 年换手率
  private String rentRevenue = "0.0%"; // 租金回报
  private String defaultFilterOption = FilterOption.annualPriceIncreasement.toString();
  
  private int forceShow = 0;
  private int zombie = 0;
  
  public static enum FilterOption {
    annualPriceIncreasement, annualTurnoverRate, rentRevenue, price
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public Position getPosition() {
    return position;
  }
  
  public void setPosition(Position position) {
    this.position = position;
  }
  
  public int getResidenceId() {
    return residenceId;
  }
  
  public void setResidenceId(int residenceId) {
    this.residenceId = residenceId;
  }
  
  public String getResidenceName() {
    return residenceName;
  }
  
  public void setResidenceName(String residenceName) {
    this.residenceName = residenceName;
  }
  
  public String getPriceRange() {
    return priceRange;
  }
  
  public void setPriceRange(String priceRange) {
    this.priceRange = priceRange;
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
  
  public int getOnSaleCount() {
    return onSaleCount;
  }
  
  public void setOnSaleCount(int onSaleCount) {
    this.onSaleCount = onSaleCount;
  }
  
  public int getFansCount() {
    return fansCount;
  }
  
  public void setFansCount(int fansCount) {
    this.fansCount = fansCount;
  }
  
  public int getOnRentCount() {
    return onRentCount;
  }
  
  public void setOnRentCount(int onRentCount) {
    this.onRentCount = onRentCount;
  }
  
  public Boolean getIsFollow() {
    return isFollow;
  }
  
  public void setIsFollow(Boolean isFollow) {
    this.isFollow = isFollow;
  }
  
  public String getRentRange() {
    return rentRange;
  }
  
  public void setRentRange(String rentPriceRange) {
    this.rentRange = rentPriceRange;
  }
  
  public String getRentPrice() {
    return rentPrice;
  }
  
  public void setRentPrice(String rentPrice) {
    this.rentPrice = rentPrice;
  }
  
  public String getDistance() {
    return distance;
  }
  
  public void setDistance(String distance) {
    this.distance = distance;
  }
  
  public String getPlateName() {
    return plateName;
  }
  
  public void setPlateName(String plateName) {
    this.plateName = plateName;
  }
  
  public String[] getPicURLWithSize() {
    return picURLWithSize;
  }
  
  public void setPicURLWithSize(String[] picURLWithSize) {
    this.picURLWithSize = picURLWithSize;
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
  
  public String getDefaultFilterOption() {
    return defaultFilterOption;
  }
  
  public void setDefaultFilterOption(String defaultFilterOption) {
    this.defaultFilterOption = defaultFilterOption;
  }

  public Long getFollowTime() {
    return followTime;
  }

  public void setFollowTime(Long followTime) {
    this.followTime = followTime;
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
