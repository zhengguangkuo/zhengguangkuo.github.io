package com.mt.android.sys.util;

public class StringFormat {
    
	public static String isNull(Object obj){
		return (obj == null ? "": obj.toString());
	}
}
