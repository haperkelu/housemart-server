package org.housemart.server.beans;

public class UserUnreadCountsBean {
	
	private int brokerId;
	private int count;
	private String clientUId;
	private int lastId;
	
	public int getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(int brokerId) {
		this.brokerId = brokerId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getClientUId() {
		return clientUId;
	}
	public void setClientUId(String clientUId) {
		this.clientUId = clientUId;
	}
	public int getLastId() {
		return lastId;
	}
	public void setLastId(int lastId) {
		this.lastId = lastId;
	}
	
	
}
