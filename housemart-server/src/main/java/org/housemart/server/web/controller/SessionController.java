package org.housemart.server.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.BrokerSummaryBean;
import org.housemart.server.beans.ChatMessageBean;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.beans.UserChatSummaryBean;
import org.housemart.server.beans.house.HouseDetailBean;
import org.housemart.server.dao.entities.AccountExtEntity;
import org.housemart.server.dao.entities.HouseChatMessageEntity;
import org.housemart.server.dao.entities.HouseInteractionExtEntity;
import org.housemart.server.dao.entities.HouseInteractionTransferExtEntity;
import org.housemart.server.service.AuthenticationService;
import org.housemart.server.service.HouseService;
import org.housemart.server.service.SessionService;
import org.housemart.server.util.PicSizeUtils;
import org.housemart.server.util.PicSizeUtils.SizeType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SessionController {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	HouseService houseService;
	
	@Autowired
	SessionService sessionService;
	
	@SuppressWarnings("rawtypes")
	private GenericDao houseChatMessageDao = SpringContextHolder.getBean("houseChatMessageDao");
	
	@SuppressWarnings("rawtypes")
	private GenericDao accountDao = SpringContextHolder.getBean("accountDao");
	
	@RequestMapping(value = "house/chat/list.controller")
	public ModelAndView houseChatList(
		@RequestParam String clientUId, 
		@RequestParam(required = false) String houseId, 
		@RequestParam(required = false) Integer brokerId,
		@RequestParam int messageId, 
		@RequestParam(required = false) Integer type,
		@RequestParam(required = false) Integer page,
		@RequestParam(required = false) String secret
		) {
		
		if (page == null)
		{
			page = 0;
		}
		
		Integer transferBrokerId = null;
		
		Integer houseId4Client = 0;
		List<String> houseId4Broker = new ArrayList<String>();
		List<Map<Object, Object>> newHouses4Broker = new ArrayList<Map<Object, Object>>();
		
		int request_user_id = -1;
		
		int fromTo = 2;
		
		if (secret != null)
		{
			//经纪人
			fromTo = 1;
			
			transferBrokerId = brokerId;
			request_user_id = authenticationService.decodeBrokerId(secret);
						
			if (request_user_id > 0)
			{
				brokerId = request_user_id;
			}
			
			if (houseId != null && !houseId.isEmpty())
			{
				houseId4Broker = new ArrayList<String>(Arrays.asList(houseId.split(",")));
			}
		}
		else
		{
			//客户端
			houseId4Client = houseId != null ? Integer.parseInt(houseId) : 0;
			if (brokerId == null)
			{
				HouseInteractionExtEntity interaction = houseService.getInteraction(houseId4Client);
				brokerId = interaction.getAgentID();
			}
		}
		
		if (type == null)
		{
			type = 1;
		}
		
		ResultBean bean = new ResultBean();
		
		int houseStatus = 0;
		
		try
		{
			if (houseId4Client > 0)
			{
				houseStatus = houseService.getHouseStatus(houseId4Client, type);
			}
			else
			{
				houseStatus = 1;
			}
		}
		catch(Exception ex)
		{
			
		}
		
		if (houseStatus == 1)
		{
			bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		}
		else
		{
			bean.setCode(houseStatus == 0 ? 201 : 200);
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("clientUID", clientUId);
		if (fromTo == 2)
		{
			//客户端
			map.put("houseID", houseId);
		}
		map.put("limit", true);
		map.put("limitStart", page * 10000);
		map.put("limitSize", 10000);
		
		map.put(fromTo == 2 ? "brokerID" : "realBrokerID", brokerId);
		map.put("transferBrokerID", transferBrokerId);
		
		map.put("type", type);
		map.put(fromTo == 2 ? "clientDelete" : "brokerDelete", 0);
		
		if (messageId > 0)
		{
			map.put("lastID", messageId);
		}
		
		List<HouseChatMessageEntity> chatList = (List<HouseChatMessageEntity>)houseChatMessageDao.select("findHouseChatMessageList", map);
		
		List<ChatMessageBean> list = new ArrayList<ChatMessageBean>();
		
		if (!chatList.isEmpty())
		{			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for(int i = 0; i < chatList.size(); i++)
			{
				HouseChatMessageEntity msg = chatList.get(i);
				ChatMessageBean msgBean = new ChatMessageBean();
				msgBean.setMessageId(msg.getId());
				msgBean.setBrokerId(msg.getBrokerID());
				msgBean.setName(msg.getBrokerName());
				msgBean.setBrokerPicURL(msg.getBrokerPicURL());
				msgBean.setTime(df.format(msg.getAddTime()));
				msgBean.setUseType(msg.getFromTo() == 1 ? 1 : 2);
				msgBean.setContent(msg.getContent());
				msgBean.setFormat(msg.getFormat());
				msgBean.setType(msg.getType());
				msgBean.setHouseId(msg.getHouseID());
				
				list.add(msgBean);
				
				messageId = msgBean.getMessageId();
				
				if (!houseId4Broker.contains(msg.getHouseID().toString()))
				{
					houseId4Broker.add(msg.getHouseID().toString());
					try
					{
						HouseDetailBean house = houseService.loadDetail(msg.getHouseID());
						String urls[] = new String[1];
						String housePics[] = house.getPicURL();
						if (housePics != null && housePics.length > 0)
						{
							urls[0] = housePics[0];
						}
						else
						{
							urls = new String[0];
						}
						Map<Object, Object> newHouse = new HashMap<Object, Object>();
						newHouse.put("id", house.getId());
						newHouse.put("picURLWithSize", PicSizeUtils.URL2URLWithSize(urls, clientUId, "house/chat/brokerSend.controller", SizeType.Default));
						newHouse.put("decorating", house.getDecorating());
						newHouse.put("price", house.getPrice());
						newHouse.put("rentPrice", house.getRentPrice());
						newHouse.put("plateName", house.getPlateName());
						newHouse.put("area", house.getArea());
						newHouse.put("roomType", house.getRoomType());
						newHouse.put("AVG", house.getAvg());
						newHouses4Broker.add(newHouse);
					}
					catch(Exception ex)
					{
						
					}
				}
			}
		}
		else
		{
			messageId = 0;
		}
	
		bean.setData(list);
		bean.setExtData(newHouses4Broker);
		
		if (messageId > 0 && !(request_user_id == 0 && brokerId > 0))
		{
			map = new HashMap<Object, Object>();
			if (fromTo == 2)
			{
				//客户端
				map.put("houseID", houseId);
			}
			map.put(fromTo == 2 ? "brokerID" : "realBrokerID", brokerId);
			map.put("transferBrokerID", transferBrokerId);
			map.put("lastID", messageId);
			map.put("updateTime", new Date());
			map.put("status", 1);
			map.put("type", type);
			map.put("fromTo", fromTo);
	
			houseChatMessageDao.update("updateHouseChatMessageStatus", map);
		}
		
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "house/chat/send.controller")
	public ModelAndView houseChatSent(
			@RequestParam String clientUId, 
			@RequestParam int houseId,
			@RequestParam(required = false) Integer type,
			@RequestParam(required = false) Integer brokerId,
			@RequestParam String content) {
		
		if (type == null)
		{
			type = 1;
		}
		
		ResultBean bean = new ResultBean();
		
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setMsg(StringUtils.EMPTY);
		bean.setData("");
		
		content = content.trim();
		
		if (content.isEmpty())
		{
			bean.setCode(201);
			bean.setMsg("消息不能为空");
			bean.setData("");
			return new ModelAndView("jsonView", "json", bean);
		}
		
		HouseInteractionExtEntity interaction = houseService.getInteraction(houseId);
		if (interaction != null || brokerId != null)
		{
			HouseChatMessageEntity msg = new HouseChatMessageEntity();
			
			brokerId = (brokerId != null ? brokerId : interaction.getAgentID());
			
			HouseInteractionTransferExtEntity transfer = houseService.getInteractionTransfer(houseId, brokerId);
			int realBrokerId = transfer == null ? brokerId : transfer.getFinalBrokerID();
			
			msg.setBrokerID(brokerId);
			msg.setRealBrokerID(realBrokerId);
			
			msg.setHouseID(houseId);
			msg.setClientUID(clientUId);
			
			int format = 0;
			Integer recommendHouseId = null;
			Pattern pattern = Pattern.compile("^@([0-9]+)$");
			Matcher matcher = pattern.matcher(content);  
			boolean match = matcher.find();
			
			if (match)
			{
				String idstr = matcher.group();
				recommendHouseId = Integer.parseInt(idstr.substring(1));
			}
			
			if (recommendHouseId != null)
			{
				content = "@" + recommendHouseId;
				
				String houseRecommendContent = house2JSONStr(recommendHouseId, type, clientUId);
				
				if (houseRecommendContent.equals("200") || houseRecommendContent.equals("201"))
				{
					bean.setCode(Integer.parseInt(houseRecommendContent));
					bean.setMsg("@" + recommendHouseId + " - " + (houseRecommendContent.equals("201") ? "房源状态错误" : "房源已下架"));
				}
				else
				{
					content = houseRecommendContent;
					format = 1;
				}
				
			}
			
			msg.setStatus(0);
			msg.setType(type);
			msg.setFormat(format);
			msg.setFromTo(1);
			msg.setAddTime(new Date());
			msg.setUpdateTime(new Date());
			msg.setContent(content);
			
			if (bean.getCode() == ResutlCodeEnum.SUCCESS.getType())
			{
				int messageId = houseChatMessageDao.add("addHouseChatMessage", msg);
			
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put("messageId", messageId);
			
				bean.setData(map);
			}
			
			
		}
		else
		{
			bean.setCode(ResutlCodeEnum.SUCCESS.getType());
			bean.setMsg(StringUtils.EMPTY);
		}
			
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "house/chat/test.controller")
	public ModelAndView test(@RequestParam String content)
	{
		ResultBean bean = new ResultBean();
		
		Pattern pattern = Pattern.compile("^@([0-9]+)$");
		Matcher matcher = pattern.matcher(content);  
		boolean match = matcher.find();
		if (match)
		{
			String idstr = matcher.group();
			bean.setData(Integer.parseInt(idstr.substring(1)));
		}
		
		return new ModelAndView("jsonView", "json", bean);
	}
	
	private String house2JSONStr(Integer id, Integer type, String clientUId)
	{
		String result = "";
		JSONObject object = new JSONObject();
		try
		{
			HouseDetailBean house = houseService.loadDetail(id);
			
			if (house != null)
			{
				int houseStatus = houseService.getHouseStatus(id, type);
				
				if (houseStatus == 1)
				{
					String urls[] = new String[1];
					String housePics[] = house.getPicURL();
					if (housePics != null && housePics.length > 0)
					{
						urls[0] = housePics[0];
					}
					else
					{
						urls = new String[0];
					}
					
					object.put("id", house.getId());
					object.put("picURLWithSize", PicSizeUtils.URL2URLWithSize(urls, clientUId, "house/chat/brokerSend.controller", SizeType.Default));
					object.put("decorating", house.getDecorating());
					object.put("price", house.getPrice());
					object.put("rentPrice", house.getRentPrice());
					object.put("plateName", house.getPlateName());
					object.put("area", house.getArea());
					object.put("roomType", house.getRoomType());
					object.put("AVG", house.getAvg());
					
					result = object.toString();
				}
				else
				{
					result = (houseStatus == 0 ? "201" : "200");
				}
			}
			else
			{
				result = "201";
			}
		}
		catch(Exception e)
		{
			result = "201";
		}
		
		return result;
	}
	
	@RequestMapping(value = "house/chat/brokerSend.controller")
	public ModelAndView houseChatBrokerSent(
			@RequestParam int brokerId, 
			@RequestParam String clientUId, 
			@RequestParam int houseId, 
			@RequestParam String content,
			@RequestParam int type,
			@RequestParam(required = false) Integer format,
			@RequestParam(required = false) Integer recommendHouseId,
			@RequestParam(required = false) String secret
			) {
		
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setMsg(StringUtils.EMPTY);
		bean.setData("");
		
		content = content.trim();
		
		if (content.isEmpty())
		{
			bean.setCode(201);
			bean.setMsg("消息不能为空");
			bean.setData("");
			return new ModelAndView("jsonView", "json", bean);
		}
		
		int realBrokerId = brokerId;
		
		if (secret != null)
		{
			realBrokerId = authenticationService.decodeBrokerId(secret);
		}
		
		HouseInteractionTransferExtEntity transfer = houseService.getInteractionTransfer(houseId, realBrokerId);
		
		if (transfer != null && transfer.getFinalBrokerID() != realBrokerId)
		{
			//已迁移，无法发送消息
			bean.setCode(203);
			bean.setMsg("会话已迁移，当前会话无法发送消息");
			bean.setData("");
			return new ModelAndView("jsonView", "json", bean);
		}
		
		HouseChatMessageEntity msg = new HouseChatMessageEntity();
		
		format = (format == null ? 0 : format);
		
		if (format == 1 && recommendHouseId != null)
		{
			
		}
		else
		{
			Pattern pattern = Pattern.compile("^@([0-9]+)$");
			Matcher matcher = pattern.matcher(content);  
			boolean match = matcher.find();
			if (match)
			{
				String idstr = matcher.group();
				recommendHouseId = Integer.parseInt(idstr.substring(1));
			}
		}
		
		if (recommendHouseId != null)
		{
			content = "@" + recommendHouseId;
			
			String houseRecommendContent = house2JSONStr(recommendHouseId, type, clientUId);
			
			if (houseRecommendContent.equals("200") || houseRecommendContent.equals("201"))
			{
				bean.setCode(Integer.parseInt(houseRecommendContent));
				bean.setMsg("@" + recommendHouseId + " - " + (houseRecommendContent.equals("201") ? "房源状态错误" : "房源已下架"));
			}
			else
			{
				content = houseRecommendContent;
				format = 1;
			}
			
		}
		
		msg.setBrokerID(brokerId);
		msg.setRealBrokerID(realBrokerId);
		msg.setHouseID(houseId);
		msg.setClientUID(clientUId);
		msg.setStatus(0);
		msg.setType(type);
		msg.setFromTo(2);
		msg.setFormat(format);
		msg.setAddTime(new Date());
		msg.setUpdateTime(new Date());
		msg.setContent(content);
		
		if (bean.getCode() == ResutlCodeEnum.SUCCESS.getType())
		{
			int messageId = houseChatMessageDao.add("addHouseChatMessage", msg);
		
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("messageId", messageId);
		
			bean.setData(map);
		}
			
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "house/chat/brokerList.controller")
	public ModelAndView houseChatBrokerList(@RequestParam String secret, 
			@RequestParam(required = false) Integer hasChat)
	{
		
		hasChat = (hasChat == null ? 0 : hasChat);
		
		ResultBean bean = new ResultBean();
		
		int brokerId = authenticationService.decodeBrokerId(secret);
		
		if (brokerId == 0)
		{
			Map<Object, Object> map = new HashMap<Object, Object>();
			if (hasChat.equals(0))
			{
				map.put("showAll", 1);
			}
			map.put("status", "0,1");
			List<BrokerSummaryBean> list = (List<BrokerSummaryBean>)houseChatMessageDao.select("getChatBrokerList", map);
			bean.setData(list);
		}
		else
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("权限错误");
		}
		
		return new ModelAndView("jsonView", "json", bean);
	}

	@Scope("request")
	@RequestMapping(value = "house/chatSummary.controller")
	public ModelAndView houseChatSummary(@RequestParam(required = false) String clientUId,
			@RequestParam(required = false) Integer brokerId,
			@RequestParam(required = false) Integer totalOnly,
			@RequestParam(required = false) String secret,
			@RequestParam(required = false) Integer groupBy,
			@RequestParam(required = false) Integer showAll
			) {
		
		ResultBean bean = new ResultBean();
		
		totalOnly = (totalOnly == null ? 0 : totalOnly); 
		groupBy = (secret == null ? 0 : (groupBy == null ? 0 : groupBy)); 
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		int request_user_id = -1;
		
		if (clientUId != null)
		{
			//客户端请求
			map.put("clientUID", clientUId);
			showAll = 0;
		}
		else
		{
			//经纪人端请求
			if (secret != null)
			{
				request_user_id = authenticationService.decodeBrokerId(secret);
				
				if (request_user_id > 0)
				{
					//除了admin, 其他账号只能查看自己的chat_summary
					brokerId = request_user_id;
				}
				else if (showAll == null)
				{
					//admin在手机端只能看自己
					brokerId = request_user_id;
				}

				AccountExtEntity account = (AccountExtEntity)accountDao.load("loadAccountById", request_user_id);
				
				if (account == null)
				{
					//已注销
					secret = "";
					bean.setCode(202);
					bean.setMsg("账号已注销");
					map = new HashMap<Object, Object>();
					map.put("count", 0);
					map.put("secret", "");
					return new ModelAndView("jsonView", "json", bean);	
				}
			}
			
			showAll = (showAll == null ? 0 : showAll);
			
			map.put("realBrokerId", brokerId);
			
			if (showAll.equals(1))
			{
				map.put("showAll", 1);
			}
		}
		
		
		if (totalOnly.equals(1))
		{
			if (clientUId != null)
			{
				map.put("countForClient", true);
			}
			else
			{
				map.put("countForBroker", true);
			}
			
			List<HouseChatMessageEntity> list = (List<HouseChatMessageEntity>)houseChatMessageDao.select("countUserUnreadChatMessage", map);
			
			map = new HashMap<Object, Object>();
			
			if (!list.isEmpty())
			{
				map.put("count", list.size());
			}
			else
			{
				map.put("count", 0);
			}
			
			map.put("secret", secret);
			bean.setData(map);
		}
		else
		{
			int fromTo;
			List<UserChatSummaryBean> list;
			if (clientUId != null)
			{
				//客户端
				fromTo = 2;
				list = (List<UserChatSummaryBean>)houseChatMessageDao.select("getUserChatSummary", map);
			}
			else
			{
				//经纪人
				fromTo = 1;
				list = (List<UserChatSummaryBean>)houseChatMessageDao.select("getBrokerChatSummary", map);
			}
			
			List<Map<Object, Object>> groupList = new ArrayList<Map<Object, Object>>();
			
			if (!list.isEmpty())
			{
				Map<Object, Object> clientMap = new HashMap<Object, Object>();
				
				Iterator itr = list.iterator();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				int groupIndex = 0;
				
				while (itr.hasNext())
				{
					
					UserChatSummaryBean summary = (UserChatSummaryBean)itr.next();
					
					if (fromTo == 1)
					{
						summary.setIsTransfer(summary.getBrokerId() != request_user_id);
					}
					else
					{
						summary.setIsTransfer(false);
					}
					
					HouseInteractionTransferExtEntity transfer = houseService.getInteractionTransfer(summary.getHouseId(), summary.getBrokerId());
					int realBrokerId = transfer == null ? summary.getBrokerId() : transfer.getFinalBrokerID();
					
					AccountExtEntity account = (AccountExtEntity)accountDao.load("loadAccountAllStatusById", realBrokerId);
					
					summary.setRealBrokerId(account.getId());
					summary.setBrokerName(account.getName());
					summary.setBrokerPicURL(account.getPicURL() != null ? account.getPicURL() + "?imageView/2/w/200/h/200" : "");
					
					if (summary.getCount() > 0)
					{
						map = new HashMap<Object, Object>();
						map.put("clientUID", summary.getClientUId());
						map.put("houseID", summary.getHouseId());
						map.put("brokerID", summary.getBrokerId());
						
						if (showAll == 0)
						{
							map.put("status", "0");
						}
						map.put("type", summary.getType());
						map.put("fromTo", fromTo);
						map.put("limit", true);
						map.put("limitStart", 0);
						map.put("limitSize", 1);
						map.put("idOrder", "DESC");
						
						List<HouseChatMessageEntity> chatList = (List<HouseChatMessageEntity>)houseChatMessageDao.select("findHouseChatMessageList", map);
						HouseChatMessageEntity lastMsg = chatList.get(0);
						
						HouseDetailBean house = null;
						
						try
						{
							house = houseService.loadDetail(lastMsg.getHouseID());
						}
						catch(Exception ex)
						{
						}
						
						if (house != null)
						{
							summary.setLastContent(lastMsg.getContent());
							summary.setLastContentFormat(lastMsg.getFormat());
							summary.setLastUpdateTime(df.format(lastMsg.getAddTime()));
							summary.setResidenceName(house.getResidenceName());
													
							summary.setTitle(summary.getType() == 1 ? 
								house.getPrice(): 
								house.getRentPrice()
							);
						}
						else
						{
							summary.setLastContent("");
							summary.setLastUpdateTime("");
							summary.setResidenceName("");
							summary.setTitle("");
						}
					}
					
					if (groupBy.equals(1))
					{
						Map<Object, Object> clientGroup;
						
						List<UserChatSummaryBean> sessionList = new ArrayList<UserChatSummaryBean>();
						
						int currentGroupIndex = 0;
						String groupKey = summary.getClientUId() + summary.getType();
						if(!clientMap.containsKey(groupKey))
						{
							clientMap.put(groupKey, groupIndex);
							
							clientGroup = new HashMap<Object, Object>();
							clientGroup.put("clientUId", summary.getClientUId());
							clientGroup.put("clientName", sessionService.getClientName(summary.getClientUId(), brokerId));
							clientGroup.put("type", summary.getType());
							clientGroup.put("count", 0);
							clientGroup.put("lastContent", summary.getLastContent());
							clientGroup.put("lastContentType", summary.getType());
							clientGroup.put("lastContentFormat", summary.getLastContentFormat());
							clientGroup.put("lastUpdateTime", summary.getLastUpdateTime());
							clientGroup.put("sessionList", sessionList);
							
							groupList.add(clientGroup);
							
							currentGroupIndex = groupIndex;
							
							groupIndex++;
						}
						else
						{
							currentGroupIndex = (Integer)clientMap.get(groupKey);
							clientGroup = groupList.get(currentGroupIndex);
						}
						
						sessionList = (List<UserChatSummaryBean>)clientGroup.get("sessionList");
						sessionList.add(summary);
						clientGroup.put("sessionList", sessionList);
						
						int groupCount = (Integer)clientGroup.get("count") + summary.getCount();
						clientGroup.put("count", groupCount);
						groupList.set(currentGroupIndex, clientGroup);
						
					}
			
				}
			}
			
			if(groupBy.equals(0))
			{
				bean.setData(list);
			}
			else 
			{
				bean.setData(groupList);
			}
			
		}
		
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setMsg(StringUtils.EMPTY);
		
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@RequestMapping(value = "house/chat/deleteSession.controller")
	public ModelAndView deleteSession(
		@RequestParam String clientUId,
		@RequestParam int brokerId,
		@RequestParam int houseId, 
		@RequestParam int type,
		@RequestParam int isClient
		)
	{
		ResultBean bean = new ResultBean();
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("clientUID", clientUId);
		map.put("houseID", houseId);
		map.put("brokerID", brokerId);
		map.put("updateTime", new Date());
		map.put("type", type);
		map.put(isClient == 1 ? "clientDelete" : "brokerDelete", 1);

		houseChatMessageDao.update("updateHouseChatMessageStatus", map);
		
		return new ModelAndView("jsonView", "json", bean);
	}
	
	@RequestMapping(value = "house/chat/notify.controller")
	public ModelAndView houseChatNotify()
	{
		ResultBean bean = new ResultBean();
		try
		{
			sessionService.notifyClientUnreadChats();
			sessionService.notifyBrokerUnreadChats();
		}
		catch(Exception ex)
		{
			bean.setMsg(ex.getMessage());
		}
		
		return new ModelAndView("jsonView", "json", bean);
	}
			
}
