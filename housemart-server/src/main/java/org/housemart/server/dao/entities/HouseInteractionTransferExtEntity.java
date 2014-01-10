package org.housemart.server.dao.entities;

public class HouseInteractionTransferExtEntity extends HouseInteractionTransferEntity {
	
	public String fromBrokerName;
	public String toBrokerName;
	public String finalBrokerName;
	public boolean finalBrokerStatus;
	
	public String getFromBrokerName() {
		return fromBrokerName;
	}
	public void setFromBrokerName(String fromBrokerName) {
		this.fromBrokerName = fromBrokerName;
	}
	public String getToBrokerName() {
		return toBrokerName;
	}
	public void setToBrokerName(String toBrokerName) {
		this.toBrokerName = toBrokerName;
	}
	public String getFinalBrokerName() {
		return finalBrokerName;
	}
	public void setFinalBrokerName(String finalBrokerName) {
		this.finalBrokerName = finalBrokerName;
	}
	public boolean getFinalBrokerStatus() {
		return finalBrokerStatus;
	}
	public void setFinalBrokerStatus(boolean finalBrokerStatus) {
		this.finalBrokerStatus = finalBrokerStatus;
	}
	
}