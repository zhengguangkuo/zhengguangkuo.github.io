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
import com.mt.app.payment.responsebean.UserRegRespBean;

public class RegCodeVerfiCommand extends AbstractCommand{
	private static Logger log = Logger.getLogger(RegCodeVerfiCommand.class);
	private boolean isSessionOut = false;//�ж�session�Ƿ�ʧЧ

	   @Override
    protected void go() {
			// TODO Auto-generated method stub
			try {
				log.debug("go");
				Response response = new Response();
				List<ResponseBean> list = DispatchRequest.doHttpRequest(
						Constants.USR_SUBMIT_VERIFY, getRequest(),
						UserRegRespBean.class);
				/*
				 * �����ݵ���ת���Ľ���(������Ӧ����)��
				 * �˴���Ҫ�����������ڳ�ʼ����һ����ת���Ľ���ģ�
				 * ����Ӧ��װ��response�е�bundle�У���һ�������bundle��ȡ��
				 */
				
				if (list != null && list.size() > 0) {
					response.setData(list.get(0));
					if (list.get(0).getRespcode().equals("-2")){//�ж�session�Ƿ�ʧЧ
						isSessionOut = true;
					}

					if (!list.get(0).getRespcode().equals("0")) {
						ResponseBean res = new ResponseBean();
						res.setMessage(list.get(0).getMessage());
						response.setError(true);
						response.setData(res);
					   setResponse(response);
					} else {
						int[] flags=new int[1];
						flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
						response.setFlags(flags);
						response.setError(false);
						UserRegRespBean respBean = (UserRegRespBean)list.get(0);
						respBean.setBusiInfo("1");
						response.setData(respBean);
						
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
		protected void onAfterExecute()
		{	
			Response response = getResponse();
			
			if(response.isError())
			{
				response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME); 
			} else
			{
		        response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME); 
			}
			if (isSessionOut) {//���sessionʧЧ����ת��¼����
				response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
			}

		}
}
