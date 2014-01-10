package org.housemart.server.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.housemart.server.beans.MessageBean;
import org.housemart.server.beans.MonthTrend;
import org.housemart.server.beans.ResidenceDetail;

public class MockService {

	public static List<ResidenceDetail> getResidenceData(){
		
		List<ResidenceDetail> result = new ArrayList<ResidenceDetail>();
		ResidenceDetail detail = new ResidenceDetail();
		detail.setCityId(1);
		detail.setAddress("虹桥路168号/徐家汇");
		detail.setDeveloper("上海东方海外徐家汇房地产有限公司");
		detail.setFinishedTime("1900-10");
		detail.setIsFollow(false);
		detail.setGreenRate(0.09);
		detail.setHouseHold("5000");
		detail.setId(33);
		detail.setLastSeasonDealCount(100);
		detail.setLastSeasonRentCount(300);
		detail.setMonthTrend(new MonthTrend[]{new MonthTrend("2012-09", 120000, 11000),new MonthTrend("2012-10", 130000, 12000)});
		detail.setOnRentCount(200);
		detail.setOnSaleCount(400);
		detail.setParking("室内单车位或双车位");
		detail.setPicURL(new String[]{});
		detail.setPlateId(433);
		detail.setPrice("13000");
		detail.setPriceRange("12000~13000");
		detail.setPropertyFee("5rmb");
		detail.setRentRange("2000~3000");
		detail.setResidenceName("东方曼哈顿");
		detail.setTrend(1.003);
		detail.setType("公寓");
		detail.setVolumnRate(2.4);


		result.add(detail);
		result.add(detail);
		return result;
	}

	
	public static List<MessageBean> getMsg(){
		
		List<MessageBean> result = new ArrayList<MessageBean>();
		MessageBean bean = new MessageBean();
		bean.setTime(Calendar.getInstance().getTime());
		bean.setContent("东方曼哈顿房价降低");
		return result;
		
	}
	
}
