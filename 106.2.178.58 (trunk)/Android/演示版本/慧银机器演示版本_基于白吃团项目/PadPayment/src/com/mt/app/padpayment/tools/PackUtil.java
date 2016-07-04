package com.mt.app.padpayment.tools;

/**
 * 
 * 
 * @Description:���ʱ�õ��Ĺ�����
 * 
 * @author:dw
 * 
 * @time:2013-7-19 ����1:28:21
 */
public class PackUtil {

	/**
	 * ��λ��������λ���������ֶ� ��������
	 * 
	 * @param field
	 *            ��Ҫ��λ���ֶ�
	 * @param length
	 *            ���ֶβ�λ��ĳ���
	 * @param isLeft
	 *            �Ƿ�����λ
	 * @param fillString
	 *            ��λʱ�����ַ�
	 * @return
	 */
	public static String fillField(String field, int length, boolean isLeft,
			String fillString) {

		if (field != null && field.length() > 0) {

			if (field.contains(".")) { // ����ǽ�ȥ��С����ռ��λ��
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
				for (int i = 0; i < length - fLength; i++) { // ��Ҫ����λ��
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
	 * ���ĸ�ʽת�����������罫ʮ��λ��ʽ�Ľ��000000120089 ת��Ϊ 1200.89
	 * 
	 * @param num
	 *            �Ѿ��󲹹���0����ʮ��λ��ʽ�Ľ��
	 * @return ת����Ľ��
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
	 * ��λ��������λ���������ֶ� ��������
	 * 
	 * @param field
	 *            ��Ҫ��λ���ֶ�
	 * @param length
	 *            ���ֶβ�λ��ĳ���
	 * @param isLeft
	 *            �Ƿ�����λ
	 * @param fillString
	 *            ��λʱ�����ַ�
	 * @return
	 */
	public static String fillFieldMoney(String field, int length,
			boolean isLeft, String fillString) {

		int fLength = field.length();
		if (fLength < length) {
			String str = "";
			for (int i = 0; i < length - fLength; i++) { // ��Ҫ����λ��
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
