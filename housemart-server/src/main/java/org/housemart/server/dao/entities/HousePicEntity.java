/**
 * Created on 2012-10-30
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.dao.entities;

import java.util.Date;

/**
 * @author pqin
 */
public class HousePicEntity {
  private Integer id;
  private Integer residenceId;
  private Integer houseId;
  private Integer type;
  private String url;
  private String cloudUrl;
  private Integer status;
  private Date addTime;
  private Date updateTime;
  
  public enum Type {
    ExternalResidence(0), Residence(1), RoomType(2), HousePic(3);
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
      HousePicEntity objModel = (HousePicEntity) obj;
      if (getId().equals(objModel.getId())) {
        isEqual = true;
      }
    }
    return isEqual;
  }
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Integer getResidenceId() {
    return residenceId;
  }
  
  public void setResidenceId(Integer residenceId) {
    this.residenceId = residenceId;
  }
  
  public Integer getHouseId() {
    return houseId;
  }
  
  public void setHouseId(Integer houseId) {
    this.houseId = houseId;
  }
  
  public Integer getType() {
    return type;
  }
  
  public void setType(Integer type) {
    this.type = type;
  }
  
  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public Integer getStatus() {
    return status;
  }
  
  public void setStatus(Integer status) {
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
  
  public String getCloudUrl() {
    return cloudUrl;
  }
  
  public void setCloudUrl(String cloudUrl) {
    this.cloudUrl = cloudUrl;
  }
  
}
