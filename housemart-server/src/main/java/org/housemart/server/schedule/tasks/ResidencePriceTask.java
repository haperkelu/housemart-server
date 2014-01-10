/**
 * Created on 2013-7-2
 * 
 */
package org.housemart.server.schedule.tasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.dao.entities.ResidencePriceHistoryEntity;
import org.housemart.server.service.ResidenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResidencePriceTask extends TimerTask {
  private static final Logger logger = LoggerFactory.getLogger(ResidencePriceTask.class);
  ResidenceService residenceService = SpringContextHolder.getBean("residenceService");
  
  @Override
  public void run() {
    logger.debug("Residence Price Begin Index");
    
    List<ResidenceEntity> residences = residenceService.findThisMonthAvg();
    
    if (residences != null) {
      List<ResidencePriceHistoryEntity> pHistory = new ArrayList<ResidencePriceHistoryEntity>();
      
      for (ResidenceEntity r : residences) {
        ResidencePriceHistoryEntity history = new ResidencePriceHistoryEntity();
        history.setAvgPrice((int) r.getPrice());
        history.setAddTime(Calendar.getInstance().getTime());
        history.setResidenceId(r.getId());
        pHistory.add(history);
        residenceService.addHistoryPrice(history);
      }
      
    }
    
    logger.debug("Residence Price Finished Index");
  }
  
}
