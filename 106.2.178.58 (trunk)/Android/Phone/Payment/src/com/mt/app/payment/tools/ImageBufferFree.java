package com.mt.app.payment.tools;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.graphics.Bitmap;
public class ImageBufferFree {
	 public static void  imageFree(Map<String, Bitmap> caches){
		 Set set =caches.entrySet();
		    Iterator it=set.iterator();
		    while(it.hasNext()){
		           Map.Entry  entry=(Map.Entry) it.next();
		           Bitmap bitmap = (Bitmap)entry.getValue();
		           if(bitmap != null){
		        	   bitmap.recycle();
		        	   bitmap = null;
		       
		           }
		    }
	 }
}
