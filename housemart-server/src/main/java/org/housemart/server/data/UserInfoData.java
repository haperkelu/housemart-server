package org.housemart.server.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.dao.entities.HouseFollowEntity;
import org.housemart.server.dao.entities.ResidenceFollowEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInfoData {

	private static final Map<houseKey, Set<String>> _userHouseFollowMap = new HashMap<houseKey,   Set<String>>();
	private static final Map<Integer, Set<String>> _userResidenceFollowMap = new HashMap<Integer, Set<String>>();	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoData.class);
	
	private static UserInfoData instance;
	
	/**
	 * 
	 */
	private UserInfoData(){
		
	}
	
	@SuppressWarnings("rawtypes")
	private static GenericDao houseFollowDao = SpringContextHolder.getBean("houseFollowDao");
	
	@SuppressWarnings("rawtypes")
	private static GenericDao residenceFollowDao = SpringContextHolder.getBean("residenceFollowDao");
	
	public static UserInfoData getInstance() {
		if(instance != null){
			return instance;
		}
		synchronized (UserInfoData.class) {
			try {
				instance = new UserInfoData();
				
				//房源关注
				_userHouseFollowMap.clear();
				@SuppressWarnings("unchecked")
				List<HouseFollowEntity> list = houseFollowDao.select("selectAll", null);
				if(CollectionUtils.isNotEmpty(list)){
					
					int houseId, type;
					houseKey key;
					for(HouseFollowEntity item: list){
						houseId = item.getHouseId();
						type = item.getType();
						key = new houseKey(houseId, type);
						Set<String> set = _userHouseFollowMap.get(key);
						if(set == null){
							set = new HashSet<String>();
							_userHouseFollowMap.put(key, set);
						} 
						set.add(item.getClientId());
					}
				}
				
				//小区关注
				_userResidenceFollowMap.clear();
				@SuppressWarnings("unchecked")
				List<ResidenceFollowEntity> residenceList = residenceFollowDao.select("selectAll", null);
				if(CollectionUtils.isNotEmpty(residenceList)){
					for(ResidenceFollowEntity item: residenceList){
						Set<String> set = _userResidenceFollowMap.get(item.getResidenceId());
						if(set == null){
							set = new HashSet<String>();
							_userResidenceFollowMap.put(item.getResidenceId(), set);
						} 
						set.add(item.getClientId());
					}					
				}				
				
				return instance;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	static class houseKey {
		
		houseKey(int houseId, int type){
			this.houseId = houseId;
			this.type = type;
		}
		private int houseId;
		private int type;
		/**
		 * @return the houseId
		 */
		public int getHouseId() {
			return houseId;
		}
		/**
		 * @param houseId the houseId to set
		 */
		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}
		/**
		 * @return the type
		 */
		public int getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(int type) {
			this.type = type;
		}
		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return String.valueOf(this.houseId + "_" + this.type).hashCode();
		}
		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof houseKey){
				houseKey realObj = (houseKey)obj;
				return this.houseId == realObj.houseId && this.type == realObj.type;
			}
			return super.equals(obj);
		}
		
	} 
	
	/**
	 * 
	* @Title: isUserFollowHouse
	* @Description: TODO
	* @param @param clientId
	* @return void
	* @throws
	 */
	public boolean isUserFollowHouse(int houseId, int type, String clientId){
		Set<String> list = _userHouseFollowMap.get(new houseKey(houseId, type));
		if(CollectionUtils.isNotEmpty(list)){
			if(list.contains(clientId)){
				return true;
			};
		}
		return false;
	}
	
	/**
	 * 
	 * @param residenceId
	 * @param clientId
	 * @return
	 */
	public boolean isUserFollowResidence(int residenceId, String clientId){
		Set<String> set = _userResidenceFollowMap.get(residenceId);
		
		if(CollectionUtils.isNotEmpty(set)){
			logger.debug("residenceId:" + residenceId + ";clientId:" + clientId);
			if(set.contains(clientId)){
				logger.debug("residenceId:" + residenceId + ";clientId:" + clientId + ";beat it");
				return true;
			}
		}
		return false;
	};
	
	/**
	 * 
	 * @param residenceId
	 * @param clientId
	 */
	public void putResidenceClientId(int residenceId, String clientId){
		Set<String> set = _userResidenceFollowMap.get(residenceId);
		if(set == null){
			_userResidenceFollowMap.put(residenceId, new HashSet<String>());
		}
		_userResidenceFollowMap.get(residenceId).add(clientId);
	}
	
	/**
	 * 
	* @Title: putHouseUserClientId
	* @Description: TODO
	* @param @param houseId
	* @param @param clientId
	* @return void
	* @throws
	 */
	public void putHouseUserClientId(int houseId, int type, String clientId){
		Set<String> list = _userHouseFollowMap.get(new houseKey(houseId, type));
		if(list == null){
			_userHouseFollowMap.put(new houseKey(houseId, type), new HashSet<String>());
		}
		_userHouseFollowMap.get(new houseKey(houseId, type)).add(clientId);		
	}
	
	/**
	 * 
	* @Title: cancelHouseUserClientId
	* @Description: TODO
	* @param @param houseId
	* @param @param clientId
	* @return void
	* @throws
	 */
	public void cancelHouseUserClientId(int houseId, int type, String clientId){
		Set<String> list = _userHouseFollowMap.get(new houseKey(houseId, type));
		if(list == null) {return;}
		list.remove(clientId);
	}
	
	/**
	 * 
	 * @param residenceId
	 * @param clientId
	 */
	public void cancelResidenceUserClientId(int residenceId, String clientId){
		Set<String> set = _userResidenceFollowMap.get(residenceId);
		if(set == null){
			return;
		}
		set.remove(clientId);
	}
	
}
