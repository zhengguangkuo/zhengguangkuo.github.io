package com.mt.app.padpayment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.frame.smart.config.ListViewAdapter;

public class ListViewOneAdapter extends ListViewAdapter {
	private int checkstats = -1;
	public static List<Integer> list = new ArrayList<Integer>();

	public ListViewOneAdapter(List datas, Context context) {
		super(datas, context);

		if (datas == null) {
			datas = new ArrayList();
		}

	}

	// 实现该方法，该方法返回的View将作为列表框
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = infalter.inflate(
					R.layout.sys_frame_smart_listview_layout, null);
		}

		TextView tv1_help = (TextView) convertView.findViewById(R.id.data);
		final ImageView imgview = (ImageView) convertView
				.findViewById(R.id.check_image);
		tv1_help.setText(datas.get(position).toString());
		tv1_help.setTextSize(25);
		tv1_help.setTextColor(Color.BLACK);

		if (checkstats == position) {
			imgview.setImageResource(R.drawable.checkbox_icon_p_h);
		} else {
			imgview.setImageResource(R.drawable.checkbox_icon_u_h);
		}

		imgview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkstats == -1) {
					checkstats = position;
					list.add(position);
					
					notifyDataSetChanged();
				} else if (checkstats == position) {
					list.remove(list.indexOf(position));
				
					checkstats = -1;
					notifyDataSetChanged();
				} else {
					checkstats = position;
					list.clear();
					list.add(position);
					
					notifyDataSetChanged();
				}
			}
		});
		return convertView;
	}
}
