package com.mt.android.global;

import java.util.HashMap;
import java.util.Map;

import com.mt.android.sys.common.view.BaseActivity;

/**
 * 

 * @Description:用于存放子工程给库工程传递的数据
	
	目前map中存有：DialogManager 解析的dialog.xml文件 
	 			ActivityID  注册的activityid
	 			CommandID 注册的commandid
	 			Initializer activityid 和commandid与相应类的对应关系
	 			DrawableID  图片与R中id的对应关系
 * @author:dw

 * @time:2013-7-19 上午9:32:39
 */
public class Globals {

	public static Map<String,Object> map=new HashMap<String,Object>();//存放app应用工程中定义的数据结果
	public static void setMap(String key,Object object){
		map.put(key,object);
	}
	public static Object AppManage;

  
	
	
	
}
