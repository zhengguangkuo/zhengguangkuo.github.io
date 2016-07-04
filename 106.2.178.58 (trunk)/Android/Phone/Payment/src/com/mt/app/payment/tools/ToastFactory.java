package com.mt.app.payment.tools;

import android.content.Context;
import android.widget.Toast;

public class ToastFactory {

	private static Context context = null;

	private static Toast toast = null;

	/**
	 * @param context
	 *            ʹ��ʱ��������
	 * @param hint
	 *            ����ʾ������Ҫ��ʾ���ı�
	 * @return ����һ�������ظ���ʾ��toast
	 * */

	public static Toast getToast(Context context, String hint) {

		if (ToastFactory.context == context) {

			toast.cancel();

			toast.setText(hint);

			System.out.println("û���´���");

		} else {

			System.out.println("������һ���µ�toast");

			ToastFactory.context = context;

			toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);

		}

		return toast;

	}

}