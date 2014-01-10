/** 
* @Title: MaxinRawEntity.java
* @Package org.housemart.dao.entities
* @Description: TODO
* @author Pie.Li
* @date 2013-5-3 下午3:41:36
* @version V1.0 
*/
package org.housemart.server.dao.entities;

/**
 * @ClassName: MaxinRawEntity
 * @Description: TODO
 * @date 2013-5-3 下午3:41:36
 * 
 */
public class MaxinRawEntity {

	private String id;   //A   脉信ID
	private String clientName;  //B  客户名称
	private String phone1;  //C      电话1
	private String phone2; //D       电话2
	private String phone3; //E       电话3
	private String contactMemo; //F  联系人备注
	private String region; //G       地区
	private String plate; //H        板块
	private String residence; //I    小区
	private String address; //J      地址
	private String buildingNo; //K   栋座
	private String cellNo; //L       单元
	private String houseArea; //M    面积
	private String avg; //N          均价
	private String totalValue; //O   总价值
	private String rentValue; //P    租房价值
	private String saleIntention; //Q  出售意向
	private String salePrice; //R    出手价格
	private String rentIntention; //S  出租意向
	private String rentPrice; //T    出租价格
	private String memo; //U  更新
	private String dialResult;//V  外拨结果
	private String taskPerson; //W  任务人
	private String dialTime; //X  拨号时间
	private String assignTime; //Y  分配时间
	private String handleTime; //Z  处理时间
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return the phone1
	 */
	public String getPhone1() {
		return phone1;
	}
	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}
	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	/**
	 * @return the phone3
	 */
	public String getPhone3() {
		return phone3;
	}
	/**
	 * @param phone3 the phone3 to set
	 */
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	/**
	 * @return the contactMemo
	 */
	public String getContactMemo() {
		return contactMemo;
	}
	/**
	 * @param contactMemo the contactMemo to set
	 */
	public void setContactMemo(String contactMemo) {
		this.contactMemo = contactMemo;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the plate
	 */
	public String getPlate() {
		return plate;
	}
	/**
	 * @param plate the plate to set
	 */
	public void setPlate(String plate) {
		this.plate = plate;
	}
	/**
	 * @return the residence
	 */
	public String getResidence() {
		return residence;
	}
	/**
	 * @param residence the residence to set
	 */
	public void setResidence(String residence) {
		this.residence = residence;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the buildingNo
	 */
	public String getBuildingNo() {
		return buildingNo;
	}
	/**
	 * @param buildingNo the buildingNo to set
	 */
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	/**
	 * @return the cellNo
	 */
	public String getCellNo() {
		return cellNo;
	}
	/**
	 * @param cellNo the cellNo to set
	 */
	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}
	/**
	 * @return the houseArea
	 */
	public String getHouseArea() {
		return houseArea;
	}
	/**
	 * @param houseArea the houseArea to set
	 */
	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}
	/**
	 * @return the avg
	 */
	public String getAvg() {
		return avg;
	}
	/**
	 * @param avg the avg to set
	 */
	public void setAvg(String avg) {
		this.avg = avg;
	}
	/**
	 * @return the totalValue
	 */
	public String getTotalValue() {
		return totalValue;
	}
	/**
	 * @param totalValue the totalValue to set
	 */
	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	/**
	 * @return the rentValue
	 */
	public String getRentValue() {
		return rentValue;
	}
	/**
	 * @param rentValue the rentValue to set
	 */
	public void setRentValue(String rentValue) {
		this.rentValue = rentValue;
	}
	/**
	 * @return the saleIntention
	 */
	public String getSaleIntention() {
		return saleIntention;
	}
	/**
	 * @param saleIntention the saleIntention to set
	 */
	public void setSaleIntention(String saleIntention) {
		this.saleIntention = saleIntention;
	}
	/**
	 * @return the salePrice
	 */
	public String getSalePrice() {
		return salePrice;
	}
	/**
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	/**
	 * @return the rentIntention
	 */
	public String getRentIntention() {
		return rentIntention;
	}
	/**
	 * @param rentIntention the rentIntention to set
	 */
	public void setRentIntention(String rentIntention) {
		this.rentIntention = rentIntention;
	}
	/**
	 * @return the rentPrice
	 */
	public String getRentPrice() {
		return rentPrice;
	}
	/**
	 * @param rentPrice the rentPrice to set
	 */
	public void setRentPrice(String rentPrice) {
		this.rentPrice = rentPrice;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return the dialResult
	 */
	public String getDialResult() {
		return dialResult;
	}
	/**
	 * @param dialResult the dialResult to set
	 */
	public void setDialResult(String dialResult) {
		this.dialResult = dialResult;
	}
	/**
	 * @return the taskPerson
	 */
	public String getTaskPerson() {
		return taskPerson;
	}
	/**
	 * @param taskPerson the taskPerson to set
	 */
	public void setTaskPerson(String taskPerson) {
		this.taskPerson = taskPerson;
	}
	/**
	 * @return the dialTime
	 */
	public String getDialTime() {
		return dialTime;
	}
	/**
	 * @param dialTime the dialTime to set
	 */
	public void setDialTime(String dialTime) {
		this.dialTime = dialTime;
	}
	/**
	 * @return the assignTime
	 */
	public String getAssignTime() {
		return assignTime;
	}
	/**
	 * @param assignTime the assignTime to set
	 */
	public void setAssignTime(String assignTime) {
		this.assignTime = assignTime;
	}
	/**
	 * @return the handleTime
	 */
	public String getHandleTime() {
		return handleTime;
	}
	/**
	 * @param handleTime the handleTime to set
	 */
	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}
	
	
}
