package org.housemart.server.dao.entities;

import java.util.Date;

public class ResidenceMonthDataEntity {

  int id;
  int residenceId;
  String residenceName;
  int year;
  int month;
  double annualPriceIncrement;
  double annualTurnoverPercent;
  String annualTurnoverRate;
  double rentRevenue;
  double averagePrice;
  double minRentPrice;
  double maxRentPrice;
  Date addTime;
  Date updateTime;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
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
  public int getYear() {
    return year;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public int getMonth() {
    return month;
  }
  public void setMonth(int month) {
    this.month = month;
  }
  public double getAnnualPriceIncrement() {
    return annualPriceIncrement;
  }
  public void setAnnualPriceIncrement(double annualPriceIncrement) {
    this.annualPriceIncrement = annualPriceIncrement;
  }
  public double getAnnualTurnoverPercent() {
    return annualTurnoverPercent;
  }
  public void setAnnualTurnoverPercent(double annualTurnoverPercent) {
    this.annualTurnoverPercent = annualTurnoverPercent;
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
  public Date getAddTime() {
    return addTime;
  }
  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
  public Date getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  public double getAveragePrice() {
    return averagePrice;
  }
  public void setAveragePrice(double averagePrice) {
    this.averagePrice = averagePrice;
  }
  public double getMinRentPrice() {
    return minRentPrice;
  }
  public void setMinRentPrice(double minRentPrice) {
    this.minRentPrice = minRentPrice;
  }
  public double getMaxRentPrice() {
    return maxRentPrice;
  }
  public void setMaxRentPrice(double maxRentPrice) {
    this.maxRentPrice = maxRentPrice;
  }
  
}