/**
 * Created on 2012-12-2
 * 
 */
package org.housemart.server.dao.entities;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.housemart.framework.utils.BundleUtils;
import org.housemart.server.beans.LandlordInfo;
import org.housemart.server.beans.house.HouseDetailBean;
import org.housemart.server.beans.house.HouseRentBean;
import org.housemart.server.beans.house.HouseRentEquipment;
import org.housemart.server.beans.house.HouseSaleBean;
import org.housemart.server.util.CommonUtils;
import org.housemart.server.util.HouseUtils;

/**
 * @author pqin
 */
public class HouseEntity {
	Log log = LogFactory.getLog(HouseEntity.class);

	private Integer id = 0;
	private Integer status = 0;
	private String title = ""; // 售房标题
	private String rentTitle = ""; // 租房标题
	private Integer cityId = 0; // 城市ID
	private Integer regionId = 0; // 行政区ID
	private Integer plateId = 0; // 板块ID
	private Integer residenceId = 0; // 小区ID
	private String residenceName = ""; // 小区名称
	private String address = ""; // 地址
	private String soldAddress = ""; // 地址
	private String buildingNo = ""; // 栋座信息
	private String cellNo = ""; // 单元号
	private String picURL = ""; // 图片地址列表，以逗号分隔
	private String residencePicURL = ""; // 小区图片地址列表，以逗号分隔
	private Integer price = 0; // 售价
	private Integer avg = 0; // 单价
	private Integer rentPrice = 0; // 租金
	private String equipment = ""; // 租房设备
	private Integer decorating = 0; // 装修
	private Float area = (float) 0; // 面积
	private Integer roomType = 0; // 房型
	private Integer direction = 0; // 朝向
	private String type = ""; // 类型
	private Integer floor = 0; // 楼层
	private Integer floorLevel = 0; // 楼层
	private Date buildDate = CommonUtils.getDefaultDate(); // 年代
	private Boolean isEmergent = false; // 是否急售
	private Boolean isRecommend = false; // 是否推荐
	private Integer viewHouseType = 0; // 看房方式
	private String houseMemo = ""; // 房源说明
	private Integer sourceType; // 来源
	private String memo = ""; // 售房说明
	private String rentMemo = ""; // 说明
	private String brokerMobile = ""; // 经纪人联系方式
	private String brokerName = ""; // 经纪人联系方式
	private Integer brokerId = 0; // 经纪人联系方式
	private Boolean isFollow = false; // 当前用户是否关注
	private Date createTime = CommonUtils.getDefaultDate();
	private Double lat = (double) 0; // 纬度
	private Double lng = (double) 0; // 经度
	private Double distance = (double) 0; // 中心点距离

	private Date onboardTime = CommonUtils.getDefaultDate(); // 审核通过时间
	private Date applyTime = CommonUtils.getDefaultDate(); // 提交审核时间
	private String auditComments = ""; // 审核备注

	private int interaction = 0; // 0有交互权，1无交互权
	private String plateName = "";
	private String regionName = "";

	private String contactName;
	private String contactMobile;
	private String contactMemo;
	private Integer contactCommitter;

	private String detailName;
	private String houseType;
	private Float propertyArea;
	private Float occupiedArea;
	private Integer creator;

	private Date updateTime;

	// Rent
	private Integer rentStatus = 0;
	private Boolean combinedRent = false; // 是否合租
	private Integer fansCount = 0; // 关注
	private Integer askedCount = 0; // 问询次数
	private Boolean isEmergentRent = false; // 是否急租
	private Boolean isRecommendRent = false; // 是否急租
	private Boolean isNew = false; // 是否新
	private Date dealTimeRent = CommonUtils.getDefaultDate(); // 出租时间
	private Float dealPriceRent = (float) 0; // 出租价格
	private String rentTagList = "";

	// Sale
	private Integer saleStatus = 0;
	private Boolean isHot = false; // 是否热门
	private Date dealTime = CommonUtils.getDefaultDate(); // 交易时间
	private Integer dealPrice = 0; // 交易价格
	private String saleTagList = "";

	DecimalFormat df_decimal_1 = new DecimalFormat("0.0");
	DecimalFormat df_decimal_2 = new DecimalFormat("0.00");

	public static enum FIELD {
		id, status, title, rentTitle, cityId, regionId, plateId, residenceId, residenceName, address, soldAddress, buildingNo, cellNo, picURL, residencePicURL, price, avg, rentPrice, equipment, decorating, area, roomType, direction, type, floor, floorLevel, buildDate, isEmergent, isRecommend, houseMemo, sourceType, viewHouseType, memo, rentMemo, brokerMobile, brokerName, brokerId, isFollow, createTime, rentStatus, combinedRent, fansCount, askedCount, isEmergentRent, isRecommendRent, isNew, dealTimeRent, dealPriceRent, saleStatus, isHot, dealTime, dealPrice, lat, lng, onboardTime, interaction, regionName, plateName, contactName, contactMobile, contactMemo, contactCommitter;
	}

	public static enum StatusEnum {
		Default(0), Invalid(0), Valid(1), InvalidExt(2), Deleted(-1), OffBoard(-2);
		public int status;

		StatusEnum(int status) {
			this.status = status;
		}
	}

	public static enum SaleStatusEnum {
		Saling(1), NotSale(2), Sold(3);
		public int saleStatus;

		SaleStatusEnum(int saleStatus) {
			this.saleStatus = saleStatus;
		}
	}

	public static enum RentStatusEnum {
		Renting(1), NotRent(2), Rented(3);
		public int value;

		RentStatusEnum(int rentStatus) {
			this.value = rentStatus;
		}
	}

	public static enum SourceTypeEnum {
		internal(1), customerservice(2), external(3);
		public Integer value;

		SourceTypeEnum(Integer sourceType) {
			this.value = sourceType;
		}
	}

	public static enum InteractionEnum {
		Without(0), With(1);
		public int value;

		InteractionEnum(int interaction) {
			this.value = interaction;
		}
	}

	private static final String PIC_SPLITTER = "12qwaszx,";

	public static HouseEntity fromDocument(Document doc) {
		HouseEntity entity = null;
		if (doc != null) {
			entity = new HouseEntity();

			if (doc.get(FIELD.address.toString()) != null) {
				entity.setAddress(doc.get(FIELD.address.toString()));
			} else {
				entity.setAddress("");
			}

			if (doc.get(FIELD.soldAddress.toString()) != null) {
				entity.setSoldAddress(doc.get(FIELD.soldAddress.toString()));
			} else {
				entity.setSoldAddress("");
			}

			if (doc.get(FIELD.buildingNo.toString()) != null) {
				entity.setBuildingNo(doc.get(FIELD.buildingNo.toString()));
			} else {
				entity.setBuildingNo("");
			}

			if (doc.get(FIELD.cellNo.toString()) != null) {
				entity.setCellNo(doc.get(FIELD.cellNo.toString()));
			} else {
				entity.setCellNo("");
			}

			if (StringUtils.isNotEmpty(doc.get(FIELD.area.toString()))) entity.setArea(Float.valueOf(doc.get(FIELD.area.toString())));
			else entity.setArea((float) 0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.askedCount.toString()))) entity.setAskedCount(Integer.valueOf(doc
					.get(FIELD.askedCount.toString())));
			else entity.setAskedCount(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.avg.toString()))) entity.setAvg(Integer.valueOf(doc.get(FIELD.avg.toString())));
			else entity.setAvg(0);

			if (doc.get(FIELD.brokerMobile.toString()) != null) entity.setBrokerMobile(doc.get(FIELD.brokerMobile.toString()));
			else entity.setBrokerMobile("");

			if (doc.get(FIELD.brokerName.toString()) != null) entity.setBrokerName(doc.get(FIELD.brokerName.toString()));
			else entity.setBrokerName("");

			if (StringUtils.isNotBlank(doc.get(FIELD.brokerId.toString()))) entity.setBrokerId(Integer.valueOf(doc.get(FIELD.brokerId
					.toString())));
			else entity.setBrokerId(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.buildDate.toString()))) entity.setBuildDate(new Date(Long.valueOf(doc
					.get(FIELD.buildDate.toString()))));
			else entity.setBuildDate(CommonUtils.getDefaultDate());

			if (StringUtils.isNotEmpty(doc.get(FIELD.cityId.toString()))) entity.setCityId(Integer.valueOf(doc.get(FIELD.cityId
					.toString())));
			else entity.setCityId(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.combinedRent.toString()))) entity.setCombinedRent(Boolean.valueOf(doc
					.get(FIELD.combinedRent.toString())));
			else entity.setCombinedRent(false);

			if (StringUtils.isNotEmpty(doc.get(FIELD.createTime.toString()))) entity.setCreateTime(new Date(Long.valueOf(doc
					.get(FIELD.createTime.toString()))));
			else entity.setCreateTime(CommonUtils.getDefaultDate());

			if (StringUtils.isNotEmpty(doc.get(FIELD.direction.toString()))) entity.setDirection(Integer.valueOf(doc.get(FIELD.direction
					.toString())));
			else entity.setDirection(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.dealPrice.toString()))) entity.setDealPrice(Integer.valueOf(doc.get(FIELD.dealPrice
					.toString())));
			else entity.setDealPrice(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.dealPriceRent.toString()))) entity.setDealPriceRent(Float.valueOf(doc
					.get(FIELD.dealPriceRent.toString())));
			else entity.setDealPriceRent((float) 0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.dealTime.toString()))) entity.setDealTime(new Date(Long.valueOf(doc
					.get(FIELD.dealTime.toString()))));
			else entity.setDealTime(CommonUtils.getDefaultDate());

			if (StringUtils.isNotEmpty(doc.get(FIELD.onboardTime.toString()))) entity.setOnboardTime(new Date(Long.valueOf(doc
					.get(FIELD.onboardTime.toString()))));
			else entity.setOnboardTime(CommonUtils.getDefaultDate());

			if (StringUtils.isNotEmpty(doc.get(FIELD.dealTimeRent.toString()))) entity.setDealTimeRent(new Date(Long.valueOf(doc
					.get(FIELD.dealTimeRent.toString()))));
			else entity.setDealTimeRent(CommonUtils.getDefaultDate());

			if (StringUtils.isNotEmpty(doc.get(FIELD.decorating.toString()))) entity.setDecorating(Integer.valueOf(doc
					.get(FIELD.decorating.toString())));
			else entity.setDecorating(0);

			if (doc.get(FIELD.equipment.toString()) != null) entity.setEquipment(doc.get(FIELD.equipment.toString()));
			else entity.setEquipment("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.fansCount.toString()))) entity.setFansCount(Integer.valueOf(doc.get(FIELD.fansCount
					.toString())));
			else entity.setFansCount(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.floor.toString()))) entity
			.setFloor(Integer.valueOf(doc.get(FIELD.floor.toString())));
			else entity.setFloor(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.floorLevel.toString()))) {
				entity.setFloorLevel(Integer.valueOf(doc.get(FIELD.floorLevel.toString())));
			} else entity.setFloorLevel(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.id.toString()))) entity.setId(Integer.valueOf(doc.get(FIELD.id.toString())));
			else entity.setId(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.isEmergent.toString()))) entity.setIsEmergent(Boolean.valueOf(doc
					.get(FIELD.isEmergent.toString())));
			else entity.setIsEmergent(false);

			if (StringUtils.isNotEmpty(doc.get(FIELD.isEmergentRent.toString()))) entity.setIsEmergentRent(Boolean.valueOf(doc
					.get(FIELD.isEmergentRent.toString())));
			else entity.setIsEmergentRent(false);

			if (StringUtils.isNotEmpty(doc.get(FIELD.isRecommendRent.toString()))) entity.setIsRecommendRent(Boolean.valueOf(doc
					.get(FIELD.isRecommendRent.toString())));
			else entity.setIsRecommendRent(false);

			if (StringUtils.isNotEmpty(doc.get(FIELD.isFollow.toString()))) entity.setIsFollow(Boolean.valueOf(doc.get(FIELD.isFollow
					.toString())));
			else entity.setIsFollow(false);

			if (StringUtils.isNotEmpty(doc.get(FIELD.isHot.toString()))) entity
			.setIsHot(Boolean.valueOf(doc.get(FIELD.isHot.toString())));
			else entity.setIsHot(false);

			if (StringUtils.isNotEmpty(doc.get(FIELD.isNew.toString()))) entity
			.setIsNew(Boolean.valueOf(doc.get(FIELD.isNew.toString())));
			else entity.setIsNew(false);

			if (StringUtils.isNotEmpty(doc.get(FIELD.isRecommend.toString()))) entity.setIsRecommend(Boolean.valueOf(doc
					.get(FIELD.isRecommend.toString())));
			else entity.setIsRecommend(false);

			if (doc.get(FIELD.memo.toString()) != null) entity.setMemo(doc.get(FIELD.memo.toString()));
			else entity.setMemo("");

			if (doc.get(FIELD.rentMemo.toString()) != null) entity.setRentMemo(doc.get(FIELD.rentMemo.toString()));
			else entity.setRentMemo("");

			if (doc.get(FIELD.picURL.toString()) != null) entity.setPicURL(doc.get(FIELD.picURL.toString()));
			else entity.setPicURL("");

			if (doc.get(FIELD.residencePicURL.toString()) != null) entity.setResidencePicURL(doc.get(FIELD.residencePicURL.toString()));
			else entity.setResidencePicURL("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.plateId.toString()))) entity.setPlateId(Integer.valueOf(doc.get(FIELD.plateId
					.toString())));
			else entity.setPlateId(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.price.toString()))) entity
			.setPrice(Integer.valueOf(doc.get(FIELD.price.toString())));
			else entity.setPrice(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.regionId.toString()))) entity.setRegionId(Integer.valueOf(doc.get(FIELD.regionId
					.toString())));
			else entity.setRegionId(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.rentPrice.toString()))) entity.setRentPrice(Integer.valueOf(doc.get(FIELD.rentPrice
					.toString())));
			else entity.setRentPrice(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.rentStatus.toString()))) entity.setRentStatus(Integer.valueOf(doc
					.get(FIELD.rentStatus.toString())));
			else entity.setRentStatus(0);

			if (doc.get(FIELD.rentTitle.toString()) != null) entity.setRentTitle(doc.get(FIELD.rentTitle.toString()));
			else entity.setRentTitle("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.residenceId.toString()))) entity.setResidenceId(Integer.valueOf(doc
					.get(FIELD.residenceId.toString())));
			else entity.setResidenceId(0);

			if (doc.get(FIELD.residenceName.toString()) != null) entity.setResidenceName(doc.get(FIELD.residenceName.toString()));
			else entity.setResidenceName("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.roomType.toString()))) entity.setRoomType(Integer.valueOf(doc.get(FIELD.roomType
					.toString())));
			else entity.setRoomType(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.saleStatus.toString()))) entity.setSaleStatus(Integer.valueOf(doc
					.get(FIELD.saleStatus.toString())));
			else entity.setSaleStatus(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.status.toString()))) entity.setStatus(Integer.valueOf(doc.get(FIELD.status
					.toString())));
			else entity.setStatus(0);

			if (doc.get(FIELD.title.toString()) != null) entity.setTitle(doc.get(FIELD.title.toString()));
			else entity.setTitle("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.type.toString()))) entity.setType(doc.get(FIELD.type.toString()));
			else entity.setType("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.houseMemo.toString()))) {
				entity.setHouseMemo(doc.get(FIELD.houseMemo.toString()));
			} else entity.setHouseMemo("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.sourceType.toString()))) {
				entity.setSourceType(Integer.valueOf(doc.get(FIELD.sourceType.toString())));
			} else entity.setSourceType(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.viewHouseType.toString()))) entity.setViewHouseType(Integer.valueOf(doc
					.get(FIELD.viewHouseType.toString())));
			else entity.setViewHouseType(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.interaction.toString()))) entity.setInteraction(Integer.valueOf(doc
					.get(FIELD.interaction.toString())));
			else entity.setInteraction(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.lat.toString()))) entity.setLat(Double.valueOf(doc.get(FIELD.lat.toString())));
			else entity.setLat(0.0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.lng.toString()))) entity.setLng(Double.valueOf(doc.get(FIELD.lng.toString())));
			else entity.setLng(0.0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.regionName.toString()))) entity.setRegionName(doc.get(FIELD.regionName.toString()));
			else entity.setRegionName("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.plateName.toString()))) entity.setPlateName(doc.get(FIELD.plateName.toString()));
			else entity.setPlateName("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.contactCommitter.toString()))) {
				entity.setContactCommitter(Integer.valueOf(doc.get(FIELD.contactCommitter.toString())));
			} else entity.setContactCommitter(0);

			if (StringUtils.isNotEmpty(doc.get(FIELD.contactMobile.toString()))) {
				entity.setContactMobile(doc.get(FIELD.contactMobile.toString()));
			} else entity.setContactMobile("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.contactMemo.toString()))) {
				entity.setContactMemo(doc.get(FIELD.contactMemo.toString()));
			} else entity.setContactMemo("");

			if (StringUtils.isNotEmpty(doc.get(FIELD.contactName.toString()))) {
				entity.setContactName(doc.get(FIELD.contactName.toString()));
			} else entity.setContactName("");

		}
		return entity;
	}

	public Document toDocument() {
		Document doc = new Document();
		if (getId() != null) {
			Field id = new Field(FIELD.id.toString(), getId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED);
			doc.add(id);
		}
		if (getStatus() != null) {
			Field status = new Field(FIELD.status.toString(), getStatus().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(status);
		}
		if (StringUtils.isNotEmpty(getTitle())) {
			String tags = HouseUtils.getTitle(getTitle());
			Field title = new Field(FIELD.title.toString(), tags, Field.Store.YES, Field.Index.ANALYZED);
			doc.add(title);
		}
		if (StringUtils.isNotEmpty(getRentTitle())) {
			String tags = HouseUtils.getTitle(getRentTitle());
			Field rentTitle = new Field(FIELD.rentTitle.toString(), tags, Field.Store.YES, Field.Index.ANALYZED);
			doc.add(rentTitle);
		}
		if (getCityId() != null) {
			NumericField cityId = new NumericField(FIELD.cityId.toString(), Field.Store.YES, true).setIntValue(getCityId());
			doc.add(cityId);
		}
		if (getRegionId() != null) {
			NumericField regionId = new NumericField(FIELD.regionId.toString(), Field.Store.YES, true).setIntValue(getRegionId());
			doc.add(regionId);
		}
		if (getPlateId() != null) {
			NumericField plateId = new NumericField(FIELD.plateId.toString(), Field.Store.YES, true).setIntValue(getPlateId());
			doc.add(plateId);
		}
		if (getResidenceId() != null) {
			NumericField residenceId = new NumericField(FIELD.residenceId.toString(), Field.Store.YES, true)
			.setIntValue(getResidenceId());
			doc.add(residenceId);
		}
		if (StringUtils.isNotEmpty(getResidenceName())) {
			Field residenceName = new Field(FIELD.residenceName.toString(), getResidenceName(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(residenceName);
		}
		if (StringUtils.isNotEmpty(getAddress())) {
			Field address = new Field(FIELD.address.toString(), getAddress(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(address);
		}
		if (StringUtils.isNotEmpty(getSoldAddress())) {
			Field soldAddress = new Field(FIELD.soldAddress.toString(), getSoldAddress(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(soldAddress);
		}
		if (StringUtils.isNotEmpty(getBuildingNo())) {
			Field buildingNo = new Field(FIELD.buildingNo.toString(), getBuildingNo(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(buildingNo);
		}
		if (StringUtils.isNotEmpty(getCellNo())) {
			Field cellNo = new Field(FIELD.cellNo.toString(), getCellNo(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(cellNo);
		}
		if (StringUtils.isNotEmpty(getPicURL())) {
			Field picURL = new Field(FIELD.picURL.toString(), getPicURL(), Field.Store.YES, Field.Index.NO);
			doc.add(picURL);
		}
		if (StringUtils.isNotEmpty(getResidencePicURL())) {
			Field residencePicURL = new Field(FIELD.residencePicURL.toString(), getResidencePicURL(), Field.Store.YES, Field.Index.NO);
			doc.add(residencePicURL);
		}
		if (getPrice() != null) {
			NumericField price = new NumericField(FIELD.price.toString(), Field.Store.YES, true).setIntValue(getPrice());
			doc.add(price);
		}
		if (getArea() != null) {
			NumericField avg = new NumericField(FIELD.avg.toString(), Field.Store.YES, true).setIntValue((int) (getPrice() / getArea()));
			doc.add(avg);
		}
		if (getRentPrice() != null) {
			NumericField rentPrice = new NumericField(FIELD.rentPrice.toString(), Field.Store.YES, true).setIntValue(getRentPrice());
			doc.add(rentPrice);
		}
		if (StringUtils.isNotEmpty(getEquipment())) {
			Field equipment = new Field(FIELD.equipment.toString(), getEquipment(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(equipment);
		}
		if (getDecorating() != null) {
			Field decorating = new Field(FIELD.decorating.toString(), getDecorating().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(decorating);
		}
		if (getArea() != null) {
			NumericField area = new NumericField(FIELD.area.toString(), Field.Store.YES, true).setFloatValue(getArea() == null ? 0
					: getArea());
			doc.add(area);
		}
		if (getRoomType() != null) {
			NumericField roomType = new NumericField(FIELD.roomType.toString(), Field.Store.YES, true)
			.setIntValue(getRoomType() == null ? 0 : getRoomType());
			doc.add(roomType);
		}
		if (getDirection() != null) {
			Field direction = new Field(FIELD.direction.toString(), getDirection().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(direction);
		}
		if (getType() != null) {
			Field type = new Field(FIELD.type.toString(), getType() == null ? null : getType().toString(), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(type);
		}
		if (getFloor() != null) {
			Field floor = new Field(FIELD.floor.toString(), getFloor() == null ? null : getFloor().toString(), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(floor);
		}
		if (getFloorLevel() != null) {
			Field floorLevel = new Field(FIELD.floorLevel.toString(), getFloorLevel() == null ? null : getFloorLevel().toString(),
					Field.Store.YES, Field.Index.ANALYZED);
			doc.add(floorLevel);
		}
		if (getBuildDate() != null) {
			NumericField buildDate = new NumericField(FIELD.buildDate.toString(), Field.Store.YES, true).setLongValue(getBuildDate()
					.getTime());
			doc.add(buildDate);
		}
		if (getIsEmergent() != null) {
			Field isEmergent = new Field(FIELD.isEmergent.toString(), getIsEmergent().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(isEmergent);
		}
		if (getIsRecommend() != null) {
			Field isRecommend = new Field(FIELD.isRecommend.toString(), getIsRecommend().toString(), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(isRecommend);
		}
		if (StringUtils.isNotEmpty(getHouseMemo())) {
			Field houseMemo = new Field(FIELD.houseMemo.toString(), getHouseMemo(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(houseMemo);
		}
		if (getSourceType() != null) {
			Field sourceType = new Field(FIELD.sourceType.toString(), getSourceType().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(sourceType);
		}
		if (getViewHouseType() != null) {
			Field viewHouseType = new Field(FIELD.viewHouseType.toString(), getViewHouseType().toString(), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(viewHouseType);
		}
		if (StringUtils.isNotEmpty(getMemo())) {
			Field memo = new Field(FIELD.memo.toString(), getMemo(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(memo);
		}
		if (StringUtils.isNotEmpty(getRentMemo())) {
			Field rentMemo = new Field(FIELD.rentMemo.toString(), getRentMemo(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(rentMemo);
		}
		if (StringUtils.isNotEmpty(getBrokerMobile())) {
			Field brokerMobile = new Field(FIELD.brokerMobile.toString(), getBrokerMobile(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(brokerMobile);
		}
		if (StringUtils.isNotEmpty(getBrokerName())) {
			Field brokerName = new Field(FIELD.brokerName.toString(), getBrokerName(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(brokerName);
		}
		if (getBrokerId() != null) {
			NumericField brokerId = new NumericField(FIELD.brokerId.toString(), Field.Store.YES, true).setIntValue(getBrokerId());
			doc.add(brokerId);
		}
		if (getIsFollow() != null) {
			Field isFollow = new Field(FIELD.isFollow.toString(), getIsFollow().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(isFollow);
		}
		if (getCreateTime() != null) {
			NumericField createTime = new NumericField(FIELD.createTime.toString(), Field.Store.YES, true).setLongValue(getCreateTime()
					.getTime());
			doc.add(createTime);
		}
		if (getRentStatus() != null) {
			Field rentStatus = new Field(FIELD.rentStatus.toString(), getRentStatus().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(rentStatus);
		}
		if (getCombinedRent() != null) {
			Field combinedRent = new Field(FIELD.combinedRent.toString(), getCombinedRent().toString(), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(combinedRent);
		}
		if (getFansCount() != null) {
			NumericField fansCount = new NumericField(FIELD.fansCount.toString(), Field.Store.YES, true).setIntValue(getFansCount());
			doc.add(fansCount);
		}
		if (getAskedCount() != null) {
			NumericField askedCount = new NumericField(FIELD.askedCount.toString(), Field.Store.YES, true).setIntValue(getAskedCount());
			doc.add(askedCount);
		}
		if (getIsEmergentRent() != null) {
			Field isEmergentRent = new Field(FIELD.isEmergentRent.toString(), getIsEmergentRent().toString(), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(isEmergentRent);
		}
		if (getIsRecommendRent() != null) {
			Field isRecommendRent = new Field(FIELD.isRecommendRent.toString(), getIsRecommendRent().toString(), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(isRecommendRent);
		}
		if (getIsNew() != null) {
			Field isNew = new Field(FIELD.isNew.toString(), getIsNew().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(isNew);
		}
		if (getDealTimeRent() != null) {
			NumericField dealTimeRent = new NumericField(FIELD.dealTimeRent.toString(), Field.Store.YES, true)
			.setLongValue(getDealTimeRent().getTime());
			doc.add(dealTimeRent);
		}
		if (getDealPriceRent() != null) {
			NumericField dealPriceRent = new NumericField(FIELD.dealPriceRent.toString(), Field.Store.YES, true)
			.setFloatValue(getDealPriceRent());
			doc.add(dealPriceRent);
		}
		if (getSaleStatus() != null) {
			Field saleStatus = new Field(FIELD.saleStatus.toString(), getSaleStatus().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(saleStatus);
		}
		if (getIsHot() != null) {
			Field isHot = new Field(FIELD.isHot.toString(), getIsHot().toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(isHot);
		}
		if (getDealTime() != null) {
			NumericField dealTime = new NumericField(FIELD.dealTime.toString(), Field.Store.YES, true).setLongValue(getDealTime()
					.getTime());
			doc.add(dealTime);
		}
		if (getOnboardTime() != null) {
			NumericField onboardTime = new NumericField(FIELD.onboardTime.toString(), Field.Store.YES, true)
			.setLongValue(getOnboardTime().getTime());
			doc.add(onboardTime);
		}
		if (getDealPrice() != null) {
			NumericField dealPrice = new NumericField(FIELD.dealPrice.toString(), Field.Store.YES, true).setIntValue(getDealPrice());
			doc.add(dealPrice);
		}
		Field interaction = new Field(FIELD.interaction.toString(), String.valueOf(getInteraction()), Field.Store.YES,
				Field.Index.ANALYZED);
		doc.add(interaction);

		if (getLat() != null) {
			NumericField lat = new NumericField(FIELD.lat.toString(), Field.Store.YES, true).setDoubleValue(getLat());
			doc.add(lat);
		}
		if (getLng() != null) {
			NumericField lng = new NumericField(FIELD.lng.toString(), Field.Store.YES, true).setDoubleValue(getLng());
			doc.add(lng);
		}

		if (getRegionName() != null) {
			Field regionName = new Field(FIELD.regionName.toString(), String.valueOf(getRegionName()), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(regionName);
		}

		if (getPlateName() != null) {
			Field plateName = new Field(FIELD.plateName.toString(), String.valueOf(getPlateName()), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(plateName);
		}

		if (getContactCommitter() != null) {
			Field contactCommitter = new Field(FIELD.contactCommitter.toString(), String.valueOf(getContactCommitter()), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(contactCommitter);
		}

		if (getContactMobile() != null) {
			Field contactMobile = new Field(FIELD.contactMobile.toString(), String.valueOf(getContactMobile()), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(contactMobile);
		}

		if (getContactMemo() != null) {
			Field contactMemo = new Field(FIELD.contactMemo.toString(), String.valueOf(getContactMemo()), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(contactMemo);
		}

		if (getContactName() != null) {
			Field contactName = new Field(FIELD.contactName.toString(), String.valueOf(getContactName()), Field.Store.YES,
					Field.Index.ANALYZED);
			doc.add(contactName);
		}

		return doc;
	}

	public HouseDetailBean getHouseDetailBean() {
		HouseDetailBean detail = new HouseDetailBean();
		detail.setAddress(getAddress() == null ? "" : getAddress());
		detail.setSoldAddress(getSoldAddress() == null ? "" : getSoldAddress());
		detail.setBuildingNo(getBuildingNo() == null ? "" : getBuildingNo());
		detail.setCellNo(getCellNo() == null ? "" : getCellNo());

		if (getArea() != null) {
			detail.setArea(String.valueOf(getArea().intValue()) + "平米");
		} else {
			detail.setArea("");
		}

		if (getAvg() != null) {
			detail.setAvg(df_decimal_2.format((float) getAvg() / 10000) + "万/平米");
		} else {
			detail.setAvg("万/平米");
		}

		if (getBrokerMobile() != null) {
			detail.setBrokerMobile(getBrokerMobile());
		} else {
			detail.setBrokerMobile("");
		}

		if (getBrokerName() != null) {
			detail.setBrokerName(getBrokerName());
		} else {
			detail.setBrokerName("");
		}

		if (getBrokerId() != null) {
			detail.setBrokerId(getBrokerId());
		} else {
			detail.setBrokerId(0);
		}

		if (getBuildDate() != null) {
			detail.setBuildDate(getBuildDate().getTime());
			if (!DateUtils.isSameDay(CommonUtils.getDefaultDate(), buildDate)) {
				detail.setBuildDateString(CommonUtils.FORMAT_DATE_DECADE.format(getBuildDate()));
			}
		} else {
			detail.setBuildDate((long) 0);
		}

		if (getCityId() != null) {
			detail.setCityId(getCityId());
		} else {
			detail.setCityId(0);
		}

		if (getCombinedRent() != null) {
			detail.setCombinedRent(getCombinedRent());
		} else {
			detail.setCombinedRent(false);
		}

		if (getDecorating() != null) {
			detail.setDecorating(BundleUtils.getString("house.house.decorating." + getDecorating().toString(), "housemart_zh_CN"));
		} else {
			detail.setDecorating("");
		}

		if (getDirection() != null) {
			detail.setDirection(BundleUtils.getString("house.house.direction." + getDirection(), "housemart_zh_CN"));
		} else {
			detail.setDirection("");
		}

		if (StringUtils.isNotBlank(getEquipment())) {
			String eq = "";
			try {
				eq = HouseRentEquipment.obj2Chinese(HouseRentEquipment.string2Obj(getEquipment()));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			detail.setEquipment(eq);
		}
		detail.setFloor(getFloor() == null ? 0 : getFloor());
		detail.setFloorLevel(getFloorLevel() == null ? "" : HouseUtils.getFloor(getFloorLevel()));
		detail.setId(getId() == null ? 0 : getId());
		detail.setIsFollow(getIsFollow() == null ? false : getIsFollow());

		detail.setIsEmergent(getIsEmergent() == null ? false : getIsEmergent());
		if (getIsEmergent()) {
			detail.setSaleRec(CommonUtils.CONSTANT_HOUSE_EMERGENT);
		}
		detail.setIsRecommend(getIsRecommend() == null ? false : getIsRecommend());
		if (getIsRecommend()) {
			detail.setSaleRec(CommonUtils.CONSTANT_HOUSE_RECOMMEND);
		}

		if (getSourceType() != null && getSourceType().equals(HouseEntity.SourceTypeEnum.external.value)) {
			detail.setMemo(getHouseMemo() == null ? "" : getHouseMemo());
			detail.setRentMemo(getHouseMemo() == null ? "" : getHouseMemo());
		} else {
			detail.setMemo(getMemo() == null ? "" : getMemo());
			detail.setRentMemo(getRentMemo() == null ? "" : getRentMemo());
		}

		if (StringUtils.isNotEmpty(getPicURL())) {
			detail.setPicURL(getPicURL().split(PIC_SPLITTER));
		} else {
			detail.setPicURL(new String[0]);
		}

		detail.setPrice(getPrice() == null ? "万" : String.valueOf(getPrice() / 10000) + "万");

		if (StringUtils.isNotEmpty(getResidencePicURL())) {
			detail.setResidencePicURL(getResidencePicURL().split(PIC_SPLITTER));
		} else {
			detail.setResidencePicURL(new String[0]);
		}

		detail.setRegionId(getRegionId() == null ? 0 : getRegionId());

		if (getRentPrice() != null) {
			detail.setRentPrice(getRentPrice().toString() + "元/月");
		} else {
			detail.setRentPrice("");
		}

		detail.setRentTitle(getRentTitle() == null ? "" : getRentTitle());
		detail.setResidenceId(getResidenceId() == null ? 0 : getResidenceId());
		detail.setResidenceName(getResidenceName() == null ? "" : getResidenceName());

		if (getRoomType() != null) {
			Integer[] roomTypeArray = integerToArray(Integer.valueOf(getRoomType()), 4);
			detail.setRoomType(MessageFormat.format(BundleUtils.getString("house.house.roomType", "housemart_zh_CN"), roomTypeArray[0],
					roomTypeArray[1], roomTypeArray[2]));
		} else detail.setRoomType("");

		detail.setTitle(getTitle() == null ? "" : getTitle());

		if (getType() != null) {
			detail.setType(getType().toString());
		} else {
			detail.setTitle("");
		}

		if (getViewHouseType() != null) {
			detail.setViewHouseType(BundleUtils.getString("house.house.viewHouseType." + getViewHouseType(), "housemart_zh_CN"));
		} else {
			detail.setViewHouseType("");
		}

		detail.setLat(getLat() == null ? (double) 0 : getLat());
		detail.setLng(getLng() == null ? (double) 0 : getLng());
		detail.setDistance(getDistance() == null ? (double) 0 : getDistance());

		if (onboardTime != null) {
			detail.setOnboardTime(onboardTime.getTime());
			if (!DateUtils.isSameDay(CommonUtils.getDefaultDate(), onboardTime)) {
				detail.setOnboardTimeString(CommonUtils.FORMAT_DATE_SIMPLE.format(onboardTime));
			}
		} else {
			detail.setOnboardTime((long) 0);
		}

		if (dealTime != null) {
			detail.setDealTime(dealTime.getTime());
			if (!DateUtils.isSameDay(CommonUtils.getDefaultDate(), dealTime)) {
				detail.setDealTimeString(CommonUtils.FORMAT_DATE_SIMPLE.format(dealTime));
			}
		} else {
			detail.setDealTime((long) 0);
		}

		if (dealTimeRent != null) {
			detail.setDealTimeRent(dealTimeRent.getTime());
			if (!DateUtils.isSameDay(CommonUtils.getDefaultDate(), dealTimeRent)) {
				detail.setDealTimeRentString(CommonUtils.FORMAT_DATE_SIMPLE.format(dealTimeRent));
			}
		} else {
			detail.setDealTimeRent((long) 0);
		}

		// detail.setPlateName(CommonUtils.getCity(getCityId()) + " " +
				// getRegionName() + " " + getPlateName());
		detail.setPlateName(getRegionName() + " " + getPlateName() + " " + getResidenceName());

		detail.setInteraction(getInteraction());

		return detail;
	}

	public HouseRentBean getHouseRentBean() {
		HouseRentBean rent = new HouseRentBean();
		if (getArea() != null) rent.setArea(String.valueOf(getArea().intValue()) + "平米");
		else rent.setArea("");

		if (getAddress() != null) rent.setAddress(getAddress());
		else rent.setAddress("");

		rent.setAskedCount(getAskedCount() == null ? 0 : getAskedCount());
		rent.setCombinedRent(getCombinedRent() == null ? false : getCombinedRent());

		if (getDecorating() != null) rent.setDecorating(BundleUtils.getString("house.house.decorating." + getDecorating().toString(),
				"housemart_zh_CN"));
		else rent.setDecorating("");

		if (getDirection() != null) rent.setDirection(BundleUtils.getString("house.house.direction." + getDirection(),
				"housemart_zh_CN"));
		else rent.setDirection("");

		rent.setFansCount(getFansCount() == null ? 0 : getFansCount());
		rent.setFloor(getFloor() == null ? 0 : getFloor());
		rent.setFloorLevel(getFloorLevel() == null ? "" : HouseUtils.getFloor(getFloorLevel()));
		rent.setId(getId() == null ? 0 : getId());
		rent.setIsFollow(getIsFollow() == null ? false : getIsFollow());
		rent.setIsNew(getIsNew() == null ? false : getIsNew());
		rent.setIsEmergent(getIsEmergentRent() == null ? false : getIsEmergentRent());
		if (getIsEmergentRent()) {
			rent.setRentRec(CommonUtils.CONSTANT_HOUSE_EMERGENT);
		}
		rent.setIsRecommend(getIsRecommendRent() == null ? false : getIsRecommendRent());
		if (getIsRecommendRent()) {
			rent.setRentRec(CommonUtils.CONSTANT_HOUSE_RECOMMEND);
		}
		if (StringUtils.isNotEmpty(getPicURL())) rent.setPicURL(getPicURL().split(PIC_SPLITTER)[0]);
		else rent.setPicURL("");

		if (StringUtils.isNotEmpty(getResidencePicURL())) {
			rent.setResidencePicURL(getResidencePicURL().split(PIC_SPLITTER));
		} else {
			rent.setResidencePicURL(new String[0]);
		}

		if (getPrice() != null) rent.setPrice(getRentPrice().toString() + "元/月");
		else rent.setPrice("元/月");

		rent.setRentStatus(getRentStatus() == null ? 0 : getRentStatus());

		if (getRoomType() != null) {
			Integer[] roomTypeArray = integerToArray(Integer.valueOf(getRoomType()), 4);
			rent.setRoomType(MessageFormat.format(BundleUtils.getString("house.house.roomType", "housemart_zh_CN"), roomTypeArray[0],
					roomTypeArray[1], roomTypeArray[2]));
		} else rent.setRoomType("");

		if (getType() != null) rent.setType(getType().toString());
		else rent.setType("");

		if (dealTimeRent != null) {
			rent.setDealTimeRent(dealTimeRent.getTime());
			if (!DateUtils.isSameDay(CommonUtils.getDefaultDate(), dealTimeRent)) {
				rent.setDealTimeRentString(CommonUtils.FORMAT_DATE_SIMPLE.format(dealTimeRent));
			}
		} else {
			rent.setDealTimeRent((long) 0);
		}

		if (onboardTime != null) {
			rent.setOnboardTime(onboardTime.getTime());
			if (!DateUtils.isSameDay(CommonUtils.getDefaultDate(), onboardTime)) {
				rent.setOnboardTimeString(CommonUtils.FORMAT_DATE_SIMPLE.format(onboardTime));
			}
		} else {
			rent.setOnboardTime((long) 0);
		}

		rent.setLat(getLat() == null ? (double) 0 : getLat());
		rent.setLng(getLng() == null ? (double) 0 : getLng());
		rent.setDistance(getDistance() == null ? (double) 0 : getDistance());
		// rent.setPlateName(CommonUtils.getCity(getCityId()) + " " +
		// getRegionName() + " " + getPlateName());
		rent.setPlateName(getRegionName() + " " + getPlateName() + " " + getResidenceName());

		return rent;
	}
	
	static public String formatFloat(Float f)
	{
		if (f == null)
		{
			return "";
		}
		
		if (f.intValue() < f)
		{
			return new DecimalFormat("0.0").format(f);
		}
		else if (f.intValue() == 0)
		{
			return "";
		}
		else
		{
			return String.valueOf(f.intValue());
		}
	}
	
	public HouseSaleBean getHouseSaleBean() {
		HouseSaleBean sale = new HouseSaleBean();
		sale.setAskedCount(getAskedCount() == null ? 0 : getAskedCount());

		if (getAddress() != null) {
			sale.setAddress(getAddress());
		} else {
			sale.setAddress("");
		}

		if (getSoldAddress() != null) {
			sale.setSoldAddress(getSoldAddress());
		} else {
			sale.setSoldAddress("");
		}

		if (getBuildingNo() != null) {
			sale.setBuildingNo(getBuildingNo());
		} else {
			sale.setBuildingNo("");
		}

		if (getCellNo() != null) {
			sale.setCellNo(getCellNo());
		} else {
			sale.setCellNo("");
		}

		if (getArea() != null) {
			sale.setArea(String.valueOf(getArea().intValue()) + "平米");
		} else {
			sale.setArea("");
		}

		if (getAvg() != null) {
			sale.setAvg(df_decimal_2.format((float) getAvg() / 10000) + "万/平米");
		} else {
			sale.setAvg("万/平米");
		}

		if (getDealPrice() != null) {
			sale.setDealPrice(String.valueOf(getDealPrice() / 10000) + "万");
		} else {
			sale.setDealPrice("万");
		}

		if (getDealTime() != null) {
			sale.setDealTime(getDealTime().getTime());
			if (!DateUtils.isSameDay(CommonUtils.getDefaultDate(), dealTime)) {
				sale.setDealTimeString(CommonUtils.FORMAT_DATE_SIMPLE.format(getDealTime()));
			}
		} else {
			sale.setDealTime((long) 0);
		}

		if (onboardTime != null) {
			sale.setOnboardTime(onboardTime.getTime());
			if (!DateUtils.isSameDay(CommonUtils.getDefaultDate(), onboardTime)) {
				sale.setOnboardTimeString(CommonUtils.FORMAT_DATE_SIMPLE.format(onboardTime));
			}
		} else {
			sale.setOnboardTime((long) 0);
		}

		if (getDirection() != null) {
			sale.setDirection(BundleUtils.getString("house.house.direction." + getDirection(), "housemart_zh_CN"));
		} else {
			sale.setDirection("");
		}

		sale.setFansCount(getFansCount() == null ? 0 : getFansCount());
		sale.setFloor(getFloor() == null ? 0 : getFloor());
		sale.setFloorLevel(getFloorLevel() == null ? "" : HouseUtils.getFloor(getFloorLevel()));
		sale.setId(getId() == null ? 0 : getId());
		sale.setIsEmergent(getIsEmergent() == null ? false : getIsEmergent());
		if (getIsEmergent()) {
			sale.setSaleRec(CommonUtils.CONSTANT_HOUSE_EMERGENT);
		}
		sale.setIsRecommend(getIsRecommend() == null ? false : getIsRecommend());
		if (getIsRecommend()) {
			sale.setSaleRec(CommonUtils.CONSTANT_HOUSE_RECOMMEND);
		}
		sale.setIsFollow(getIsFollow() == null ? false : getIsFollow());
		sale.setIsHot(getIsHot() == null ? false : getIsHot());

		if (StringUtils.isNotEmpty(getPicURL())) sale.setPicURL(getPicURL().split(PIC_SPLITTER)[0]);
		else sale.setPicURL("");

		if (StringUtils.isNotEmpty(getResidencePicURL())) {
			sale.setResidencePicURL(getResidencePicURL().split(PIC_SPLITTER));
		} else {
			sale.setResidencePicURL(new String[0]);
		}

		if (getPrice() != null) sale.setPrice(String.valueOf(getPrice() / 10000) + "万");
		else sale.setPrice("万");

		if (getRoomType() != null) {
			Integer[] roomTypeArray = integerToArray(Integer.valueOf(getRoomType()), 4);
			sale.setRoomType(MessageFormat.format(BundleUtils.getString("house.house.roomType", "housemart_zh_CN"), roomTypeArray[0],
					roomTypeArray[1], roomTypeArray[2]));
		} else sale.setRoomType("");

		sale.setSaleStatus(getSaleStatus() == null ? 0 : getSaleStatus());
		if (getType() != null) sale.setType(getType().toString());
		else sale.setType("");

		sale.setLat(getLat() == null ? (double) 0 : getLat());
		sale.setLng(getLng() == null ? (double) 0 : getLng());
		sale.setDistance(getDistance() == null ? (double) 0 : getDistance());
		sale.setResidenceName(getResidenceName() == null ? "" : getResidenceName());

		// sale.setPlateName(CommonUtils.getCity(getCityId()) + " " +
		// getRegionName() + " " + getPlateName());
		sale.setPlateName(getRegionName() + " " + getPlateName() + " " + getResidenceName());

		return sale;
	}

	public LandlordInfo getLandloadInfo() {
		LandlordInfo landload = new LandlordInfo();

		if (getContactCommitter() != null) {
			landload.setHouseCommitterId(getContactCommitter());
		}
		if (getContactMobile() != null) {
			landload.setLandlordMobile(getContactMobile());
		}
		if (getContactMemo() != null) {
			landload.setLandlordMemo(getContactMemo());
		}
		if (getContactName() != null) {
			landload.setLandlordName(getContactName());
		}

		if (getType() != null && getType().equals("3")) { // 外围登盘
			String address = "";
			if (StringUtils.isNotBlank(getAddress())) {
				address += getAddress();
			}
			if (StringUtils.isNotBlank(getBuildingNo())) {
				address += getBuildingNo() + "栋（号）";
			}
			if (StringUtils.isNotBlank(getCellNo())) {
				address += getCellNo() + "单元（室）";
			}

			landload.setHouseAddress(address);
		} else {
			if (StringUtils.isNotBlank(getSoldAddress())) { // 登入已售房源
				landload.setHouseAddress(getSoldAddress());
			} else { // 内部登盘
				String address = "";
				if (StringUtils.isNotBlank(getAddress())) {
					address += getAddress();
				}
				if (getFloor() != null) {
					address += getFloor();
				}
				landload.setHouseAddress(address);
			}

		}

		return landload;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRentTitle() {
		return rentTitle;
	}

	public void setRentTitle(String rentTitle) {
		this.rentTitle = rentTitle;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getPlateId() {
		return plateId;
	}

	public void setPlateId(Integer plateId) {
		this.plateId = plateId;
	}

	public Integer getResidenceId() {
		return residenceId;
	}

	public void setResidenceId(Integer residenceId) {
		this.residenceId = residenceId;
	}

	public String getResidenceName() {
		return residenceName;
	}

	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getAvg() {
		return avg;
	}

	public void setAvg(Integer avg) {
		this.avg = avg;
	}

	public Integer getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public Integer getDecorating() {
		return decorating;
	}

	public void setDecorating(Integer decorating) {
		this.decorating = decorating;
	}

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public Boolean getIsEmergent() {
		return isEmergent;
	}

	public void setIsEmergent(Boolean isEmergent) {
		this.isEmergent = isEmergent;
	}

	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getViewHouseType() {
		return viewHouseType;
	}

	public void setViewHouseType(Integer viewHouseType) {
		this.viewHouseType = viewHouseType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBrokerMobile() {
		return brokerMobile;
	}

	public void setBrokerMobile(String brokerMobile) {
		this.brokerMobile = brokerMobile;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public Integer getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Integer brokerId) {
		this.brokerId = brokerId;
	}

	public Boolean getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(Boolean isFollow) {
		this.isFollow = isFollow;
	}

	public Boolean getCombinedRent() {
		return combinedRent;
	}

	public void setCombinedRent(Boolean combinedRent) {
		this.combinedRent = combinedRent;
	}

	public Integer getFansCount() {
		return fansCount;
	}

	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}

	public Integer getAskedCount() {
		return askedCount;
	}

	public void setAskedCount(Integer askedCount) {
		this.askedCount = askedCount;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Integer getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(Integer dealPrice) {
		this.dealPrice = dealPrice;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getRentStatus() {
		return rentStatus;
	}

	public void setRentStatus(Integer rentStatus) {
		this.rentStatus = rentStatus;
	}

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Boolean getIsEmergentRent() {
		return isEmergentRent;
	}

	public void setIsEmergentRent(Boolean isEmergentRent) {
		this.isEmergentRent = isEmergentRent;
	}

	public Boolean getIsRecommendRent() {
		return isRecommendRent;
	}

	public void setIsRecommendRent(Boolean isRecommendRent) {
		this.isRecommendRent = isRecommendRent;
	}

	public Date getDealTimeRent() {
		return dealTimeRent;
	}

	public void setDealTimeRent(Date dealTimeRent) {
		this.dealTimeRent = dealTimeRent;
	}

	public Float getDealPriceRent() {
		return dealPriceRent;
	}

	public void setDealPriceRent(Float dealPriceRent) {
		this.dealPriceRent = dealPriceRent;
	}

	/**
	 * 10011 -> [1, 0, 0, 1, 1]
	 * 
	 * @param integer
	 * @param length
	 * @return
	 */
	 private Integer[] integerToArray(Integer integer, int length) {
		 Integer[] result = new Integer[length];
		 if (integer != null) {
			 for (int i = result.length - 1; i >= 0; i--) {
				 result[i] = integer % 10;
				 integer = integer / 10;
			 }
		 }
		 return result;
	 }

	 public Date getOnboardTime() {
		 return onboardTime;
	 }

	 public void setOnboardTime(Date onboardTime) {
		 this.onboardTime = onboardTime;
	 }

	 public Integer getStatus() {
		 return status;
	 }

	 public void setStatus(Integer status) {
		 this.status = status;
	 }

	 public String getResidencePicURL() {
		 return residencePicURL;
	 }

	 public void setResidencePicURL(String residencePicURL) {
		 this.residencePicURL = residencePicURL;
	 }

	 public int getInteraction() {
		 return interaction;
	 }

	 public void setInteraction(int interaction) {
		 this.interaction = interaction;
	 }

	 public String getSoldAddress() {
		 return soldAddress;
	 }

	 public void setSoldAddress(String soldAddress) {
		 this.soldAddress = soldAddress;
	 }

	 public Integer getFloorLevel() {
		 return floorLevel;
	 }

	 public void setFloorLevel(Integer floorLevel) {
		 this.floorLevel = floorLevel;
	 }

	 public String getRentMemo() {
		 return rentMemo;
	 }

	 public void setRentMemo(String rentMemo) {
		 this.rentMemo = rentMemo;
	 }

	 public String getPlateName() {
		 return plateName;
	 }

	 public void setPlateName(String plateName) {
		 this.plateName = plateName;
	 }

	 public String getRegionName() {
		 return regionName;
	 }

	 public void setRegionName(String regionName) {
		 this.regionName = regionName;
	 }

	 public String getContactName() {
		 return contactName;
	 }

	 public void setContactName(String contactName) {
		 this.contactName = contactName;
	 }

	 public String getContactMobile() {
		 return contactMobile;
	 }

	 public void setContactMobile(String contactMobile) {
		 this.contactMobile = contactMobile;
	 }

	 public String getContactMemo() {
		 return contactMemo;
	 }

	 public void setContactMemo(String contactMemo) {
		 this.contactMemo = contactMemo;
	 }

	 public Integer getContactCommitter() {
		 return contactCommitter;
	 }

	 public void setContactCommitter(Integer contactCommitter) {
		 this.contactCommitter = contactCommitter;
	 }

	 public String getBuildingNo() {
		 return buildingNo;
	 }

	 public void setBuildingNo(String buildingNo) {
		 this.buildingNo = buildingNo;
	 }

	 public String getCellNo() {
		 return cellNo;
	 }

	 public void setCellNo(String cellNo) {
		 this.cellNo = cellNo;
	 }

	 public String getHouseMemo() {
		 return houseMemo;
	 }

	 public void setHouseMemo(String houseMemo) {
		 this.houseMemo = houseMemo;
	 }

	 public Integer getSourceType() {
		 return sourceType;
	 }

	 public void setSourceType(Integer sourceType) {
		 this.sourceType = sourceType;
	 }

	 public Date getUpdateTime() {
		 return updateTime;
	 }

	 public void setUpdateTime(Date updateTime) {
		 this.updateTime = updateTime;
	 }

	 public String getDetailName() {
		 return detailName;
	 }

	 public String getHouseType() {
		 return houseType;
	 }

	 public void setHouseType(String houseType) {
		 this.houseType = houseType;
	 }

	 public void setDetailName(String detailName) {
		 this.detailName = detailName;
	 }

	 public Float getPropertyArea() {
		 return propertyArea;
	 }

	 public void setPropertyArea(Float propertyArea) {
		 this.propertyArea = propertyArea;
	 }

	 public Float getOccupiedArea() {
		 return occupiedArea;
	 }

	 public void setOccupiedArea(Float occupiedArea) {
		 this.occupiedArea = occupiedArea;
	 }

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public String getRentTagList() {
		return rentTagList;
	}

	public void setRentTagList(String rentTagList) {
		this.rentTagList = rentTagList;
	}

	public String getSaleTagList() {
		return saleTagList;
	}

	public void setSaleTagList(String saleTagList) {
		this.saleTagList = saleTagList;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getAuditComments() {
		return auditComments;
	}

	public void setAuditComments(String auditComments) {
		this.auditComments = auditComments;
	}

}
