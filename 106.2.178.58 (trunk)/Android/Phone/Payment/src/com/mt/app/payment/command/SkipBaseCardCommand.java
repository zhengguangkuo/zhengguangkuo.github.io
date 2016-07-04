package com.mt.app.payment.command;

import org.apache.log4j.Logger;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;

public class SkipBaseCardCommand extends AbstractCommand {
	
	private static Logger log = Logger.getLogger(SkipBaseCardCommand.class);
	
	@Override
	protected void go() {
			log.debug("go");
			Response response = new Response();
			setResponse(response);
	}

	@Override
	protected void onAfterExecute() {	
		Response response = getResponse();
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_BaseCard"));
	}

}
