package com.mt.android.global;

import java.util.HashMap;
import java.util.Map;

import com.mt.android.sys.common.view.BaseActivity;

/**
 * 

 * @Description:���ڴ���ӹ��̸��⹤�̴��ݵ�����
	
	Ŀǰmap�д��У�DialogManager ������dialog.xml�ļ� 
	 			ActivityID  ע���activityid
	 			CommandID ע���commandid
	 			Initializer activityid ��commandid����Ӧ��Ķ�Ӧ��ϵ
	 			DrawableID  ͼƬ��R��id�Ķ�Ӧ��ϵ
 * @author:dw

 * @time:2013-7-19 ����9:32:39
 */
public class Globals {

	public static Map<String,Object> map=new HashMap<String,Object>();//���appӦ�ù����ж�������ݽ��
	public static void setMap(String key,Object object){
		map.put(key,object);
	}
	public static Object AppManage;

  
	
	
	
}
