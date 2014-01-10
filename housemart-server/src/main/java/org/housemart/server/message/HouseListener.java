/** 
* @Title: SaleHouseListner.java
* @Package org.housemart.server.message
* @Description: TODO
* @author Pie.Li
* @date 2013-4-20 下午6:47:48
* @version V1.0 
*/
package org.housemart.server.message;

import org.housemart.framework.message.Message;
import org.housemart.framework.message.MessageListener;
import org.housemart.framework.message.StringMessage;

/**
 * @ClassName: SaleHouseListner
 * @Description: TODO
 * @date 2013-4-20 下午6:47:48
 * 
 */
public class HouseListener implements  MessageListener{

	@Override
	public void onMessage(Message msg) {
		
		StringMessage obj = (StringMessage)msg;
		if(msg.getBizType() == 1){  //房源相关
			
			
			
			
		}
		
	}

	
	
	
}
