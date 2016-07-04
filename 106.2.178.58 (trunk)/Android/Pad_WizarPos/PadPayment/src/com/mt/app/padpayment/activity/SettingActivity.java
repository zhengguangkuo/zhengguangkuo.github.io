package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawComponent;
import com.mt.android.frame.smart.config.DrawGridViewAdapter;
import com.mt.android.frame.smart.config.DrawListViewAdapter;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.SettingReqBean;

/**
 * 
 * 
 * @Description:�������ý���
 * 
 * @author:dw
 * 
 * @time:2013-8-6 ����9:54:39
 */
public class SettingActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(SettingActivity.class);
	private DbHandle db = new DbHandle();
	private EditText ip, port, timeout, logTime, reversalFreq, reversalAmount;

	@Override
	public List getDataListById(String id) {
		// ��ȡ��ǰ��־������ʾ
		Map map = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "LOGLEVEL" }, null, null, null);
		List<String> list = new ArrayList<String>();
		if (map.size() > 0) {
			String current = (String) map.get("PARA_VALUE");
			list.add("verbose");
			list.add("debug");
			list.add("info");
			list.add("warn");
			list.add("error");
			list.add("assert");
			for (String str : list) {
				if (str.equals(current)) {
					list.remove(str);
					break;
				}
			}
			list.add(0, current);

		} else {
			list.add("verbose");
			list.add("debug");
			list.add("info");
			list.add("warn");
			list.add("error");
			list.add("assert");
		}
		return list;
	}

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("SETTING.SCREEN");
		// �����ݿ��ȡ��ǰ��־�������ڣ�����ʾ
		Map paraMap = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "LOGTIME" }, null, null, null);
		logTime = (EditText) findViewById("LOGTIME");
		if (paraMap != null && paraMap.size() > 0) {
			logTime.setText(paraMap.get("PARA_VALUE") + "");
		}

		// �����ݿ��ȡ��ǰЭ���ip�Ͷ˿�,��ʱʱ��,����ʾ
		Map protoclMap = db.selectOneRecord("TBL_PROTOCL", new String[] {
				"HOST", "PORT", "READTIMEOUT" }, "ID = ?",
				new String[] { "TCP_SHORT_1" }, null, null, null);
		ip = (EditText) findViewById("IP");
		ip.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		port = (EditText) findViewById("PORT");
		timeout = (EditText) findViewById("TIMEOUT");
		if (protoclMap != null && protoclMap.size() > 0) {
			ip.setText(protoclMap.get("HOST") + "");
			port.setText(protoclMap.get("PORT") + "");
			timeout.setText(protoclMap.get("READTIMEOUT") + "");
		}
		// ����Ƶ��
		reversalFreq = (EditText) findViewById("REVERSALFREQ");
		Map freqMap = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "REVERSAL_FREQ" }, null, null, null);
		if (freqMap != null && freqMap.size() > 0) {
			reversalFreq.setText(freqMap.get("PARA_VALUE") + "");
		}
		// ��������
		reversalAmount = (EditText) findViewById("REVERSALAMOUNT");
		Map amountMap = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "REVERSAL_AMOUNT" }, null, null, null);
		if (amountMap != null && amountMap.size() > 0) {
			reversalAmount.setText(amountMap.get("PARA_VALUE") + "");
		}
	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		response.getData();

	}

	@Override
	public Request getRequestByCommandName(String commandName) {

		SettingReqBean reqBean = new SettingReqBean();

		if (commandName.equals("SETTING")) {
			if (!MsgTools.checkEdit(logTime, this, "��־����ʱ�䲻��Ϊ��")) {
				return null;
			}
			if (!MsgTools.checkEdit(ip, this, "ip����Ϊ��")) {
				return null;
			}
			if (!MsgTools.checkEdit(port, this, "�˿ڲ���Ϊ��")) {
				return null;
			}
			if (!MsgTools.checkEdit(timeout, this, "��ʱʱ�䲻��Ϊ��")) {
				return null;
			}
			if (!MsgTools.checkEdit(reversalFreq, this, "����Ƶ�ʲ���Ϊ��")) {
				return null;
			}
			if (!MsgTools.checkEdit(reversalAmount, this, "������������Ϊ��")) {

				return null;
			}

			if (logTime != null && logTime.getText() != null) {
				reqBean.setLogTime(logTime.getText().toString());
			}
			if (ip != null && ip.getText() != null) {
				reqBean.setHost(ip.getText().toString());
			}
			if (port != null && port.getText() != null) {
				reqBean.setPort(port.getText().toString());
			}
			if (!DrawComponent.spinnerChecked.equals("")) {
				reqBean.setLogLevel(DrawComponent.spinnerChecked);
				DrawComponent.spinnerChecked = "";
			}
			if (timeout != null && timeout.getText() != null) {
				reqBean.setTimeout(timeout.getText().toString());
			}
			if (reversalFreq != null && reversalFreq.getText() != null) {
				reqBean.setReversalFreq(reversalFreq.getText().toString());
			}
			if (reversalAmount != null && reversalAmount.getText() != null) {
				if (Integer.parseInt(reversalAmount.getText().toString()) == 0) {
					MsgTools.toast(this, "������������Ϊ0", "l");
					return null;
				}
				reqBean.setReversalAmount(reversalAmount.getText().toString());
			}

			Request request = new Request();
			request.setData(reqBean);
			return request;
		}
		return new Request();
	}
}
