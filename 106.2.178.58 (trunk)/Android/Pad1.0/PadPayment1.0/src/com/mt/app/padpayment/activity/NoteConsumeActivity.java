package com.mt.app.padpayment.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.mt.android.R;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.config.DrawGridViewAdapter;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.adapter.CouponGridAdapter;
import com.mt.app.padpayment.common.MathUtil;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.requestbean.ConsumeReqBean;
import com.mt.app.padpayment.responsebean.AppListRespBean;
import com.mt.app.padpayment.responsebean.CouponsListRespBean;

/**
 * 消费录入界面
 * 
 * @author Administrator
 * 
 */
public class NoteConsumeActivity extends DemoSmartActivity {
	private static Logger log = Logger.getLogger(NoteConsumeActivity.class);
	ArrayList cardAppList = null;
	ArrayList payAppList = null;
	ArrayList<ResponseBean> couponsList = null;
	EditText amountText = null;
	public static int aa = 1;
	GridView gridView2 = null;
	GridView gridView = null;
	private CouponGridAdapter adapter;

	@Override
	public List getDataListById(String id) {
		List<String[]> list = new ArrayList<String[]>();
		String[] appName = null;
		String[] appIcon = null;
		if (id.equals("GRIDVIEW")) {

			Bundle bundle = getIntent().getBundleExtra("bundleInfo");

			if (bundle != null) {
				payAppList = (ArrayList) bundle
						.getSerializable("CARDBINDAPPLIST");
				if (payAppList != null) {
					appName = new String[payAppList.size()];
					appIcon = new String[payAppList.size()];

					for (int z = 0; z < payAppList.size(); z++) {
						AppListRespBean appBean = (AppListRespBean) (payAppList
								.get(z));
						appName[z] = appBean.getMpayAppName() + "("
								+ MathUtil.mul(appBean.getDiscount(), 10 + "")
								+ "折)";

						appIcon[z] = appBean.getPic_path();

					}

					list.add(appName);
					list.add(appIcon);
				} else {
					MsgTools.toast(this, "应用列表获取失败，请检查网络连接", "l");
				}
			}

			/*
			 * list.add(new String[]{"中国银行信用卡", "中国银行信用卡", "中国银行信用卡", "中国银行信用卡",
			 * "中国银行信用卡", "中国银行信用卡", "中国银行信用卡"}); list.add(new
			 * String[]{R.drawable.ka1+"" , R.drawable.ka2+"" ,
			 * R.drawable.ka3+"" , R.drawable.ka4+"" , R.drawable.ka5+"" ,
			 * R.drawable.ka6+"" , R.drawable.ka7+""});
			 */

		}
		/*
		 * if (id.equals("arrayList")) { Bundle bundle =
		 * getIntent().getBundleExtra("bundleInfo"); if (bundle != null) {
		 * cardAppList = (ArrayList) bundle .getSerializable("arrayList"); if
		 * (cardAppList != null && cardAppList.size() > 0) { String[] cName =
		 * new String[cardAppList.size()]; String[] cIcon = new
		 * String[cardAppList.size()];
		 * 
		 * for (int z = 0; z < cardAppList.size(); z++) { AppListRespBean
		 * appBean = (AppListRespBean) (cardAppList .get(z)); cName[z] =
		 * appBean.getAppName();
		 * 
		 * cIcon[z] = appBean.getAppIdent();
		 * 
		 * }
		 * 
		 * list.add(cName); list.add(cIcon); } } }
		 */
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mt.android.sys.common.view.BaseActivity#onCreateContent(android.os
	 * .Bundle)
	 */
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView("NOTE_CONSUME.SCREEN");
		gridView = (GridView) findViewById("GRIDVIEW");

		gridView2 = (GridView) findViewById("arrayList");
		gridView.setVisibility(View.VISIBLE);
		gridView2.setVisibility(View.GONE);
		final Button button = (Button) findViewById("BTN_APPLICATION");
		final Button button2 = (Button) findViewById("BTN_DISCOUNT");
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gridView.setVisibility(View.VISIBLE);
				gridView2.setVisibility(View.GONE);
				button.setBackgroundResource(R.drawable.beacon);
				button2.setBackgroundResource(R.drawable.gray_png2);
			}
		});
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gridView.setVisibility(View.GONE);
				gridView2.setVisibility(View.VISIBLE);

				button2.setBackgroundResource(R.drawable.beacon);
				button.setBackgroundResource(R.drawable.gray_png2);
			}
		});
		gridView2.setAdapter(getAdapter());
		gridView2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
			}
		});
		/*
		 * gridView2.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * position, long arg3) { String id = ((AppListRespBean)
		 * cardAppList.get(position)) .getInstId(); Request request = new
		 * Request(); CouponsListReqBean reqBean = new CouponsListReqBean();
		 * Controller.session.put("couponsId", id); reqBean.setInstId(id);
		 * request.setData(reqBean); go(CommandID.map.get("CouponsList"),
		 * request, false); } });
		 */

		amountText = (EditText) findViewById("MONEY_COUNT");
		amountText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				9) });
		amountText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				/*
				 * if (amountText.getText().toString().equals("")) { aa = 1; }
				 */

				if (amountText.getText().toString().equals("")) {
					aa = 1;
				}
				aa++;
				if (aa % 2 != 1) {

					if (!amountText.getText().toString().equals("")) {
						String str = amountText.getText().toString();
						String ss = "";

						if (str.length() == 1) {
							ss = "0.0" + str;
						} else {

							String[] arr = new String[2];

							arr = str.split("\\.");
							if (arr.length == 2) {
								str = arr[0] + arr[1];
							} else if (arr.length == 1) {
								str = arr[0];
							}

							str = Integer.parseInt(str) + "";
							if (str.length() == 0) {
								ss = "";
							} else if (str.length() == 1) {
								ss = "0.0" + str;
							} else if (str.length() == 2) {
								ss = "0." + str;
							} else {
								ss = str.substring(0, str.length() - 2)
										+ "."
										+ str.substring(str.length() - 2,
												str.length());
							}

						}

						amountText.setText(ss);
						amountText.setSelection(ss.length());

					}

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mt.android.sys.common.view.BaseActivity#onSuccess(com.mt.android.
	 * sys.mvc.common.Response)
	 */
	@Override
	public void onSuccess(Response response) {/*
											 * 
											 * GridView gv = (GridView)
											 * findViewById("arrayList");
											 * gv.setOnItemClickListener(null);
											 * List<String[]> dataList = new
											 * ArrayList<String[]>();
											 * couponsList =
											 * (ArrayList<ResponseBean>)
											 * response.getData(); if
											 * (couponsList != null) { String[]
											 * name = new
											 * String[couponsList.size()];
											 * String[] icon = new
											 * String[couponsList.size()];
											 * String[] id = new
											 * String[couponsList.size()];
											 * 
											 * for (int z = 0; z <
											 * couponsList.size(); z++) {
											 * CouponsListRespBean appBean =
											 * (CouponsListRespBean)
											 * (couponsList .get(z)); name[z] =
											 * appBean.getCoupon_name();
											 * 
											 * icon[z] =
											 * appBean.getCoupon_img_path();
											 * 
											 * id[z] = appBean.getCoupon_id();
											 * 
											 * }
											 * 
											 * dataList.add(name);
											 * dataList.add(icon);
											 * dataList.add(id); } else {
											 * MsgTools.toast(this,
											 * "优惠券列表获取失败，请检查网络连接！", "l"); }
											 * DrawGridAdapter adapter = new
											 * DrawGridAdapter(dataList, this);
											 * gv.setAdapter(adapter);
											 */
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		MsgTools.toast(this, response.getData().toString(), "l");
	}

	@Override
	public Request getRequestByCommandName(String commandName) {

		if (commandName.equals("TO_SURE_CONSUME")) {
			ConsumeReqBean reqBean = new ConsumeReqBean();
			String sum = "";
			EditText dt = (EditText) findViewById("MONEY_COUNT");
			if (dt != null && dt.getText() != null
					&& !dt.getText().toString().equals("")
					&& !dt.getText().toString().equals("0.00")) {

				sum = dt.getText().toString(); // 消费金额

				if (sum == null || sum.trim().equalsIgnoreCase("")
						|| sum.trim().equalsIgnoreCase("0.00")) {
					MsgTools.toast(NoteConsumeActivity.this, "消费金额不能为空", "l");
					return null;
				}
				reqBean.setSum(sum);

				if (Controller.session.get("checked") != null) {
					int pos = Integer.parseInt(Controller.session
							.get("checked").toString());
					AppListRespBean appList = (AppListRespBean) payAppList
							.get(pos);
					reqBean.setAppId(appList.getId()); // 应用id
					if (appList.getDiscount() != null
							&& !appList.getDiscount().equals("")
							&& appList
									.getDiscount()
									.matches(
											"^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
						String vip;
						try {
							vip = MathUtil.sub(sum,
									MathUtil.mul(appList.getDiscount(), sum));
							reqBean.setVipSum(String.format("%.2f",
									Double.parseDouble(vip == null ? "0" : vip)));
						} catch (Exception e) {
							e.printStackTrace();
						}

						reqBean.setAppDiscount(MathUtil.mul(
								appList.getDiscount(), 10 + "")
								+ "折"); // 支付卡折扣率
					}
				}

				List list = (List) Controller.session.get("checkedList");
				if (list != null && list.size() > 0) {
					reqBean.setUseCoupons(true);
					String[] arr = new String[list.size()];
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							arr[i] = list.get(i) + "";
							Log.i("=====couponsChecked=====", arr[i]);
						}
					}

					String sumAmount = "0";

					ArrayList<String> types = new ArrayList<String>();
					ArrayList<String> ids = new ArrayList<String>();

					for (int i = 0; i < arr.length; i++) {
						for (int j = 0; j < couponsList.size(); j++) {
							CouponsListRespBean appBean = (CouponsListRespBean) (couponsList
									.get(j));

							if (appBean.getCoupon_id().trim()
									.equalsIgnoreCase(arr[i])) {// 此优惠券已被选中

								ids.add(arr[i]);

								if (appBean.getAct_type().equalsIgnoreCase("0")) {// 折扣券
									if (appBean
											.getCoupon_discount()
											.matches(
													"^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
										sumAmount = MathUtil.add(MathUtil.sub(
												sum, MathUtil.mul(appBean
														.getCoupon_discount(),
														sum)), sumAmount);
									}

									types.add("折扣券");

								} else {// 代金券
									if (appBean
											.getCoupon_voch_amt()
											.matches(
													"^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
										sumAmount = MathUtil.add(
												appBean.getCoupon_voch_amt(),
												sumAmount);
									}

									types.add("代金券");
								}
							}
						}
					}

					reqBean.setCouponsId(arr); // 优惠券id

					String[] idss = new String[ids.size()];
					for (int i = 0; i < ids.size(); i++) {
						idss[i] = ids.get(i);
					}
					reqBean.setCouponsSeId(idss);

					String[] typess = new String[types.size()];
					for (int i = 0; i < types.size(); i++) {
						typess[i] = types.get(i);
					}
					reqBean.setCouponsSeType(typess);

					reqBean.setCouponsSum(String.format("%.2f", Double
							.parseDouble(sumAmount == null ? "0" : sumAmount)));// 折扣金额

				}
				String realSum = sum;
				if (!reqBean.getCouponsSum().equals("")) {
					realSum = MathUtil.sub(realSum, reqBean.getCouponsSum());
				}
				if (!reqBean.getVipSum().equals("")) {
					realSum = MathUtil.sub(realSum, reqBean.getVipSum());
				}
				if (Double.parseDouble(realSum) < 0) {
					reqBean.setRealSum("0.00");
				} else {
					reqBean.setRealSum(realSum);
				}
			}
			reqBean.setRealSum(String.format("%.2f", Double.parseDouble(reqBean
					.getRealSum().equals("") ? "0" : reqBean.getRealSum())));
			Request request = new Request();
			request.setData(reqBean);
			return request;
		}
		return new Request();

	}

	@Override
	public void setActivityIDById(String id) {

		boolean useApp = false; // 是否使用应用
		boolean useCoupons = false; // 是否使用优惠券
		if (Controller.session.get("checked") != null) {
			useApp = true;
		}
		List list = (List) Controller.session.get("checkedList");
		if (list != null && list.size() > 0) {
			useCoupons = true;
		}

		if (useCoupons && useApp) { // 优惠券+应用
			/*
			 * Controller.session.put("succForward",
			 * ActivityID.map.get("ACTIVITY_ID_BindAppDiscountActivity"));
			 */
			Controller.session.put("succForward",
					ActivityID.map.get("ACTIVITY_ID_APPBINDPWD"));
		} else if (useCoupons) { // 优惠券
			Controller.session.put("succForward",
					ActivityID.map.get("ACTIVITY_ID_PAYSUCCESS"));
		} else if (useApp) { // 应用
			Controller.session.put("succForward",
					ActivityID.map.get("ACTIVITY_ID_APPBINDPWD"));
		}
	}

	private CouponGridAdapter getAdapter() {// 设置优惠券列表的adapter

		List<String[]> list = new ArrayList<String[]>();
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {

			couponsList = (ArrayList) bundle.getSerializable("COUPONSLIST");
			if (couponsList != null) {
				String[] cName = new String[couponsList.size()];
				String[] cIcon = new String[couponsList.size()];
				String[] appId = new String[couponsList.size()];
				String[] eId = new String[couponsList.size()];
				String[] id = new String[couponsList.size()];
				String[] objId = new String[couponsList.size()];
				String[] detail = new String[couponsList.size()];
				for (int z = 0; z < couponsList.size(); z++) {
					CouponsListRespBean appBean = (CouponsListRespBean) (couponsList
							.get(z));
					cName[z] = appBean.getCoupon_name();

					cIcon[z] = appBean.getCoupon_img_path();

					id[z] = appBean.getCoupon_id();

					if (appBean.getCoupon_id() != null
							&& appBean.getCoupon_id().length() > 24) { // 从优惠券id中截取活动id
						eId[z] = appBean.getCoupon_id().substring(0, 24);
					}

					appId[z] = appBean.getC_iss_id();

					objId[z] = appBean.getIssuerId();

					if (appBean.getUseRule() != null) {
						detail[z] = "活动开始时间："
								+ appBean.getUseRule().getStartDate()
								+ "</br>活动结束时间："
								+ appBean.getUseRule().getEnd_date()
								+ "</br>一次消费可同时使用的最大张数："
								+ appBean.getUseRule().getMax_pieces()
								+ "</br>消费满"
								+ appBean.getUseRule().getLowest_tran_amt()
								+ "可使用此券</br>使用规则</br>"
								+ appBean.getUseRule().getDetail();
					}

				}

				list.add(cName);
				list.add(cIcon);
				list.add(id);
				list.add(eId);
				list.add(appId);
				list.add(objId);
				list.add(detail);
			} else {
				MsgTools.toast(NoteConsumeActivity.this, "优惠券列表获取失败，请检查网络连接！",
						"l");
			}
		}
		adapter = new CouponGridAdapter(list, this);

		return adapter;
	}

	/*
	 * private CouponsAppGridViewAdapter getAdapter() {// 设置优惠券应用列表的adapter
	 * 
	 * List<String[]> list = new ArrayList<String[]>(); Bundle bundle =
	 * getIntent().getBundleExtra("bundleInfo"); if (bundle != null) {
	 * cardAppList = (ArrayList) bundle.getSerializable("COUPONSLIST"); if
	 * (cardAppList != null) { String[] cName = new String[cardAppList.size()];
	 * String[] cIcon = new String[cardAppList.size()];
	 * 
	 * for (int z = 0; z < cardAppList.size(); z++) { AppListRespBean appBean =
	 * (AppListRespBean) (cardAppList .get(z)); cName[z] =
	 * appBean.getMpayAppName();
	 * 
	 * cIcon[z] = appBean.getPic_path();
	 * 
	 * }
	 * 
	 * list.add(cName); list.add(cIcon); } else {
	 * MsgTools.toast(NoteConsumeActivity.this, "优惠券应用列表获取失败，请检查网络连接！", "l"); }
	 * } CouponsAppGridViewAdapter adapter = new CouponsAppGridViewAdapter(list,
	 * this);
	 * 
	 * return adapter; }
	 */

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {// 释放bitmap
			adapter.recycleBitmap();
			((DrawGridViewAdapter) gridView.getAdapter()).recycleBitmap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
