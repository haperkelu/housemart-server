/** 
 * @Title: HouseKeywordSearchController.java
 * @Package org.housemart.server.web.controller
 * @Description: TODO
 * @author Pie.Li
 * @date 2013-3-6 下午3:47:04
 * @version V1.0 
 */
package org.housemart.server.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.MonthTrend;
import org.housemart.server.beans.MonthTrendWrapper;
import org.housemart.server.beans.ResidenceBean;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.dao.entities.ResidenceMonthDataEntity;
import org.housemart.server.dao.entities.ResidenceEntity.ForceShowEnum;
import org.housemart.server.data.UserInfoData;
import org.housemart.server.service.ResidenceService;
import org.housemart.server.util.PageUtils;
import org.housemart.server.util.PicSizeUtils;
import org.housemart.server.util.PicSizeUtils.SizeType;
import org.housemart.server.util.ResidenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: HouseKeywordSearchController
 * @Description: TODO
 * @date 2013-3-6 下午3:47:04
 * 
 */
@Controller
public class HouseKeywordSearchController extends BaseController {
  
  @SuppressWarnings("rawtypes")
  @Autowired
  private GenericDao residenceDao;
  
  @Autowired
  private ResidenceService residenceService;
  
  @SuppressWarnings("rawtypes")
  private GenericDao residenceMonthDataDao = SpringContextHolder.getBean("residenceMonthDataDao");
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "house/searchKeyword.controller")
  public ModelAndView searchKeyword(@RequestParam int cityId, @RequestParam String keyword) {
    
    List<ResidenceEntity> rEntities = null;
    ResultBean bean = new ResultBean();
    if (StringUtils.isEmpty(keyword)) {
      bean.setCode(ResutlCodeEnum.ERROR.getType());
      return new ModelAndView("jsonView", "json", bean);
    }
    
    String type = this.getRequest().getParameter("type");
    if (StringUtils.isEmpty(type)) {
      type = "1";
    }
    int typeValue;
    try {
      typeValue = Integer.parseInt(type);
    } catch (NumberFormatException e) {
      bean.setCode(ResutlCodeEnum.ERROR.getType());
      return new ModelAndView("jsonView", "json", bean);
    }
    
    String pageIndex = this.getRequest().getParameter("pageIndex");
    if (StringUtils.isEmpty(pageIndex)) {
      pageIndex = "1";
    }
    int pageIndexValue = 1;
    try {
      pageIndexValue = Integer.parseInt(pageIndex);
    } catch (NumberFormatException e) {}
    
    String pageSize = this.getRequest().getParameter("pageSize");
    if (StringUtils.isEmpty(pageSize)) {
      pageSize = "30";
    }
    int pageSizeValue = 30;
    try {
      pageSizeValue = Integer.parseInt(pageSize);
    } catch (NumberFormatException e) {}
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("cityId", cityId);
    map.put("keyword", "%" + keyword + "%");
    
    if (typeValue == 1) {
      map.put("onSaleCount", 1);
    }
    if (typeValue == 2) {
      map.put("onRentCount", 1);
    }
    
    rEntities = residenceDao.select("findResidence", map);
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    
    String clientUID = this.getRequest().getParameter("clientUId");
    UserInfoData data = UserInfoData.getInstance();
    
    List<ResidenceBean> rBeans = new ArrayList<ResidenceBean>();
    if (CollectionUtils.isNotEmpty(rEntities)) {
      int skip = PageUtils.generateSkipNumber(pageIndexValue, pageSizeValue);
      int size = PageUtils.generateSize(rEntities.size(), skip, pageSizeValue);
      rEntities = rEntities.subList(skip, skip + size);
      
      for (ResidenceEntity rEntity : rEntities) {
        if (rEntity.getZombie() == 1 || (rEntity.getOnSaleCount() < 1 && rEntity.getForceShow() != ForceShowEnum.Show.value)) {
          continue;
        }
        
        // avgprice, turnover rate ..
        Map<String,Object> monthDataParam = new HashMap<String,Object>();
        monthDataParam.put("residenceId", rEntity.getId());
        List<ResidenceMonthDataEntity> monthTrends = residenceMonthDataDao.select("findMonthData", monthDataParam);
        if(monthTrends!=null && monthTrends.size() > 0 && monthTrends.get(0)!=null){
          ResidenceMonthDataEntity monthData = monthTrends.get(0);
          rEntity.setAnnualPriceIncrement(monthData.getAnnualPriceIncrement());
          rEntity.setAnnualTurnoverRate(monthData.getAnnualTurnoverRate());
          rEntity.setAnnualTurnoverPercent(monthData.getAnnualTurnoverPercent());
          rEntity.setRentRevenue(monthData.getRentRevenue());
        }
        
        // month trend
        MonthTrendWrapper trends = residenceService.getPriceStrategy().getResidenceMonthTrendWrapper(rEntity);
        residenceService.getPriceStrategy().populatePrice(rEntity, trends);
        
        ResidenceBean rBean = ResidenceUtils.residenceEntity2Bean(rEntity);
        
        if (clientUID != null) {
          rBean.setIsFollow(UserInfoData.getInstance().isUserFollowResidence(rEntity.getId(), clientUID));
          rBean.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(rBean.getPicURL(), clientUID, "house/searchKeyword.controller",
              SizeType.Default));
        }
        rBeans.add(rBean);
      }
    }
    
    bean.setData(rBeans);
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
}
