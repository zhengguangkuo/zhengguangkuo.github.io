package com.mt.app.padpayment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringFormat;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.requestbean.AdminPassReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.TransSequence;

public class ToAdminCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(ToAdminCommand.class);
	

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
		int[] flags = new int[1];
		flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
		response.setFlags(flags);

		// 从界面上获得主管的密码
		AdminPassReqBean bean = (AdminPassReqBean) getRequest().getData();
		String pass = bean.getPassWord();
		DbHandle handle = new DbHandle();
		Map<String, String> map = handle
				.rawQueryOneRecord(
						"select PASSWORD from TBL_ADMIN where PASSWORD=?  and LIMITS = 2",
						new String[] { pass });
		if (map!=null&&map.size() != 0) {
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_AdminMainActivity"));
		} else { // 密码错误 返回当前界面
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			response.setError(true);
		}
		setResponse(response);
	}
}
