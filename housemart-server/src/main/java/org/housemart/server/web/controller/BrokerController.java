package org.housemart.server.web.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.housemart.server.resource.ResourceProvider;
import org.housemart.server.dao.entities.HouseAuditHistoryEntity;
import org.housemart.server.dao.entities.HouseEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.push.JavaPNSProvider;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.beans.AjaxResultBean;
import org.housemart.server.beans.LandlordInfo;
import org.housemart.server.beans.ResidenceBean;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.beans.UpdateInfoBean;
import org.housemart.server.beans.house.HouseDetailBean;
import org.housemart.server.beans.house.HouseRentBean;
import org.housemart.server.beans.house.HouseSaleBean;
import org.housemart.server.dao.entities.ClientNotesEntity;
import org.housemart.server.dao.entities.ClientRegisterEntity;
import org.housemart.server.dao.entities.HouseProcessEntity;
import org.housemart.server.dao.entities.AccountEntity;
import org.housemart.server.dao.entities.HouseRent;
import org.housemart.server.dao.entities.HouseSale;
import org.housemart.server.dao.entities.ResidenceEntity;
import org.housemart.server.service.AuthenticationService;
import org.housemart.server.service.HouseService;
import org.housemart.server.service.ResidenceService;
import org.housemart.server.util.HttpUtils;
import org.housemart.server.util.ResidenceUtils;
import org.housemart.server.service.enums.AuditTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import freemarker.template.utility.StringUtil;

@Controller
public class BrokerController extends BaseController {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	HouseService houseService;

	@Autowired
	ResidenceService residenceService;
	
	@Autowired
	ResourceProvider resourceProvider;

	@SuppressWarnings("rawtypes")
	private GenericDao clientRegisterDao = SpringContextHolder
			.getBean("clientRegisterDao");

	@SuppressWarnings("rawtypes")
	private GenericDao houseProcessDao = SpringContextHolder
			.getBean("houseProcessDao");

	@SuppressWarnings("rawtypes")
	private GenericDao clientNotesDao = SpringContextHolder
			.getBean("clientNotesDao");

	@SuppressWarnings("rawtypes")
	private GenericDao houseDao = SpringContextHolder.getBean("houseDao");
	
	@SuppressWarnings("rawtypes")
	private GenericDao housePicDao = SpringContextHolder.getBean("housePicDao");

	@RequestMapping(value = "broker/login.controller")
	public ModelAndView brokerLogin(@RequestParam String loginName,
			@RequestParam String password,
			@RequestParam(required = false) String clientUId) {

		ResultBean bean = new ResultBean();

		AccountEntity broker = authenticationService.login(loginName, password);

		if (broker == null) {
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("登录失败，请检查用户名和密码。");
		} else {
			bean.setCode(ResutlCodeEnum.SUCCESS.getType());

			Map<Object, Object> map = new HashMap<Object, Object>();

			if (clientUId != null) {
				//清空登录标记，只允许一台手机登录
				map.put("loginBrokerID", broker.getId());
				map.put("brokerLogin", 0);
				map.put("updateTime", new Date());
				clientRegisterDao.update("updateClientRegister", map);
				
				//登录
				map = new HashMap<Object, Object>();
				map.put("clientID", clientUId);
				List<ClientRegisterEntity> list = (List<ClientRegisterEntity>) clientRegisterDao
						.select("findClientRegisterList", map);
				ClientRegisterEntity clientRegister = null;

				if (list.size() == 1) {
					map.put("brokerId", broker.getId());
					map.put("updateTime", new Date());
					map.put("brokerLogin", 1);
					clientRegisterDao.update("updateClientRegister", map);
				}
			}

			map = new HashMap<Object, Object>();

			String secret = this.authenticationService.getSecretKey(broker.getId(), clientUId);
			map.put("secret", secret);
			map.put("type", broker.getType());
			if (broker.getType() == 1) {
				// external
				map.put("authLandlordInfo", false);
				map.put("authHouseUpdate", false);
			} else {
				map.put("authLandlordInfo", true);
				map.put("authHouseUpdate", true);
			}

			bean.setData(map);
		}
		return new ModelAndView("jsonView", "json", bean);

	}

	@RequestMapping(value = "broker/logout.controller")
	public ModelAndView brokerLogout(@RequestParam String secret,
			@RequestParam(required = false) String clientUId) {
		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		
		int brokerId = authenticationService.decodeBrokerId(secret);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("loginBrokerID", brokerId);
		map.put("clientID", clientUId);
		map.put("brokerLogin", 0);
		map.put("updateTime", new Date());
		clientRegisterDao.update("updateClientRegister", map);
		
		return new ModelAndView("jsonView", "json", bean);
	}
	
	@RequestMapping(value = "broker/relogin.controller")
	public ModelAndView brokerRelogin(int isExpired) {
		ResultBean bean = new ResultBean();
		bean.setCode(202);
		bean.setMsg(isExpired == 1 ? "账号安全性升级，请重新登录。"  : "该账号已在其它手机登录，请重新登录.");
		return new ModelAndView("jsonView", "json", bean);
	}

	@RequestMapping(value = "broker/house/landlord/view.controller")
	public ModelAndView brokerHouseLandlordView(@RequestParam String secret,
			@RequestParam int houseId) {

		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());

		LandlordInfo info = new LandlordInfo();
		info.setHouseAddress("");
		info.setLandlordName("");
		info.setLandlordMobile("");
		info.setLandlordMemo("");
		info.setHouseCommitterName("");
		info.setHouseCommitterMobile("");

		try {
			int brokerId = authenticationService.decodeBrokerId(secret);

			info = houseService.getLandlordInfo(houseId, brokerId);

			if (info.getCode() == 0) {
				if (info.getHouseAddress().equals("")) {
					info.setHouseAddress("[无权查看]");
				}
				if (info.getHouseCommitterMobile().equals("")) {
					info.setHouseCommitterMobile("[无权查看]");
				}
				if (info.getHouseCommitterName().equals("")) {
					info.setHouseCommitterName("[无权查看]");
				}

				if (info.getLandlordMobile().equals("")) {
					info.setLandlordMobile("[无权查看]");
				}
				if (info.getLandlordName().equals("")) {
					info.setLandlordName("[无权查看]");
				}
				if (info.getLandlordMemo().equals("")) {
					info.setLandlordMemo("[无权查看]");
				}

			}

		} catch (Exception ex) {

		}

		bean.setData(info);
		return new ModelAndView("jsonView", "json", bean);

	}

	@RequestMapping(value = "broker/house/process/view.controller")
	public ModelAndView houseUpdateInfoView(@RequestParam String secret,
			@RequestParam int houseId) {

		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());

		int brokerId = authenticationService.decodeBrokerId(secret);

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("houseId", houseId);
		List<HouseProcessEntity> list = (List<HouseProcessEntity>) houseProcessDao
				.select("findHouseProcessListByHouseId", map);

		List<UpdateInfoBean> result = new ArrayList<UpdateInfoBean>();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		for (int i = 0; i < list.size(); i++) {
			UpdateInfoBean updateBean = new UpdateInfoBean();
			updateBean.setTime(df.format(list.get(i).getAddTime()));
			updateBean.setContent(list.get(i).getMemo());
			updateBean.setUpdater(list.get(i).getBrokerName());
			result.add(updateBean);
		}

		bean.setData(result);

		bean.setExtData(1);

		if (brokerId == 2) {
			bean.setExtData(0);
			bean.setMsg("[没有权限]");
			bean.setCode(402);
		}

		return new ModelAndView("jsonView", "json", bean);

	}

	@RequestMapping(value = "broker/house/process/update.controller")
	public ModelAndView houseUpdateInfoUpdate(@RequestParam String secret,
			@RequestParam int houseId, @RequestParam String content) {

		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());

		int brokerId = authenticationService.decodeBrokerId(secret);

		HouseProcessEntity entity = new HouseProcessEntity();
		entity.setAddTime(new Date());
		entity.setHouseId(houseId);
		entity.setBrokerId(brokerId);
		entity.setMemo(content);
		houseProcessDao.add("addHouseProcess", entity);

		return new ModelAndView("jsonView", "json", bean);

	}

	@RequestMapping(value = "broker/updateClientNotes.controller")
	public ModelAndView updateClientNotes(@RequestParam String secret,
			@RequestParam String clientUId,
			@RequestParam(required = false) String alias,
			@RequestParam(required = false) String note) {

		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());

		int brokerId = authenticationService.decodeBrokerId(secret);

		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("clientID", clientUId);
			map.put("clientUId", clientUId);
			List<ClientRegisterEntity> list = (List<ClientRegisterEntity>) clientRegisterDao
					.select("findClientRegisterList", map);
			boolean isClient = (list.size() == 1);

			if (isClient) {
				if (alias != null) {
					map.put("type", 0);
					List<ClientNotesEntity> clientNoteList = (List<ClientNotesEntity>) clientNotesDao
							.select("findClientNotes", map);

					if (clientNoteList.size() == 1) {
						map.put("brokerId", brokerId);
						map.put("note", alias);
						clientNotesDao.update("updateClientNotes", map);
					} else {
						ClientNotesEntity entity = new ClientNotesEntity();
						entity.setBrokerId(brokerId);
						entity.setClientUId(clientUId);
						entity.setNote(alias);
						entity.setType(0);
						clientNotesDao.add("addClientNotes", entity);
					}
				}

				if (note != null) {
					ClientNotesEntity entity = new ClientNotesEntity();
					entity.setBrokerId(brokerId);
					entity.setClientUId(clientUId);
					entity.setNote(note);
					entity.setType(1);
					clientNotesDao.add("addClientNotes", entity);
				}
			}

		} catch (Exception ex) {
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg(ex.getMessage());
		}

		return new ModelAndView("jsonView", "json", bean);

	}

	@RequestMapping(value = "broker/clientNotes.controller")
	public ModelAndView clientNotes(@RequestParam String secret,
			@RequestParam String clientUId) {

		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());

		int brokerId = authenticationService.decodeBrokerId(secret);
		
		List<Map<Object, Object>> resultList = new ArrayList<Map<Object, Object>>();
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("clientID", clientUId);
			map.put("clientUId", clientUId);
			List<ClientRegisterEntity> list = (List<ClientRegisterEntity>) clientRegisterDao
					.select("findClientRegisterList", map);
			boolean isClient = (list.size() == 1);

			if (isClient) {
				map.put("type", 1);
				map.put("brokerId", brokerId);
				List<ClientNotesEntity> clientNoteList = (List<ClientNotesEntity>) clientNotesDao
						.select("findClientNotes", map);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

				for (int i = 0; i < clientNoteList.size(); i++) {
					ClientNotesEntity entity = clientNoteList.get(i);
					map = new HashMap<Object, Object>();
					map.put("note", entity.getNote());
					map.put("noteTime", df.format(entity.getAddTime()));

					resultList.add(map);
				}

			}

		} catch (Exception ex) {

		}
		bean.setData(resultList);
		bean.setExtData(1);

		if (brokerId == 2) {
			bean.setExtData(0);
			bean.setMsg("[没有权限]");
		}

		return new ModelAndView("jsonView", "json", bean);

	}

	@RequestMapping(value = "broker/house/viewContactInfo.controller")
	public ModelAndView viewContactInfo(@RequestParam int houseId,
			@RequestParam int brokerId) throws Exception {

		ResultBean bean = new ResultBean();
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());

		LandlordInfo landload = houseService.getLandlordInfo(houseId, brokerId);

		if (landload.getCode() == 0) {
			landload.setHouseAddress("[无权查看]");
			landload.setHouseCommitterMobile("[无权查看]");
			landload.setLandlordMobile("[无权查看]");
			landload.setLandlordName("[无权查看]");
		}

		bean.setData(landload);

		return new ModelAndView("jsonView", "json", bean);

	}

	// http://api.housemart.cn:8080/broker/residence/list.controller?secret=FDD12FBBB001D674
	@RequestMapping(value = "broker/residence/list.controller")
	public ModelAndView residenceList(@RequestParam String secret) {

		ResultBean bean = new ResultBean();

		int brokerId = authenticationService.decodeBrokerId(secret);
		
		List<ResidenceEntity> residences = residenceService
				.getBrokerResidences(brokerId);
		List<ResidenceBean> list = new ArrayList<ResidenceBean>();
		
		ResidenceBean rbean = new ResidenceBean();
		rbean.setId(0);
		rbean.setResidenceId(0);
		rbean.setResidenceName("全部");
		list.add(rbean);
		
		if (CollectionUtils.isNotEmpty(residences)) {
			for (ResidenceEntity entity : residences) {
				rbean = ResidenceUtils
						.residenceEntity2Bean(entity);
				list.add(rbean);
			}
		}

		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		bean.setData(list);

		return new ModelAndView("jsonView", "json", bean);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "broker/apns.controller")
	public ModelAndView apns(@RequestParam Integer brokerId,
			@RequestParam String message) {

		ResultBean bean = new ResultBean();

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("brokerID", brokerId);
		List<ClientRegisterEntity> clients = (List<ClientRegisterEntity>) clientRegisterDao
				.select("findClientRegisterList", map);

		ClientRegisterEntity clientRegister = null;

		if (clients.size() > 0) {
			clientRegister = clients.get(0);
			bean.setData(clientRegister.getClientID() + " - "
					+ clientRegister.getClientToken());
			try {
				JavaPNSProvider.pushMessageToAPNS(
						clientRegister.getClientToken(), message, 1, false);
				bean.setCode(ResutlCodeEnum.SUCCESS.getType());

			} catch (Exception e) {
				bean.setMsg(e.getMessage());
				bean.setCode(ResutlCodeEnum.ERROR.getType());
			}
		} else {
			bean.setCode(ResutlCodeEnum.ERROR.getType());
		}

		return new ModelAndView("jsonView", "json", bean);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "broker/house/edit.controller")
	public ModelAndView houseEdit(
			@RequestParam String appCode,
			@RequestParam String secret,
			@RequestParam(required = false) Integer id, 
			@RequestParam Integer residenceId,
			@RequestParam String detailName, @RequestParam String houseType,
			@RequestParam String buildingNo, @RequestParam(required = false) String cellNo,
			@RequestParam String propertyArea,
			@RequestParam(required = false) String occupiedArea, @RequestParam String price,
			@RequestParam String roomType, @RequestParam Integer floor,
			@RequestParam Integer decorating, @RequestParam Integer direction,
			@RequestParam Integer buildTime, @RequestParam String memo,
			@RequestParam(required = false) String dealTime, 
			@RequestParam(required = false) String equipments,
			@RequestParam String tags, @RequestParam Integer type) {

		ResultBean bean = new ResultBean();

		int brokerId = authenticationService.decodeBrokerId(secret);

		Date rentDealTime = null;
		
		String msg = "";
		
		Float propertyAreaVal = Float.parseFloat(propertyArea);
		Float occupiedAreaVal = null;
		Float priceVal = null;
		
		if (propertyAreaVal > 2000000000)
		{
			msg += "产证面积不能大于2000000000;";
		}
		
		if (occupiedArea != null && occupiedArea.length() > 0)
		{
			occupiedAreaVal = Float.parseFloat(occupiedArea);
			if (propertyAreaVal > 2000000000)
			{
				msg += "占地面积不能大于2000000000;";
			}
			
		}
		
		if (price != null && price.length() > 0)
		{
			priceVal = Float.parseFloat(price) * (type.equals(1) ? 10000 : 1);
			if (priceVal > 2000000000)
			{
				msg += (type.equals(1) ? "售价不能大于200000万;" : "租金不能大于2000000000元");
			}
		}
		
		if (msg.length() > 0)
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg(msg);
			
			return new ModelAndView("jsonView", "json", bean);
		}

		try
		{
			HouseEntity house;
			HouseSale sale;
			HouseRent rent;
	
			if (id == null) {
				house = new HouseEntity();
				sale = new HouseSale();
				rent = new HouseRent();
				house.setStatus(HouseEntity.StatusEnum.Default.status);
			} else {
				house = (HouseEntity) houseDao.load("loadHouse", id);
				sale = (HouseSale) houseDao.load("loadHouseSale", id);
				rent = (HouseRent) houseDao.load("loadHouseRent", id);
			}
	
			if (!house.getStatus().equals(HouseEntity.StatusEnum.Valid.status))
			{
				house.setResidenceId(residenceId);
				house.setDetailName(detailName);
				house.setHouseType(houseType);
				house.setBuildingNo(buildingNo);
				house.setCellNo(cellNo == null ? "" : cellNo);
				
				house.setPropertyArea(propertyAreaVal);
				house.setOccupiedArea(occupiedAreaVal);
				
				house.setRoomType(Integer.parseInt(roomType));
				house.setFloor(floor);
				house.setDecorating(decorating);
				house.setDirection(direction);
				house.setBuildDate(new Date(buildTime - 1900, 0, 1));
				if (type.equals(1))
				{
					house.setMemo(memo.equals("0") ? "" : memo);
				}
				else
				{
					house.setMemo(memo);
				}
				house.setSourceType(HouseEntity.SourceTypeEnum.external.value);
				house.setCreator(brokerId);
		
				if (type.equals(1)) {
					// 售房
					sale.setPriceValue(priceVal);
					sale.setTagList(tags);
				} else {
					// 租房
					if (dealTime != null)
					{
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						rentDealTime = format.parse(dealTime);
						rent.setDealTime(rentDealTime);
					}
					rent.setPriceValue(priceVal);
					rent.setTagList(tags);
					rent.setEquipmentList(equipments);
					
				}
		
				if (id == null) {
					
					if (appCode.equals("iosClient"))
					{
						house.setClientType(HouseEntity.ClientTypeEnum.ios.value);
					}
					else 
					{
						house.setClientType(HouseEntity.ClientTypeEnum.android.value);
					}
					
					id = houseDao.add("addHouse", house);
					if (type.equals(1)) {
						sale.setId(id);
						sale.setSaleStatus(1);
						sale.setSource(2);
						houseDao.add("addHouseSale", sale);
					} else {
						rent.setId(id);
						rent.setRentStatus(1);
						houseDao.add("addHouseRent", rent);
					}
				} else {
					houseDao.update("updateHouse", house);
					if (type.equals(1)) {
						// 售房
						houseDao.update("updateHouseSale", sale);
					}
					else
					{
						houseDao.update("updateHouseRent", rent);
					}
				}
				
				bean.setCode(ResutlCodeEnum.SUCCESS.getType());
				
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put("houseId", id);
				bean.setData(map);
			}
			else
			{
				bean.setCode(ResutlCodeEnum.ERROR.getType());
				bean.setMsg("无法编辑上架房源，请先下架");
			}
		}
		catch (Exception ex)
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("格式错误，请检查信息是否正确");
			
			log.error(ex.getMessage(), ex);
		}

		return new ModelAndView("jsonView", "json", bean);

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "broker/house/detail.controller")
	public ModelAndView houseDetail(@RequestParam String secret,
			@RequestParam Integer id) {

		ResultBean bean = new ResultBean();

		Map<String, String> brokerInfo = authenticationService.decodeSecretKey(secret);
		
		int brokerId = Integer.parseInt(brokerInfo.get("brokerId"));
		String clientUId = brokerInfo.get("clientUId");
		
		HouseEntity house = (HouseEntity) houseDao.load("loadHouse", id);
		HouseDetailBean houseDetail = house.getHouseDetailBean();
		
		if (house.getCreator() != null && house.getCreator().equals(brokerId))
		{
			HouseSale sale = (HouseSale) houseDao.load("loadHouseSale", id);
			HouseRent rent = (HouseRent) houseDao.load("loadHouseRent", id);
			ResidenceEntity residence = residenceService.loadResidence(house.getResidenceId());
			
			house.setPrice(sale != null ? sale.getPriceValue().intValue() : 0);
			house.setRentPrice(rent != null ? rent.getPriceValue().intValue() : 0);
			house.setResidenceName(residence.getResidenceName());
			house.setRegionName(residence.getRegionName());
			house.setPlateName(residence.getPlateName());
			house.setAddress(residence.getAddress() + " " + house.getBuildingNo() + "号");
			
			Map<String, Object> houseMap = new HashMap<String, Object>();
			houseMap.put("id", house.getId());
			houseMap.put("residenceId", house.getResidenceId());
			houseMap.put("residenceName", house.getResidenceName());
			houseMap.put("address", house.getAddress());
			houseMap.put("detailName", house.getDetailName());
			houseMap.put("houseType", house.getHouseType());
			houseMap.put("buildingNo", house.getBuildingNo());
			houseMap.put("cellNo", house.getCellNo());
			houseMap.put("propertyArea", HouseEntity.formatFloat(house.getPropertyArea()));
			houseMap.put("occupiedArea", HouseEntity.formatFloat(house.getOccupiedArea()));
			String roomType = "0000" + house.getRoomType();
			houseMap.put("roomType", roomType.substring(roomType.length() - 4));
			houseMap.put("floor", house.getFloor());
			houseMap.put("decorating", house.getDecorating());
			houseMap.put("direction", house.getDirection());
			Date buildDate = house.getBuildDate();
			houseMap.put("buildTime", buildDate.getYear() + 1900);
			
			if (sale != null)
			{
				houseMap.put("salePrice", HouseEntity.formatFloat(sale.getPriceValue() / 10000));
				houseMap.put("tags", sale.getTagList());
				houseMap.put("type", 1);
				if (house.getMemo() == null)
				{
					houseMap.put("saleMemo", "0");
				}
				else
				{
					houseMap.put("saleMemo", house.getMemo().length() == 0 ? "0" : house.getMemo());
				}
				
				houseMap.put("msiteTitle", houseDetail.getResidenceName() + "," + houseDetail.getPrice());
				
			}
			if (rent != null)
			{
				houseMap.put("rentPrice", HouseEntity.formatFloat(rent.getPriceValue()));
				houseMap.put("tags", rent.getTagList());
				houseMap.put("equipments", rent.getEquipmentList());
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				houseMap.put("dealTime", rent.getDealTime() != null ? df.format(rent.getDealTime()) : "");
				houseMap.put("type", 2);
				houseMap.put("rentMemo", house.getMemo());
				
				houseMap.put("msiteTitle", houseDetail.getResidenceName() + "," + houseDetail.getRentPrice());
			}
			
			houseMap.put("msiteDesc", houseDetail.getRoomType() + "\n" + 
					houseDetail.getArea() + "\n" + 
					houseDetail.getPlateName());
			String mHouseDetailLink = resourceProvider.getValue("housemart.msite.host") + 
		    		MessageFormat.format(resourceProvider.getValue("housemart.msite.house.detail"), 
		            house.getId().toString(), 
		            sale != null ? "sale" : "rent");
			houseMap.put("msiteUrl", mHouseDetailLink);
	
			houseMap.put("picURL", houseService.getHousePics(id, clientUId));
			
			bean.setData(houseMap);
	
			bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		}
		else
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("非账号所属房源");
		}

		return new ModelAndView("jsonView", "json", bean);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "broker/house/listSummary.controller")
	public ModelAndView houseListSummary(@RequestParam String secret) {
		
		ResultBean bean = new ResultBean();
		
		int brokerId = authenticationService.decodeBrokerId(secret);
		
		// 上架房源数
	    Map<Object,Object> mapCount = new HashMap<Object,Object>();
	    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
	    mapCount.put("creator", brokerId);
	    mapCount.put("status", HouseEntity.StatusEnum.Valid.status);
	    mapCount.put("onboard", true);
	    mapCount.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
	    
	    Integer onboardCount = houseDao.count("countHouseExt", mapCount);
	    onboardCount = (onboardCount == null ? 0 : onboardCount);
	    
	    // 审核房源数
	    mapCount = new HashMap<Object,Object>();
	    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
	    mapCount.put("creator", brokerId);
	    mapCount.put("status", HouseEntity.StatusEnum.Default.status);
	    mapCount.put("auditResult",
	        HouseAuditHistoryEntity.ResultEnum.Default.getValue());
	    mapCount.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
	    
	    Integer auditCount = houseDao.count("countHouseExt", mapCount);
	    auditCount = (auditCount == null ? 0 : auditCount);
	    
	    // 拒绝房源数
	    mapCount = new HashMap<Object,Object>();
	    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
	    mapCount.put("creator", brokerId);
	    mapCount.put("status", HouseEntity.StatusEnum.InvalidExt.status);
	    mapCount.put("auditResult",
	        HouseAuditHistoryEntity.ResultEnum.Reject.getValue());
	    Integer rejectCount = houseDao.count("countHouseExt", mapCount);
	    rejectCount = (rejectCount == null ? 0 : rejectCount);
	    
	    // 下架房源数
	    mapCount = new HashMap<Object,Object>();
	    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
	    mapCount.put("creator", brokerId);
	    mapCount.put("status", HouseEntity.StatusEnum.OffBoard.status);
	    Integer offboardCount = houseDao.count("countHouseExt", mapCount);
	    offboardCount = (offboardCount == null ? 0 : offboardCount);
	    
	    // 未提交审核房源数
	    mapCount = new HashMap<Object,Object>();
	    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
	    mapCount.put("creator", brokerId);
	    mapCount.put("status", HouseEntity.StatusEnum.Default.status);
	    mapCount.put("auditResultNull", true);
	    mapCount.put("auditTypeNull", null);
	    Integer notRequestAuditCount = houseDao.count("countHouseExt", mapCount);
	    notRequestAuditCount = (notRequestAuditCount == null ? 0
	        : notRequestAuditCount);
		
	    Map<Integer, Object> summaryMap = new HashMap<Integer, Object>();
	    summaryMap.put(1, onboardCount);
	    summaryMap.put(2, auditCount);
	    summaryMap.put(3, rejectCount);
	    summaryMap.put(4, offboardCount);
	    summaryMap.put(5, notRequestAuditCount);
	    
	    bean.setData(summaryMap);
		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
	    
		return new ModelAndView("jsonView", "json", bean);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "broker/house/list.controller")
	public ModelAndView houseList(@RequestParam String secret,
			@RequestParam(required = false) Integer residenceId,
			@RequestParam(required = false) Integer page, 
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) String order, 
			@RequestParam(required = false) Integer tabIndex,
			@RequestParam(required = false) Integer saleRent) {

		ResultBean bean = new ResultBean();

		int brokerId = authenticationService.decodeBrokerId(secret);

		tabIndex = tabIndex == null ? 1 : tabIndex;
		saleRent = saleRent == null ? 1 : saleRent;
		page = page == null ? 0 : page;
		pageSize = pageSize == null ? 50 : pageSize;

		Map<Object, Object> map = new HashMap<Object, Object>();
		if (residenceId != null && !residenceId.equals(0))
		{
			map.put("residenceId", residenceId);
		}
		map.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
		map.put("creator", brokerId);

		if (tabIndex == 1) {
			// 上架
			map.put("status", HouseEntity.StatusEnum.Valid.status);
			map.put("onboard", true);
			map.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
		} else if (tabIndex == 2) {
			// 审核中, status = 0
			map.put("status", HouseEntity.StatusEnum.Default.status);
			map.put("auditResult",
					HouseAuditHistoryEntity.ResultEnum.Default.getValue());
			map.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
		} else if (tabIndex == 3) {
			// 拒绝
			map.put("status", HouseEntity.StatusEnum.InvalidExt.status);
			map.put("auditResult",
					HouseAuditHistoryEntity.ResultEnum.Reject.getValue());
			map.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
		} else if (tabIndex == 4) {
			// 下架
			map.put("status", HouseEntity.StatusEnum.OffBoard.status);
			map.put("auditType", AuditTypeEnum.OffboardAudit.getValue());
		} else if (tabIndex == 5) {
			// 未提交审核
			map.put("status", HouseEntity.StatusEnum.Default.status);
			map.put("auditResultNull", true);
			map.put("auditTypeNull", true);
		}

		if (saleRent.equals(2)) {
			map.put("rentStatus", 1);
		} else if (saleRent.equals(1)) {
			map.put("saleStatus", 1);
		}

		map.put("orderByClause", "RES.PinyinName ASC, H.UpdateTime DESC");
		map.put("tabIndex", tabIndex);

		Integer totalCount = houseDao.count("countHouseExt", map);

		// for pagination query
		map.put("skip", page);
		map.put("count", pageSize);

		List<HouseEntity> houseList = (List<HouseEntity>) houseDao.select(
				"findHouseExtList", map);

		List<HouseSaleBean> saleList = new ArrayList<HouseSaleBean>();
		List<HouseRentBean> rentList = new ArrayList<HouseRentBean>();

		for (HouseEntity houseInfo : houseList) {
			String address = houseInfo.getResidenceName();
			
			if (houseInfo.getBuildingNo() != null && houseInfo.getBuildingNo().length() > 0)
			{
				address += " " + houseInfo.getBuildingNo() + "栋（号）";
			}
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			String onboardTimeStr = "";
			String applyTimeStr = "";
			String updateTimeStr = "";
			
			if (houseInfo.getStatus().equals(HouseEntity.StatusEnum.Valid.status))
			{
				onboardTimeStr = df.format(houseInfo.getOnboardTime());
			}
			
			applyTimeStr = df.format(houseInfo.getApplyTime());
			
			updateTimeStr = df.format(houseInfo.getUpdateTime());
			
			if (saleRent.equals(1)) {
				HouseSaleBean houseSale = houseInfo.getHouseSaleBean();
				houseSale.setResidenceName(address);
				houseSale.setOnboardTimeString(onboardTimeStr);
				houseSale.setApplyTimeString(applyTimeStr);
				houseSale.setAuditComments(houseInfo.getAuditComments());
				houseSale.setUpdateTimeString(updateTimeStr);
				
				saleList.add(houseSale);
			} else if (saleRent.equals(2)) {
				HouseRentBean houseRent = houseInfo.getHouseRentBean();
				houseRent.setResidenceName(address);
				houseRent.setOnboardTimeString(onboardTimeStr);
				houseRent.setApplyTimeString(applyTimeStr);
				houseRent.setAuditComments(houseInfo.getAuditComments());
				houseRent.setUpdateTimeString(updateTimeStr);
				houseRent.setRentPrice(houseRent.getPrice());
				
				rentList.add(houseRent);
			}
		}

		bean.setCode(ResutlCodeEnum.SUCCESS.getType());

		if (saleRent.equals(1)) {
			bean.setData(saleList);
		} else if (saleRent.equals(2)) {
			bean.setData(rentList);
		}

		return new ModelAndView("jsonView", "json", bean);
	}

	@RequestMapping(value = "broker/house/delete.controller")
	public ModelAndView deleteHouse(@RequestParam String secret,
			@RequestParam("ids") String ids) {

		ResultBean bean = new ResultBean();

		int brokerId = authenticationService.decodeBrokerId(secret);

		String url = resourceProvider.getValue("housemart.url.house.delete") + 
				"?ids=" + ids + "&brokerId=" + brokerId;
		
		try
		{
			HttpGet httpGet = new HttpGet(url);
			HttpUtils.requestJson(httpGet);
		}
		catch(Exception ex)
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("删除失败");
			log.error(ex.getMessage(), ex);
		}

		bean.setCode(ResutlCodeEnum.SUCCESS.getType());

		return new ModelAndView("jsonView", "json", bean);
	}

	@RequestMapping(value = "broker/house/deactive.controller")
	public ModelAndView deactiveHouse(@RequestParam String secret,
			@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "comments", required = false) String comments) {

		ResultBean bean = new ResultBean();

		comments = (comments == null ? "" : comments);
		
		int brokerId = authenticationService.decodeBrokerId(secret);

		String url = resourceProvider.getValue("housemart.url.house.deactive") + 
				"?ids=" + ids + "&brokerId=" + brokerId + "&comments=" + comments;
		
		try
		{
			HttpGet httpGet = new HttpGet(url);
			HttpUtils.requestJson(httpGet);
		}
		catch(Exception ex)
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("下架失败");
			log.error(ex.getMessage(), ex);
		}

		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		return new ModelAndView("jsonView", "json", bean);
	}

	@RequestMapping(value = "broker/house/apply.controller")
	public ModelAndView applyHouse(@RequestParam String secret,
			@RequestParam("ids") String ids) {

		ResultBean bean = new ResultBean();


		int brokerId = authenticationService.decodeBrokerId(secret);

		String url = resourceProvider.getValue("housemart.url.house.apply") + 
				"?ids=" + ids + "&brokerId=" + brokerId;
		
		try
		{
			HttpGet httpGet = new HttpGet(url);
			HttpUtils.requestJson(httpGet);
		}
		catch(Exception ex)
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("申请失败");
			log.error(ex.getMessage(), ex);
		}


		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		return new ModelAndView("jsonView", "json", bean);
	}

	@RequestMapping(value = "broker/house/picUpload.controller")
	public ModelAndView housePicUpload(@RequestParam String secret,
			@RequestParam MultipartFile file, @RequestParam Integer houseId,
			@RequestParam Integer picType) {

		ResultBean bean = new ResultBean();
		String url = resourceProvider.getValue("housemart.url.house.uploadPic");
		
		try
		{
			HttpPost httpPost = new HttpPost(url);
			final String fileName = "/upload/" + Calendar.getInstance().getTime().getTime() + secret + houseId + ".jpg";
			String fileFullPath = "/mnt" + fileName;
			FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));
			File tempFile = new File(fileFullPath);
			
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("houseId", new StringBody(houseId.toString()));
			reqEntity.addPart("picType", new StringBody(picType.toString()));
	    	reqEntity.addPart("imageFile", new FileBody(tempFile));
	    	httpPost.setEntity(reqEntity); 
			AjaxResultBean ajax = HttpUtils.requestJson(httpPost);
			
			int code = ajax.getCode();
			
			if (code == 1)
			{
				List<Map<String,Object>> bizData = (List<Map<String,Object>>)ajax.getBizData();
				if (bizData.size() > 0)
				{
					bean.setData(bizData.get(0));
					bean.setCode(ResutlCodeEnum.SUCCESS.getType());
				}
				else
				{
					bean.setCode(ResutlCodeEnum.ERROR.getType());
					bean.setMsg("上传失败");
				}
				
			}
			else
			{
				bean.setCode(ResutlCodeEnum.ERROR.getType());
				bean.setMsg("上传失败");
			}
		}
		catch(Exception ex)
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("上传失败");
			log.error(ex.getMessage(), ex);
		}
		
		return new ModelAndView("jsonView", "json", bean);
	}
	
	@RequestMapping(value = "broker/house/picDelete.controller")
	public ModelAndView housePicDelete(@RequestParam String secret,
			@RequestParam Integer houseId,
			@RequestParam String picIds) {

		ResultBean bean = new ResultBean();
		String url = resourceProvider.getValue("housemart.url.house.deletePic") + 
				"?houseId=" + houseId + "&picIds=" + picIds;
		
		try
		{
			HttpGet httpGet = new HttpGet(url);
			HttpUtils.requestJson(httpGet);
		}
		catch(Exception ex)
		{
			bean.setCode(ResutlCodeEnum.ERROR.getType());
			bean.setMsg("删除失败");
			log.error(ex.getMessage(), ex);
		}

		bean.setCode(ResutlCodeEnum.SUCCESS.getType());
		return new ModelAndView("jsonView", "json", bean);
	}

}
