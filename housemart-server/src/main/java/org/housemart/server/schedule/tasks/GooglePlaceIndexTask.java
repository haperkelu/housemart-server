/**
 * Created on 2012-12-3
 * 
 */
package org.housemart.server.schedule.tasks;

import java.util.TimerTask;

import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pqin
 */
public class GooglePlaceIndexTask extends TimerTask {
	private static final Logger logger = LoggerFactory.getLogger(GooglePlaceIndexTask.class);
	SearchService searchService = SpringContextHolder.getBean("searchService");

	@Override
	public void run() {
		logger.debug("GooglePlaceBaseEntity Begin Index");
		try {
      searchService.buildGooglePlaceIndex();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
		logger.debug("GooglePlaceBaseEntity Finished Index");
	}

}
