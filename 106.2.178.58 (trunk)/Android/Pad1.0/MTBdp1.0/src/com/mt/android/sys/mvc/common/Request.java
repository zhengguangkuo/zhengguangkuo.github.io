package com.mt.android.sys.mvc.common;

import android.os.Handler;

public class Request
{
	private Handler listener;
	private Object data;
	private int activityID;
	
	public Handler getListener() {
		return listener;
	}

	public void setListener(Handler listener) {
		this.listener = listener;
	}

	public Request()
	{
	}

	public Request(Object data)
	{
		this.data = data;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public int getActivityID()
	{
		return activityID;
	}

	public void setActivityID(int activityID)
	{
		this.activityID = activityID;
	}
}
