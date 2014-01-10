/**
 * 
 */
package org.housemart.server.resource;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author pai.li
 *
 */
public class ResourceProvider {

	private static final Properties props = new Properties();
	private static final String path = "biz-data/webKeyValueRes.properties";
	
	public ResourceProvider() {
		try {
			props.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path), "UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		};
	}
	
	public String getValue(String key){
		return props.getProperty(key);
	}
	
}
