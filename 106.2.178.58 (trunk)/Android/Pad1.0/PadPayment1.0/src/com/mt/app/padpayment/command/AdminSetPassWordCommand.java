package com.mt.app.padpayment.command;

import android.content.Intent;
import android.util.Log;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;

public class AdminSetPassWordCommand extends AbstractCommand{

	@Override
	protected void go() {
		// TODO Auto-generated method stub
	
		
		
		Response response = new Response();
		int[] flags=new int[1];
		flags[0]=Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
		int id=getRequest().getActivityID();
		response.setFlags(flags);
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_AdminSetPassWordActivity"));
		setResponse(response);
		
	}
}
