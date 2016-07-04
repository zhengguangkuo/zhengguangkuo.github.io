package com.mt.app.padpayment.command;


import org.apache.log4j.Logger;

import android.content.Intent;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;

public class AdFlashCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(AdFlashCommand.class);

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
		int[] flags=new int[1];
		flags[0]=Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
		response.setFlags(flags);
		if(Controller.session.get("userID") != null){
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_GotoMainActivity"));
		}else{
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_ManageLockActivity"));
		}
		setResponse(response);
	}


}
