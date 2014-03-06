package org.housemart.server.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.server.beans.CityBean;
import org.housemart.server.beans.Position;
import org.housemart.server.beans.RegionBean;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.dao.entities.AreaPositionEntity;
import org.housemart.server.dao.entities.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author pai.li
 * 
 */
@SuppressWarnings("unchecked")
@Controller
public class BasicDataController {

	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao regionDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao areaPositionDao;

	@RequestMapping(value = "regionList.controller")
	public ModelAndView regionList(@RequestParam int cityId) {

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("cityId", cityId);
		List<RegionEntity> list = regionDao.select("findRegionListByCity", map);
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setData(list);
		return new ModelAndView("jsonView", "json", bean);

	}

	@RequestMapping(value = "locationAllList.controller")
	public ModelAndView locationAllList(@RequestParam int cityId) {

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("cityId", cityId);
		List<RegionEntity> list = regionDao.selectByType("findRegionListByCity", map);
		List<RegionBean> finalResult = null;

		if (!CollectionUtils.isEmpty(list)) {
			finalResult = new ArrayList<RegionBean>();
			for (RegionEntity item : list) {
				RegionBean bean = new RegionBean();
				bean.setRegionId(item.getId());
				bean.setRegionName(item.getName());
				Map<Object, Object> plateMap = new HashMap<Object, Object>();
				plateMap.put("parentId", item.getId());
				List<RegionEntity> plateList = regionDao.select("findPlateListByRegionId", plateMap);
				bean.setPlateList(plateList);
				finalResult.add(bean);
			}
		}

		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setData(finalResult);
		bean.setVersion("2.0");
		return new ModelAndView("jsonView", "json", bean);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "position.controller")
	public ModelAndView position(@RequestParam int cityId, @RequestParam int positionId, @RequestParam int type) {

		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		Map para = new HashMap();
		para.put("cityId", cityId);
		para.put("positionId", positionId);
		para.put("type", type);

		List<AreaPositionEntity> positions = areaPositionDao.select("findPlatePosition", para);
		if (CollectionUtils.isNotEmpty(positions)) {
			Position p = new Position();
			p.setLat(Double.valueOf(positions.get(0).getLat()));
			p.setLng(Double.valueOf(positions.get(0).getLng()));
			bean.setData(p);
		}

		return new ModelAndView("jsonView", "json", bean);

	}
	
	@RequestMapping(value = "cityList.controller")
	public ModelAndView cityList() {

		ResultBean bean = new ResultBean();
		List<CityBean> list = new  ArrayList<CityBean>();	
		CityBean bean1 = new CityBean(1, "上海");
		bean1.setDefaultCity(false);
		bean1.setOverseaCity(false);
		list.add(bean1);
		CityBean bean2 = new CityBean(2, "南加州");
		bean2.setDefaultCity(true);
		bean2.setOverseaCity(true);
		list.add(bean2);
		bean.setData(list);
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setVersion("5.1");
		
		return new ModelAndView("jsonView", "json", bean);

	}	

}
