/**
 * Created on 2013-10-21
 * 
 */
package org.housemart.server.beans;

public class VersionBean {
  String currentVersion = "";
  String requiredVersion = "";
  String currentVersionInfo = "";
  String link = "";
  
  public String getCurrentVersion() {
    return currentVersion;
  }
  
  public void setCurrentVersion(String currentVersion) {
    this.currentVersion = currentVersion;
  }
  
  public String getRequiredVersion() {
    return requiredVersion;
  }
  
  public void setRequiredVersion(String requiredVersion) {
    this.requiredVersion = requiredVersion;
  }
  
  public String getCurrentVersionInfo() {
    return currentVersionInfo;
  }
  
  public void setCurrentVersionInfo(String currentVersionUpdateInfo) {
    this.currentVersionInfo = currentVersionUpdateInfo;
  }
  
  public String getLink() {
    return link;
  }
  
  public void setLink(String link) {
    this.link = link;
  }
  
}
