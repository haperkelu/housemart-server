/**   
* @Title: RPCUtils.java 
* @Package org.housemart.server.util 
* @Description: TODO
* @author Pie.Li   
* @date 2013-12-29 上午10:13:12 
* @version V1.0   
*/
package org.housemart.server.util;

import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.resource.ResourceProvider;

/**
 * @author Pie.Li
 *
 */
public class RPCUtils {

	/**
	 * 
	 * @return
	 */
	public static String getDefaultRPCServer(){
		
		ResourceProvider resourceProvider = (ResourceProvider)SpringContextHolder.getBean("resourceProvider");
		return resourceProvider.getValue("housemart.rpc.erp.server").toString().trim();
		
	}
	/**
	 * 
	 * @return
	 */
	public static int getDefaultRPCServerPort(){
		
		ResourceProvider resourceProvider = (ResourceProvider)SpringContextHolder.getBean("resourceProvider");
		return Integer.parseInt(resourceProvider.getValue("housemart.rpc.erp.server.port").toString().trim());
		
	}
	
}
