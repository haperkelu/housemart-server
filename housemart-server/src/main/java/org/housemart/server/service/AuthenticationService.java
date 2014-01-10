package org.housemart.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.housemart.server.dao.entities.AccountEntity;
import org.housemart.server.dao.entities.ClientRegisterEntity;
import org.housemart.server.util.EncryptUtils;

public class AuthenticationService {

	@SuppressWarnings("rawtypes")
	private static final GenericDao accountDao = SpringContextHolder.getBean("accountDao"); 
	
	@SuppressWarnings("rawtypes")
	private GenericDao clientRegisterDao = SpringContextHolder.getBean("clientRegisterDao");
	
	public AccountEntity login(String userName, String password)
	{	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", userName);
		map.put("password", password);
		@SuppressWarnings("unchecked")
		List<AccountEntity> result = (List<AccountEntity>)accountDao.select("loadAccountByNameAndPassword", map);
		if(!result.isEmpty()){
			return result.get(0);
		}	
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isLoggin(String input)
	{	
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public ClientRegisterEntity getClientInfo(String clientUId)
	{	
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("clientID", clientUId);
		
		List<ClientRegisterEntity> result = (List<ClientRegisterEntity>)clientRegisterDao.select("findClientRegisterList", map);
		
		if(!result.isEmpty())
		{
			return result.get(0);
		}	
		
		return null;
	}
	
	private String deCrypt(String secret)
	{
		String result = "";
		try
		{
			result = EncryptUtils.deCrypt(secret, "housemart_app");
		}
		catch(Exception ex)
		{
			
		}
		
		return result;
	}
	
	private String enCrypt(String input)
	{
		String secret = "";
		try
		{
			secret = EncryptUtils.enCrypt(input, "housemart_app");
		}
		catch(Exception ex)
		{
			
		}
		
		return secret;
	}
	
	public String getSecretKey(int brokerId, String clientUId)
	{
		return this.enCrypt(brokerId + "," + clientUId);
	}
	
	public Map<String, String> decodeSecretKey(String secret)
	{
		Map<String, String> result = new HashMap<String, String>();
		
		String info = this.deCrypt(secret);
		
		result.put("brokerId", info.split(",")[0]);
		
		if (info.split(",").length == 2)
		{
			result.put("clientUId", info.split(",")[1]);
		}
		
		return result;
	}
	
	public int decodeBrokerId(String secret)
	{
		return Integer.parseInt(this.decodeSecretKey(secret).get("brokerId"));
	}
	
}
