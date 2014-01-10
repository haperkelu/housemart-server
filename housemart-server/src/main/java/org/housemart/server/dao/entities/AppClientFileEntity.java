/**
 * Created on 2013-11-17
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.dao.entities;

import java.util.Date;

public class AppClientFileEntity {
  int id;
  String version;
  String clientType;
  String osType;
  String cloudURL;
  int status;
  Date addTime;
  Date updateTime;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }
  public String getClientType() {
    return clientType;
  }
  public void setClientType(String clientType) {
    this.clientType = clientType;
  }
  public String getOsType() {
    return osType;
  }
  public void setOsType(String osType) {
    this.osType = osType;
  }
  public String getCloudURL() {
    return cloudURL;
  }
  public void setCloudURL(String cloudURL) {
    this.cloudURL = cloudURL;
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
