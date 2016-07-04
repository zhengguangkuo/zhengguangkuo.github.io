package com.mt.app.padpayment.activity;

import org.apache.log4j.Logger;

import android.os.Bundle;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.android.view.common.BaseCommandID;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
/**
 * 支付应用主菜单界面
 * @author Administrator
 *
 */
public class PayMentAppActivity extends DemoSmartActivity{
	private static Logger log = Logger.getLogger(PayMentAppActivity.class);
	
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
			setContentView("PAYMENT_MAIN.SCREEN");
	}
	
	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setActivityIDById(String id){
	if(id != null && id.equalsIgnoreCase("PAY_CONSUM")){  //支付应用消费跳转的界面ID
				Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_NoteConsumeActivity"));
				/*如果刷卡失败，跳转其他 activity*/
				// Controller.session.put("erroForward", "ACTIVITY_ID_PayMentAppActivity");
			}
	 if(id != null && id.equalsIgnoreCase("PAY_CANCEL")){  //消费撤销跳转的界面ID
			Controller.session.put("type", "consume");
			/*如果刷卡失败，跳转其他 activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_PayMentAppActivity");
		} 
	 if(id != null && id.equalsIgnoreCase("PAY_RETURN_GOODS")){   //消费退货跳转的界面ID
			Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_OriginalDate"));
			Controller.session.put("type", "backGoods");
			/*如果刷卡失败，跳转其他 activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_PayMentAppActivity");
		} 
	 
	 if(id.equalsIgnoreCase("PAY_OBSERVE")){   //消费查询跳转的界面ID
			Controller.session.put("succForward", ActivityID.map.get("ACTIVITY_ID_SearchActivity"));
			Controller.session.put("type", "consumeCheck");
			/*如果刷卡失败，跳转其他 activity*/
			// Controller.session.put("erroForward", "ACTIVITY_ID_PayMentAppActivity");
		}
	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		Request request=new Request();
		return request;
	}
}
