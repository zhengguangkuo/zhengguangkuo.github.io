package com.mt.app.padpayment.command;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.util.Log;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.activity.AdFlashActivity;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;

public class ToFirstLoginCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(ToFirstLoginCommand.class);

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
		ReadCardReqBean readcard =(ReadCardReqBean)getRequest().getData();
		if(readcard != null){
			Controller.session.put("CardNum",readcard.getCardNum().toString());
		}
		int[] flags=new int[1];
		flags[0]=Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
		response.setFlags(flags);
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_ManageLockActivity"));

		AdFlashActivity.isLoginDisplay = true;//第一次启动的时候先跳登录界面
		setResponse(response);
	}

	@Override
    protected void onAfterExecute(){
		
	}
}
