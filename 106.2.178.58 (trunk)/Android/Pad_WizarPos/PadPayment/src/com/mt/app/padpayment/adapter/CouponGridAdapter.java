package com.mt.app.padpayment.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.frame.smart.config.ListViewAdapter;
import com.mt.android.protocol.http.client.ImageHttpPadClient;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.common.MsgTools;

public class CouponGridAdapter extends ListViewAdapter<String[]> {
	private String[] textArr = null;
	private String[] imageArr = null;
	private String[] id = null; // �Ż�ȯid
	private String[] eId = null;// �id
	private String[] appId = null;// �Ż�ȯӦ��id
	private String[] objId = null;// ��ȯ����id
	private String[] detail = null;// ʹ�ù���
	public static String listChecked = null;// ��ȯ�����ʶ
	public static String apId = null;// �Ż�ȯӦ��id
	public static List<String> list = new ArrayList<String>();
	private String[] checkstats = null;
	private List<String> checked = new ArrayList<String>();
	private Context context = null;
	private List<String> pList = new ArrayList<String>();
	private Map<String,Bitmap> map = new HashMap<String,Bitmap>();
	public CouponGridAdapter(List<String[]> datas, Context context) {
		super(datas, context);

		this.context = context;
		if (datas == null) {
			datas = new ArrayList<String[]>();
		} else {
			textArr = new String[0];
		}
		if (datas!=null&&datas.size() ==7) {
			textArr = (String[]) datas.get(0);
			imageArr = (String[]) datas.get(1);
			id = (String[]) datas.get(2);
			eId = (String[]) datas.get(3);
			appId = (String[]) datas.get(4);
			objId = (String[]) datas.get(5);
			detail = (String[]) datas.get(6);
			
			if (imageArr!=null) {
				checkstats = new String[imageArr.length];
				ImageThread thread = new ImageThread();
				thread.start();// ʹ�� ���߳�����Ӧ��ͼƬ
			}
			for (int i = 0; i < checkstats.length; i++) {
				checkstats[i] = "0";
			}

		}
		

	}

	private Handler handler = new Handler() { // �첽ˢ��ͼƬ
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				notifyDataSetChanged();
				break;
			}
		}
	};

	public class ImageThread extends Thread {

		public void run() {

			

			for (int i = 0; i < imageArr.length; i++) {
				
				Bitmap bitmap = ImageHttpPadClient.getBitmap(imageArr[i]);
				map.put(imageArr[i], bitmap);
			}
			
			Message ms = Message.obtain();
			ms.what = 1;
			handler.sendMessage(ms);

			
		}
	}

	@Override
	public int getCount() {
		return textArr.length;
	}

	@Override
	public Object getItem(int position) {
		return textArr[position];
	}

	// ʵ�ָ÷������÷������ص�View����Ϊ�б��
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mt.android.frame.smart.config.ListViewAdapter#getView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = infalter.inflate(R.layout.adapter, null);
		}

		TextView tv1_help = (TextView) convertView
				.findViewById(R.id.gridlistdata1);
		final ImageView imgview = (ImageView) convertView
				.findViewById(R.id.gridimage1);
		final ImageView imgviewBack = (ImageView) convertView
				.findViewById(R.id.gridbackimage1);
		for (int i = 0; i < pList.size(); i++) {
			if (pList.contains(position + "")) {
				imgviewBack.setVisibility(View.VISIBLE);
			} else {
				imgviewBack.setVisibility(View.GONE);
			}
		}
		final ImageView detalimageView = (ImageView) convertView
				.findViewById(R.id.detalimageView);
		detalimageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				View layout = LayoutInflater.from(context).inflate(
						R.layout.payment_dialog,
						(ViewGroup) v.findViewById(R.id.payment_dialog));
				final WebView webView = (WebView) layout
						.findViewById(R.id.web_view);
				webView.getSettings().setJavaScriptEnabled(true); // ֧��js
				//webView.getSettings().setDefaultTextEncodingName("utf-8");
				//webView.loadData(detail[position], "text/html", "utf-8");
				
				webView.getSettings().setDefaultTextEncodingName("utf-8");
				webView.loadData(detail[position], "text/html; charset=UTF-8", null);

				new AlertDialog.Builder(context)
						.setView(layout)
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										dialog.dismiss();
									}
								}).show();

			}
		});
		tv1_help.setText(textArr[position] == null ? " " : textArr[position]);
		tv1_help.setTextSize(25);
		if (map.get(imageArr[position])==null) {
			Bitmap bitmap = ImageHttpPadClient.getBitmap(imageArr[position]);
			map.put(imageArr[position], bitmap);
		} else {
			imgview.setImageBitmap(map.get(imageArr[position]));
		}
		imgview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkstats[position].equalsIgnoreCase("0")) {
					if (checked.size() < 15) {
						for (int i = 0; i < checked.size(); i++) {
							int check = Integer.parseInt(checked.get(i));
							if (!eId[check].equals(eId[position])) {
								MsgTools.toast(context, "����ͬʱʹ�ò�ͬ��µ��Ż�ȯ��", "l");
								return;
							}
							if (!appId[check].equals(appId[position])) {
								MsgTools.toast(context, "����ͬʱʹ�ò�ͬ��ȯ�������Ż�ȯ��",
										"l");
								return;
							}
						}
						apId = appId[position];
						listChecked = objId[position];
						checked.add(position + "");
						checkstats[position] = "1";
						list.add(id[position]);
						pList.add(position + "");
						Log.i("====position=====", position + "");
						Controller.session.put("checkedList", list);
						imgviewBack.setVisibility(View.VISIBLE);
						
					} else {
						MsgTools.toast(context, "�Ż�ȯһ�����ֻ��ʹ��15�ţ�", "l");
					}

				} else {
					checked.remove(position + "");
					pList.remove(position + "");
					list.remove(id[position]);
					Controller.session.put("checkedList", list);
					checkstats[position] = "0";
					imgviewBack.setVisibility(View.GONE);
				}

			}
		});
		return convertView;
	}
	/**
	 * �ͷ�map
	 */
	public void recycleBitmap(){
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Bitmap> set= (Entry<String, Bitmap>) it.next();
			set.getValue().recycle();
		}
	}
}
