/**
 * Created on 2012-12-6
 * 
 */
package org.housemart.server.service;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.server.beans.LandlordInfo;
import org.housemart.server.beans.SearchResultBean;
import org.housemart.server.beans.house.HouseDetailBean;
import org.housemart.server.beans.house.HouseRentBean;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author pqin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring-web-dao.xml", "classpath*:/beans/spring-web-beans.xml"})
public class HouseServiceTest {
	@Autowired
	HouseService houseService;

	// @Test
	public void loadDetail() throws Exception {
		HouseDetailBean detail = houseService.loadDetail(80);
		System.out.println(detail.getAddress());
	}

	// @Test
	public void findList() throws Exception {
		SearchResultBean<HouseRentBean> houses = houseService.findResidenceRentHouse(1, 2184, 2, 0, 10);
		if (CollectionUtils.isNotEmpty(houses.getData()))
			for (HouseRentBean rent : houses.getData()) {
				System.out.println(rent.getId());
			}
	}

//	@Test
	public void detailFindList() throws Exception {
		SearchResultBean<HouseRentBean> houses = houseService.detailFindRentHouse(null, null, null, null, new int[]{1563, 1564},
				null, null, 0, 99999999, 2, 0, 10);
		if (CollectionUtils.isNotEmpty(houses.getData()))
			for (HouseRentBean rent : houses.getData()) {
				System.out.println(rent.getId());
			}
	}

	//@Test
	public void landloadTest() throws Exception{
	  
	  LandlordInfo landload = houseService.getLandlordInfo(36, 2);
	  System.out.println(landload.getCode());
	  
	}
}
