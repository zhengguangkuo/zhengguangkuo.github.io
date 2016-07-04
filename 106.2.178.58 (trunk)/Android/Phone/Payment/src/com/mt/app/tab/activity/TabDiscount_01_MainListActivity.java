package com.mt.app.tab.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.frame.DemoTabActivity;
import com.mt.android.frame.entity.TabDataBean;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.activity.Discount_01_bus_MainListActivity;
import com.mt.app.payment.activity.Discount_01_dis_MainListActivity;
import com.mt.app.payment.activity.SearchNearByActivity;
import com.mt.app.payment.view.ExpandTabView;
import com.mt.app.payment.view.ViewLeft;
import com.mt.app.payment.view.ViewMiddle;
import com.mt.app.payment.view.ViewMiddle02;
import com.mt.app.payment.view.ViewRight;

public class TabDiscount_01_MainListActivity extends DemoTabActivity {
	public static TabHost mTabHost;
	private List<TabDataBean> datas;
	private Button btn_elePay_back;
	public static List<TextView> tvText = new ArrayList<TextView>();
	private ImageButton discount_img;
	private LinearLayout linearLayout;
	private ImageView tvSearchMap;
	private String distance="";
	private ImageButton imageSousuo;
	public static LinearLayout sousuoLinear;
	private EditText editSousuo;
	private TextView textSousuo;

	public static String jiemianType;

	public ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;

	public ExpandTabView expandTabView2;
	private ArrayList<View> mViewArray2 = new ArrayList<View>();
	private ViewLeft viewLeft2;
	private ViewMiddle02 viewMiddle2;
	private ViewRight viewRight2;
	
	private DialogInterface dialog ;

	public static Context context = null;
	@Override
	public List<TabDataBean> obtainDatalist() {
		List<TabDataBean> datalist = new ArrayList<TabDataBean>();

		// TabDataBean bean = new TabDataBean();
		// bean.setTextMess("全部");
		// bean.setClasses(Discount_01_MainListActivity.class);
		// datalist.add(bean);

		TabDataBean bean = new TabDataBean();
		bean.setTextMess("优惠劵");
		bean.setClasses(Discount_01_dis_MainListActivity.class);
		datalist.add(bean);

		bean = new TabDataBean();
		bean.setTextMess("折扣商家");
		bean.setClasses(Discount_01_bus_MainListActivity.class);
		datalist.add(bean);

		return datalist;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.elecard_payment_child);

		tvText.clear();
		
		initView();
		initVaule();
		initListener();

		linearLayout = (LinearLayout) findViewById(R.id.listlayout_tanchu);
		if (linearLayout.getVisibility() != View.GONE) {
			linearLayout.setVisibility(View.GONE);
		}
		imageSousuo = (ImageButton) findViewById(R.id.discount_img_sousuo_child);
		imageSousuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sousuoLinear.getVisibility() == View.GONE) {
					sousuoLinear.setVisibility(View.VISIBLE);
					linearLayout.setVisibility(View.GONE);
					expandTabView.onPressBack();
					expandTabView2.onPressBack();
					editSousuo.setHint("请输入您要搜索的商家");
				} else if (sousuoLinear.getVisibility() == View.VISIBLE) {
					sousuoLinear.setVisibility(View.GONE);
					editSousuo.setText("");
				}
			}
		});
		sousuoLinear = (LinearLayout) findViewById(R.id.sousuo_top_linear);
		editSousuo = (EditText) findViewById(R.id.sousuo_top_edit);
		editSousuo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				editSousuo.setHint("");
			}
		});
		textSousuo = (TextView) findViewById(R.id.sousuo_top_text);
		textSousuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(TabDiscount_01_MainListActivity.this
						.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				if (editSousuo.getText() != null
						&& !(editSousuo.getText().toString().equals(""))) {
					String condition = editSousuo.getText().toString();
					try {
						if (TabDiscount_01_MainListActivity.this.jiemianType != null
								&& TabDiscount_01_MainListActivity.this.jiemianType
										.equals("youhuiquan")) { // 优惠券界面
							/*
							 * ((Discount_01_dis_MainListActivity)
							 * Controller.currentActivity)
							 * .sousuoRefresh(condition);
							 */
							Controller.session.put("condition", condition);
							if (Controller.session
									.get("Discount_01_dis_MainListActivity") != null) {
								((Discount_01_dis_MainListActivity)Controller.session
										.get("Discount_01_dis_MainListActivity"))
										.initParams();
								((Discount_01_dis_MainListActivity) Controller.session
										.get("Discount_01_dis_MainListActivity"))
										.setSearchDatas();
							}
						} else if (TabDiscount_01_MainListActivity.this.jiemianType != null
								&& TabDiscount_01_MainListActivity.this.jiemianType
										.equals("zhekoushangjia")) { // 折扣商家界面
							// new
							// Discount_01_bus_MainListActivity().sousuoRefresh(condition);
							Controller.session.put("shopCondition", condition);
							if (Controller.session
									.get("Discount_01_bus_MainListActivity") != null) {
								((Discount_01_bus_MainListActivity) Controller.session
										.get("Discount_01_bus_MainListActivity"))
										.initParams();
								((Discount_01_bus_MainListActivity) Controller.session
										.get("Discount_01_bus_MainListActivity"))
										.setSearchDatas();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(TabDiscount_01_MainListActivity.this,
							"搜索条件不能为空！", Toast.LENGTH_LONG).show();
				}
			}
		});
		tvSearchMap = (ImageView) findViewById(R.id.tv_search_map);
		tvSearchMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sousuoLinear.setVisibility(View.GONE);
				linearLayout.setVisibility(View.GONE);
				expandTabView.onPressBack();
				expandTabView2.onPressBack();
				// TODO Auto-generated method stub
				final View view = LayoutInflater.from(
						TabDiscount_01_MainListActivity.this).inflate(
						R.layout.map_distance_dialog, null);
				RadioGroup group = (RadioGroup) view
						.findViewById(R.id.radio_map_distance_select);
				group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						Controller.session.remove("condition");
						Controller.session.remove("shopCondition");
						// TODO Auto-generated method stub
						RadioButton bn = (RadioButton) view
								.findViewById(checkedId);
						String text = bn.getText().toString();
						distance = text.substring(0, text.length() - 1); // 例如：1000
						Log.i("info", "---------------> 用户选择的搜索附近的半径是："
								+ distance);
					}
				});
				new AlertDialog.Builder(TabDiscount_01_MainListActivity.this)
						.setView(view)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										TabDiscount_01_MainListActivity.this.dialog = dialog ;
										if (distance.equals("")) {
											Toast.makeText(TabDiscount_01_MainListActivity.this, "搜索范围不能为空！", Toast.LENGTH_LONG).show();
										} else {
											if (Controller.session.get("Discount_01_dis_MainListActivity") != null) {
												((Discount_01_dis_MainListActivity)Controller.session
														.get("Discount_01_dis_MainListActivity")).initParams();
											}
											if (Controller.session.get("Discount_01_bus_MainListActivity") != null) {
												((Discount_01_bus_MainListActivity) Controller.session
														.get("Discount_01_bus_MainListActivity")).initParams();
											}
											Controller.session.remove("condition");
											Controller.session.remove("shopCondition");
											Intent intent = new Intent(TabDiscount_01_MainListActivity.this, SearchNearByActivity.class);
											// 将搜索半径传到下个界面上去
											intent.putExtra("radiu", distance);
											startActivity(intent);
											// 关闭对话框
											dialog.dismiss();
										}
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										TabDiscount_01_MainListActivity.this.dialog = dialog ;
										// 关闭对话框
										dialog.dismiss();
									}
								}).show();
			}
		});

		discount_img = (ImageButton) findViewById(R.id.discount_img_child);
		discount_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tab();
			}
		});
		btn_elePay_back = (Button) findViewById(R.id.btn_elePay_back_child);
		btn_elePay_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// tvText = null;
				tvText.clear();
				if (linearLayout.getVisibility() != View.GONE) {
					linearLayout.setVisibility(View.GONE);
				}
				finish();
			}
		});
		mTabHost = null;
		mTabHost = getTabHost();

		/**
		 * 根据初始化数据，动态设置菜单界面的显示
		 */
		datas = obtainDatalist();
		if (datas != null) {
			setDataResource(datas);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(dialog != null){
			dialog.dismiss();
		}
	}

	private void tab() {
		if (linearLayout.getVisibility() == View.GONE) {
			linearLayout.setVisibility(View.VISIBLE);
			sousuoLinear.setVisibility(View.GONE);
			expandTabView.onPressBack();
			expandTabView2.onPressBack();
		} else {
			linearLayout.setVisibility(View.GONE);
			if (ExpandTabView.popLists != null && ExpandTabView.popLists.size() != 0) {
				try {
					for (PopupWindow po : ExpandTabView.popLists) {
						po.dismiss();
					}
					expandTabView.selectedButton.setChecked(false);
				} catch (Exception e) {
				}
				try {
					expandTabView2.selectedButton.setChecked(false);
				} catch (Exception e) {
				}

			}
		}
	}

	@Override
	protected void setDataResource(List<TabDataBean> datas) {
		if (datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {

				if (datas.get(i) == null) {
					continue;
				}
				setTabIndicator_NoIcon(datas.get(i).getTextMess(), i + 1,
						new Intent(TabDiscount_01_MainListActivity.this, datas
								.get(i).getClasses()));
			}
		}
	}

	@Override
	protected void setTabIndicator_NoIcon(String text, int itemId, Intent intent) {
		try {

			View view = LayoutInflater.from(this.mTabHost.getContext())
					.inflate(R.layout.elecard_toptab_child, null);
			TextView t = (TextView) view.findViewById(R.id.tab_label_child);
			t.setText(text);
			if (itemId != 1) {
				t.setTextColor(Color.GRAY);
				t.setShadowLayer(0, 0, 0, 0);
			}
			tvText.add(t);

			String strId = String.valueOf(itemId);

			TabHost.TabSpec tabHostSpec = this.mTabHost.newTabSpec(strId)
					.setIndicator(view).setContent(intent);
			mTabHost.addTab(tabHostSpec);

		} catch (Exception e) {
			//e.printStackTrace();
			String error = e.getMessage();
		}
	}

	@Override
	public int[] obtainIds() {
		// TODO Auto-generated method stub
		return null;
	}

	private void initView() {

		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view1);
		viewLeft = new ViewLeft(this);
		viewMiddle = new ViewMiddle(this);
		viewRight = new ViewRight(this);

		expandTabView2 = (ExpandTabView) findViewById(R.id.expandtab_view2);
		viewLeft2 = new ViewLeft(this);
		viewMiddle2 = new ViewMiddle02(this);
		viewRight2 = new ViewRight(this);

		// expandTabView3 = (ExpandTabView) findViewById(R.id.expandtab_view3);
		// viewLeft3 = new ViewLeft(this);
		// viewMiddle3 = new ViewMiddle(this);
		// viewRight3 = new ViewRight(this);

	}

	private void initVaule() {

		mViewArray.add(viewMiddle);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("区域");
		expandTabView.setValue(mTextArray, mViewArray);

		mViewArray2.add(viewMiddle2);
		ArrayList<String> mTextArray2 = new ArrayList<String>();
		mTextArray2.add("类别");
		expandTabView2.setValue(mTextArray2, mViewArray2);

		// mViewArray3.add(viewMiddle3);
		// ArrayList<String> mTextArray3 = new ArrayList<String>();
		// mTextArray3.add("");
		// expandTabView3.setValue(mTextArray3, mViewArray3);

	}

	private void initListener() {

		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewLeft, showText, "", "");
			}
		});

		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

			@Override
			public void getValue(String showText, String code) {

				onRefresh(viewMiddle, showText, code, "0");

			}
		});

		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewRight, showText, "", "1");
			}
		});

		// -------------------------------------------------------------
		viewLeft2.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				// onRefresh2(viewLeft2, showText);
			}
		});

		viewMiddle2.setOnSelectListener(new ViewMiddle02.OnSelectListener() {

			@Override
			public void getValue(String showCode, String showText) {

				onRefresh2(viewMiddle2, showText, showCode, "1");

			}
		});

		viewRight2.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				// onRefresh2(viewRight2, showText);
			}
		});

		// --------------------------------------------------------------
		// viewLeft3.setOnSelectListener(new ViewLeft.OnSelectListener() {
		//
		// @Override
		// public void getValue(String distance, String showText) {
		// onRefresh3(viewLeft3, showText);
		// }
		// });
		//
		// viewMiddle3.setOnSelectListener(new ViewMiddle.OnSelectListener() {
		//
		// @Override
		// public void getValue(String showText) {
		//
		// onRefresh3(viewMiddle3,showText);
		//
		// }
		// });
		//
		// viewRight3.setOnSelectListener(new ViewRight.OnSelectListener() {
		//
		// @Override
		// public void getValue(String distance, String showText) {
		// onRefresh3(viewRight3, showText);
		// }
		// });

	}

	private void onRefresh(View view, String showText, String code, String type) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}

		freshViewByCodition(type, code);

	}

	/** zyb 查询条件改变时调用请求发送命令 **/
	public void freshViewByCodition(String type, String code) {
		if (Controller.session
				.get("Discount_01_dis_MainListActivity") != null) {
			((Discount_01_dis_MainListActivity)Controller.session
					.get("Discount_01_dis_MainListActivity"))
					.initParams();
			}
		if (Controller.session
				.get("Discount_01_bus_MainListActivity") != null) {
			((Discount_01_bus_MainListActivity) Controller.session
					.get("Discount_01_bus_MainListActivity"))
					.initParams();
			}
		Controller.session.remove("condition");
		Controller.session.remove("shopCondition");
		Log.i("", Controller.currentActivity.toString());
		Log.i("", type + "," + code);

		if (context instanceof Discount_01_dis_MainListActivity) {
			Discount_01_dis_MainListActivity activity = (Discount_01_dis_MainListActivity) context;
			if (type.equals("0")) {
				Discount_01_dis_MainListActivity.flagAreafresh = "first";
			} else {
				Discount_01_dis_MainListActivity.flagTypefresh = "first";
			}
			activity.refresh(type, code);// type 0 地区信息 1 类型信息 code 条件编号
		} else if (context instanceof Discount_01_bus_MainListActivity) {
			Discount_01_bus_MainListActivity activity = (Discount_01_bus_MainListActivity) context;
			if (type.equals("0")) {
				Discount_01_bus_MainListActivity.flagAreafresh = "first";
			} else {
				Discount_01_bus_MainListActivity.flagTypefresh = "first";
			}
			activity.refresh(type, code);// type 0 地区信息 1 类型信息 code 条件编号
		}
	}

	/** zyb **/
	private void onRefresh2(View view, String showText, String code, String type) {
		expandTabView2.onPressBack();
		int position = getPositon2(view);
		if (position >= 0
				&& !expandTabView2.getTitle(position).equals(showText)) {
			expandTabView2.setTitle(showText, position);
		}
		/*
		 * Toast.makeText(TabDiscount_01_MainListActivity.this, showText,
		 * Toast.LENGTH_SHORT).show();
		 */

		freshViewByCodition(type, code);
	}

	// private void onRefresh3(View view, String showText) {
	// expandTabView3.onPressBack();
	// int position = getPositon3(view);
	// if (position >= 0 && !expandTabView3.getTitle(position).equals(showText))
	// {
	// expandTabView3.setTitle(showText, position);
	// }
	// Toast.makeText(Discount_01_MainListActivity.this, showText,
	// Toast.LENGTH_SHORT).show();
	//
	// }

	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}

		return -1;
	}

	private int getPositon2(View tView) {

		for (int i = 0; i < mViewArray2.size(); i++) {
			if (mViewArray2.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}

	// private int getPositon3(View tView) {
	//
	// for (int i = 0; i < mViewArray3.size(); i++) {
	// if (mViewArray3.get(i) == tView) {
	// return i;
	// }
	// }
	// return -1;
	// }
	// -------可弹出下拉框与布局相关代码------end--------------------

	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}
		if (!expandTabView2.onPressBack()) {
			finish();
		}
		// if (!expandTabView3.onPressBack()) {
		// finish();
		// }

	}

}
