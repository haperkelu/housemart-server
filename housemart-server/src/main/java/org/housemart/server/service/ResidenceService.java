/**
 * Created on 2013-7-2
 * 
 */
package org.housemart.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.MonthTrendWrapper;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.dao.entities.ResidencePriceHistoryEntity;
import org.housemart.server.service.residence.IPriceStrategy;
import org.housemart.server.service.residence.PriceByMonthData;

public class ResidenceService {
  
  @SuppressWarnings("rawtypes")
  private GenericDao residenceDao = SpringContextHolder.getBean("residenceDao");
  @SuppressWarnings("rawtypes")
  private GenericDao residencePriceHistoryDao = SpringContextHolder.getBean("residencePriceHistoryDao");
  @SuppressWarnings({"rawtypes", "unused"})
  private GenericDao accountDao = SpringContextHolder.getBean("accountDao");
  private IPriceStrategy priceStrategy = new PriceByMonthData();
  
  public ResidenceEntity loadResidence(int id) {
    ResidenceEntity r = (ResidenceEntity) residenceDao.load("loadResidence", id);
    
    if (r != null) {
      // month trend
      MonthTrendWrapper trends = priceStrategy.getResidenceMonthTrendWrapper(r);
      priceStrategy.populatePrice(r, trends);
    }
    
    return r;
  }
  
  @SuppressWarnings("unchecked")
  public List<ResidenceEntity> findThisMonthAvg() {
    return residenceDao.select("findThisMonthAvg", null);
  }
  
  public int addHistoryPrice(ResidencePriceHistoryEntity history) {
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("residenceId", history.getResidenceId());
    para.put("avgPrice", history.getAvgPrice());
    para.put("addTime", history.getAddTime());
    return residencePriceHistoryDao.add("addPriceHistory", para);
  }
  
  @SuppressWarnings("unchecked")
  public List<ResidenceEntity> getBrokerResidences(int brokerId) {
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("brokerId", brokerId);
    return residenceDao.select("findAccountResidenceList", para);
  }
  
  public IPriceStrategy getPriceStrategy() {
    return priceStrategy;
  }
  
  public void setPriceStrategy(IPriceStrategy priceStrategy) {
    this.priceStrategy = priceStrategy;
  }
  
}
