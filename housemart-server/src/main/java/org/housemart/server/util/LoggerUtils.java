package org.housemart.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

	public static final Logger _logger = LoggerFactory.getLogger("CommonLogger");
	
	/**
	 * 
	* @Title: error
	* @Description: TODO
	* @param @param msg
	* @param @param e
	* @return void
	* @throws
	 */
	public static void error(String msg, Throwable e){
		_logger.error(msg, e);
	}
	
}
