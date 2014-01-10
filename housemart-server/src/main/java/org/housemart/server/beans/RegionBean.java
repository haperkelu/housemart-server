package org.housemart.server.beans;

import java.util.ArrayList;
import java.util.List;

import org.housemart.server.dao.entities.RegionEntity;

public class RegionBean {
	
	private int regionId;
	private String regionName="";
	private List<RegionEntity> plateList=new ArrayList<RegionEntity>();
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public List<RegionEntity> getPlateList() {
		return plateList;
	}
	public void setPlateList(List<RegionEntity> plateList) {
		this.plateList = plateList;
	}	
	
}
