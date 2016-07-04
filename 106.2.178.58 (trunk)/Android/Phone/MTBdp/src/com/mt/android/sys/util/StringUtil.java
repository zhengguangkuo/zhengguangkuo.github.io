package com.mt.android.sys.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	/**
	 * 将字符串转换为List
	 * 
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param clazz
	 *            list里对象的类型
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static List string2List(String str, Class clazz)
			throws InstantiationException, IllegalAccessException {
		List list = new ArrayList();
		String[] arr = null;
		if(str != null){
			arr = str.split(";");
		}
		if(arr != null){
			for (int i = 0; i < arr.length; i++) {
				String[] objArr = arr[i].split(",");
				Object obj = clazz.newInstance();
				for (int j = 0; j < objArr.length; j++) {

					Method[] methods = obj.getClass().getMethods();
					for (Method method : methods) {

						String methodName = getMethodName("set",
								objArr[j].split(":")[0]);
						if (method.getName().equals(methodName)) {
							Class[] parameterC = method.getParameterTypes();
							try {
								if(objArr[j].split(":").length==1){
									objArr[j]=objArr[j]+" ";
								}
								if (parameterC[0] == int.class) {
									method.invoke(obj, Integer.parseInt(objArr[j]
											.split(":")[1]));
									break;
								} else if (parameterC[0] == float.class) {
									method.invoke(obj, Float.parseFloat(objArr[j]
											.split(":")[1]));
									break;
								} else if (parameterC[0] == double.class) {
									method.invoke(obj, Double.parseDouble(objArr[j]
											.split(":")[1]));
									break;
								} else if (parameterC[0] == byte.class) {
									method.invoke(obj,
											Byte.parseByte(objArr[j].split(":")[1]));
									break;
								} else if (parameterC[0] == char.class) {
									method.invoke(obj,
											objArr[j].split(":")[1].toCharArray());
									break;
								} else if (parameterC[0] == boolean.class) {
									method.invoke(obj, Boolean
											.parseBoolean(objArr[j].split(":")[1]));
									break;
								} else {
									method.invoke(obj, StringFormat.isNull(objArr[j].split(":")[1]));
									break;
								}
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} catch (SecurityException e) {
								e.printStackTrace();
							}
						}
					}

				}
				list.add(obj);
			}
		}
		
		return list;
	}

	/**
	 * 将传入的list转换为String字符串 转换后的格式为:
	 * 
	 * 每个list元素间以 ; 分隔 每个属性以 ， 分隔 每个属性名和属性值以： 分隔
	 * 
	 * 例如： 属性1:属性值1,属性2:属性值2;属性1:属性值1,属性2:属性值2;
	 * 
	 * @param list
	 * @return
	 */
	public static String list2String(List list) {
		StringBuffer sb = new StringBuffer();
		if(list != null){
			for (int j = 0; j < list.size(); j++) {
				Class cla = list.get(j).getClass();
				Method[] ma = cla.getDeclaredMethods();// 获取所有声明的方法数组
				Field[] fd = cla.getDeclaredFields();
				for (int i = 0; i < fd.length; i++) {

					if (i != 0) {
						sb.append(",");
					}
					sb.append(fd[i].getName());
					sb.append(":");
					try {
						sb.append(cla.getMethod(
								getMethodName("get", fd[i].getName())).invoke(
								list.get(j), null));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				}
				sb.append(";");
			}
		}
		return sb.toString();
	}

	/**
	 * 组装get,set方法名
	 * 
	 * @param type
	 *            要组装set或get方法
	 * @param name
	 *            属性名
	 * @return
	 */
	public static String getMethodName(String type, String name) {
		String methodName = "";
		if (name != null && name.length() > 1) {
			if (type != null && type.equals("get")) {
				methodName = "get" + name.substring(0, 1).toUpperCase()
						+ name.substring(1, name.length());
			}
			if (type != null && type.equals("set")) {
				methodName = "set" + name.substring(0, 1).toUpperCase()
						+ name.substring(1, name.length());
			}
		}
		return methodName;
	}
	
	public static int byteToInt( byte[] tmpLen) {
	  	  int sH = 0, sL = 0;
	  	  byte[] b = new byte[4];
	  	  b[0] = 0x00;
	  	  b[1] = tmpLen[0];
	  	  b[2] = 0x00;
	  	  b[3] = 0x00;
	  	  int s0 = b[0] & 0xff;// 最低位
	  	  int s1 = b[1] & 0xff;
	  	  int s2 = b[2] & 0xff;
	  	  int s3 = b[3] & 0xff;
	  	  s3 <<= 24;
	  	  s2 <<= 16;
	  	  s1 <<= 8;
	  	  sH = s0 | s1 | s2 | s3;
	  	  b[0] = tmpLen[1];
	  	  b[1] = 0x00;
	  	  b[2] = 0x00;
	  	  b[3] = 0x00;
	  	  s0 = b[0] & 0xff;// 最低位
	  	  s1 = b[1] & 0xff;
	  	  s2 = b[2] & 0xff;
	  	  s3 = b[3] & 0xff;
	  	  s3 <<= 24;
	  	  s2 <<= 16;
	  	  s1 <<= 8;
	  	  sL = s0 | s1 | s2 | s3;
	  	  return sH+sL;     
	    } 
}
