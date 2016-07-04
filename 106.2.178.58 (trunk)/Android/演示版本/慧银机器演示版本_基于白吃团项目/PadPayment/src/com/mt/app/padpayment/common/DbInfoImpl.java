package com.mt.app.padpayment.common;

import com.mt.android.db.IDbInfo;

public class DbInfoImpl implements IDbInfo {

	public static String TableNames[] = { 
		"TBL_RESPONSE_CODE",// 响应码对照表
		"TBL_FlOW",// 流水表
		"TBL_TMPFlOW",// 临时流水表
		"TBL_REVERSAL",// 冲正表
		"TBL_PARAMETER", //参数表
		"TBL_TMPPARAMETER",//临时参数表
		"TBL_ADMIN", //人员管理表
		"TBL_PROTOCL", //协议配置表
		"TBL_MANAGE"   //管理签到表
	};// 表名

	/**
	 * 数据库中要用到的字段名：
	 *    _ID                          id
	 *    MSG_ID                       交易类型
	 *    RESP_CODE                    应答码
	 *    MESSAGE                      应答信息
	 *    PROCESS_CODE                 交易处理码
	 *    CARD_NO                      支付卡号
	 *    TRACK2					       基卡卡号
	 *    ORIGIN_AMOUNT                原交易金额（交易总额）
	 *    TRANS_AMOUNT                 交易金额（实收金额）
	 *    SYS_TRACE_AUDIT_NUM            受卡方系统跟踪号（交易流水号）
	 *    DATE_EXPIRED                 卡有效期
	 *    SERVICE_ENTRY_MODE           服务点输入方式码
	 *    SERVICE_CONDITION_MODE       服务点条件码
	 *    SERVICE_PIN_CAPTURE_CODE       服务点PIN获取码
	 *    RET_REFER_NUM                  检索参考号（交易参考号）
	 *    CARD_ACCEPT_TERM_IDENT         受卡机终端标识码
	 *    CARD_ACCEPT_IDENTCODE        受卡方标识码
	 *    AUTHOR_IDENT_RESP              授权码
	 *    CURRENCY_TRANS_CODE          交易货币代码
	 *    PIN_DATA                     个人标识码数据
	 *    SECURITY_RELATED_CONTROL     安全控制信息
	 *    RESERVED_PRIVATE1                                应用标识
	 *    RESERVED_PRIVATE2                                批次号 
	 *    ORIGINAL_MESSAGE             原始信息域
	 *    MESSAGE_AUTHENT_CODE         MAC
	 *    DISCOUNT_AMOUNT              优惠券抵扣金额    （折扣金额）
	 *    VIP_AMOUNT                   会员卡折扣金额
	 *    LOCAL_TRANS_TIME             受卡方所在地时间
	 *    LOCAL_TRANS_DATE             受卡方所在地日期（交易日期）
	 *    SETTLE_DATE                  清算日期
	 *    ACQ_INST_IDENT_CODE            受理方标识码
	 *    ADDITRESPDATA                发券机构标识
	 *    SWAPCODE_1                                                 兑换码
	 *    ADDIT_RESP_DATA              附加响应数据
	 *    RESERVED_PRIVATE3                               卡名称
	 *    RESERVED_PRIVATE4                                应答信息
	 *    FLUSH_OCUNT                  冲正次数
	 *    FLUSH_LASTTIME               冲正时间
	 *    FLUSH_RESULT                 冲正结果  
	 *    CARD_ACCEPT_LOCAL            受卡方名称地址
	 *    
	 *    APP_DISCOUNT                 支付卡折扣率
	 *    COUPONS_IDS                  优惠券编号
	 *    COUPONS_TYPES                优惠券类型
	 *    
	 *    ---------TBL_PARAMETER   参数表-------
	 *    TYPE                         参数类型    1.签到类参数    2.冲正类参数  3.日志类参数   4.版本号
	 *    PARA_NAME                    参数名
	 *    PARA_VALUE                   参数值
	 *    PARA_EXPLAIN                 参数说明
	 *    
	 *    
	 *    
	 *    
	 *    
	 *    ---------TBL_TMPPARAMETER  临时参数表-------
	 *    TYPE                         参数类型     1.签到类参数    2.冲正类参数  
	 *    PARA_NAME                    参数名
	 *    PARA_VALUE                   参数值
	 *    PARA_EXPLAIN                 参数说明
	 *    
	 *    
	 *    
	 *    
	 *    ---------TBL_ADMIN   人员管理表-------
	 *    USER_ID                       用户编号
	 *    USER_NAME                     用户名
	 *    PASSWORD                      密码
	 *    LIMITS                        权限             1 柜员    2主管
	 *    
	 *    ---------TBL_PROTOCL 协议配置表 ----------
	 *   ID      协议名称 
	 *   TYPE    协议的类型 
	 *   ASYNMODE
	 *   MODE    长连接还是短连接  0短连接 1长连接
	 *   HOST    协议的IP
	 *   PORT      协议的端口
	 *   ENCODING  通讯所使用的编码 
	 *   POLICY   读写策略 
	 *   READTIMEOUT  读超时时间 
	 *   SERVERSIDE   是否是server端 
	 *   COOLPOOLSIZE  空闲线程数
	 *   MAXINUMPOOLSIZE  最大线程数量 
	 *    
	 *    
	 *    
	 */
	public static String FieldNames[][] = {
			{ "_ID", "RESP_CODE", "MESSAGE" }, // 响应码对照表
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
					"SWAP_CODE_1","USER_ID","APP_DISCOUNT","COUPONS_IDS","COUPONS_TYPES"}, // 流水表
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
					,"AUTHOR_IDENT_RESP_1"}, // 临时流水表
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
					"FLUSH_OCUNT","FLUSH_LASTTIME", "FLUSH_RESULT", "AUTHOR_IDENT_RESP_1"}, // 冲正表
			{ "_ID", "TYPE", "PARA_NAME", "PARA_VALUE", "PARA_EXPLAIN"  },//参数表
			{ "_ID", "TYPE", "PARA_NAME", "PARA_VALUE", "PARA_EXPLAIN"  },//临时参数表
			{ "_ID", "USER_ID", "USER_NAME", "PASSWORD", "LIMITS"},//人员管理表
			{ "_ID", "ID", "TYPE", "ASYNMODE", "MODE", "HOST", "PORT", "ENCODING",
				    "POLICY", "READTIMEOUT", "SERVERSIDE", "COOLPOOLSIZE", 
				    "MAXINUMPOOLSIZE"}, //协议配置表
			{"_ID" , "SYS_TRACE_AUDIT_NUM" , "LOCAL_TRANS_TIME" , "LOCAL_TRANS_DATE" ,
				    "ACQ_INST_IDENT_CODE" , "RET_REFER_NUM" , "RESP_CODE" , "CARD_ACCEPT_TERM_IDENT",
				    "CARD_ACCEPT_IDENTCODE" , "CARD_ACCEPT_LOCAL" , "RESERVED_PRIVATE2"} //管理签到表

	};// 字段名

	public static String FieldTypes[][] = {
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text" }, // 响应码对照表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text"},    // 流水表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text"},  // 临时流水表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text", 
		  "text", "text", "text", "text", "text", "text", "text",
		  "text", "text", "text DEFAULT '0'","text", "text DEFAULT '-1'" ,"text"},        // 冲正表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text","text", "text"},//参数表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text","text", "text"},//临时参数表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text","text"},//人员管理表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text", "text","text", "text","text", "text",
		  "text","text", "text","text","text"}, //协议配置表
		{ "INTEGER PRIMARY KEY AUTOINCREMENT", "text", "text",
			  "text", "text", "text", "text", "text", "text", "text",
			  "text"}   //管理签到表
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
