package com.mt.app.padpayment.command;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;

public class ToPaymentQueryCommand extends AbstractCommand{

	private static Logger log = Logger.getLogger(ToPaymentQueryCommand.class);

	@Override
	protected void go() {
		// TODO Auto-generated method stub
		Response response = new Response();
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_PaymentQueryActivity"));
		Bundle bundle =new Bundle();
		 response.setBundle(bundle);
		 int[] flags = new int[1];
		 flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
		 response.setFlags(flags);
		setResponse(response);
	}

}
