/**
 * Created on 2013-2-24
 * 
 */
package org.housemart.server.dao.entities;

import java.util.Date;
import org.housemart.server.util.CommonUtils;

/**
 * 
* @ClassName: AreaPositionEntity
* @Description: TODO
* @date 2013-4-16 上午10:52:22
*
 */
public class AreaPositionEntity {
	private int id;
	private int cityId;
	private int type;
	private int positionId;
	private String lat = "";
	private String lng = "";
	private Date addTime = CommonUtils.getDefaultDate();
	private Date updateTime = CommonUtils.getDefaultDate();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
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
