/**
 * Created on 2012-12-7
 * 
 */
package org.housemart.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.server.dao.entities.HouseEntity;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author pqin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring-web-beans.xml", "classpath*:/beans/spring-web-dao.xml"})
public class HouseTest {
	@Autowired
	@SuppressWarnings("rawtypes")
	GenericDao houseDao;

	@SuppressWarnings({"unchecked", "rawtypes"})
//	@Test
	public void loadDetail() throws Exception {
		Map para = new HashMap();
		para.put("houseIdFrom", 1);
		para.put("houseIdTo", 200);
		List<HouseEntity> houses = houseDao.select("findHouseList", para);
		if (CollectionUtils.isNotEmpty(houses))
			for (HouseEntity rent : houses) {
				System.out.println(rent.getId());
			}
	}

}
