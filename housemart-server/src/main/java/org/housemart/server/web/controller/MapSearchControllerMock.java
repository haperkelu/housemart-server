package org.housemart.server.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.server.beans.Position;
import org.housemart.server.beans.ResidenceBean;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapSearchControllerMock {
  public static List<ResidenceBean> houseList = new ArrayList<ResidenceBean>();
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao areaPositionDao;
  @Autowired
  SearchService searchService;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao regionDao;
  
  static {
    // 财富海景
    ResidenceBean itemCaiFuHaiJing01 = new ResidenceBean();
    itemCaiFuHaiJing01.setFansCount(100);
    itemCaiFuHaiJing01.setIsFollow(false);
    itemCaiFuHaiJing01.setId(1);
    itemCaiFuHaiJing01.setOnSaleCount(2);
    itemCaiFuHaiJing01.setOnRentCount(2);
    itemCaiFuHaiJing01.setPicURL(new String[] {"http://42.121.87.85:8080/upload/caifu1360070216993.JPG"});
    itemCaiFuHaiJing01.setPosition(new Position(31.225681, 121.50890400000003));
    itemCaiFuHaiJing01.setPrice("500万");
    itemCaiFuHaiJing01.setPriceRange("4万~12万");
    itemCaiFuHaiJing01.setResidenceId(1);
    itemCaiFuHaiJing01.setResidenceName("财富海景");
    itemCaiFuHaiJing01.setTrend(0.008);
    
    ResidenceBean itemCaiFuHaiJing02 = new ResidenceBean();
    itemCaiFuHaiJing02.setFansCount(100);
    itemCaiFuHaiJing02.setIsFollow(true);
    itemCaiFuHaiJing02.setId(2);
    itemCaiFuHaiJing02.setOnSaleCount(2);
    itemCaiFuHaiJing02.setOnRentCount(2);
    itemCaiFuHaiJing02.setPicURL(new String[] {"http://42.121.87.85:8080/upload/DSC00012_conew11360070230636.jpg"});
    itemCaiFuHaiJing02.setPosition(new Position(31.225681, 121.50890400000003));
    itemCaiFuHaiJing02.setPrice("600万");
    itemCaiFuHaiJing02.setPriceRange("4万~12万");
    itemCaiFuHaiJing02.setResidenceId(1);
    itemCaiFuHaiJing02.setResidenceName("财富海景");
    itemCaiFuHaiJing02.setTrend(0.008);
    
    // 仁恒滨江
    ResidenceBean itemRenHengBinJiang01 = new ResidenceBean();
    itemRenHengBinJiang01.setFansCount(100);
    itemRenHengBinJiang01.setIsFollow(false);
    itemRenHengBinJiang01.setId(11);
    itemRenHengBinJiang01.setOnSaleCount(2);
    itemRenHengBinJiang01.setOnRentCount(2);
    itemRenHengBinJiang01.setPicURL(new String[] {"http://42.121.87.85:8080/upload/conew_wp_0014891360070386450.jpg"});
    itemRenHengBinJiang01.setPosition(new Position(31.228725, 121.50664400000005));
    itemRenHengBinJiang01.setPrice("700万");
    itemRenHengBinJiang01.setPriceRange("4万~10万");
    itemRenHengBinJiang01.setResidenceId(2);
    itemRenHengBinJiang01.setResidenceName("仁恒滨江");
    itemRenHengBinJiang01.setTrend(0.008);
    
    // ResidenceBean itemRenHengBinJiang02 = new ResidenceBean();
    // itemRenHengBinJiang02.setFansCount(100);
    // itemRenHengBinJiang02.setIsFollow(false);
    // itemRenHengBinJiang02.setId(12);
    // itemRenHengBinJiang02.setOnSaleCount(2);
    // itemRenHengBinJiang02.setOnRentCount(2);
    // itemRenHengBinJiang02.setPicURL("http://42.121.87.85:8080/upload/conew_wp_0014831360070395489.jpg");
    // itemRenHengBinJiang02.setPosition(new Position(31.228725,
    // 121.50664400000005));
    // itemRenHengBinJiang02.setPrice("550万");
    // itemRenHengBinJiang02.setPriceRange("4万~10万");
    // itemRenHengBinJiang02.setResidenceId(2);
    // itemRenHengBinJiang02.setResidenceName("仁恒滨江");
    // itemRenHengBinJiang02.setTrend(0.008);
    
    // 世茂滨江
    ResidenceBean itemShiMaoBinJiang01 = new ResidenceBean();
    itemShiMaoBinJiang01.setFansCount(100);
    itemShiMaoBinJiang01.setIsFollow(false);
    itemShiMaoBinJiang01.setId(21);
    itemShiMaoBinJiang01.setOnSaleCount(2);
    itemShiMaoBinJiang01.setOnRentCount(2);
    itemShiMaoBinJiang01.setPicURL(new String[] {"http://42.121.87.85:8080/upload/waiguan-min1360069884512.jpg"});
    itemShiMaoBinJiang01.setPosition(new Position(31.222961, 121.51357499999995));
    itemShiMaoBinJiang01.setPrice("2250万");
    itemShiMaoBinJiang01.setPriceRange("7万~13万");
    itemShiMaoBinJiang01.setResidenceId(3);
    itemShiMaoBinJiang01.setResidenceName("世茂滨江");
    itemShiMaoBinJiang01.setTrend(0.008);
    
    // ResidenceBean itemShiMaoBinJiang02 = new ResidenceBean();
    // itemShiMaoBinJiang02.setFansCount(100);
    // itemShiMaoBinJiang02.setIsFollow(false);
    // itemShiMaoBinJiang02.setId(22);
    // itemShiMaoBinJiang02.setOnSaleCount(2);
    // itemShiMaoBinJiang02.setOnRentCount(2);
    // itemShiMaoBinJiang02.setPicURL("http://42.121.87.85:8080/upload/SMBJ-min1360070029245.jpg");
    // itemShiMaoBinJiang02.setPosition(new Position(31.222961,
    // 121.51357499999995));
    // itemShiMaoBinJiang02.setPrice("1250万");
    // itemShiMaoBinJiang02.setPriceRange("7万~13万");
    // itemShiMaoBinJiang02.setResidenceId(3);
    // itemShiMaoBinJiang02.setResidenceName("世茂滨江");
    // itemShiMaoBinJiang02.setTrend(0.008);
    
    // Sacramento
    ResidenceBean itemSA01 = new ResidenceBean();
    itemSA01.setFansCount(100);
    itemSA01.setIsFollow(false);
    itemSA01.setId(31);
    itemSA01.setOnSaleCount(3);
    itemSA01.setOnRentCount(3);
    itemSA01
        .setPicURL(new String[] {"http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/2/2/d/b/picture-uh=daba7d161a4b41baa28a9a9572ba99d0-ps=22dbe6df3bddc765c90bb735bda398-533-Laidley-St-San-Francisco-CA-94131.jpg"});
    itemSA01.setPosition(new Position(37.735542, -122.431244));
    itemSA01.setPrice("$300,000");
    itemSA01.setPriceRange("$4,000~20,0000");
    itemSA01.setResidenceId(4);
    itemSA01.setResidenceName("Sacramento");
    itemSA01.setTrend(0.008);
    
    // ResidenceBean itemSA02 = new ResidenceBean();
    // itemSA02.setFansCount(100);
    // itemSA02.setIsFollow(false);
    // itemSA02.setId(32);
    // itemSA02.setOnSaleCount(3);
    // itemSA02.setOnRentCount(3);
    // itemSA02.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/b/8/e/0/picture-uh=8fa39250b508e7768d0b96bcc89dcdb-ps=b8e06d5196f7ff583297f5feb853732-661-Peralta-Ave-4-San-Francisco-CA-94110.jpg");
    // itemSA02.setPosition(new Position(37.742046, -122.408623));
    // itemSA02.setPrice("$1,000,000");
    // itemSA02.setPriceRange("$4,000~20,0000");
    // itemSA02.setResidenceId(4);
    // itemSA02.setResidenceName("Sacramento");
    // itemSA02.setTrend(0.008);
    
    // ResidenceBean itemSA03 = new ResidenceBean();
    // itemSA03.setFansCount(100);
    // itemSA03.setIsFollow(false);
    // itemSA03.setId(33);
    // itemSA03.setOnSaleCount(3);
    // itemSA03.setOnRentCount(3);
    // itemSA03.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.50/5/c/8/c/picture-uh=e8546639f8bdf5c93847f677123e513-ps=5c8cb1e2b0da93be508620f2a0f0b46-729-Congo-St-San-Francisco-CA-94131.jpg");
    // itemSA03.setPosition(new Position(37.735641, -122.441673));
    // itemSA03.setPrice("$1,000,000");
    // itemSA03.setPriceRange("$4,000~20,0000");
    // itemSA03.setResidenceId(4);
    // itemSA03.setResidenceName("Sacramento");
    // itemSA03.setTrend(0.008);
    
    houseList.add(itemCaiFuHaiJing01);
    houseList.add(itemRenHengBinJiang01);
    houseList.add(itemShiMaoBinJiang01);
    houseList.add(itemSA01);
  }
  
  @RequestMapping(value = "house/residenceSale/mapSearch.controller")
  public ModelAndView residenceSaleListByMap(@RequestParam double lat, @RequestParam double lng, @RequestParam int range,
      @RequestParam int pageIndex, @RequestParam int pageSize) {
    
    ResultBean bean = new ResultBean();
    bean.setCount(20);
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(houseList);
    return new ModelAndView("jsonView", "json", bean);
  }
  
  @RequestMapping(value = "house/residenceSale/search.controller")
  public ModelAndView residenceSaleList(@RequestParam int cityId, @RequestParam int regionId, @RequestParam int plateId,
      @RequestParam int pageIndex, @RequestParam int pageSize) {
    
    ResultBean bean = new ResultBean();
    bean.setCount(20);
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(houseList);
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
  @RequestMapping(value = "house/residenceRent/mapSearch.controller")
  public ModelAndView residenceRentListByMap(@RequestParam double lat, @RequestParam double lng, @RequestParam int range,
      @RequestParam int pageIndex, @RequestParam int pageSize) {
    
    ResultBean bean = new ResultBean();
    bean.setCount(20);
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(houseList);
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
  @RequestMapping(value = "house/residenceRent/search.controller")
  public ModelAndView residenceRentList(@RequestParam int cityId, @RequestParam int regionId, @RequestParam int plateId,
      @RequestParam int pageIndex, @RequestParam int pageSize) {
    
    ResultBean bean = new ResultBean();
    bean.setCount(20);
    bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    bean.setData(houseList);
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
}
