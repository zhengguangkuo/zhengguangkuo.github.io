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
	private String respcode = "";// ���ڴ����Ӧ��
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
		/* ��ȡ����,�������� */
		Response response = new Response();
		try {
			SignInBean signin = signin();
			if (signin != null) {

				respcode = signin.getRespCode();
				if (respcode.equals("00")) {// ��Ӧ�ɹ�
					respcode = "ǩ�˳ɹ�";
				} else {
					Map<String, String> map = dbhandle
							.rawQueryOneRecord(
									"select MESSAGE from TBL_RESPONSE_CODE where RESP_CODE = ?",
									new String[] { respcode });
					if (map.get("MESSAGE") != null) { // ��Ӧ������������ȡ��ѯ�����3��
						respcode = map.get("MESSAGE");
					}
					respcode = "ǩ��ʧ��";
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
				bean.setMessage("ǩ��ʧ��");
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
				setResponse(response);
			}

		} catch (Exception e) {
			response.setError(true);
			Bundle bundle = new Bundle();
			ResultRespBean bean = new ResultRespBean();
			bean.setMessage("ǩ��ʧ��");
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
	 * ǩ��
	 * 
	 * @return SignInBean�����
	 */
	private SignInBean signin() {
		SignInBean signin = new SignInBean();
		signin.setMsgId("0820");
		signin.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
		signin.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42
		String str2 = PackUtil.fillField(auditnum, 6, true, "0");
		signin.setSysTraceAuditNum(str2);// �ܿ���ϵͳ���ٺ�11
		SignInBean bean = new SignInBean();
		IsoCommHandler comm = new IsoCommHandler();
		bean = (SignInBean) comm.sendIsoMsg(signin); // ��������ͱ���
		return bean;
	}
}
