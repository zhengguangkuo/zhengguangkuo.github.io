package com.mt.app.payment.tools;

/**
 * 
 * 
 * @Description:�����жϵĹ�����
 * 
 * @author:dw
 * 
 * @time:2013-9-27 ����3:46:29
 */
public class DistanceUtil {
	/**
	 * �÷����жϴ����ֵ�Ƿ�Ϊ���ڵ�������� ������Ƿ���true
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
	 * �÷���������������վ�����ʾ ���Ҵ��˵�λ km��������Ǿ��� ���� ������δ֪��
	 * @param str
	 * @return
	 */
	public static String getDistance(String str) {
		if (isDistance(str)) {
			return Math.round(Double.valueOf(str))/1000.0 + "km";
		} else {
			return "����δ֪";
		}
	}
	/**
	 * �÷���������������վ�����ʾ ���Ҵ��˵�λ km��������Ǿ��� ���� ������δ֪��
	 * @param str
	 * @return
	 */
	public static String getDistance(double str) {
		return getDistance(String.valueOf(str));
	}
}
