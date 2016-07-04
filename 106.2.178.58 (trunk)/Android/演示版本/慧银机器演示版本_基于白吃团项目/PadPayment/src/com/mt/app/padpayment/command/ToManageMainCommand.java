package com.mt.app.padpayment.command;

import org.apache.log4j.Logger;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;

public class ToManageMainCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(ToManageMainCommand.class);

	@Override
	protected void prepare() {
		log.debug("prepare");
	}

	@Override
	protected final void onBeforeExecute() {
		log.debug("onBeforeExecute");
	}

	@Override
	protected void go() {
		Response response = new Response();
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_ManageMainActivity"));
		setResponse(response);
	}
}
