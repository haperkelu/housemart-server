/**   
* @Title: RPCTester.java 
* @Package org.housemart.server.dao 
* @Description: TODO
* @author Pie.Li   
* @date 2013-12-29 上午10:01:00 
* @version V1.0   
*/
package org.housemart.server.test.rpc;

import org.brilliance.middleware.client.ClientWrapper;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.rpc.stubs.AuditServiceStub;
import org.housemart.server.resource.ResourceProvider;
import org.housemart.server.util.RPCUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author Pie.Li
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring-web-beans.xml", "classpath*:/beans/spring-web-dao.xml"})
public class RPCTester {

	@Test
	public void fn() {

		AuditServiceStub stub = (AuditServiceStub) ClientWrapper.powerStub(AuditServiceStub.class, 
				RPCUtils.getDefaultRPCServer(), RPCUtils.getDefaultRPCServerPort());
		int result = stub.requestAddNewHouse(132);
		System.out.println(result);
		
	}
	
}
