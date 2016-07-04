package com.mt.app.payment.tools;

import java.util.ArrayList;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.mt.android.protocol.http.client.ImageHttpClient;

public class Old_ImageThread extends Thread {
	private ArrayList<String> appphoto;
	private Map<String, Bitmap> caches;
	private Handler handler;
	
	public Old_ImageThread(ArrayList<String> appphoto, Map<String, Bitmap> caches, Handler handler){
		this.appphoto = appphoto;
		this.caches = caches;
		this.handler = handler;
	}
	

	public void run() {

		boolean hasNotify = false;
		
		for (int i = 0; i < appphoto.size(); i++) {
			try{
				hasNotify = true;
				if(!caches.containsKey(appphoto.get(i))){
					Bitmap bitmap = ImageHttpClient.getBitmap(appphoto.get(i));
					
					if(bitmap != null){
						caches.put(appphoto.get(i), bitmap);
						
					}
					if (hasNotify) {
						Message ms = Message.obtain();
						ms.what = 1;
						handler.sendMessage(ms);
						
					}
				}

				Thread.sleep(50);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
	}
}

