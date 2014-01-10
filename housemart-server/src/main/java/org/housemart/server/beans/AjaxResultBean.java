package org.housemart.server.beans;

import java.io.Serializable;

public class AjaxResultBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 818635904968648296L;
	private int code;
	private String msg;
	private Object bizData;
	
	public AjaxResultBean(){
		
	}
	
	public AjaxResultBean(String msg){
		this.setMsg(msg);
	}
	
	public AjaxResultBean(int code, String msg){
		this.setCode(code);
		this.setMsg(msg);
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getBizData() {
		return bizData;
	}
	public void setBizData(Object bizData) {
		this.bizData = bizData;
	}
}
