/**
 * Created on 2013-5-17
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.beans.house;


public class HouseDirection {
  
  static final String ON = "on";
  String east;
  String south;
  String west;
  String north;
  
  public static HouseDirection newInstance(String east, String south,
      String west, String north) {
    HouseDirection result = new HouseDirection();
    result.east = east;
    result.south = south;
    result.west = west;
    result.north = north;
    return result;
  }
  
  public static HouseDirection newInstance(Integer east, Integer south,
      Integer west, Integer north) {
    HouseDirection result = new HouseDirection();
    if (east != null && east == 1) result.east = ON;
    if (south != null && south == 1) result.south = ON;
    if (west != null && west == 1) result.west = ON;
    if (north != null && north == 1) result.north = ON;
    return result;
  }
  
  public String getEast() {
    return east;
  }
  
  public void setEast(String east) {
    this.east = east;
  }
  
  public String getSouth() {
    return south;
  }
  
  public void setSouth(String south) {
    this.south = south;
  }
  
  public String getWest() {
    return west;
  }
  
  public void setWest(String west) {
    this.west = west;
  }
  
  public String getNorth() {
    return north;
  }
  
  public void setNorth(String north) {
    this.north = north;
  }
  
  public Integer[] toArray() {
    Integer[] result = {0, 0, 0, 0};
    if (ON.equals(this.east)) result[0] = 1;
    if (ON.equals(this.south)) result[1] = 1;
    if (ON.equals(this.west)) result[2] = 1;
    if (ON.equals(this.north)) result[3] = 1;
    return result;
  }
  
}
