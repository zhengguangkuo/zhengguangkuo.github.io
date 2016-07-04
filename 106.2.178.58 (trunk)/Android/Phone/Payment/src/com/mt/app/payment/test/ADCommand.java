package com.mt.app.payment.test;

import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.responsebean.AdResult;

public class ADCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(ADCommand.class);
	private boolean isSessionOut = false;//�ж�session�Ƿ�ʧЧ

	
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
			List<ResponseBean> adList = DispatchRequest.doHttpRequest(Constants.USR_AD, new Request(), AdResult.class);
			
			if (adList != null && adList.size() > 0) {
				if (adList.get(0).getRespcode().equals("-2")){//�ж�session�Ƿ�ʧЧ
					isSessionOut = true;
				}
				
				if (adList.get(0).getRespcode().equals("-1")) {
					response.setError(true);
				} else {
					AdResult adResult = (AdResult) adList.get(0);
					response.setData(adResult);					
					int[] flags=new int[1];
					flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
					response.setFlags(flags);
					response.setError(false);
					response.setBussinessType("adcommand") ;
				}
			} else {
				ResponseBean res = new ResponseBean();
				res.setMessage("�����ͼƬ��ȡʧ�ܣ�������������");
				response.setError(true);
				response.setData(res);
				response.setBussinessType("adcommand") ;
			}

			setResponse(response);
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