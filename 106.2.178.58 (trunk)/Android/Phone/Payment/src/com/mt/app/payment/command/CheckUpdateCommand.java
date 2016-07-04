package com.mt.app.payment.command;

import java.util.List;

import android.content.Intent;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.responsebean.UpdateResBean;

public class CheckUpdateCommand extends AbstractCommand{
	private boolean isSessionOut = false;//判断session是否失效

	@Override
	protected void go() {
		try {

			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.USR_APP_UPDATE, getRequest(),
					UpdateResBean.class);
		 	/*
			 * 发数据到跳转到的界面(接收响应数据)，
			 * 此处需要的数据是用于初始化下一个跳转到的界面的，
			 * 数据应该装到response中的bundle中，下一个界面从bundle中取出
			 */
			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")){//判断session是否失效
					isSessionOut = true;
				}

				if (list.get(0).getRespcode().equals("-1")) {
					ResponseBean res = new ResponseBean();
					res.setMessage(list.get(0).getMessage());
					response.setError(true);
					response.setData(res);
					setResponse(response);
				} if(list.get(0).getRespcode().equals("1")){
					int[] flags=new int[1];
					flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
					response.setError(false);
					response.setData(list.get(0));
					response.setFlags(flags);
					setResponse(response);
					
				}else {
					int[] flags=new int[1];
					flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
					response.setError(true);
					ResponseBean res = new ResponseBean();
					res.setMessage("已经是最新版本");
					response.setData(res);
					response.setFlags(flags);
					setResponse(response);
				}
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
	protected void onAfterExecute()
	{	
		Response response = getResponse();
		
		if(response != null && response.isError())
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);  
		} else if(response != null ){
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		}
		if (isSessionOut) {//如果session失效，跳转登录界面
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}
	}