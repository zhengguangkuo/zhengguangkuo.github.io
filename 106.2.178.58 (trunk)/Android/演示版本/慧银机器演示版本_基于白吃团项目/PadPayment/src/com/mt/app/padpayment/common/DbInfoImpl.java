package com.mt.app.padpayment.common;

import com.mt.android.db.IDbInfo;

public class DbInfoImpl implements IDbInfo {

	public static String TableNames[] = { 
		"TBL_RESPONSE_CODE",// ��Ӧ����ձ�
		"TBL_FlOW",// ��ˮ��
		"TBL_TMPFlOW",// ��ʱ��ˮ��
		"TBL_REVERSAL",// ������
		"TBL_PARAMETER", //������
		"TBL_TMPPARAMETER",//��ʱ������
		"TBL_ADMIN", //��Ա�����
		"TBL_PROTOCL", //Э�����ñ�
		"TBL_MANAGE"   //����ǩ����
	};// ����

	/**
	 * ���ݿ���Ҫ�õ����ֶ�����
	 *    _ID                          id
	 *    MSG_ID                       ��������
	 *    RESP_CODE                    Ӧ����
	 *    MESSAGE                      Ӧ����Ϣ
	 *    PROCESS_CODE                 ���״�����
	 *    CARD_NO                      ֧������
	 *    TRACK2					       ��������
	 *    ORIGIN_AMOUNT                ԭ���׽������ܶ
	 *    TRANS_AMOUNT                 ���׽�ʵ�ս�
	 *    SYS_TRACE_AUDIT_NUM            �ܿ���ϵͳ���ٺţ�������ˮ�ţ�
	 *    DATE_EXPIRED                 ����Ч��
	 *    SERVICE_ENTRY_MODE           ��������뷽ʽ��
	 *    SERVICE_CONDITION_MODE       �����������
	 *    SERVICE_PIN_CAPTURE_CODE       �����PIN��ȡ��
	 *    RET_REFER_NUM                  �����ο��ţ����ײο��ţ�
	 *    CARD_ACCEPT_TERM_IDENT         �ܿ����ն˱�ʶ��
	 *    CARD_ACCEPT_IDENTCODE        �ܿ�����ʶ��
	 *    AUTHOR_IDENT_RESP              ��Ȩ��
	 *    CURRENCY_TRANS_CODE          ���׻��Ҵ���
	 *    PIN_DATA                     ���˱�ʶ������
	 *    SECURITY_RELATED_CONTROL     ��ȫ������Ϣ
	 *    RESERVED_PRIVATE1                                Ӧ�ñ�ʶ
	 *    RESERVED_PRIVATE2                                ���κ� 
	 *    ORIGINAL_MESSAGE             ԭʼ��Ϣ��
	 *    MESSAGE_AUTHENT_CODE         MAC
	 *    DISCOUNT_AMOUNT              �Ż�ȯ�ֿ۽��    ���ۿ۽�
	 *    VIP_AMOUNT                   ��Ա���ۿ۽��
	 *    LOCAL_TRANS_TIME             �ܿ������ڵ�ʱ��
	 *    LOCAL_TRANS_DATE             �ܿ������ڵ����ڣ��������ڣ�
	 *    SETTLE_DATE                  ��������
	 *    ACQ_INST_IDENT_CODE            ������ʶ��
	 *    ADDITRESPDATA                ��ȯ������ʶ
	 *    SWAPCODE_1                                                 �һ���
	 *    ADDIT_RESP_DATA              ������Ӧ����
	 *    RESERVED_PRIVATE3                               ������
	 *    RESERVED_PRIVATE4                                Ӧ����Ϣ
	 *    FLUSH_OCUNT                  ��������
	 *    FLUSH_LASTTIME               ����ʱ��
	 *    FLUSH_RESULT                 �������  
	 *    CARD_ACCEPT_LOCAL            �ܿ������Ƶ�ַ
	 *    
	 *    APP_DISCOUNT                 ֧�����ۿ���
	 *    COUPONS_IDS                  �Ż�ȯ���
	 *    COUPONS_TYPES                �Ż�ȯ����
	 *    
	 *    ---------TBL_PARAMETER   ������-------
	 *    TYPE                         ��������    1.ǩ�������    2.���������  3.��־�����   4.�汾��
	 *    PARA_NAME                    ������
	 *    PARA_VALUE                   ����ֵ
	 *    PARA_EXPLAIN                 ����˵��
	 *    
	 *    
	 *    
	 *    
	 *    
	 *    ---------TBL_TMPPARAMETER  ��ʱ������-------
	 *    TYPE                         ��������     1.ǩ�������    2.���������  
	 *    PARA_NAME                    ������
	 *    PARA_VALUE                   ����ֵ
	 *    PARA_EXPLAIN                 ����˵��
	 *    
	 *    
	 *    
	 *    
	 *    ---------TBL_ADMIN   ��Ա�����-------
	 *    USER_ID                       �û����
	 *    USER_NAME                     �û���
	 *    PASSWORD                      ����
	 *    LIMITS                        Ȩ��             1 ��Ա    2����
	 *    
	 *    ---------TBL_PROTOCL Э�����ñ� ----------
	 *   ID      Э������ 
	 *   TYPE    Э������� 
	 *   ASYNMODE
	 *   MODE    �����ӻ��Ƕ�����  0������ 1������
	 *   HOST    Э���IP
	 *   PORT      Э��Ķ˿�
	 *   ENCODING  ͨѶ��ʹ�õı��� 
	 *   POLICY   ��д���� 
	 *   READTIMEOUT  ����ʱʱ�� 
	 *   SERVERSIDE   �Ƿ���server�� 
	 *   COOLPOOLSIZE  �����߳���
	 *   MAXINUMPOOLSIZE  ����߳����� 
	 *    
	 *    
	 *    
	 */
	public static String FieldNames[][] = {
			{ "_ID", "RESP_CODE", "MESSAGE" }, // ��Ӧ����ձ�
			{ "_ID", "PROCESS_CODE", "TRACK2","TRACK2_1","ORGAN_ID","ORGAN_ID_1",
					"TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM", "DATE_EXPIRED",
					"SERVICE_ENTRY_MODE", "SERVICE_CONDITION_MODE",
					"SERVICE_PIN_CAPTURE_CODE", "RET_REFER_NUM", "CARD_ACCEPT_TERM_IDENT", 
					"CARD_ACCEPT_IDENTCODE", "AUTHOR_IDENT_RESP","AUTHOR_IDENT_RESP_1" , "CURRENCY_TRANS_CODE", 
					"PIN_DATA", "SECURITY_RELATED_CONTROL", "RESERVED_PRIVATE1","RESERVED_PRIVATE2",
					"MESSAGE_AUTHENT_CODE", "DISCOUNT_AMOUNT","DISCOUNT_AMOUNT_1","SWAP_CODE",
					"VIP_AMOUNT", "LOCAL_TRANS_TIME_1","ADDIT_RESP_DATA",
					"LOCAL_TRANS_DATE_1", "SETTLE_DATE_1", "ACQ_INST_IDENT_CODE_1",
					"RESP_CODE", "ADDIT_RESP_DATA_1", "RESERVED_PRIVATE3",
					"RESERVED_PRIVATE4", "ORIGIN_AMOUNT", "MSG_ID","ORIGINAL_MESSAGE",
					"RET_REFER_NUM_1","RESP_CODE_1","CARD_ACCEPT_TERM_IDENT_1","CARD_ACCEPT_IDENTCODE_1",
					"CURRENCY_TRANS_CODE_1","SECURITY_RELATED_CONTROL_1","RESERVED_PRIVATE1_1",
					"RESERVED_PRIVATE2_1","RESERVED_PRIVATE3_1","MESSAGE_AUTHENT_CODE_1", 
					"SERVICE_CONDITION_MODE_1","MSG_ID_1","CARD_NO_1","PROCESS_CODE_1","TRANS_AMOUNT_1",
					"SYS_TRACE_AUDIT_NUM_1","DATE_EXPIRED_1","COUPONS_ADVERT_ID","COUPONS_ADVERT_ID_1",
					"SWAP_CODE_1","USER_ID","APP_DISCOUNT","COUPONS_IDS","COUPONS_TYPES"}, // ��ˮ��
			{ "_ID", "PROCESS_CODE","TRACK2","TRACK2_1","ORGAN_ID","ORGAN_ID_1",
					"TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM", "DATE_EXPIRED","SWAP_CODE",
					"SERVICE_ENTRY_MODE", "SERVICE_CONDITION_MODE","ADDIT_RESP_DATA",
					"SERVICE_PIN_CAPTURE_CODE", "RET_REFER_NUM", "CARD_ACCEPT_TERM_IDENT", 
					"CARD_ACCEPT_IDENTCODE", "AUTHOR_IDENT_RESP", "CURRENCY_TRANS_CODE", 
					"PIN_DATA", "SECURITY_RELATED_CONTROL", "RESERVED_PRIVATE1","RESERVED_PRIVATE2",
					"MESSAGE_AUTHENT_CODE", "DISCOUNT_AMOUNT","DISCOUNT_AMOUNT_1",
					"VIP_AMOUNT", "LOCAL_TRANS_TIME_1",
					"LOCAL_TRANS_DATE_1", "SETTLE_DATE_1", "ACQ_INST_IDENT_CODE_1",
					"RESP_CODE", "ADDIT_RESP_DATA_1", "RESERVED_PRIVATE3",
					"RESERVED_PRIVATE4", "ORIGIN_AMOUNT", "MSG_ID","ORIGINAL_MESSAGE",
					"RET_REFER_NUM_1","RESP_CODE_1","CARD_ACCEPT_TERM_IDENT_1","CARD_ACCEPT_IDENTCODE_1",
					"CURRENCY_TRANS_CODE_1","SECURITY_RELATED_CONTROL_1","RESERVED_PRIVATE1_1",
					"RESERVED_PRIVATE2_1","RESERVED_PRIVATE3_1","MESSAGE_AUTHENT_CODE_1",
					"SERVICE_CONDITION_MODE_1","MSG_ID_1","CARD_NO_1","PROCESS_CODE_1",
					"TRANS_AMOUNT_1","SYS_TRACE_AUDIT_NUM_1","DATE_EXPIRED_1","COUPONS_ADVERT_ID","COUPONS_ADVERT_ID_1", "SWAP_CODE_1","USER_ID"
					,"AUTHOR_IDENT_RESP_1"}, // ��ʱ��ˮ��
			{ "_ID", "PROCESS_CODE", "TRACK2","TRACK2_1","ORGAN_ID","ORGAN_ID_1",
					"TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM", "DATE_EXPIRED","DISCOUNT_AMOUNT","DISCOUNT_AMOUNT_1",
					"SERVICE_ENTRY_MODE", "SERVICE_CONDITION_MODE","SWAP_CODE",
					"AUTHOR_IDENT_RESP", "RESP_CODE", "CURRENCY_TRANS_CODE","ADDIT_RESP_DATA",
					"RESERVED_PRIVATE1","RESERVED_PRIVATE2" ,"MESSAGE_AUTHENT_CODE",
					"SECURITY_RELATED_CONTROL", "ORIGIN_AMOUNT", "MSG_ID", "CARD_ACCEPT_TERM_IDENT",
					"CARD_ACCEPT_IDENTCODE", "RESERVED_PRIVATE3","ORIGINAL_MESSAGE",
					"RESP_CODE_1","CARD_ACCEPT_TERM_IDENT_1","CARD_ACCEPT_IDENTCODE_1", 
					"CURRENCY_TRANS_CODE_1","SECURITY_RELATED_CONTROL_1","RESERVED_PRIVATE1_1",
					"RESERVED_PRIVATE2_1","RESERVED_PRIVATE3_1","MESSAGE_AUTHENT_CODE_1", 
					"SERVICE_CONDITION_MODE_1","MSG_ID_1","SWAP_CODE_1","CARD_NO_1","PROCESS_CODE_1",
					"TRANS_AMOUNT_1","SYS_TRACE_AUDIT_NUM_1","DATE_EXPIRED_1","COUPONS_ADVERT_ID","COUPONS_ADVERT_ID_1",
					"FLUSH_OCUNT","FLUSH_LASTTIME", "FLUSH_RESULT", "AUTHOR_IDENT_RESP_1"}, // ������
			{ "_ID", "TYPE", "PARA_NAME", "PARA_VALUE", "PARA_EXPLAIN"  },//������
			{ "_ID", "TYPE", "PARA_NAME", "PARA_VALUE", "PARA_EXPLAIN"  },//��ʱ������
			{ "_ID", "USER_ID", "USER_NAME", "PASSWORD", "LIMITS"},//��Ա�����
			{ "_ID", "ID", "TYPE", "ASYNMODE", "MODE", "HOST", "PORT", "ENCODING",
				    "POLICY", "READTIMEOUT", "SERVERSIDE", "COOLPOOLSIZE", 
				    "MAXINUMPOOLSIZE"}, //Э�����ñ�
			{"_ID" , "SYS_TRACE_AUDIT_NUM" , "LOCAL_TRANS_TIME" , "LOCAL_TRANS_DATE" ,
				    "ACQ_INST_IDENT_CODE" , "RET_REFER_NUM" , "RESP_CODE" , "CARD_ACCEPT_TERM_IDENT",
				    "CARD_ACCEPT_IDENTCODE" , "CARD_ACCEPT_LOCAL" , "RESERVED_PRIVATE2"} //����ǩ����

	};// �ֶ���

	public static String FieldTypes[][] = {
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text" }, // ��Ӧ����ձ�
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text"},    // ��ˮ��
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text"},  // ��ʱ��ˮ��
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text DEFAULT '0'","text", "text DEFAULT '-1'" ,"text"},        // ������
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text","text", "text"},//������
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text","text", "text"},//��ʱ������
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text","text"},//��Ա�����
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text","text", "text","text", "text",
		  "text","text", "text","text","text"}, //Э�����ñ�
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text",
			  "text", "text", "text", "text", "text", "text", "text",
			  "text"}   //����ǩ����
	};// �ֶ�����

	@Override
	public String[] getTableNames() {
		return TableNames;
	}

	@Override
	public String[][] getFieldNames() {
		return FieldNames;
	}

	@Override
	public String[][] getFieldTypes() {
		return FieldTypes;
	}

}
