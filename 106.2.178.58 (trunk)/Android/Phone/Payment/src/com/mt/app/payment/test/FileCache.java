package com.mt.app.payment.test;
import java.io.File;  
import android.content.Context;  
import android.os.Environment;
  
public class FileCache {  
  
    private File cacheDir;  
	private final static String ALBUM_PATH = "/download_image/";
	private final static String COUPONS_PATH = ALBUM_PATH + "coupons/";
  
    public FileCache(Context context) {  
        // ÕÒµ½±£´æ»º´æµÄÍ¼Æ¬Ä¿Â¼  
        if (android.os.Environment.getExternalStorageState().equals(  
                android.os.Environment.MEDIA_MOUNTED))  
            cacheDir = new File(  
                    android.os.Environment.getExternalStorageDirectory(),  
                    COUPONS_PATH);  
        else  
            cacheDir = context.getCacheDir();  
        if (!cacheDir.exists())  
            cacheDir.mkdirs();  
    }  
  
    public File getFile(String url) {  
        String filename = String.valueOf(url.hashCode());  
        File f = new File(cacheDir, filename);  
        return f;  
  
    }  
  
    public void clear() {  
        File[] files = cacheDir.listFiles();  
        for (File f : files)  
            f.delete();  
    }  
  
}  