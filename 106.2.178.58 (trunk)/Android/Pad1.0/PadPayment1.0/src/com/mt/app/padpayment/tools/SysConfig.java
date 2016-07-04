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
	
	// ��ȡproperties��ȫ����Ϣ
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

			// ���������ж�ȡ�����б�����Ԫ�ضԣ�
			prop.load(fis);
			// ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
			// ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
			OutputStream fos = new FileOutputStream(filepath);
			prop.setProperty(parameterName, parameterValue);
			// ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
			// ���� Properties ���е������б�����Ԫ�ضԣ�д�������
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
