/**
 * Created on 2013-8-13
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.dao.entities;

import java.util.Date;

public class HousePicSortEntity {
  
  int id;
  int houseId;
  int type;
  String sort;
  int lastUpdater;
  Date updateTime;
  
  public enum Type {
    Residence(1), RoomType(2), HousePic(3);
    Type(int value) {
      this.value = value;
    }
    
    private int value;
    
    public void setValue(int value) {
      this.value = value;
    }
    
    public int getValue() {
      return value;
    }
    
    public static Type valueOf(int val) {
      Type type = null;
      for (Type t : Type.values()) {
        if (t.getValue() == val) {
          type = t;
          break;
        }
      }
      return type;
    }
  }
  
  @Override
  public boolean equals(Object obj) {
    boolean isEqual = false;
    if (getClass().getName().equals(obj.getClass().getName())) {
      HousePicSortEntity objModel = (HousePicSortEntity) obj;
      if (getId() == objModel.getId()) {
        isEqual = true;
      }
    }
    return isEqual;
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
  
  public String getSort() {
    return sort;
  }
  
  public void setSort(String sort) {
    this.sort = sort;
  }
  
  public int getLastUpdater() {
    return lastUpdater;
  }
  
  public void setLastUpdater(int lastUpdater) {
    this.lastUpdater = lastUpdater;
  }
  
  public Date getUpdateTime() {
    return updateTime;
  }
  
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  
}
