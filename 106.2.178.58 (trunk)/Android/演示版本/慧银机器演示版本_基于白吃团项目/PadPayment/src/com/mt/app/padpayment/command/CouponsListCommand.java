package com.mt.app.padpayment.command;

import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.common.Constants;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.DispatchRequest;
import com.mt.app.padpayment.common.GlobalParameters;
import com.mt.app.padpayment.requestbean.CouponsListReqBean;
import com.mt.app.padpayment.responsebean.CouponsListRespBean;

public class CouponsListCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(CouponsListCommand.class);

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
		try {
			log.debug("go");
			Response response = new Response();
			CouponsListReqBean reqBean = (CouponsListReqBean) getRequest()
					.getData();
			
			reqBean.setMerchantCode(DbHelp.getCardAcceptIdentcode());// 设置商户编号
			String cardN = Controller.session.get("CardNum").toString();
			reqBean.setBaseCardNo(cardN);// 设置卡号

			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.USR_COUPONS, getRequest(),
					CouponsListRespBean.class);

			if (list != null && list.size() >= 0) {
				
				response.setData(list);
				
				int[] flags = new int[1];
				flags[0] = Intent.FLAG_ACTIVITY_SINGLE_TOP;
				response.setFlags(flags);
				response.setError(false);

			} else {
				response.setError(true);
				response.setData("请检查网络连接");
			}

			setResponse(response);
		} catch (Exception e) {

			Response response = new Response();
			response.setError(true);
			setResponse(response);

		}
	}

	@Override
	protected void onAfterExecute() {
		log.debug("onAfterExecute");

		Response response = getResponse();
		if (response.isError()) {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else {

			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);

		}
	}

}
