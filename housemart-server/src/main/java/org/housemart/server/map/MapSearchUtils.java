/**
 * Created on 2012-12-16
 * 
 */
package org.housemart.server.map;

/**
 * @author pqin
 */
public class MapSearchUtils {

	public static final double PI = 3.14159265358979323; // 圆周率
	public static final double EARTH_RADIUS = 6371229; // 地球的半径

	// 角度换算弧度
	public static double degreeToRadian(double degree) {
		return degree * PI / 180;
	}

	// 弧度换算角度
	public static double radianToDegree(double radian) {
		return radian * 180 / PI;
	}

	// 球面距离
	public static double getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
		double radLat1 = degreeToRadian(lat_a);
		double radLat2 = degreeToRadian(lat_b);
		double a = radLat1 - radLat2;
		double b = degreeToRadian(lng_a) - degreeToRadian(lng_b);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000.0;
		return s;
	}

	// 根据距离换算同经度，纬度的差值
	public static double getLatDiff(double distance) {
		return radianToDegree(distance / EARTH_RADIUS);
	}

	// 根据距离换算同纬度，经度的差值
	public static double getLngDiff(double lat, double distance) {
		double r_lat = degreeToRadian(lat);
		return radianToDegree(Math.acos(1 - (1 - Math.cos(distance / EARTH_RADIUS)) / Math.pow(Math.cos(r_lat), 2)));
	}

	public static void main(String[] args) {
		System.out.println("紫荆园 -- 师大科技园 : "
				+ MapSearchUtils.getDistance(121.41787380103, 31.163528654627, 121.42180250759, 31.164104572103));
		System.out.println("紫荆园 -- 古美小区 : "
				+ MapSearchUtils.getDistance(121.41787380103, 31.163528654627, 121.405204, 31.160639));

		System.out.println("紫荆园 -- 师大科技园  经度差值:" + MapSearchUtils.getLngDiff(121.4, 500));
		// 桂莘 121.41447591898,31.166274259198
		System.out.println("紫荆园 -- 桂莘小区  纬度差值:" + MapSearchUtils.getLatDiff(500));

		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182515, 121.520733));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182945, 121.520449));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182499, 121.524352));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182878, 121.524007));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182187, 121.522871));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182895, 121.522095));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182895, 121.522095));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182878, 121.524007));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182878, 121.524007));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182878, 121.524007));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182878, 121.524007));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182878, 121.524007));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182878, 121.524007));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18294, 121.523997));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18292, 121.524017));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.181934, 121.523676));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185491, 121.52946));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.1851, 121.529674));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.181644, 121.518483));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.181822, 121.519198));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.179487, 121.517353));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180572, 121.517837));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.181123, 121.519981));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.187999, 121.518637));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18859, 121.518309));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.188556, 121.516711));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185881, 121.518897));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.187405, 121.520787));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185448, 121.519241));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18743, 121.516898));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.1863, 121.520097));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.186159, 121.519626));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.186296, 121.519651));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18639, 121.519881));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.186609, 121.520723));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.188274, 121.516681));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.189657, 121.530554));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.188755, 121.530722));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.188674, 121.530717));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182842, 121.517566));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.183132, 121.518736));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.183467, 121.518754));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185881, 121.518897));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185448, 121.519241));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.186145, 121.51927));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.17903, 121.515922));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180241, 121.524395));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.1863, 121.520097));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.186159, 121.519626));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.1881673, 121.5166297));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.175328, 121.517153));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.173656, 121.516422));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.177663, 121.517006));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.175838, 121.5154));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.175213, 121.518615));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.17424, 121.517832));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.176326, 121.51653));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.174723, 121.513737));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.178322, 121.511581));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.179542, 121.512312));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.177218, 121.511469));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.177525, 121.512468));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.178208, 121.512292));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18073, 121.515601));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.181064, 121.515269));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.179203, 121.51597));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.184074, 121.511388));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185954, 121.511504));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185381, 121.51426));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.186376, 121.512958));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18539, 121.511923));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.184954, 121.511762));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18455, 121.513817));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.186221, 121.515068));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185677, 121.514463));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185771, 121.514519));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.175789, 121.511308));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.183537, 121.513326));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.183572, 121.514254));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182845, 121.513752));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185377, 121.511689));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18209, 121.514648));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.178322, 121.511581));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.179542, 121.512312));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.178825, 121.510844));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.177218, 121.511469));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.178208, 121.512292));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.177525, 121.512468));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.184074, 121.511388));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18539, 121.511923));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18455, 121.513817));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185698, 121.513658));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.184578, 121.514));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185645, 121.514086));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185479, 121.514165));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18053, 121.510242));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180405, 121.511331));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180154, 121.510314));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180232, 121.510459));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182453, 121.510883));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.181727, 121.511616));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.183407, 121.511196));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.188446, 121.531056));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.181074, 121.516515));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180943, 121.51493));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180311, 121.516745));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.186236, 121.516161));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.18603, 121.516724));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185897, 121.516617));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.177615, 121.518563));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.177624, 121.519358));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180165, 121.514419));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180503, 121.514011));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180236, 121.514916));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.185266, 121.520546));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.184559, 121.520607));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180702, 121.512741));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180313, 121.513086));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.181276, 121.515555));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180286, 121.513139));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.188494, 121.515732));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.188589, 121.516163));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.188513, 121.516287));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.184665, 121.515751));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.183462, 121.516582));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.183796, 121.515432));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.182947, 121.515042));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.183635, 121.514521));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.175849, 121.53005));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.17868, 121.513473));
		System.out.println(MapSearchUtils.getDistance(31.182515, 121.520733, 31.180286, 121.513139));
	}
}
