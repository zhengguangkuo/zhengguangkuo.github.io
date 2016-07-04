package com.mt.app.payment.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import com.mt.android.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

/**
 * �첽����ͼƬ��
 */
public class ImageLoader {
	// �ֻ��еĻ���
	private MemoryCache memoryCache = new MemoryCache();
	// sd������
	private FileCache fileCache;
	private PicturesLoader pictureLoaderThread = new PicturesLoader();
	private PicturesQueue picturesQueue = new PicturesQueue();
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

	public ImageLoader(Context context) {
		// �����̵߳����ȼ�
		pictureLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
		fileCache = new FileCache(context);
	}

	// ���Ҳ���ͼƬʱ��Ĭ�ϵ�ͼƬ
	final int stub_id = R.drawable.adload;

	public void DisplayImage(String url, Activity activity, ImageView imageView) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null)
			imageView.setBackgroundDrawable(new BitmapDrawable(bitmap)); // imageView.setImageBitmap(bitmap);
		else {// ����ֻ��ڴ滺����û��ͼƬ�������������У���������Ĭ��ͼƬ
			queuePhoto(url, activity, imageView);
			imageView.setBackgroundResource(stub_id); // imageView.setImageResource(stub_id);
		}
	}

	private void queuePhoto(String url, Activity activity, ImageView imageView) {
		// ��ImageView����֮ǰ����������ͼ�����Կ��ܻ���һЩ�ɵ�������С�������Ҫ��������ǡ�
		picturesQueue.Clean(imageView);
		PictureToLoad p = new PictureToLoad(url, imageView);
		synchronized (picturesQueue.picturesToLoad) {
			picturesQueue.picturesToLoad.push(p);
			picturesQueue.picturesToLoad.notifyAll();
		}

		// �������̻߳�û���������������߳�
		if (pictureLoaderThread.getState() == Thread.State.NEW)
			pictureLoaderThread.start();
	}

	/**
	 * ����url��ȡ��Ӧ��ͼƬ��Bitmap
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// ��SD�������л�ȡ
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// ����������л�ȡ
		try {
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
//			Log.i("---->>>>", df.format(new Date())) ;
			long startTime = System.currentTimeMillis() ;
			Log.i("---->>>>", startTime+",,,"+url) ;
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			// ��ͼƬд��sd��Ŀ¼��ȥ
			ImageUtil.CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			Log.i("---->>>>", "��ʱ��" + (System.currentTimeMillis()-startTime)/1000+",,,"+url) ;
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// ����ͼ��������Լ����ڴ������
	private Bitmap decodeFile(File f) {
		/*try {
			// ����ͼ��ߴ�
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// �ҵ���ȷ������ֵ����Ӧ����2���ݡ�
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// ����ǡ����inSampleSize����ʹBitmapFactory������ٵĿռ�
			// ����ȷǡ����inSampleSize����decode
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;*/
		return BitmapFactory.decodeFile(f.getAbsolutePath()) ;
	}

	/**
	 * PictureToLoad��(����ͼƬ�ĵ�ַ��ImageView����)
	 * 
	 * @author loonggg
	 * 
	 */
	private class PictureToLoad {
		public String url;
		public ImageView imageView;

		public PictureToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	public void stopThread() {
		pictureLoaderThread.interrupt();
	}

	// �洢���ص���Ƭ�б�
	class PicturesQueue {
		private Stack<PictureToLoad> picturesToLoad = new Stack<PictureToLoad>();

		// ɾ�����ImageView������ʵ��
		public void Clean(ImageView image) {
			for (int j = 0; j < picturesToLoad.size();) {
				if (picturesToLoad.get(j).imageView == image)
					picturesToLoad.remove(j);
				else
					++j;
			}
		}
	}

	// ͼƬ�����߳�
	class PicturesLoader extends Thread {
		public void run() {
			try {
				while (true) {
					// �̵߳ȴ�ֱ����ͼƬ�����ڶ�����
					if (picturesQueue.picturesToLoad.size() == 0)
						synchronized (picturesQueue.picturesToLoad) {
							picturesQueue.picturesToLoad.wait();
						}
					if (picturesQueue.picturesToLoad.size() != 0) {
						PictureToLoad photoToLoad;
						synchronized (picturesQueue.picturesToLoad) {
							photoToLoad = picturesQueue.picturesToLoad.pop();
						}
						Bitmap bmp = getBitmap(photoToLoad.url);
						// д���ֻ��ڴ���
						memoryCache.put(photoToLoad.url, bmp);
						String tag = imageViews.get(photoToLoad.imageView);
						if (tag != null && tag.equals(photoToLoad.url)) {
							BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad.imageView);
							Activity activity = (Activity) photoToLoad.imageView.getContext();
							activity.runOnUiThread(bd);
						}
					}
					if (Thread.interrupted())
						break;
				}
			} catch (InterruptedException e) {
				// �����������߳��˳�
			}
		}
	}

	// ��UI�߳�����ʾBitmapͼ��
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		ImageView imageView;

		public BitmapDisplayer(Bitmap bitmap, ImageView imageView) {
			this.bitmap = bitmap;
			this.imageView = imageView;
		}

		public void run() {
			if (bitmap != null)
				imageView.setBackgroundDrawable(new BitmapDrawable(bitmap)); // imageView.setImageBitmap(bitmap);
			else
				imageView.setBackgroundResource(stub_id); // imageView.setImageResource(stub_id);
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

}