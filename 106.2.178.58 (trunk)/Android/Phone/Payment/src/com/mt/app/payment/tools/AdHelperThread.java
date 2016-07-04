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
 * @Description:���ͼƬ����
 * 
 * @author:dw
 * 
 * @time:2013-10-28 ����3:44:42
 */
public class AdHelperThread extends Thread {
	private static final String TAG_LOG = AdHelperThread.class.getSimpleName() ;
	private ArrayList<Bitmap> bitmapList;
	private Handler handler;
	private AdResult adResult;
	private boolean isStop = false; // �Ƿ����ִ���߳�
	private boolean loadFail = false;// �Ƿ���ͼƬ����ʧ�� false:û��ͼƬ����ʧ��
	private int runNum = 3;// �߳����ִ�д���

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
					int order = 0;// ��ʾ˳�� Ŀǰ֧�� ��ʾ����ͼƬ 1 �� 5
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
						hasNotify = true;// ��ȡ������ʧ�ܾͲ�֪ͨˢ��
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
