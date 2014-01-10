/**
 * Created on 2013-5-17
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.server.beans.house;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.housemart.server.util.GenericCollections;
import org.housemart.server.util.ReflectHelper;

public class HouseRentEquipment {
  
  static final String ON = "on";
  String water;
  String power;
  String gas;
  String heat;
  String cable;
  String network;
  String tv;
  String refrigerator;
  String airCondition;
  String washer;
  String waterHeater;
  String microwave;
  String telephone;
  
  public static enum EnumFields {
    water("水"), power("电"), gas("煤气"), heat("暖气"), cable("有线电视"), network("宽带"), tv("电视"), refrigerator("冰箱"), airCondition("空调"), washer(
        "洗衣机"), waterHeater("热水机"), microwave("微波炉"), telephone("电话");
    private String value;
    
    EnumFields(String value) {
      this.setValue(value);
    }
    
    public String getValue() {
      return value;
    }
    
    public void setValue(String value) {
      this.value = value;
    }
  };
  
  public static String obj2String(HouseRentEquipment equipt) throws Exception {
    if (equipt == null) return null;
    String ret = null;
    List<String> equipts = new ArrayList<String>();
    for (EnumFields e : EnumFields.values()) {
      Object o = ReflectHelper.field(equipt, e.toString());
      if (o != null) {
        equipts.add(e.toString());
      }
    }
    ret = GenericCollections.join(equipts, ",");
    return ret;
  }
  
  public static String obj2Chinese(HouseRentEquipment equipt) throws Exception {
    
    if (equipt == null) return null;
    String ret = null;
    List<String> equipts = new ArrayList<String>();
    for (EnumFields e : EnumFields.values()) {
      Object o = ReflectHelper.field(equipt, e.toString());
      if (o != null) {
        equipts.add(e.getValue());
      }
    }
    ret = GenericCollections.join(equipts, ",");
    return ret;
    
  }
  
  public static HouseRentEquipment string2Obj(String equipments) throws Exception {
    HouseRentEquipment result = new HouseRentEquipment();
    if (StringUtils.isNotBlank(equipments)) {
      for (String equipt : equipments.split(",")) {
        for (EnumFields e : EnumFields.values()) {
          if (e.toString().equals(equipt)) {
            ReflectHelper.field(result, equipt, equipt);
            break;
          }
        }
      }
    }
    return result;
  }
  
  public String getWater() {
    return water;
  }
  
  public void setWater(String water) {
    this.water = water;
  }
  
  public String getPower() {
    return power;
  }
  
  public void setPower(String power) {
    this.power = power;
  }
  
  public String getGas() {
    return gas;
  }
  
  public void setGas(String gas) {
    this.gas = gas;
  }
  
  public String getHeat() {
    return heat;
  }
  
  public void setHeat(String heat) {
    this.heat = heat;
  }
  
  public String getCable() {
    return cable;
  }
  
  public void setCable(String cable) {
    this.cable = cable;
  }
  
  public String getNetwork() {
    return network;
  }
  
  public void setNetwork(String network) {
    this.network = network;
  }
  
  public String getTv() {
    return tv;
  }
  
  public void setTv(String tv) {
    this.tv = tv;
  }
  
  public String getRefrigerator() {
    return refrigerator;
  }
  
  public void setRefrigerator(String refrigerator) {
    this.refrigerator = refrigerator;
  }
  
  public String getAirCondition() {
    return airCondition;
  }
  
  public void setAirCondition(String airCondition) {
    this.airCondition = airCondition;
  }
  
  public String getWasher() {
    return washer;
  }
  
  public void setWasher(String washer) {
    this.washer = washer;
  }
  
  public String getWaterHeater() {
    return waterHeater;
  }
  
  public void setWaterHeater(String waterHeater) {
    this.waterHeater = waterHeater;
  }
  
  public String getMicrowave() {
    return microwave;
  }
  
  public void setMicrowave(String microwave) {
    this.microwave = microwave;
  }
  
  public String getTelephone() {
    return telephone;
  }
  
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
  
}
