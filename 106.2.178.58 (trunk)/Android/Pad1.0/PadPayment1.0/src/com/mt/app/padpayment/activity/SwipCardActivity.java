package com.mt.app.padpayment.activity;

import java.util.Random;

import org.apache.log4j.Logger;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.ReadCardReqBean;

/**
 * 刷卡等待界面
 * 
 * @author Administrator
 * 
 */
public class SwipCardActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(SwipCardActivity.class);
	private EditText CardNumber;
	private TextView cardError;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("SWIP_CARD.SCREEN");

		CardNumber = (EditText) findViewById("EDIT_CARD_NUMBER");
		cardError = (TextView) findViewById("card_error");
	}
	
	@Override
	public void onReadCard(String cardnumber) {
		// TODO Auto-generated method stub
		super.onReadCard(cardnumber);
		
//		 Random r = new Random();
//		CardNumber.setText(cardnumber + r.nextInt(10));
		CardNumber.setText(cardnumber);
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		cardError.setText("卡号错误！");
		cardError.setTextColor(Color.RED);
	}

	@Override
	public Request getRequestByCommandName(String commandName) {

		Request request = new Request();
		if (commandName.equals("SubmitCard")) {
			ReadCardReqBean readcard = new ReadCardReqBean();
			String cardNo = CardNumber.getText() == null ? "" : CardNumber
					.getText().toString();

			if (cardNo.trim().equalsIgnoreCase("")) {
				MsgTools.toast(SwipCardActivity.this, "卡号不能为空", "l");
				return null;
			}

			if (CardNumber.getText() != null
					&& !(CardNumber.getText().equals(""))) {
				readcard.setCardNum(CardNumber.getText().toString());
			}
			request.setData(readcard);

			if (Controller.session.get("succForward") != null) {
				// ---支付应用------------------------------------------------------
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_NoteConsumeActivity"))) {
					// 出现等待画面
					hasWaitView();
				}
				if (Controller.session.get("succForward").equals(
						ActivityID.map
								.get("ACTIVITY_ID_CreditsConsumeActivity"))) {
					hasWaitView();
				}
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_CreditsQueryActivity"))) {
					hasWaitView();
				}

				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"))) {
					hasWaitView();
				}
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_InputFlowNumActivity"))) {
					hasWaitView();
				}
				// 支付应用-查询：
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_SearchActivity"))) {
					hasWaitView();
				}
				// --------------------------------------------------------------------

				// -----优惠劵申领-------------
				if (Controller.session.get("succForward").equals(
						ActivityID.map.get("ACTIVITY_ID_CouponsInfoActivity"))) {
					hasWaitView();
				}
				// --------------------------------------------------------------------

			}
		}

		return request;
	}

}
