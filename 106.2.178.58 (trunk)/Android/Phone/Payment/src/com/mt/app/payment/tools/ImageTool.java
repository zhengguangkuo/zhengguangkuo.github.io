package com.mt.app.payment.tools;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mt.android.R;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class ImageTool {
	private static final String TAG_LOG = ImageTool.class.getSimpleName() ;
	private int defalutImage;

	public void setImagePath(ImageView image, String imagePath, Map<String, SoftReference<Bitmap>> caches, Handler handler, int defalutImage) {
		this.defalutImage = defalutImage;
		setImagePath(image, imagePath, caches, handler);
	}

	public void setImagePath(ImageView image, String imagePath, Map<String, SoftReference<Bitmap>> caches, Handler handler) {
		Log.i(TAG_LOG, "imagePath-------->" + imagePath) ;
		if (caches.containsKey(imagePath)) {
			Log.i(TAG_LOG, "imagePath---IF----->" + imagePath) ;
			SoftReference<Bitmap> sf = caches.get(imagePath);
			Bitmap bitmap = sf.get();

			if (bitmap != null) {

				image.setImageBitmap(bitmap);
			} else {
				image.setImageResource(defalutImage);
				Log.i("notfound", "图片被清除了,重新下载!!!");
				caches.remove(imagePath);// 如果图片被清除了,则整个删除sf
				new NewImageThread(imagePath, caches, handler).start();
			}
		} else {// 异步下载
			image.setImageResource(defalutImage);
			new NewImageThread(imagePath, caches, handler).start();

		}
	}

	public Bitmap getImage(String imagePath, Map<String, SoftReference<Bitmap>> caches, Handler handler) {

		Bitmap bitmap = null;
		if (caches.containsKey(imagePath)) {
			SoftReference<Bitmap> sf = caches.get(imagePath);
			bitmap = sf.get();

			if (bitmap != null) {

			} else {
				Log.i("notfound", "图片被清除了,重新下载");
				caches.remove(imagePath);// 如果图片被清除了,则整个删除sf
				new NewImageThread(imagePath, caches, handler).start();
			}
		} else {// 异步下载
			new NewImageThread(imagePath, caches, handler).start();
		}

		return bitmap;
	}

	public void imageFree(Map<String, SoftReference<Bitmap>> caches) {

		Set set = caches.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			SoftReference<Bitmap> sf = (SoftReference<Bitmap>) entry.getValue();
			Bitmap bitmap = sf.get();
			if (bitmap != null) {
				bitmap.recycle();
				bitmap = null;

			}
		}
	}

}
