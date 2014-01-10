/**
 * Created on 2013-1-28
 * 
 */
package org.housemart.server.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.server.beans.SearchResultBean;
import org.housemart.server.beans.house.HouseSaleBean;
import org.housemart.server.dao.entities.GooglePlaceBaseEntity;
import org.housemart.server.dao.entities.HouseEntity;
import org.housemart.server.service.HouseService;
import org.housemart.server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GoogleMapController {
  private Log log = LogFactory.getLog(this.getClass());
  @Autowired
  HouseService houseServiceNew;
  @Autowired
  SearchService searchService;
  
  @RequestMapping(value = "map/house/sale/detailedSearchNearby.controller")
  public String saleDetailedListNearBy(Model model, Integer cityId, String lat, String lng, Integer range, Integer type,
      Integer roomType, String priceRange) {
    SearchResultBean<HouseSaleBean> saleList = new SearchResultBean<HouseSaleBean>();
    GooglePlaceBaseEntity[] places = null;
    Map<Integer,GooglePlaceBaseEntity> resMap = null;
    try {
      places = searchService.searchGooglePlace(Double.valueOf(lat), Double.valueOf(lng), range, true, false);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    if (!ArrayUtils.isEmpty(places)) {
      resMap = new HashMap<Integer,GooglePlaceBaseEntity>();
      for (GooglePlaceBaseEntity gPlace : places) {
        resMap.put(gPlace.getResidenceId(), gPlace);
      }
    }
    try {
      saleList = houseServiceNew.detailFindSaleHouseWithPositionInfo(HouseEntity.SaleStatusEnum.Saling.saleStatus, cityId, null,
          null, resMap, Double.valueOf(lat), Double.valueOf(lng), type, roomType, 0, Integer.MAX_VALUE, 0, 0, Integer.MAX_VALUE);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    
    if (!CollectionUtils.isEmpty(saleList.getData())) {
      try {
        model.addAttribute("houseList", new ObjectMapper().writeValueAsString(saleList.getData()));
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    } else {
      model.addAttribute("houseList", "[]");
    }
    model.addAttribute("totalCount", saleList.getTotalCount());
    model.addAttribute("lat", lat);
    model.addAttribute("lng", lng);
    model.addAttribute("range", range);
    return "map/saleListNearBy";
  }
}
