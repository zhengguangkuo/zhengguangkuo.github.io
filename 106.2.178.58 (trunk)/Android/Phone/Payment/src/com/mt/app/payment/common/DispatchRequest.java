package com.mt.app.payment.common;

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
	 *            业务类型: 注册、登录、套餐查询、套餐绑定、基卡绑定、基卡解绑、基卡应用关系绑定 UrlConstant
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
		 	//使用挡板返回假报文数据
			
			jsonResult = HTTPtools.invoke(bStruct.getDoUrl(), namePairList);
			//jsonResult = SystemInit.mockMap.get(bStruct.getDoUrl()).toString();
			// 没数据
			if (jsonResult == null || jsonResult.trim().equalsIgnoreCase("") || jsonResult.length() > 10000) {

			} else {
				log.debug(" Http  rec eived response");
				mBeanList = JsonHandler.UnPack(jsonResult, respBeanClass,
						bStruct.getJosonResultType());

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.debug("doHttpRequest end");
		return mBeanList;
	}
	
	/**
	 * 获取雅酷卡数据
	 * @param iBusiType
	 * @param requestBean
	 * @param rspBeanClass
	 * @return
	 */
	public static List<ResponseBean> doHttpRequestForYaCol(Request requestBean, Class rspBeanClass) {
		List<ResponseBean> mBeanList = new ArrayList<ResponseBean>();
		reqbean = requestBean;
		respBeanClass = rspBeanClass;
		
		List<NameValuePair> namePairList = new ArrayList<NameValuePair>();
		String jsonResult = "";
		try {
			namePairList = getObjectToString(reqbean.getData());
			//使用挡板返回假报文数据
			jsonResult = HTTPtools.invokeForYaol(namePairList) ;
			// 没数据
			if (jsonResult == null || jsonResult.trim().equalsIgnoreCase("")) {
				
			} else {
				mBeanList = JsonHandler.UnPack(jsonResult, respBeanClass, bStruct.getJosonResultType());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		Method[] ma = cla.getDeclaredMethods();// 获取所有声明的方法数组
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
