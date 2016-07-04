package com.mt.app.padpayment.common;

import java.math.BigDecimal;

/**
 * 
 * 
 * @Description:计算double 帮助类，提供double的加减乘除算法
 * 
 * @author:dw
 * 
 * @time:2013-8-28 下午4:59:07
 */
public class MathUtil {

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static String add(String v1, String v2) {
		if (v1 == null) {
			v1 = "0";
		}
		if (v2 == null) {
			v2 = "0";
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).doubleValue() + "";
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static String sub(String v1, String v2) {
//		if (v1 == null) {
//			v1 = "0";
//		}
//		if (v2 == null) {
//			v2 = "0";
//		}
//		if (v2.equals("0.00")){
//			return v1;
//		}
//		BigDecimal b1 = new BigDecimal(v1);
//		BigDecimal b2 = new BigDecimal(v2);
//		return b1.subtract(b2).doubleValue() + "";
		
			if (v1 == null) {
				v1 = "0";
			}
			if (v2 == null) {
				v2 = "0";
			}
			if (v2.equals("0.00")){
				return v1;
			}
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			if (b1.equals(b2) ||b1.min(b2).equals(b1) ) {
				return "0.00";  
			}
			return b1.subtract(b2).doubleValue() + "";
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static String mul(String v1, String v2) {
		if (v1 == null) {
			v1 = "0";
		}
		if (v2 == null) {
			v2 = "0";
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue() + "";
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后2位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static String div(String v1, String v2) {

		if (v1 == null) {
			v1 = "0";
		}
		if (v2 == null) {
			v2 = "0";
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";

	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static String round(String v, int scale) {
		if (v == null) {
			v = "0";
		}

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue()
				+ "";
	}
}
