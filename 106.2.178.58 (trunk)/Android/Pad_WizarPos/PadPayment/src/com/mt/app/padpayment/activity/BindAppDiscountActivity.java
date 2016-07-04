package com.mt.app.padpayment.activity;

import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;

/**
 * 绑定应用密码输入界面（带有优惠劵）
 * 
 * @author Administrator
 * 
 */
public class BindAppDiscountActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(BindAppDiscountActivity.class);
	private boolean isSend = false;
	private ConsumeReqBean reqBean; 

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("BINDAPP_DISCOUNT.SCREEN");

		TextView tv = (TextView) findViewById("TEXT_DISCOUNT_INFO");

		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {
			 reqBean = (ConsumeReqBean) bundle
					.getSerializable("ConsumeReqBean");

			String text = "优惠券扣除成功，抵扣金额为  " + reqBean.getCouponsSum()
					+ "元，待付金额为  " + reqBean.getRealSum() + "元";
			tv.setText(text);
		}

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
	public Request getRequestByCommandName(String commandName) {

		Request request = new Request();

		if (commandName.equals("APPPAYSUCCESS")) {

			EditText tv = (EditText) findViewById("EDIT_APP_PASSWORD");
			if (!MsgTools.checkEdit(tv, this, "密码不能为空")) {
				return null;
			}

			hasWaitView();

			Bundle bundle = getIntent().getBundleExtra("bundleInfo");
			if (bundle != null) {

				ConsumeReqBean reqBean = (ConsumeReqBean) bundle
						.getSerializable("ConsumeReqBean");
				// TODO 从加密键盘读取数据
				// reqBean.setAppPwd(tv.getText().toString());

				request.setData(reqBean);
				
			}
			isSend = true;
		}
		return request;

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (!isSend) {
			
				DbHandle db = new DbHandle();
				Map map = db.selectOneRecord("TBL_FlOW", new String[] {
						"CARD_NO", "PROCESS_CODE", "TRANS_AMOUNT",
						"SYS_TRACE_AUDIT_NUM", "AUTHOR_IDENT_RESP",
						"ADDIT_RESP_DATA", "SWAP_CODE_1",
						"COUPONS_ADVERT_ID","RESERVED_PRIVATE2" }, "SYS_TRACE_AUDIT_NUM = ?",
						new String[] { reqBean.getFlowNum() }, null,
						null, null);
				if (map != null && map.size() > 0) {
					db.insert(
							"TBL_REVERSAL",
							new String[] { "MSG_ID","CARD_NO", "PROCESS_CODE",
									"TRANS_AMOUNT",
									"SYS_TRACE_AUDIT_NUM",
									"AUTHOR_IDENT_RESP",
									"ADDIT_RESP_DATA", "SWAP_CODE",
									"COUPONS_ADVERT_ID" ,"FLUSH_OCUNT","FLUSH_RESULT","RESERVED_PRIVATE2"},
							new String[] {
									"0200",
									map.get("CARD_NO") + "",
									map.get("PROCESS_CODE") + "",
									map.get("TRANS_AMOUNT") + "",
									map.get("SYS_TRACE_AUDIT_NUM") + "",
									map.get("AUTHOR_IDENT_RESP") + "",
									map.get("ADDIT_RESP_DATA") + "",
									map.get("SWAP_CODE_1") + "",
									map.get("COUPONS_ADVERT_ID") + "" ,"0","-1",
									map.get("RESERVED_PRIVATE2")+""});
					db.delete("TBL_FlOW", "SYS_TRACE_AUDIT_NUM = ?",
						new String[] { reqBean.getFlowNum() });
				}
			
		
		}
	}
	
}
