package com.mt.android.sys.mvc.command;

import android.os.Handler;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;

public interface ICommand
{
	Request getRequest();

	void setRequest(Request request);

	Response getResponse();

	void setResponse(Response response);

	void execute();

	Handler getResponseListener();

	void setResponseListener(Handler listener);

	void setTerminated(boolean terminated);

	boolean isTerminated();
}
