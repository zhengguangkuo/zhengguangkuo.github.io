package com.mt.android.message.mdo;

import java.util.HashMap;
import java.util.Map;

public class MessageField {
	private static Map<String, String> fieldmap = new HashMap<String, String>();

	static{
		defineFiled("msgId", 1); //MessageId��Ϣ����
		defineFiled("cardNo", 2);//����
		defineFiled("processCode", 3);//���״�����
		defineFiled("transAmount", 4);//���׽��
		defineFiled("discountAmount", 5);//�ֿ۽��
		defineFiled("sysTraceAuditNum", 11);//�ܿ���ϵͳ���ٺ�
		defineFiled("localTransTime", 12);//�ܿ������ڵ�ʱ��
		defineFiled("localTransDate", 13);//�ܿ������ڵ�����
		defineFiled("dateExpired", 14);//����Ч��
		defineFiled("settleDate", 15);//��������		
		defineFiled("serviceEntryMode", 22);//��������뷽ʽ��		
		defineFiled("cardSequenceNum", 23);//�����к�		
		defineFiled("serviceConditionMode", 25); //�����������
        defineFiled("servicePINCaptureCode", 26);//�����PIN��ȡ��
        defineFiled("acqInstIdentCode", 32);//������ʶ��
        defineFiled("track2", 35);//�ŵ�2����
        defineFiled("track3", 36);//�ŵ�3����
        defineFiled("retReferNum", 37);//�����ο���
        defineFiled("authorIdentResp", 38);//��Ȩ��ʶӦ����
        defineFiled("respCode", 39);//Ӧ����
        defineFiled("cardAcceptTermIdent", 41);//�ܿ����ն˱�ʶ��
        defineFiled("cardAcceptIdentcode", 42);//�ܿ�����ʶ��
        defineFiled("cardAcceptLocal", 43);//�ܿ������Ƶ�ַ
        defineFiled("additRespData", 44);//������Ӧ����/���������ʶ
        defineFiled("additDataPrivate", 48);//�������� - ˽��
        defineFiled("currencyTransCode", 49);//���׻��Ҵ��� 
        defineFiled("pinData", 52);//���˱�ʶ������
        defineFiled("securityRelatedControl", 53);//��ȫ������Ϣ
        defineFiled("balanceAmount", 54);//���ӽ��
        defineFiled("organId", 56);//��ȯ������ʶ
        defineFiled("swapCode", 57);//�һ���
        defineFiled("couponsAdvertId", 58);//��������ʶ
        defineFiled("reservedPrivate1", 59);//�Զ�����1
        defineFiled("reservedPrivate2", 60);//�Զ�����2
        defineFiled("originalMessage", 61);//ԭʼ��Ϣ��
        defineFiled("reservedPrivate3", 62);//�Զ�����3
        defineFiled("reservedPrivate4", 63);//�Զ�����4
        defineFiled("messageAuthentCode", 64);//mac
	}
    //���������ƻ�ȡ����
	public static int getFiledNo(String fieldName) {
		if (fieldmap.get(fieldName) != null) {
			return Integer.valueOf(fieldmap.get(fieldName));
		} else {
			return -1;
		}
	}
	
	private static void defineFiled(String filedName, int index){
		fieldmap.put(filedName.toUpperCase(), index+"");
	}

}
