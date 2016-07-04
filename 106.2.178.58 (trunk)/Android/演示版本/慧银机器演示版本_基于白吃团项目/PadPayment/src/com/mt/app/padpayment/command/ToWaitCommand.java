package com.mt.app.padpayment.command;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringUtil;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;

public class ToWaitCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(ToWaitCommand.class);

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
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_RequestWaitActivity"));
		setResponse(response);
	}
}
