package com.mt.app.payment.tools;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
/**
 * ���Ͷ��ŵĹ�����
 * @author dw
 *
 */
public class SmsUtil {
	/**
	 * ���Ͷ���
	 * @param activity ���ͺ����Ӧactivity
	 * @param number   ���͵��ֻ���
	 * @param msg      ���͵�����
	 */
	public void sendMessage(Context context, String number, String msg) {
		SmsManager sManager = SmsManager.getDefault();
		// ����һ��PendingIntent����
		PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(),0);
		// ���Ͷ���
		sManager.sendTextMessage(number, null, msg, pi, null);
	}
}
