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
		/* ��ȡ����,�������� */
		Response response = new Response();
		try {
			//��������
			List<ResponseBean> respList = DispatchRequest.doHttpRequest(
					Constants.USR_REGISTER, getRequest(), RegisterRespBean.class);
			
			Bundle bundle = new Bundle();
			if (respList != null) {//�յ�Ӧ��
				ResultRespBean bean = new ResultRespBean();
				bean.setMessage("ע��ɹ���");
				if (respList.get(0).getRespcode()!= null && !"".equals(respList.get(0).getRespcode()) && !"0".equals(respList.get(0).getRespcode())) {
					bean.setMessage("ע��ʧ�ܣ�");
				}
				
				if (respList.get(0).getMessage()!= null && !"".equals(respList.get(0).getMessage()) ) {
					bean.setMessage(respList.get(0).getMessage());
				}
				bundle.putSerializable("ResultRespBean", bean);
				response.setBundle(bundle);
			} else {
				response.setError(true);
				ResultRespBean bean = new ResultRespBean();
				bean.setMessage("�����������ӣ�");
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
