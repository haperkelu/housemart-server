package org.housemart.server.beans;

public class MonthTrend {

	public MonthTrend(){
		
	}
	public MonthTrend(String month, int price1, int price2){
		this.month = month;
		this.price1 = price1;
		this.price2 = price2;
	}
	
	private String month;
	private int price1;
	private int price2;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getPrice1() {
		return price1;
	}
	public void setPrice1(int price1) {
		this.price1 = price1;
	}
	public int getPrice2() {
		return price2;
	}
	public void setPrice2(int price2) {
		this.price2 = price2;
	}
	
}
