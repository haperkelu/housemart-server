/** 
* @Title: CityBean.java
* @Package org.housemart.server.beans
* @Description: TODO
* @author Pie.Li
* @date 2013-4-21 上午12:13:55
* @version V1.0 
*/
package org.housemart.server.beans;

/**
 * @ClassName: CityBean
 * @Description: TODO
 * @date 2013-4-21 上午12:13:55
 * 
 */
public class CityBean {
	
	public CityBean(){}
	public CityBean(int id, String name) {this.id = id; this.name = name;}
	private int id;
	private String name;
	
	private boolean isDefaultCity;
	private boolean isOverseaCity;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the isDefaultCity
	 */
	public boolean isDefaultCity() {
		return isDefaultCity;
	}
	/**
	 * @param isDefaultCity the isDefaultCity to set
	 */
	public void setDefaultCity(boolean isDefaultCity) {
		this.isDefaultCity = isDefaultCity;
	}
	/**
	 * @return the isOverseaCity
	 */
	public boolean isOverseaCity() {
		return isOverseaCity;
	}
	/**
	 * @param isOverseaCity the isOverseaCity to set
	 */
	public void setOverseaCity(boolean isOverseaCity) {
		this.isOverseaCity = isOverseaCity;
	}

}
