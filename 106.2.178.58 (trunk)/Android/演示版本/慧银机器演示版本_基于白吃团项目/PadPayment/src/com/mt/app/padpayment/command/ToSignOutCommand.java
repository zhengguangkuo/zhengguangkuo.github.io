package com.mt.app.padpayment.command;

import java.util.Map;
import org.apache.log4j.Logger;
import android.content.Intent;
import android.os.Bundle;
import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.message.iso.trans.SignInBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.SystemUtil;
import com.mt.app.padpayment.tools.TransSequence;

public class ToSignOutCommand extends AbstractCommand {
	DbHandle dbhandle = new DbHandle();
	TransSequence trans = new TransSequence();
	SystemUtil sysutil = new SystemUtil();
	private static Logger log = Logger.getLogger(ToSignOutCommand.class);
	private String respcode = "";// 用于存放响应码
	String auditnum = trans.getSysTraceAuditNum();

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
		/* 获取卡号,交易类型 */
		Response response = new Response();
		try {
			SignInBean signin = signin();
			if (signin != null) {

				respcode = signin.getRespCode();
				if (respcode.equals("00")) {// 响应成功
					respcode = "签退成功";
				} else {
					Map<String, String> map = dbhandle
							.rawQueryOneRecord(
									"select MESSAGE from TBL_RESPONSE_CODE where RESP_CODE = ?",
									new String[] { respcode });
					if (map.get("MESSAGE") != null) { // 对应的列数，这里取查询结果第3列
						respcode = map.get("MESSAGE");
					}
					respcode = "签退失败";
				}
		
				Bundle bundle = new Bundle();
				ResultRespBean result = new ResultRespBean();
				result.setMessage(respcode);
				bundle.putSerializable("ResultRespBean", result);
				response.setBundle(bundle);
				int[] flags = new int[1];
				flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
				response.setFlags(flags);
				setResponse(response);
			} else {
				response.setError(true);
				Bundle bundle = new Bundle();
				ResultRespBean bean = new ResultRespBean();
				bean.setMessage("签退失败");
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
				setResponse(response);
			}

		} catch (Exception e) {
			response.setError(true);
			Bundle bundle = new Bundle();
			ResultRespBean bean = new ResultRespBean();
			bean.setMessage("签退失败");
			bundle.putSerializable("ResultRespBean", bean);
			response.setBundle(bundle);
			setResponse(response);
			e.printStackTrace();
		}
	}

	@Override
	protected void onAfterExecute() {
		super.onAfterExecute();
		Response response = getResponse();

		if (response.isError()) {
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_CANCELRESULT"));
		} else {
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_CANCELRESULT"));
		}
		setResponse(response);
	}

	/**
	 * 签退
	 * 
	 * @return SignInBean结果集
	 */
	private SignInBean signin() {
		SignInBean signin = new SignInBean();
		signin.setMsgId("0820");
		signin.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
		signin.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42
		String str2 = PackUtil.fillField(auditnum, 6, true, "0");
		signin.setSysTraceAuditNum(str2);// 受卡方系统跟踪号11
		SignInBean bean = new SignInBean();
		IsoCommHandler comm = new IsoCommHandler();
		bean = (SignInBean) comm.sendIsoMsg(signin); // 打包并发送报文
		return bean;
	}
}
