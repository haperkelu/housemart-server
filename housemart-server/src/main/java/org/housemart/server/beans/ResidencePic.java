package org.housemart.server.beans;

public class ResidencePic {
  
  private String[] picURL = new String[0];
  private String[] picURLWithSize = new String[0];
  private String[] picURLWithOriginSize = new String[0];
  public String[] getPicURL() {
    return picURL;
  }
  public void setPicURL(String[] picURL) {
    this.picURL = picURL;
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
