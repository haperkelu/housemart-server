/**
 * Created on 2013-8-13
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.dao.entities;

import java.util.Date;

public class HouseInteractionNoticeEntity {
  
  int id;
  int houseId;
  int type;
  int status;
  Date addTime;
  Date updateTime;
  
  public static enum StatusEnum {
    Unread(0), Read(1);
    public int value;
    
    StatusEnum(int status) {
      this.value = status;
    }
  }
  
  public static enum TypeEnum {
    ToWithInteraction(1), ToWithoutInteraction(2);
    public int value;
    
    TypeEnum(int type) {
      this.value = type;
    }
  }
  
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
  
  public int getType() {
    return type;
  }
  
  public void setType(int type) {
    this.type = type;
  }
  
  public int getStatus() {
    return status;
  }
  
  public void setStatus(int status) {
    this.status = status;
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
  
}
