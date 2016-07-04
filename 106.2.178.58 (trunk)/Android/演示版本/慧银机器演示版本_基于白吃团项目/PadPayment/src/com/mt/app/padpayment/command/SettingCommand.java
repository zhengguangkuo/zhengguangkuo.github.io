package com.mt.app.padpayment.command;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.SettingReqBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
/**
 * 

 * @Description:参数设置command

 * @author:dw

 * @time:2013-8-6 上午9:55:03
 */
public class SettingCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(SettingCommand.class);
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

		SettingReqBean reqBean = (SettingReqBean) getRequest().getData();
		Response response = new Response();
		response.setError(false);
		if (reqBean.getPort()!=null) { //设置端口号
			long num = db.update("TBL_PROTOCL", new String[]{"PORT"}, new String[]{reqBean.getPort()}, "ID = ?", new String[] { "TCP_SHORT_1" });
			if (num == 0) {
				response.setError(true);
			} else {
				new DbHandle().update("TBL_PARAMETER", new String[]{"PARA_VALUE"}, new String[]{"否"},"PARA_NAME = ?", new String[]{"INITIALIZE"});
			}
		}
		if (reqBean.getHost()!=null) { //设置ip 
			long num = db.update("TBL_PROTOCL", new String[]{"HOST"}, new String[]{reqBean.getHost()}, "ID = ?", new String[] { "TCP_SHORT_1" });
			if (num == 0) {
				response.setError(true);
			} else {
				new DbHandle().update("TBL_PARAMETER", new String[]{"PARA_VALUE"}, new String[]{"否"},"PARA_NAME = ?", new String[]{"INITIALIZE"});
			}
		}
		if (reqBean.getLogLevel()!=null) { //设置日志级别
			long num = db.update("TBL_PARAMETER", new String[]{"PARA_VALUE"}, new String[]{reqBean.getLogLevel()}, "PARA_NAME = ?", new String[] { "LOGLEVEL" });
			if (num == 0) {
				response.setError(true);
			}
		}
		if (reqBean.getLogTime()!=null) { //设置日志保存天数
			long num = db.update("TBL_PARAMETER", new String[]{"PARA_VALUE"}, new String[]{reqBean.getLogTime()}, "PARA_NAME = ?", new String[] { "LOGTIME" });
			if (num == 0) {
				response.setError(true);
			}
		}
		if (reqBean.getTimeout()!=null) { //设置协议超时时间
			long num = db.update("TBL_PROTOCL", new String[]{"READTIMEOUT"}, new String[]{reqBean.getTimeout()}, "ID = ?", new String[] { "TCP_SHORT_1" });
			if (num == 0) {
				response.setError(true);
			}
		}
		if (reqBean.getReversalFreq()!=null) { //设置冲正频率
			long num = db.update("TBL_PARAMETER", new String[]{"PARA_VALUE"}, new String[]{reqBean.getReversalFreq()}, "PARA_NAME = ?", new String[] { "REVERSAL_FREQ" });
			if (num == 0) {
				response.setError(true);
			}
		}
		if (reqBean.getReversalAmount()!=null) { //设置冲正次数
			long num = db.update("TBL_PARAMETER", new String[]{"PARA_VALUE"}, new String[]{reqBean.getReversalAmount()}, "PARA_NAME = ?", new String[] { "REVERSAL_AMOUNT" });
			if (num == 0) {
				response.setError(true);
			}
		}
		Bundle bundle = new Bundle();
		ResultRespBean result = new ResultRespBean();
		if(response.isError()) {
			result.setMessage("修改失败！");
		} else {
			result.setMessage("修改成功！");
		}
		bundle.putSerializable("ResultRespBean", result);
		response.setBundle(bundle);
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
		setResponse(response);
	}
}
