/**
 * 
 */
package org.housemart.server.dao.entities;

/**
 * @author user
 *
 */
public class FeedBackEntity {

	
	private int id;
	private String content;
	private String name;
	private String mobile;
	private String clientUid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getClientUid() {
		return clientUid;
	}
	public void setClientUid(String clientUid) {
		this.clientUid = clientUid;
	}
	
}
