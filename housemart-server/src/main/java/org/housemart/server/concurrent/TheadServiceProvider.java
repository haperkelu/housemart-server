/**   
* @Title: TheadServiceProvider.java 
* @Package org.housemart.server.concurrent 
* @Description: TODO
* @author Pie.Li   
* @date 2014-1-4 下午9:49:46 
* @version V1.0   
*/
package org.housemart.server.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pie.Li
 *
 */
public class TheadServiceProvider {

	/** **/
	private static final ExecutorService service = Executors.newFixedThreadPool(5);
	
	public static ExecutorService getThreadService(){
		return service;
	}
	
}
