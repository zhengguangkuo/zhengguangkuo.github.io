package com.mt.app.payment.command;

import android.content.Intent;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;

public class UserForgotPassCommand extends AbstractCommand{

	@Override
	protected void go() {
		Response response = new Response();
		int[] flags=new int[1];
		flags[0]=Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
		response.setFlags(flags);
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_FORGOTPASS"));
		setResponse(response);
	}
	


}
