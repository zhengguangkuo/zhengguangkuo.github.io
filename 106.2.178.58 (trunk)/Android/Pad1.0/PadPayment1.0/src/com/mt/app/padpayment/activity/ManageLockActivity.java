package com.mt.app.padpayment.activity;

import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.util.ExitApplication;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.common.SysManager;
import com.mt.app.padpayment.requestbean.LockInfoReqBean;

/**
 * 锁定终端界面
 * 
 * @author Administrator
 * 
 */
public class ManageLockActivity extends DemoSmartActivity {
	// 柜员号码 ，密码
	private EditText lockNum, lockPass;
	private TextView tvError;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("MANAGE_LOCK.SCREEN");

		lockNum = (EditText) findViewById("manage_lock_number");
		lockPass = (EditText) findViewById("manage_lock_password");

		tvError = (TextView) findViewById("ERROR_INFO");

		Map map = new DbHandle().selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "INITIALIZE" }, null, null, null);
		if (map == null || map.size() == 0 || map.get("PARA_VALUE").equals("否")) {

			AlertDialog.Builder builder = new Builder(ManageLockActivity.this);
			builder.setMessage("初始化失败，退出程序");
			builder.setTitle("提示");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							dialog.dismiss();
							stopService(SysManager.intentservice);

							ExitApplication.getInstance().exit();
						}
					});

			builder.create().show();

		}
	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if (commandIDName.equals("LOCKED_OVER")) {
			Request request = new Request();
			if (!MsgTools.checkEdit(lockNum, this, "柜员号不能为空")) {
				return null;
			}
			if (!MsgTools.checkEdit(lockPass, this, "密码不能为空")) {
				return null;
			}
			LockInfoReqBean bean = new LockInfoReqBean();
			if (lockNum.getText() != null && !(lockNum.equals(""))) {
				bean.setLockNum(lockNum.getText().toString());
			}
			if (lockPass.getText() != null && !(lockPass.equals(""))) {
				bean.setLockPass(lockPass.getText().toString());
			}
			request.setData(bean);
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
		tvError.setText("您输入的柜员号或密码有误，请重新输入！");
		tvError.setTextColor(Color.RED);
	}
}
