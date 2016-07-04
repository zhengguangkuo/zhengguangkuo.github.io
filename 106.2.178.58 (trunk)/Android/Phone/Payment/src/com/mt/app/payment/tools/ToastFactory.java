package com.mt.app.payment.tools;

import android.content.Context;
import android.widget.Toast;

public class ToastFactory {

	private static Context context = null;

	private static Toast toast = null;

	/**
	 * @param context
	 *            使用时的上下文
	 * @param hint
	 *            在提示框中需要显示的文本
	 * @return 返回一个不会重复显示的toast
	 * */

	public static Toast getToast(Context context, String hint) {

		if (ToastFactory.context == context) {

			toast.cancel();

			toast.setText(hint);

			System.out.println("没有新创建");

		} else {

			System.out.println("创建了一个新的toast");

			ToastFactory.context = context;

			toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);

		}

		return toast;

	}

}