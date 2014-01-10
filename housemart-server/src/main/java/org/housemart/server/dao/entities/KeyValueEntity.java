/**
 * Created on 2013-10-22
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.dao.entities;

import java.util.Date;

public class KeyValueEntity {
  private int id;
  private String key;
  private String value;
  private Date updateTime;
  private Date addTime;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  public Date getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  public Date getAddTime() {
    return addTime;
  }
  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
  
}
