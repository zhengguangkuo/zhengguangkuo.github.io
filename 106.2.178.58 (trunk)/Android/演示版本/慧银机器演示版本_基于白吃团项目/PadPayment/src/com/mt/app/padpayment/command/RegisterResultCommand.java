package com.mt.app.padpayment.command;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.Constants;
import com.mt.app.padpayment.common.DispatchRequest;
import com.mt.app.padpayment.responsebean.RegisterRespBean;
import com.mt.app.padpayment.responsebean.ResultRespBean;

public class RegisterResultCommand extends AbstractCommand {

	@Override
	protected void go() {
		/* 获取卡号,交易类型 */
		Response response = new Response();
		try {
			//发送请求
			List<ResponseBean> respList = DispatchRequest.doHttpRequest(
					Constants.USR_REGISTER, getRequest(), RegisterRespBean.class);
			
			Bundle bundle = new Bundle();
			if (respList != null) {//收到应答
				ResultRespBean bean = new ResultRespBean();
				bean.setMessage("注册成功！");
				if (respList.get(0).getRespcode()!= null && !"".equals(respList.get(0).getRespcode()) && !"0".equals(respList.get(0).getRespcode())) {
					bean.setMessage("注册失败！");
				}
				
				if (respList.get(0).getMessage()!= null && !"".equals(respList.get(0).getMessage()) ) {
					bean.setMessage(respList.get(0).getMessage());
				}
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
			} else {
				response.setError(true);
				ResultRespBean bean = new ResultRespBean();
				bean.setMessage("请检查网络连接！");
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
			}
			
			int[] flags = new int[1];
			flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
			response.setFlags(flags);
			setResponse(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onAfterExecute() {
		super.onAfterExecute();
		Response response = getResponse();

		if (response.isError()) {
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_RegisterResultActivity"));
		} else {
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_RegisterResultActivity"));
		}
		setResponse(response);
	}
}
