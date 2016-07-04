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
 * ԭ���׽���
 * 
 * @author Administrator
 * 
 */
public class OriginalDealActivity extends DemoSmartActivity {
	private TextView cardnum; // ����
	private TextView vouchNum; // ԭ����ƾ֤�ţ���ˮ�ţ�
	private TextView disAmount; // �Ż݄��ֿ۽��
	private TextView vipAmount; // ��Ա���ۿ۽��
	private TextView actualMon; // ʵ�ս����׽�
	private TextView dealNum; // ���ײο��ţ������ο��ţ�
	private String vouch = "";
	private String vip = "0.00";//�̻��ۿ۽��
	private String dis = "0.00";//�һ����
	private String real = "0.00";//Ӧ�ս��
	private String sum = "0.00";//�ܶ�

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

		// �����ݿ���в�ѯ�����ݣ����ݿ��ź�ԭ����ƾ֤���ֶΣ�����ʼ������
		DbHandle handle = new DbHandle();
		// ��ѯ�����ţ��ֿ۽�� ��ʵ�ս�ԭ����ƾ֤�ţ�ԭ������ˮ�ţ������ײο��ţ������ο��ţ�
		String[] columns = new String[] { "TRACK2", "DISCOUNT_AMOUNT",
				"TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM",
				"RET_REFER_NUM_1" };
		List<Map<String, String>> list = handle.select("TBL_FlOW", columns,
				"SYS_TRACE_AUDIT_NUM=?", new String[] { vouch }, null, null,
				null);
		if (list != null && list.size() > 0) {

			// ���ÿ���
			cardnum.setText(list.get(0).get("TRACK2"));
			Controller.session.put("card", list.get(0).get("TRACK2"));
			String amount = list.get(0).get("DISCOUNT_AMOUNT");
			if (amount.length()==36) { //���ӽ���ֶ�
				vip = MoneyUtil.getMoney(amount.substring(0, 12));
				dis = MoneyUtil.getMoney(amount.substring(12, 24));
				sum = MoneyUtil.getMoney(amount.substring(24, 36));
			}
			// �����Ż݄��ֿ۽��
			disAmount
					.setText(dis);

			// ���û�Ա���ۿ۽��
			vipAmount.setText(vip);

			actualMon.setText(MoneyUtil.getMoney(list.get(0)
					.get("TRANS_AMOUNT")));// ����ʵ�ս��

			vouchNum.setText(list.get(0).get("SYS_TRACE_AUDIT_NUM"));// ����ԭ����ƾ֤�ţ�ԭ������ˮ�ţ�

			dealNum.setText(list.get(0).get("RET_REFER_NUM_1"));// ���ý��ײο��ţ������ο��ţ�

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
