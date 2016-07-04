
package com.mt.app.payment.common;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;


public class MapApplication extends Activity {
	
    private static MapApplication mInstance = null;
    public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null; 

    //public static final String strKey = "B5a6ab55ace35e6d88620466d39005a2";
    public static final String strKey = "518cfa9ed354f16cedf082eed9a80cf8";
    //public static final String strKey = "xDBWQEGt7q2WUU4xaHGGdrms";
    /*
    	注意：为了给用户提供更安全的服务，Android SDK自v2.1.3版本开始采用了全新的Key验证体系。
    	因此，当您选择使用v2.1.3及之后版本的SDK时，需要到新的Key申请页面进行全新Key的申请，
    	申请及配置流程请参考开发指南的对应章节
    */
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		mInstance = this;
		initEngineManager(this);
	}
	
	public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(MapApplication.getInstance().getApplicationContext(), 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
	
	public static MapApplication getInstance() {
		return mInstance;
	}
	
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(MapApplication.getInstance().getApplicationContext(), "您的网络出错啦！",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(MapApplication.getInstance().getApplicationContext(), "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onGetPermissionState(int iError) {
        	
        	try{
        		 if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                     //授权Key错误：
                     Toast.makeText(MapApplication.getInstance().getApplicationContext(), 
                             "请在 MapApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
                     MapApplication.getInstance().m_bKeyRight = false;
                 }
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
           
        }
    }
}