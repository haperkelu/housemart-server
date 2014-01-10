/**
 * Created on 2012-12-23
 * 
 */
package org.housemart.server.service;

import org.apache.commons.lang.ArrayUtils;
import org.housemart.server.dao.entities.GooglePlaceBaseEntity;
import org.housemart.server.map.MapSearchUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author pqin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring-web-dao.xml", "classpath*:/beans/spring-web-beans.xml"})
public class SearchServiceTest {
	@Autowired
	SearchService searchService;

	// @Test
	public void writeHouseIndex() throws Exception {
		searchService.buildHouseIndex();
	}

	// @Test
	public void writeGoogleMapIndex() throws Exception {
		searchService.buildGooglePlaceIndex();
	}

//	@Test
	public void searchGoogleMap() {
		GooglePlaceBaseEntity[] result = null;
		double lat = 31.182515;
		double lng = 121.520733;
		double distance = 6000;
		try {
			result = searchService.searchGooglePlace(lat, lng, distance, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int errorCount = 0;
		if (!ArrayUtils.isEmpty(result)) {
			for (GooglePlaceBaseEntity entity : result) {
				System.out.println("Id:"
						+ entity.getId()
						+ "Lat:"
						+ entity.getLat()
						+ "  Lng:"
						+ entity.getLng()
						+ " Distance:"
						+ MapSearchUtils.getDistance(lat, lng, Double.valueOf(entity.getLat()),
								Double.valueOf(entity.getLng())) + " OnSaleCount:" + entity.getOnSaleCount()
						+ " OnRentCount:" + entity.getOnRentCount());
				if (MapSearchUtils.getDistance(lat, lng, Double.valueOf(entity.getLat()),
						Double.valueOf(entity.getLng())) > distance)
					errorCount++;
			}
			System.out.println("Total: " + result.length + " Error: " + errorCount);
		}
	}
}
