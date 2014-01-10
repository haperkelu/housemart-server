/** 
* @Title: HouseLeadsEntity.java
* @Package org.housemart.server.dao.entities
* @Description: TODO
* @author Pie.Li
* @date 2013-4-8 下午6:08:08
* @version V1.0 
*/
package org.housemart.server.dao.entities;

/**
 * @ClassName: HouseLeadsEntity
 * @Description: TODO
 * @date 2013-4-8 下午6:08:08
 * 
 */
public class HouseLeadsEntity {

	private int id;
	private String contactName;
	private String mobile;
	private String residenceName;
	private String address;
	private String area;
	private String clientUid;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the residenceName
	 */
	public String getResidenceName() {
		return residenceName;
	}
	/**
	 * @param residenceName the residenceName to set
	 */
	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
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
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	public String getClientUid() {
		return clientUid;
	}
	public void setClientUid(String clientUid) {
		this.clientUid = clientUid;
	}
		
}
