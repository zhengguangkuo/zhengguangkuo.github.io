package com.mt.app.payment.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.util.Log;

public class DownLoadImage {
	public static void getImageMap(String imagePath, Map<String, byte[]> map) {
		try{
			if(true){
				return ;
			}
			Log.i("imagePath=",imagePath);
			byte[] images = getImage(imagePath);
			
			map.put(imagePath, images);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static byte[] getImage(String htmlpath) throws Exception {
		byte[] imagearray = null;
		URL url = new URL(htmlpath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		// conn.setRequestMethod("GET");
		conn.connect();
		if (conn.getResponseCode() == 200) {
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			int len = 0;
			InputStream inputStream = conn.getInputStream();
			while ((len = inputStream.read(buffer)) != -1) {
				arrayOutputStream.write(buffer, 0, buffer.length);
			}
			imagearray = arrayOutputStream.toByteArray();
		}
		return imagearray;

	}

}
