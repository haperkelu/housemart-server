/**
 * Created on 2013-7-21
 * 
 */
package org.housemart.server.dao.entities;

public class RegionExtEntity {
  String cityId;
  String cityName;
  String parentId;
  String parentName;
  int id;
  String name;
  int level;
  public String getCityId() {
    return cityId;
  }
  public void setCityId(String cityId) {
    this.cityId = cityId;
  }
  public String getCityName() {
    return cityName;
  }
  public void setCityName(String cityName) {
    this.cityName = cityName;
  }
  public String getParentId() {
    return parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  public String getParentName() {
    return parentName;
  }
  public void setParentName(String parentName) {
    this.parentName = parentName;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getLevel() {
    return level;
  }
  public void setLevel(int level) {
    this.level = level;
  }
  
  
}
