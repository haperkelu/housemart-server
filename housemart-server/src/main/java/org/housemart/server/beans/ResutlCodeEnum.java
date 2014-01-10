package org.housemart.server.beans;

public enum ResutlCodeEnum {

	SUCCESS(100), ERROR(200);
	private int type;
	private ResutlCodeEnum(int type){
		this.type = type;
	}
	public int getType(){
		return this.type;
	}
}
