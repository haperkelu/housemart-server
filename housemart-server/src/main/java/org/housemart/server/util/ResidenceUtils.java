/**
 * Created on 2013-3-2
 * 
 */
package org.housemart.server.util;

import java.text.DecimalFormat;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.server.beans.Position;
import org.housemart.server.beans.ResidenceBean;
import org.housemart.server.beans.ResidenceDetail;
import org.housemart.server.dao.entities.ResidenceEntity;

public class ResidenceUtils {
  private static DecimalFormat df_decimal_2 = new DecimalFormat("0.00");
  private static final String PIC_SPLITTER = "12qwaszx,";
  
  public static ResidenceBean residenceEntity2Bean(ResidenceEntity rEntity) {
    ResidenceBean rBean = new ResidenceBean();
    rBean.setFansCount(rEntity.getFansCount());
    rBean.setIsFollow(rEntity.getIsFollow());
    rBean.setId(rEntity.getId());
    rBean.setLastSeasonDealCount(rEntity.getLastSeasonDealCount());
    rBean.setLastMonthDealCount(rEntity.getLastMonthDealCount());
    rBean.setOnSaleCount(rEntity.getOnSaleCount());
    rBean.setOnRentCount(rEntity.getOnRentCount());
    if (StringUtils.isNotBlank(rEntity.getPicURL()) && !ArrayUtils.isEmpty(rEntity.getPicURL().split(PIC_SPLITTER))
        && rEntity.getPicURL().split(PIC_SPLITTER).length > 0) rBean.setPicURL(rEntity.getPicURL().split(PIC_SPLITTER));
    if (StringUtils.isNotBlank(rEntity.getLat()) && StringUtils.isNotBlank(rEntity.getLng())) rBean.setPosition(new Position(Double
        .valueOf(rEntity.getLat()), Double.valueOf(rEntity.getLng())));
    rBean.setPrice(df_decimal_2.format((float) rEntity.getPrice() / 10000) + "万/平米");
    rBean.setRentPrice(String.valueOf(rEntity.getMinRentPrice() + "元/月起"));
    rBean.setPriceRange(String.valueOf(rEntity.getMinPrice() / 10000) + "万" + "-" + String.valueOf(rEntity.getMaxPrice() / 10000)
        + "万");
    rBean.setRentRange(String.valueOf(rEntity.getMinRentPrice()) + "元/月-" + String.valueOf(rEntity.getMaxRentPrice()) + "元/月");
    rBean.setResidenceId(rEntity.getId());
    rBean.setResidenceName(rEntity.getResidenceName());
    rBean
        .setTrend(Double.valueOf(df_decimal_2.format(generateTrend(rEntity.getThisMonthAvgPrice(), rEntity.getLastMonthAvgPrice()))));
    rBean.setPlateName(CommonUtils.getCity(rEntity.getCityId()) + " " + rEntity.getRegionName() + " " + rEntity.getPlateName());
    
    rBean.setAnnualTurnoverRate(df_decimal_2.format(rEntity.getAnnualTurnoverPercent() * 100) + "%");
    rBean.setRentRevenue(df_decimal_2.format(rEntity.getRentRevenue() * 100) + "%");
    rBean.setAnnualPriceIncreasement(df_decimal_2.format(rEntity.getAnnualPriceIncrement() * 100) + "%");
    
    rBean.setForceShow(rEntity.getForceShow());
    rBean.setZombie(rEntity.getZombie());
    return rBean;
  }
  
  public static ResidenceDetail residenceEntity2DetailBean(ResidenceEntity rEntity) {
    ResidenceDetail detail = new ResidenceDetail();
    detail.setAddress(rEntity.getAddress());
    detail.setCityId(rEntity.getCityId());
    detail.setDeveloper(rEntity.getDeveloper());
    detail.setFinishedTime(rEntity.getFinishedTime());
    if (rEntity.getGreenRate() != null) detail.setGreenRate(rEntity.getGreenRate());
    detail.setHouseHold(rEntity.getHouseHold());
    detail.setId(rEntity.getId());
    detail.setIsFollow(rEntity.getIsFollow());
    detail.setLastMonthDealCount(rEntity.getLastMonthDealCount());
    detail.setLastSeasonDealCount(rEntity.getLastSeasonDealCount());
    detail.setLastSeasonRentCount(rEntity.getLastSeasonRentCount());
    detail.setMonthTrend(rEntity.getMonthTrend());
    detail.setOnRentCount(rEntity.getOnRentCount());
    detail.setOnSaleCount(rEntity.getOnSaleCount());
    detail.setParking(rEntity.getParking());
    if (StringUtils.isNotBlank(rEntity.getPicURL())) detail.setPicURL(rEntity.getPicURL().split(PIC_SPLITTER));
    detail.setPlateId(rEntity.getPlateId());
    detail.setPrice(df_decimal_2.format((float) rEntity.getPrice() / 10000) + "万/平米");
    detail.setRentPrice(String.valueOf(rEntity.getMinRentPrice()) + "元/月起");
    detail.setPriceRange(String.valueOf(rEntity.getMinPrice() / 10000) + "万" + "-" + rEntity.getMaxPrice() / 10000);
    detail.setPropertyFee(rEntity.getPropertyFee());
    detail.setRentRange(String.valueOf(rEntity.getMinRentPrice()) + "元/月" + "-" + rEntity.getMaxRentPrice());
    detail.setResidenceName(rEntity.getResidenceName());
    detail
        .setTrend(Double.valueOf(df_decimal_2.format(generateTrend(rEntity.getThisMonthAvgPrice(), rEntity.getLastMonthAvgPrice()))));
    detail.setType(rEntity.getPropertyType());
    if (rEntity.getVolumeRate() != null) {
      detail.setVolumnRate(rEntity.getVolumeRate());
    }
    
    detail.setAnnualTurnoverRate(df_decimal_2.format(rEntity.getAnnualTurnoverPercent() * 100) + "%");
    detail.setRentRevenue(df_decimal_2.format(rEntity.getRentRevenue() * 100) + "%");
    detail.setAnnualPriceIncreasement(df_decimal_2.format(rEntity.getAnnualPriceIncrement() * 100) + "%");
    
    detail.setForceShow(rEntity.getForceShow());
    detail.setZombie(rEntity.getZombie());
    return detail;
  }
  
  public static double generateTrend(double thisMonthAvg, double lastMonthAvg) {
    double trend = (double) 0;
    
    if (lastMonthAvg == 0 && thisMonthAvg == 0) {} else if (lastMonthAvg == 0) {
      trend = (double) 1;
    } else {
      trend = (double) (thisMonthAvg - lastMonthAvg) / lastMonthAvg;
    }
    return trend;
  }
}
