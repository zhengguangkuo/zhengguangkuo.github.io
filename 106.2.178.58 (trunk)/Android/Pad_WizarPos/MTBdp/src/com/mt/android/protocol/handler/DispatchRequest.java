package com.mt.android.protocol.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import android.os.Handler;

import com.mt.android.protocol.http.client.HTTPtools;
import com.mt.android.sys.bean.base.RequestBean;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.util.StringFormat;

public class DispatchRequest {
	private static Logger log = Logger.getLogger(DispatchRequest.class);

	private static List<NameValuePair> getObjectToString(Object obj)
			throws Exception {
		log.debug("getObjectToString start");
		List<NameValuePair> mList = new ArrayList<NameValuePair>();

		if (obj == null) {
			return null;
		}

		Class cla = obj.getClass();
		Method[] ma = cla.getDeclaredMethods();// ��ȡ���������ķ�������
		Field[] fd = cla.getDeclaredFields();
		Object returnValue = null;

		for (int i = 0; i < fd.length; i++) {
			returnValue = cla.getMethod(
					"get" + fd[i].getName().substring(0, 1).toUpperCase()
							+ fd[i].getName().substring(1)).invoke(obj, null);
			log.debug("name=" + fd[i].getName() + "; value=" + returnValue);
			BasicNameValuePair bNameVal = new BasicNameValuePair(
					fd[i].getName(), StringFormat.isNull(returnValue));
			mList.add(bNameVal);
		}

		log.debug("getObjectToString end");
		return mList;
	}
	/**
	 * 
	 * @param url �����url
	 * @param requestBean ��������ݶ���
	 * @return ������Ӧ���Ĵ�
	 */
	public static String doHttpRequest(String url,RequestBean requestBean) {
		log.debug("doHttpRequest start");

		List<NameValuePair> namePairList = new ArrayList<NameValuePair>();
		String result = "";
		try {
			namePairList = getObjectToString(requestBean);
			result = HTTPtools.invoke(url, namePairList);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
		log.debug("doHttpRequest end");
		return result;
	}
}
