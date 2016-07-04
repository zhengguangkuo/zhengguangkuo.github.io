package com.mt.app.padpayment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.responsebean.ResultRespBean;


/**
 * 
 * 
 * @Description:结算command
 * 
 * @author:dw
 * 
 * @time:2013-9-9 下午8:01:51
 */
public class ClearingCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(ClearingCommand.class);

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

		Bundle bundle = new Bundle();

		ResultRespBean resultRespBean = new ResultRespBean();

		try {
			DbHandle db = new DbHandle();
			db.delete("TBL_FlOW", null, null);
			String str = DbHelp.getBatchNum();
			if (DbHelp.getBatchNum() != null) {
				int num = Integer.parseInt(str);
				if (num==999999) {
					num = 100000;
				} else {
					str = num + 1+ "";
				}
				db.update(
						"TBL_PARAMETER",
						new String[] { "PARA_VALUE" },
						new String[] { str }, "PARA_NAME = ?",
						new String[] { "batchNum" });

			}
			Map map = db.selectOneRecord("TBL_PARAMETER",
					new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
					new String[] { "batchNum" }, null, null, null);
			if (map != null && map.size() > 0) {
				GlobalParameters.g_map_para.put("batchNum",
						map.get("PARA_VALUE") + "");// 设置批次号
			}
			resultRespBean.setMessage("结算成功！");

		} catch (Exception e) {
			resultRespBean.setMessage("结算失败！");
		}

		bundle.putSerializable("ResultRespBean", resultRespBean);
		response.setBundle(bundle);

		setResponse(response);

	}

	@Override
	protected void onAfterExecute() {

		super.onAfterExecute();
		Response response = getResponse();

		response.setTargetActivityID(ActivityID.map
				.get("ACTIVITY_ID_CANCELRESULT"));

		setResponse(response);

	}
}
