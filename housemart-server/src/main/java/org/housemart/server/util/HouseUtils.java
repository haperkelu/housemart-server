/**
 * Created on 2013-1-31
 * 
 */
package org.housemart.server.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.dao.entities.HouseEntity;
import org.housemart.server.dao.entities.HouseTag;

public class HouseUtils {
  private static Log log = LogFactory.getLog(HouseUtils.class);
  
  @SuppressWarnings("rawtypes")
  private static GenericDao houseTagDao = SpringContextHolder.getBean("houseTagDao");
  @SuppressWarnings("rawtypes")
  private static GenericDao houseDao = SpringContextHolder.getBean("houseDao");
  
  public static String getTitle(String tagList) {
    
    String title = StringUtils.EMPTY;
    String[] tagIds = tagList.split(",");
    for (String tagId : tagIds) {
      HouseTag tag = (HouseTag) houseTagDao.load("loadHouseTag", Integer.valueOf(tagId));
      if (tag != null) {
        title += tag.getName() + " ";
      }
    }
    return title.trim();
    
  }
  
  public static String getFloor(int floor) {
    String ret = "";
    if (floor == 1) {
      ret = "低层";
    } else if (floor == 2) {
      ret = "中低层";
    } else if (floor == 3) {
      ret = "中层";
    } else if (floor == 4) {
      ret = "中高层";
    } else if (floor == 5) {
      ret = "高层";
    } else if (floor == 6) {
      ret = "独栋";
    } else if (floor == 7) {
      ret = "双拼";
    } else if (floor == 8) {
      ret = "联排";
    } else if (floor == 9) {
      ret = "叠加";
    } else if (floor == 10) {
      ret = "新里";
    } else if (floor == 11) {
      ret = "老洋房";
    }
    
    return ret;
  }
  
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static float getAvg(int residenceId) {
    float result = 0;
    Map param = new HashMap();
    param.put("residenceId", residenceId);
    param.put("saleStatus", HouseEntity.SaleStatusEnum.Saling.saleStatus);
    List<HouseEntity> houses = houseDao.select("findHouseListWithInteraction", param);
    if (CollectionUtils.isNotEmpty(houses)) {
      float avgSum = 0;
      int n = 0;
      for (HouseEntity house : houses) {
        try {
          if (house.getArea() != null && house.getPrice() != null && house.getArea() > 0 && house.getPrice() > 0) {
            float avg = house.getPrice() / house.getArea();
            avgSum += avg;
            n++;
          }
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      }
      if (n > 0) result = avgSum / n;
    }
    return result;
  }
}
