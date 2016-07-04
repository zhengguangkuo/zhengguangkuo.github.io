package com.mt.app.payment.command;

import org.apache.log4j.Logger;

import android.os.Bundle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.requestbean.MyAccountReqBean;

public class ResetMyAccountOfPhoneCommand extends AbstractCommand {
	private static Logger log = Logger
			.getLogger(ResetMyAccountOfPhoneCommand.class);

	@Override
	protected void go() {
		// TODO Auto-generated method stub
		Response response = new Response();
		setResponse(response);
	}

	@Override
	protected void onAfterExecute() {
		Response response = getResponse();
		response.setTargetActivityID(ActivityID.map
				.get("ACTIVITY_ID_ResetMyaccount_Phone"));

	}
}
