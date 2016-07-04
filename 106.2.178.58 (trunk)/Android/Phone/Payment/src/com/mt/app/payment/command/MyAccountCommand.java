package com.mt.app.payment.command;

import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.util.StringUtil;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.responsebean.AppBind_DataBean;
import com.mt.app.payment.responsebean.UserInfoResBean;

public class MyAccountCommand extends AbstractCommand{
	private static Logger log = Logger.getLogger(MyAccountCommand.class);
	
	@Override
	protected void go() {
		// TODO Auto-generated method stub
			log.debug("go");
			Response response = new Response();
			setResponse(response);
	}

	@Override
	protected void onAfterExecute()
	{	
		Response response = getResponse();
		
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_MYACCOUNT"));
	}
}