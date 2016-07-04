package com.mt.app.padpayment.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.content.Intent;
import android.os.Bundle;
import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.PaymentQueryReqBean;

public class PaymentQueryResultCommand extends AbstractCommand {
	PaymentQueryReqBean queryreq = new PaymentQueryReqBean();
	ArrayList<Map<String, String>> list;
	PaymentQueryReqBean querybean = new PaymentQueryReqBean();
	private String condition = " where 1=1 and RESP_CODE_1 = '00'";
	DbHandle dbhandle = new DbHandle();

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
	protected void go() {
		// TODO Auto-generated method stub
		queryreq = (PaymentQueryReqBean) getRequest().getData();
		if (queryreq.getSerialnum() != null
				&& (!queryreq.getSerialnum().trim().equalsIgnoreCase(""))) {
			condition = condition + " and SYS_TRACE_AUDIT_NUM='"
					+ queryreq.getSerialnum() + "' ";
		}
		if (queryreq.getReferencenum() != null
				&& (!queryreq.getReferencenum().trim().equals(""))) {
			condition = condition + " and RET_REFER_NUM_1='"
					+ queryreq.getReferencenum() + "' ";
		}
		if (queryreq.getTime() != null
				&& (!queryreq.getTime().trim().equals(""))) {
			condition = condition + " and LOCAL_TRANS_DATE_1='"
					+ queryreq.getTime() + "' ";
		}
		if (queryreq.getUserId() != null
				&& (!queryreq.getUserId().trim().equals(""))) {
			condition = condition + " and USER_ID='"
					+ queryreq.getUserId() + "' ";
		}
		list = (ArrayList<Map<String, String>>) dbhandle.rawQuery(
				"select * from TBL_FlOW" + condition +" order by SYS_TRACE_AUDIT_NUM desc", null);
		querybean.setList(list);
		Response response = new Response();
		response.setTargetActivityID(ActivityID.map
				.get("ACTIVITY_ID_PaymentQueryResultActivity"));
		Bundle bundle = new Bundle();
		bundle.putSerializable("list", querybean);
		response.setBundle(bundle);
		int[] flags = new int[1];
		flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
		response.setFlags(flags);
		setResponse(response);
	}

	@Override
	protected void onAfterExecute() {
		// TODO Auto-generated method stub
		super.onAfterExecute();
	}
}
