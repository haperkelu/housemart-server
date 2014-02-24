package org.housemart.server.beans;

public class UserChatSummaryBean {
	
	private String residenceName;
	private int houseId;
	private String housePicURL;
	private int brokerId;
	private int realBrokerId;
	private String brokerName;
	private String brokerPicURL;
	private String title;
	private int count;
	private int type;
	private String clientUId;
	private String lastContent;
	private int lastContentFormat;
	private String lastUpdateTime;
	private boolean isTransfer;
	
	public String getResidenceName() {
		return residenceName;
	}
	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getLastContent() {
		return lastContent;
	}
	public void setLastContent(String lastContent) {
		this.lastContent = lastContent;
	}
	public int getLastContentFormat() {
		return lastContentFormat;
	}
	public void setLastContentFormat(int lastContentFormat) {
		this.lastContentFormat = lastContentFormat;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getClientUId() {
		return clientUId;
	}
	public void setClientUId(String clientUId) {
		this.clientUId = clientUId;
	}
	public boolean getIsTransfer() {
		return isTransfer;
	}
	public void setIsTransfer(boolean isTransfer) {
		this.isTransfer = isTransfer;
	}
	public int getRealBrokerId() {
		return realBrokerId;
	}
	public void setRealBrokerId(int realBrokerId) {
		this.realBrokerId = realBrokerId;
	}
	public String getBrokerPicURL() {
		return brokerPicURL;
	}
	public void setBrokerPicURL(String brokerPicURL) {
		this.brokerPicURL = brokerPicURL;
	}
	public String getHousePicURL() {
		return housePicURL;
	}
	public void setHousePicURL(String housePicURL) {
		this.housePicURL = housePicURL;
	}
	
}
