package com.mt.app.payment.command;

import java.util.List;

import org.apache.log4j.Logger;

import android.os.Bundle;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringUtil;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.requestbean.MealBindUnBindReqBean;
import com.mt.app.payment.responsebean.MealSelected_DataBean;

public class SelectPackageCommand extends AbstractCommand{
	
		private static Logger log = Logger.getLogger(SelectPackageCommand.class);
		private boolean isSessionOut = false;//判断session是否失效
		   @Override
	    protected void go() {
			  
		// TODO Auto-generated method stub
			   try {
					// TODO Auto-generated method stub
					log.debug("go");
					Response response = new Response();
					List<ResponseBean> list = DispatchRequest.doHttpRequest(
							Constants.USR_BIND_PACKAGE, getRequest(),
							MealSelected_DataBean.class);
					if (list != null && list.size() > 0) {
						if (list.get(0).getRespcode().equals("-2")){//判断session是否失效
							isSessionOut = true;
						}

						if (list.get(0).getRespcode().equals("-1")) {
							ResponseBean res = new ResponseBean();
							res.setMessage("操作失败");
							response.setError(false);
							response.setData(res);
							setResponse(response);						
							} else {
							ResponseBean res = new ResponseBean();
							res.setMessage("操作成功");
							response.setError(false);
							response.setData(res);
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
			protected void onAfterExecute() {
				log.debug("onAfterExecute");

				Response response = getResponse();

				if (response.isError()) {
					response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
				} else {
					response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
				}
				if (isSessionOut) {//如果session失效，跳转登录界面
					response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
				}

			}

		}


