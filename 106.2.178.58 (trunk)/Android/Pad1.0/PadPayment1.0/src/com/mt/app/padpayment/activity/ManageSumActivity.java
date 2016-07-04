package com.mt.app.padpayment.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.util.StringFormat;
import com.mt.android.sys.util.StringUtil;
import com.mt.app.padpayment.common.MathUtil;
import com.mt.app.padpayment.tools.MoneyUtil;

/**
 * 结算界面
 * 
 * @author Administrator
 * 
 */
public class ManageSumActivity extends DemoSmartActivity {
	// 消费交易的总金额和总笔数
	private TextView consumeAmount, consumeCount;
	// 退货交易的总金额和总笔数
	private TextView backGoodsAmount, backGoodsCount;
	// 所有交易的总金额和总笔数
	private TextView totalAmount, totalCount;
	// 撤销的交易总额和总笔数
	private TextView repealAmount, repealCount;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("MANAGE_SUM.SCREEN");

		consumeAmount = (TextView) findViewById("sum_consume_amount");
		consumeCount = (TextView) findViewById("sum_consume_count");
		backGoodsAmount = (TextView) findViewById("sum_backgoods_amount");
		backGoodsCount = (TextView) findViewById("sum_backgoods_count");
		repealAmount = (TextView) findViewById("sum_repeal_amount");
		repealCount = (TextView) findViewById("sum_repeal_count");
		totalAmount = (TextView) findViewById("sum_total_amount");
		totalCount = (TextView) findViewById("sum_total_count");

		String conAm = null;
		String conCo = "0";
		String bacAm = null;
		String showbacAm = null; // 显示为负数
		String bacCo = "0";
		String repealAm = null;
		String repealCo = "0";
		String showbreAm = null; //显示为负数

		// 从数据库中查出数据
		DbHandle handle = new DbHandle();
		List<Map<String, String>> list = handle
				.rawQuery(
						"select MSG_ID||PROCESS_CODE aaa,sum(TRANS_AMOUNT) sss,count(*) ccc from  TBL_FlOW where RESP_CODE_1 = '00' group by MSG_ID||PROCESS_CODE", null);
		if (list != null) {
			for (Map<String, String> map : list) {
				if (map.get("aaa").equals("0200000000")) { //消费

					conAm = MoneyUtil.getMoney(map.get("sss"));
					conCo = map.get("ccc");
				} else if (map.get("aaa").equals("0220200000")) { //退货
					
					bacAm = MoneyUtil.getMoney(map.get("sss"));
					showbacAm = "-" + bacAm;
					bacCo = map.get("ccc");
				} else if (map.get("aaa").equals("0200200000")) { //撤销
					repealAm = MoneyUtil.getMoney(map.get("sss"));
					showbreAm = "-" + repealAm;
					repealCo = map.get("ccc");
				}
			}
		}
		if (conAm != null && conCo != null) {
			consumeAmount.setText(conAm);
			consumeCount.setText(conCo);
		}
		if (showbreAm != null && repealCo != null) {
			repealAmount.setText(showbreAm);
			repealCount.setText(repealCo);
		}
		if (showbacAm != null && bacCo != null) {
			backGoodsAmount.setText(showbacAm);
			backGoodsCount.setText(bacCo);
		}
		
		totalAmount.setText(MathUtil.sub(conAm,MathUtil.add(bacAm, repealAm)));
		
		int count = Integer.parseInt(conCo) + Integer.parseInt(bacCo) + Integer.parseInt(repealCo);
		totalCount.setText(String.valueOf(count));

	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if (commandIDName.equals("TO_MANAGE_MAIN")) {
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
