package com.mt.android.protocol.http.client;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.mt.android.db.DbHandle;
import com.mt.android.db.DbHelper;
import com.mt.android.sys.mvc.controller.Controller;

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
public class ImageHttpClient {
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
		// bm.compress(null, 0 , bos);
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

			/*
			 * BitmapFactory.Options opts = new BitmapFactory.Options();
			 * 
			 * opts.inJustDecodeBounds = true;
			 * BitmapFactory.decodeFile(COUPONS_PATH + BKDRHash(path), opts);
			 * opts.inSampleSize = computeSampleSize(opts, -1, 1280 * 1280);
			 * opts.inJustDecodeBounds = false;
			 */

			try {

				/*
				 * Bitmap bitmap1 = BitmapFactory.decodeFile(COUPONS_PATH +
				 * BKDRHash(path), opts); Bitmap bitmap = new
				 * SoftReference<Bitmap>(bitmap1).get();
				 */
				Bitmap bitmap = BitmapFactory.decodeFile(COUPONS_PATH
						+ BKDRHash(path));
				/*
				 * Bitmap bitmap1 = BitmapFactory.decodeFile(COUPONS_PATH +
				 * pathArr[pathArr.length - 1],opts); Bitmap bitmap = new
				 * SoftReference<Bitmap>(bitmap1).get();
				 */

				if (bitmap == null) { // 本地不存在，在远程下载，并保存
					try {
						byte[] data = getImage(path);
						bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);

						saveFile(bitmap, COUPONS_PATH,
								String.valueOf(BKDRHash(path)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return bitmap;
			} catch (OutOfMemoryError err) {
				err.printStackTrace();
				return null;
			}

		} else {
			return null;
		}
	}

	/**
	 * 获得广告图片
	 * 
	 * @param path
	 * @param num
	 * @return
	 */
	public static Bitmap getAdBitmap(String path, String num) {
		if ((path ==null||path.equals(""))&&num!=null){
			
			try {
				Bitmap bitmap = BitmapFactory.decodeFile(ALBUM_PATH
						+ "ad/" + num);
				return bitmap;
			} catch (Exception ee) {
				ee.printStackTrace();
				return null;
			}
			
		}
		if (num != null) {
			try {
				Map<String, String> map = new DbHandle().selectOneRecord(
						"PARA_TABLE", new String[] { "VALUE" }, "NAME = ?",
						new String[] { num }, null, null, null);
				Bitmap bitmap;
				if (map != null && map.get("VALUE") != null
						&& map.get("VALUE").equals(path)) {// 已经下载过
					bitmap = BitmapFactory.decodeFile(ALBUM_PATH + "ad/" + num);
					if (bitmap == null) { // 本地不存在，在远程下载，并保存
						try {
							byte[] data = getImage(path);
							bitmap = BitmapFactory.decodeByteArray(data, 0,
									data.length);
							saveFile(bitmap,  ALBUM_PATH + "ad/", String.valueOf(num));
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
					return bitmap;
				} else {// 未下载过
					try {
						byte[] data = getImage(path);
						bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);
						saveFile(bitmap, ALBUM_PATH + "ad/", String.valueOf(num));
						new DbHandle().insert("PARA_TABLE", new String[] {
								"NAME", "VALUE" }, new String[] { num, path });
					} catch (Exception e) {
						try {
							bitmap = BitmapFactory.decodeFile(ALBUM_PATH
									+ "ad/" + num);
							return bitmap;
						} catch (Exception ee) {
							ee.printStackTrace();
							return null;
						}
					}
					if (bitmap == null) {
						try {
							bitmap = BitmapFactory.decodeFile(ALBUM_PATH
									+ "ad/" + num);
						} catch (Exception ee) {
							return null;
						}
					}
					return bitmap;
				}
			} catch (Exception err) {
				err.printStackTrace();
				return null;
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
			Bitmap bitmap = BitmapFactory.decodeFile(COUPONS_PATH
					+ BKDRHash(path));
			if (bitmap == null) {
				return false;
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

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * BKDR算法
	 * 
	 * @param str
	 * @return
	 */
	public static int BKDRHash(String str) {
		int seed = 31; // 31 131 1313 13131 131313 etc..
		int hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash * seed) + str.charAt(i);
		}

		return (hash & 0x7FFFFFFF);
	}
}