/**
 * Created on 2012-12-3
 * 
 */
package org.housemart.server.schedule.tasks;

import java.util.TimerTask;

import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ken
 */
public class APNSTask extends TimerTask {
	private static final Logger logger = LoggerFactory.getLogger(APNSTask.class);
	SessionService sessionService = SpringContextHolder.getBean("sessionService");

	@Override
	public void run() {
		logger.debug("Notify Unread Chats Begin");
		sessionService.notifyClientUnreadChats();
		sessionService.notifyBrokerUnreadChats();
		logger.debug("Notify Unread Chats Finished");
	}

}
