package com.mt.app.padpayment.command;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;

public class ToSureConsumeCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(ToSureConsumeCommand.class);

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
		ConsumeReqBean reqBean = (ConsumeReqBean) getRequest().getData();
		Response response = new Response();

		// ���ѽ���Ϊ0
		if (reqBean.getSum().equalsIgnoreCase("")) {
			response.setError(true);
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			response.setData("���������ѽ��!");
			setResponse(response);
			return;
		}
		// ����û���δѡ���Ż�ȯ��δѡ��Ӧ��
		if (!reqBean.isUseCoupons() && reqBean.getAppId().equalsIgnoreCase("")) {
			response.setError(true);
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			response.setData("��ѡ��֧��Ӧ�û��Ż�ȯ!");
			setResponse(response);
			return;
		}

		if (!reqBean.isUseCoupons()) { // ��ʹ���Ż�ȯ���Ż�ȯ�ۿ۽��Ϊ0
			reqBean.setCouponsSum("0.00");
		}
		if (reqBean.getAppId().equals("0")) { // ��ʹ�û�Ա������Ա���ۿ۽��Ϊ0
			reqBean.setVipSum("0.00");
		}

		Bundle bundle = new Bundle();
		bundle.putSerializable("ConsumeReqBean", reqBean);
		response.setBundle(bundle);
		response.setTargetActivityID(ActivityID.map
				.get("ACTIVITY_ID_SureConsumeActivity"));
		setResponse(response);
	}

}
