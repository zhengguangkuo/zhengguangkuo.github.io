package com.mt.android.view.common;

import java.util.HashMap;
import java.util.Map;

import com.mt.android.sys.mvc.controller.Controller;

public class BaseActivityID
{	
	public BaseActivityID(){
		registerActivity();
	}
	public static Map<String,Integer> map=new HashMap<String,Integer>();
	public void registerBaseActivity(){
		map.put("ACTIVITY_ID_LISTVIEW_SHOW", Controller.IDistributer());
		map.put("ACTIVITY_ID_TAB_TOP_SHOW",Controller.IDistributer());
		map.put("ACTIVITY_ID_TAB_BOTTOM_SHOW",Controller.IDistributer());
		map.put("ACTIVITY_ID_VIEW_SLIDE",Controller.IDistributer());
		map.put("ACTIVITY_ID_LAYOUT_REUSE",Controller.IDistributer());
		map.put("ACTIVITY_ID_MAIN",Controller.IDistributer());
		map.put("ACTIVITY_ID_HOME",Controller.IDistributer());
		map.put("ACTIVITY_ID_WAITCARD",Controller.IDistributer());
	}
	public void registerActivity(){
		registerBaseActivity();
	}
}
