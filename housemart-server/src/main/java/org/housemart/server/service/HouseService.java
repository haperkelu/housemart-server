/**
 * Created on 2012-11-30
 * 
 */
package org.housemart.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.HttpGet;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.AjaxResultBean;
import org.housemart.server.beans.LandlordInfo;
import org.housemart.server.beans.SearchResultBean;
import org.housemart.server.beans.house.HouseDetailBean;
import org.housemart.server.beans.house.HouseRentBean;
import org.housemart.server.beans.house.HouseSaleBean;
import org.housemart.server.dao.entities.AccountEntity;
import org.housemart.server.dao.entities.GooglePlaceBaseEntity;
import org.housemart.server.dao.entities.HouseEntity;
import org.housemart.server.dao.entities.HouseInteractionExtEntity;
import org.housemart.server.dao.entities.HouseInteractionTransferExtEntity;
import org.housemart.server.dao.entities.HousePicEntity;
import org.housemart.server.dao.entities.HousePicSortEntity;
import org.housemart.server.dao.entities.MaxinRawEntity;
import org.housemart.server.map.MapSearchUtils;
import org.housemart.server.resource.ResourceProvider;
import org.housemart.server.service.enums.RentSortType;
import org.housemart.server.service.enums.SortType;
import org.housemart.server.util.HttpUtils;
import org.housemart.server.util.PicSizeUtils;
import org.housemart.server.util.PicSizeUtils.SizeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pqin
 */
@Service
public class HouseService {
  private static Log log = LogFactory.getLog(HouseService.class);
  
  @SuppressWarnings("rawtypes")
  SearchService searchService = SpringContextHolder.getBean("searchService");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseInteractionDao = SpringContextHolder.getBean("houseInteractionDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseInteractionTransferDao = SpringContextHolder.getBean("houseInteractionTransferDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao accountDao = SpringContextHolder.getBean("accountDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseTagDao = SpringContextHolder.getBean("houseTagDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao housePicDao = SpringContextHolder.getBean("housePicDao");
  private GenericDao housePicSortDao = SpringContextHolder.getBean("housePicSortDao");
  
  private ResourceProvider resourceProvider = SpringContextHolder.getBean("resourceProvider");
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao maxinRawDao;
  
  /**
   * 
   * @param houseId
   * @return
   * @throws Exception
   */
  public HouseDetailBean loadDetail(int houseId) throws Exception {
    
    HouseDetailBean detail = null;
    Query[] queries = {new TermQuery(new Term(HouseEntity.FIELD.id.toString(), String.valueOf(houseId)))};
    Occur[] occurs = {Occur.MUST};
    HouseEntity[] result = searchService.searchHouse(queries, occurs, null, 1, SearchService.DEFAULT_SEARCH_MAX_SIZE);
    if (!ArrayUtils.isEmpty(result)) {
      detail = result[0].getHouseDetailBean();
    }
    return detail;
    
  }
  
  // houseId房源ID，type租房或者售房 1是售房 2是租房
  // 返回： 如果是售房 1是在售 2不售 3已售 如果是租房 1是再租 2不租 3已租
  // 0 表示错误
  public int getHouseStatus(int houseId, int type) throws Exception {
    int status = 0;
    
    Query[] queries = {new TermQuery(new Term(HouseEntity.FIELD.id.toString(), String.valueOf(houseId)))};
    Occur[] occurs = {Occur.MUST};
    HouseEntity[] result = searchService.searchHouse(queries, occurs, null, 1, SearchService.DEFAULT_SEARCH_MAX_SIZE);
    HouseEntity detail = null;
    if (!ArrayUtils.isEmpty(result)) {
      detail = result[0];
    }
    
    if (detail != null && detail.getId() > 0) {
      if (type == 1) {
        status = detail.getSaleStatus();
      }
      if (type == 2) {
        status = detail.getRentStatus();
      }
    }
    
    return status;
  }
  
  /**
   * @throws Exception
   * 
   * @Title: findHouseSaleBeanList
   * @Description: TODO
   * @param @param houseIds
   * @param @return
   * @return List<HouseSaleBean>
   * @throws
   */
  public List<HouseSaleBean> findHouseSaleBeanList(int[] houseIds) throws Exception {
    if (houseIds == null) {
      return null;
    }
    Query[] queries = new Query[houseIds.length];
    Occur[] occurs = new Occur[houseIds.length];
    List<HouseSaleBean> resultFinal = new ArrayList<HouseSaleBean>();
    for (int i = 0; i < houseIds.length; i++) {
      queries[i] = new TermQuery(new Term(HouseEntity.FIELD.id.toString(), String.valueOf(houseIds[i])));
      occurs[i] = Occur.SHOULD;
    }
    
    HouseEntity[] result = searchService.searchHouse(queries, occurs, null, 1, SearchService.DEFAULT_SEARCH_MAX_SIZE);
    if (result != null) {
      
      for (HouseEntity item : result) {
        resultFinal.add(item.getHouseSaleBean());
      }
      
    }
    
    return resultFinal;
    
  };
  
  /**
   * 
   * @Title: findHouseRentBeanList
   * @Description: TODO
   * @param @param houseIds
   * @param @return
   * @param @throws Exception
   * @return List<HouseRentBean>
   * @throws
   */
  public List<HouseRentBean> findHouseRentBeanList(int[] houseIds) throws Exception {
    if (houseIds == null) {
      return null;
    }
    Query[] queries = new Query[houseIds.length];
    Occur[] occurs = new Occur[houseIds.length];
    List<HouseRentBean> resultFinal = new ArrayList<HouseRentBean>();
    for (int i = 0; i < houseIds.length; i++) {
      queries[i] = new TermQuery(new Term(HouseEntity.FIELD.id.toString(), String.valueOf(houseIds[i])));
      occurs[i] = Occur.SHOULD;
    }
    
    HouseEntity[] result = searchService.searchHouse(queries, occurs, null, 1, SearchService.DEFAULT_SEARCH_MAX_SIZE);
    if (result != null) {
      
      for (HouseEntity item : result) {
        resultFinal.add(item.getHouseRentBean());
      }
      
    }
    
    return resultFinal;
    
  };
  
  /**
   * 
   * @param saleStatus
   * @param residenceId
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   * @throws Exception
   */
  public SearchResultBean<HouseSaleBean> findResidenceSaleHouse(int saleStatus, int residenceId, int orderType, int pageIndex,
      int pageSize) throws Exception {
    
    SearchResultBean<HouseSaleBean> searchResult = new SearchResultBean<HouseSaleBean>();
    List<HouseSaleBean> sales = new ArrayList<HouseSaleBean>();
    Query[] queries = {
        NumericRangeQuery.newIntRange(HouseEntity.FIELD.residenceId.toString(), residenceId, residenceId, true, true),
        new TermQuery(new Term(HouseEntity.FIELD.saleStatus.toString(), String.valueOf(saleStatus))),
        new TermQuery(new Term(HouseEntity.FIELD.interaction.toString(), String.valueOf(HouseEntity.InteractionEnum.With.value)))};
    Occur[] occurs = {Occur.MUST, Occur.MUST, Occur.SHOULD};
    
    if (saleStatus == HouseEntity.SaleStatusEnum.Saling.saleStatus) {
      occurs[2] = Occur.MUST;
    }
    
    SortType sortType = SortType.typeOf(orderType);
    Sort sort = null;
    if (sortType != null) {
      switch (sortType) {
        case onboardTime:
          sort = new Sort(new SortField(HouseEntity.FIELD.onboardTime.toString(), SortField.LONG, true));
          break;
        case price:
          sort = new Sort(new SortField(HouseEntity.FIELD.price.toString(), SortField.INT, true));
          break;
        case avg:
          sort = new Sort(new SortField(HouseEntity.FIELD.avg.toString(), SortField.INT, true));
          break;
        case area:
          sort = new Sort(new SortField(HouseEntity.FIELD.area.toString(), SortField.INT, true));
          break;
        default:
          sort = new Sort(new SortField(HouseEntity.FIELD.createTime.toString(), SortField.LONG, true));
      }
    }
    
    HouseEntity[] result = searchService.searchHouse(queries, occurs, sort, pageIndex, pageSize);
    int totalCount = searchService.countHouse(queries, occurs);
    if (!ArrayUtils.isEmpty(result)) {
      for (HouseEntity house : result) {
        try {
          HouseSaleBean saleBean = house.getHouseSaleBean();
          sales.add(saleBean);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      }
    }
    searchResult.setData(sales);
    searchResult.setTotalCount(totalCount);
    return searchResult;
    
  }
  
  /**
   * 
   * @param rentStatus
   * @param residenceId
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   * @throws Exception
   */
  public SearchResultBean<HouseRentBean> findResidenceRentHouse(int rentStatus, int residenceId, int orderType, int pageIndex,
      int pageSize) throws Exception {
    
    SearchResultBean<HouseRentBean> searchResult = new SearchResultBean<HouseRentBean>();
    List<HouseRentBean> rents = new ArrayList<HouseRentBean>();
    Query[] queries = {
        NumericRangeQuery.newIntRange(HouseEntity.FIELD.residenceId.toString(), residenceId, residenceId, true, true),
        new TermQuery(new Term(HouseEntity.FIELD.rentStatus.toString(), String.valueOf(rentStatus))),
        new TermQuery(new Term(HouseEntity.FIELD.interaction.toString(), String.valueOf(HouseEntity.InteractionEnum.With.value)))};
    Occur[] occurs = {Occur.MUST, Occur.MUST, Occur.SHOULD};
    
    if (rentStatus == HouseEntity.RentStatusEnum.Renting.value) {
      occurs[2] = Occur.MUST;
    }
    
    RentSortType sortType = RentSortType.typeOf(orderType);
    Sort sort = null;
    if (sortType != null) {
      switch (sortType) {
        case onboardTime:
          sort = new Sort(new SortField(HouseEntity.FIELD.onboardTime.toString(), SortField.LONG, true));
          break;
        case price:
          sort = new Sort(new SortField(HouseEntity.FIELD.rentPrice.toString(), SortField.INT, true));
          break;
        case area:
          sort = new Sort(new SortField(HouseEntity.FIELD.area.toString(), SortField.FLOAT, true));
          break;
        default:
          sort = new Sort(new SortField(HouseEntity.FIELD.onboardTime.toString(), SortField.LONG, true));
      }
    }
    
    HouseEntity[] result = searchService.searchHouse(queries, occurs, sort, pageIndex, pageSize);
    int totalCount = searchService.countHouse(queries, occurs);
    if (!ArrayUtils.isEmpty(result)) {
      for (HouseEntity house : result) {
        try {
          HouseRentBean rentBean = house.getHouseRentBean();
          rents.add(rentBean);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      }
    }
    searchResult.setData(rents);
    searchResult.setTotalCount(totalCount);
    return searchResult;
  }
  
  public SearchResultBean<HouseSaleBean> detailFindSaleHouseWithPositionInfo(Integer saleStatus, Integer cityId, Integer regionId,
      Integer plateId, Map<Integer,GooglePlaceBaseEntity> residenceMap, double centerLat, double centerLng, Integer type,
      Integer roomType, int priceMin, int priceMax, int orderType, int pageIndex, int pageSize) throws Exception {
    BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
    SearchResultBean<HouseSaleBean> searchResult = new SearchResultBean<HouseSaleBean>();
    List<HouseSaleBean> sales = null;
    List<Query> queryList = new ArrayList<Query>();
    
    if (saleStatus != null) queryList.add(new TermQuery(new Term(HouseEntity.FIELD.saleStatus.toString(), String
        .valueOf(saleStatus))));
    if (cityId != null && cityId > 0) queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.cityId.toString(), cityId,
        cityId, true, true));
    if (regionId != null && regionId > 0) queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.regionId.toString(),
        regionId, regionId, true, true));
    if (plateId != null && plateId > 0) queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.plateId.toString(), plateId,
        plateId, true, true));
    if (MapUtils.isNotEmpty(residenceMap)) {
      BooleanQuery resQuery = new BooleanQuery();
      for (Integer residenceId : residenceMap.keySet()) {
        resQuery.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.residenceId.toString(), residenceId, residenceId, true, true),
            Occur.SHOULD);
      }
      queryList.add(resQuery);
    }
    
    if (type != null) {
      float[] area = getAreaOf(type);
      queryList.add(NumericRangeQuery.newFloatRange(HouseEntity.FIELD.area.toString(), area[0], area[1], true, true));
      
    }
    
    if (roomType != null) {
      int[] rType = getRoomTypeOf(roomType);
      queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.roomType.toString(), rType[0], rType[1], true, true));
    }
    
    queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.price.toString(), priceMin, priceMax, true, true));
    queryList.add(new TermQuery(new Term(HouseEntity.FIELD.status.toString(), String.valueOf(1))));
    queryList.add(new TermQuery(new Term(HouseEntity.FIELD.interaction.toString(), String
        .valueOf(HouseEntity.InteractionEnum.With.value))));
    
    Query[] queries = queryList.toArray(new Query[queryList.size()]);
    Occur[] occurs = new Occur[queries.length];
    for (int i = 0; i < occurs.length; i++)
      occurs[i] = Occur.MUST;
    
    SortType sortType = SortType.typeOf(orderType);
    Sort sort = null;
    if (sortType != null) {
      switch (sortType) {
        case onboardTime:
          sort = new Sort(new SortField(HouseEntity.FIELD.onboardTime.toString(), SortField.LONG, true));
          break;
        case price:
          sort = new Sort(new SortField(HouseEntity.FIELD.price.toString(), SortField.INT, true));
          break;
        case avg:
          sort = new Sort(new SortField(HouseEntity.FIELD.avg.toString(), SortField.INT, true));
          break;
        case area:
          sort = new Sort(new SortField(HouseEntity.FIELD.area.toString(), SortField.INT, true));
          break;
        default:
          sort = new Sort(new SortField(HouseEntity.FIELD.createTime.toString(), SortField.LONG, true));
      }
    }
    
    int count = searchService.countHouse(queries, occurs);
    searchResult.setTotalCount(count);
    
    HouseEntity[] result = searchService.searchHouse(queries, occurs, sort, pageIndex, pageSize);
    
    sales = new ArrayList<HouseSaleBean>();
    if (!ArrayUtils.isEmpty(result)) {
      for (HouseEntity house : result) {
        if (MapUtils.isNotEmpty(residenceMap) && residenceMap.get(house.getResidenceId()) != null) {
          if (StringUtils.isNotBlank(residenceMap.get(house.getResidenceId()).getLat())) {
            house.setLat(Double.valueOf(residenceMap.get(house.getResidenceId()).getLat()));
          }
          if (StringUtils.isNotBlank(residenceMap.get(house.getResidenceId()).getLng())) {
            house.setLng(Double.valueOf(residenceMap.get(house.getResidenceId()).getLng()));
          }
          house.setDistance(MapSearchUtils.getDistance(centerLat, centerLng, house.getLat(), house.getLng()));
        }
        try {
          HouseSaleBean saleBean = house.getHouseSaleBean();
          sales.add(saleBean);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      }
    }
    searchResult.setData(sales);
    
    return searchResult;
    
  }
  
  /**
   * 
   * @param saleStatus
   * @param cityId
   * @param regionId
   * @param plateId
   * @param type
   * @param roomType
   * @param priceMin
   * @param priceMax
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   * @throws Exception
   */
  public SearchResultBean<HouseSaleBean> detailFindSaleHouse(Integer saleStatus, Integer cityId, Integer regionId, Integer plateId,
      int[] residenceIds, Integer type, Integer roomType, int priceMin, int priceMax, int orderType, int pageIndex, int pageSize)
      throws Exception {
    
    if (!ArrayUtils.isEmpty(residenceIds)) {
      Map<Integer,GooglePlaceBaseEntity> residenceMap = new HashMap<Integer,GooglePlaceBaseEntity>();
      for (int resId : residenceIds) {
        residenceMap.put(resId, null);
      }
      return detailFindSaleHouseWithPositionInfo(saleStatus, cityId, regionId, plateId, residenceMap, 0, 0, type, roomType,
          priceMin, priceMax, orderType, pageIndex, pageSize);
    } else {
      return detailFindSaleHouseWithPositionInfo(saleStatus, cityId, regionId, plateId, null, 0, 0, type, roomType, priceMin,
          priceMax, orderType, pageIndex, pageSize);
    }
    
  }
  
  /**
   * 
   * @param rentStatus
   * @param cityId
   * @param regionId
   * @param plateId
   * @param type
   * @param roomType
   * @param priceMin
   * @param priceMax
   * @param orderType
   * @param pageIndex
   * @param pageSize
   * @return
   * @throws Exception
   */
  public SearchResultBean<HouseRentBean> detailFindRentHouseWithPositionInfo(Integer rentStatus, Integer cityId, Integer regionId,
      Integer plateId, Map<Integer,GooglePlaceBaseEntity> residenceMap, double centerLat, double centerLng, Integer type,
      Integer roomType, Integer priceMin, Integer priceMax, int orderType, int pageIndex, int pageSize) throws Exception {
    BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
    SearchResultBean<HouseRentBean> searchResult = new SearchResultBean<HouseRentBean>();
    List<HouseRentBean> rents = null;
    List<Query> queryList = new ArrayList<Query>();
    if (rentStatus != null) queryList.add(new TermQuery(new Term(HouseEntity.FIELD.rentStatus.toString(), String
        .valueOf(rentStatus))));
    if (cityId != null && cityId > 0) queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.cityId.toString(), cityId,
        cityId, true, true));
    if (regionId != null && regionId > 0) queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.regionId.toString(),
        regionId, regionId, true, true));
    if (plateId != null && plateId > 0) queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.plateId.toString(), plateId,
        plateId, true, true));
    if (MapUtils.isNotEmpty(residenceMap)) {
      BooleanQuery resQuery = new BooleanQuery();
      for (Integer residenceId : residenceMap.keySet()) {
        resQuery.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.residenceId.toString(), residenceId, residenceId, true, true),
            Occur.SHOULD);
      }
      queryList.add(resQuery);
    }
    
    if (type != null) {
      float[] area = getAreaOf(type);
      queryList.add(NumericRangeQuery.newFloatRange(HouseEntity.FIELD.area.toString(), area[0], area[1], true, true));
      
    }
    
    if (roomType != null) {
      int[] rType = getRoomTypeOf(roomType);
      queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.roomType.toString(), rType[0], rType[1], true, true));
    }
    queryList.add(NumericRangeQuery.newIntRange(HouseEntity.FIELD.rentPrice.toString(), priceMin, priceMax, true, true));
    queryList.add(new TermQuery(new Term(HouseEntity.FIELD.status.toString(), String.valueOf(1))));
    
    queryList.add(new TermQuery(new Term(HouseEntity.FIELD.interaction.toString(), String
        .valueOf(HouseEntity.InteractionEnum.With.value))));
    
    Query[] queries = queryList.toArray(new Query[queryList.size()]);
    Occur[] occurs = new Occur[queries.length];
    for (int i = 0; i < occurs.length; i++)
      occurs[i] = Occur.MUST;
    
    RentSortType sortType = RentSortType.typeOf(orderType);
    Sort sort = null;
    if (sortType != null) {
      switch (sortType) {
        case onboardTime:
          sort = new Sort(new SortField(HouseEntity.FIELD.onboardTime.toString(), SortField.LONG, true));
          break;
        case price:
          sort = new Sort(new SortField(HouseEntity.FIELD.rentPrice.toString(), SortField.INT, true));
          break;
        case area:
          sort = new Sort(new SortField(HouseEntity.FIELD.area.toString(), SortField.INT, true));
          break;
        default:
          sort = new Sort(new SortField(HouseEntity.FIELD.createTime.toString(), SortField.LONG, true));
      }
    }
    
    int count = searchService.countHouse(queries, occurs);
    searchResult.setTotalCount(count);
    
    HouseEntity[] result = searchService.searchHouse(queries, occurs, sort, pageIndex, pageSize);
    
    rents = new ArrayList<HouseRentBean>();
    if (!ArrayUtils.isEmpty(result)) {
      for (HouseEntity house : result) {
        if (MapUtils.isNotEmpty(residenceMap) && residenceMap.get(house.getResidenceId()) != null) {
          if (StringUtils.isNotBlank(residenceMap.get(house.getResidenceId()).getLat())) house.setLat(Double.valueOf(residenceMap
              .get(house.getResidenceId()).getLat()));
          if (StringUtils.isNotBlank(residenceMap.get(house.getResidenceId()).getLng())) house.setLng(Double.valueOf(residenceMap
              .get(house.getResidenceId()).getLng()));
          house.setDistance(MapSearchUtils.getDistance(centerLat, centerLng, house.getLat(), house.getLng()));
        }
        try {
          HouseRentBean rentBean = house.getHouseRentBean();
          rents.add(rentBean);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      }
    }
    searchResult.setData(rents);
    
    return searchResult;
    
  }
  
  public SearchResultBean<HouseRentBean> detailFindRentHouse(Integer rentStatus, Integer cityId, Integer regionId, Integer plateId,
      int[] residenceIds, Integer type, Integer roomType, Integer priceMin, Integer priceMax, int orderType, int pageIndex,
      int pageSize) throws Exception {
    
    if (!ArrayUtils.isEmpty(residenceIds)) {
      Map<Integer,GooglePlaceBaseEntity> residenceMap = new HashMap<Integer,GooglePlaceBaseEntity>();
      for (int resId : residenceIds) {
        residenceMap.put(resId, null);
      }
      return detailFindRentHouseWithPositionInfo(rentStatus, cityId, regionId, plateId, residenceMap, 0, 0, type, roomType,
          priceMin, priceMax, orderType, pageIndex, pageSize);
    } else {
      return detailFindRentHouseWithPositionInfo(rentStatus, cityId, regionId, plateId, null, 0, 0, type, roomType, priceMin,
          priceMax, orderType, pageIndex, pageSize);
    }
  }
  
  @SuppressWarnings("unchecked")
  public HouseInteractionExtEntity getInteraction(Integer houseID) {
    
    List<HouseInteractionExtEntity> list = null;
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("houseID", houseID);
    map.put("currentTime", new Date());
    map.put("limit", true);
    map.put("limitStart", 0);
    map.put("limitSize", 1);
    list = houseInteractionDao.select("findHouseInteractionList", map);
    
    HouseInteractionExtEntity interaction = null;
    
    if (!list.isEmpty()) {
      interaction = list.get(0);
    }
    return interaction;
  }
  
  private int[] getRoomTypeOf(Integer roomType) {
    
    int minRoomType = 0;
    int maxRoomType = 0;
    
    if (roomType == null || roomType == 0) {
      minRoomType = 0;
      maxRoomType = Integer.MAX_VALUE;
    } else if (roomType == 1) {
      minRoomType = 1000;
      maxRoomType = 1999;
    } else if (roomType == 2) {
      minRoomType = 2000;
      maxRoomType = 2999;
    } else if (roomType == 3) {
      minRoomType = 3000;
      maxRoomType = 3999;
    } else if (roomType == 4) {
      minRoomType = 4000;
      maxRoomType = 4999;
    } else {
      minRoomType = 5000;
      maxRoomType = Integer.MAX_VALUE;
    }
    
    return new int[] {minRoomType, maxRoomType};
  }
  
  private float[] getAreaOf(Integer area) {
    
    float minArea = 0;
    float maxArea = 0;
    
    if (area == null || area == 0) {
      minArea = 0;
      maxArea = 999990000;
    } else if (area == 1) {
      minArea = 0;
      maxArea = 49;
    } else if (area == 2) {
      minArea = 50;
      maxArea = 89;
    } else if (area == 3) {
      minArea = 90;
      maxArea = 129;
    } else if (area == 4) {
      minArea = 130;
      maxArea = 199;
    } else if (area == 5) {
      minArea = 200;
      maxArea = 299;
    } else if (area == 6) {
      minArea = 300;
      maxArea = 499;
    } else {
      minArea = 500;
      maxArea = Integer.MAX_VALUE;
    }
    
    return new float[] {minArea, maxArea};
  }
  
  @SuppressWarnings("unchecked")
  public HouseInteractionTransferExtEntity getInteractionTransfer(Integer houseID, Integer brokerID) {
    
    List<HouseInteractionTransferExtEntity> list = null;
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("houseID", houseID);
    map.put("fromBrokerID", brokerID);
    map.put("status", 1);
    list = houseInteractionTransferDao.select("findHouseInteractionTransferList", map);
    
    if (list.isEmpty()) {
      return null;
    } else {
      HouseInteractionTransferExtEntity transfer = list.get(0);
      if (!transfer.getFinalBrokerStatus()) {
        transfer.setFinalBrokerID(0);
        transfer.setFinalBrokerName("admin");
        transfer.setFinalBrokerStatus(true);
      }
      
      return transfer;
    }
  }
  
  public LandlordInfo getLandlordInfo(int houseId, int brokerId) throws Exception {
    
    LandlordInfo landload = new LandlordInfo();
    
    String url = resourceProvider.getValue("housemart.url.audit.housecontact.view") + "?houseId=" + houseId + "&brokerId="
        + brokerId;
    HttpGet httpGet = new HttpGet(url);
    AjaxResultBean json = HttpUtils.requestJson(httpGet);
    int code = json.getCode();
    
    log.info("LandLordInfo::url~" + url);
    if (code == 1) { // 可以查看
      landload.setCode(1);
      
      Query[] queries = {new TermQuery(new Term(HouseEntity.FIELD.id.toString(), String.valueOf(houseId)))};
      Occur[] occurs = {Occur.MUST};
      HouseEntity[] result = searchService.searchHouse(queries, occurs, null, 1, SearchService.DEFAULT_SEARCH_MAX_SIZE);
      if (!ArrayUtils.isEmpty(result)) {
        log.info("resultSize::" + result.length);
        
        landload = result[0].getLandloadInfo();
        
        if (landload != null) {
          log.info("contactName::" + landload.getLandlordName() + "contactMobile::" + landload.getLandlordMobile());
          
          MaxinRawEntity maxinData = null;
          Object data = maxinRawDao.load("loadRecord", houseId);
          if (data != null) {
            maxinData = (MaxinRawEntity) data;
            landload.setHouseAddress(landload.getHouseAddress() + "【栋座号】" + maxinData.getBuildingNo() + " 【单元号】"
                + maxinData.getCellNo());
          }
          
        }
        
        if (landload != null && landload.getHouseCommitterId() > 0) {
          AccountEntity account = (AccountEntity) accountDao.load("loadAccountById", landload.getHouseCommitterId());
          if (account != null) {
            if (account.getContactInfo1() != null) {
              landload.setHouseCommitterMobile(account.getContactInfo1());
            } else {
              landload.setHouseCommitterMobile("");
            }
            
            if (account.getName() != null) {
              landload.setHouseCommitterName(account.getName());
            } else {
              landload.setHouseCommitterName("");
            }
          }
        }
      }
      
      landload.setCode(1);
    } else { // 无权查看
      landload.setCode(0);
    }
    
    log.info("landload code " + landload.getCode());
    return landload;
  }
  
  

	@SuppressWarnings({"rawtypes", "unchecked"})
	public List getTagOptionsByCategory(String categoryName) {
		List tagOptions = null;
		Map para = new HashMap();
		if (!StringUtils.isEmpty(categoryName))
			para.put("categoryName", categoryName);
		tagOptions = houseTagDao.select("findHouseTagList", para);
		return tagOptions;
	}
	
	public List<Map<String, Object>> getHousePics(int houseId, String clientUID)
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
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
	      
	      for (HousePicEntity p : finalPicData) {
	        if (p == null || StringUtils.isBlank(p.getCloudUrl())) {
	          continue;
	        }
	        String urlWithSize = PicSizeUtils.URL2URLWithSize(p.getCloudUrl(), clientUID, "house/detailNew.controller",
	            SizeType.Default);
	        String urlWithOriginSize = PicSizeUtils.URL2URLWithSize(p.getCloudUrl(), clientUID, "house/detailNew.controller",
	            SizeType.Large);
	        
	        Map<String, Object> picMap = new HashMap<String, Object>();
	        if (p.getType() != null && (p.getType().equals(HousePicEntity.Type.RoomType.getValue()) || p.getType().equals(HousePicEntity.Type.HousePic.getValue())))
	        {
		        picMap.put("id", p.getId());
		        picMap.put("type", p.getType());
		        picMap.put("urlWithSize", urlWithSize);
		        picMap.put("urlOrignal", urlWithOriginSize);
		        result.add(picMap);
	        }
	        
	      }
	    }
	    
	    return result;
	}
}
