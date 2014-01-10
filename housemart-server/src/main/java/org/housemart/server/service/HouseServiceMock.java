/**
 * Created on 2012-11-18
 * 
 */
package org.housemart.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.housemart.server.beans.house.HouseDetailBean;
import org.housemart.server.beans.house.HouseRentBean;
import org.housemart.server.beans.house.HouseSaleBean;

/**
 * @author pqin
 */
@SuppressWarnings("rawtypes")
public class HouseServiceMock {
	public static SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");

	public HouseDetailBean loadDetail(int houseId) throws ParseException {
		HouseDetailBean detail = new HouseDetailBean();
		// 财富海景
		if (houseId > 0 && houseId <= 10) {
			detail.setAddress("浦东浦明路266号");
			detail.setArea("125平米");
			detail.setAvg("79000万/平");
			detail.setBrokerMobile("13898765467");
			detail.setBuildDate(df.parse("01/12/2012").getTime());
			detail.setCityId(222);
			detail.setDecorating("豪装");
			detail.setDirection("南北");
			detail.setEquipment("电视机，热水器，网络");
//			detail.setFloor("高");
			detail.setId(houseId);
			detail.setIsEmergent(true);
			detail.setIsFollow(true);
			detail.setIsRecommend(true);
			detail.setMemo("海景房，急售");
			detail.setOnboardTime(df.parse("12/25/2012").getTime());
			detail.setPicURL(new String[]{"http://42.121.87.85:8080/upload/caifu1360070216993.JPG",
					"http://42.121.87.85:8080/upload/DSC00012_conew11360070230636.jpg"});
			detail.setPlateId(22);
			detail.setPrice("1500万");
			detail.setRegionId(23);
			detail.setRentPrice("25000/月");
			detail.setRentTitle("海景房，急租");
			detail.setResidenceId(1);
			detail.setResidenceName("财富海景");
			detail.setRoomType("3房2厅");
			detail.setTitle("海景房，急售");
			detail.setType("公寓");
			detail.setViewHouseType("周一到周五下午2:00~4:00看房");
			detail.setLat(31.225681);
			detail.setLng(121.50890400000003);
			detail.setDistance((double) 1.5);
			detail.setCombinedRent(true);
			if (houseId == 1) {

			}
			if (houseId == 2) {
				detail.setArea("250平米");
				detail.setAvg("8000万/平");
				detail.setPicURL(new String[]{"http://42.121.87.85:8080/upload/DSC005721360072074379.JPG",
						"http://42.121.87.85:8080/upload/DSC005071360072065567.JPG"});
				detail.setPrice("1500万");
				detail.setRentPrice("55000/月");
			}
		}

		// 仁恒滨江
		if (houseId > 10 && houseId <= 20) {
			detail.setAddress("浦明路99弄1～33号");
			detail.setArea("125平米");
			detail.setAvg("76000万/平");
			detail.setBrokerMobile("13898765467");
			detail.setBuildDate(df.parse("01/12/2012").getTime());
			detail.setCityId(222);
			detail.setDecorating("简装");
			detail.setDirection("南北");
			detail.setEquipment("电视机，热水器，网络");
//			detail.setFloor("高");
			detail.setId(houseId);
			detail.setIsEmergent(true);
			detail.setIsFollow(true);
			detail.setIsRecommend(true);
			detail.setMemo("仁恒滨江园诚心出售");
			detail.setOnboardTime(df.parse("12/25/2012").getTime());
			detail.setPicURL(new String[]{"http://42.121.87.85:8080/upload/conew_wp_0014891360070386450.jpg",
					"http://42.121.87.85:8080/upload/conew_wp_0014831360070395489.jpg"});
			detail.setPlateId(22);
			detail.setPrice("900万");
			detail.setRegionId(23);
			detail.setRentPrice("15000/月");
			detail.setRentTitle("仁恒滨江园诚心出租");
			detail.setResidenceId(2);
			detail.setResidenceName("仁恒滨江");
			detail.setRoomType("3房2厅");
			detail.setTitle("仁恒滨江园诚心出售");
			detail.setType("公寓");
			detail.setViewHouseType("周一到周五下午2:00~4:00看房");
			detail.setLat(31.228725);
			detail.setLng(121.50664400000005);
			detail.setDistance((double) 1.5);
			detail.setCombinedRent(true);
			if (houseId == 11) {

			}
			if (houseId == 12) {
				detail.setArea("183.7平米");
				detail.setAvg("76000万/平");
				detail.setPicURL(new String[]{"http://42.121.87.85:8080/upload/kitchen1360072622416.jpg",
						"http://42.121.87.85:8080/upload/baby1360072614497.jpg"});
				detail.setPrice("1500万");
				detail.setRentPrice("55000/月");
			}

		}
		// 世茂滨江
		if (houseId > 20 && houseId <= 30) {

			detail.setAddress("浦东新区潍坊西路1-2弄(近浦城路) ");
			detail.setArea("333平米");
			detail.setAvg("98000万/平");
			detail.setBrokerMobile("13898765467");
			detail.setBuildDate(df.parse("01/12/2012").getTime());
			detail.setCityId(222);
			detail.setDecorating("简装");
			detail.setDirection("南北");
			detail.setEquipment("电视机，热水器，网络");
//			detail.setFloor("高");
			detail.setId(houseId);
			detail.setIsEmergent(true);
			detail.setIsFollow(true);
			detail.setIsRecommend(true);
			detail.setMemo("紧邻陆家嘴黄浦江，东方明珠");
			detail.setOnboardTime(df.parse("12/25/2012").getTime());
			detail.setPicURL(new String[]{"http://42.121.87.85:8080/upload/waiguan-min1360069884512.jpg",
					"http://42.121.87.85:8080/upload/shoulouwaiguan1360069911253.jpg"});
			detail.setPlateId(22);
			detail.setPrice("2700万");
			detail.setRegionId(23);
			detail.setRentPrice("80000/月");
			detail.setRentTitle("紧邻陆家嘴黄浦江，东方明珠");
			detail.setResidenceId(3);
			detail.setResidenceName("世茂滨江花园");
			detail.setRoomType("4房2厅");
			detail.setTitle("紧邻陆家嘴黄浦江，东方明珠");
			detail.setType("公寓");
			detail.setViewHouseType("周一到周五下午2:00~4:00看房");
			detail.setLat(31.228725);
			detail.setLng(121.50664400000005);
			detail.setDistance((double) 1.5);
			detail.setCombinedRent(true);
			if (houseId == 21) {

			}
			if (houseId == 22) {
				detail.setArea("183.7平米");
				detail.setAvg("98000万/平");
				detail.setPicURL(new String[]{"http://42.121.87.85:8080/upload/smbj2-min1360070025395.jpg",
						"http://42.121.87.85:8080/upload/SMBJ-min1360070029245.jpg"});
				detail.setPrice("2000万");
				detail.setRentPrice("55000/月");
			}

		}
		if (houseId > 30) {
			detail.setAddress("San Francisco, CA 94131 (Glen Park)");
			detail.setArea("800 sqm");
			detail.setAvg("$3,312/sqm");
			detail.setBrokerMobile("(415) 799-3564");
			detail.setBuildDate(df.parse("01/12/2012").getTime());
			detail.setCityId(222);
			detail.setDecorating("Fine decoration");
			detail.setDirection("South North");
			detail.setEquipment("TV, Router, Internet, Heater");
//			detail.setFloor("高");
			detail.setId(houseId);
			detail.setIsEmergent(true);
			detail.setIsFollow(true);
			detail.setIsRecommend(true);
			detail.setMemo("Impeccable attention to detail and quality finishes throughout really makes this new construction home stand out. A thoughtful floor plan mirrors todays lifestyle and offers amenities not typically seen in San Francisco. This home features four bedrooms, three- and- half baths, a landscaped rear yard, large walk out deck and two car parking. The open plan kitchen, dining and living room are spacious, bright and welcoming opening up to a walk out deck with city views. The upper level of the house has three bedrooms and two bathrooms including a generous view master suite. The lower level of the home features a family… room with wet bar/kitchenette, a fourth bedroom that can be used as a library or office, a full bathroom, a fully equipped media room, wine storage area and bonus activity or storage area. Floor to ceiling glass accordion doors takes you out to a private, flat landscaped rear yard and patio. A two car garage and Luminalt solar electrical system complete this wonderful home. Excellent location that is convenient to downtown Glen Park, BART and numerous corporate shuttle buses. ");
			detail.setOnboardTime(df.parse("12/25/2012").getTime());
			detail.setPicURL(new String[]{
					"http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/2/2/d/b/picture-uh=daba7d161a4b41baa28a9a9572ba99d0-ps=22dbe6df3bddc765c90bb735bda398-533-Laidley-St-San-Francisco-CA-94131.jpg",
					"http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/c/f/6/c/picture-uh=4d7113e136b0513474bb6381ba779c3e-ps=cf6c49f19eed36355ff26ecb4ac6dbda.jpg"});
			detail.setPlateId(22);
			detail.setPrice("$2,650,000");
			detail.setRegionId(23);
			detail.setRentPrice("$1500/m");
			detail.setRentTitle("San Francisco, CA 94131 (Glen Park) 4 bed, 3.5 bath Single-Family Home");
			detail.setResidenceId(4);
			detail.setResidenceName("533 Laidley St");
			detail.setRoomType("4 bed, 3.5 bath Single-Family Home");
			detail.setTitle("San Francisco, CA 94131 (Glen Park) 4 bed, 3.5 bath Single-Family Home");
			detail.setType("Single-Family Home");
			detail.setViewHouseType("Saturday Feb 2nd, 12pm to 2pm,Sunday Feb 3rd, 12pm to 2pm");
			detail.setLat(37.735542);
			detail.setLng(-122.431244);
			detail.setDistance((double) 1.5);
			detail.setCombinedRent(true);

			if (houseId == 31) {
				// http://www.trulia.com/property/1084021391-533-Laidley-St-San-Francisco-CA-94131
				detail.setAddress("San Francisco, CA 94131 (Glen Park)");
				detail.setArea("800 sqm");
				detail.setAvg("$3,312/sqm");
				detail.setBrokerMobile("(415) 799-3564");
				detail.setCityId(222);
				detail.setMemo("Impeccable attention to detail and quality finishes throughout really makes this new construction home stand out. A thoughtful floor plan mirrors todays lifestyle and offers amenities not typically seen in San Francisco. This home features four bedrooms, three- and- half baths, a landscaped rear yard, large walk out deck and two car parking. The open plan kitchen, dining and living room are spacious, bright and welcoming opening up to a walk out deck with city views. The upper level of the house has three bedrooms and two bathrooms including a generous view master suite. The lower level of the home features a family… room with wet bar/kitchenette, a fourth bedroom that can be used as a library or office, a full bathroom, a fully equipped media room, wine storage area and bonus activity or storage area. Floor to ceiling glass accordion doors takes you out to a private, flat landscaped rear yard and patio. A two car garage and Luminalt solar electrical system complete this wonderful home. Excellent location that is convenient to downtown Glen Park, BART and numerous corporate shuttle buses. ");
				detail.setPicURL(new String[]{
						"http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/2/2/d/b/picture-uh=daba7d161a4b41baa28a9a9572ba99d0-ps=22dbe6df3bddc765c90bb735bda398-533-Laidley-St-San-Francisco-CA-94131.jpg",
						"http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/c/f/6/c/picture-uh=4d7113e136b0513474bb6381ba779c3e-ps=cf6c49f19eed36355ff26ecb4ac6dbda.jpg"});
				detail.setPrice("$2,650,000");
				detail.setRentPrice("$1500/m");
				detail.setRentTitle("San Francisco, CA 94131 (Glen Park) 4 bed, 3.5 bath Single-Family Home");
				detail.setResidenceName("533 Laidley St");
				detail.setRoomType("4 bed, 3.5 bath Single-Family Home");
				detail.setTitle("San Francisco, CA 94131 (Glen Park) 4 bed, 3.5 bath Single-Family Home");
				detail.setType("Single-Family Home");
				detail.setViewHouseType("Saturday Feb 2nd, 12pm to 2pm,Sunday Feb 3rd, 12pm to 2pm");
				detail.setLat(37.735542);
				detail.setLng(-122.431244);
				detail.setDistance((double) 1.5);
			}
			if (houseId == 32) {
				// http://www.trulia.com/property/3105899397-661-Peralta-Ave-4-San-Francisco-CA-94110#photo-2
				detail.setAddress("San Francisco, CA 94110 (Bernal Heights)");
				detail.setArea("600 sqft");
				detail.setAvg("$733/sqft");
				detail.setBrokerMobile("(415) 305-3291");
				detail.setCityId(222);
				detail.setMemo("Modern top unit with open floor plan near the peak of Bernal Heights. Panoramic bay views, designer colors, granite and cherry kitchen, marble bathroom with pedestal sink, hardwood floors, crown molding, custom lighting, view deck, and landscaped rear yard. Includes 1 car parking, common laundry, and storage. Amazing location close to Cortland shops and restaurants, Holly Park, Precita Park, 280 and 101 freeways, and public transportation. Seller financing possible as second loan.");
				detail.setPicURL(new String[]{
						"http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/b/8/e/0/picture-uh=8fa39250b508e7768d0b96bcc89dcdb-ps=b8e06d5196f7ff583297f5feb853732-661-Peralta-Ave-4-San-Francisco-CA-94110.jpg",
						"http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.50/c/9/f/b/picture-uh=a6a72a6de3ad4431c21375b3ef95f212-ps=c9fbaf8944984db5eb34f26fb2fe1e2.jpg",});
				detail.setPrice("$440,000");
				detail.setRentPrice("$1500/m");
				detail.setRentTitle("San Francisco, CA 94110 (Bernal Heights)1 bed, 1 bath 600 sqft Condo");
				detail.setResidenceName("661 Peralta Ave #4");
				detail.setRoomType("1 bed, 1 bath 600 sqft Condo");
				detail.setTitle("San Francisco, CA 94110 (Bernal Heights)1 bed, 1 bath 600 sqft Condo");
				detail.setType("Condo");
				detail.setViewHouseType("Saturday Feb 2nd, 2pm to 4pm");
				detail.setLat(37.742046);
				detail.setLng(-122.408623);
				detail.setDistance((double) 1.7);
			}
			if (houseId == 33) {
				// http://www.trulia.com/property/3086087502-729-Congo-St-San-Francisco-CA-94131
				detail.setAddress("San Francisco, CA 94131 (Miraloma)");
				detail.setArea("3,100 sqft");
				detail.setAvg("$704/sqft");
				detail.setBrokerMobile("(415) 789-3614");
				detail.setCityId(222);
				detail.setMemo("729 Congo St 729 Congo is a stunning, exquisitely designed home with unique forest views of Glen Park Canyon and a short walk to downtown Glen Park. This newly constructed modern masterpiece offers an exceptional floor plan, with open plan living spaces, a beautifully appointed chef's kitchen, an expansive master suite and an entertainment level with wired media room. There are 3+ bedrooms, 3.5 baths and private decks with expansive views to the north. ");
				detail.setPicURL(new String[]{
						"http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.50/5/c/8/c/picture-uh=e8546639f8bdf5c93847f677123e513-ps=5c8cb1e2b0da93be508620f2a0f0b46-729-Congo-St-San-Francisco-CA-94131.jpg",
						"http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.50/5/b/6/2/picture-uh=56a3632b5650e1f9c41a406ade2afff8-ps=5b624f846cc732554b5f4076844c856a.jpg",});
				detail.setPrice("$2,185,000");
				detail.setRentPrice("$1500/m");
				detail.setRentTitle("San Francisco, CA 94131 (Miraloma)3 bed, 4 bath 3,100 sqft Single-Family Home");
				detail.setResidenceName("729 Congo St ");
				detail.setRoomType("3 bed, 4 bath 3,100 sqft Single-Family Home");
				detail.setTitle("San Francisco, CA 94131 (Miraloma)3 bed, 4 bath 3,100 sqft Single-Family Home");
				detail.setType("Single-Family Home");
				detail.setViewHouseType("Tuesday Feb 5th, 12:30pm to 2pm Sunday Feb 3rd, 1pm to 3pm");
				detail.setLat(37.735641);
				detail.setLng(-122.441673);
				detail.setDistance((double) 1.7);
			}
			if (houseId % 10 == 4) {
				// http://www.trulia.com/property/3107344369-149-Teresita-Blvd-San-Francisco-CA-94127
			}
			if (houseId % 10 == 5) {
				// http://www.trulia.com/property/3107407347-33-Clementina-St-5-San-Francisco-CA-94105
			}
			if (houseId % 10 == 6) {
				// http://www.trulia.com/property/3104230824-23-Blair-Ter-San-Francisco-CA-94107
			}
		}
		return detail;
	}

	public HouseSaleBean loadSale(int houseId) throws ParseException {
		HouseSaleBean sale = new HouseSaleBean();
		sale.setAskedCount(22);
		sale.setDealTime(df.parse("12/25/2012").getTime());
		sale.setFansCount(88);
		sale.setIsHot(true);
		sale.setSaleStatus(1);

		// 财富海景
		if (houseId > 0 && houseId <= 10) {
			sale.setArea("125平米");
			sale.setAvg("79000万/平");
			sale.setDecorating("豪装");
			sale.setDirection("南北");
//			sale.setFloor("高");
			sale.setId(houseId);
			sale.setIsEmergent(true);
			sale.setIsFollow(true);
			sale.setOnboardTime(df.parse("12/25/2012").getTime());
			sale.setPicURL("http://42.121.87.85:8080/upload/caifu1360070216993.JPG");
			sale.setPrice("1500万");
			sale.setResidenceName("财富海景");
			sale.setRoomType("3房2厅");
			sale.setType("公寓");
			sale.setLat(31.225681);
			sale.setLng(121.50890400000003);
			sale.setDistance((double) 1.5);
			sale.setDealPrice("1500万");

			if (houseId == 1) {

			}
			if (houseId == 2) {
				sale.setArea("250平米");
				sale.setAvg("8000万/平");
				sale.setPicURL("http://42.121.87.85:8080/upload/DSC005721360072074379.JPG");
				sale.setPrice("1500万");
			}
		}

		// 仁恒滨江
		if (houseId > 10 && houseId <= 20) {
			sale.setArea("125平米");
			sale.setAvg("76000万/平");
			sale.setDecorating("简装");
			sale.setDirection("南北");
//			sale.setFloor("高");
			sale.setId(houseId);
			sale.setIsEmergent(true);
			sale.setIsFollow(true);
			sale.setOnboardTime(df.parse("12/25/2012").getTime());
			sale.setPicURL("http://42.121.87.85:8080/upload/conew_wp_0014891360070386450.jpg");
			sale.setPrice("900万");
			sale.setResidenceName("仁恒滨江");
			sale.setRoomType("3房2厅");
			sale.setType("公寓");
			sale.setLat(31.228725);
			sale.setLng(121.50664400000005);
			sale.setDistance((double) 1.5);
			sale.setDealPrice("900万");
			if (houseId == 11) {

			}
			if (houseId == 12) {
				sale.setArea("183.7平米");
				sale.setAvg("76000万/平");
				sale.setPicURL("http://42.121.87.85:8080/upload/kitchen1360072622416.jpg");
				sale.setPrice("1500万");
				sale.setDealPrice("1500万");
			}

		}
		// 世茂滨江
		if (houseId > 20 && houseId <= 30) {

			sale.setArea("333平米");
			sale.setAvg("98000万/平");
			sale.setDecorating("简装");
			sale.setDirection("南北");
//			sale.setFloor("高");
			sale.setId(houseId);
			sale.setIsEmergent(true);
			sale.setIsFollow(true);
			sale.setOnboardTime(df.parse("12/25/2012").getTime());
			sale.setPicURL("http://42.121.87.85:8080/upload/waiguan-min1360069884512.jpg");
			sale.setPrice("2700万");
			sale.setResidenceName("世茂滨江花园");
			sale.setRoomType("4房2厅");
			sale.setType("公寓");
			sale.setLat(31.228725);
			sale.setLng(121.50664400000005);
			sale.setDistance((double) 1.5);
			sale.setDealPrice("2700万");
			if (houseId == 21) {

			}
			if (houseId == 22) {
				sale.setArea("183.7平米");
				sale.setAvg("98000万/平");
				sale.setPicURL("http://42.121.87.85:8080/upload/smbj2-min1360070025395.jpg");
				sale.setPrice("2000万");
				sale.setDealPrice("2000万");
			}

		}
		if (houseId > 30) {
			sale.setArea("800 sqm");
			sale.setAvg("$3,312/sqm");
			sale.setDecorating("Fine decoration");
			sale.setDirection("South North");
//			sale.setFloor("高");
			sale.setId(houseId);
			sale.setIsEmergent(true);
			sale.setIsFollow(true);
			sale.setOnboardTime(df.parse("12/25/2012").getTime());
			sale.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/2/2/d/b/picture-uh=daba7d161a4b41baa28a9a9572ba99d0-ps=22dbe6df3bddc765c90bb735bda398-533-Laidley-St-San-Francisco-CA-94131.jpg");
			sale.setPrice("$2,650,000");
			sale.setResidenceName("533 Laidley St");
			sale.setRoomType("4 bed, 3.5 bath Single-Family Home");
			sale.setType("Single-Family Home");
			sale.setLat(37.735542);
			sale.setLng(-122.431244);
			sale.setDistance((double) 1.5);
			sale.setDealPrice("$2,650,000");

			if (houseId == 31) {
				// http://www.trulia.com/property/1084021391-533-Laidley-St-San-Francisco-CA-94131
				sale.setArea("800 sqm");
				sale.setAvg("$3,312/sqm");
				sale.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/2/2/d/b/picture-uh=daba7d161a4b41baa28a9a9572ba99d0-ps=22dbe6df3bddc765c90bb735bda398-533-Laidley-St-San-Francisco-CA-94131.jpg");
				sale.setPrice("$2,650,000");
				sale.setResidenceName("533 Laidley St");
				sale.setRoomType("4 bed, 3.5 bath Single-Family Home");
				sale.setType("Single-Family Home");
				sale.setLat(37.735542);
				sale.setLng(-122.431244);
				sale.setDistance((double) 1.5);
				sale.setDealPrice("$2,650,000");
			}
			if (houseId == 32) {
				// http://www.trulia.com/property/3105899397-661-Peralta-Ave-4-San-Francisco-CA-94110#photo-2
				sale.setArea("600 sqft");
				sale.setAvg("$733/sqft");
				sale.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/b/8/e/0/picture-uh=8fa39250b508e7768d0b96bcc89dcdb-ps=b8e06d5196f7ff583297f5feb853732-661-Peralta-Ave-4-San-Francisco-CA-94110.jpg");
				sale.setPrice("$440,000");
				sale.setResidenceName("661 Peralta Ave #4");
				sale.setRoomType("1 bed, 1 bath 600 sqft Condo");
				sale.setType("Condo");
				sale.setLat(37.742046);
				sale.setLng(-122.408623);
				sale.setDistance((double) 1.7);
				sale.setDealPrice("$440,000");
			}
			if (houseId == 33) {
				// http://www.trulia.com/property/3086087502-729-Congo-St-San-Francisco-CA-94131
				sale.setArea("3,100 sqft");
				sale.setAvg("$704/sqft");
				sale.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.50/5/c/8/c/picture-uh=e8546639f8bdf5c93847f677123e513-ps=5c8cb1e2b0da93be508620f2a0f0b46-729-Congo-St-San-Francisco-CA-94131.jpg");
				sale.setPrice("$2,185,000");
				sale.setResidenceName("729 Congo St ");
				sale.setRoomType("3 bed, 4 bath 3,100 sqft Single-Family Home");
				sale.setType("Single-Family Home");
				sale.setLat(37.735641);
				sale.setLng(-122.441673);
				sale.setDistance((double) 1.7);
				sale.setDealPrice("$2,185,000");
			}
			if (houseId % 10 == 4) {
				// http://www.trulia.com/property/3107344369-149-Teresita-Blvd-San-Francisco-CA-94127
			}
			if (houseId % 10 == 5) {
				// http://www.trulia.com/property/3107407347-33-Clementina-St-5-San-Francisco-CA-94105
			}
			if (houseId % 10 == 6) {
				// http://www.trulia.com/property/3104230824-23-Blair-Ter-San-Francisco-CA-94107
			}
		}

		return sale;
	}
	public HouseRentBean loadRent(int houseId) throws ParseException {
		HouseRentBean rent = new HouseRentBean();

		rent.setAskedCount(78);
		rent.setDealTimeRent(df.parse("12/25/2012").getTime());
		rent.setFansCount(90);
		rent.setIsNew(true);
		rent.setRentStatus(1);

		// 财富海景
		if (houseId > 0 && houseId <= 10) {
			rent.setArea("125平米");
			rent.setDecorating("豪装");
			rent.setDirection("南北");
//			rent.setFloor("高");
			rent.setId(houseId);
			rent.setIsEmergent(true);
			rent.setIsFollow(true);
			rent.setIsRecommend(true);
			rent.setOnboardTime(df.parse("12/25/2012").getTime());
			rent.setPicURL("http://42.121.87.85:8080/upload/caifu1360070216993.JPG");
			rent.setPrice("25000/月");
			rent.setRoomType("3房2厅");
			rent.setType("公寓");
			rent.setLat(31.225681);
			rent.setLng(121.50890400000003);
			rent.setDistance((double) 1.5);
			rent.setCombinedRent(true);
			if (houseId == 1) {

			}
			if (houseId == 2) {
				rent.setArea("250平米");
				rent.setPrice("55000/月");
				rent.setPicURL("http://42.121.87.85:8080/upload/DSC005721360072074379.JPG");
				rent.setPrice("1500万");
			}
		}

		// 仁恒滨江
		if (houseId > 10 && houseId <= 20) {
			rent.setArea("125平米");
			rent.setDecorating("简装");
			rent.setDirection("南北");
//			rent.setFloor("高");
			rent.setId(houseId);
			rent.setIsEmergent(true);
			rent.setIsFollow(true);
			rent.setIsRecommend(true);
			rent.setOnboardTime(df.parse("12/25/2012").getTime());
			rent.setPicURL("http://42.121.87.85:8080/upload/conew_wp_0014891360070386450.jpg");
			rent.setPrice("15000/月");
			rent.setRoomType("3房2厅");
			rent.setType("公寓");
			rent.setLat(31.228725);
			rent.setLng(121.50664400000005);
			rent.setDistance((double) 1.5);
			rent.setCombinedRent(true);
			if (houseId == 11) {

			}
			if (houseId == 12) {
				rent.setArea("183.7平米");
				rent.setPicURL("http://42.121.87.85:8080/upload/kitchen1360072622416.jpg");
				rent.setPrice("55000/月");
			}

		}
		// 世茂滨江
		if (houseId > 20 && houseId <= 30) {

			rent.setArea("333平米");
			rent.setDecorating("简装");
			rent.setDirection("南北");
//			rent.setFloor("高");
			rent.setId(houseId);
			rent.setIsEmergent(true);
			rent.setIsFollow(true);
			rent.setIsRecommend(true);
			rent.setOnboardTime(df.parse("12/25/2012").getTime());
			rent.setPicURL("http://42.121.87.85:8080/upload/waiguan-min1360069884512.jpg");
			rent.setPrice("80000/月");
			rent.setRoomType("4房2厅");
			rent.setType("公寓");
			rent.setLat(31.228725);
			rent.setLng(121.50664400000005);
			rent.setDistance((double) 1.5);
			rent.setCombinedRent(true);
			if (houseId == 21) {

			}
			if (houseId == 22) {
				rent.setArea("183.7平米");
				rent.setPicURL("http://42.121.87.85:8080/upload/smbj2-min1360070025395.jpg");
				rent.setPrice("55000/月");
			}

		}
		if (houseId > 30) {
			rent.setArea("800 sqm");
			rent.setDecorating("Fine decoration");
			rent.setDirection("South North");
//			rent.setFloor("高");
			rent.setId(houseId);
			rent.setIsEmergent(true);
			rent.setIsFollow(true);
			rent.setIsRecommend(true);
			rent.setOnboardTime(df.parse("12/25/2012").getTime());
			rent.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/2/2/d/b/picture-uh=daba7d161a4b41baa28a9a9572ba99d0-ps=22dbe6df3bddc765c90bb735bda398-533-Laidley-St-San-Francisco-CA-94131.jpg");
			rent.setPrice("$1500/m");
			rent.setRoomType("4 bed, 3.5 bath Single-Family Home");
			rent.setType("Single-Family Home");
			rent.setLat(37.735542);
			rent.setLng(-122.431244);
			rent.setDistance((double) 1.5);
			rent.setCombinedRent(true);

			if (houseId == 31) {
				// http://www.trulia.com/property/1084021391-533-Laidley-St-San-Francisco-CA-94131
				rent.setArea("800 sqm");
				rent.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/2/2/d/b/picture-uh=daba7d161a4b41baa28a9a9572ba99d0-ps=22dbe6df3bddc765c90bb735bda398-533-Laidley-St-San-Francisco-CA-94131.jpg");
				rent.setPrice("$1500/m");
				rent.setRoomType("4 bed, 3.5 bath Single-Family Home");
				rent.setType("Single-Family Home");
				rent.setLat(37.735542);
				rent.setLng(-122.431244);
				rent.setDistance((double) 1.5);
			}
			if (houseId == 32) {
				// http://www.trulia.com/property/3105899397-661-Peralta-Ave-4-San-Francisco-CA-94110#photo-2
				rent.setArea("600 sqft");
				rent.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.49/b/8/e/0/picture-uh=8fa39250b508e7768d0b96bcc89dcdb-ps=b8e06d5196f7ff583297f5feb853732-661-Peralta-Ave-4-San-Francisco-CA-94110.jpg");
				rent.setPrice("$1500/m");
				rent.setRoomType("1 bed, 1 bath 600 sqft Condo");
				rent.setType("Condo");
				rent.setLat(37.742046);
				rent.setLng(-122.408623);
				rent.setDistance((double) 1.7);
			}
			if (houseId == 33) {
				// http://www.trulia.com/property/3086087502-729-Congo-St-San-Francisco-CA-94131
				rent.setArea("3,100 sqft");
				rent.setPicURL("http://thumbs.trulia-cdn.com/pictures/thumbs_4/ps.50/5/c/8/c/picture-uh=e8546639f8bdf5c93847f677123e513-ps=5c8cb1e2b0da93be508620f2a0f0b46-729-Congo-St-San-Francisco-CA-94131.jpg");
				rent.setPrice("$1500/m");
				rent.setRoomType("3 bed, 4 bath 3,100 sqft Single-Family Home");
				rent.setType("Single-Family Home");
				rent.setLat(37.735641);
				rent.setLng(-122.441673);
				rent.setDistance((double) 1.7);
			}
			if (houseId % 10 == 4) {
				// http://www.trulia.com/property/3107344369-149-Teresita-Blvd-San-Francisco-CA-94127
			}
			if (houseId % 10 == 5) {
				// http://www.trulia.com/property/3107407347-33-Clementina-St-5-San-Francisco-CA-94105
			}
			if (houseId % 10 == 6) {
				// http://www.trulia.com/property/3104230824-23-Blair-Ter-San-Francisco-CA-94107
			}
		}
		return rent;
	}

	public List findSales(int residenceId) throws ParseException {
		List<HouseSaleBean> sale = new ArrayList<HouseSaleBean>();
		if (residenceId == 1) {
			sale.add(loadSale(1));
			sale.add(loadSale(2));
		}
		if (residenceId == 2) {
			sale.add(loadSale(11));
			sale.add(loadSale(12));
		}
		if (residenceId == 3) {
			sale.add(loadSale(21));
			sale.add(loadSale(22));
		}
		if (residenceId == 4) {
			sale.add(loadSale(31));
			sale.add(loadSale(32));
			sale.add(loadSale(33));
			sale.add(loadSale(34));
		}
		if (residenceId > 4) {
			sale.add(loadSale(1));
			sale.add(loadSale(2));
			sale.add(loadSale(3));
			sale.add(loadSale(4));
			sale.add(loadSale(5));
			sale.add(loadSale(6));
			sale.add(loadSale(7));
			sale.add(loadSale(8));
			sale.add(loadSale(9));
			sale.add(loadSale(10));
			sale.add(loadSale(11));
			sale.add(loadSale(12));
			sale.add(loadSale(13));
			sale.add(loadSale(14));
			sale.add(loadSale(15));
			sale.add(loadSale(16));
			sale.add(loadSale(17));
			sale.add(loadSale(18));
			sale.add(loadSale(19));
			sale.add(loadSale(20));
			sale.add(loadSale(21));
			sale.add(loadSale(22));
			sale.add(loadSale(23));
			sale.add(loadSale(24));
			sale.add(loadSale(25));
			sale.add(loadSale(26));
			sale.add(loadSale(27));
			sale.add(loadSale(28));
			sale.add(loadSale(29));
		}

		return sale;
	}

	public List findRents(int residenceId) throws ParseException {
		List<HouseRentBean> rent = new ArrayList<HouseRentBean>();
		if (residenceId == 1) {
			rent.add(loadRent(1));
			rent.add(loadRent(2));
		}
		if (residenceId == 2) {
			rent.add(loadRent(11));
			rent.add(loadRent(12));
		}
		if (residenceId == 3) {
			rent.add(loadRent(21));
			rent.add(loadRent(22));
		}
		if (residenceId == 4) {
			rent.add(loadRent(31));
			rent.add(loadRent(32));
			rent.add(loadRent(33));
			rent.add(loadRent(34));
		}
		if (residenceId > 4) {
			rent.add(loadRent(1));
			rent.add(loadRent(2));
			rent.add(loadRent(3));
			rent.add(loadRent(4));
			rent.add(loadRent(5));
			rent.add(loadRent(6));
			rent.add(loadRent(7));
			rent.add(loadRent(8));
			rent.add(loadRent(9));
			rent.add(loadRent(10));
			rent.add(loadRent(11));
			rent.add(loadRent(12));
			rent.add(loadRent(13));
			rent.add(loadRent(14));
			rent.add(loadRent(15));
			rent.add(loadRent(16));
			rent.add(loadRent(17));
			rent.add(loadRent(18));
			rent.add(loadRent(19));
			rent.add(loadRent(20));
			rent.add(loadRent(21));
			rent.add(loadRent(22));
			rent.add(loadRent(23));
			rent.add(loadRent(24));
			rent.add(loadRent(25));
			rent.add(loadRent(26));
			rent.add(loadRent(27));
			rent.add(loadRent(28));
			rent.add(loadRent(29));
		}
		return rent;
	}
}
