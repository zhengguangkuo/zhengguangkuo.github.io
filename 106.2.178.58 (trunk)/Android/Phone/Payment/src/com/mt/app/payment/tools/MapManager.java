package com.mt.app.payment.tools;

import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

public class MapManager extends BMapManager {
	//public static final String strKey = "8a7e2a67b4ddcb263ab48324d00543e2 ";
	public static final String strKey = "518cfa9ed354f16cedf082eed9a80cf8";
	//public static final String strKey = "xDBWQEGt7q2WUU4xaHGGdrms";
	private Context context;
	public boolean m_bKeyRight = true;

	public MapManager(Context context) {
		super(context);
		this.context = context;
	}

	public void initMapManager(MapManager manager) {
		if(!manager.init(strKey, new MyGeneralListener())){
			Toast.makeText(context, "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
		}
	}

	class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(context, "您的网络出错啦！", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(context, "输入正确的检索条件！", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(context, "请在 DemoApplication.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
				m_bKeyRight = false;
			}
		}
	}
}
