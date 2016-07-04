package com.mt.android.protocol.http.client;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * 
 * 
 * @Description:图片下载时的工具类
 * 
 * @author:dw
 * 
 * @time:2013-8-12 下午2:18:01
 */
public class ImageHttpPadClient {
	private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/download_image/";
	private final static String COUPONS_PATH = ALBUM_PATH + "coupons/";
	private final static String APP_PATH = ALBUM_PATH + "apps/";

	/**
	 * 获取指定路径，的数据。
	 * 
	 * **/
	private static byte[] getImage(String urlpath) throws Exception {
		URL url = new URL(urlpath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(6 * 1000);
		// 别超过10秒。
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			return readStream(inputStream);
		}
		return null;
	}

	/**
	 * 读取数据 输入流
	 * 
	 * */
	private static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		inStream.close();

		return outstream.toByteArray();
	}

	/**
	 * 保存文件
	 * 
	 * @param bm
	 * @param path
	 * @param fileName
	 * @throws IOException
	 */
	private static void saveFile(Bitmap bm, String path, String fileName)
			throws IOException {
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			boolean flg = dirFile.mkdirs();
		}
		File myCaptureFile = new File(path + fileName);

		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		//bm.compress(null, 0 , bos);
		
		bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
		bos.flush();
		bos.close();

	}

	/**
	 * 根据url得到图片，如果图片存在，直接从本地文件中取出，如果图片在本地不存在，则根据url从服务器下载，并保存在本地
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmap(String path) {
		if (path != null) {
			if (path.contains("/")) {
				String[] pathArr = path.split("/");
				Bitmap bitmap = BitmapFactory.decodeFile(COUPONS_PATH
						+ pathArr[pathArr.length - 1]);
				if (bitmap == null) { // 本地不存在，在远程下载，并保存
					try {
						byte[] data = getImage(path);
						bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);
						saveFile(bitmap, COUPONS_PATH,
								pathArr[pathArr.length - 1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return bitmap;
			} else {
				Bitmap bitmap = BitmapFactory.decodeFile(APP_PATH + path);
				return bitmap;
			}
		} else {
			return null;
		}
	}
	/**
	 * 根据url得到图片
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapPad(String path) {
		if (path != null) {
			if (path.contains("/")) {
				String[] pathArr = path.split("/");
				Bitmap bitmap = BitmapFactory.decodeFile(COUPONS_PATH
						+ pathArr[pathArr.length - 1]);
				return bitmap;
			} else {
				Bitmap bitmap = BitmapFactory.decodeFile(APP_PATH + path);
				return bitmap;
			}
		} else {
			return null;
		}
	}
	/**
	 * 根据url判断本地是否存在图片，只有路径正确，不存在时返回false
	 * 
	 * @param path
	 * @return
	 */
	public static boolean hasImage(String path) {
		if (path != null) {
			if (path.contains("/")) {
				String[] pathArr = path.split("/");
				Bitmap bitmap = BitmapFactory.decodeFile(COUPONS_PATH
						+ pathArr[pathArr.length - 1]);
				if (bitmap == null) {
					return false;
				} else {
					bitmap.recycle();
					return true;
				}
				
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * 根据url下载图片保存在本地，并以传入的名字命名
	 * 
	 * @param path
	 * @param name
	 *            图片名
	 * @return
	 */
	public static boolean saveImage(String path, String name) {
		boolean isSave = true;
		try {
			byte[] data = getImage(path);
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			saveFile(bitmap, APP_PATH, name);
		} catch (Exception e) {
			e.printStackTrace();
			isSave = false;
		}
		return isSave;
	}
}