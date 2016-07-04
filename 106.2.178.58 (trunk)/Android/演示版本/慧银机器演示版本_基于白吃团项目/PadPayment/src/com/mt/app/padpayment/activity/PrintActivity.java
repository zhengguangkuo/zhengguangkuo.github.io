package com.mt.app.padpayment.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
import com.wizarpos.mt.SumBill;

/**
 * 
 * 
 * @Description:��ӡ�˵�����
 * 
 * @author:dw
 * 
 * @time:2014-4-1 ����2:06:23
 */
public class PrintActivity extends DemoSmartActivity {
	long time = 0;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("PRINT.SCREEN");

		// �ش����һ��
		Button button = (Button) findViewById("printEnd");

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// if (time == 0) {
				// time =System.currentTimeMillis();
				// } else {
				// if (System.currentTimeMillis() - time< 15000) {
				//
				// return ;
				// }
				// }
				// time = System.currentTimeMillis();
				DbHandle db = new DbHandle();

				Map<String, String> map = db
						.selectOneRecord(
								"TBL_FlOW",
								DbInfoImpl.FieldNames[1],
								" RESP_CODE_1 = ? and PROCESS_CODE_1 in (?,?,?) order by SYS_TRACE_AUDIT_NUM desc",
								new String[] { "00", "021000", "200000",
										"020000" }, null, null, null);

				if (map != null && map.size() != 0) {

					if (map.get("PROCESS_CODE_1").equals("021000")) { // ����

						String num1 = map.get("ORIGINAL_MESSAGE").substring(6,
								12);

						Map<String, String> map1 = db.selectOneRecord(
								"TBL_FlOW", DbInfoImpl.FieldNames[1],
								"SYS_TRACE_AUDIT_NUM=?", new String[] { num1 },
								null, null, null);

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

								purchaseBill.setMerchantNo(map1
										.get("CARD_ACCEPT_IDENTCODE_1")); // �̻����
																			// CARD_ACCEPT_IDENTCODE
								purchaseBill.setTerminalNo(map1
										.get("CARD_ACCEPT_TERM_IDENT_1")); // �ն˱��
																			// CARD_ACCEPT_TERM_IDENT
								purchaseBill.setOperator(map.get("USER_ID")); // ����Ա��
																				// USER_ID
								purchaseBill.setBaseCardNumber(map1
										.get("TRACK2_1")); // ��������
															// TRACK2
								purchaseBill.setPrepayCardName(map1
										.get("RESERVED_PRIVATE3_1")); // Ԥ��������
																		// RESERVED_PRIVATE3
								purchaseBill.setTxnType("����"); // ������� MSG_ID

								purchaseBill.setPrepayCardNo(map1
										.get("CARD_NO_1")); // Ԥ��������
															// CARD_NO

								String coupons = (String) map1
										.get("RESERVED_PRIVATE3");
								if (coupons != null && !"".equals(coupons)) {
									int num = Integer.parseInt(coupons
											.substring(0, 3));
									String couponNo = "";
									for (int i = 0; i < num; i++) {
										couponNo += coupons.substring(
												3 + i * 30, 33 + i * 30) + ",";
									}
									couponNo = couponNo.substring(0,
											couponNo.length() - 1);

									purchaseBill.setCouponNo(couponNo); // �Ż�ȯ���
								}

								// purchaseBill.setCouponNo(map.get("COUPONS_IDS"));
								// // �Ż�ȯ���

								purchaseBill.setPrepayCardExpdate(map1
										.get("DATE_EXPIRED_1")); // ����Ч��
																	// DATE_EXPIRED
								purchaseBill.setVoucherNo(map
										.get("SYS_TRACE_AUDIT_NUM_1")); // ƾ֤�ţ���ˮ�ţ�
																		// SYS_TRACE_AUDIT_NUM
								purchaseBill.setBatchNo(map
										.get("RESERVED_PRIVATE2")); // ���κ�
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
								purchaseBill.setDataTime(date + " " + time);
								// LOCAL_TRANS_DATE

								purchaseBill.setAuthNo(map
										.get("AUTHOR_IDENT_RESP_1")); // ��Ȩ��
																		// AUTHOR_IDENT_RESP
								purchaseBill.setRefNo(map
										.get("RET_REFER_NUM_1")); // �ο���
																	// RET_REFER_NUM
								purchaseBill.setCouponType(map1
										.get("COUPONS_TYPES")); // �Ż�ȯ���
																// COUPONS_TYPES

								String mon = map.get("DISCOUNT_AMOUNT_1");
								String realmon = mon.substring(12, 24);
								String money = mon.substring(24, 36);
								purchaseBill.setCouponDeduce("RMB  "
										+ MoneyUtil.getMoney(realmon)); // �ֿ۽��
																		// DISCOUNT_AMOUNT

								purchaseBill.setPrepayCardDis(map
										.get("APP_DISCOUNT")); // ֧�����ۿ���
																// APP_DISCOUNT
								purchaseBill.setDisAmount("RMB  "
										+ map.get("VIP_AMOUNT")); // �ۿ۽��
																	// VIP_AMOUNT

								purchaseBill.setActuAmount("RMB  "
										+ MoneyUtil.getMoney(MathUtil.sub(
												MoneyUtil.getMoney(money),
												MoneyUtil.getMoney(realmon)))); // ֧�����
								// TRANS_AMOUNT

								purchaseBill.setReference("�ش�ӡƾ֤/DUPLICATD"); // ��ע
								/*-----------demo data-----------*/
								PrinterHelper.getInstance()
										.printerPurchaseBill(purchaseBill);
							} catch (PrinterException e) {
								e.printStackTrace();
							}
						} else {
							MsgTools.toast(PrintActivity.this, "û�пɴ�ӡ�Ľ���", "1");
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
										.get("CARD_ACCEPT_IDENTCODE_1")); // �̻����
																			// CARD_ACCEPT_IDENTCODE
								purchaseBill.setTerminalNo(map
										.get("CARD_ACCEPT_TERM_IDENT_1")); // �ն˱��
																			// CARD_ACCEPT_TERM_IDENT
								purchaseBill.setOperator(map.get("USER_ID")); // ����Ա��
																				// USER_ID
								purchaseBill.setBaseCardNumber(map
										.get("TRACK2_1")); // ��������
															// TRACK2
								purchaseBill.setPrepayCardName(map
										.get("RESERVED_PRIVATE3_1")); // Ԥ��������
																		// RESERVED_PRIVATE3
								purchaseBill.setTxnType("����"); // ������� MSG_ID

								purchaseBill.setPrepayCardNo(map
										.get("CARD_NO_1")); // Ԥ��������
															// CARD_NO

								String coupons = (String) map
										.get("RESERVED_PRIVATE3");
								if (coupons != null && !"".equals(coupons)) {
									int num = Integer.parseInt(coupons
											.substring(0, 3));
									String couponNo = "";
									for (int i = 0; i < num; i++) {
										couponNo += coupons.substring(
												3 + i * 30, 33 + i * 30) + ",";
									}
									couponNo = couponNo.substring(0,
											couponNo.length() - 1);

									purchaseBill.setCouponNo(couponNo); // �Ż�ȯ���
								}

								// purchaseBill.setCouponNo(map.get("COUPONS_IDS"));
								// // �Ż�ȯ���

								purchaseBill.setPrepayCardExpdate(map
										.get("DATE_EXPIRED_1")); // ����Ч��
																	// DATE_EXPIRED
								purchaseBill.setVoucherNo(map
										.get("SYS_TRACE_AUDIT_NUM_1")); // ƾ֤�ţ���ˮ�ţ�
																		// SYS_TRACE_AUDIT_NUM
								purchaseBill.setBatchNo(map
										.get("RESERVED_PRIVATE2")); // ���κ�
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
								purchaseBill.setDataTime(date + " " + time);
								// LOCAL_TRANS_DATE

								purchaseBill.setAuthNo(map
										.get("AUTHOR_IDENT_RESP_1")); // ��Ȩ��
																		// AUTHOR_IDENT_RESP
								purchaseBill.setRefNo(map
										.get("RET_REFER_NUM_1")); // �ο���
																	// RET_REFER_NUM
								purchaseBill.setCouponType(map
										.get("COUPONS_TYPES")); // �Ż�ȯ���
																// COUPONS_TYPES

								String mon = map.get("DISCOUNT_AMOUNT_1");
								String realmon = mon.substring(12, 24);
								String money = mon.substring(24, 36);
								purchaseBill.setCouponDeduce("RMB  "
										+ MoneyUtil.getMoney(realmon)); // �ֿ۽��
																		// DISCOUNT_AMOUNT

								purchaseBill.setPrepayCardDis(map
										.get("APP_DISCOUNT")); // ֧�����ۿ���
																// APP_DISCOUNT
								purchaseBill.setDisAmount("RMB  "
										+ map.get("VIP_AMOUNT")); // �ۿ۽��
																	// VIP_AMOUNT

								purchaseBill.setActuAmount("RMB  "
										+ MoneyUtil.getMoney(MathUtil.sub(
												MoneyUtil.getMoney(money),
												MoneyUtil.getMoney(realmon)))); // ֧�����
								// TRANS_AMOUNT

								purchaseBill.setReference("�ش�ӡƾ֤/DUPLICATD"); // ��ע
								/*-----------demo data-----------*/
								PrinterHelper.getInstance()
										.printerPurchaseBill(purchaseBill);
							} catch (PrinterException e) {
								e.printStackTrace();
							}
						} else {
							MsgTools.toast(PrintActivity.this, "û�пɴ�ӡ�Ľ���", "1");
						}
					}

				} else {
					MsgTools.toast(PrintActivity.this, "û�пɴ�ӡ�Ľ���", "1");
				}

			}
		});

		// ��ӡ���׻���
		Button buttonSum = (Button) findViewById("printSum");
		buttonSum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (time == 0) {
				// time =System.currentTimeMillis();
				// } else {
				// if (System.currentTimeMillis() - time < 15000) {
				// return ;
				// }
				// }
				// time =System.currentTimeMillis();
				DbHandle db = new DbHandle();

				List<Map<String, String>> list = db
						.rawQuery(
								"select MSG_ID||PROCESS_CODE aaa,sum(TRANS_AMOUNT) sss,count(*) ccc, sum(SUBSTR(RESERVED_PRIVATE3,1,3)) couponsNum, sum(TRANS_AMOUNT) amount, sum(SUBSTR(DISCOUNT_AMOUNT,13,12)) couponsAmount from  TBL_FlOW where RESP_CODE_1 = '00' group by MSG_ID||PROCESS_CODE",
								null);

				List<SumBill> sumlist = new ArrayList<SumBill>();
				if (list != null && list.size() != 0) {
					SumBill bill1 = null;// ����
					SumBill bill2 = null;// ����
					SumBill bill3 = null;// �˻�
					SumBill bill4 = null;// ��ȯ
					SumBill bill5 = null;// ��ȯ
					for (Map map : list) {
						if (map.get("aaa").equals("0200020000")) { // ����
							bill1 = new SumBill();
							bill1.setType("����");
							bill1.setAmount("0.00");
							bill1.setNumber((String) map.get("ccc"));
							bill1.setCouponsAmount((String) map
									.get("couponsAmount"));
							bill1.setCouponsNum((String) map.get("couponsNum"));
						} else if (map.get("aaa").equals("0200021000")) { // ����
							bill2 = new SumBill();
							bill2.setType("����");
							bill2.setAmount("0.00");
							// bill2.setAmount((String) map.get("sss"));
							bill2.setNumber((String) map.get("ccc"));
							String amount = (String) map.get("couponsAmount");
							bill2.setCouponsAmount(amount);

							bill2.setCouponsNum(Integer.parseInt(amount) / 15
									/ 100 + "");
						} else if (map.get("aaa").equals("02202000000")) {// �˻�
							bill3 = new SumBill();
							bill3.setType("�˻�");
							bill3.setAmount((String) map.get("sss"));
							bill3.setNumber((String) map.get("ccc"));
							bill3.setCouponsAmount((String) map
									.get("couponsAmount"));
							bill3.setCouponsNum((String) map.get("couponsNum"));
						} else if (map.get("aaa").equals("0220022000")) { // ��ȯ
							bill4 = new SumBill();
							bill4.setType("��ȯ");
							bill4.setAmount((String) map.get("sss"));
							bill4.setNumber((String) map.get("ccc"));
							bill4.setCouponsAmount((String) map
									.get("couponsAmount"));
							bill4.setCouponsNum((String) map.get("couponsNum"));
						} else if (map.get("aaa").equals("0220021000")) {// ��ȯ
							bill5 = new SumBill();
							bill5.setType("��ȯ");
							bill5.setAmount((String) map.get("sss"));
							bill5.setNumber((String) map.get("ccc"));
							bill5.setCouponsAmount((String) map
									.get("couponsAmount"));
							bill5.setCouponsNum((String) map.get("couponsNum"));
						}
					}

					if (bill1 == null) {
						bill1 = new SumBill();
						bill1.setType("����");
					}
					// bill1.setAmount("1111111");
					// bill1.setCouponsAmount("88888");
					// bill1.setCouponsNum("241");
					// bill1.setNumber("3232");
					if (bill2 == null) {
						bill2 = new SumBill();
						bill2.setType("����");
					}
					if (bill3 == null) {
						bill3 = new SumBill();
						bill3.setType("�˻�");
					}
					if (bill4 == null) {
						bill4 = new SumBill();
						bill4.setType("��ȯ");
					}
					if (bill5 == null) {
						bill5 = new SumBill();
						bill5.setType("��ȯ");
					}
					sumlist.add(bill1);
					sumlist.add(bill2);
					sumlist.add(bill3);
					sumlist.add(bill4);
					sumlist.add(bill5);
				} else {
					for (int i = 0; i < 5; i++) {
						SumBill sumBill = new SumBill();
						switch (i) {
						case 0:
							sumBill.setType("����");
							break;
						case 1:
							sumBill.setType("����");
							break;
						case 2:
							sumBill.setType("�˻�");
							break;
						case 3:
							sumBill.setType("��ȯ");
							break;
						case 4:
							sumBill.setType("��ȯ");
							break;
						default:
							break;
						}
						sumlist.add(sumBill);
					}
				}

				PrinterHelper.printerSumBill(sumlist);

			}
		});
	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		// if (commandIDName != null && commandIDName.equals("printEnd")) {
		// hasWaitView();
		// }
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
}
