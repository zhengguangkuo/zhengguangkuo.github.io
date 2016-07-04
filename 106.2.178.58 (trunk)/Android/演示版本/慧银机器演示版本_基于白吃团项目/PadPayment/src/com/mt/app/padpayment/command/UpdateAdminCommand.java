package com.mt.app.padpayment.command;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.AdminReqBean;
import com.mt.app.padpayment.requestbean.SettingReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;

/**
 * 

 * @Description:修改柜员command

 * @author:dw

 * @time:2013-8-6 下午2:27:29
 */
public class UpdateAdminCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(UpdateAdminCommand.class);
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

		AdminReqBean reqBean = (AdminReqBean) getRequest().getData();
		Response response = new Response();
		response.setError(false);

		long num = db.updateObject("TBL_ADMIN", reqBean, "USER_ID = ?",new String[]{reqBean.getUserId()});
		if (num == 0) {
			response.setError(true);
		}

		Bundle bundle = new Bundle();
		ResultRespBean result = new ResultRespBean();
		if (response.isError()) {
			result.setMessage("修改失败！");
		} else {
			result.setMessage("修改成功！");
		}
		bundle.putSerializable("ResultRespBean", result);
		response.setBundle(bundle);
		response.setTargetActivityID(ActivityID.map
				.get("ACTIVITY_ID_CANCELRESULT"));
		setResponse(response);
	}
}
