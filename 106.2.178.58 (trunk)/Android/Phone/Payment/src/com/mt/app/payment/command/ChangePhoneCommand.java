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

	private boolean isSessionOut = false;//�ж�session�Ƿ�ʧЧ
	
	@Override
	protected void go() {
		try {
			String info;
			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(Constants.USR_CHANGEPHONE, getRequest(),
					ResponseBean.class);
			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")) {// �ж�session�Ƿ�ʧЧ
					isSessionOut = true;
				}

				ResponseBean respbean = list.get(0);
				response.setData(respbean);
				setResponse(response);
			} else {
				ResponseBean res = new ResponseBean();
				res.setMessage("������������");
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
		if (isSessionOut) {// ���sessionʧЧ����ת��¼����
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}
	}

}
