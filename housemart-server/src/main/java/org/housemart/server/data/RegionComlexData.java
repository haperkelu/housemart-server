/**   
* @Title: RegionComlexData.java 
* @Package org.housemart.server.data 
* @Description: TODO
* @author Pie.Li   
* @date 2014-2-26 下午9:50:27 
* @version V1.0   
*/
package org.housemart.server.data;

/**
 * @author Pie.Li
 *
 */
public class RegionComlexData {

	public static class Region {
		
		private int ID;
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
