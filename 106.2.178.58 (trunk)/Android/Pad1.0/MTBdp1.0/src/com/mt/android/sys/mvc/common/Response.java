package com.mt.android.sys.mvc.common;

import android.os.Bundle;
import android.os.Handler;

public class Response
{
	private Handler listener;

	private Object data;
	private boolean error;
	private int targetActivityID;
	private Bundle bundle;
	private int[] flags;
    private String bussinessType;    //用做业务区分的
	
	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public Response()
	{
	}

	public Response(Object data)
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

	public boolean isError()
	{
		return error;
	}

	public void setError(boolean error)
	{
		this.error = error;
	}

	public int getTargetActivityID()
	{
		return targetActivityID;
	}

	public void setTargetActivityID(int targetActivityID)
	{
		this.targetActivityID = targetActivityID;
	}
	
	public Handler getListener() {
		return listener;
	}

	public void setListener(Handler listener) {
		this.listener = listener;
	}
	public int[] getFlags() {
		return flags;
	}

	public void setFlags(int[] flags) {
		this.flags = flags;
	}
	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}
	
}
