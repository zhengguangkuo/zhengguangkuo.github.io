package com.mt.app.payment.tools;

/**
 * 
 * 
 * @Description:距离判断的工具类
 * 
 * @author:dw
 * 
 * @time:2013-9-27 下午3:46:29
 */
public class DistanceUtil {
	/**
	 * 该方法判断传入的值是否为大于等于零的数 ，如果是返回true
	 * @param str
	 * @return
	 */
	public static boolean isDistance(String str) {
		if (str != null) {
			try {
				double num = Double.valueOf(str);
				if (num < 0) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 该方法将传入的数按照距离显示 并且带了单位 km，如果不是距离 返回 “距离未知”
	 * @param str
	 * @return
	 */
	public static String getDistance(String str) {
		if (isDistance(str)) {
			return Math.round(Double.valueOf(str))/1000.0 + "km";
		} else {
			return "距离未知";
		}
	}
	/**
	 * 该方法将传入的数按照距离显示 并且带了单位 km，如果不是距离 返回 “距离未知”
	 * @param str
	 * @return
	 */
	public static String getDistance(double str) {
		return getDistance(String.valueOf(str));
	}
}
