package com.mt.android.sys.mvc.command;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;

/**
 * <p>
 * Steps for a general command:
 * </p>
 * <ol>
 * <li>Prepare: Initialize the command</li>
 * <li>onBeforeExecute: About to execute</li>
 * <li>go: Actual execution</li>
 * <li>onAfterExecute: Just executed. Notify the response listener.</li>
 * </ol>
 * 
 * @author Gaurav Vaish
 */
public abstract class AbstractCommand extends AbstractBaseCommand
{
	public final void execute()
	{try {

		Log.i("Current Execute Command: " ,  this.getClass().getName());
		prepare();
		onBeforeExecute();
		go();
		onAfterExecute();
		
		Request request = getRequest();
		Response response = getResponse();
		response.setListener(getResponseListener());
		
		if(response != null)
		{   
			notifyListener(response);
		}
	
	} catch (Exception e) {
		// TODO: handle exception
	}}

	protected void prepare()
	{
	}

	protected abstract void go();

	protected void onBeforeExecute()
	{
	}

	protected void onAfterExecute()
	{
	}

	protected void notifyListener(Response response)
	{
		Handler listener = response.getListener();
		
		if (listener != null){
			Message msg = new Message();
			msg.what = 0;
			msg.obj = response;
			listener.sendMessage(msg);
		}
	}
}
