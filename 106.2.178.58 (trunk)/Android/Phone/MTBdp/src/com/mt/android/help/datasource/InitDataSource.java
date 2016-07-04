package com.mt.android.help.datasource;

import java.util.ArrayList;
import java.util.List;

import com.mt.android.R;
import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.frame.entity.TabDataBean;
import com.mt.android.help.activity.TabInnerFirstActivity;
import com.mt.android.help.activity.TabInnerForthActivity;
import com.mt.android.help.activity.TabInnerSecondActivity;
import com.mt.android.help.activity.TabInnerThirdActivity;

public class InitDataSource {
	/**
	 *   初始化数据的方法
	 */
	public static void initTabTopData(){
		
		List<TabDataBean> listData = new ArrayList<TabDataBean>();
		
		TabDataBean tabData_1 = new TabDataBean();
		tabData_1.setTextMess("菜单1");
		tabData_1.setClasses(TabInnerFirstActivity.class);
		listData.add(tabData_1);
		
		TabDataBean tabData_2 = new TabDataBean();
		tabData_2.setTextMess("菜单2");
		tabData_2.setClasses(TabInnerSecondActivity.class);
		listData.add(tabData_2);
		
		TabDataBean tabData_3 = new TabDataBean();
		tabData_3.setTextMess("菜单3");
		tabData_3.setClasses(TabInnerThirdActivity.class);
		listData.add(tabData_3);
		
//		FrameDataSource.tabDataSource.put("tabTopSource", listData);
	}
}
