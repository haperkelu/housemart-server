/**
 * Created on 2012-12-23
 * 
 */
package org.housemart.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author pqin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring-web-beans.xml", "classpath*:/beans/spring-web-dao.xml"})
public class GooglePlaceBaseTest {

	@Autowired
	@SuppressWarnings("rawtypes")
	GenericDao googlePlaceDao;

	@SuppressWarnings({"unchecked", "rawtypes"})
//	@Test
	public void findRawDat() throws Exception {
		Map para = new HashMap();
		para.put("index", 0);
		para.put("size", 20000);
		List result = googlePlaceDao.select("findRawDat", para);
		if (CollectionUtils.isNotEmpty(result)) {
			System.out.println(result.size());
		}
	}

}
