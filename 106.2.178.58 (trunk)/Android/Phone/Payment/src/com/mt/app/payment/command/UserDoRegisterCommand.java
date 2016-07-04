package com.mt.app.payment.command;

import java.util.List;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;

public class UserDoRegisterCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(UserDoRegisterCommand.class);

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

			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.USR_REGISTER, getRequest(), ResponseBean.class);

			if (list != null && list.size() > 0) {
				response.setData(list.get(0));
				if (list.get(0).getRespcode().equals("-1")) {
					response.setError(true);
				} else {
					Bundle bundle = new Bundle();
					bundle.putString("return", "ok");
					response.setBundle(bundle);
					response.setError(false);
				}
			} else {
				ResponseBean res = new ResponseBean();
				res.setMessage("Çë¼ì²éÍøÂçÁ¬½Ó");
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
		if (response.isError()) {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else {
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}
	}

}
