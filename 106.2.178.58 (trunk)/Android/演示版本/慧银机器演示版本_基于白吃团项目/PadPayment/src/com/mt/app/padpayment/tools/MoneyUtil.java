package com.mt.app.padpayment.tools;

/**
 * 
 * 
 * @Description:金额转换的帮助类
 * 
 * @author:dw
 * 
 * @time:2013-8-29 下午7:49:09
 */
public class MoneyUtil {
	/**
	 * 将数字字符串转换为金额
	 * 
	 * @param money
	 *            需要转换的字符串
	 * @return 转换后的金额
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
