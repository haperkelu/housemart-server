package org.housemart.server.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.push.JavaPNSProvider;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.AuditStatusAndContentBean;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.beans.house.HouseDetailBean;
import org.housemart.server.dao.entities.ClientRegisterEntity;
import org.housemart.server.dao.entities.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @author user
 *
 */
@Service
public class HouseFollowService {

	@SuppressWarnings("rawtypes")
	private GenericDao houseFollowDao = SpringContextHolder.getBean("houseFollowDao");
	
	@SuppressWarnings("rawtypes")
	private GenericDao houseAuditHistoryDao = SpringContextHolder.getBean("houseAuditHistoryDao");

	@SuppressWarnings("rawtypes")
	private GenericDao notificationDao = SpringContextHolder.getBean("notificationDao");
	
	@SuppressWarnings("rawtypes")
	private GenericDao clientRegisterDao = SpringContextHolder.getBean("clientRegisterDao");
	
	HouseService houseService = SpringContextHolder.getBean("houseService");
	
	/**
	 * 
	 * @param houseId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String>  findClientUIDsByHouseId(int houseId, int type){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("houseId", houseId);
		map.put("type", type);
		return houseFollowDao.select("findClientListByHouseID", map);
	}
	
	public Map<Object, Object> getHouseFollowUpdates(String clientUId, int type)
	{
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("clientUId", clientUId);
		map.put("status", 0);
		if (type > 0)
		{
			map.put("types", type);
		}
		
		List<NotificationEntity> notifications = (List<NotificationEntity>)notificationDao.select("findNotificationList", map);
		
		Map<Object, Object> houseMap = new HashMap<Object, Object>();
		
		if(CollectionUtils.isNotEmpty(notifications))
		{
			for (NotificationEntity notification : notifications)
			{
				map = new HashMap<Object, Object>();
				map.put("houseId", notification.getHouseId());
				map.put("type", notification.getType());
				map.put("action", notification.getAction());
				map.put("time", notification.getActionTime());
				
				houseMap.put(notification.getHouseId(), map);
				
				map = new HashMap<Object, Object>();
				map.put("status", 1);
				map.put("sendTime", new Date());
				map.put("id", notification.getId());
				notificationDao.update("updateNotification", map);
			}
		}

		
		return houseMap;
	}
	
	public Map<Object, Object> getResidenceFollowUpdates(String clientUId)
	{
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("clientUId", clientUId);
		map.put("countStatus", 0);
		map.put("types", "1,2,3");
		
		List<NotificationEntity> notifications = (List<NotificationEntity>)notificationDao.select("findNotificationList", map);
		
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>(); 
		
		Map<Object, Object> residenceMap = new HashMap<Object, Object>();
		
		if(CollectionUtils.isNotEmpty(notifications))
		{
			for (NotificationEntity notification : notifications)
			{
				if (notification.getAction() == 4)
				{
					map = new HashMap<Object, Object>();
					
					if (residenceMap.containsKey(notification.getResidenceId()))
					{
						map = (Map<Object, Object>)residenceMap.get(notification.getResidenceId());
						map.put("countSale", (Integer)map.get("countSale") + (notification.getType() == 1 ? 1 : 0));
						map.put("countRent", (Integer)map.get("countRent") + (notification.getType() == 2 ? 1 : 0));
					}
					else
					{
						map.put("countSale", notification.getType() == 1 ? 1 : 0);
						map.put("countRent", notification.getType() == 2 ? 1 : 0);
					}
					residenceMap.put(notification.getResidenceId(), map);
				}
				
				map = new HashMap<Object, Object>();
				map.put("countStatus", 1);
				map.put("sendTime", new Date());
				map.put("id", notification.getId());
				notificationDao.update("updateNotification", map);
			}
		}
		
		return residenceMap;
	}
	
	@SuppressWarnings("unchecked")
	public void addNotifications()
	{
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("types", "1,2,3");
		List<Date> list = (List<Date>)notificationDao.select("getLastActionTime", map);
		
		Date default_last_time = new Date();
		default_last_time.setTime(default_last_time.getTime() - 24*60*60*1000);
		Date last_time = list.isEmpty() ? default_last_time : list.get(0);
		
		map = new HashMap<Object, Object>();
		map.put("lastAuditTime", last_time);
		List<Map<Object, Object>> audit_list = (List<Map<Object, Object>>)houseAuditHistoryDao.select("findFollowHouseAuditHistoryList", map);
		
		ObjectMapper mapper = new ObjectMapper();
		
		for(Map<Object, Object> audit : audit_list)
		{
			String houseClientUId = "";
			String residenceClientUId = "";
			
			if (audit.get("houseClientUId") != null)
			{
				houseClientUId = (String)audit.get("houseClientUId");
			}
			
			if (audit.get("residenceClientUId") != null)
			{
				residenceClientUId = (String)audit.get("residenceClientUId");
			}
			
			NotificationEntity entity = new NotificationEntity();
			entity.setResidenceId((Integer)audit.get("residenceId"));
			entity.setHouseId((Integer)audit.get("houseId"));
			entity.setType((Integer)audit.get("followType"));
			entity.setAction(0);
			
			if (entity.getType() == null)
			{
				entity.setType(3);
			}
			
			if ((Integer)audit.get("auditType") == 5)
			{
				//内网房源
				try
				{
					AuditStatusAndContentBean preContent = (AuditStatusAndContentBean)mapper.readValue((String)audit.get("preContent"), AuditStatusAndContentBean.class);
					AuditStatusAndContentBean postContent = (AuditStatusAndContentBean)mapper.readValue((String)audit.get("postContent"), AuditStatusAndContentBean.class);
					
					if (preContent != null && postContent != null)
					{
						//不售->在售: (preContent LIKE '%"saleStatus":null%' OR preContent LIKE '%"saleStatus":0%') AND postContent LIKE '%"saleStatus":1%'
						if ((preContent.getSaleStatus() == 0 && postContent.getSaleStatus() == 1) || 
								(preContent.getRentStatus() == 0 && postContent.getRentStatus() == 1))
						{
							//新增房源
							entity.setAction(4);
						}
						else if (entity.getType() == 1)
						{
							if (preContent.getSaleStatus() == 1 && (
									postContent.getSaleStatus() != 1
									))
							{
								//在售->不售
								entity.setAction(1);
							}
							else if (preContent.getSalePrice() != postContent.getSalePrice())
							{
								//售房价格变化
								entity.setAction(3);
							}

						}
						else if (entity.getType() == 2)
						{
							if (preContent.getRentStatus() == 1 && (
									postContent.getRentStatus() != 1
									))
							{
								//在租->不租
								entity.setAction(2);
							}
							else if (preContent.getRentPrice() != postContent.getRentPrice())
							{
								//租金价格变化
								entity.setAction(3);
							}
						}
					}
				}
				catch(Exception ex)
				{
					entity.setAction(-4);
				}
			}
			else if ((Integer)audit.get("auditType") == 1)
			{
				//外网新增
				entity.setAction(4);
			}
			else if ((Integer)audit.get("auditType") == 6)
			{
				//外网下架、删
				if (entity.getType() == 1)
				{
					//售房
					entity.setAction(1);
				}
				if (entity.getType() == 2)
				{
					//租房
					entity.setAction(2);
				}
				
			}
			
			if (entity.getAction() > 0)
			{
				entity.setActionTime((Date)audit.get("auditTime"));
			
				if (!houseClientUId.isEmpty())
				{
					entity.setClientUId(houseClientUId);
					notificationDao.add("addNotification", entity);
					entity.setHouseFollow((Integer)audit.get("houseFollow"));
				}
				
				if (!residenceClientUId.isEmpty())
				{
					entity.setClientUId(residenceClientUId);
					entity.setHouseFollow(null);
					entity.setResidenceFollow((Integer)audit.get("residenceFollow"));
					notificationDao.add("addNotification", entity);
				}
				
			}
		}
	}
	
	public void sendNotifications()
	{
		//关注房源
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("countStatus", 0);
		map.put("unnotified", 0);
		
		List<NotificationEntity> notifications = (List<NotificationEntity>)notificationDao.select("findNotificationList", map);
		for (NotificationEntity notification : notifications)
		{
			String msg = "";
			try
			{
				HouseDetailBean house = houseService.loadDetail(notification.getHouseId());
			
				if (notification.getHouseFollow() != null)
				{
					//房源关注
					//售房下架
					if (notification.getAction() == 1)
					{
						msg = "您关注的" + house.getResidenceName() + "小区房源" + house.getPrice() +
								house.getRoomType() + house.getArea() + "已下架.";
					}
					//租房下架
					if (notification.getAction() == 2)
					{
						msg = "您关注的" + house.getResidenceName() + "小区房源" + house.getRentPrice() +
								house.getRoomType() + house.getArea() + "已下架.";
					}
					
					//内容变更
					if (notification.getAction() == 3)
					{
						msg = "您关注的" + house.getResidenceName() + "小区房源" + 
								house.getRoomType() + house.getArea();
						
						if (!house.getPrice().isEmpty())
						{
							msg += " 价格变更为 " + house.getPrice();
						}
						if (!house.getRentPrice().isEmpty())
						{
							msg += " 租金变更为" + house.getRentPrice();
						}
				
					}
					
				}
				else if (notification.getResidenceFollow() != null)
				{
					//小区关注
					if (notification.getAction() == 4)
					{
						//新增房源
						msg = "您关注的" + house.getResidenceName() + "小区新增房源" +
								house.getRoomType() + house.getArea();
						if (!house.getPrice().isEmpty())
						{
							msg += " 售价 " + house.getPrice();
						}
						if (!house.getRentPrice().isEmpty())
						{
							msg += " 租金" + house.getRentPrice();
						}
					}
				}
				
				map = new HashMap<Object, Object>();
				map.put("clientID", notification.getClientUId());
				List<ClientRegisterEntity> clients = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", map);
				
				ClientRegisterEntity clientRegister = null;
				
				if (clients.size() == 1)
				{
					clientRegister = clients.get(0);
					JavaPNSProvider.pushMessageToAPNS(clientRegister.getClientToken(), msg, 1, true);
				}
				
			}
			catch(Exception ex)
			{
			
			}
			
			map = new HashMap<Object, Object>();
			map.put("notifyTime", new Date());
			map.put("id", notification.getId());
			notificationDao.update("updateNotification", map);
		}
	}
}
