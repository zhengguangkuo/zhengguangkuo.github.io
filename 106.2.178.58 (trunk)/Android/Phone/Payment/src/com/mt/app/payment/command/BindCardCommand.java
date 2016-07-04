package com.mt.app.payment.command;

import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.responsebean.Card_DataBean;

public class BindCardCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(BindCardCommand.class);
	private boolean isSessionOut = false;//�ж�session�Ƿ�ʧЧ


	@Override
	protected void go() {
		try {

			log.debug("go");
			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.USR_BASE_CARD_BIND, getRequest(),
					Card_DataBean.class);
			/*
			 * �����ݵ���ת���Ľ���(������Ӧ����)�� �˴���Ҫ�����������ڳ�ʼ����һ����ת���Ľ���ģ�
			 * ����Ӧ��װ��response�е�bundle�У���һ�������bundle��ȡ��
			 */
			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")){//�ж�session�Ƿ�ʧЧ
					isSessionOut = true;
				}

				if (list.get(0).getRespcode().equals("-1")) {
					response.setError(true);
					response.setData(list.get(0));
					setResponse(response);
				} else {
					int[] flags = new int[1];
					flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
					ResponseBean res = new ResponseBean();
					if (list.get(0).getMessage() != null) {
						res.setMessage(list.get(0).getMessage());
						if (list.get(0).getMessage().equals("�������ɹ�")) {
							Controller.session.remove("isBind");
							response.setBussinessType("BindBaseCard") ;
						}
						if (list.get(0).getMessage().equals("�����󶨳ɹ�")) {
							Controller.session.put("isBind", "bind");
							response.setBussinessType("BindBaseCard") ;
						}
					} else {
						res.setMessage("�����ѳɹ� ��");
					}
					response.setError(false);
					response.setData(res);
					response.setFlags(flags);
					setResponse(response);
				}
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
		Response response = getResponse();

		if (response != null && response.isError()) {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else if (response != null) {
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		}
		if (isSessionOut) {// ���sessionʧЧ����ת��¼����
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}
}