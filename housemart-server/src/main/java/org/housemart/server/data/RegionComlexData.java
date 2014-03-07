/**   
* @Title: RegionComlexData.java 
* @Package org.housemart.server.data 
* @Description: TODO
* @author Pie.Li   
* @date 2014-2-26 下午9:50:27 
* @version V1.0   
*/
package org.housemart.server.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.dao.entities.AreaPositionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Pie.Li
 *
 */
public class RegionComlexData {
	
	private static final Logger logger = LoggerFactory.getLogger(RegionComlexData.class);	
	private static Date timeStamp = Calendar.getInstance().getTime();
	private static List<AreaPositionEntity> plate_positions = null;
	static {
		
		try {
			refreshPlate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static List<AreaPositionEntity> getPlates() {
		Date now = Calendar.getInstance().getTime();
		long timeMills = now.getTime();
		if((timeStamp.getTime() - timeMills) > 1000 * 60 *5) {
			refreshPlate();
			timeStamp = now;
		} 
		return plate_positions;
	}
	
	private static void refreshPlate() {
		GenericDao areaPositionDao = SpringContextHolder.getBean("areaPositionDao");
		Map para = new HashMap();
		para.put("cityId", 2);

		plate_positions = areaPositionDao.select("findAllPlatesByCityId", para);
	}
	
	public static class Region {
		
		private int ID;
		private int cityId;
		private int Name;
		private String lat;
		private String lng;
		private int level;
		private String geoHash;
		/**
		 * @return the iD
		 */
		public int getID() {
			return ID;
		}
		/**
		 * @param iD the iD to set
		 */
		public void setID(int iD) {
			ID = iD;
		}
		
		/**
		 * @return the cityId
		 */
		public int getCityId() {
			return cityId;
		}
		/**
		 * @param cityId the cityId to set
		 */
		public void setCityId(int cityId) {
			this.cityId = cityId;
		}
		/**
		 * @return the name
		 */
		public int getName() {
			return Name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(int name) {
			Name = name;
		}
		/**
		 * @return the lat
		 */
		public String getLat() {
			return lat;
		}
		/**
		 * @param lat the lat to set
		 */
		public void setLat(String lat) {
			this.lat = lat;
		}
		/**
		 * @return the lng
		 */
		public String getLng() {
			return lng;
		}
		/**
		 * @param lng the lng to set
		 */
		public void setLng(String lng) {
			this.lng = lng;
		}
		/**
		 * @return the level
		 */
		public int getLevel() {
			return level;
		}
		/**
		 * @param level the level to set
		 */
		public void setLevel(int level) {
			this.level = level;
		}
		/**
		 * @return the geoHash
		 */
		public String getGeoHash() {
			return geoHash;
		}
		/**
		 * @param geoHash the geoHash to set
		 */
		public void setGeoHash(String geoHash) {
			this.geoHash = geoHash;
		}
	}
	
	
	
}
