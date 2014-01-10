/**
 * Created on 2014-1-5
 * 
 */
package org.housemart.server.service.residence;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.MonthTrend;
import org.housemart.server.beans.MonthTrendWrapper;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.dao.entities.ResidencePriceHistoryEntity;

public class PriceByAuto implements IPriceStrategy {
  
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
  @SuppressWarnings("rawtypes")
  private GenericDao residencePriceHistoryDao = SpringContextHolder.getBean("residencePriceHistoryDao");
  
  @Override
  public void populatePrice(ResidenceEntity entity, MonthTrendWrapper trendsWrapper) {
    
    MonthTrend[] trends = trendsWrapper.getTrends();
    entity.setMonthTrend(trends);
    
    // current price
    entity.setPrice(trends[0].getPrice1());
    
    // this month avg
    entity.setThisMonthAvgPrice(entity.getPrice());
    
    // last month avg
    if (trends.length > 1) {
      entity.setLastMonthAvgPrice(trends[1].getPrice1());
    } else {
      entity.setLastMonthAvgPrice(entity.getPrice());
    }
    
  }
  
  @Override
  public MonthTrendWrapper getResidenceMonthTrendWrapper(ResidenceEntity r) {
    MonthTrendWrapper wrapper = new MonthTrendWrapper();
    
    
    MonthTrend[] trends = new MonthTrend[] {};
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("id", r.getId());
    List<ResidencePriceHistoryEntity> monthTrends = residencePriceHistoryDao.select("findByResidenceId", param);
    MonthTrend thisMonth = new MonthTrend(sdf.format(Calendar.getInstance().getTime()), (int) r.getPrice(), 0);
    
    if (CollectionUtils.isNotEmpty(monthTrends)) {
      trends = new MonthTrend[monthTrends.size() + 1];
      // current
      trends[0] = thisMonth;
      
      // history
      for (int i = 0; i < monthTrends.size(); i++) {
        ResidencePriceHistoryEntity entity = monthTrends.get(i);
        MonthTrend trend = new MonthTrend(entity.getMonth(), entity.getAvgPrice(), 0);
        trends[i + 1] = trend;
      }
    } else {
      trends = new MonthTrend[1];
      trends[0] = thisMonth;
    }
    wrapper.setTrends(trends);
    wrapper.setMinRentPrice(r.getMinRentPrice());
    wrapper.setMaxRentPrice(r.getMaxRentPrice());
    
    return wrapper;
  }
}
