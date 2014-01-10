package org.housemart.server.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.push.JavaPNSProvider;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.UserUnreadCountsBean;
import org.housemart.server.dao.entities.ClientNotesEntity;
import org.housemart.server.dao.entities.ClientRegisterEntity;
import org.housemart.server.dao.entities.HouseChatMessageEntity;
import org.housemart.framework.push.BaiduYunAPI;
import org.json.JSONObject;

/**
 * 
* @ClassName: SessionService
* @Description: TODO
* @date 2013-4-14 下午9:53:49
*
 */
public class SessionService {

	private Log log = LogFactory.getLog(this.getClass());
	
	@SuppressWarnings("rawtypes")
	private GenericDao houseChatMessageDao = SpringContextHolder.getBean("houseChatMessageDao");
	
	@SuppressWarnings("rawtypes")
	private GenericDao clientRegisterDao = SpringContextHolder.getBean("clientRegisterDao");
	
	@SuppressWarnings("rawtypes")
	private GenericDao clientNotesDao = SpringContextHolder.getBean("clientNotesDao");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void notifyClientUnreadChats()
	{
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("countForClient", true);
		map.put("accountStatus", 1);
		map.put("notify", 1);
		
		List<HouseChatMessageEntity> list = (List<HouseChatMessageEntity>)houseChatMessageDao.select("countUserUnreadChatMessage", map);
		
		if (!list.isEmpty())
		{
			Iterator itr = list.iterator();
			String lastID = "";
			int count = 0;
			while (itr.hasNext())
			{
				HouseChatMessageEntity entity = (HouseChatMessageEntity)itr.next();
				
				if (!entity.getClientUID().equals(lastID))
				{
					lastID = entity.getClientUID();
					count = 1;
				}
				else
				{
					count++;
				}
				
				map = new HashMap<Object, Object>();
				map.put("clientID", entity.getClientUID());
				List<ClientRegisterEntity> clients = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", map);
				
				ClientRegisterEntity clientRegister = null;
				
				if (clients.size() == 1)
				{
					clientRegister = clients.get(0);
					String message = "";
					
					if (entity.getFormat() == 0)
					{
						message = entity.getContent();
						message = entity.getBrokerName() + ": " + message;
					}
					else
					{
						message = entity.getBrokerName() + ": 推荐" + (entity.getType() == 1 ? "在售" : "在租") + "房源";
					}
					
					try
					{						
						if (clientRegister.getDevice() == null)
						{
						}
						else if (clientRegister.getDevice().toLowerCase().contains("iphone"))
						{
							JavaPNSProvider.pushMessageToAPNS(clientRegister.getClientToken(), message, count, true);
						}
						else
						{
							if (clientRegister.getClientToken().contains("|"))
							{
								String[] token = clientRegister.getClientToken().split("\\|");
								
								JSONObject msgObj = new JSONObject();
								msgObj.put("title", "好识买问询消息通知");
								msgObj.put("description", message);
								message = msgObj.toString();
								BaiduYunAPI.pushMessage2Android(token[0], token[1], 1, message);
							}
						}
							
						map = new HashMap<Object, Object>();
						map.put("id", entity.getId());
						map.put("notifyTime", new Date());
						map.put("updateTime", new Date());
						houseChatMessageDao.update("updateHouseChatMessageStatus", map);
						
					}
					catch(Exception e)
					{
						log.error(e.getMessage(), e);
					}
				}
				
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void notifyBrokerUnreadChats()
	{
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("countForBroker", true);
		map.put("accountStatus", 1);
		map.put("notify", 1);
		
		List<HouseChatMessageEntity> list = (List<HouseChatMessageEntity>)houseChatMessageDao.select("countUserUnreadChatMessage", map);
		
		if (!list.isEmpty())
		{
			Iterator itr = list.iterator();
			String lastID = "";
			int count = 0;
			while (itr.hasNext())
			{
				HouseChatMessageEntity entity = (HouseChatMessageEntity)itr.next();
				
				if (!entity.getClientUID().equals(lastID))
				{
					lastID = entity.getClientUID();
					count = 1;
				}
				else
				{
					count++;
				}
				
				map = new HashMap<Object, Object>();
				map.put("brokerID", entity.getRealBrokerID());
				map.put("brokerLogin", 1);
				
				List<ClientRegisterEntity> clients = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", map);
				
				ClientRegisterEntity clientRegister = null;
				
				if (clients.size() > 0)
				{
					clientRegister = clients.get(0);
					String message = "";
					
					message = entity.getContent();
					message = this.getClientName(entity.getClientUID(), entity.getBrokerID(), false) + ": " + message;
					
					try
					{						
						
						if (clientRegister.getDevice() == null)
						{
						}
						else if (clientRegister.getDevice().toLowerCase().contains("iphone"))
						{
							JavaPNSProvider.pushMessageToAPNS(clientRegister.getClientToken(), message, count, false);
						}
						else
						{
							if (clientRegister.getClientToken().contains("|"))
							{
								String[] token = clientRegister.getClientToken().split("\\|");
								
								JSONObject msgObj = new JSONObject();
								msgObj.put("title", "好识买问询消息通知");
								msgObj.put("description", message);
								message = msgObj.toString();
								BaiduYunAPI.pushMessage2Android(token[0], token[1], 1, message, true);
							}
						}
						
						map = new HashMap<Object, Object>();
						map.put("id", entity.getId());
						map.put("notifyTime", new Date());
						map.put("updateTime", new Date());
						houseChatMessageDao.update("updateHouseChatMessageStatus", map);
						
					}
					catch(Exception e)
					{
						log.error(e.getMessage(), e);
					}
				}
				
			}
		}
	}
	
	public String getClientName(String clientUId, Integer brokerId, boolean fullClientUId)
	{
		String clientName = clientUId;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("clientUId", clientUId);
		map.put("type", 0);
		map.put("brokerId", brokerId);
		List<ClientNotesEntity> clientNoteList = (List<ClientNotesEntity>)clientNotesDao.select("findClientNotes", map);
		
		if (clientNoteList.size() > 0)
		{
			clientName = clientNoteList.get(0).getNote();
		}
		else if (!fullClientUId)
		{
			clientName = (clientName.length() > 4) ? (clientName.substring(0, 3) + "...") : clientName;
		}
		
		return clientName;
	}
	
	public String getClientName(String clientUId, Integer brokerId)
	{
		return this.getClientName(clientUId, brokerId, true);
	}
	
	public String getBrokerClientUId(Integer brokerId)
	{
		String clientUId = "";
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("brokerID", brokerId);
		List<ClientRegisterEntity> clients = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", map);
		
		ClientRegisterEntity clientRegister = null;
		
		if (clients.size() > 0)
		{
			clientRegister = clients.get(0);
			clientUId = clientRegister.getClientID();
		}
		
		return clientUId;
		
	}
}
