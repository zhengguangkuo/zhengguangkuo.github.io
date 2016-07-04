package com.mt.app.padpayment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Intent;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.LockInfoReqBean;

public class LockOverCommand extends AbstractCommand {
	private static Logger log = Logger.getLogger(LockOverCommand.class);

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
		Response response = new Response();
		int[] flags = new int[1];
		flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
		response.setFlags(flags);
		
		LockInfoReqBean bean = (LockInfoReqBean)getRequest().getData();
		String num = bean.getLockNum();
		String pass = bean.getLockPass();
		String realPass = null;   //数据库中查出的密码
		String userN = null;     //数据库中查出的用户名
		String userL = null;     //数据库中查出的用户权限（等级）
		Controller.session.remove("userID");
		Controller.session.remove("userName");
		Controller.session.remove("userLimits");
		DbHandle handle = new DbHandle();
		Map<String , String> map = handle.rawQueryOneRecord("select * from TBL_ADMIN where USER_ID=?", new String[]{num});
		if(map != null){
			realPass = map.get("PASSWORD");
			userN = map.get("USER_NAME");
			userL = map.get("LIMITS");
			if(pass != null && pass.equals(realPass)){//用户编号和密码都正确
				Controller.session.put("userID", num);
				Controller.session.put("userName", userN);
				Controller.session.put("userLimits", userL);
				response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_GotoMainActivity"));
			}else{
				response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
				response.setError(true);
			}
		}else{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			response.setError(true);
		}
		setResponse(response);
	}
}
