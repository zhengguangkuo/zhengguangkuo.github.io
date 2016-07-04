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
import com.mt.app.payment.requestbean.ForgotPwdReqBean;
import com.mt.app.payment.responsebean.ForgotPwdRespBean;
import com.mt.app.payment.responsebean.UserInfoResBean;

public class UserDoForgotPassCommand extends AbstractCommand{
	private static Logger log = Logger.getLogger(UserDoForgotPassCommand.class);
	@Override
	protected void go() {
		// TODO Auto-generated method stub
		try {
			log.debug("go");
			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.USR_FORGOTPWD, getRequest(),
					ForgotPwdRespBean.class);
			/*
			 * 发数据到跳转到的界面(接收响应数据)，
			 * 此处需要的数据是用于初始化下一个跳转到的界面的，
			 * 数据应该装到response中的bundle中，下一个界面从bundle中取出
			 */
			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-1")) {
					ResponseBean res = new ResponseBean();
					res.setMessage("重置失败");
					response.setError(true);
					response.setData(res);
				   setResponse(response);
				}else{
					if(list.get(0).getRespcode().equals("-3")){
					ResponseBean res = new ResponseBean();
					res.setMessage("一天之内不可重复找回密码");
					response.setError(true);
					response.setData(res);
					setResponse(response);
				}else{
				if(list.get(0).getRespcode().equals("-5")){
					ResponseBean res = new ResponseBean();
					res.setMessage("输入的用户名不存在请重新输入");
					response.setError(true);
					response.setData(res);
					setResponse(response);
				}else{
					ResponseBean res = new ResponseBean();
					int[] flags=new int[1];
					flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
					res.setMessage(list.get(0).getMessage());
					response.setError(false);
					response.setData(res);
					response.setFlags(flags);
					setResponse(response);
				 }
			   }
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
		
		if(response.isError())
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME); 
			} else
		{
	        response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME); 
		}
	}
}
