package com.mt.app.padpayment.common;
/**
 * 识别交易的帮助类
 */
public class TypeUtil {

	
	public static String getType(String type, String number) {

		if (type != null && number != null && !type.equals("") && !number.equals("")) {
			
		
		if (type.equals("0200")) { // 金融类请求消息

			if (number.equals("310000")) { // 余额查询
				return "余额查询";
			} else if (number.equals("000000")) { // 消费
				return "消        费";
			} else if (number.equals("200000")) { // 消费撤销
				return "消费撤销";
			} else if (number.equals("110000")) { // 积分查询
				return "积分查询";
			} else if (number.equals("100000")) { // 积分消费
				return "积分消费";
			} else if (number.equals("300000")) { // 积分撤销
				return "积分撤销";
			} else if (number.equals("020000")) { // 优惠卷兑换
				return "兑        换";
			} else if (number.equals("021000")) { // 优惠券兑换撤销
				return "兑换撤销";
			}

		} else if (type.equals("0220")) { // 金融通知类消息

			if (number.equals("200000")) { // 退货
				return "退        货";
			} else if (number.equals("010000")) { // 改密
				return "改        密";
			} else if (number.equals("022000")) { // 优惠卷领用
				return "领        用";
			} else if (number.equals("021000")) { // 优惠卷退领
				return "退        领";
			}

		} 
		}
		return "";
	}

}
