package org.housemart.server.web.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.housemart.framework.baiduapi.BaiduAPIWrapper;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.server.beans.CurrenCityBean;
import org.housemart.server.beans.MonthTrendWrapper;
import org.housemart.server.beans.ResidenceBean;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.dao.entities.AreaPositionEntity;
import org.housemart.server.dao.entities.GooglePlaceBaseEntity;
import org.housemart.server.dao.entities.HousePicEntity;
import org.housemart.server.dao.entities.RegionEntity;
import org.housemart.server.dao.entities.RegionExtEntity;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.dao.entities.ResidenceEntity.ForceShowEnum;
import org.housemart.server.data.RegionComlexData;
import org.housemart.server.data.UserInfoData;
import org.housemart.server.map.MapSearchUtils;
import org.housemart.server.service.HouseService;
import org.housemart.server.service.ResidenceService;
import org.housemart.server.service.SearchService;
import org.housemart.server.util.PageUtils;
import org.housemart.server.util.PicSizeUtils;
import org.housemart.server.util.PicSizeUtils.SizeType;
import org.housemart.server.util.ResidenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapSearchController extends BaseController {
	
  private static final Logger LOGGER = LoggerFactory.getLogger(MapSearchController.class);
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao areaPositionDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao regionDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceDao;
  @Autowired
  SearchService searchService;
  @Autowired
  HouseService houseService;
  @Autowired
  ResidenceService residenceService;
  
  private static DecimalFormat df_decimal_1 = new DecimalFormat("0.0");
  
  // http://localhost:8080/house/residenceSale/mapSearchNew.controller?lat=31.1829450&lng=121.5204490&range=6000&pageIndex=1&pageSize=5
  @Scope("request")
  @RequestMapping(value = "house/residenceSale/mapSearchNew.controller")
  public ModelAndView residenceSaleListByMap(final double lat, final double lng,  int range,
       int pageIndex,  int pageSize) {
	  
	ExecutorService singleThreadPool =  Executors.newSingleThreadExecutor();  
	Future<String> future = singleThreadPool.submit(new Callable<String>() {

		@Override
		public String call() throws Exception {
			
			if(lng < 0) {
				List<AreaPositionEntity> list = RegionComlexData.getPlates();
				if(!CollectionUtils.isEmpty(list)){
					boolean isWithin = false;
					for(AreaPositionEntity item: list){
						double distance = MapSearchUtils.getDistance(lat, lng, Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()));
						if(distance <= 1000 * 5){
							return "CA";
						}
					}
				}
			} else {
				String cityCode = BaiduAPIWrapper.invokeRequestCityCodeByLatLng(String.valueOf(lat), String.valueOf(lng));
				return cityCode;
			}			
			return "";
		}
		
	}); 
	  
    GooglePlaceBaseEntity[] gEntities = new GooglePlaceBaseEntity[0];
    List<ResidenceBean> rBeans = new ArrayList<ResidenceBean>();
    int totalCount = 0;
    try {
      gEntities = searchService.searchGooglePlace(lat, lng, range, true, false);
      int skip = PageUtils.generateSkipNumber(pageIndex, pageSize);
      int size = PageUtils.generateSize(gEntities.length, skip, pageSize);
      rBeans = new ArrayList<ResidenceBean>();
      if (!ArrayUtils.isEmpty(gEntities)) {
        totalCount = gEntities.length;
        String clientUID = this.getRequest().getParameter("clientUId");
        
        gEntities = (GooglePlaceBaseEntity[]) ArrayUtils.subarray(gEntities, skip, skip + size);
        Set<Integer> deDuplicate = new HashSet<Integer>();
        
        for (GooglePlaceBaseEntity gEntity : gEntities) {
          if (!deDuplicate.contains(gEntity.getResidenceId())) {
            ResidenceEntity rEntity = (ResidenceEntity) residenceDao.load("loadResidence", gEntity.getResidenceId());
            if (rEntity != null) {
              if (rEntity.getZombie() == 1 || (rEntity.getOnSaleCount() < 1 && rEntity.getForceShow() != ForceShowEnum.Show.value)) {
                continue;
              }
              
              MonthTrendWrapper trends = residenceService.getPriceStrategy().getResidenceMonthTrendWrapper(rEntity);
              residenceService.getPriceStrategy().populatePrice(rEntity, trends);
              
              ResidenceBean rBean = ResidenceUtils.residenceEntity2Bean(rEntity);
              // Sort
              List<HousePicEntity> list = searchService.findResidencePicWithSort(gEntity.getResidenceId());
              String[] picURLs = null;
              if (CollectionUtils.isNotEmpty(list)) {
                picURLs = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                  picURLs[i] = list.get(i).getCloudUrl();
                }
              } else {
                picURLs = rBean.getPicURL();
              }
              rBean.setPicURL(picURLs);
              
              if (rEntity.getLat() != null && rEntity.getLng() != null) {
                rBean.setDistance(df_decimal_1.format(MapSearchUtils.getDistance(lat, lng, Double.valueOf(rEntity.getLat()),
                    Double.valueOf(rEntity.getLng())) / 1000)
                    + "公里");
              }
              if (clientUID != null) {
                rBean.setIsFollow(UserInfoData.getInstance().isUserFollowResidence(gEntity.getResidenceId(), clientUID));
                rBean.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(picURLs, clientUID,
                    "house/residenceSale/mapSearchNew.controller", SizeType.Default));
              }
              
              rBeans.add(rBean);
              deDuplicate.add(gEntity.getResidenceId());
            }
          }
        }
      }
    } catch (Exception e) {
    	LOGGER.error(e.getMessage(), e);
    }
    CurrenCityBean currentCity = new CurrenCityBean();
    currentCity.setBizTag("Current City");
    try {
		String cityCode = future.get(1000, TimeUnit.MILLISECONDS);
		if(cityCode.equalsIgnoreCase("289")){
			currentCity.setCityID(1);
		} else if(cityCode.equalsIgnoreCase("CA")){
			currentCity.setCityID(2);
		}else {
			currentCity.setCityID(-1);
		};
	} catch(Exception e) {
		LOGGER.error(e.getMessage(), e);
		currentCity.setCityID(1);
	}
    ResultBean bean = new ResultBean();
    bean.setCount(rBeans.size());
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setTotalCount(totalCount);
    bean.setData(rBeans);
    bean.setExtData(currentCity);
    return new ModelAndView("jsonView", "json", bean);
  }
  
  // http://localhost:8080/house/residenceSale/searchNew.controller?cityId=1&regionId=359&plateId=389&pageIndex=1&pageSize=5
  @SuppressWarnings({"unchecked", "rawtypes"})
  @RequestMapping(value = "house/residenceSale/searchNew.controller")
  public ModelAndView residenceSaleList(@RequestParam int cityId, @RequestParam int regionId, @RequestParam int plateId,
      @RequestParam int pageIndex, @RequestParam int pageSize) {
    ResultBean bean = new ResultBean();
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    int totalCount = 0;
    
    Map param = new HashMap();
    param.put("cityId", cityId);
    if (regionId > 0) {
      param.put("regionId", regionId);
    }
    if (plateId > 0) {
      param.put("plateId", plateId);
    }
    param.put("onSaleCount", 1);
    List<ResidenceEntity> rEntities = (List<ResidenceEntity>) residenceDao.select("findResidence", param);
    List<ResidenceBean> rBeans = new ArrayList<ResidenceBean>();
    
    if (CollectionUtils.isNotEmpty(rEntities)) {
      totalCount = rEntities.size();
      int skip = PageUtils.generateSkipNumber(pageIndex, pageSize);
      int size = PageUtils.generateSize(rEntities.size(), skip, pageSize);
      rEntities = rEntities.subList(skip, skip + size);
      
      String clientUID = this.getRequest().getParameter("clientUId");
      for (ResidenceEntity rEntity : rEntities) {
        if (rEntity.getOnSaleCount() < 1) {
          continue;
        }
        
        // month trend
        MonthTrendWrapper trends = residenceService.getPriceStrategy().getResidenceMonthTrendWrapper(rEntity);
        residenceService.getPriceStrategy().populatePrice(rEntity, trends);
        
        ResidenceBean rBean = ResidenceUtils.residenceEntity2Bean(rEntity);
        if (clientUID != null) {
          rBean.setIsFollow(UserInfoData.getInstance().isUserFollowResidence(rEntity.getId(), clientUID));
          rBean.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(rBean.getPicURL(), clientUID,
              "house/residenceSale/searchNew.controller", SizeType.Default));
        }
        rBeans.add(rBean);
      }
    }
    bean.setTotalCount(totalCount);
    bean.setCount(rBeans.size());
    bean.setData(rBeans);
    return new ModelAndView("jsonView", "json", bean);
  }
  
  // http://localhost:8080/house/residenceRent/mapSearchNew.controller?lat=31.1829450&lng=121.5204490&range=6000&pageIndex=1&pageSize=1
  @Scope("request")  
  @RequestMapping(value = "house/residenceRent/mapSearchNew.controller")
  public ModelAndView residenceRentListByMap(final @RequestParam double lat, final @RequestParam double lng, @RequestParam int range,
      @RequestParam int pageIndex, @RequestParam int pageSize) {
    
	ExecutorService singleThreadPool =  Executors.newSingleThreadExecutor();  
	Future<String> future = singleThreadPool.submit(new Callable<String>() {

		@Override
		public String call() throws Exception {
			if(lng < 0) {
				List<AreaPositionEntity> list = RegionComlexData.getPlates();
				if(!CollectionUtils.isEmpty(list)){
					boolean isWithin = false;
					for(AreaPositionEntity item: list){
						double distance = MapSearchUtils.getDistance(lat, lng, Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()));
						if(distance <= 1000 * 5){
							return "CA";
						}
					}
				}
			} else {
				String cityCode = BaiduAPIWrapper.invokeRequestCityCodeByLatLng(String.valueOf(lat), String.valueOf(lng));
				return cityCode;
			}			
			return "";
		}
		
	}); 
		    
	GooglePlaceBaseEntity[] gEntities = new GooglePlaceBaseEntity[0];
    List<ResidenceBean> rBeans = new ArrayList<ResidenceBean>();
    try {
      gEntities = searchService.searchGooglePlace(lat, lng, range, false, true);
      rBeans = new ArrayList<ResidenceBean>();
      if (!ArrayUtils.isEmpty(gEntities)) {
        int skip = PageUtils.generateSkipNumber(pageIndex, pageSize);
        int size = PageUtils.generateSize(gEntities.length, skip, pageSize);
        gEntities = (GooglePlaceBaseEntity[]) ArrayUtils.subarray(gEntities, skip, skip + size);
        Set<Integer> deDuplicate = new HashSet<Integer>();
        
        String clientUID = this.getRequest().getParameter("clientUId");
        for (GooglePlaceBaseEntity gEntity : gEntities) {
          if (!deDuplicate.contains(gEntity.getResidenceId())) {
            ResidenceEntity rEntity = (ResidenceEntity) residenceDao.load("loadResidence", gEntity.getResidenceId());
            
            if (rEntity != null) {
              if (rEntity.getZombie() == 1 || rEntity.getOnRentCount() < 1 && rEntity.getForceShow() != ForceShowEnum.Show.value) {
                continue;
              }
              
              MonthTrendWrapper trends = residenceService.getPriceStrategy().getResidenceMonthTrendWrapper(rEntity);
              residenceService.getPriceStrategy().populatePrice(rEntity, trends);
              
              ResidenceBean rBean = ResidenceUtils.residenceEntity2Bean(rEntity);
              if (rEntity.getLat() != null && rEntity.getLng() != null) {
                rBean.setDistance(df_decimal_1.format(MapSearchUtils.getDistance(lat, lng, Double.valueOf(rEntity.getLat()),
                    Double.valueOf(rEntity.getLng())) / 1000)
                    + "公里");
              }
              if (clientUID != null) {
                rBean.setIsFollow(UserInfoData.getInstance().isUserFollowResidence(gEntity.getResidenceId(), clientUID));
                rBean.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(rBean.getPicURL(), clientUID,
                    "house/residenceRent/mapSearchNew.controller", SizeType.Default));
              }
              
              rBeans.add(rBean);
              deDuplicate.add(gEntity.getResidenceId());
            }
          }
        }
      }
    } catch (Exception e) {
    	LOGGER.error(e.getMessage(), e);
    }
    CurrenCityBean currentCity = new CurrenCityBean();
    currentCity.setBizTag("Current City");
    try {
		String cityCode = future.get(1000, TimeUnit.MILLISECONDS);
		if(cityCode.equalsIgnoreCase("289")){
			currentCity.setCityID(1);
		} else if(cityCode.equalsIgnoreCase("CA")){
			currentCity.setCityID(2);
		}else {
			currentCity.setCityID(-1);
		};
	} catch(Exception e) {
		LOGGER.error(e.getMessage(), e);
		currentCity.setCityID(1);
	}
    ResultBean bean = new ResultBean();
    bean.setCount(rBeans.size());
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(rBeans);
    bean.setExtData(currentCity);
    return new ModelAndView("jsonView", "json", bean);
  }
  
  // http://localhost:8080/house/residenceRent/searchNew.controller?cityId=1&regionId=359&plateId=389&pageIndex=1&pageSize=5
  @SuppressWarnings({"unchecked", "rawtypes"})
  @RequestMapping(value = "house/residenceRent/searchNew.controller")
  public ModelAndView residenceRentList(@RequestParam int cityId, @RequestParam int regionId, @RequestParam int plateId,
      @RequestParam int pageIndex, @RequestParam int pageSize) {
    
    ResultBean bean = new ResultBean();
    bean.setCount(20);
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    Map param = new HashMap();
    param.put("cityId", cityId);
    if (regionId > 0) param.put("regionId", regionId);
    if (plateId > 0) param.put("plateId", plateId);
    param.put("onRentCount", 1);
    List<ResidenceEntity> rEntities = (List<ResidenceEntity>) residenceDao.select("findResidence", param);
    List<ResidenceBean> rBeans = new ArrayList<ResidenceBean>();
    
    if (CollectionUtils.isNotEmpty(rEntities)) {
      
      int skip = PageUtils.generateSkipNumber(pageIndex, pageSize);
      int size = PageUtils.generateSize(rEntities.size(), skip, pageSize);
      rEntities = rEntities.subList(skip, skip + size);
      
      String clientUID = this.getRequest().getParameter("clientUId");
      for (ResidenceEntity rEntity : rEntities) {
        if (rEntity.getOnRentCount() < 1) {
          continue;
        }
        
        // month trend
        MonthTrendWrapper trends = residenceService.getPriceStrategy().getResidenceMonthTrendWrapper(rEntity);
        residenceService.getPriceStrategy().populatePrice(rEntity, trends);
        
        ResidenceBean rBean = ResidenceUtils.residenceEntity2Bean(rEntity);
        if (clientUID != null) {
          rBean.setIsFollow(UserInfoData.getInstance().isUserFollowResidence(rEntity.getId(), clientUID));
          rBean.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(rBean.getPicURL(), clientUID,
              "house/residenceRent/searchNew.controller", SizeType.Default));
        }
        rBeans.add(rBean);
      }
    }
    bean.setData(rBeans);
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
  /**
   * 根据经纬度搜索当前所处板块
   * 
   * @param lat
   * @param lng
   * @return
   */
  // http://localhost:8080/plate/nearBy.controller?lat=31.1829450&lng=121.5204490
  @SuppressWarnings("unchecked")
  @Scope("request")
  @RequestMapping(value = "plate/nearBy.controller")
  public ModelAndView plate(@RequestParam double lat, @RequestParam double lng) {
    ResultBean bean = new ResultBean();
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    // TODO 先用取出所有版块，未来用猜测的方法效率
    List<AreaPositionEntity> plates = areaPositionDao.select("findAllPlates", "");
    AreaPositionEntity p = searchService.searchPlateByPosition(lat, lng, plates);
    RegionEntity plate = new RegionEntity();
    
    if (p != null && p.getPositionId() > 0) {
      Object rO = regionDao.load("loadRegionById", p.getPositionId());
      RegionExtEntity r = rO == null ? null : (RegionExtEntity) rO;
      
      if (r != null) {
        plate.setId(r.getId());
        plate.setName(r.getCityName() + " " + r.getParentName() + " " + r.getName());
        plate.setLevel(r.getLevel());
      }
    }
    bean.setData(plate);
    return new ModelAndView("jsonView", "json", bean);
  }
  
}
