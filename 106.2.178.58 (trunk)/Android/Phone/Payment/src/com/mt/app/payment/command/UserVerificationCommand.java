package com.mt.app.payment.command;

import java.util.List;

import org.apache.log4j.Logger;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;

public class UserVerificationCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(UserVerificationCommand.class);

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

			List<ResponseBean> list = DispatchRequest
					.doHttpRequest(Constants.USR_GET_REGTEXT, getRequest(),
							ResponseBean.class);

			if (list != null && list.size() > 0) {
				response.setData(list.get(0));
				if (list.get(0).getRespcode().equals("-1")) {
					response.setError(true);
				} else {
					list.get(0).setMessage("短信发送中...");
					response.setError(false);
				}
			} else {
				ResponseBean res = new ResponseBean();
				res.setMessage("请检查网络连接");
				response.setError(true);
				response.setData(res);
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

		response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);

	}

}
