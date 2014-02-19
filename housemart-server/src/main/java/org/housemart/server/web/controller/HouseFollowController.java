package org.housemart.server.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.server.beans.HouseExtData;
import org.housemart.server.beans.ResidenceBean;
import org.housemart.server.beans.ResidenceExtData;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.beans.house.HouseRentBean;
import org.housemart.server.beans.house.HouseSaleBean;
import org.housemart.server.dao.entities.HouseFollowEntity;
import org.housemart.server.dao.entities.NotificationEntity;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.dao.entities.ResidenceFollowEntity;
import org.housemart.server.data.UserInfoData;
import org.housemart.server.service.HouseFollowService;
import org.housemart.server.service.HouseService;
import org.housemart.server.util.LoggerUtils;
import org.housemart.server.util.PicSizeUtils;
import org.housemart.server.util.ResidenceUtils;
import org.housemart.server.util.PicSizeUtils.SizeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HouseFollowController {
	private static final Log log = LogFactory.getLog(HouseFollowController.class);
	
	@Autowired
	HouseService houseService;
	
	@Autowired
	HouseFollowService houseFollowService;
	
	@Autowired
	GenericDao residenceDao;
	
	@Autowired
	private GenericDao houseFollowDao; 
	
	@Autowired
	private GenericDao residenceFollowDao; 
	
	@Autowired
	private GenericDao notificationDao; 
	
	private static SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping(value = "house/follow.controller")
	public ModelAndView houseFollow(@RequestParam Integer houseId, @RequestParam Integer type, @RequestParam String clientUId) {
		
		HouseFollowEntity entity = new HouseFollowEntity();
		entity.setClientId(clientUId);
		entity.setType(type);
		entity.setHouseId(houseId);
		
		ResultBean bean = new ResultBean();
		try {
			int status = houseService.getHouseStatus(houseId, type);
			if(status == 0){
				bean.setCode(301); //不存在
				bean.setMsg("房源不存在，无法关注！");
			} else {
				if(status != 1){
					bean.setCode(300); //下架 
					bean.setMsg("房源已经下架，无法关注！");
				} else {
					houseFollowDao.add("addHouseFollow", entity);
					UserInfoData.getInstance().putHouseUserClientId(houseId, type, clientUId);
					bean.setCode(ResutlCodeEnum.SUCCESS.getType());
				}
			}

		} catch (Exception e) {
			LoggerUtils.error(e.getMessage(), e);
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("服务器错误，请稍后再试");
		}		

		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "house/followCancel.controller")
	public ModelAndView houseCancel(@RequestParam Integer houseId, @RequestParam Integer type, @RequestParam String clientUId) {

		HouseFollowEntity entity = new HouseFollowEntity();
		entity.setClientId(clientUId);
		entity.setType(type);
		entity.setHouseId(houseId);
		
		ResultBean bean = new ResultBean();
		houseFollowDao.delete("deleteHouseFollow", entity);
		UserInfoData.getInstance().cancelHouseUserClientId(houseId, type, clientUId);
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "house/sale/favorite.controller")
	public ModelAndView favoriteSaleList(@RequestParam String clientUId) {
		
		ResultBean bean = new ResultBean();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		map.put("clientId", clientUId);
		List<HouseFollowEntity> list = houseFollowDao.select("findHouseFollowByCondition", map);
		List<HouseSaleBean> data = null;
		List<HouseSaleBean> newData = new ArrayList<HouseSaleBean>();
		
		if(!CollectionUtils.isEmpty(list)){
			int[] houseIds = new int[list.size()];
			int count = 0;
			Map<Integer, Date> dateMap = new HashMap<Integer, Date>();
			for(HouseFollowEntity item: list){
				houseIds[count ++] = item.getHouseId();
				dateMap.put(item.getHouseId(), item.getAddTime());
			}
			try {
				data = houseService.findHouseSaleBeanList(houseIds);
				if(CollectionUtils.isNotEmpty(data)){
					for(HouseSaleBean item: data){
						if(houseService.getHouseStatus(item.getId(), 1) == 1){
							item.setIsFollow(true);
							item.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUId, "house/sale/favorite.controller", SizeType.Default));
							if(dateMap.get(item.getId()) != null){
								item.setFollowTime(dateMap.get(item.getId()).getTime());
							}
							newData.add(item);
						}
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			bean.setData(newData);
		}
		
		if(bean.getData() == null){
			bean.setData(new ArrayList<HouseSaleBean>());
		}
		
		//消息数据
		Map<Object, Object> extMap = houseFollowService.getHouseFollowUpdates(clientUId, 1);
		List<HouseExtData> extData = new ArrayList<HouseExtData>();
		Iterator ite = extMap.entrySet().iterator();
		while(ite.hasNext()){
			Map.Entry<Object, Object> entry = (Entry<Object, Object>) ite.next();
			Object key = entry.getKey();//map中的key
			Object value = entry.getValue();//上面key对应的value
			HouseExtData specifiedItem = new HouseExtData();
			
			int houseId = Integer.parseInt(key.toString());
			Map<Object, Object> targetMap = (Map<Object, Object>)value;
			int type = Integer.parseInt(targetMap.get("type").toString());
			int action = Integer.parseInt(targetMap.get("action").toString());
			Date date = (Date)targetMap.get("time");
			
			if(type ==1){
				specifiedItem.setType(1);
				specifiedItem.setHouseId(houseId);
				specifiedItem.setAction(action);
				specifiedItem.setTime(dateformat1.format(date));
				extData.add(specifiedItem);
			}		
		}
		
		
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setExtData(extData);
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "house/rent/favorite.controller")
	public ModelAndView favoriteRentList(@RequestParam String clientUId) {
		
		ResultBean bean = new ResultBean();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 2);
		map.put("clientId", clientUId);
		List<HouseFollowEntity> list = houseFollowDao.select("findHouseFollowByCondition", map);
		List<HouseRentBean> data = null;
		List<HouseRentBean> newData = new ArrayList<HouseRentBean>();
		
		if(!CollectionUtils.isEmpty(list)){
			int[] houseIds = new int[list.size()];
			int count = 0;
			Map<Integer, Date> dateMap = new HashMap<Integer, Date>();
			for(HouseFollowEntity item: list){
				houseIds[count ++] = item.getHouseId();
				dateMap.put(item.getHouseId(), item.getAddTime());
			}
			try {
				data = houseService.findHouseRentBeanList(houseIds);
				if(CollectionUtils.isNotEmpty(data)){
					for(HouseRentBean item: data){
						if(houseService.getHouseStatus(item.getId(), 2) == 1){
							item.setIsFollow(true);
							item.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(item.getPicURL(), clientUId, "house/rent/favorite.controller", SizeType.Default));
							if(dateMap.get(item.getId()) != null){
								item.setFollowTime(dateMap.get(item.getId()).getTime());
							}
							newData.add(item);
						}
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			bean.setData(newData);
		}
		
		if(bean.getData() == null){
			bean.setData(new ArrayList<HouseRentBean>());
		}
		
		//消息数据
		Map<Object, Object> extMap = houseFollowService.getHouseFollowUpdates(clientUId, 2);
		List<HouseExtData> extData = new ArrayList<HouseExtData>();
		Iterator ite = extMap.entrySet().iterator();
		while(ite.hasNext()){
			Map.Entry<Object, Object> entry = (Entry<Object, Object>) ite.next();
			Object key = entry.getKey();//map中的key
			Object value = entry.getValue();//上面key对应的value
			HouseExtData specifiedItem = new HouseExtData();
			
			int houseId = Integer.parseInt(key.toString());
			Map<Object, Object> targetMap = (Map<Object, Object>)value;
			int type = Integer.parseInt(targetMap.get("type").toString());
			int action = Integer.parseInt(targetMap.get("action").toString());
			Date date = (Date)targetMap.get("time");
			
			if(type ==2){
				specifiedItem.setType(2);
				specifiedItem.setHouseId(houseId);
				specifiedItem.setAction(action);
				specifiedItem.setTime(dateformat1.format(date));
				extData.add(specifiedItem);
			}		
		}
		bean.setExtData(extData);
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "residence/follow.controller")
	public ModelAndView residenceFollow(@RequestParam int residenceId, @RequestParam String clientUId) {
		
		ResultBean bean = new ResultBean();
		
		ResidenceFollowEntity entity = new ResidenceFollowEntity();
		entity.setClientId(clientUId);
		entity.setResidenceId(residenceId);
		
		residenceFollowDao.add("addResidenceFollow", entity);
		UserInfoData.getInstance().putResidenceClientId(residenceId, clientUId);
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "residence/followCancel.controller")
	public ModelAndView residenceFollowCancel(@RequestParam int residenceId, @RequestParam String clientUId) {
		
		ResultBean bean = new ResultBean();
		
		ResidenceFollowEntity entity = new ResidenceFollowEntity();
		entity.setClientId(clientUId);
		entity.setResidenceId(residenceId);
		
		residenceFollowDao.delete("deleteResidenceFollow", entity);
		UserInfoData.getInstance().cancelResidenceUserClientId(residenceId, clientUId);
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "residence/favorite.controller")
	public ModelAndView residenceFavrite(@RequestParam String clientUId) {
		
		ResultBean bean = new ResultBean();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clientId", clientUId);
		List<ResidenceFollowEntity> list = residenceFollowDao.select("findResidenceFollowByCondition", map);
		List<ResidenceBean> data = new ArrayList<ResidenceBean>();
		if(CollectionUtils.isNotEmpty(list)){
			Set uniqueSet = new TreeSet(new Comparator(){
				public int compare(Object o1, Object o2) {
					ResidenceFollowEntity obj1 = (ResidenceFollowEntity)o1;
					ResidenceFollowEntity obj2 = (ResidenceFollowEntity)o2;
					return obj1.getResidenceId() - obj2.getResidenceId();
				}
			});
			uniqueSet.addAll(list);
			List<ResidenceFollowEntity> newList = new ArrayList<ResidenceFollowEntity>(uniqueSet);
			for(ResidenceFollowEntity item: newList){
				ResidenceEntity entity = (ResidenceEntity) residenceDao.load("loadResidence",
						item.getResidenceId());
				if(entity == null || entity.getZombie() == 1){ //过滤暗小区
					continue;
				}
				ResidenceBean rBean = ResidenceUtils.residenceEntity2Bean(entity);
				rBean.setIsFollow(true);
				rBean.setPicURLWithSize(PicSizeUtils.URL2URLWithSize(rBean.getPicURL(), clientUId, "residence/favorite.controller", SizeType.Default));
				if(item.getAddTime() != null){
					rBean.setFollowTime(item.getAddTime().getTime());
				}
				data.add(rBean);
			}
		}
						
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setData(data);
		Map<Object, Object> extMap = houseFollowService.getResidenceFollowUpdates(clientUId);
		List<ResidenceExtData> extData = new ArrayList<ResidenceExtData>();
		Iterator ite = extMap.entrySet().iterator();
		while(ite.hasNext()){
			Map.Entry<Object, Object> entry = (Entry<Object, Object>) ite.next();
			Object key = entry.getKey();//map中的key
			Object value = entry.getValue();//上面key对应的value
			ResidenceExtData specifiedItem = new ResidenceExtData();
			specifiedItem.setResidenceId(Integer.parseInt(key.toString()));
			Map<Object, Object> targetMap = (Map<Object, Object>)value;
			specifiedItem.setCountSale(Integer.parseInt(targetMap.get("countSale").toString()));
			specifiedItem.setCountSale(Integer.parseInt(targetMap.get("countRent").toString()));
			extData.add(specifiedItem);
		}
		
		bean.setExtData(extData);
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "house/follow/update.controller")
	public ModelAndView houseFollowUpdate(@RequestParam String appCode, @RequestParam String clientUId) {
		
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		
		Map<Object, Object> map = houseFollowService.getHouseFollowUpdates(clientUId, 0);
		
		bean.setData(map);
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "residence/follow/update.controller")
	public ModelAndView residenceFollowUpdate(@RequestParam String appCode, @RequestParam String clientUId,
			@RequestParam(required = false) Integer totalOnly) {
		
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		totalOnly = (totalOnly == null ? 0 : 1); 
		
		if (totalOnly == 1)
		{
			map.put("clientUId", clientUId);
			map.put("status", 0);
			
			List<NotificationEntity> notifications = (List<NotificationEntity>)notificationDao.select("findNotificationList", map);
			
			map = new HashMap<Object, Object>();
			map.put("count", notifications.size());
			bean.setData(map);
		}
		else
		{
			map = houseFollowService.getResidenceFollowUpdates(clientUId);
			bean.setData(map);
		}
		
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "client/follow/notify.controller")
	public ModelAndView clientFollowNotify()
	{
		ResultBean bean = new ResultBean();
		try
		{
			houseFollowService.addNotifications();
		}
		catch(Exception ex)
		{
			bean.setMsg(ex.getMessage());
		}
		
		return new ModelAndView("jsonView", "json", bean);
	}
	
}
