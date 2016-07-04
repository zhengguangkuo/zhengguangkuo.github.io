package com.mt.app.payment.command;

import java.util.List;
import org.apache.log4j.Logger;
import android.content.Intent;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.requestbean.LoginReqBean;

public class UserDoLoginCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(UserDoLoginCommand.class);

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
					Constants.USR_LOGIN, getRequest(), ResponseBean.class);

			if (list != null && list.size() > 0) {
				response.setData(list.get(0));
				if (list.get(0).getRespcode().equals("-1")) {
					response.setError(true);
					}else {
					Controller.session.put("user", (LoginReqBean) getRequest()
							.getData());
					int[] flags=new int[1];
					flags[0]=Intent.FLAG_ACTIVITY_SINGLE_TOP;
					response.setFlags(flags);
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
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			//response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_NOLOGININDEX"));
			Controller.session.put("IsLogin", "true");//ÉèÖÃµÇÂ½×´Ì¬ÎªÒÑµÇÂ½
		}
	}

}
