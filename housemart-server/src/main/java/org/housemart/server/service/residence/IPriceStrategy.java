/**
 * Created on 2014-1-5
 * 
 */
package org.housemart.server.service.residence;

import org.housemart.server.beans.MonthTrendWrapper;
import org.housemart.server.dao.entities.ResidenceEntity;

public interface IPriceStrategy {
  
  MonthTrendWrapper getResidenceMonthTrendWrapper(ResidenceEntity r);
  
  void populatePrice(ResidenceEntity entity, MonthTrendWrapper trendsWrapper);
}
