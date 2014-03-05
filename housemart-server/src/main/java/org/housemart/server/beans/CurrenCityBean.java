/**   
* @Title: CurrenCityBean.java 
* @Package org.housemart.server.beans 
* @Description: TODO
* @author Pie.Li   
* @date 2014-3-5 上午7:54:08 
* @version V1.0   
*/
package org.housemart.server.beans;

import java.io.Serializable;

/**
 * @author Pie.Li
 *
 */
public class CurrenCityBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2900607539215432184L;

	private String bizTag;
	private int cityID;
	/**
	 * @return the bizTag
	 */
	public String getBizTag() {
		return bizTag;
	}
	/**
	 * @param bizTag the bizTag to set
	 */
	public void setBizTag(String bizTag) {
		this.bizTag = bizTag;
	}
	/**
	 * @return the cityID
	 */
	public int getCityID() {
		return cityID;
	}
	/**
	 * @param cityID the cityID to set
	 */
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	
}
