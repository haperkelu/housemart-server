/**
 * Created on 2012-11-17
 * 
 */
package org.housemart.server.web.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.service.HouseServiceMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author pqin
 */
@Controller
public class HouseControllerMock {
	private static final Log log = LogFactory.getLog(HouseControllerMock.class);
	@Autowired
	HouseServiceMock houseServiceMock;

	@RequestMapping(value = "house/detail.controller")
	public ModelAndView detail(@RequestParam Integer houseId) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		try {
			bean.setData(houseServiceMock.loadDetail(houseId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ModelAndView("jsonView", "json", bean);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "residenceSale/houseList.controller")
	public ModelAndView residenceSaleList(@RequestParam Integer residenceId, @RequestParam Integer orderType,
			@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		try {
			List sales = houseServiceMock.findSales(residenceId);
			// if (sales.size() > pageSize)
			// bean.setData(houseService.findSales(residenceId).subList(pageIndex
			// * pageSize,
			// (pageIndex + 1) * pageSize));
			// else
			bean.setData(sales);
			bean.setCount(sales.size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return new ModelAndView("jsonView", "json", bean);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "residenceSold/houseList.controller")
	public ModelAndView residenceSoldList(@RequestParam Integer residenceId, @RequestParam Integer orderType,
			@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		try {
			List sales = houseServiceMock.findSales(residenceId);
			// if (sales.size() > pageSize)
			// bean.setData(houseService.findSales(residenceId).subList(pageIndex
			// * pageSize,
			// (pageIndex + 1) * pageSize));
			// else
			bean.setData(sales);
			bean.setCount(sales.size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ModelAndView("jsonView", "json", bean);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "residenceRent/houseList.controller")
	public ModelAndView residenceRentList(@RequestParam Integer residenceId, @RequestParam Integer orderType,
			@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		try {
			List rents = houseServiceMock.findRents(residenceId);
			// if (rents.size() > pageSize)
			// bean.setData(houseService.findRents(residenceId).subList(pageIndex
			// * pageSize,
			// (pageIndex + 1) * pageSize));
			// else
			bean.setData(rents);
			bean.setCount(rents.size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ModelAndView("jsonView", "json", bean);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "residenceRenting/houseList.controller")
	public ModelAndView residenceRentingList(@RequestParam Integer residenceId, @RequestParam Integer orderType,
			@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		try {
			List rents = houseServiceMock.findRents(residenceId);
			// if (rents.size() > pageSize)
			// bean.setData(houseService.findRents(residenceId).subList(pageIndex
			// * pageSize,
			// (pageIndex + 1) * pageSize));
			// else
			bean.setData(rents);
			bean.setCount(rents.size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ModelAndView("jsonView", "json", bean);
	}

	@RequestMapping(value = "house/sale/detailedSearchByPosition.controller")
	public ModelAndView saleDetailedListByPosition(@RequestParam Integer cityId, @RequestParam Integer regionId,
			@RequestParam Integer plateId, @RequestParam Integer type, @RequestParam Integer roomType,
			@RequestParam String priceRange, @RequestParam Integer orderType, @RequestParam Integer pageIndex,
			@RequestParam Integer pageSize) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		try {
			bean.setData(houseServiceMock.findSales(40).subList(pageIndex * pageSize, (pageIndex + 1) * pageSize));
			bean.setCount(houseServiceMock.findSales(40).size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ModelAndView("jsonView", "json", bean);
	}

	@RequestMapping(value = "house/sale/detailedSearchNearby.controller")
	public ModelAndView saleDetailedListNearBy(@RequestParam Integer cityId, @RequestParam String lat,
			@RequestParam String lng, @RequestParam Integer range, @RequestParam Integer type,
			@RequestParam Integer roomType, @RequestParam String priceRange, @RequestParam Integer orderType,
			@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		try {
			bean.setData(houseServiceMock.findSales(40).subList(pageIndex * pageSize, (pageIndex + 1) * pageSize));
			bean.setCount(houseServiceMock.findSales(40).size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ModelAndView("jsonView", "json", bean);
	}

	@RequestMapping(value = "house/rent/detailedSearchByPosition.controller")
	public ModelAndView rentDetailedListByPosition(@RequestParam Integer cityId, @RequestParam Integer regionId,
			@RequestParam Integer plateId, @RequestParam Integer type, @RequestParam Integer roomType,
			@RequestParam String priceRange, @RequestParam Integer orderType, @RequestParam Integer pageIndex,
			@RequestParam Integer pageSize) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		try {
			bean.setData(houseServiceMock.findRents(40).subList(pageIndex * pageSize, (pageIndex + 1) * pageSize));
			bean.setCount(houseServiceMock.findRents(40).size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ModelAndView("jsonView", "json", bean);
	}

	@RequestMapping(value = "house/rent/detailedSearchNearby.controller")
	public ModelAndView rentDetailedListNearBy(@RequestParam Integer cityId, @RequestParam String lat,
			@RequestParam String lng, @RequestParam Integer range, @RequestParam Integer type,
			@RequestParam Integer roomType, @RequestParam String priceRange, @RequestParam Integer orderType,
			@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		try {
			bean.setData(houseServiceMock.findRents(40).subList(pageIndex * pageSize, (pageIndex + 1) * pageSize));
			bean.setCount(houseServiceMock.findRents(40).size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ModelAndView("jsonView", "json", bean);
	}
}
