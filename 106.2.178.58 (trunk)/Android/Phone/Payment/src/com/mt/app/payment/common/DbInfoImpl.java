package com.mt.app.payment.common;

import com.mt.android.db.IDbInfo;

public class DbInfoImpl implements IDbInfo {

	public static String TableNames[] = { 
		"AREA_CODE",// 地区表
		"MERCHANT_BUSSINESS_TYPE",//类型表
		"PARA_TABLE"//参数表
	};// 表名

	/**
	 * 数据库中要用到的字段名：
	 *    _ID                              id
	 *    AREA_CODE                        地区编号
	 *    AREA_LEVEL                       地区级别
	 *    PHONE_AREA_CODE                    区号
	 *    AREA_NAME                        地区名称
	 *    SUPER_AREA_CODE                 上级地区编号
	 *    
	 *    
	 *    
	 */
	/**
	 * 数据库中要用到的字段名：
	 *    _ID                              id
	 *    TYPE_CODE                         业务类型编码
	 *    TYPE_NAME                         业务类型名称
	 *    TYPE_LEVEL                        业务类型级别
	 *    SUPER_TYPE                        上级业务类型编码
	 *    
	 *    
	 *    
	 */
	public static String FieldNames[][] = {
			{ "_ID", "AREA_CODE", "AREA_LEVEL", "PHONE_AREA_CODE", "AREA_NAME", "SUPER_AREA_CODE"}, // 响应码对照表
			{ "_ID", "TYPE_CODE", "TYPE_NAME", "TYPE_LEVEL", "SUPER_TYPE"},//类型表
			{"_ID" , "NAME", "VALUE"}//参数表
	};// 字段名

	public static String FieldTypes[][] = {
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text", "text", "text"}, //地区表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text", "text"}, //类型表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text"} //参数表
	};// 字段类型

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
