package com.mt.app.payment.command;

import android.content.Intent;

import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.activity.EleCard_pay_addCardActivity;
import com.mt.app.tab.activity.TabDiscount_01_MainListActivity;
import com.mt.app.tab.activity.TabEleCardActivity;

public class PaymentMainActivity_id_discountbtn_Command extends AbstractCommand {

	@Override
	protected void go() {
		// TODO Auto-generated method stub
		try {
			Response response = new Response();
			int[] flags=new int[1];
			flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
			int id=getRequest().getActivityID();
			response.setFlags(flags);
			setResponse(response);
		} catch (Exception e) {
			Response response = new Response();
			response.setError(true);
			setResponse(response);
		}
	}

	@Override
	protected void onAfterExecute() {
		Response response = getResponse();

		if (response.isError()) {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else {
			FrameDataSource.tabDataClass.put("tabClass",TabDiscount_01_MainListActivity.class);
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_EleCard_DemoTab"));
		}
	}
}
