package com.mt.android.sys.util;

import java.util.LinkedList;
import java.util.List;

import com.mt.android.global.Globals;
import com.mt.android.sys.mvc.controller.Controller;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

public class ExitApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();

	private static ExitApplication instance;

	private ExitApplication() {
	}

	// 单例模式中获取唯一的ExitApplication 实例
	public static ExitApplication getInstance() {
		try {
			if (null == instance) {
				instance = new ExitApplication();
			}
			return instance;

		} catch (Exception e) {

			return null;

		}
	}

	// 添加Activity 到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
		Log.i("rrr==", activityList.size() + "");
	}

	// 遍历所有Activity 并finish

	public void exit(Activity act) {
		try {
			finishAllAcivity(act);
			//Controller.session.clear();
			//Globals.map.clear();
			//System.exit(0);

		} catch (Exception e) {

		}
	}

	public void finishAllAcivity(Activity act) {
		try {
			for (Activity activity : activityList) {
				
				if(act != activity)
				{
					activity.finish();
				}
			}
		} catch (Exception e) {

		}
	}
}