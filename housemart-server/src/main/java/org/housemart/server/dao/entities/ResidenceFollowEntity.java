/**
 * 
 */
package org.housemart.server.dao.entities;

import java.util.Date;

/**
 * @author user
 *
 */
public class ResidenceFollowEntity {

	private int id;
	private int residenceId;
	private String clientId;
	private Date addTime;
	
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getResidenceId() {
		return residenceId;
	}
	public void setResidenceId(int residenceId) {
		this.residenceId = residenceId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	
}
