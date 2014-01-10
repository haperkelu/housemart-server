package org.housemart.server.web.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.server.beans.MonthTrend;
import org.housemart.server.beans.ResidenceDetail;
import org.housemart.server.beans.ResidencePic;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.dao.entities.HousePicEntity;
import org.housemart.server.dao.entities.HousePicSortEntity;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.data.UserInfoData;
import org.housemart.server.service.ResidenceService;
import org.housemart.server.service.SearchService;
import org.housemart.server.service.residence.IPriceStrategy;
import org.housemart.server.service.residence.PriceByAuto;
import org.housemart.server.service.residence.PriceByMonthData;
import org.housemart.server.util.PicSizeUtils;
import org.housemart.server.util.PicSizeUtils.SizeType;
import org.housemart.server.util.ResidenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResidenceController extends BaseController {
  @Autowired
  @SuppressWarnings("rawtypes")
  private GenericDao residenceFollowDao;
  
  @Autowired
  private GenericDao<HousePicEntity> housePicDao;
  
  @Autowired
  private GenericDao<HousePicSortEntity> housePicSortDao;
  
  @Autowired
  private ResidenceService residenceService;
  
  @Autowired
  private SearchService searchService;
  
  private static final Logger logger = LoggerFactory.getLogger(ResidenceController.class);
  
  @RequestMapping(value = "residence/detail.controller")
  public ModelAndView residenceDetail(@RequestParam int residenceId) {
    
    ResultBean bean = new ResultBean();
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    ResidenceDetail detail = new ResidenceDetail();
    detail.setCityId(1);
    detail.setAddress("虹桥路168号/徐家汇");
    detail.setDeveloper("上海东方海外徐家汇房地产有限公司");
    detail.setFinishedTime("1900-10");
    detail.setIsFollow(false);
    detail.setGreenRate(0.09);
    detail.setHouseHold("5000");
    detail.setId(residenceId);
    detail.setLastSeasonDealCount(100);
    detail.setLastSeasonRentCount(300);
    detail.setMonthTrend(new MonthTrend[] {new MonthTrend("2012-09", 120000, 11000), new MonthTrend("2012-10", 130000, 12000)});
    detail.setOnRentCount(200);
    detail.setOnSaleCount(400);
    detail.setParking("室内单车位或双车位");
    detail.setPicURL(new String[] {
        "http://pic1.ajkimg.com/display/anjuke/0ce9e2-%E6%9B%B9%E5%A4%A9%E4%BF%8A/09bfdee990f0c8da6a718b3cb63abb5e-600x600.jpg",
        "http://pic1.ajkimg.com/display/anjuke/ebd8e0-%E6%9B%B9%E5%A4%A9%E4%BF%8A/9607b3f0e9558e5cb49236667aa8b754-600x600.jpg",
        "http://pic1.ajkimg.com/display/anjuke/d68b1e-%E9%BB%84%E5%85%B0%E8%8B%B1/6718b19318419f4219c33906e1463f86-600x600.jpg"});
    detail.setPlateId(433);
    detail.setPrice("13000");
    detail.setPriceRange("12000~13000");
    detail.setPropertyFee("5rmb");
    detail.setRentRange("2000~3000");
    detail.setResidenceName("东方曼哈顿");
    detail.setTrend(1.003);
    detail.setType("公寓");
    detail.setVolumnRate(2.4);
    bean.setData(detail);
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
  // http://localhost:8080/residence/detailNew.controller?residenceId=1562
  @RequestMapping(value = "residence/detailNew.controller")
  public ModelAndView residenceDetailNew(@RequestParam int residenceId) {
    ResultBean bean = new ResultBean();
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    
    ResidenceEntity r = residenceService.loadResidence(residenceId);
    if (r != null) {
      ResidenceDetail detail = ResidenceUtils.residenceEntity2DetailBean(r);
      
      String clientUID;
      if (detail != null) {
        detail.setIsFollow(false);
        
        // Sort
        List<HousePicEntity> list = searchService.findResidencePicWithSort(detail.getId());
        String[] picURLs = null;
        if (CollectionUtils.isNotEmpty(list)) {
          picURLs = new String[list.size()];
          for (int i = 0; i < list.size(); i++) {
            picURLs[i] = list.get(i).getCloudUrl();
          }
        } else {
          picURLs = detail.getPicURL();
        }
        detail.setPicURL(picURLs);
      }
      
      logger.debug("clientUid residence begin");
      if ((clientUID = this.getRequest().getParameter("clientUId")) != null && detail != null) {
        logger.debug("clientUid residence:" + clientUID);
        detail.setIsFollow(UserInfoData.getInstance().isUserFollowResidence(residenceId, clientUID));
        detail.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(detail.getPicURL(), clientUID, "residence/detailNew.controller",
            SizeType.Default));
        detail.setPicURLWithOriginSize(PicSizeUtils.URL2URLWithSize(detail.getPicURL(), clientUID,
            "residence/detailNew.controller", SizeType.Large));
      }
      bean.setData(detail);
    } else {
      bean.setData(new ResidenceDetail());
    }
    return new ModelAndView("jsonView", "json", bean);
  }
  
  @RequestMapping(value = "residence/pic.controller")
  public ModelAndView residencePic(@RequestParam int residenceId) {
    ResultBean bean = new ResultBean();
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    
    ResidenceEntity r = residenceService.loadResidence(residenceId);
    if (r != null) {
      ResidenceDetail detail = ResidenceUtils.residenceEntity2DetailBean(r);
      
      List<HousePicEntity> list = searchService.findResidencePicWithSort(residenceId);
      
      // Final
      String[] picURLs = null;
      if (CollectionUtils.isNotEmpty(list)) {
        picURLs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
          picURLs[i] = list.get(i).getCloudUrl();
        }
      } else {
        picURLs = detail.getPicURL();
      }
      
      ResidencePic pic = new ResidencePic();
      pic.setPicURL(picURLs);
      
      String clientUID;
      logger.debug("clientUid residence begin");
      if ((clientUID = this.getRequest().getParameter("clientUId")) != null && picURLs != null) {
        logger.debug("clientUid residence:" + clientUID);
        pic.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(picURLs, clientUID, "residence/detailNew.controller", SizeType.Default));
        pic.setPicURLWithOriginSize(PicSizeUtils.URL2URLWithSize(picURLs, clientUID, "residence/detailNew.controller",
            SizeType.Large));
      }
      bean.setData(pic);
    } else {
      bean.setData(new ResidencePic());
    }
    return new ModelAndView("jsonView", "json", bean);
  }
  
  @RequestMapping(value = "residence/switchPrice.controller")
  public ModelAndView switchResidencePriceStragety(@RequestParam int s) {
    
    IPriceStrategy ps = null;
    if (s == 0) {
      ps = new PriceByMonthData();
    }
    if (s == 1) {
      ps = new PriceByAuto();
    }
    
    if (ps != null) {
      residenceService.setPriceStrategy(ps);
    }
    ResultBean bean = new ResultBean();
    bean.setData(residenceService.getPriceStrategy().getClass().getName());
    return new ModelAndView("jsonView", "json", bean);
  }
}
