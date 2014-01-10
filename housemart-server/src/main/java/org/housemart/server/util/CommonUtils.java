/** 
 * @Title: CommonUtils.java
 * @Package org.housemart.server.util
 * @Description: TODO
 * @author Pie.Li
 * @date 2013-4-16 上午10:50:53
 * @version V1.0 
 */
package org.housemart.server.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: CommonUtils
 * @Description: TODO
 * @date 2013-4-16 上午10:50:53
 * 
 */
public class CommonUtils {
  
  /**
   * 
   * @Title: getDefaultDatte
   * @Description: TODO
   * @param @return
   * @return Date
   * @throws
   */
  public static Date getDefaultDate() {
    Calendar c = Calendar.getInstance();
    c.set(1900, 0, 1);
    return c.getTime();
  }
  
  public static String getCity(int id) {
    String city = "上海";
    // TODO:
    return city;
  }
  
  public static SimpleDateFormat FORMAT_DATE_DECADE = new SimpleDateFormat("yyyy");
  public static SimpleDateFormat FORMAT_DATE_SIMPLE = new SimpleDateFormat("yyyy-MM-dd");
  
  public static String CONSTANT_HOUSE_EMERGENT = "急";
  public static String CONSTANT_HOUSE_RECOMMEND = "荐";
}
