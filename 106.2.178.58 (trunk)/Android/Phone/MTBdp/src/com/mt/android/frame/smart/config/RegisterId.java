package com.mt.android.frame.smart.config;

import java.util.HashMap;
import java.util.Map;

public class RegisterId {
	public static int baseNumber=3000;
	public static Map<String,Integer> map=new HashMap<String,Integer>();
	public static void registerId(String idName){
		map.put(idName, baseNumber++);
	}
}
