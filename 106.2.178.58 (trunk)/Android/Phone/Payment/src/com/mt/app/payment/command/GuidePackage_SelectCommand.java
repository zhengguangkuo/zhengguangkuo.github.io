package com.mt.app.payment.command;

import java.util.List;
import org.apache.log4j.Logger;
import android.os.Bundle;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.ActivityID;
import com.mt.app.payment.common.Constants;
import com.mt.app.payment.common.DispatchRequest;
import com.mt.app.payment.responsebean.MealSelected_DataBean;

public class GuidePackage_SelectCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(GuidePackage_SelectCommand.class);
	private boolean isSessionOut = false;//判断session是否失效

	@Override
	protected void go() {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			log.debug("go");
			Response response = new Response();
			List<ResponseBean> list = DispatchRequest.doHttpRequest(
					Constants.USR_QUERY_PACKAGE, getRequest(),
					MealSelected_DataBean.class);
			Bundle bundle = new Bundle();
			if (list != null && list.size() > 0) {
				if (list.get(0).getRespcode().equals("-2")){//判断session是否失效
					isSessionOut = true;
				}

				if (list.get(0).getRespcode().equals("0")) {
				bundle.putSerializable("package", list.get(0));
				response.setBundle(bundle);
				response.setError(false);
				setResponse(response);

				}else {
					
					bundle.putSerializable("package", null);
					response.setError(true);
					response.setBundle(bundle);
					setResponse(response);
				} 
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
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_GuidePackage_Select"));
		} else {
			response.setTargetActivityID(ActivityID.map
					.get("ACTIVITY_ID_GuidePackage_Select"));
		}
		if (isSessionOut) {//如果session失效，跳转登录界面
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_USER_LOGIN"));
		}

	}

}
