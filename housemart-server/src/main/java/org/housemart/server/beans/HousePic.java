/**
 * Created on 2013-12-25
 * 
 */
package org.housemart.server.beans;

public class HousePic {
  
  private Integer[] id = new Integer[0];
  private String[] picURLWithSize = new String[0];
  private String[] picURLWithOriginSize = new String[0];
  public Integer[] getId() {
    return id;
  }
  public void setId(Integer[] id) {
    this.id = id;
  }
  public String[] getPicURLWithSize() {
    return picURLWithSize;
  }
  public void setPicURLWithSize(String[] picURLWithSize) {
    this.picURLWithSize = picURLWithSize;
  }
  public String[] getPicURLWithOriginSize() {
    return picURLWithOriginSize;
  }
  public void setPicURLWithOriginSize(String[] picURLWithOriginSize) {
    this.picURLWithOriginSize = picURLWithOriginSize;
  }
}
