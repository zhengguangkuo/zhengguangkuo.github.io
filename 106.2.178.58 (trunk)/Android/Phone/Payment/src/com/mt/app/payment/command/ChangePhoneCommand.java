package com.mt.app.payment.command;

import java.util.List;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;

public class ChangePhoneCommand extends AbstractCommand {

	private boolean isSessionOut = false;//判断session是否失效
	
	@Override
	protected void go() {
		try {
			String info;
			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(Constants.USR_CHANGEPHONE, getRequest(),
					ResponseBean.class);
			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")) {// 判断session是否失效
					isSessionOut = true;
				}

				ResponseBean respbean = list.get(0);
				response.setData(respbean);
				setResponse(response);
			} else {
				ResponseBean res = new ResponseBean();
				res.setMessage("请检查网络连接");
				response.setError(true);
				response.setData(res);
				setResponse(response);
			}
		} catch (Exception e) {
			Response response = new Response();
			response.setError(true);
			setResponse(response);
		}
	}

	@Override
	protected void onAfterExecute() {
		super.onAfterExecute();
		Response response = getResponse();

		response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		if (isSessionOut) {// 如果session失效，跳转登录界面
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}
	}

}
