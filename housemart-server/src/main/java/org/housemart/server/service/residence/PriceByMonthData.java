/**
 * Created on 2014-1-5
 * 
 */
package org.housemart.server.service.residence;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.MonthTrend;
import org.housemart.server.beans.MonthTrendWrapper;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.dao.entities.ResidenceMonthDataEntity;

public class PriceByMonthData implements IPriceStrategy {
  
  @SuppressWarnings("rawtypes")
  private GenericDao residenceMonthDataDao = SpringContextHolder.getBean("residenceMonthDataDao");
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
  
  @SuppressWarnings("unchecked")
  @Override
  public MonthTrendWrapper getResidenceMonthTrendWrapper(ResidenceEntity r) {
    MonthTrendWrapper wrapper = new MonthTrendWrapper();
    
    MonthTrend[] trends = null;
    double minRentPrice = r.getMinRentPrice();
    double maxRentPrice = r.getMaxRentPrice();
    
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("residenceId", r.getId());
    List<ResidenceMonthDataEntity> monthTrends = residenceMonthDataDao.select("findMonthData", param);
    Map<Integer,Double> finalMonthPrice = new TreeMap<Integer,Double>().descendingMap();
    
    if (CollectionUtils.isNotEmpty(monthTrends)) {
      Map<Integer,Double> monthPrice = new HashMap<Integer,Double>();
      Calendar c = Calendar.getInstance();
      Integer minMonth = c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1;
      Integer maxMonth = c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1;
      for (ResidenceMonthDataEntity m : monthTrends) {
        int month = m.getYear() * 100 + m.getMonth();
        monthPrice.put(month, m.getAveragePrice());
        if (month < minMonth) {
          minMonth = month;
        }
      }
      
      // complete month data
      int minY = minMonth / 100;
      int maxY = maxMonth / 100;
      
      int month;
      int lastMonth = -1;
      for (int y = minY; y <= maxY; y++) {
        for (int m = 1; m <= 12; m++) {
          month = y * 100 + m;
          if (lastMonth == -1) {
            lastMonth = month;
          }
          if (month < minMonth) {
            continue;
          }
          if (month > maxMonth) {
            break;
          }
          if (monthPrice.containsKey(month)) {
            // if match, add matched month
            finalMonthPrice.put(month, monthPrice.get(month));
          } else {
            // not match, add pre matched month
            finalMonthPrice.put(month, finalMonthPrice.get(lastMonth));
          }
          
          lastMonth = month;
        }
      }
      
      trends = new MonthTrend[finalMonthPrice.size()];
      int i = 0;
      for (Entry<Integer,Double> m : finalMonthPrice.entrySet()) {
        int mInt = m.getKey();
        DecimalFormat df = new DecimalFormat("00");
        String mString = String.valueOf(mInt / 100) + "-" + df.format(mInt % 100);
        MonthTrend t = new MonthTrend(mString, m.getValue().intValue(), 0);
        trends[i++] = t;
      }
      
      // rent price
      for (i = 0; i < monthTrends.size(); i++) {
        if (monthTrends.get(i).getMinRentPrice() > 0 && monthTrends.get(i).getMaxRentPrice() > 0) {
          minRentPrice = monthTrends.get(i).getMinRentPrice();
          maxRentPrice = monthTrends.get(i).getMaxRentPrice();
          break;
        }
      }
    }
    
    Set<Double> priceSet = new HashSet<Double>(finalMonthPrice.values());
    if (trends == null || trends.length == 0 || (priceSet.size() == 1 && priceSet.contains((double) 0))) {
      MonthTrend thisMonth = new MonthTrend(sdf.format(Calendar.getInstance().getTime()), (int) r.getPrice(), 0);
      
      trends = new MonthTrend[1];
      trends[0] = thisMonth;
    }
    wrapper.setTrends(trends);
    wrapper.setMinRentPrice(minRentPrice);
    wrapper.setMaxRentPrice(maxRentPrice);
    
    return wrapper;
  }
  
  @Override
  public void populatePrice(ResidenceEntity entity, MonthTrendWrapper wrapper) {
    
    MonthTrend[] trends = wrapper.getTrends();
    
    entity.setMonthTrend(wrapper.getTrends());
    
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
    
    entity.setMinRentPrice((int) wrapper.getMinRentPrice());
    entity.setMaxRentPrice((int) wrapper.getMaxRentPrice());
  }
}
