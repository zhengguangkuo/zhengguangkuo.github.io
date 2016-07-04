package com.mt.app.padpayment.common;

import java.math.BigDecimal;

/**
 * 
 * 
 * @Description:����double �����࣬�ṩdouble�ļӼ��˳��㷨
 * 
 * @author:dw
 * 
 * @time:2013-8-28 ����4:59:07
 */
public class MathUtil {

	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������ĺ�
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
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������Ĳ�
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
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������Ļ�
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
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ�� С�����Ժ�2λ���Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ������������
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
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
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
