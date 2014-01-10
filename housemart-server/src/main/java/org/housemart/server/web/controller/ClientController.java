package org.housemart.server.web.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.push.JavaPNSProvider;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.dao.entities.ClientRegisterEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {
	
	@SuppressWarnings("rawtypes")
	private GenericDao clientRegisterDao = SpringContextHolder.getBean("clientRegisterDao");
	
	@RequestMapping(value = "client/clientRegister.controller")
	public ModelAndView clientRegister(@RequestParam String clientUId, 
			@RequestParam String clientToken,
			@RequestParam(required = false) String device,
			@RequestParam(required = false) String version,
			@RequestParam(required = false) String systemInfo
			) {
		
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setMsg(StringUtils.EMPTY);
		
		device = device == null ? "" : device;
		version = version == null ? "" : version;
		systemInfo = systemInfo == null ? "" : systemInfo;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("clientID", clientUId);
		
		@SuppressWarnings("unchecked")
		List<ClientRegisterEntity> list = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", map);
		
		ClientRegisterEntity clientRegister = null;
		
		if (list.size() == 1)
		{
			map.put("clientToken", clientToken);
			map.put("device", device);
			map.put("version", version);
			map.put("systemInfo", systemInfo);
			map.put("updateTime", new Date());
			clientRegisterDao.update("updateClientRegister", map);
		}
		else
		{
			clientRegister = new ClientRegisterEntity();
			clientRegister.setClientID(clientUId);
			clientRegister.setClientToken(clientToken);
			clientRegister.setUpdateTime(new Date());
			clientRegister.setAddTime(new Date());
			clientRegister.setDevice(device);
			clientRegister.setVersion(version);
			clientRegister.setSystemInfo(systemInfo);
			
			clientRegisterDao.add("addClientRegister", clientRegister);
		}
		
		map = new HashMap<Object, Object>();
		map.put("chatListInterval", 5);
		int intevals[] = {4*3600, 5, 5, 5};
		map.put("chatSummaryInterval", intevals);
		bean.setData(map);
		
		return new ModelAndView("jsonView", "json", bean);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "client/apns.controller")
	public ModelAndView apns(@RequestParam String clientUId,
			@RequestParam String message
			) {

		ResultBean bean = new ResultBean();

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("clientID", clientUId);
		List<ClientRegisterEntity> clients = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", map);
		
		ClientRegisterEntity clientRegister = null;
		
		if (clients.size() > 0)
		{
			clientRegister = clients.get(0);
			bean.setData(clientRegister.getClientID() + " - " + clientRegister.getClientToken());
			try
			{						
				JavaPNSProvider.pushMessageToAPNS(clientRegister.getClientToken(), message, 1, true);
				bean.setCode(ResutlCodeEnum.SUCCESS.getType());
				
			}
			catch(Exception e)
			{
				bean.setMsg(e.getMessage());
				bean.setCode(ResutlCodeEnum.ERROR.getType());
			}
		}
		else
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
		}
		
		return new ModelAndView("jsonView", "json", bean);

	} 
	
}
