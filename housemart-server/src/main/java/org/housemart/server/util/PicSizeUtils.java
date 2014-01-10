/**
 * Created on 2013-6-30
 * 
 */
package org.housemart.server.util;

import java.text.MessageFormat;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.dao.entities.ClientRegisterEntity;
import org.housemart.server.service.AuthenticationService;

public class PicSizeUtils {
  
  private static final Log log = LogFactory.getLog(PicSizeUtils.class);
//  private static String FORMAT_ORIGIN_ZOOM = "imageView/2/w/{0}/h/{1}/q/{2}"; // 原图进行缩略
//  private static String FORMAT_ORIGIN_QUALITY = "imageView/2/w/600/q/{0}"; // 指定目标缩略图的图像质量，取值范围1-100
  private static String FORMAT_ORIGIN_ZOOM = "imageMogr/v2/thumbnail/!{0}x{1}r/quality/{2}"; // 原图进行缩略
  private static String FORMAT_ORIGIN_QUALITY = "imageMogr/v2/thumbnail/600x/quality/{0}"; // 指定目标缩略图的图像质量，取值范围1-100
  
  // origin http://housemart.qiniudn.com/watermark _1380769195170.png
  // base64
  // aHR0cDovL2hvdXNlbWFydC5xaW5pdWRuLmNvbS93YXRlcm1hcmsgXzEzODA3NjkxOTUxNzAucG5n
  // total
  // http://qiniuphotos.qiniudn.com/gogopher.jpg?watermark/1/image/aHR0cDovL2hvdXNlbWFydC5xaW5pdWRuLmNvbS93YXRlcm1hcmsgXzEzODA3NjkxOTUxNzAucG5n/dissolve/100/gravity/Center
  
  // origin http://housemart.qiniudn.com/watermark%2004_1381404185194.png
  // base64
  // aHR0cDovL2hvdXNlbWFydC5xaW5pdWRuLmNvbS93YXRlcm1hcmslMjAwNF8xMzgxNDA0MTg1MTk0LnBuZw==
  // total
  // http://qiniuphotos.qiniudn.com/gogopher.jpg?watermark/1/image/aHR0cDovL2hvdXNlbWFydC5xaW5pdWRuLmNvbS93YXRlcm1hcmslMjAwNF8xMzgxNDA0MTg1MTk0LnBuZw==/dissolve/100/gravity/Center
  
  private static String SUFIX_WATER_MASK = "|watermark/1/image/aHR0cDovL2hvdXNlbWFydC5xaW5pdWRuLmNvbS93YXRlcm1hcmslMjAwNF8xMzgxNDA0MTg1MTk0LnBuZw==/dissolve/100/gravity/Center";
  
  private static AuthenticationService clientInfoService = SpringContextHolder.getBean("authenticationService");
  
  public static enum SizeType {
    Large, Small, Default, ResidenceLarge, ResidenceSamll, ResidenceDefault
  }
  
  public static String URL2URLWithSize(String url, String clientUId, String controller, SizeType type) {
    
    if (StringUtils.isBlank(url)) {
      return url;
    }
    
    ClientRegisterEntity rEntity = null;
    if (!StringUtils.isBlank(clientUId)) {
      rEntity = clientInfoService.getClientInfo(clientUId);
    }
    
    if (rEntity == null || StringUtils.isBlank(rEntity.getSystemInfo())) {
      rEntity = new ClientRegisterEntity();
      rEntity.setSystemInfo("iPhone4");
      // iphone4 by default
    }
    
    log.info("ClientOSInfo::" + rEntity.getSystemInfo());
    String deviceInfo = rEntity.getSystemInfo();
    String size = getSize(deviceInfo, controller, type);
    return url + "?" + size;
  }
  
  public static String[] URL2URLWithSize(String[] urls, String clientUId, String controller, SizeType type) {
    if (ArrayUtils.isEmpty(urls)) {
      return urls;
    }
    
    ClientRegisterEntity rEntity = clientInfoService.getClientInfo(clientUId);
    if (rEntity == null || StringUtils.isBlank(rEntity.getSystemInfo())) {
      return urls;
    }
    
    String deviceInfo = rEntity.getSystemInfo();
    String[] urlWithSize = new String[urls.length];
    String size = getSize(deviceInfo, controller, type);
    
    for (int i = 0; i < urls.length; i++) {
      urlWithSize[i] = urls[i] + "?" + size;
    }
    
    return urlWithSize;
  }
  
  // imageView/2/w/200/h/200
  public static String getSize(String deviceInfo, String controller, SizeType type) {
    String size = "";
    
    Device device = Device.deviceOf(deviceInfo);
    switch (device) {
      case iphone4:
        if (StringUtils.containsIgnoreCase(controller, "house/residenceSale/mapSearchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "residenceSale/houseListNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.Default) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
          } else if (type == SizeType.ResidenceLarge) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.ResidenceDefault) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "residenceSold/houseListNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/searchKeyword.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/detailNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "residence/detailNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "house/residenceRent/mapSearchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "residenceRent/houseListNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.Default) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
          } else if (type == SizeType.ResidenceLarge) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.ResidenceDefault) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "house/searchKeyword.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/sale/detailedSearchByPositionNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/sale/detailedSearchNearbyNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/rent/detailedSearchByPositionNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/rent/detailedSearchNearbyNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/residenceSale/searchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/residenceRent/searchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/chat/brokerSend.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 90, 90, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "residence/favorite.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/sale/favorite.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/rent/favorite.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        }
        break;
      case iphone5:
        if (StringUtils.containsIgnoreCase(controller, "house/residenceSale/mapSearchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "residenceSale/houseListNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.Default) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
          } else if (type == SizeType.ResidenceLarge) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.ResidenceDefault) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "residenceSold/houseListNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/searchKeyword.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/detailNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "residence/detailNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "house/residenceRent/mapSearchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "residenceRent/houseListNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.Default) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
          } else if (type == SizeType.ResidenceLarge) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.ResidenceDefault) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "house/searchKeyword.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/sale/detailedSearchByPositionNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/sale/detailedSearchNearbyNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/rent/detailedSearchByPositionNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/rent/detailedSearchNearbyNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/residenceSale/searchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/residenceRent/searchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/chat/brokerSend.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 90, 90, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "residence/favorite.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/sale/favorite.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/rent/favorite.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        }
        break;
      default:
        if (StringUtils.containsIgnoreCase(controller, "house/residenceSale/mapSearchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "residenceSale/houseListNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.Default) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
          } else if (type == SizeType.ResidenceLarge) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.ResidenceDefault) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "residenceSold/houseListNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/searchKeyword.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/detailNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "residence/detailNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "house/residenceRent/mapSearchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "residenceRent/houseListNew.controller")) {
          if (type == SizeType.Large) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.Default) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
          } else if (type == SizeType.ResidenceLarge) {
            size = MessageFormat.format(FORMAT_ORIGIN_QUALITY, 60);
            size = size + SUFIX_WATER_MASK;
          } else if (type == SizeType.ResidenceDefault) {
            size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 640, 300, 60);
          }
        } else if (StringUtils.containsIgnoreCase(controller, "house/searchKeyword.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/sale/detailedSearchByPositionNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/sale/detailedSearchNearbyNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/rent/detailedSearchByPositionNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/rent/detailedSearchNearbyNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/residenceSale/searchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/residenceRent/searchNew.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/chat/brokerSend.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 90, 90, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "residence/favorite.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/sale/favorite.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        } else if (StringUtils.containsIgnoreCase(controller, "house/rent/favorite.controller")) {
          size = MessageFormat.format(FORMAT_ORIGIN_ZOOM, 144, 144, 60);
        }
    }
    
    return size;
  }
  
  public static enum Device {
    iphone4, iphone5;
    
    public static Device deviceOf(String deviceInfo) {
      Device d = iphone4;
      if (deviceInfo.startsWith("iPhone5")) {
        d = iphone5;
      }
      return d;
    }
  }
  
}
