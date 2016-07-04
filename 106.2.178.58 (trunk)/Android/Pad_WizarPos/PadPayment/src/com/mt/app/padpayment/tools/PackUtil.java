package com.mt.app.padpayment.tools;

/**
 * 
 * 
 * @Description:打包时用到的工具类
 * 
 * @author:dw
 * 
 * @time:2013-7-19 下午1:28:21
 */
public class PackUtil {

	/**
	 * 补位方法，将位数不够的字段 补充完整
	 * 
	 * @param field
	 *            需要补位的字段
	 * @param length
	 *            该字段补位后的长度
	 * @param isLeft
	 *            是否是左补位
	 * @param fillString
	 *            补位时填充的字符
	 * @return
	 */
	public static String fillField(String field, int length, boolean isLeft,
			String fillString) {

		if (field != null && field.length() > 0) {

			if (field.contains(".")) { // 如果是金额，去掉小数点占的位数
				String[] arr = field.split("\\.");
				if (arr[1] == null) {
					arr[1] = "00";

				} else if (arr[1].length() == 1) {
					arr[1] = arr[1] + "0";

				}
				field = arr[0] + arr[1];

			}
			int fLength = field.length();
			if (fLength < length) {
				String str = "";
				for (int i = 0; i < length - fLength; i++) { // 需要补的位数
					str += fillString;
				}
				if (isLeft) {
					return str + field;
				}
				return field + str;
			}
		} else {
			field = null;
		}
		return field;
	}

	/**
	 * 金额的格式转换方法，例如将十二位格式的金额000000120089 转换为 1200.89
	 * 
	 * @param num
	 *            已经左补过“0”的十二位格式的金额
	 * @return 转换后的金额
	 */
	public static String format(String num) {
		int index = 0;
		char[] ch = num.toCharArray();
		for (char c : ch) {
			if (c != '0') {
				break;
			}
			index++;
		}
		StringBuilder sb = new StringBuilder();
		if (index == num.length() - 2) {
			sb.append("0.");
			String lastNum = num.substring(num.length() - 2, num.length());
			sb.append(lastNum);
		} else if (index == num.length() - 1) {
			sb.append("0.0");
			String lastNum = num.substring(index);
			sb.append(lastNum);
		} else {
			String beginNum = num.substring(index, num.length() - 2);
			String lastNum = num.substring(num.length() - 2, num.length());
			sb.append(beginNum);
			sb.append(".");
			sb.append(lastNum);
		}
		return sb.toString();
	}

	/**
	 * 补位方法，将位数不够的字段 补充完整
	 * 
	 * @param field
	 *            需要补位的字段
	 * @param length
	 *            该字段补位后的长度
	 * @param isLeft
	 *            是否是左补位
	 * @param fillString
	 *            补位时填充的字符
	 * @return
	 */
	public static String fillFieldMoney(String field, int length,
			boolean isLeft, String fillString) {

		int fLength = field.length();
		if (fLength < length) {
			String str = "";
			for (int i = 0; i < length - fLength; i++) { // 需要补的位数
				str += fillString;
			}
			if (isLeft) {
				return str + field;
			}
			return field + str;
		}

		return field;
	}
}
