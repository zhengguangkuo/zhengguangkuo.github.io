package com.mt.android.message.iso.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.mt.android.message.annotation.MsgColumn;

public class LoadMsgField {
	public static void Convert(Object src, Object des) {
		Field[] desfields = null;
		Method[] srcmethods = null;
		if(des != null){
			desfields = des.getClass().getFields();
		}
		if(src != null){
			srcmethods = src.getClass().getMethods();
		}
		if(desfields != null){
			for (Field m : desfields) {
				if (m.isAnnotationPresent(MsgColumn.class)) {
					MsgColumn column = m.getAnnotation(MsgColumn.class);
					String srcfiled = column.field();
					
					if (srcfiled != null && !srcfiled.trim().equalsIgnoreCase("")){
						String srcVal = getFiledValue(src, srcfiled);
						setFiledValue(des, m.getName(), srcVal);
					}
				}
			}
		}
	}
	
	//根据属性名字获取属性对应的值
	private static String getFiledValue(Object obj, String filedName){
		 String ret = "";
		 Method[] methods = null;
		 if(obj != null){
			 methods = obj.getClass().getMethods();
		 }
		 if(methods != null){
			 for (Method m : methods){
				 
				 String methodName = m.getName().substring(3).toUpperCase();
				 
				 if (m.getName().startsWith("get") && methodName.equalsIgnoreCase(filedName.toUpperCase())){
					 try{
						 ret = m.invoke(obj, null).toString();
						 return ret;
					 }catch(Exception ex){
						 ex.printStackTrace();
						 return "";
					 }
				 }
			 }
		 }
		 return ret;
	}
	
	//根据属性名字设置属性对应的值
		private static void setFiledValue(Object obj, String filedName, String value){
			 String ret = "";
			 Method[] methods = null;
			 if(obj != null){
				 methods = obj.getClass().getMethods();
			 }
			 if(methods != null){
				 for (Method m : methods){
					 String methodName = m.getName().substring(3).toUpperCase();
					 if (m.getName().startsWith("set") && methodName.equalsIgnoreCase(filedName.toUpperCase())){
						 try{
							  m.invoke(obj, value);
						 }catch(Exception ex){
							 ex.printStackTrace();
						 }
					 }
				 }
			 }
		}
}
