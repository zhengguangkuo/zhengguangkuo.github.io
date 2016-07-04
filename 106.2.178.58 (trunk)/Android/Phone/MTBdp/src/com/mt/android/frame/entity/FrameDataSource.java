package com.mt.android.frame.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;

public class FrameDataSource {
	// tab数据源定义	
	public static Map<String, Class> tabDataClass = new HashMap<String , Class>();
	// SlideMenu数据源定义
	public static Map<String, List<SlideMenuDataEntity>> slideMenuDataSource = new HashMap<String, List<SlideMenuDataEntity>>();
	// ViewPagerSlideMenu数据源定义
	public static Map<String, List<ViewPagerSlideMenuDataEntity>> viewPagerSlideMenuDataSource = new HashMap<String, List<ViewPagerSlideMenuDataEntity>>();

	public static void init() {
		// tabDataSource.put(key, value);
		// slideMenuDataSource.put(key, value);
		// viewPagerSlideMenuDataSource.put(key, value);
	}
}
