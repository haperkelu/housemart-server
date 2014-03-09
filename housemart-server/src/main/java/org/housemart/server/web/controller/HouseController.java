/**
 * Created on 2012-11-30
 * 
 */
package org.housemart.server.web.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.server.beans.HousePic;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.beans.SearchResultBean;
import org.housemart.server.beans.house.HouseDetailBean;
import org.housemart.server.beans.house.HouseRentBean;
import org.housemart.server.beans.house.HouseSaleBean;
import org.housemart.server.dao.entities.GooglePlaceBaseEntity;
import org.housemart.server.dao.entities.HouseEntity;
import org.housemart.server.dao.entities.HousePicEntity;
import org.housemart.server.dao.entities.HousePicSortEntity;
import org.housemart.server.dao.entities.HouseTag;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.data.UserInfoData;
import org.housemart.server.resource.ResourceProvider;
import org.housemart.server.service.AuthenticationService;
import org.housemart.server.service.HouseService;
import org.housemart.server.service.HouseServiceMock;
import org.housemart.server.service.ResidenceService;
import org.housemart.server.service.SearchService;
import org.housemart.server.util.CommonUtils;
import org.housemart.server.util.PicSizeUtils;
import org.housemart.server.util.PicSizeUtils.SizeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author pqin
 */
@Controller
public class HouseController extends BaseController {
  private Log log = LogFactory.getLog(this.getClass());
  @Autowired
  HouseService houseService;
  @Autowired
  SearchService searchService;
  @Autowired
  HouseServiceMock houseServiceMock;
  @Autowired
  GenericDao<ResidenceEntity> residenceDao;
  @Autowired
  GenericDao<HousePicEntity> housePicDao;
  @Autowired
  GenericDao<HousePicSortEntity> housePicSortDao;
  @Autowired
  AuthenticationService authenticationService;
  @Autowired
  ResidenceService residenceService;
  @Autowired
  ResourceProvider resourceProvider;
  
  @SuppressWarnings({"unchecked", "rawtypes"})
  @RequestMapping(value = "house/status.controller")
  public ModelAndView houseStatus(@RequestParam int houseId) throws Exception {
    int saleStatus = houseService.getHouseStatus(houseId, 1);
    int rentStatus = houseService.getHouseStatus(houseId, 2);
    
    Map data = new HashMap();
    data.put("saleStatus", saleStatus);
    data.put("rentStatus", rentStatus);
    data.put("house", houseService.loadDetail(houseId));
    
    ResultBean bean = new ResultBean();
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(data);
    return new ModelAndView("jsonView", "json", bean);
  }
  
  /**
   * 1.1.2 房源详情
   * 
   * @param houseId
   * @return
   */
  // http://localhost:8080/house/detailNew.controller?houseId=77
  @RequestMapping(value = "house/detailNew.controller")
  public ModelAndView detail(@RequestParam int houseId) {
    HouseDetailBean house = null;
    int code = ResutlCodeEnum.SUCCESS.getType();
    try {
      house = houseService.loadDetail(houseId);
      if (house == null) {
        house = new HouseDetailBean();
        code = 201; // 200（房源已下架)/ 201(房源不存在)
      } else {
        if (house.getInteraction() == HouseEntity.InteractionEnum.With.value
            && (houseService.getHouseStatus(house.getId(), 1) == 1 || houseService.getHouseStatus(house.getId(), 2) == 1)) {
          code = ResutlCodeEnum.SUCCESS.getType();
        } else {
          code = 200;
        }
      }
      UserInfoData data = UserInfoData.getInstance();
      String clientUID;
      String type;
      if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
        house.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(house.getPicURL(), clientUID, "house/detailNew.controller",
            SizeType.Default));
        house.setPicURLWithOriginSize(PicSizeUtils.URL2URLWithSize(house.getPicURL(), clientUID, "house/detailNew.controller",
            SizeType.Large));
        house.setResidencePicURLWithSize(PicSizeUtils.URL2URLWithSize(house.getResidencePicURL(), clientUID,
            "house/detailNew.controller", SizeType.Default));
        house.setResidencePicURLWithOriginSize(PicSizeUtils.URL2URLWithSize(house.getResidencePicURL(), clientUID,
            "house/detailNew.controller", SizeType.Large));
        String saleRent;
        if (StringUtils.isNotBlank(house.getTitle().trim())) {
          saleRent = "sale";
          house.setMsiteDesc(house.getTitle());
        } else {
          saleRent = "rent";
          house.setMsiteDesc(house.getRentTitle());
        }
        String mHouseDetailLink = resourceProvider.getValue("housemart.msite.host")
            + MessageFormat.format(resourceProvider.getValue("housemart.msite.house.detail"), house.getId().toString(), clientUID,
                saleRent);
        house.setMsiteUrl(mHouseDetailLink);
      }
      if ((clientUID = this.getRequest().getParameter("clientUId")) != null
          && (type = this.getRequest().getParameter("type")) != null) {
        house.setIsFollow(data.isUserFollowHouse(house.getId(), Integer.parseInt(type), clientUID));
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      house = new HouseDetailBean();
    }
    ResultBean bean = new ResultBean();
    bean.setCode(code);
    bean.setData(house);
    return new ModelAndView("jsonView", "json", bean);
  }
  
  /**
   * 1.2.3 指定小区出售房源列表
   * 
   * @param residenceId
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   */
  // http://localhost:8080/residenceSale/houseListNew.controller?residenceId=1564&orderType=0&pageIndex=1&pageSize=5
  @RequestMapping(value = "residenceSale/houseListNew.controller")
  public ModelAndView residenceSaleList(@RequestParam Integer residenceId, @RequestParam Integer orderType,
      @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
    ResultBean bean = new ResultBean();
    SearchResultBean<HouseSaleBean> saleList = null;
    try {
      saleList = houseService.findResidenceSaleHouse(HouseEntity.SaleStatusEnum.Saling.saleStatus, residenceId, orderType,
          pageIndex, pageSize);
      if (saleList == null) {
        saleList = new SearchResultBean<HouseSaleBean>();
      }
      if (saleList.getData() == null) {
        saleList.setData(new ArrayList<HouseSaleBean>());
      }
      
      UserInfoData data = UserInfoData.getInstance();
      String clientUID;
      if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
        for (HouseSaleBean item : saleList.getData()) {
          item.setIsFollow(data.isUserFollowHouse(item.getId(), 1, clientUID));
          item.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUID, "residenceSale/houseListNew.controller",
              SizeType.Default));
          item.setPicURLWithOriginSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUID,
              "residenceSale/houseListNew.controller", SizeType.Large));
          
          item.setResidencePicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getResidencePicURL(), clientUID,
              "residenceSale/houseListNew.controller", SizeType.ResidenceDefault));
          item.setResidencePicURLWithOriginSize(PicSizeUtils.URL2URLWithSize(item.getResidencePicURL(), clientUID,
              "residenceSale/houseListNew.controller", SizeType.ResidenceLarge));
        }
      }
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      saleList = new SearchResultBean<HouseSaleBean>();
      saleList.setData(new ArrayList<HouseSaleBean>());
    }
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(saleList.getData());
    bean.setTotalCount(saleList.getTotalCount());
    if (CollectionUtils.isNotEmpty(saleList.getData())) {
      bean.setCount(CollectionUtils.size(saleList.getData()));
    }
    return new ModelAndView("jsonView", "json", bean);
  }
  
  /**
   * 1.2.4 指定小区已售房源列表
   * 
   * @param residenceId
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   */
  // http://localhost:8080/residenceSold/houseListNew.controller?residenceId=1564&orderType=0&pageIndex=1&pageSize=5
  @RequestMapping(value = "residenceSold/houseListNew.controller")
  public ModelAndView residenceSoldList(@RequestParam Integer residenceId, @RequestParam Integer orderType,
      @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
    ResultBean bean = new ResultBean();
    SearchResultBean<HouseSaleBean> saleList = new SearchResultBean<HouseSaleBean>();
    try {
      saleList = houseService.findResidenceSaleHouse(HouseEntity.SaleStatusEnum.Sold.saleStatus, residenceId, orderType, pageIndex,
          pageSize);
      
      if (saleList == null) {
        saleList = new SearchResultBean<HouseSaleBean>();
      }
      
      if (saleList.getData() == null) {
        saleList.setData(new ArrayList<HouseSaleBean>());
      }
      
      for (HouseSaleBean s : saleList.getData()) {
        s.setAddress(s.getSoldAddress());
        s.setPicURL("http://housemart.qiniudn.com/salehousedefault_1366810967425.png");
      }
      
      UserInfoData data = UserInfoData.getInstance();
      String clientUID;
      if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
        for (HouseSaleBean item : saleList.getData()) {
          item.setIsFollow(data.isUserFollowHouse(item.getId(), 1, clientUID));
          item.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUID, "residenceSold/houseListNew.controller",
              SizeType.Default));
        }
      }
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      saleList = new SearchResultBean<HouseSaleBean>();
      saleList.setData(new ArrayList<HouseSaleBean>());
    }
    
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(saleList.getData());
    bean.setTotalCount(saleList.getTotalCount());
    if (CollectionUtils.isNotEmpty(saleList.getData())) {
      bean.setCount(saleList.getData().size());
    }
    return new ModelAndView("jsonView", "json", bean);
  }
  
  /**
   * 1.3.3 指定小区出租房源列表
   * 
   * @param residenceId
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   */
  // http://localhost:8080/residenceRent/houseListNew.controller?residenceId=1564&orderType=0&pageIndex=1&pageSize=5
  @RequestMapping(value = "residenceRent/houseListNew.controller")
  public ModelAndView residenceRentList(@RequestParam Integer residenceId, @RequestParam Integer orderType,
      @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
    ResultBean bean = new ResultBean();
    SearchResultBean<HouseRentBean> rentList = new SearchResultBean<HouseRentBean>();
    rentList.setData(new ArrayList<HouseRentBean>());
    
    try {
      rentList = houseService.findResidenceRentHouse(HouseEntity.RentStatusEnum.Renting.value, residenceId, orderType, pageIndex,
          pageSize);
      if (rentList == null) {
        rentList = new SearchResultBean<HouseRentBean>();
      }
      if (rentList.getData() == null) {
        rentList.setData(new ArrayList<HouseRentBean>());
      }
      
      UserInfoData data = UserInfoData.getInstance();
      String clientUID;
      if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
        for (HouseRentBean item : rentList.getData()) {
          item.setIsFollow(data.isUserFollowHouse(item.getId(), 2, clientUID));
          item.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUID, "residenceRent/houseListNew.controller",
              SizeType.Default));
          item.setPicURLWithOriginSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUID,
              "residenceRent/houseListNew.controller", SizeType.Large));
          
          item.setResidencePicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getResidencePicURL(), clientUID,
              "residenceRent/houseListNew.controller", SizeType.ResidenceDefault));
          item.setResidencePicURLWithOriginSize(PicSizeUtils.URL2URLWithSize(item.getResidencePicURL(), clientUID,
              "residenceRent/houseListNew.controller", SizeType.ResidenceLarge));
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      rentList = new SearchResultBean<HouseRentBean>();
      rentList.setData(new ArrayList<HouseRentBean>());
    }
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(rentList.getData());
    bean.setTotalCount(rentList.getTotalCount());
    if (CollectionUtils.isNotEmpty(rentList.getData())) {
      bean.setCount(rentList.getData().size());
    }
    return new ModelAndView("jsonView", "json", bean);
  }
  
  /**
   * 1.3.4 指定小区已租房源列表
   * 
   * @param residenceId
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   */
  // http://localhost:8080/residenceRenting/houseListNew.controller?residenceId=1564&orderType=0&pageIndex=1&pageSize=5
  @RequestMapping(value = "residenceRenting/houseListNew.controller")
  public ModelAndView residenceRentingList(@RequestParam Integer residenceId, @RequestParam Integer orderType,
      @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
    ResultBean bean = new ResultBean();
    SearchResultBean<HouseRentBean> rentList = null;
    try {
      rentList = houseService.findResidenceRentHouse(HouseEntity.RentStatusEnum.Rented.value, residenceId, orderType, pageIndex,
          pageSize);
      if (rentList == null) {
        rentList = new SearchResultBean<HouseRentBean>();
      }
      if (rentList.getData() == null) {
        rentList.setData(new ArrayList<HouseRentBean>());
      }
      
      UserInfoData data = UserInfoData.getInstance();
      String clientUID;
      if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
        for (HouseRentBean item : rentList.getData()) {
          item.setIsFollow(data.isUserFollowHouse(item.getId(), 2, clientUID));
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      rentList = new SearchResultBean<HouseRentBean>();
    }
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(rentList.getData());
    bean.setTotalCount(rentList.getTotalCount());
    if (CollectionUtils.isNotEmpty(rentList.getData())) {
      bean.setCount(rentList.getData().size());
    }
    return new ModelAndView("jsonView", "json", bean);
  }
  
  /**
   * 1.4.1 精确查询出售房源列表(地点)
   * 
   * @param cityId
   * @param regionId
   * @param plateId
   * @param type
   * @param roomType
   * @param priceRange
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   */
  // http://localhost:8080/house/sale/detailedSearchByPositionNew.controller?cityId=1&regionId=359&plateId=389&type=4&roomType=2&priceRange=0-10000&orderType=1&pageIndex=1&pageSize=10
  @RequestMapping(value = "house/sale/detailedSearchByPositionNew.controller")
  public ModelAndView saleDetailedListByPosition(@RequestParam Integer cityId, @RequestParam Integer regionId,
      @RequestParam Integer plateId, @RequestParam Integer type, @RequestParam Integer roomType, @RequestParam Integer priceRange,
      @RequestParam Integer orderType, @RequestParam Integer pageIndex, @RequestParam Integer pageSize, String keyword,
      Integer residenceId) {
    
    ResultBean bean = new ResultBean();
    SearchResultBean<HouseSaleBean> saleList = new SearchResultBean<HouseSaleBean>();
    try {
      int[] price = getPriceOfSale(priceRange);
      int[] residenceIds = null;
      if (residenceId != null) {
        // 小区id
        residenceIds = new int[1];
        residenceIds[0] = residenceId;
        saleList = houseService.detailFindSaleHouse(HouseEntity.SaleStatusEnum.Saling.saleStatus, cityId, regionId, plateId,
            residenceIds, type, roomType, price[0], price[1], orderType, pageIndex, pageSize);
      } else {
        if (StringUtils.isNotBlank(keyword)) {
          // 关键字
          residenceIds = getResidencesByKeyword(keyword, cityId, 1);
          if (residenceIds != null && residenceIds.length > 0) {
            saleList = houseService.detailFindSaleHouse(HouseEntity.SaleStatusEnum.Saling.saleStatus, cityId, regionId, plateId,
                residenceIds, type, roomType, price[0], price[1], orderType, pageIndex, pageSize);
          }
        } else {
          // 其它
          saleList = houseService.detailFindSaleHouse(HouseEntity.SaleStatusEnum.Saling.saleStatus, cityId, regionId, plateId,
              null, type, roomType, price[0], price[1], orderType, pageIndex, pageSize);
        }
      }
      
      if (saleList == null) {
        saleList = new SearchResultBean<HouseSaleBean>();
        saleList.setData(new ArrayList<HouseSaleBean>());
      } else {
        if (saleList.getData() == null) {
          saleList.setData(new ArrayList<HouseSaleBean>());
        }
        
        String clientUID;
        UserInfoData data = UserInfoData.getInstance();
        if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
          for (HouseSaleBean item : saleList.getData()) {
            item.setIsFollow(data.isUserFollowHouse(item.getId(), 1, clientUID));
            item.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUID,
                "house/sale/detailedSearchByPositionNew.controller", SizeType.Default));
          }
        }
      }
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      saleList = new SearchResultBean<HouseSaleBean>();
      saleList.setData(new ArrayList<HouseSaleBean>());
    }
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(saleList.getData() == null ? new ArrayList<HouseSaleBean>() : saleList.getData());
    bean.setTotalCount(saleList.getTotalCount());
    if (CollectionUtils.isNotEmpty(saleList.getData())) {
      bean.setCount(saleList.getData().size());
    }
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
  /**
   * 1.4.2 精确查询出售房源列表(附近)
   * 
   * @param cityId
   * @param lat
   * @param lng
   * @param range
   * @param type
   * @param roomType
   * @param priceRange
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   */
  // http://localhost:8080/house/sale/detailedSearchNearbyNew.controller?cityId=1&lat=31.1829450&lng=121.5204490&range=6000&type=4&roomType=2&priceRange=0-99999999&orderType=1&pageIndex=1&pageSize=10
  @RequestMapping(value = "house/sale/detailedSearchNearbyNew.controller")
  public ModelAndView saleDetailedListNearBy(@RequestParam Integer cityId, @RequestParam String lat, @RequestParam String lng,
      @RequestParam Integer range, @RequestParam Integer type, @RequestParam Integer roomType, @RequestParam Integer priceRange,
      @RequestParam Integer orderType, @RequestParam Integer pageIndex, @RequestParam Integer pageSize, String keyword) {
    ResultBean bean = new ResultBean();
    SearchResultBean<HouseSaleBean> saleList = new SearchResultBean<HouseSaleBean>();
    GooglePlaceBaseEntity[] places = null;
    Map<Integer,GooglePlaceBaseEntity> resMap = null;
    try {
      int r = getRangeOf(range);
      places = searchService.searchGooglePlace(Double.valueOf(lat), Double.valueOf(lng), r, true, false);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    if (!ArrayUtils.isEmpty(places)) {
      int[] keywordMathResidenceIds = getResidencesByKeyword(keyword, cityId, 1);
      List<Integer> resIds = new ArrayList<Integer>();
      if (keywordMathResidenceIds != null) {
        for (int rId : keywordMathResidenceIds) {
          resIds.add(rId);
        }
      }
      
      resMap = new HashMap<Integer,GooglePlaceBaseEntity>();
      
      if (StringUtils.isNotBlank(keyword)) {
        // 有小区关键字条件
        if (CollectionUtils.isNotEmpty(resIds)) {
          for (GooglePlaceBaseEntity gPlace : places) {
            if (resIds.contains(gPlace.getResidenceId())) {
              resMap.put(gPlace.getResidenceId(), gPlace);
            }
          }
        }
      } else {
        // 无小区关键字条件
        for (GooglePlaceBaseEntity gPlace : places) {
          resMap.put(gPlace.getResidenceId(), gPlace);
        }
      }
    }
    
    if (resMap != null && resMap.size() > 0) {
      try {
        int[] price = getPriceOfSale(priceRange);
        saleList = houseService.detailFindSaleHouseWithPositionInfo(HouseEntity.SaleStatusEnum.Saling.saleStatus, cityId, null,
            null, resMap, Double.valueOf(lat), Double.valueOf(lng), type, roomType, price[0], price[1], orderType, pageIndex,
            pageSize);
        if (saleList == null) {
          saleList = new SearchResultBean<HouseSaleBean>();
          saleList.setData(new ArrayList<HouseSaleBean>());
        } else {
          if (saleList.getData() == null) {
            saleList.setData(new ArrayList<HouseSaleBean>());
          }
          
          String clientUID;
          UserInfoData data = UserInfoData.getInstance();
          if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
            for (HouseSaleBean item : saleList.getData()) {
              item.setIsFollow(data.isUserFollowHouse(item.getId(), 1, clientUID));
              item.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUID,
                  "house/sale/detailedSearchNearbyNew.controller", SizeType.Default));
            }
          }
        }
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        saleList = new SearchResultBean<HouseSaleBean>();
        saleList.setData(new ArrayList<HouseSaleBean>());
      }
    }
    
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(saleList.getData() == null ? new ArrayList<HouseSaleBean>() : saleList.getData());
    bean.setTotalCount(saleList.getTotalCount());
    if (CollectionUtils.isNotEmpty(saleList.getData())) {
      bean.setCount(saleList.getData().size());
    }
    return new ModelAndView("jsonView", "json", bean);
  }
  
  /**
   * 1.4.3 精确查询出租房源列表(地点)
   * 
   * @param cityId
   * @param regionId
   * @param plateId
   * @param type
   * @param roomType
   * @param priceRange
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   */
  // http://localhost:8080/house/rent/detailedSearchByPositionNew.controller?cityId=1&regionId=359&plateId=389&type=4&roomType=2&priceRange=0-10000&orderType=1&pageIndex=1&pageSize=10
  @RequestMapping(value = "house/rent/detailedSearchByPositionNew.controller")
  public ModelAndView rentDetailedListByPosition(@RequestParam Integer cityId, @RequestParam Integer regionId,
      @RequestParam Integer plateId, @RequestParam Integer type, @RequestParam Integer roomType, @RequestParam Integer priceRange,
      @RequestParam Integer orderType, @RequestParam Integer pageIndex, @RequestParam Integer pageSize, String keyword,
      Integer residenceId) {
    ResultBean bean = new ResultBean();
    SearchResultBean<HouseRentBean> rentList = new SearchResultBean<HouseRentBean>();
    try {
      int[] price = getPriceOfRent(priceRange);
      int[] residenceIds = null;
      if (residenceId != null) {
        // 小区id
        residenceIds = new int[1];
        residenceIds[0] = residenceId;
        rentList = houseService.detailFindRentHouse(HouseEntity.RentStatusEnum.Renting.value, cityId, regionId, plateId,
            residenceIds, type, roomType, price[0], price[1], orderType, pageIndex, pageSize);
      } else {
        if (StringUtils.isNotBlank(keyword)) {
          // 关键字
          residenceIds = getResidencesByKeyword(keyword, cityId, 1);
          if (residenceIds != null && residenceIds.length > 0) {
            rentList = houseService.detailFindRentHouse(HouseEntity.RentStatusEnum.Renting.value, cityId, regionId, plateId,
                residenceIds, type, roomType, price[0], price[1], orderType, pageIndex, pageSize);
          }
        } else {
          // 其它
          rentList = houseService.detailFindRentHouse(HouseEntity.RentStatusEnum.Renting.value, cityId, regionId, plateId, null,
              type, roomType, price[0], price[1], orderType, pageIndex, pageSize);
        }
        
      }
      
      if (rentList == null) {
        rentList = new SearchResultBean<HouseRentBean>();
        rentList.setData(new ArrayList<HouseRentBean>());
      } else {
        if (rentList.getData() == null) {
          rentList.setData(new ArrayList<HouseRentBean>());
        }
        
        String clientUID;
        UserInfoData data = UserInfoData.getInstance();
        if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
          for (HouseRentBean item : rentList.getData()) {
            item.setIsFollow(data.isUserFollowHouse(item.getId(), 2, clientUID));
            item.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUID,
                "house/rent/detailedSearchByPositionNew.controller", SizeType.Default));
          }
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      rentList = new SearchResultBean<HouseRentBean>();
      rentList.setData(new ArrayList<HouseRentBean>());
    }
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(rentList.getData() == null ? new ArrayList<HouseRentBean>() : rentList.getData());
    bean.setTotalCount(rentList.getTotalCount());
    if (CollectionUtils.isNotEmpty(rentList.getData())) {
      bean.setCount(rentList.getData().size());
    }
    return new ModelAndView("jsonView", "json", bean);
  }
  
  /**
   * 1.4.4 精确查询出租房源列表(附近)
   * 
   * @param cityId
   * @param lat
   * @param lng
   * @param range
   * @param type
   * @param roomType
   * @param priceRange
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   */
  // http://localhost:8080/house/rent/detailedSearchNearbyNew.controller?cityId=1&lat=31.1825150&lng=121.5204490&range=6000&type=4&roomType=2&priceRange=0-99999999&orderType=1&pageIndex=1&pageSize=10
  @RequestMapping(value = "house/rent/detailedSearchNearbyNew.controller")
  public ModelAndView rentDetailedListNearBy(@RequestParam Integer cityId, @RequestParam String lat, @RequestParam String lng,
      @RequestParam Integer range, @RequestParam Integer type, @RequestParam Integer roomType, @RequestParam Integer priceRange,
      @RequestParam Integer orderType, @RequestParam Integer pageIndex, @RequestParam Integer pageSize, String keyword) {
    ResultBean bean = new ResultBean();
    SearchResultBean<HouseRentBean> rentList = new SearchResultBean<HouseRentBean>();
    GooglePlaceBaseEntity[] places = null;
    Map<Integer,GooglePlaceBaseEntity> resMap = null;
    try {
      int r = getRangeOf(range);
      places = searchService.searchGooglePlace(Double.valueOf(lat), Double.valueOf(lng), r, false, true);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    if (!ArrayUtils.isEmpty(places)) {
      int[] keywordMathResidenceIds = getResidencesByKeyword(keyword, cityId, 2);
      List<Integer> resIds = new ArrayList<Integer>();
      if (keywordMathResidenceIds != null) {
        for (int rId : keywordMathResidenceIds) {
          resIds.add(rId);
        }
      }
      
      resMap = new HashMap<Integer,GooglePlaceBaseEntity>();
      if (StringUtils.isNotBlank(keyword)) {
        // 有小区关键字条件
        if (CollectionUtils.isNotEmpty(resIds)) {
          for (GooglePlaceBaseEntity gPlace : places) {
            if (resIds.contains(gPlace.getResidenceId())) {
              resMap.put(gPlace.getResidenceId(), gPlace);
            }
          }
        }
      } else {
        // 无小区关键字条件
        for (GooglePlaceBaseEntity gPlace : places) {
          resMap.put(gPlace.getResidenceId(), gPlace);
        }
      }
      
    }
    if (resMap != null && resMap.size() > 0) {
      try {
        int[] price = getPriceOfRent(priceRange);
        rentList = houseService.detailFindRentHouseWithPositionInfo(HouseEntity.RentStatusEnum.Renting.value, cityId, null, null,
            resMap, Double.valueOf(lat), Double.valueOf(lng), type, roomType, price[0], price[1], orderType, pageIndex, pageSize);
        if (rentList == null) {
          rentList = new SearchResultBean<HouseRentBean>();
          rentList.setData(new ArrayList<HouseRentBean>());
        } else {
          if (rentList.getData() == null) {
            rentList.setData(new ArrayList<HouseRentBean>());
          }
          
          String clientUID;
          UserInfoData data = UserInfoData.getInstance();
          if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
            for (HouseRentBean item : rentList.getData()) {
              item.setIsFollow(data.isUserFollowHouse(item.getId(), 2, clientUID));
              item.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUID,
                  "house/rent/detailedSearchNearbyNew.controller", SizeType.Default));
            }
          }
        }
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        rentList = new SearchResultBean<HouseRentBean>();
        rentList.setData(new ArrayList<HouseRentBean>());
      }
    }
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(rentList.getData() == null ? new ArrayList<HouseRentBean>() : rentList.getData());
    bean.setTotalCount(rentList.getTotalCount());
    if (CollectionUtils.isNotEmpty(rentList.getData())) {
      bean.setCount(rentList.getData().size());
    }
    return new ModelAndView("jsonView", "json", bean);
  }
  
  /**
   * 默认推荐小区
   * 
   * @param houseId
   * @param securityKey
   * @param type
   *          1售，2租
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "recommend/residence/houseList.controller")
  public ModelAndView residenceHouseList(Integer houseId, Integer residenceId, @RequestParam String secret, @RequestParam int type,
      @RequestParam int orderType, @RequestParam Integer pageIndex, @RequestParam Integer pageSize) throws Exception {
    ResultBean bean = new ResultBean();
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    
    List<ResidenceEntity> residences = new ArrayList<ResidenceEntity>();
    
    if (houseId != null) {
      // 根据houseId推荐
      HouseDetailBean house = houseService.loadDetail(houseId);
      if (house != null && house.getResidenceId() != null) {
        residenceId = house.getResidenceId();
      }
      
      int brokerId = authenticationService.decodeBrokerId(secret);
      
      residences = residenceService.getBrokerResidences(brokerId);
      
      if (residences != null) {
        
        List<Integer> hasSaleDataResidence = new ArrayList<Integer>();
        List<Integer> hasRentDataResidence = new ArrayList<Integer>();
        boolean isTakeChage = false;
        boolean isDataReady = false;
        for (ResidenceEntity rsd : residences) {
          ResidenceEntity r = residenceService.loadResidence(rsd.getId());
          
          if (r != null && !(hasSaleDataResidence.size() > 0 && hasRentDataResidence.size() > 0)) {
            int saleCount = r.getOnSaleCount();
            int rentCount = r.getOnRentCount();
            
            if (saleCount > 0) {
              hasSaleDataResidence.add(rsd.getId());
            }
            if (rentCount > 0) {
              hasRentDataResidence.add(rsd.getId());
            }
          } else {
            isDataReady = true;
          }
          
          if (r != null && r.getId() == residenceId) {
            isTakeChage = true;
          }
          
          if (isDataReady && isTakeChage) {
            break;
          }
        }
        
        if (type == 1) {
          // sale
          List<HouseSaleBean> saleList = new ArrayList<HouseSaleBean>();
          
          if (isTakeChage) {
            // residenceId是负责小区
            ResultBean rBean = (ResultBean) saleDetailedListByPosition(null, null, null, null, null, null, orderType, pageIndex,
                pageSize, null, residenceId).getModel().get("json");
            saleList = (List<HouseSaleBean>) rBean.getData();
          } else {
            // residenceId非负责小区
            if (hasSaleDataResidence.size() > 0) {
              residenceId = hasSaleDataResidence.get(0);
              ResultBean rBean = (ResultBean) saleDetailedListByPosition(null, null, null, null, null, null, orderType, pageIndex,
                  pageSize, null, residenceId).getModel().get("json");
              saleList = (List<HouseSaleBean>) rBean.getData();
            }
          }
          
          bean.setData(saleList);
        }
        if (type == 2) {
          // rent
          List<HouseRentBean> rentList = new ArrayList<HouseRentBean>();
          
          if (isTakeChage) {
            // residenceId是负责小区
            ResultBean rBean = (ResultBean) rentDetailedListByPosition(null, null, null, null, null, null, orderType, pageIndex,
                pageSize, null, residenceId).getModel().get("json");
            rentList = (List<HouseRentBean>) rBean.getData();
          } else {
            // residenceId非负责小区
            if (hasSaleDataResidence.size() > 0) {
              residenceId = hasSaleDataResidence.get(0);
              ResultBean rBean = (ResultBean) rentDetailedListByPosition(null, null, null, null, null, null, orderType, pageIndex,
                  pageSize, null, residenceId).getModel().get("json");
              rentList = (List<HouseRentBean>) rBean.getData();
            }
            
            bean.setData(rentList);
          }
        }
      }
      
    } else {
      // 根据residenceId推荐
      
      if (type == 1) {
        // sale
        List<HouseSaleBean> saleList = new ArrayList<HouseSaleBean>();
        
        ResultBean rBean = (ResultBean) saleDetailedListByPosition(null, null, null, null, null, null, orderType, pageIndex,
            pageSize, null, residenceId).getModel().get("json");
        saleList = (List<HouseSaleBean>) rBean.getData();
        
        bean.setData(saleList);
      }
      if (type == 2) {
        // rent
        List<HouseRentBean> rentList = new ArrayList<HouseRentBean>();
        
        ResultBean rBean = (ResultBean) rentDetailedListByPosition(null, null, null, null, null, null, orderType, pageIndex,
            pageSize, null, residenceId).getModel().get("json");
        rentList = (List<HouseRentBean>) rBean.getData();
        
        bean.setData(rentList);
      }
      
    }
    
    return new ModelAndView("jsonView", "json", bean);
  }
  
  @RequestMapping(value = "house/pic.controller")
  public ModelAndView housePic(@RequestParam int houseId) {
    ResultBean bean = new ResultBean();
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    HousePic result = new HousePic();
    
    List<HousePicEntity> finalPicData = new ArrayList<HousePicEntity>();
    // pics
    Map<String,Object> housePicsSortPara = new HashMap<String,Object>();
    housePicsSortPara.put("houseId", houseId);
    housePicsSortPara.put("type", HousePicEntity.Type.HousePic.getValue());
    List<HousePicEntity> housePics = housePicDao.select("findHousePicByHouseIdAndType", housePicsSortPara);
    List<HousePicSortEntity> housePicsSort = housePicSortDao.select("findHousePicSortByHouseIdAndType", housePicsSortPara);
    if (CollectionUtils.isNotEmpty(housePics)) {
      List<HousePicEntity> sortedPics = housePics;
      if (CollectionUtils.isNotEmpty(housePicsSort)) {
        sortedPics = searchService.sortPics(housePics, housePicsSort.get(0).getSort());
      }
      finalPicData.addAll(sortedPics);
    }
    
    Map<String,Object> roomTypesSortPara = new HashMap<String,Object>();
    roomTypesSortPara.put("houseId", houseId);
    roomTypesSortPara.put("type", HousePicEntity.Type.RoomType.getValue());
    List<HousePicEntity> roomTypes = housePicDao.select("findHousePicByHouseIdAndType", roomTypesSortPara);
    List<HousePicSortEntity> roomTypesSort = housePicSortDao.select("findHousePicSortByHouseIdAndType", roomTypesSortPara);
    if (CollectionUtils.isNotEmpty(roomTypes)) {
      List<HousePicEntity> sortedPics = roomTypes;
      if (CollectionUtils.isNotEmpty(roomTypesSort)) {
        sortedPics = searchService.sortPics(roomTypes, roomTypesSort.get(0).getSort());
      }
      finalPicData.addAll(sortedPics);
    }
    
    if (CollectionUtils.isNotEmpty(finalPicData)) {
      List<Integer> picId = new ArrayList<Integer>();
      List<String> picURLWithSize = new ArrayList<String>();
      List<String> picURLWithOriginSize = new ArrayList<String>();
      
      String clientUID = this.getRequest().getParameter("clientUId");
      for (HousePicEntity p : finalPicData) {
        if (p == null || StringUtils.isBlank(p.getCloudUrl())) {
          continue;
        }
        String urlWithSize = PicSizeUtils.URL2URLWithSize(p.getCloudUrl(), clientUID, "house/detailNew.controller",
            SizeType.Default);
        String urlWithOriginSize = PicSizeUtils.URL2URLWithSize(p.getCloudUrl(), clientUID, "house/detailNew.controller",
            SizeType.Large);
        
        picId.add(p.getId());
        picURLWithSize.add(urlWithSize);
        picURLWithOriginSize.add(urlWithOriginSize);
      }
      result.setId(picId.toArray(new Integer[picId.size()]));
      result.setPicURLWithSize(picURLWithSize.toArray(new String[picURLWithSize.size()]));
      result.setPicURLWithOriginSize(picURLWithOriginSize.toArray(new String[picURLWithOriginSize.size()]));
    }
    
    bean.setData(result);
    return new ModelAndView("jsonView", "json", bean);
  }
  
  @RequestMapping(value = "house/index/rebuild.controller")
  public ModelAndView rebuildIndex() {
    
    ResultBean bean = new ResultBean();
    try {
      if (SearchService.HOUSE_INDEX_MUTEX < SearchService.HOUSE_INDEX_MUTEX_EXPECT) {
        log.warn("one house index task is runing, this task will exit, current index mutex " + SearchService.HOUSE_INDEX_MUTEX);
        bean.setCode(ResutlCodeEnum.ERROR.getType());
        bean.setMsg("one house index task is runing, this task will exit, current index mutex " + SearchService.HOUSE_INDEX_MUTEX);
        
        return new ModelAndView("jsonView", "json", bean);
      }
      
      searchService.resetLastTaskTime();
      searchService.resetTmpDir();
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    return new ModelAndView("jsonView", "json", bean);
  }
  
  @RequestMapping(value = "house/index/forceRebuild.controller")
  public ModelAndView forceRebuildIndex() {
    
    ResultBean bean = new ResultBean();
    try {
      if (SearchService.HOUSE_INDEX_MUTEX < SearchService.HOUSE_INDEX_MUTEX_EXPECT) {
        SearchService.HOUSE_INDEX_MUTEX = SearchService.HOUSE_INDEX_MUTEX_EXPECT;
        log.warn("one house index task is runing, force reset index mutex to  " + SearchService.HOUSE_INDEX_MUTEX_EXPECT);
      }
      
      searchService.deleteAllInteractionNotice();
      searchService.resetLastTaskTime();
      searchService.resetTmpDir();
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    return new ModelAndView("jsonView", "json", bean);
  }
  
  private int[] getResidencesByKeyword(String keyword, Integer cityId, int type) {
    int[] residenceIds = null;
    if (StringUtils.isNotBlank(keyword)) {
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("cityId", cityId);
      map.put("keyword", "%" + keyword + "%");
      if (type == 1) {
        map.put("onSaleCount", 1);
      }
      if (type == 2) {
        map.put("onRentCount", 1);
      }
      List<ResidenceEntity> rEntities = residenceDao.select("findResidence", map);
      if (rEntities != null) {
        residenceIds = new int[rEntities.size()];
        for (int i = 0; i < rEntities.size(); i++) {
          residenceIds[i] = rEntities.get(i).getId();
        }
      }
    }
    return residenceIds;
  }
  
  private int[] getPriceOfSale(Integer priceRange) {
    int[] ret = {0, 0};
    if (priceRange == null || priceRange == 0) {
      ret = new int[] {0, 999990000};
    } else if (priceRange == 1) {
      ret = new int[] {0, 2999999};
    } else if (priceRange == 2) {
      ret = new int[] {3000000, 5999999};
    } else if (priceRange == 3) {
      ret = new int[] {6000000, 9999999};
    } else ret = new int[] {10000000, 999990000};
    return ret;
  }
  
  private int[] getPriceOfRent(Integer priceRange) {
    int[] ret = {0, 0};
    if (priceRange == null || priceRange == 0) {
      ret = new int[] {0, Integer.MAX_VALUE};
    } else if (priceRange == 1) {
      ret = new int[] {0, 2999};
    } else if (priceRange == 2) {
      ret = new int[] {3000, 4999};
    } else if (priceRange == 3) {
      ret = new int[] {5000, 7999};
    } else if (priceRange == 4) {
      ret = new int[] {8000, 19999};
    } else ret = new int[] {20000, Integer.MAX_VALUE};
    return ret;
  }
  
  private int getRangeOf(Integer range) {
    int ret = 0;
    if (range == null || range == 0) {
      ret = 6000;
    } else if (range == 1) {
      ret = 2000;
    } else if (range == 2) {
      ret = 4000;
    } else if (range == 3) {
      ret = 6000;
    } else ret = 6000;
    return ret;
  }
  
  @SuppressWarnings("unused")
  @Deprecated
  private Integer[] getPriceOf(String priceRange) {
    Integer[] prices = new Integer[2];
    Integer priceMin = Integer.MIN_VALUE;
    Integer priceMax = Integer.MAX_VALUE;
    // A, A-B, B+
    if (StringUtils.isNotBlank(priceRange)) {
      if (priceRange.indexOf('-') > -1) {
        priceMin = Integer.valueOf(priceRange.split("-")[0]);
        priceMax = Integer.valueOf(priceRange.split("-")[1]);
      } else if (priceRange.indexOf('+') > -1) {
        priceMin = Integer.valueOf(StringUtils.substringBefore(priceRange, "+"));
      } else {
        priceMax = Integer.valueOf(priceRange);
      }
    }
    
    prices[0] = priceMin;
    prices[1] = priceMax;
    return prices;
  }
  
  @RequestMapping(value = "house/attributes.controller")
  public ModelAndView houseAttributes(@RequestParam(required = false) Integer floor, @RequestParam(required = false) Integer type,
      @RequestParam(required = false) Integer decorating, @RequestParam(required = false) Integer direction,
      @RequestParam(required = false) Integer buildYear, @RequestParam(required = false) Integer rentEquipment,
      @RequestParam(required = false) Integer rentMemo, @RequestParam(required = false) Integer saleMemo,
      @RequestParam(required = false) Integer rentTag, @RequestParam(required = false) Integer saleTag) {
    
    floor = (floor == null ? 0 : floor);
    type = (type == null ? 0 : type);
    decorating = (decorating == null ? 0 : decorating);
    direction = (direction == null ? 0 : direction);
    buildYear = (buildYear == null ? 0 : buildYear);
    rentEquipment = (rentEquipment == null ? 0 : rentEquipment);
    rentMemo = (rentMemo == null ? 0 : rentMemo);
    saleMemo = (saleMemo == null ? 0 : saleMemo);
    rentTag = (rentTag == null ? 0 : rentTag);
    saleTag = (saleTag == null ? 0 : saleTag);
    
    Map<String,Object> attributes = new HashMap<String,Object>();
    
    if (floor == 1) {
      Map<String,String> floorMap = new LinkedHashMap<String,String>();
      floorMap.put("1", "低层");
      floorMap.put("2", "中低层");
      floorMap.put("3", "中层");
      floorMap.put("4", "中高层");
      floorMap.put("5", "高层");
      floorMap.put("6", "独栋");
      floorMap.put("7", "双拼");
      floorMap.put("8", "联排");
      floorMap.put("9", "叠加");
      floorMap.put("10", "新里");
      floorMap.put("11", "老洋房");
      floorMap.put("_keyOrder", CommonUtils.getMapKeyOrder(floorMap));
      attributes.put("floor", floorMap);
    }
    
    if (type == 1) {
      Map<String,String> typeMap = new LinkedHashMap<String,String>();
      typeMap.put("4", "公寓");
      typeMap.put("5", "老公房");
      typeMap.put("7", "独栋");
      typeMap.put("8", "双拼");
      typeMap.put("2", "联排");
      typeMap.put("9", "叠加");
      typeMap.put("3", "新里");
      typeMap.put("10", "洋房");
      typeMap.put("_keyOrder", CommonUtils.getMapKeyOrder(typeMap));
      attributes.put("type", typeMap);
    }
    
    if (decorating == 1) {
      Map<String,String> decoratingMap = new LinkedHashMap<String,String>();
      decoratingMap.put("5", "豪装");
      decoratingMap.put("1", "精装");
      decoratingMap.put("2", "简装");
      decoratingMap.put("3", "毛坯");
      decoratingMap.put("_keyOrder", CommonUtils.getMapKeyOrder(decoratingMap));
      attributes.put("decorating", decoratingMap);
    }
    
    if (direction == 1) {
      Map<String,String> directionMap = new LinkedHashMap<String,String>();
      directionMap.put("1000", "东");
      directionMap.put("100", "南");
      directionMap.put("10", "西");
      directionMap.put("1", "北");
      directionMap.put("1100", "东南");
      directionMap.put("1001", "东北");
      directionMap.put("110", "西南");
      directionMap.put("11", "西北");
      directionMap.put("101", "南北");
      directionMap.put("1010", "东西");
      directionMap.put("_keyOrder", CommonUtils.getMapKeyOrder(directionMap));
      attributes.put("direction", directionMap);
    }
    
    if (buildYear == 1) {
      Calendar calendar = Calendar.getInstance();
      int buildYearMap[] = new int[calendar.get(Calendar.YEAR) - 1900 + 1];
      
      for (int i = 0; i < buildYearMap.length; i++) {
        buildYearMap[i] = calendar.get(Calendar.YEAR) - i;
      }
      
      attributes.put("buildYear", buildYearMap);
    }
    
    if (rentEquipment == 1) {
      Map<String,String> rentEquipmentMap = new LinkedHashMap<String,String>();
      rentEquipmentMap.put("water", "水");
      rentEquipmentMap.put("power", "电");
      rentEquipmentMap.put("gas", "煤");
      rentEquipmentMap.put("heat", "暖气");
      rentEquipmentMap.put("cable", "有线电视");
      rentEquipmentMap.put("network", "宽带");
      rentEquipmentMap.put("tv", "电视");
      rentEquipmentMap.put("refrigerator", "冰箱");
      rentEquipmentMap.put("airCondition", "空调");
      rentEquipmentMap.put("washer", "洗衣机");
      rentEquipmentMap.put("waterHeater", "热水机");
      rentEquipmentMap.put("microwave", "微波炉");
      rentEquipmentMap.put("telephone", "电话");
      rentEquipmentMap.put("_keyOrder", CommonUtils.getMapKeyOrder(rentEquipmentMap));
      attributes.put("rentEquipment", rentEquipmentMap);
    }
    
    if (rentMemo == 1) {
      Map<String,int[]> rentMemoMap = new LinkedHashMap<String,int[]>();
      int[] pay = {0, 1, 2, 3, 4, 5};
      int[] mortage = {0, 1, 2, 3, 4, 5};
      rentMemoMap.put("付", pay);
      rentMemoMap.put("押", mortage);
      
      attributes.put("rentMemo", rentMemoMap);
    }
    
    if (saleMemo == 1) {
      Map<String,String> saleMemoMap = new LinkedHashMap<String,String>();
      saleMemoMap.put("0", "不选");
      saleMemoMap.put("1", "税费各付价");
      saleMemoMap.put("2", "房东到手价");
      saleMemoMap.put("_keyOrder", CommonUtils.getMapKeyOrder(saleMemoMap));
      attributes.put("saleMemo", saleMemoMap);
    }
    
    if (rentTag == 1) {
      Map<String,String> rentTagMap = new LinkedHashMap<String,String>();
      List<HouseTag> rentTags = houseService.getTagOptionsByCategory("rent");
      for (HouseTag tag : rentTags) {
        rentTagMap.put(tag.getId().toString(), tag.getName());
      }
      rentTagMap.put("_keyOrder", CommonUtils.getMapKeyOrder(rentTagMap));
      attributes.put("rentTag", rentTagMap);
    }
    
    if (rentTag == 1) {
      Map<String,String> saleTagMap = new LinkedHashMap<String,String>();
      List<HouseTag> saleTags = houseService.getTagOptionsByCategory("sale");
      for (HouseTag tag : saleTags) {
        saleTagMap.put(tag.getId().toString(), tag.getName());
      }
      saleTagMap.put("_keyOrder", CommonUtils.getMapKeyOrder(saleTagMap));
      attributes.put("saleTag", saleTagMap);
    }
    
    ResultBean bean = new ResultBean();
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(attributes);
    return new ModelAndView("jsonView", "json", bean);
  }
}
