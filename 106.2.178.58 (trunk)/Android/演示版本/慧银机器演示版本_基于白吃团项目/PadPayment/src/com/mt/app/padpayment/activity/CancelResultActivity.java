package com.mt.app.padpayment.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.common.DbInfoImpl;
import com.mt.app.padpayment.common.MathUtil;
import com.mt.app.padpayment.responsebean.ResultRespBean;
import com.mt.app.padpayment.tools.MoneyUtil;
import com.wizarpos.apidemo.printer.PrinterException;
import com.wizarpos.mt.PrinterHelper;
import com.wizarpos.mt.PurchaseBill;

/**
 * 交易结果界面
 * 
 * @author Administrator
 * 
 */
public class CancelResultActivity extends DemoSmartActivity {
	private TextView result;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("CANCELRESULT.SCREEN");

		result = (TextView) findViewById("RESULT_MESSAGE"); // 交易结果

		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {
			ResultRespBean bean = (ResultRespBean) bundle
					.getSerializable("ResultRespBean");
			if (bean != null) {
				String message = bean.getMessage();
				if (message != null && !(message.equals(""))) {
					result.setText(message);

					if (message.contains("兑换成功")) {
						String fluNum = bundle.getString("sysnum");

						if (!(fluNum.equals(""))) {

							try {
								print();
							} catch (Exception ex) {
								ex.printStackTrace();
							}

						}
					} else if (message.contains("撤销成功")) {

						String fluNum = bundle.getString("sysnum");

						if (!(fluNum.equals(""))) {

							try {
								print2(fluNum);
							} catch (Exception ex) {
								ex.printStackTrace();
							}

						}

					}
				}
			}
		}
	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		if (commandIDName.equals("TO_MAIN")) {
			Request request = new Request();
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

	}

	private void print() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				DbHandle db = new DbHandle();

				Map<String, String> map = db
						.selectOneRecord(
								"TBL_FlOW",
								DbInfoImpl.FieldNames[1],
								" RESP_CODE_1 = ? and PROCESS_CODE_1 in (?,?,?) order by SYS_TRACE_AUDIT_NUM desc",
								new String[] { "00", "000000", "200000",
										"020000" }, null, null, null);

				if (map != null && map.size() != 0) {
					PurchaseBill purchaseBill = new PurchaseBill();

					List<Map<String, String>> listmap = db.rawQuery(
							"select * from TBL_MANAGE", null);
					if (listmap != null && listmap.size() > 0) {
						try {
							String merchName = listmap.get(0).get(
									"CARD_ACCEPT_LOCAL");
							if (merchName != null && !(merchName.equals(""))) {
								purchaseBill.setMerchantName(merchName);
							}

							purchaseBill.setMerchantNo(map
									.get("CARD_ACCEPT_IDENTCODE_1")); // 商户编号
																		// CARD_ACCEPT_IDENTCODE
							purchaseBill.setTerminalNo(map
									.get("CARD_ACCEPT_TERM_IDENT_1")); // 终端编号
																		// CARD_ACCEPT_TERM_IDENT
							purchaseBill.setOperator(map.get("USER_ID")); // 操作员号
																			// USER_ID
							purchaseBill.setBaseCardNumber(map.get("TRACK2_1")); // 基卡卡号
																					// TRACK2
							purchaseBill.setPrepayCardName(map
									.get("RESERVED_PRIVATE3_1")); // 预付卡名称
																	// RESERVED_PRIVATE3
							purchaseBill.setTxnType("消费"); // 交易类别 MSG_ID

							purchaseBill.setPrepayCardNo(map.get("CARD_NO_1")); // 预付卡卡号
																				// CARD_NO

							String coupons = (String) map
									.get("RESERVED_PRIVATE3");
							if (coupons != null && !"".equals(coupons)) {
								int num = Integer.parseInt(coupons.substring(0,
										3));
								String couponNo = "";
								for (int i = 0; i < num; i++) {
									couponNo += coupons.substring(3 + i * 30,
											33 + i * 30) + ",";
								}
								couponNo = couponNo.substring(0,
										couponNo.length() - 1);

								purchaseBill.setCouponNo(couponNo); // 优惠券编号
							}

							// purchaseBill.setCouponNo(map.get("COUPONS_IDS"));
							// // 优惠券编号

							purchaseBill.setPrepayCardExpdate(map
									.get("DATE_EXPIRED_1")); // 卡有效期
																// DATE_EXPIRED
							purchaseBill.setVoucherNo(map
									.get("SYS_TRACE_AUDIT_NUM_1")); // 凭证号（流水号）
																	// SYS_TRACE_AUDIT_NUM
							purchaseBill.setBatchNo(map
									.get("RESERVED_PRIVATE2")); // 批次号
																// RESERVED_PRIVATE2

							String date = map.get("LOCAL_TRANS_DATE_1");
							String time = map.get("LOCAL_TRANS_TIME_1");
							// String time1 = time.substring(0, 2);
							// String time2 = time.substring(2, 4);

							try {
								date = "2014/" + date.substring(0, 2) + "/"
										+ date.substring(2, 4);
								time = time.substring(0, 2) + ":"
										+ time.substring(2, 4) + ":"
										+ time.substring(4, 6);
							} catch (Exception e) {
								e.printStackTrace();
							}
							purchaseBill.setDataTime(date + " " + time); // 日期时间
																			// LOCAL_TRANS_DATE

							purchaseBill.setAuthNo(map
									.get("AUTHOR_IDENT_RESP_1")); // 授权码
																	// AUTHOR_IDENT_RESP
							purchaseBill.setRefNo(map.get("RET_REFER_NUM_1")); // 参考号
																				// RET_REFER_NUM
							purchaseBill.setCouponType(map.get("COUPONS_TYPES")); // 优惠券类别
																					// COUPONS_TYPES

							String mon = map.get("DISCOUNT_AMOUNT_1");
							String realmon = mon.substring(12, 24);
							String money = mon.substring(24, 36);
							// String realmon = mon.substring(12, 24);
							purchaseBill.setCouponDeduce("RMB  "
									+ MoneyUtil.getMoney(realmon)); // 抵扣金额
																	// DISCOUNT_AMOUNT

							purchaseBill.setPrepayCardDis(map
									.get("APP_DISCOUNT")); // 支付卡折扣率
															// APP_DISCOUNT
							purchaseBill.setDisAmount("RMB  "
									+ map.get("VIP_AMOUNT")); // 折扣金额 VIP_AMOUNT
							// purchaseBill.setActuAmount("RMB  "
							// + MoneyUtil.getMoney(map.get("TRANS_AMOUNT_1")));
							// // 支付金额
							//
							// TRANS_AMOUNT

							// String money = mon.substring(24, 36);
							Log.i("money1", MoneyUtil.getMoney(money));
							Log.i("money2", MoneyUtil.getMoney(realmon));
							purchaseBill.setActuAmount("RMB  "
									+ MoneyUtil.getMoney(MathUtil.sub(
											MoneyUtil.getMoney(money),
											MoneyUtil.getMoney(realmon)))); // 支付金额
							// TRANS_AMOUNT
							purchaseBill.setReference(" "); // 备注
							/*-----------demo data-----------*/
							PrinterHelper.getInstance().printerPurchaseBill(
									purchaseBill);
						} catch (PrinterException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

	}

	private void print2(final String num) {// 撤销

		new Thread(new Runnable() {

			@Override
			public void run() {
				DbHandle db = new DbHandle();
				Map<String, String> map = db.selectOneRecord("TBL_FlOW",
						DbInfoImpl.FieldNames[1], "SYS_TRACE_AUDIT_NUM=?",
						new String[] { num }, null, null, null);

				Map<String, String> map2 = db
						.selectOneRecord(
								"TBL_FlOW",
								DbInfoImpl.FieldNames[1],
								" RESP_CODE_1 = ? and PROCESS_CODE_1 in (?,?,?) order by SYS_TRACE_AUDIT_NUM desc",
								new String[] { "00", "000000", "200000",
										"021000" }, null, null, null);

				if (map != null && map.size() != 0) {
					PurchaseBill purchaseBill = new PurchaseBill();

					List<Map<String, String>> listmap = db.rawQuery(
							"select * from TBL_MANAGE", null);
					if (listmap != null && listmap.size() > 0) {
						try {
							String merchName = listmap.get(0).get(
									"CARD_ACCEPT_LOCAL");
							if (merchName != null && !(merchName.equals(""))) {
								purchaseBill.setMerchantName(merchName);
							}

							purchaseBill.setMerchantNo(map
									.get("CARD_ACCEPT_IDENTCODE_1")); // 商户编号
																		// CARD_ACCEPT_IDENTCODE
							purchaseBill.setTerminalNo(map
									.get("CARD_ACCEPT_TERM_IDENT_1")); // 终端编号
																		// CARD_ACCEPT_TERM_IDENT
							purchaseBill.setOperator(map2.get("USER_ID")); // 操作员号
																			// USER_ID
							purchaseBill.setBaseCardNumber(map.get("TRACK2_1")); // 基卡卡号
																					// TRACK2
							purchaseBill.setPrepayCardName(map
									.get("RESERVED_PRIVATE3_1")); // 预付卡名称
																	// RESERVED_PRIVATE3
							purchaseBill.setTxnType("撤销"); // 交易类别 MSG_ID

							purchaseBill.setPrepayCardNo(map.get("CARD_NO_1")); // 预付卡卡号
																				// CARD_NO

							String coupons = (String) map
									.get("RESERVED_PRIVATE3");
							if (coupons != null && !"".equals(coupons)) {
								int num = Integer.parseInt(coupons.substring(0,
										3));
								String couponNo = "";
								for (int i = 0; i < num; i++) {
									couponNo += coupons.substring(3 + i * 30,
											33 + i * 30) + ",";
								}
								couponNo = couponNo.substring(0,
										couponNo.length() - 1);

								purchaseBill.setCouponNo(couponNo); // 优惠券编号
							}

							// purchaseBill.setCouponNo(map.get("COUPONS_IDS"));
							// //
							// 优惠券编号

							purchaseBill.setPrepayCardExpdate(map
									.get("DATE_EXPIRED_1")); // 卡有效期
																// DATE_EXPIRED
							purchaseBill.setVoucherNo(map2
									.get("SYS_TRACE_AUDIT_NUM_1")); // 凭证号（流水号）
																	// SYS_TRACE_AUDIT_NUM
							purchaseBill.setBatchNo(map2
									.get("RESERVED_PRIVATE2")); // 批次号
																// RESERVED_PRIVATE2

							String date = map2.get("LOCAL_TRANS_DATE_1");
							String time = map2.get("LOCAL_TRANS_TIME_1");
							// String time1 = time.substring(0, 2);
							// String time2 = time.substring(2, 4);

							try {
								date = "2014/" + date.substring(0, 2) + "/"
										+ date.substring(2, 4);
								time = time.substring(0, 2) + ":"
										+ time.substring(2, 4) + ":"
										+ time.substring(4, 6);
							} catch (Exception e) {
								e.printStackTrace();
							}
							purchaseBill.setDataTime(date + " " + time); // 日期时间
																			// LOCAL_TRANS_DATE

							purchaseBill.setAuthNo(map
									.get("AUTHOR_IDENT_RESP_1")); // 授权码
																	// AUTHOR_IDENT_RESP
							purchaseBill.setRefNo(map2.get("RET_REFER_NUM_1")); // 参考号
																				// RET_REFER_NUM
							purchaseBill.setCouponType(map.get("COUPONS_TYPES")); // 优惠券类别
																					// COUPONS_TYPES

							String mon = map.get("DISCOUNT_AMOUNT_1");
							String realmon = mon.substring(12, 24);
							String money = mon.substring(24, 36);
							// String realmon = mon.substring(12, 24);
							purchaseBill.setCouponDeduce("RMB  "
									+ MoneyUtil.getMoney(realmon)); // 抵扣金额
																	// DISCOUNT_AMOUNT

							purchaseBill.setPrepayCardDis(map
									.get("APP_DISCOUNT")); // 支付卡折扣率
															// APP_DISCOUNT
							purchaseBill.setDisAmount("RMB  "
									+ map.get("VIP_AMOUNT")); // 折扣金额 VIP_AMOUNT
							// purchaseBill.setActuAmount("RMB  "
							// + MoneyUtil.getMoney(map.get("TRANS_AMOUNT_1")));
							// //
							// 支付金额
							//
							// TRANS_AMOUNT

							// String money = mon.substring(24, 36);
							Log.i("money1", MoneyUtil.getMoney(money));
							Log.i("money2", MoneyUtil.getMoney(realmon));
							purchaseBill.setActuAmount("RMB  "
									+ MoneyUtil.getMoney(MathUtil.sub(
											MoneyUtil.getMoney(money),
											MoneyUtil.getMoney(realmon)))); // 支付金额
							// TRANS_AMOUNT
							purchaseBill.setReference(" "); // 备注
							/*-----------demo data-----------*/
							PrinterHelper.getInstance().printerPurchaseBill(
									purchaseBill);
						} catch (PrinterException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

	}
}
