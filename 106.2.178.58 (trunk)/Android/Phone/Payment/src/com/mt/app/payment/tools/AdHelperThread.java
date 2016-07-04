package com.mt.app.payment.tools;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.miteno.coupon.entity.TopContent;
import com.mt.android.protocol.http.client.ImageHttpClient;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.responsebean.AdResult;

/**
 * 
 * 
 * @Description:广告图片下载
 * 
 * @author:dw
 * 
 * @time:2013-10-28 下午3:44:42
 */
public class AdHelperThread extends Thread {
	private static final String TAG_LOG = AdHelperThread.class.getSimpleName() ;
	private ArrayList<Bitmap> bitmapList;
	private Handler handler;
	private AdResult adResult;
	private boolean isStop = false; // 是否继续执行线程
	private boolean loadFail = false;// 是否有图片下载失败 false:没有图片下载失败
	private int runNum = 3;// 线程最大执行次数

	public AdHelperThread(AdResult adResult, ArrayList<Bitmap> bitmapList,
			Handler handler) {
		this.bitmapList = bitmapList;
		this.adResult = adResult;
		this.handler = handler;
		Log.i(TAG_LOG, "AdHelperThread AdHelperThread()....") ;
	}

	public void run() {
		Log.i(TAG_LOG, "AdHelperThread....run() ") ;
		boolean hasNotify = false;
		try {
			do {
				loadFail = false;
				runNum--;
				for (int i = 0; i < 5; i++) {
					TopContent topContent = adResult.getRows().get(i);
					int order = 0;// 显示顺序 目前支持 显示五张图片 1 到 5
					try {
						order = Integer.parseInt(topContent.getDisplay_order());
					} catch (Exception e) {
						continue;
					}
					if (order > 5 || order < 1) {
						continue;
					}

					Log.i(TAG_LOG, "Image url = " + topContent.getImage_path()) ;
					
					Bitmap bitmap = ImageHttpClient.getBitmap(topContent
							.getImage_path());
					
					Log.i(TAG_LOG, (bitmap == null) + "") ;

					if (bitmap != null) {
						bitmapList.set(order - 1, bitmap);
						hasNotify = true;// 读取或下载失败就不通知刷新
						Controller.session.put("adMap", bitmapList);
					} else {
						loadFail = true;
					}
					try {
						sleep(50);
					} catch (InterruptedException e) {
						isStop = true;
					}
				}
			} while (!isStop && runNum >= 0 && loadFail); 
			
			if (hasNotify) {
				Message ms = Message.obtain();
				ms.what = 1;
				handler.sendMessage(ms);
			}
			
		} catch (Exception ex) {
			
			if (hasNotify) {
				Message ms = Message.obtain();
				ms.what = 1;
				handler.sendMessage(ms);
			}
			ex.printStackTrace();
		}

	}
}
