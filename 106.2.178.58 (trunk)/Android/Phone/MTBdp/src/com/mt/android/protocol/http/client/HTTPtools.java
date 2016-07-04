package com.mt.android.protocol.http.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

import android.util.Log;

public class HTTPtools {
	private static Logger log = Logger.getLogger(HTTPtools.class);
	//private final static String SERVER_URL = "http://192.168.16.34:8080/HttpSer/servlet/";// 访问地址
	//private final static String SERVER_URL = "http://192.168.16.40:8089/miteno-mobile/pad/";//pad应用 张昊
	//private final static String SERVER_URL = "http://192.168.16.35:8080/miteno-mobile/pad/";//pad应用 周
	//private final static String SERVER_URL = "http://192.168.16.115:8080/miteno-mobile/pad/";//pad应用 115
//	private final static String SERVER_URL = "http://106.2.168.138:8090/miteno-mobile/pad/";//外网应用 115
	
//	private final static String SERVER_URL = "http://192.168.16.40:8089/miteno-mobile/";//手机应用 张昊
//	private final static String SERVER_URL = "http://192.168.16.97:8888/miteno-mobile/";//手机应用 刘正
//	private final static String SERVER_URL = "http://192.168.16.65:8080/miteno-mobile/";//手机应用 阮文洲
	
	//	private final static String SERVER_URL ="http://192.168.16.115:8090/miteno-mobile/";//手机应用 115
//	private final static String SERVER_URL = "http://106.2.168.138:8090/miteno-mobile/";//外网手机应用 115

//	private final static String SERVER_URL = "http://192.168.16.27:8092/miteno-mobile/";//开发测试服务器
//	private final static String SERVER_URL = "http://192.168.16.27:8091/miteno-mobile/";//开发测试服务器(新地址)
//	private final static String SERVER_URL = "http://192.168.16.35:8083/miteno-mobile/";// 周培卿
	
	private final static String SERVER_URL = "http://app.modepay.cn/miteno-mobile/";//外网手机应用 

	//private final static String SERVER_URL = "http://192.168.16.113:8090/miteno-mobile/pad/";//pad应用 开发113
	//private final static String SERVER_URL = "http://192.168.16.35:8080/m-pay/mpayFront/";
	//private final static String SERVER_URL = "http://192.168.16.34:8080/HttpSer/servlet/";  //pad挡板
	private static CookieStore cookieStore;// 定义一个Cookie来保存session
	
	private static String YACOL_DETAIL_URL = "http://app.yacol.com/yacolApp/mobile/store.do?" ;
	private static final int CONNECTION_TIMEOUT = 30000;
	public static final int DO_GET = 1;
	public static final int DO_POST = 0;
	public static final String GBK = "GBK";
	public static String PARSER_UNICODE = "GBK";
	private static final int SO_TIMEOUT = 30000;
	public static final String UTF_8 = "utf-8";

	/**
	 * 向服务器发送URL请求 获得返回数据
	 * 
	 * @param doUrl
	 *            stauts的类名
	 * @param actionName
	 *            方法名
	 * @param params
	 *            传递的参数
	 * @return 获得返回的JSON结果
	 */
	public static String invoke(String doUrl, List<NameValuePair> params) {
		log.debug("start connect --------------");
		String result = null;

		try {
			// String url = SERVER_URL + doUrl+".do?action="+actionName;
			String url = SERVER_URL + doUrl;

			
			BasicHttpParams httpParams = new BasicHttpParams();  
			
			//设置请求超时30秒钟 
		    HttpConnectionParams.setConnectionTimeout(httpParams, 30*1000);  
		    
		    //设置等待数据超时时间30秒钟 
		    HttpConnectionParams.setSoTimeout(httpParams, 30*1000);  
			
		    HttpPost httpPost = new HttpPost(url);
		    
			if (params != null && params.size() > 0) {

				HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

				httpPost.setEntity(entity);
			}
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=\"UTF-8\"");
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
			// 添加Cookie
			if (cookieStore != null) {
				httpClient.setCookieStore(cookieStore);
			}
			Log.i("====send url=====",url);
			if (params != null) {
				for (NameValuePair value: params) {
					Log.i("====send data=====",value.getName() +":" + value.getValue());
				}
			} else {
				Log.i("====send data=====","null");
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity()
								.getContent(), "UTF-8"));
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					builder.append(s);
				}
				result = builder.toString();

				// value=GSONTOOLS.getGson().fromJson(result,
				// ActionValue.class);

				// MUser user = GSONTOOLS.getGson().fromJson(value.getVALUE(),
				// MUser.class);
				if (result == null) {
					log.info("result is null");
				} else {					
					log.info("result is " + result );
				}
				// 保存Cookie
				cookieStore = ((AbstractHttpClient) httpClient)
						.getCookieStore();
			}
		} catch (Exception e) {
			log.error(e.toString());
		}

		log.debug("over");
		if (result == null) {
			Log.i("====recive=====","null");
		} else {
			Log.i("====recive=====",result);
		}
		return result;
	}

	/**
	 * 清除cookie
	 */
	public static void clear() {
		HTTPtools.cookieStore = null;
	}
	
	
	/**
	 * 获取雅酷详情数据
	 * @param params
	 * @return
	 */
	public static String invokeForYaol(List<NameValuePair> params) {
		HttpResponse httpResponse ;
		Object object = null ;
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			httpResponse = doPost(YACOL_DETAIL_URL, params) ;
			object = httpResponse.getEntity().getContent();
			object = new GZIPInputStream((InputStream) object);
			String str = PARSER_UNICODE;
			InputStreamReader isr = new InputStreamReader((InputStream) object, str);
			br = new BufferedReader(isr, 8);
			sb = new StringBuilder();
			
			while (true) {
				String str2 = br.readLine();
				if (str2 == null) {
					((InputStream) object).close();
					return sb.toString();
				}
				String str3 = String.valueOf(str2);
				String str4 = str3 + "\n";
				sb.append(str4);
			}			
		} catch (Exception e) {
			log.error(e.toString()) ;
			return "" ;
		}	
	}
	
	
	public static HttpResponse doPost(String paramString, List<NameValuePair> paramList) {
		HttpResponse httpResponse = null ;
		try {
			BasicHttpParams localBasicHttpParams = new BasicHttpParams();
			setHttpParams(localBasicHttpParams);
			DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localBasicHttpParams);
			HttpPost localHttpPost = new HttpPost(paramString);
			String str = PARSER_UNICODE;
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, str);
			localHttpPost.setEntity(entity);
			httpResponse = localDefaultHttpClient.execute(localHttpPost);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new Exception() ;			  
			}			
		} catch (Exception e) {
			log.error(e.toString()) ;
		}

		return httpResponse;
	}
	
	private static void setHttpParams(HttpParams paramHttpParams) {
		HttpConnectionParams.setConnectionTimeout(paramHttpParams, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(paramHttpParams, SO_TIMEOUT);
	}	  
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
