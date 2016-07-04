package com.mt.app.padpayment.tools;

import java.io.BufferedInputStream;
import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.util.Enumeration;
import java.util.Map;

import java.util.Properties;

import com.mt.android.sys.util.GlobalPathUtil;

import android.content.Context;
import android.os.Environment;

public class SysConfig {
    public static String filepath = GlobalPathUtil.getExternalStorageDirectory().getPath() + File.separator + "configs" + File.separator + "SysConfig.properties";
    
	public static String readValue(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filepath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 读取properties的全部信息
	public static void readProperties(Map desmap) {
		Properties props = new Properties();
		
		try{
			InputStream in = new BufferedInputStream(new FileInputStream(
					filepath));
			props.load(in);
			Enumeration en = props.propertyNames();
			
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				desmap.put(key, Property);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void writeProperties(String parameterName,
			String parameterValue) {
		Properties prop = new Properties();

		try {
			InputStream fis = new FileInputStream(filepath);

			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filepath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
