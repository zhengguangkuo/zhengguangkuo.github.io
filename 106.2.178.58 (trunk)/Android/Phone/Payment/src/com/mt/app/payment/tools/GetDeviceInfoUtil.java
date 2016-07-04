package com.mt.app.payment.tools;

import android.content.Context;
import android.telephony.TelephonyManager;

public class GetDeviceInfoUtil {
	private static String imei;
	public static String getImei(Context ctx) {
		if (imei == null)
			imei = ((TelephonyManager)ctx.getSystemService("phone")).getDeviceId();
		return imei;
	}
}
