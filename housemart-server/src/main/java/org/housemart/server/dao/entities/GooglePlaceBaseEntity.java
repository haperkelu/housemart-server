package org.housemart.server.dao.entities;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.housemart.server.map.MapSearchUtils;

public class GooglePlaceBaseEntity {
  
  private int id;
  private int residenceId;
  private String address = "";
  private String name = "";
  private String lat = "";
  private String lng = "";
  private String type = "";
  private String keyword = "";
  private int onSaleCount;
  private int onRentCount;
  private double distance;
  private int forceShow;
  
  // key = id_radius
  public static enum FIELD {
    key, id, residenceId, lat, lng, radius, latFrom, latTo, lngFrom, lngTo, onSaleCount, onRentCount, distance, forceShow;
  }
  
  public Document toDocument(double distance) {
    Document doc = null;
    if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) {
      doc = new Document();
      double latDiff = MapSearchUtils.getLatDiff(distance);
      double latFrom = Double.valueOf(lat) - latDiff;
      double latTo = Double.valueOf(lat) + latDiff;
      
      double lngDiff = MapSearchUtils.getLngDiff(Double.valueOf(lat), distance);
      double lngFrom = Double.valueOf(lng) - lngDiff;
      double lngTo = Double.valueOf(lng) + lngDiff;
      
      Field fId = new Field(FIELD.id.toString(), String.valueOf(getId()), Field.Store.YES, Field.Index.NOT_ANALYZED);
      doc.add(fId);
      
      Field fKey = new Field(FIELD.key.toString(), String.valueOf(getId()) + "_" + String.valueOf(getResidenceId()) + "_"
          + String.valueOf(distance), Field.Store.YES, Field.Index.NOT_ANALYZED);
      doc.add(fKey);
      
      Field fResidenceId = new Field(FIELD.residenceId.toString(), String.valueOf(getResidenceId()), Field.Store.YES,
          Field.Index.NOT_ANALYZED);
      doc.add(fResidenceId);
      
      NumericField fOnSaleStatus = new NumericField(FIELD.onSaleCount.toString(), Field.Store.YES, true)
          .setIntValue(getOnSaleCount());
      doc.add(fOnSaleStatus);
      
      NumericField fOnRentStatus = new NumericField(FIELD.onRentCount.toString(), Field.Store.YES, true)
          .setIntValue(getOnRentCount());
      doc.add(fOnRentStatus);
      
      NumericField fForceShow = new NumericField(FIELD.forceShow.toString(), Field.Store.YES, true).setIntValue(getForceShow());
      doc.add(fForceShow);
      
      NumericField fLat = new NumericField(FIELD.lat.toString(), Field.Store.YES, true).setDoubleValue(Double.valueOf(getLat()));
      doc.add(fLat);
      
      NumericField fLng = new NumericField(FIELD.lng.toString(), Field.Store.YES, true).setDoubleValue(Double.valueOf(getLng()));
      doc.add(fLng);
      
      NumericField fRadius = new NumericField(FIELD.radius.toString(), Field.Store.YES, true).setDoubleValue(distance);
      doc.add(fRadius);
      
      NumericField fLatFrom = new NumericField(FIELD.latFrom.toString(), Field.Store.YES, true).setDoubleValue(latFrom);
      doc.add(fLatFrom);
      
      NumericField fLatTo = new NumericField(FIELD.latTo.toString(), Field.Store.YES, true).setDoubleValue(latTo);
      doc.add(fLatTo);
      
      NumericField fLngFrom = new NumericField(FIELD.lngFrom.toString(), Field.Store.YES, true).setDoubleValue(lngFrom);
      doc.add(fLngFrom);
      
      NumericField fLngTo = new NumericField(FIELD.lngTo.toString(), Field.Store.YES, true).setDoubleValue(lngTo);
      doc.add(fLngTo);
    }
    return doc;
  }
  
  public static GooglePlaceBaseEntity fromDocument(Document doc) {
    GooglePlaceBaseEntity entity = null;
    if (doc != null) {
      entity = new GooglePlaceBaseEntity();
      entity.setId(Integer.valueOf(doc.get(FIELD.id.toString())));
      entity.setResidenceId(Integer.valueOf(doc.get(FIELD.residenceId.toString())));
      entity.setLat(doc.get(FIELD.lat.toString()));
      entity.setLng(doc.get(FIELD.lng.toString()));
      
      if (doc.get(FIELD.onSaleCount.toString()) != null) {
        entity.setOnSaleCount(Integer.valueOf(doc.get(FIELD.onSaleCount.toString())));
      } else {
        entity.setOnSaleCount(0);
      }
      
      if (doc.get(FIELD.onRentCount.toString()) != null) {
        entity.setOnRentCount(Integer.valueOf(doc.get(FIELD.onRentCount.toString())));
      } else {
        entity.setOnRentCount(0);
      }
      
      if (doc.get(FIELD.forceShow.toString()) != null) {
        entity.setForceShow(Integer.valueOf(doc.get(FIELD.forceShow.toString())));
      } else {
        entity.setForceShow(0);
      }
    }
    return entity;
  }
  
  @Override
  public boolean equals(Object obj) {
    return this.getId() == ((GooglePlaceBaseEntity) obj).getId();
  }
  
  public int getResidenceId() {
    return residenceId;
  }
  
  public void setResidenceId(int residenceId) {
    this.residenceId = residenceId;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getKeyword() {
    return keyword;
  }
  
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
  
  public String getLat() {
    return lat;
  }
  
  public void setLat(String lat) {
    this.lat = lat;
  }
  
  public String getLng() {
    return lng;
  }
  
  public void setLng(String lng) {
    this.lng = lng;
  }
  
  public int getOnSaleCount() {
    return onSaleCount;
  }
  
  public void setOnSaleCount(int onSaleCount) {
    this.onSaleCount = onSaleCount;
  }
  
  public int getOnRentCount() {
    return onRentCount;
  }
  
  public void setOnRentCount(int onRentCount) {
    this.onRentCount = onRentCount;
  }
  
  public double getDistance() {
    return distance;
  }
  
  public void setDistance(double distance) {
    this.distance = distance;
  }
  
  public int getForceShow() {
    return forceShow;
  }
  
  public void setForceShow(int forceShow) {
    this.forceShow = forceShow;
  }
  
}
