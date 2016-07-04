package com.mt.app.padpayment.command;

import android.content.Intent;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;

/**
 * ¹ñÔ±É¾³ý°´Å¥command
 * @author lzw
 *
 */
public class AdminDeleteCommand extends AbstractCommand{
	
	@Override
	protected void go() {
		// TODO Auto-generated method stub
	
		Response response = new Response();
		int[] flags=new int[1];
		flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
		int id=getRequest().getActivityID();
		response.setFlags(flags);
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_AdminDeleteActivity"));
		setResponse(response);
		
	}

}
