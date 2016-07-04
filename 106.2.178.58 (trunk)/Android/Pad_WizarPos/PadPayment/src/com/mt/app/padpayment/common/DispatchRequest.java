package com.mt.app.padpayment.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.mt.android.message.packager.JsonHandler;
import com.mt.android.protocol.http.client.HTTPtools;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.util.StringFormat;

public class DispatchRequest {
	private static Logger log = Logger.getLogger(DispatchRequest.class);
	private static Request reqbean = null;
	private static Class respBeanClass = null;
	private static BusiTypeStruct bStruct = new BusiTypeStruct();

	/**
	 * 
	 * @param ibusiType
	 *            ҵ������: ע�ᡢ��¼���ײͲ�ѯ���ײͰ󶨡������󶨡�������󡢻���Ӧ�ù�ϵ�� UrlConstant
	 * @param requestBean
	 * @param handler
	 * @param rspBeanClass
	 */
	public static List<ResponseBean> doHttpRequest(int iBusiType,
			Request requestBean, Class rspBeanClass) {
		log.debug("doHttpRequest start");
		log.debug("iBusiType = " + iBusiType);
		List<ResponseBean> mBeanList = new ArrayList<ResponseBean>();
		reqbean = requestBean;
		respBeanClass = rspBeanClass;
		bStruct = BusiTypeCreator.getBusiType(iBusiType);

		List<NameValuePair> namePairList = new ArrayList<NameValuePair>();
		String jsonResult = "";
		try {
			namePairList = getObjectToString(reqbean.getData());
			jsonResult = HTTPtools.invoke(bStruct.getDoUrl(), namePairList);

			// û����
			if (jsonResult == null || jsonResult.trim().equalsIgnoreCase("")) {
				mBeanList = null;
			} else {
				log.debug(" Http  received response");
				mBeanList = JsonHandler.UnPack(jsonResult, respBeanClass,
						bStruct.getJosonResultType());

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mBeanList = null;
		}
		log.debug("doHttpRequest end");
		return mBeanList;
	}

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

	public static void doClear() {
		HTTPtools.clear();
	}
}
