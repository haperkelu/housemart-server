package org.housemart.server.beans;

public class ResultBean {

	private int code;
	private Object data;
	private String version;
	private int count; //总记录数
	private int totalCount; 
	private String msg;
	private String secret;
	private int unReadMsgCount;
	private Object extData;	
	private String exceptionDetail;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public int getUnReadMsgCount() {
		return unReadMsgCount;
	}
	public void setUnReadMsgCount(int unReadMsgCount) {
		this.unReadMsgCount = unReadMsgCount;
	}
	/**
	 * @return the exceptionDetail
	 */
	public String getExceptionDetail() {
		return exceptionDetail;
	}
	public Object getExtData() {
		return extData;
	}
	public void setExtData(Object extData) {
		this.extData = extData;
	}
	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}
  public int getTotalCount() {
    return totalCount;
  }
  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }
	
}
