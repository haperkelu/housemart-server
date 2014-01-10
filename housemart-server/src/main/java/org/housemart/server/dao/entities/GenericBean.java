/**
 * Created on 2013-3-21
 * 
 */
package org.housemart.server.dao.entities;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

@SuppressWarnings("rawtypes")
public abstract class GenericBean {

	@SuppressWarnings("deprecation")
	public GenericBean() {

		Map bean = null;

		try {
			bean = BeanUtils.describe(this);
			for (Object o : bean.entrySet()) {

				Map.Entry e = (Map.Entry) o;
				Class type = PropertyUtils.getPropertyType(this, e.getKey().toString());

				if (type.equals(Integer.class)) {
					PropertyUtils.setProperty(this, e.getKey().toString(), 0);
				} else if (type.equals(String.class)) {
					PropertyUtils.setProperty(this, e.getKey().toString(), "");
				} else if (type.equals(Double.class)) {
					PropertyUtils.setProperty(this, e.getKey().toString(), (double) 0);
				} else if (type.equals(Float.class)) {
					PropertyUtils.setProperty(this, e.getKey().toString(), (float) 0);
				} else if (type.equals(Long.class)) {
					PropertyUtils.setProperty(this, e.getKey().toString(), (long) 0);
				} else if (type.equals(Date.class)) {
					PropertyUtils.setProperty(this, e.getKey().toString(), new Date(100, 0, 1));
				} else if (type.equals(Boolean.class)) {
					PropertyUtils.setProperty(this, e.getKey().toString(), false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		String ret = null;
		Map bean = null;

		try {
			StringBuffer sb = new StringBuffer();
			bean = BeanUtils.describe(this);
			for (Object o : bean.entrySet()) {
				Map.Entry e = (Map.Entry) o;
				if (e.getKey().toString().equals("class"))
					continue;
				sb.append(e.getKey()).append(":").append(e.getValue()).append(" ");
			}
			ret = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

}
