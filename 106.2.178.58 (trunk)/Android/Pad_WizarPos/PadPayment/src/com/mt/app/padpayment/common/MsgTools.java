package com.mt.app.padpayment.common;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;

public class MsgTools {
	private static Toast toast = null;
	private static Handler mhandler = new Handler();
	private static Runnable r = new Runnable(){
		public void run() {
			toast.cancel();
			toast = null;
		};
	};

	/**
	 * 
	 * @param context
	 *            上下文对象
	 * @param msg
	 *            要显示的信息
	 * @param timeTag
	 *            时间参数 若是“s”表示短时间显示 若是“l”（小写L）表示长时间显示
	 */
	public static void toast(Context context, String msg, String timeTag) {
		

			if (toast == null) {
			int time = Toast.LENGTH_SHORT;
			if (timeTag == null || "l".equals(timeTag)) {
				time = Toast.LENGTH_LONG;
			}

			// cancelToast();
			
			
			toast = Toast.makeText(context, null, time);
			
			LinearLayout layout = (LinearLayout) toast.getView();
			/*
			 * layout.setLayoutParams(new WindowManager.LayoutParams(10000,
			 * android.view.WindowManager.LayoutParams.WRAP_CONTENT,
			 * WindowManager.LayoutParams.TYPE_TOAST,
			 * WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
			 * WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
			 * WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
			 * PixelFormat.TRANSLUCENT));
			 */
			layout.setBackgroundResource(R.drawable.button_bg);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.CENTER_VERTICAL);
			TextView tv = new TextView(context);
			tv.setLayoutParams(new LayoutParams(300, 100));
			tv.setGravity(Gravity.CENTER_VERTICAL);
			tv.setTextColor(Color.parseColor("#5B5B5B"));
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
			tv.setPadding(0, 0, 0, 0);
			tv.setText(msg);
			layout.addView(tv);
			mhandler.postDelayed(r, 5000);
			toast.show();
			}

	}

	public static void cancelToast() {

		if (toast != null) {
			toast.cancel();
			if (toast!= null) {
				toast = null;
			}
		}
	}

	public static boolean checkEdit(EditText et, Context context, String msg) {
		if (et == null || et.getText().toString().trim().equalsIgnoreCase("")||et.getText().toString().trim().equals("0.00")) {
			toast(context, msg, "l");
			return false;
		} else {
			return true;
		}

	}
}
