package com.mt.app.payment.tools;

import java.util.ArrayList;
import java.util.HashMap;

import com.mt.android.global.Globals;
import com.mt.android.sys.common.view.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.BaseActivityID;
import com.mt.android.view.common.BaseCommandID;

public class BottomMenu {

	public Context context;
	private View view;

	public static int flag = 0;

	public View getView() {
		return view;
	}

	// public void setView(View view) {
	// this.view = view;
	// }
	public BottomMenu(Context context) {
		this.context = context;
		this.view = LayoutInflater.from(context).inflate(R.layout.botton_menu,
				null);
		init_menu();
	}

	private  int[] images = { R.drawable.bottom_index,
			R.drawable.bottom_fj, R.drawable.bottom_my, R.drawable.bottom_set };
	// 菜单栏中的文字显示
	private  String[] menu_texts = { "首页", "附近", "我的", "设置" };

	private ArrayList<HashMap<String, Object>> menu_data;

	private GridView grid_view;

	// 初始化底部菜单栏
	public void init_menu() {
		try {

			grid_view = (GridView) this.view.findViewById(R.id.grid_view);

			grid_view.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					BaseCommandID CommandID = (BaseCommandID) Globals.map.get("CommandID");
					
						switch (position) {
						case 0:
							if (!Controller.session.get("position").equals(
									Controller.session.get("0"))) { // 当前打开不为0
								if (Controller.session.get("user") != null) {// 已登录
									/*
									 * Intent intent_n = new Intent(context,
									 * LoginIndexActivity.class);
									 * context.startActivity(intent_n);
									 */
									Controller.session
											.put("position", position);
									((BaseActivity) (context))
											.bottomMenus(CommandID.map.get("TO_LOGININDEX"));
								} else {
									/*
									 * Intent intent_n = new Intent(context,
									 * NoLoginIndexActivity.class);
									 * context.startActivity(intent_n);
									 */
									((BaseActivity) (context))
											.bottomMenus(CommandID.map.get("TO_NOLOGININDEX"));
								}
							}
							break;
						case 1:
							System.out.println("position is :" + position);
							// intent.setClass(MainActivity.this,
							// RainfallWebViewActivity.class);
							// MainActivity.this.startActivity(intent);
							break;
						case 2:
							if (!Controller.session.get("position").equals(
									Controller.session.get("2"))) { // 当前打开不为0
								if (Controller.session.get("user") != null) {// 已登录
									/*
									 * Intent intent_m = new Intent(context,
									 * MyViewActivity.class);
									 * context.startActivity(intent_m);
									 */
									Controller.session
											.put("position", position);
									((BaseActivity) (context))
											.bottomMenus(CommandID.map.get("TO_MYVIEW"));
								} else {
									/*
									 * Intent intent_m = new
									 * Intent(context,LoginActivity.class);
									 * context.startActivity(intent_m);
									 */
									Toast.makeText(context, "请先登录！", Toast.LENGTH_LONG)
											.show();
									((BaseActivity) (context))
											.bottomMenus(CommandID.map.get("TO_LOGIN"));
								}
							}
							break;
						case 3:
							if (!Controller.session.get("position").equals(
									Controller.session.get("3"))) { // 当前打开不为0
								if (Controller.session.get("user") != null) {// 已登录
									/*
									 * Intent intent_m = new Intent(context,
									 * SettingActivity.class);
									 * context.startActivity(intent_m);
									 */
									Controller.session
											.put("position", position);
									if (flag == 0) { // 登录进来以后第一次点击设置
										((BaseActivity) (context))
												.bottomMenus(CommandID.map.get("CHECK_CODE_SETTING"));

									} else if (flag == 1) { // 登录进来后非第一次点击设置
										((BaseActivity) (context))
												.bottomMenus(CommandID.map.get("TO_SETTING"));
									}

								} else { // 没登录
									/*
									 * Intent intent_m = new
									 * Intent(context,LoginActivity.class);
									 * context.startActivity(intent_m);
									 */
									// Controller.session.put("forward",
									// "setting");
									Toast.makeText(context, "请先登录！", 2000)
											.show();
									((BaseActivity) (context))
											.bottomMenus(CommandID.map.get("TO_LOGIN"));
								}
							}
							break;
						case 4:
							System.out.println("position is :" + position);
							break;
						default:
							System.out.println("position is defule");
							break;
						}
					
				}
			});
			add_menu_data();

			// 向菜单栏中的控件添加适配其
			SimpleAdapter adapter = new SimpleAdapter(context, menu_data,
					R.layout.menu_item, new String[] { "menu_image",
							"menu_text" }, new int[] { R.id.item_iamge,
							R.id.item_text });

			grid_view.setAdapter(adapter);
		} catch (Exception e) {

		}
	}

	// 添加菜单栏中显示的数据
	private void add_menu_data() {
		try {
			menu_data = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < images.length; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("menu_image", images[i]);
				map.put("menu_text", menu_texts[i]);
				menu_data.add(map);
			}

		} catch (Exception e) {

		}
	}

}
