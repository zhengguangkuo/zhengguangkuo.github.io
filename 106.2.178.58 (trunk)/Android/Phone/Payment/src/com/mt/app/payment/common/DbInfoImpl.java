package com.mt.app.payment.common;

import com.mt.android.db.IDbInfo;

public class DbInfoImpl implements IDbInfo {

	public static String TableNames[] = { 
		"AREA_CODE",// ������
		"MERCHANT_BUSSINESS_TYPE",//���ͱ�
		"PARA_TABLE"//������
	};// ����

	/**
	 * ���ݿ���Ҫ�õ����ֶ�����
	 *    _ID                              id
	 *    AREA_CODE                        �������
	 *    AREA_LEVEL                       ��������
	 *    PHONE_AREA_CODE                    ����
	 *    AREA_NAME                        ��������
	 *    SUPER_AREA_CODE                 �ϼ��������
	 *    
	 *    
	 *    
	 */
	/**
	 * ���ݿ���Ҫ�õ����ֶ�����
	 *    _ID                              id
	 *    TYPE_CODE                         ҵ�����ͱ���
	 *    TYPE_NAME                         ҵ����������
	 *    TYPE_LEVEL                        ҵ�����ͼ���
	 *    SUPER_TYPE                        �ϼ�ҵ�����ͱ���
	 *    
	 *    
	 *    
	 */
	public static String FieldNames[][] = {
			{ "_ID", "AREA_CODE", "AREA_LEVEL", "PHONE_AREA_CODE", "AREA_NAME", "SUPER_AREA_CODE"}, // ��Ӧ����ձ�
			{ "_ID", "TYPE_CODE", "TYPE_NAME", "TYPE_LEVEL", "SUPER_TYPE"},//���ͱ�
			{"_ID" , "NAME", "VALUE"}//������
	};// �ֶ���

	public static String FieldTypes[][] = {
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text", "text", "text"}, //������
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text", "text"}, //���ͱ�
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text"} //������
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
