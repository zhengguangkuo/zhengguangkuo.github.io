package com.mt.android.frame.entity;

import android.view.View;
import com.mt.android.sys.mvc.common.Response;

public interface IPagerSlide {
	//指定当前的page所使用的layout
	public int getViewPagerLayOut();
	//指定当前page页的名称
	public String getPageName();
	//自定义初始化
	public void initialize();
	//初始化所使用layout的一些监听事件
	public void layOutListenerInit(View layout);
	public void onSuccess(Response response);
	public void onError(Response response);
}
