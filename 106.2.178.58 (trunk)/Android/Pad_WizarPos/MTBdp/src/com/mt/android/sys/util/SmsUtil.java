package com.mt.android.sys.util;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
/**
 * 发送短信的工具类
 * @author dw
 *
 */
public class SmsUtil {
	/**
	 * 发送短信
	 * @param activity 发送后的响应activity
	 * @param number   发送的手机号
	 * @param msg      发送的内容
	 */
	public Object sendMessage(Context context, String number, String msg) {
		SmsManager sManager = SmsManager.getDefault();
		// 创建一个PendingIntent对象
		PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(),0);
		// 发送短信
		sManager.sendTextMessage(number, null, msg, pi, null);
		return pi;
	}
}
