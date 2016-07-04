package com.mt.app.padpayment.command;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.responsebean.ResultRespBean;

public class ToAdminSignoutCommand extends AbstractCommand{
    private String respcode = "";//用于存放响应码
	@Override
	protected void go() {
		// TODO Auto-generated method stub
		    respcode="签退成功";
	        Response response = new Response();  
		    response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
			Bundle bundle =new Bundle();
			ResultRespBean result = new ResultRespBean();
			result.setMessage(respcode);
			bundle.putSerializable("ResultRespBean", result);
			response.setBundle(bundle);
			int[] flags = new int[1];
			flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
			response.setFlags(flags);
			setResponse(response);
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		super.prepare();
	}

	@Override
	protected void onBeforeExecute() {
		// TODO Auto-generated method stub
		super.onBeforeExecute();
	}

	@Override
	protected void onAfterExecute() {
		// TODO Auto-generated method stub
		super.onAfterExecute();
	}

	@Override
	protected void notifyListener(Response response) {
		// TODO Auto-generated method stub
		super.notifyListener(response);
	}

}
