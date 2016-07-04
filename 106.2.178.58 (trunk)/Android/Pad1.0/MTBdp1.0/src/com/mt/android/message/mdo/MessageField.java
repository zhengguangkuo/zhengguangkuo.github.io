package com.mt.android.message.mdo;

import java.util.HashMap;
import java.util.Map;

public class MessageField {
	private static Map<String, String> fieldmap = new HashMap<String, String>();

	static{
		defineFiled("msgId", 1); //MessageId消息类型
		defineFiled("cardNo", 2);//卡号
		defineFiled("processCode", 3);//交易处理码
		defineFiled("transAmount", 4);//交易金额
		defineFiled("discountAmount", 5);//抵扣金额
		defineFiled("sysTraceAuditNum", 11);//受卡方系统跟踪号
		defineFiled("localTransTime", 12);//受卡方所在地时间
		defineFiled("localTransDate", 13);//受卡发所在地日期
		defineFiled("dateExpired", 14);//卡有效期
		defineFiled("settleDate", 15);//清算日期		
		defineFiled("serviceEntryMode", 22);//服务点输入方式码		
		defineFiled("cardSequenceNum", 23);//卡序列号		
		defineFiled("serviceConditionMode", 25); //服务点条件码
        defineFiled("servicePINCaptureCode", 26);//服务点PIN获取码
        defineFiled("acqInstIdentCode", 32);//受理方标识码
        defineFiled("track2", 35);//磁道2数据
        defineFiled("track3", 36);//磁道3数据
        defineFiled("retReferNum", 37);//检索参考号
        defineFiled("authorIdentResp", 38);//授权标识应答码
        defineFiled("respCode", 39);//应答码
        defineFiled("cardAcceptTermIdent", 41);//受卡机终端标识码
        defineFiled("cardAcceptIdentcode", 42);//受卡方标识码
        defineFiled("cardAcceptLocal", 43);//受卡方名称地址
        defineFiled("additRespData", 44);//附加响应数据/发卷机构标识
        defineFiled("additDataPrivate", 48);//附加数据 - 私有
        defineFiled("currencyTransCode", 49);//交易货币代码 
        defineFiled("pinData", 52);//个人标识码数据
        defineFiled("securityRelatedControl", 53);//安全控制信息
        defineFiled("balanceAmount", 54);//附加金额
        defineFiled("organId", 56);//发券机构标识
        defineFiled("swapCode", 57);//兑换码
        defineFiled("couponsAdvertId", 58);//发卷对象标识
        defineFiled("reservedPrivate1", 59);//自定义域1
        defineFiled("reservedPrivate2", 60);//自定义域2
        defineFiled("originalMessage", 61);//原始信息域
        defineFiled("reservedPrivate3", 62);//自定义域3
        defineFiled("reservedPrivate4", 63);//自定义域4
        defineFiled("messageAuthentCode", 64);//mac
	}
    //根据域名称获取域编号
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
