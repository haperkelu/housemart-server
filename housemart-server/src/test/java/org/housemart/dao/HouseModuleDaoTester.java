/**
 * Created on 2012-9-19
 * 
 * Copyright (c) Comprice, Inc. 2010. All rights reserved.
 */
package org.housemart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.push.JavaPNSProvider;
import org.housemart.framework.utils.CommonUtils;
import org.housemart.server.dao.entities.HouseFollowEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring*.xml"})
public class HouseModuleDaoTester {
	
	@Autowired
	private GenericDao houseFollowDao; 
	
	@Test
	public void addHouseTest() {
		
		HouseFollowEntity entity = new HouseFollowEntity();
		entity.setClientId("dddddd");
		entity.setType(1);
		entity.setHouseId(555);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("houseId", entity.getHouseId());
		map.put("clientId", entity.getClientId());
		map.put("type", entity.getType());
		
		houseFollowDao.add("addHouseFollow", entity);
		
		List<HouseFollowEntity> list = houseFollowDao.select("loadHouseFollowByCondition", map);
		System.out.println(list.size());
		
		houseFollowDao.delete("deleteHouseFollow", entity);
		
	}
	
	@Test
	public void testPNS(){
		Logger logger = LoggerFactory.getLogger(JavaPNSProvider.class);
		final String decodedToken = CommonUtils.decodeDeviceId("TK96nBHihNF0Be0Cyydd7gSCerwAq45KDmP1qAmkrx8=");
		System.out.println(decodedToken);
	}
}
