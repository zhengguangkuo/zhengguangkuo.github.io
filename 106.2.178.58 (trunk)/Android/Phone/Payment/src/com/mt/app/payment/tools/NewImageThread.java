package com.mt.app.payment.tools;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Map;

import com.mt.android.protocol.http.client.ImageHttpClient;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class NewImageThread extends Thread {
	private Map<String, SoftReference<Bitmap>> caches;
	private Handler handler;
	private String picName;

	public NewImageThread(String picName, Map<String, SoftReference<Bitmap>> caches, Handler handler) {
		this.caches = caches;
		this.handler = handler;
		this.picName = picName;
	}

	public void run() {

		boolean hasNotify = false;
		try {
			hasNotify = true;
			if (!caches.containsKey(picName)) {
				Bitmap bitmap = ImageHttpClient.getBitmap(picName);

				if (bitmap != null) {
					caches.put(picName, new SoftReference<Bitmap>(
							bitmap));
					hasNotify = true;//读取或下载失败就不通知刷新

				}else{
					hasNotify = false;
				}

				if (hasNotify) {
					Message ms = Message.obtain();
					ms.what = 1;
					handler.sendMessage(ms);
				}
			}

			Thread.sleep(50);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
