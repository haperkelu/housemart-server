package org.housemart.server.beans;

public class LandlordInfo {
	
	private String houseAddress = "";
	private String landlordName = "";
	private String landlordMobile ="";
	private String landlordMemo ="";
	private int houseCommitterId = 0;
	private String houseCommitterName = "";
	private String houseCommitterMobile = "";
	private int code; // 0 当天查看次数已经用完， 1 可以查看
	public String getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	public String getLandlordName() {
		return landlordName;
	}
	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}
	public String getLandlordMobile() {
		return landlordMobile;
	}
	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}
	public String getLandlordMemo() {
    return landlordMemo;
  }
  public void setLandlordMemo(String landlordMemo) {
    this.landlordMemo = landlordMemo;
  }
  public String getHouseCommitterName() {
		return houseCommitterName;
	}
	public void setHouseCommitterName(String houseCommitterName) {
		this.houseCommitterName = houseCommitterName;
	}
	public String getHouseCommitterMobile() {
		return houseCommitterMobile;
	}
	public void setHouseCommitterMobile(String houseCommitterMobile) {
		this.houseCommitterMobile = houseCommitterMobile;
	}
  public int getHouseCommitterId() {
    return houseCommitterId;
  }
  public void setHouseCommitterId(int houseCommitterId) {
    this.houseCommitterId = houseCommitterId;
  }
  public int getCode() {
    return code;
  }
  public void setCode(int code) {
    this.code = code;
  }

}
