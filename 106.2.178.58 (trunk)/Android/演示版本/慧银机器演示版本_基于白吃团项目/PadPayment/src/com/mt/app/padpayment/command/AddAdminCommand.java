package com.mt.app.padpayment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.AdminReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;

/**
 * 
 * 
 * @Description:����û�command
 * 
 * @author:dw
 * 
 * @time:2013-8-6 ����2:27:29
 */
public class AddAdminCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(AddAdminCommand.class);
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
		Bundle bundle = new Bundle();
		ResultRespBean result = new ResultRespBean();
		if (reqBean.getUserId() != null && reqBean.getPassword() != null
				&& reqBean.getUserName() != null && reqBean.getLimits() != null&&!reqBean.getUserId().equals("") && !reqBean.getPassword().equals("")
				&& !reqBean.getUserName().equals("") && !reqBean.getLimits().equals("")) {

			Map map = db.selectOneRecord("TBL_ADMIN",
					new String[] { "USER_ID" }, "USER_ID = ?",
					new String[] { reqBean.getUserId() }, null, null, null);

			if (map != null && map.get("USER_ID") != null) {
				result.setMessage("���ʧ��,��Ա�Ѵ��ڣ�");
			} else {
				long num = db.insertObject("TBL_ADMIN", reqBean);
				if (num == 0) {
					response.setError(true);
					result.setMessage("���ʧ�ܣ�");
				} else {
					result.setMessage("��ӳɹ���");
				}
			}

		} else {
			result.setMessage("���ʧ�ܣ�����δ��д�����ݣ�");
		}
		bundle.putSerializable("ResultRespBean", result);
		response.setBundle(bundle);
		response.setTargetActivityID(ActivityID.map
				.get("ACTIVITY_ID_CANCELRESULT"));
		setResponse(response);
	}
}
