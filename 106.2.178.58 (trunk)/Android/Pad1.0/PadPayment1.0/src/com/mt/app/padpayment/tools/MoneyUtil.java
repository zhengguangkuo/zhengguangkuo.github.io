package com.mt.app.padpayment.tools;

/**
 * 
 * 
 * @Description:���ת���İ�����
 * 
 * @author:dw
 * 
 * @time:2013-8-29 ����7:49:09
 */
public class MoneyUtil {
	/**
	 * �������ַ���ת��Ϊ���
	 * 
	 * @param money
	 *            ��Ҫת�����ַ���
	 * @return ת����Ľ��
	 */
	public static String getMoney(String money) {
		if (money.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
			if (money.contains(".")) {
				return money;
			}
			if (money.length() == 0) {
				return "";
			} else if (money.length() == 1) {
				return "0.0" + money;
			} else if (money.length() == 2) {
				return "0." + money;
			} else {
				return Integer.parseInt(money.substring(0, money.length() - 2)) + "."
						+ money.substring(money.length() - 2, money.length());
			}
		} 
		return " ";
	}
}
