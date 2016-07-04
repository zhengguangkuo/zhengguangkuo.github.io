package com.mt.app.payment.command;

import java.util.List;
import org.apache.log4j.Logger;

import android.text.TextUtils;
import android.util.Log;

import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;

/**
 * 
 * 设置默认支付应用
 */
public class EleCardAppDefaultCommand extends AbstractCommand {

	private static final String LOG_TAG = EleCardAppDefaultCommand.class.getSimpleName() ;
	private static Logger log = Logger.getLogger(EleCardAppDefaultCommand.class);
	private boolean isSessionOut = false;//判断session是否失效

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
			// TODO Auto-generated method stub
			log.debug("go");

			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.USR_APP_DEFAULT_SET, getRequest(), ResponseBean.class);
			response.setBussinessType("appDefault");
			
			Log.i(LOG_TAG, "list.size() ===== " + list.size()) ;
			
			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")){//判断session是否失效
					isSessionOut = true;
				}
//				response.setData(list.get(0));
				if (list.get(0).getRespcode().equals("0")) {
					response.setError(false) ;
					Controller.session.put("set_default_success_to_tab", "ok");
				} else {
					ResponseBean res = new ResponseBean();
					res.setMessage(TextUtils.isEmpty(list.get(0).getMessage()) ? "" : list.get(0).getMessage());
					response.setError(true);
					response.setData(res);
				}
			} else {
				ResponseBean res = new ResponseBean();
				res.setMessage("请检查网络连接");
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
		if (isSessionOut) {//如果session失效，跳转登录界面
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}
}
