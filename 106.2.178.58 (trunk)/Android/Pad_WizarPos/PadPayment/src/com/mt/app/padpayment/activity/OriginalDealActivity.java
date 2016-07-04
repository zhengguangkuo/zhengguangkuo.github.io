package com.mt.app.padpayment.activity;

import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.OriginalDealReqBean;
import com.mt.app.padpayment.tools.MoneyUtil;
import com.mt.app.padpayment.tools.PackUtil;

/**
 * 原交易界面
 * 
 * @author Administrator
 * 
 */
public class OriginalDealActivity extends DemoSmartActivity {
	private TextView cardnum; // 卡号
	private TextView vouchNum; // 原交易凭证号（流水号）
	private TextView disAmount; // 优惠劵抵扣金额
	private TextView vipAmount; // 会员卡折扣金额
	private TextView actualMon; // 实收金额（交易金额）
	private TextView dealNum; // 交易参考号（检索参考号）
	private String vouch = "";
	private String vip = "0.00";//商户折扣金额
	private String dis = "0.00";//兑换金额
	private String real = "0.00";//应收金额
	private String sum = "0.00";//总额

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("ORIGINALDEAL.SCREEN");
		cardnum = (TextView) findViewById("TWO");
		vouchNum = (TextView) findViewById("FOUR");
		disAmount = (TextView) findViewById("SIX");
		vipAmount = (TextView) findViewById("EIGHT");
		actualMon = (TextView) findViewById("TEN");
		dealNum = (TextView) findViewById("TELEVE");

		if (Controller.session.get("Vouchers") != null) {
			vouch = Controller.session.get("Vouchers").toString();
		}

		// 从数据库表中查询出数据（根据卡号和原交易凭证号字段），初始化界面
		DbHandle handle = new DbHandle();
		// 查询：卡号，抵扣金额 ，实收金额，原交易凭证号（原交易流水号），交易参考号（检索参考号）
		String[] columns = new String[] { "TRACK2", "DISCOUNT_AMOUNT",
				"TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM",
				"RET_REFER_NUM_1" };
		List<Map<String, String>> list = handle.select("TBL_FlOW", columns,
				"SYS_TRACE_AUDIT_NUM=?", new String[] { vouch }, null, null,
				null);
		if (list != null && list.size() > 0) {

			// 设置卡号
			cardnum.setText(list.get(0).get("TRACK2"));
			Controller.session.put("card", list.get(0).get("TRACK2"));
			String amount = list.get(0).get("DISCOUNT_AMOUNT");
			if (amount.length()==36) { //附加金额字段
				vip = MoneyUtil.getMoney(amount.substring(0, 12));
				dis = MoneyUtil.getMoney(amount.substring(12, 24));
				sum = MoneyUtil.getMoney(amount.substring(24, 36));
			}
			// 设置优惠劵抵扣金额
			disAmount
					.setText(dis);

			// 设置会员卡折扣金额
			vipAmount.setText(vip);

			actualMon.setText(MoneyUtil.getMoney(list.get(0)
					.get("TRANS_AMOUNT")));// 设置实收金额

			vouchNum.setText(list.get(0).get("SYS_TRACE_AUDIT_NUM"));// 设置原交易凭证号（原交易流水号）

			dealNum.setText(list.get(0).get("RET_REFER_NUM_1"));// 设置交易参考号（检索参考号）

		}
	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		hasWaitView();
		if (commandIDName != null && commandIDName.equals("TO_SWIP_CARD")) {
			Controller.session.put("succForward",
					ActivityID.map.get("ACTIVITY_ID_CANCELRESULT"));
			Request request = new Request();
			return request;
		}
		return new Request();
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

}
