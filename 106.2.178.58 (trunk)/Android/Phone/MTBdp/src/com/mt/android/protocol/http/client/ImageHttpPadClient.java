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
 * @Description:ͼƬ����ʱ�Ĺ�����
 * 
 * @author:dw
 * 
 * @time:2013-8-12 ����2:18:01
 */
public class ImageHttpPadClient {
	private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/download_image/";
	private final static String COUPONS_PATH = ALBUM_PATH + "coupons/";
	private final static String APP_PATH = ALBUM_PATH + "apps/";

	/**
	 * ��ȡָ��·���������ݡ�
	 * 
	 * **/
	private static byte[] getImage(String urlpath) throws Exception {
		URL url = new URL(urlpath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(6 * 1000);
		// �𳬹�10�롣
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			return readStream(inputStream);
		}
		return null;
	}

	/**
	 * ��ȡ���� ������
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
	 * �����ļ�
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
	 * ����url�õ�ͼƬ�����ͼƬ���ڣ�ֱ�Ӵӱ����ļ���ȡ�������ͼƬ�ڱ��ز����ڣ������url�ӷ��������أ��������ڱ���
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
				if (bitmap == null) { // ���ز����ڣ���Զ�����أ�������
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
	 * ����url�õ�ͼƬ
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
	 * ����url�жϱ����Ƿ����ͼƬ��ֻ��·����ȷ��������ʱ����false
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
	 * ����url����ͼƬ�����ڱ��أ����Դ������������
	 * 
	 * @param path
	 * @param name
	 *            ͼƬ��
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