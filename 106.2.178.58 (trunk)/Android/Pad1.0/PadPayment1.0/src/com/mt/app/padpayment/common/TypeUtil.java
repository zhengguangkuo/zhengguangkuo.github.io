package com.mt.app.padpayment.common;
/**
 * ʶ���׵İ�����
 */
public class TypeUtil {

	
	public static String getType(String type, String number) {

		if (type != null && number != null && !type.equals("") && !number.equals("")) {
			
		
		if (type.equals("0200")) { // ������������Ϣ

			if (number.equals("310000")) { // ����ѯ
				return "����ѯ";
			} else if (number.equals("000000")) { // ����
				return "��        ��";
			} else if (number.equals("200000")) { // ���ѳ���
				return "���ѳ���";
			} else if (number.equals("110000")) { // ���ֲ�ѯ
				return "���ֲ�ѯ";
			} else if (number.equals("100000")) { // ��������
				return "��������";
			} else if (number.equals("300000")) { // ���ֳ���
				return "���ֳ���";
			} else if (number.equals("020000")) { // �Żݾ�һ�
				return "��        ��";
			} else if (number.equals("021000")) { // �Ż�ȯ�һ�����
				return "�һ�����";
			}

		} else if (type.equals("0220")) { // ����֪ͨ����Ϣ

			if (number.equals("200000")) { // �˻�
				return "��        ��";
			} else if (number.equals("010000")) { // ����
				return "��        ��";
			} else if (number.equals("022000")) { // �Żݾ�����
				return "��        ��";
			} else if (number.equals("021000")) { // �Żݾ�����
				return "��        ��";
			}

		} 
		}
		return "";
	}

}
