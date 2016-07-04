package com.mt.app.padpayment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.AdminReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;

/**
 * 
 * 
 * @Description:提交柜员号
 * 
 * @author:dw
 * 
 * @time:2013-8-6 下午2:45:51
 */
public class SubmitUserCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(SubmitUserCommand.class);
	private Bundle bundle = new Bundle();
	private Response response = new Response();
	private DbHandle db = new DbHandle();

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
		if (Controller.session.get("succForward") != null
				&& Controller.session.get("succForward").equals(
						ActivityID.map
								.get("ACTIVITY_ID_UpdateAdminActivity"))) {
			AdminReqBean reqBean = (AdminReqBean) getRequest().getData();
			if (reqBean!= null) {
				Map map = db.selectOneRecord("TBL_ADMIN", new String[] {
						"USER_NAME", "PASSWORD", "LIMITS" }, "USER_ID = ?",
						new String[] { reqBean.getUserId() }, null, null,
						null);
				if (map != null && map.size() > 0) {
					reqBean.setLimits((String) map.get("LIMITS"));
					reqBean.setPassword((String) map.get("PASSWORD"));
					reqBean.setUserName((String) map.get("USER_NAME"));
					bundle.putSerializable("AdminReqBean", reqBean);
					
					response.setBundle(bundle);
				} else {
					response.setError(true);
				}
			} else {
				response.setError(true);
			}
		}
		setResponse(response);
	}

	@Override
	protected void onAfterExecute() {
		Response response = getResponse();
		if (response.isError()) {
			ResultRespBean result = new ResultRespBean();
			result.setMessage("无此柜员信息！");
			bundle.putSerializable("ResultRespBean", result);
			response.setBundle(bundle);
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_CANCELRESULT"));
		} else {
			response.setTargetActivityID((Integer) Controller.session
					.get("succForward"));
		}
	}
}
