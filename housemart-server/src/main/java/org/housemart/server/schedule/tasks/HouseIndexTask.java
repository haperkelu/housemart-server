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
public class HouseIndexTask extends TimerTask {
  private static final Logger logger = LoggerFactory.getLogger(HouseIndexTask.class);
  SearchService searchService = SpringContextHolder.getBean("searchService");
  private static Integer MUTEX = 1;
  private static final int REBUILD_ALL_INDEX_WHEN_LOOP_EQUALS = 20 * 12;
  private static int CURRENT_LOOP = 0;
  
  @Override
  public void run() {
    
    if (MUTEX > 0) {
      synchronized (MUTEX) {
        try {
          MUTEX = 0;
          CURRENT_LOOP++;
          logger.info("HouseIndexTask Start, CURRENT_LOOP " + CURRENT_LOOP);
          
          if (CURRENT_LOOP >= REBUILD_ALL_INDEX_WHEN_LOOP_EQUALS) {
            CURRENT_LOOP = 0;
            searchService.resetLastTaskTime();
            searchService.resetTmpDir();
            Thread.sleep(1000);
          }
          
          searchService.buildHouseIndex();
          
          MUTEX = 1;
          logger.info("HouseIndexTask Finish");
        } catch (Exception e) {
          MUTEX = 1;
          logger.error(e.getMessage(), e);
        }
      }
    } else {
      logger.warn("House Index Mutex was Monopolized! Ignore.");
    }
    
  }
  
}
