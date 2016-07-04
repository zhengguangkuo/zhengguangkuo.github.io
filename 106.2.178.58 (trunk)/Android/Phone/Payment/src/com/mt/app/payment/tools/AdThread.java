package com.mt.app.payment.tools;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.common.Request;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
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
public class AdThread extends Thread {
	private static final String TAG_LOG = AdThread.class.getSimpleName() ;
	private AdHelperThread helpThread;
	private ArrayList<Bitmap> bitmapList; // ���ͼƬ��list
	private Handler handler;
	// private AdResult adResult;
	private boolean isStop = false; // �Ƿ����ִ���߳�
	private int runNum = 3;// �߳����ִ�д���

	public AdThread(ArrayList<Bitmap> bitmapList, Handler handler) {
		this.bitmapList = bitmapList;
		this.handler = handler;
		Log.i(TAG_LOG, "AdThread AdThread()....") ;
	}

	public void run() {
		Log.i(TAG_LOG, "AdThread....run() ") ;
		try {
			while (!isStop && runNum > 0) {
				runNum--;

				List<ResponseBean> list = DispatchRequest.doHttpRequest(Constants.USR_AD, new Request(), AdResult.class);
				if (list != null && list.size() > 0) {
					AdResult adResult = (AdResult) list.get(0);
					if (adResult != null) {
						helpThread = new AdHelperThread(adResult, bitmapList, handler);
						helpThread.start();
						isStop = true; // ֹͣ�߳�
					}
				}
				try {
					sleep(30000);
					
				} catch (InterruptedException e) {
					isStop = true;
					try{			
						if (helpThread != null) {
							Log.i("threadStop","adadThread");
							helpThread.interrupt();
						}
					}catch(Exception ee){
						ee.printStackTrace();
					}
				}
			}

		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		try{			
			if (helpThread != null) {
				Log.i("threadStop","adadThread");
				helpThread.interrupt();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		super.interrupt();
	}
	
}
