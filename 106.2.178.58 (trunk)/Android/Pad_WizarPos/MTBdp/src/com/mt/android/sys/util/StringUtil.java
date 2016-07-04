package com.mt.android.sys.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	/**
	 * ���ַ���ת��ΪList
	 * 
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * @param clazz
	 *            list����������
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
	 * �������listת��ΪString�ַ��� ת����ĸ�ʽΪ:
	 * 
	 * ÿ��listԪ�ؼ��� ; �ָ� ÿ�������� �� �ָ� ÿ��������������ֵ�ԣ� �ָ�
	 * 
	 * ���磺 ����1:����ֵ1,����2:����ֵ2;����1:����ֵ1,����2:����ֵ2;
	 * 
	 * @param list
	 * @return
	 */
	public static String list2String(List list) {
		StringBuffer sb = new StringBuffer();
		if(list != null){
			for (int j = 0; j < list.size(); j++) {
				Class cla = list.get(j).getClass();
				Method[] ma = cla.getDeclaredMethods();// ��ȡ���������ķ�������
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
	 * ��װget,set������
	 * 
	 * @param type
	 *            Ҫ��װset��get����
	 * @param name
	 *            ������
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
	  	  int s0 = b[0] & 0xff;// ���λ
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
	  	  s0 = b[0] & 0xff;// ���λ
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
