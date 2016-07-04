package com.mt.app.padpayment.activity;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.app.padpayment.common.DbInfoImpl;
import com.mt.app.padpayment.common.MathUtil;
import com.mt.app.padpayment.common.MsgTools;
import com.mt.app.padpayment.tools.MoneyUtil;
import com.wizarpos.apidemo.printer.PrinterException;
import com.wizarpos.mt.PrinterHelper;
import com.wizarpos.mt.PurchaseBill;

/**
 * 重新打印凭条界面
 * 
 * @author
 * 
 */
public class RePrintListTipActivity extends DemoSmartActivity {

	private EditText editRe;
	private Button btnSure, btnBack;
	long time = 0;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {

		setContentView("REPRINT_LISTTIP.SCREEN");
		editRe = (EditText) findViewById("rePrint_editText");
		btnSure = (Button) findViewById("Sure_rePrint_listTip");
		btnBack = (Button) findViewById("Back_rePrint_Tip");

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RePrintListTipActivity.this.finish();
			}
		});

		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// if (time == 0) {
				// time =System.currentTimeMillis();
				// } else {
				// long t = System.currentTimeMillis() - time;
				// if (t < 15000) {
				// Log.i("time",t +"");
				// return ;
				// }
				// }
				// time = System.currentTimeMillis();
				// TODO Auto-generated method stub
				String sysNum = editRe.getText().toString();
				if (sysNum.equals("")) {
					MsgTools.toast(RePrintListTipActivity.this, "交易凭证号不能为空",
							"1");
				} else {

					DbHandle db = new DbHandle();

					Map<String, String> map = db.selectOneRecord("TBL_FlOW",
							DbInfoImpl.FieldNames[1],
							"SYS_TRACE_AUDIT_NUM = ? ",
							new String[] { sysNum }, null, null, null);

					if (map != null && map.size() != 0) {
						if (map.get("PROCESS_CODE_1").equals("021000")) { // 撤销

							String num1 = map.get("ORIGINAL_MESSAGE")
									.substring(6, 12);

							Map<String, String> map1 = db.selectOneRecord(
									"TBL_FlOW", DbInfoImpl.FieldNames[1],
									"SYS_TRACE_AUDIT_NUM=?",
									new String[] { num1 }, null, null, null);

							PurchaseBill purchaseBill = new PurchaseBill();

							List<Map<String, String>> listmap = db.rawQuery(
									"select * from TBL_MANAGE", null);
							if (listmap != null && listmap.size() > 0) {
								try {
									String merchName = listmap.get(0).get(
											"CARD_ACCEPT_LOCAL");
									if (merchName != null
											&& !(merchName.equals(""))) {
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
									purchaseBill.setBaseCardNumber(map
											.get("TRACK2_1")); // 基卡卡号
																// TRACK2
									purchaseBill.setPrepayCardName(map1
											.get("RESERVED_PRIVATE3_1")); // 预付卡名称
																			// RESERVED_PRIVATE3
									// if (map.get("MSG_ID_1").equals("0210")) {
									purchaseBill.setTxnType("撤销"); // 交易类别
																	// MSG_ID
									// }
									purchaseBill.setPrepayCardNo(map1
											.get("CARD_NO_1")); // 预付卡卡号
																// CARD_NO
									String coupons = (String) map1
											.get("RESERVED_PRIVATE3");
									if (coupons != null && !"".equals(coupons)) {
										int num = Integer.parseInt(coupons
												.substring(0, 3));
										String couponNo = "";
										for (int i = 0; i < num; i++) {
											couponNo += coupons.substring(
													3 + i * 30, 33 + i * 30)
													+ ",";
										}
										couponNo = couponNo.substring(0,
												couponNo.length() - 1);

										purchaseBill.setCouponNo(couponNo); // 优惠券编号
									}
									// purchaseBill.setCouponNo(map.get("COUPONS_IDS"));
									// // 优惠券编号
									// COUPONS_IDS
									purchaseBill.setPrepayCardExpdate(map1
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
										date = "2014/" + date.substring(0, 2)
												+ "/" + date.substring(2, 4);
										time = time.substring(0, 2) + ":"
												+ time.substring(2, 4) + ":"
												+ time.substring(4, 6);
									} catch (Exception e) {
										e.printStackTrace();
									}
									purchaseBill.setDataTime(date + " " + time);
									purchaseBill.setAuthNo(map
											.get("AUTHOR_IDENT_RESP_1")); // 授权码
																			// AUTHOR_IDENT_RESP
									purchaseBill.setRefNo(map
											.get("RET_REFER_NUM_1")); // 参考号
																		// RET_REFER_NUM
									purchaseBill.setCouponType(map1
											.get("COUPONS_TYPES")); // 优惠券类别
																	// COUPONS_TYPES

									String mon = map1.get("DISCOUNT_AMOUNT_1");
									String realmon = mon.substring(12, 24);
									String money = mon.substring(24, 36);
									purchaseBill.setCouponDeduce("RMB  "
											+ MoneyUtil.getMoney(realmon)); // 抵扣金额
																			// DISCOUNT_AMOUNT

									purchaseBill.setPrepayCardDis(map1
											.get("APP_DISCOUNT")); // 支付卡折扣率
																	// APP_DISCOUNT
									purchaseBill.setDisAmount("RMB  "
											+ map1.get("VIP_AMOUNT")); // 折扣金额
																		// VIP_AMOUNT

									purchaseBill.setActuAmount("RMB  "
											+ MoneyUtil.getMoney(MathUtil.sub(
													MoneyUtil.getMoney(money),
													MoneyUtil.getMoney(realmon)))); // 支付金额
									// TRANS_AMOUNT

									// purchaseBill.setActuAmount("RMB  "
									// +
									// MoneyUtil.getMoney(map.get("TRANS_AMOUNT_1")));
									// // 支付金额
									// // TRANS_AMOUNT
									purchaseBill.setActuAmount("RMB  "
											+ MoneyUtil.getMoney(MathUtil.sub(
													MoneyUtil.getMoney(money),
													MoneyUtil.getMoney(realmon)))); // 支付金额
									// TRANS_AMOUNT
									purchaseBill
											.setReference("重打印凭证/DUPLICATD"); // 备注
									/*-----------demo data-----------*/
									PrinterHelper.getInstance()
											.printerPurchaseBill(purchaseBill);
								} catch (PrinterException e) {
									e.printStackTrace();
								}
								;
							} else {
								MsgTools.toast(RePrintListTipActivity.this,
										"该交易的记录不存在，请重新输入交易凭证号", "1");
								editRe.setText("");
							}
						} else {
							PurchaseBill purchaseBill = new PurchaseBill();

							List<Map<String, String>> listmap = db.rawQuery(
									"select * from TBL_MANAGE", null);
							if (listmap != null && listmap.size() > 0) {
								try {
									String merchName = listmap.get(0).get(
											"CARD_ACCEPT_LOCAL");
									if (merchName != null
											&& !(merchName.equals(""))) {
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
									purchaseBill.setBaseCardNumber(map
											.get("TRACK2_1")); // 基卡卡号
																// TRACK2
									purchaseBill.setPrepayCardName(map
											.get("RESERVED_PRIVATE3_1")); // 预付卡名称
																			// RESERVED_PRIVATE3
									if (map.get("MSG_ID_1").equals("0210")) {
										purchaseBill.setTxnType("消费"); // 交易类别
																		// MSG_ID
									}
									purchaseBill.setPrepayCardNo(map
											.get("CARD_NO_1")); // 预付卡卡号
																// CARD_NO
									String coupons = (String) map
											.get("RESERVED_PRIVATE3");
									if (coupons != null && !"".equals(coupons)) {
										int num = Integer.parseInt(coupons
												.substring(0, 3));
										String couponNo = "";
										for (int i = 0; i < num; i++) {
											couponNo += coupons.substring(
													3 + i * 30, 33 + i * 30)
													+ ",";
										}
										couponNo = couponNo.substring(0,
												couponNo.length() - 1);

										purchaseBill.setCouponNo(couponNo); // 优惠券编号
									}
									// purchaseBill.setCouponNo(map.get("COUPONS_IDS"));
									// // 优惠券编号
									// COUPONS_IDS
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
										date = "2014/" + date.substring(0, 2)
												+ "/" + date.substring(2, 4);
										time = time.substring(0, 2) + ":"
												+ time.substring(2, 4) + ":"
												+ time.substring(4, 6);
									} catch (Exception e) {
										e.printStackTrace();
									}
									purchaseBill.setDataTime(date + " " + time);
									purchaseBill.setAuthNo(map
											.get("AUTHOR_IDENT_RESP_1")); // 授权码
																			// AUTHOR_IDENT_RESP
									purchaseBill.setRefNo(map
											.get("RET_REFER_NUM_1")); // 参考号
																		// RET_REFER_NUM
									purchaseBill.setCouponType(map
											.get("COUPONS_TYPES")); // 优惠券类别
																	// COUPONS_TYPES

									String mon = map.get("DISCOUNT_AMOUNT_1");
									String realmon = mon.substring(12, 24);
									String money = mon.substring(24, 36);
									purchaseBill.setCouponDeduce("RMB  "
											+ MoneyUtil.getMoney(realmon)); // 抵扣金额
																			// DISCOUNT_AMOUNT

									purchaseBill.setPrepayCardDis(map
											.get("APP_DISCOUNT")); // 支付卡折扣率
																	// APP_DISCOUNT
									purchaseBill.setDisAmount("RMB  "
											+ map.get("VIP_AMOUNT")); // 折扣金额
																		// VIP_AMOUNT

									purchaseBill.setActuAmount("RMB  "
											+ MoneyUtil.getMoney(MathUtil.sub(
													MoneyUtil.getMoney(money),
													MoneyUtil.getMoney(realmon)))); // 支付金额
									// TRANS_AMOUNT

									// purchaseBill.setActuAmount("RMB  "
									// +
									// MoneyUtil.getMoney(map.get("TRANS_AMOUNT_1")));
									// // 支付金额
									// // TRANS_AMOUNT
									purchaseBill.setActuAmount("RMB  "
											+ MoneyUtil.getMoney(MathUtil.sub(
													MoneyUtil.getMoney(money),
													MoneyUtil.getMoney(realmon)))); // 支付金额
									// TRANS_AMOUNT
									purchaseBill
											.setReference("重打印凭证/DUPLICATD"); // 备注
									/*-----------demo data-----------*/
									PrinterHelper.getInstance()
											.printerPurchaseBill(purchaseBill);
								} catch (PrinterException e) {
									e.printStackTrace();
								}
							} else {
								MsgTools.toast(RePrintListTipActivity.this,
										"该交易的记录不存在，请重新输入交易凭证号", "1");
								editRe.setText("");
							}
						}

					} else {
						MsgTools.toast(RePrintListTipActivity.this,
								"该交易的记录不存在，请重新输入交易凭证号", "1");
						editRe.setText("");
					}
				}
			}
		});

	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public Request getRequestByCommandName(String commandName) {
		// TODO Auto-generated method stub
		return null;
	}

}
