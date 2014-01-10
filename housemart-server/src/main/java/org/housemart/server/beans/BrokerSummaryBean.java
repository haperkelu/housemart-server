package org.housemart.server.beans;

public class BrokerSummaryBean {
	
	private int brokerId;
	private String brokerName;
	private String brokerPicURL;
	private int countSession;
	private int countClient;
	private int status;
	public int getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(int brokerId) {
		this.brokerId = brokerId;
	}
	public String getBrokerName() {
		return brokerName;
	}
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	public String getBrokerPicURL() {
		return brokerPicURL;
	}
	public void setBrokerPicURL(String brokerPicURL) {
		this.brokerPicURL = brokerPicURL;
	}
	public int getCountSession() {
		return countSession;
	}
	public void setCountSession(int countSession) {
		this.countSession = countSession;
	}
	public int getCountClient() {
		return countClient;
	}
	public void setCountClient(int countClient) {
		this.countClient = countClient;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
