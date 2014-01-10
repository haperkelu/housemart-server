package org.housemart.server.beans;

public class ChatMessageBean {

	private int messageId;
	private int brokerId;
	private String name;
	private String brokerPicURL;
	private int useType;
	private String content;
	private String time;
	private int format;
	private int type;
	private int houseId;
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(int brokerId) {
		this.brokerId = brokerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUseType() {
		return useType;
	}
	public void setUseType(int useType) {
		this.useType = useType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getFormat() {
		return format;
	}
	public void setFormat(int format) {
		this.format = format;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public String getBrokerPicURL() {
		return brokerPicURL;
	}
	public void setBrokerPicURL(String brokerPicURL) {
		this.brokerPicURL = brokerPicURL;
	}
	
}
