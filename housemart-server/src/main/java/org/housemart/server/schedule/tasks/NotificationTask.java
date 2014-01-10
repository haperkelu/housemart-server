/**
 * Created on 2012-12-3
 * 
 */
package org.housemart.server.schedule.tasks;

import java.util.TimerTask;

import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.service.HouseFollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ken
 */
public class NotificationTask extends TimerTask {
	private static final Logger logger = LoggerFactory.getLogger(APNSTask.class);
	HouseFollowService houseFollowService = SpringContextHolder.getBean("houseFollowService");

	@Override
	public void run() {
		logger.debug("Add Notification Begin");
		houseFollowService.addNotifications();
		houseFollowService.sendNotifications();
		logger.debug("Add Notification Finished");
	}

}
