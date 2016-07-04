package com.mt.android.sys.mvc.command;

import android.os.Handler;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;

public abstract class AbstractBaseCommand implements ICommand
{
	private Request request;
	private Response response;
	private Handler responseListener;
	private boolean terminated;

	public AbstractBaseCommand()
	{
		super();
	}

	public Request getRequest()
	{
		return request;
	}

	public void setRequest(Request request)
	{
		this.request = request;
	}

	public Response getResponse()
	{
		return response;
	}

	public void setResponse(Response response)
	{
		this.response = response;
	}

	public Handler getResponseListener()
	{
		return responseListener;
	}

	public void setResponseListener(Handler responseListener)
	{
		this.responseListener = responseListener;
	}

	public boolean isTerminated()
	{
		return terminated;
	}

	public void setTerminated(boolean terminated)
	{
		this.terminated = terminated;
	}

}
