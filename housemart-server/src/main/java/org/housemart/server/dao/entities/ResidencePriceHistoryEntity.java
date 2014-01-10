package org.housemart.server.dao.entities;

import java.util.Date;

public class ResidencePriceHistoryEntity {
  
  private int residenceId;
  private int avgPrice;
  private String month;
  private Date addTime;
  
  public int getAvgPrice() {
    return avgPrice;
  }
  
  public void setAvgPrice(int price1) {
    this.avgPrice = price1;
  }
  
  public int getResidenceId() {
    return residenceId;
  }
  
  public void setResidenceId(int residenceId) {
    this.residenceId = residenceId;
  }
  
  public Date getAddTime() {
    return addTime;
  }
  
  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
  
  public String getMonth() {
    return month;
  }
  
  public void setMonth(String month) {
    this.month = month;
  }
  
}
