package com.mt.app.payment.tools;

import android.text.TextUtils;


/**
 * ClassName:GetDistance <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2013-8-21 下午5:14:04 <br/>
 * @author   dell
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class GetDistance {
	private static double EARTH_RADIUS = 6378.137; 

	 

    private static double Rad(double d) { 

        return d * Math.PI / 180.0; 

    } 

    //坐标字符串是经度在前
    private double EARTH_RADIUS_KM=6378.137;//地球半径
    public static double getDistance(String point, String coornidate) { 
    	double lng1;//经度
    	double lat1;//纬度
    	double lng2;
    	double lat2;
    	//double swp;
    	if(TextUtils.isEmpty(point)||TextUtils.isEmpty(coornidate)){
    		return -1.0;
    	}
    	lng1=Double.valueOf(point.split(",")[0].trim());
    	lat1=Double.valueOf(point.split(",")[1].trim());
    	lng2=Double.valueOf(coornidate.split(",")[0].trim());
    	lat2=Double.valueOf(coornidate.split(",")[1].trim());
    	/*if(lng1<lat1){
    		swp=lng1;
    		lng1=lat1;
    		lat1=swp;
    	}*/
    	 double radLat1 = Rad(lat1);
	        double radLat2 = Rad(lat2);
	        double a = radLat1 - radLat2;
	        double b = Rad(lng1) - Rad(lng2);
	        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	         Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b/2),2)));
	         s = s * 6378.137;
	         s=s*1000;
	         s=Math.round(s);
	        return s;

    } 
  
}
