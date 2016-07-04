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

		// 消费金额不能为0
		if (reqBean.getSum().equalsIgnoreCase("")) {
			response.setError(true);
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			response.setData("请输入消费金额!");
			setResponse(response);
			return;
		}
		// 如果用户既未选择优惠券又未选择应用
		if (!reqBean.isUseCoupons() && reqBean.getAppId().equalsIgnoreCase("")) {
			response.setError(true);
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			response.setData("请选择支付应用或优惠券!");
			setResponse(response);
			return;
		}

		if (!reqBean.isUseCoupons()) { // 不使用优惠券，优惠券折扣金额为0
			reqBean.setCouponsSum("0.00");
		}
		if (reqBean.getAppId().equals("0")) { // 不使用会员卡，会员卡折扣金额为0
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
