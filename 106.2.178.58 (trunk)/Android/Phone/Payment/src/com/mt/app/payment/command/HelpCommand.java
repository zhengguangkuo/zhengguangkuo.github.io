package com.mt.app.payment.command;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;

public class HelpCommand extends AbstractCommand{
	@Override
	protected void go() {
		// TODO Auto-generated method stub
		Response response = new Response();
		setResponse(response);
	}
	 @Override
	protected void onAfterExecute() {
		// TODO Auto-generated method stub
		super.onAfterExecute();
		Response response = getResponse();
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_HELP"));
	}
}