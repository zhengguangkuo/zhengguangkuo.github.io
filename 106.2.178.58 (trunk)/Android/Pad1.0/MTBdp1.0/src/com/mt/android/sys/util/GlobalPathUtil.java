package com.mt.android.sys.util;

import java.io.File;
import android.content.Context;
import android.os.Environment;

public class GlobalPathUtil {

	public static File getExternalStorageDirectory() {
		if(ExistSDCard()) {
			return Environment.getExternalStorageDirectory() ;
		} else {
			return new File("data/data/com.mt.android") ;
		}
	}

	public static boolean ExistSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true ;
		} else {
			return false ;			
		}
	}

}
