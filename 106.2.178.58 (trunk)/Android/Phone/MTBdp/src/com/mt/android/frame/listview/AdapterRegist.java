package com.mt.android.frame.listview;

import java.util.HashMap;
import java.util.Map;
import com.mt.android.help.listview.HelpListViewAdapter;

public class AdapterRegist {
	public static  Map<String, Class> mAdapterRegist = new HashMap<String, Class>();

	static{
		//key��ֵ��listview�����ֶ�Ӧ
		mAdapterRegist.put("help_listview", HelpListViewAdapter.class);
	}
}
