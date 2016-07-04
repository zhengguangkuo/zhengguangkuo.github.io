package com.mt.app.padpayment.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.mt.android.db.DbHandle;
import com.mt.app.padpayment.common.DbHelp;
import com.mt.app.padpayment.common.IsoCommHandler;
import com.mt.app.padpayment.message.iso.trans.AccuPointConsumeReversalBean;
import com.mt.app.padpayment.message.iso.trans.ConsumeReversalBean;
import com.mt.app.padpayment.message.iso.trans.CouponsConvertRepealReversalBean;
import com.mt.app.padpayment.message.iso.trans.CouponsConvertReverseBean;
import com.mt.app.padpayment.message.iso.trans.PaymentCancelReverseBean;
import com.mt.app.padpayment.message.iso.trans.PointsCancelReversalBean;
import com.mt.app.padpayment.message.iso.trans.ReceiveCouponsReversalBean;

/**
 * 数据库服务器类
 * 
 * @author lzw
 * 
 */
public class DBService extends Service {

	private static final String TAG = "LocalService";
	private static DbHandle dbhandle = new DbHandle();

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "---ServiceDBonCreate");
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(TAG, "---ServiceDBonStart");

		// 加入线程
		new Thread(new Runnable() {
			public boolean isRunning = true;

			/*
			 * db.execSQL(
			 * "insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('2','REVERSAL_FREQ','30000','冲正轮询时间')"
			 * ); db.execSQL(
			 * "insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('2','REVERSAL_AMOUNT','5','冲正次数')"
			 * );
			 */

			public void run() {
				// TODO Auto-generated method stub
				// super.run();
				DbHandle db = new DbHandle();
				Map<String, String> map1 = db.selectOneRecord("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
						new String[] { "REVERSAL_FREQ" }, null, null, null);
				Map<String, String> map2 = db.selectOneRecord("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
						new String[] { "REVERSAL_AMOUNT" }, null, null, null);

				while (isRunning) {
					try {
						Thread.sleep(Long.parseLong(map1.get("PARA_VALUE")));// 沉睡时间
						// start polling method;
						// 此处编写pad轮询需要的程序
						List<Map<String, String>> list = dbhandle
								.rawQuery(
										"select * from TBL_REVERSAL where FLUSH_RESULT !=? and FLUSH_OCUNT < ? ",
										new String[] { "00",
												map2.get("PARA_VALUE") });
						Log.i(TAG, "正在轮询...检测到--" + list.size()
								+ "--条需要处理的冲正信息");
						for (int i = 0; i < list.size(); i++) {
							// 积分消费冲正
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("100000")) {
								AccuPointConsumeReversalBean apcrb = getAccuPointConsumeReversal(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (apcrb != null) {
									if (apcrb.getRespCode().equals("00")) {
										// 更新数据库FLUSH_RESULT冲正结果字段 为 冲正成功的值
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的积分消费冲正结果："
												+ apcrb.getRespCode());
									} else {
										// 更新数据库在冲正次数字段FLUSH_OCUNT上加1
										int flush_ocunt_int = Integer
												.parseInt(list.get(i).get(
														"FLUSH_OCUNT"));
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_OCUNT" },
												new String[] { (flush_ocunt_int + 1)
														+ "" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] {  list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的积分消费冲正结果："
												+ apcrb.getRespCode());
									}
								} else {
									int flush_ocunt_int = Integer
											.parseInt(list.get(i).get(
													"FLUSH_OCUNT"));
									dbhandle.update(
											"TBL_REVERSAL",
											new String[] { "FLUSH_OCUNT" },
											new String[] { (flush_ocunt_int + 1)
													+ "" },
											"SYS_TRACE_AUDIT_NUM=?",
											new String[] {  list
													.get(i).get("SYS_TRACE_AUDIT_NUM") });
								}
							}
							// 消费冲正
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("000000")) {
								ConsumeReversalBean crb = getConsumeReversal(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (crb != null) {
									if (crb.getRespCode().equals("00")) {
										// 更新数据库FLUSH_RESULT冲正结果字段 为 冲正成功的值
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的消费冲正结果："
												+ crb.getRespCode());
									} else {
										// 更新数据库在冲正次数字段FLUSH_OCUNT上加1

										int flush_ocunt_int = Integer
												.parseInt(list.get(i).get(
														"FLUSH_OCUNT"));
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_OCUNT" },
												new String[] { (flush_ocunt_int + 1)
														+ "" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的消费冲正结果："
												+ crb.getRespCode());
									}
								}else {
									int flush_ocunt_int = Integer
											.parseInt(list.get(i).get(
													"FLUSH_OCUNT"));
									dbhandle.update(
											"TBL_REVERSAL",
											new String[] { "FLUSH_OCUNT" },
											new String[] { (flush_ocunt_int + 1)
													+ "" },
											"SYS_TRACE_AUDIT_NUM=?",
											new String[] {  list
													.get(i).get("SYS_TRACE_AUDIT_NUM") });
								}

							}
						
							// 优惠券领用冲正
							if (list.get(i).get("MSG_ID").equals("0220")
									&& list.get(i).get("PROCESS_CODE")
											.equals("022000")) {
								ReceiveCouponsReversalBean rcrb = getReceiveCouponsReversalBean(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (rcrb != null) {

									if (rcrb.getRespCode().equals("00")) {
										// 更新数据库FLUSH_RESULT冲正结果字段 为 冲正成功的值
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的领用冲正结果："
												+ rcrb.getRespCode());
									} else {
										// 更新数据库在冲正次数字段FLUSH_OCUNT上加1
										int flush_ocunt_int = Integer
												.parseInt(list.get(i).get(
														"FLUSH_OCUNT"));
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_OCUNT" },
												new String[] { (flush_ocunt_int + 1)
														+ "" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的优惠劵领用冲正结果："
												+ rcrb.getRespCode());
									}
								}else {
									int flush_ocunt_int = Integer
											.parseInt(list.get(i).get(
													"FLUSH_OCUNT"));
									dbhandle.update(
											"TBL_REVERSAL",
											new String[] { "FLUSH_OCUNT" },
											new String[] { (flush_ocunt_int + 1)
													+ "" },
											"SYS_TRACE_AUDIT_NUM=?",
											new String[] {  list
													.get(i).get("SYS_TRACE_AUDIT_NUM") });
								}

							}
							// 消费撤销冲正
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("200000")) {
								PaymentCancelReverseBean prb = getPaymentCancelReverse(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (prb != null) {
									if (prb.getRespCode().equals("00")) {
										// 更新数据库FLUSH_RESULT冲正结果字段 为 冲正成功的值
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"PROCESS_CODE=?",
												new String[] { "200000" });
										System.out.println("返回的消费撤销冲正结果："
												+ prb.getRespCode());
									} else {
										// 更新数据库在冲正次数字段FLUSH_OCUNT上加1
										int flush_ocunt_int = Integer
												.parseInt(list.get(i).get(
														"FLUSH_OCUNT"));
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_OCUNT" },
												new String[] { (flush_ocunt_int + 1)
														+ "" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的消费撤销冲正结果："
												+ prb.getRespCode());
									}
								}else {
									int flush_ocunt_int = Integer
											.parseInt(list.get(i).get(
													"FLUSH_OCUNT"));
									dbhandle.update(
											"TBL_REVERSAL",
											new String[] { "FLUSH_OCUNT" },
											new String[] { (flush_ocunt_int + 1)
													+ "" },
											"SYS_TRACE_AUDIT_NUM=?",
											new String[] {  list
													.get(i).get("SYS_TRACE_AUDIT_NUM") });
								}
							}
							// 积分撤销冲正
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("300000")) {
								PointsCancelReversalBean pcrb = getPointsCancelReversal(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (pcrb != null) {
									if (pcrb.getRespCode().equals("00")) {
										// 更新数据库FLUSH_RESULT冲正结果字段 为 冲正成功的值
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"PROCESS_CODE=?",
												new String[] { "300000" });
										System.out.println("返回的积分撤销冲正结果："
												+ pcrb.getRespCode());
									} else {
										// 更新数据库在冲正次数字段FLUSH_OCUNT上加1
										int flush_ocunt_int = Integer
												.parseInt(list.get(i).get(
														"FLUSH_OCUNT"));
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_OCUNT" },
												new String[] { (flush_ocunt_int + 1)
														+ "" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的积分撤销冲正结果："
												+ pcrb.getRespCode());
									}
								}else {
									int flush_ocunt_int = Integer
											.parseInt(list.get(i).get(
													"FLUSH_OCUNT"));
									dbhandle.update(
											"TBL_REVERSAL",
											new String[] { "FLUSH_OCUNT" },
											new String[] { (flush_ocunt_int + 1)
													+ "" },
											"SYS_TRACE_AUDIT_NUM=?",
											new String[] {  list
													.get(i).get("SYS_TRACE_AUDIT_NUM") });
								}
							}
							// 兑换冲正
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("020000")) {
								CouponsConvertReverseBean ccrb = getCouponConvertReverse(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (ccrb != null) {
									if (ccrb.getRespCode().equals("00")) {
										// 更新数据库FLUSH_RESULT冲正结果字段 为 冲正成功的值
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"PROCESS_CODE=?",
												new String[] { "020000" });
										System.out.println("返回的兑换冲正结果："
												+ ccrb.getRespCode());
									} else {
										// 更新数据库在冲正次数字段FLUSH_OCUNT上加1
										int flush_ocunt_int = Integer
												.parseInt(list.get(i).get(
														"FLUSH_OCUNT"));
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_OCUNT" },
												new String[] { (flush_ocunt_int + 1)
														+ "" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的兑换冲正结果："
												+ ccrb.getRespCode());
									}
								}else {
									int flush_ocunt_int = Integer
											.parseInt(list.get(i).get(
													"FLUSH_OCUNT"));
									dbhandle.update(
											"TBL_REVERSAL",
											new String[] { "FLUSH_OCUNT" },
											new String[] { (flush_ocunt_int + 1)
													+ "" },
											"SYS_TRACE_AUDIT_NUM=?",
											new String[] {  list
													.get(i).get("SYS_TRACE_AUDIT_NUM") });
								}
							}
							// 兑换撤销冲正
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("021000")) {
								CouponsConvertRepealReversalBean ccrb = getCouponsConvertRepealReversalBean(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (ccrb != null) {
									if (ccrb.getRespCode().equals("00")) {
										// 更新数据库FLUSH_RESULT冲正结果字段 为 冲正成功的值
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"PROCESS_CODE=?",
												new String[] { "021000" });
										System.out.println("返回的兑换冲正结果："
												+ ccrb.getRespCode());
									} else {
										// 更新数据库在冲正次数字段FLUSH_OCUNT上加1
										int flush_ocunt_int = Integer
												.parseInt(list.get(i).get(
														"FLUSH_OCUNT"));
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_OCUNT" },
												new String[] { (flush_ocunt_int + 1)
														+ "" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("返回的兑换冲正结果："
												+ ccrb.getRespCode());
									}
								}else {
									int flush_ocunt_int = Integer
											.parseInt(list.get(i).get(
													"FLUSH_OCUNT"));
									dbhandle.update(
											"TBL_REVERSAL",
											new String[] { "FLUSH_OCUNT" },
											new String[] { (flush_ocunt_int + 1)
													+ "" },
											"SYS_TRACE_AUDIT_NUM=?",
											new String[] {  list
													.get(i).get("SYS_TRACE_AUDIT_NUM") });
								}
							}

						}

					} catch (InterruptedException e) {
						Log.v("TAG", e.toString());
					}
				}
			}

		}).start();
		// 执行定时清理数据
		// run();
		super.onStart(intent, startId);
	}

	// 指定时间对数据库操作
	public void run() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23); // 控制时
		calendar.set(Calendar.MINUTE, 59); // 控制分
		calendar.set(Calendar.SECOND, 59); // 控制秒
		Date time = calendar.getTime(); // 得出执行任务的时间,此处为今天的23：59：59
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				dbhandle.delete("TBL_FlOW", null, null);
				dbhandle.delete("TBL_TMPFlOW", null, null);
			}
		}, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
	}

	// 积分消费冲正
	public AccuPointConsumeReversalBean getAccuPointConsumeReversal(
			String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		AccuPointConsumeReversalBean accupointconsumereversal = new AccuPointConsumeReversalBean();
		accupointconsumereversal.setMsgId("0400");// 消息id
		accupointconsumereversal.setTrack2(map.get("TRACK2"));// 机卡卡号
		accupointconsumereversal.setProcessCode(map.get("PROCESS_CODE"));// 交易处理码
		accupointconsumereversal.setTransAmount(map.get("TRANS_AMOUNT"));// 交易金额
		accupointconsumereversal.setSysTraceAuditNum(map
				.get("SYS_TRACE_AUDIT_NUM"));// 受卡方系统跟踪号
		accupointconsumereversal.setDateExpired(map.get("DATE_EXPIRED").equals(
				"") ? null : map.get("DATE_EXPIRED"));// 卡有效期
		accupointconsumereversal.setServiceEntryMode(map
				.get("SERVICE_ENTRY_MODE"));// 服务点输入方式码
		accupointconsumereversal.setServiceConditionMode(map
				.get("SERVICE_CONDITION_MODE"));// 服务点条件码
		accupointconsumereversal.setRespCode("98");// 应答码
		accupointconsumereversal.setCardAcceptIdentcode(map
				.get("CARD_ACCEPT_IDENTCODE"));// 受卡机终端标识码***
		accupointconsumereversal.setCardAcceptTermIdent(map
				.get("CARD_ACCEPT_TERM_IDENT"));// 受卡方标识码***
		accupointconsumereversal.setOrganId(map.get("ORGAN_ID"));// 发券机构标识
		accupointconsumereversal.setCurrencyTransCode(map
				.get("CURRENCY_TRANS_CODE"));// 交易货币代码
		accupointconsumereversal.setCouponsAdvertId(map
				.get("COUPONS_ADVERT_ID"));// 发卷对象标识
		accupointconsumereversal.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// 批次号
		accupointconsumereversal.setMessageAuthentCode("11111111");// mac
		IsoCommHandler comm = new IsoCommHandler();
		AccuPointConsumeReversalBean apcrl = (AccuPointConsumeReversalBean) comm
				.sendIsoMsg(accupointconsumereversal); // 打包并发送报文
		return apcrl;
	}

	// 消费冲正
	public ConsumeReversalBean getConsumeReversal(String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		ConsumeReversalBean consumereversal = new ConsumeReversalBean();
		consumereversal.setMsgId("0400");// 消息id
		consumereversal.setCardNo(map.get("CARD_NO_1").equals("")?null:map.get("CARD_NO_1"));// 支付卡号
		consumereversal.setTrack2(map.get("TRACK2"));//基卡卡号
		consumereversal.setProcessCode(map.get("PROCESS_CODE"));// 交易处理码
		consumereversal.setTransAmount(map.get("TRANS_AMOUNT"));// 交易金额
		consumereversal.setDiscountAmount(map.get("DISCOUNT_AMOUNT"));//附加金额
		consumereversal.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// 受卡方系统跟踪号
		consumereversal
				.setDateExpired(map.get("DATE_EXPIRED").equals("") ? null : map
						.get("DATE_EXPIRED"));// 卡有效期
		consumereversal.setServiceEntryMode(map.get("SERVICE_ENTRY_MODE"));// 服务点输入方式码
		consumereversal.setServiceConditionMode(map
				.get("SERVICE_CONDITION_MODE"));// 服务点条件码
		consumereversal.setAuthorIdentResp(map.get("AUTHOR_IDENT_RESP").equals(
				"") ? null : map.get("AUTHOR_IDENT_RESP"));// 授权码
		consumereversal.setRespCode("98");// 应答码
		consumereversal
				.setCardAcceptIdentcode(map.get("CARD_ACCEPT_IDENTCODE"));// 受卡机终端标识码
		consumereversal.setCardAcceptTermIdent(map
				.get("CARD_ACCEPT_TERM_IDENT"));// 受卡方标识码
		consumereversal.setCurrencyTransCode(map.get("CURRENCY_TRANS_CODE"));// 交易货币代码
		consumereversal.setOrganId(map.get("ORGAN_ID").equals("") ? null
				: map.get("ORGAN_ID"));//发券机构标识
		consumereversal.setSwapCode(map.get("SWAP_CODE_1").equals("") ? null
				: map.get("SWAP_CODE_1"));// 兑换码
		consumereversal.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID").equals("") ? null
				: map.get("COUPONS_ADVERT_ID"));//发券对象标识
		consumereversal.setReservedPrivate1(map.get("RESERVED_PRIVATE1"));// 应用标识
		
		consumereversal.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// 批次号
		consumereversal.setMessageAuthentCode(null);// mac
		IsoCommHandler comm = new IsoCommHandler();
		ConsumeReversalBean crlb = (ConsumeReversalBean) comm
				.sendIsoMsg(consumereversal); // 打包并发送报文
		return crlb;
	}

	// 优惠券兑换冲正
	public CouponsConvertReverseBean getCouponConvertReverse(String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		CouponsConvertReverseBean couponconvertreverse = new CouponsConvertReverseBean();
		couponconvertreverse.setMsgId("0400");// 消息id
		couponconvertreverse.setTrack2(map.get("TRACK2"));// 基卡卡号35
		couponconvertreverse.setProcessCode(map.get("PROCESS_CODE"));// 交易处理码3
		couponconvertreverse.setTransAmount(map.get("TRANS_AMOUNT").equals("")?"000000000000":map.get("TRANS_AMOUNT"));// 交易金额4
		couponconvertreverse.setDiscountAmount(map.get("DISCOUNT_AMOUNT"));//附加金额
		couponconvertreverse
				.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// 受卡方系统跟踪号11
		couponconvertreverse.setAuthorIdentResp(map.get("AUTHOR_IDENT_RESP")
				.equals("") ? null : map.get("AUTHOR_IDENT_RESP"));// 授权码38
		couponconvertreverse.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
		couponconvertreverse.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42
		couponconvertreverse.setOrganId(map.get("ORGAN_ID"));// 发卷机构标识
		couponconvertreverse.setSwapCode(map.get("SWAP_CODE_1").equals("")?null:map.get("SWAP_CODE_1"));//兑换码
		couponconvertreverse.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// 发卷对象标识58
		couponconvertreverse.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// 批次号
		couponconvertreverse.setMessageAuthentCode("11111111"); // MAC64
		IsoCommHandler comm = new IsoCommHandler();
		CouponsConvertReverseBean ccrb = (CouponsConvertReverseBean) comm
				.sendIsoMsg(couponconvertreverse); // 打包并发送报文
		return ccrb;
	}
	// 优惠券兑换撤消冲正
	public CouponsConvertRepealReversalBean getCouponsConvertRepealReversalBean(String flownum) {
			Map<String, String> map = dbhandle.rawQueryOneRecord(
					"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
					new String[] { flownum });
			CouponsConvertRepealReversalBean couponsConvertRepealReversalBean = new CouponsConvertRepealReversalBean();
			couponsConvertRepealReversalBean.setMsgId("0400");// 消息id
			couponsConvertRepealReversalBean.setTrack2(map.get("TRACK2"));// 基卡卡号35
			couponsConvertRepealReversalBean.setProcessCode(map.get("PROCESS_CODE"));// 交易处理码3
			couponsConvertRepealReversalBean.setTransAmount(map.get("TRANS_AMOUNT").equals("")?"000000000000":map.get("TRANS_AMOUNT"));// 交易金额4
			couponsConvertRepealReversalBean.setDiscountAmount(map.get("DISCOUNT_AMOUNT"));//附加金额
			couponsConvertRepealReversalBean
					.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// 受卡方系统跟踪号11
			couponsConvertRepealReversalBean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
			couponsConvertRepealReversalBean.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42
			couponsConvertRepealReversalBean.setOrganId(map.get("ORGAN_ID"));// 发卷机构标识
			couponsConvertRepealReversalBean.setSwapCode(map.get("SWAP_CODE_1").equals("")?null:map.get("SWAP_CODE_1"));//兑换码
			couponsConvertRepealReversalBean.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// 发卷对象标识58
			couponsConvertRepealReversalBean.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// 批次号
			couponsConvertRepealReversalBean.setOriginalMessage(map.get("ORIGINAL_MESSAGE"));//原始信息域
			couponsConvertRepealReversalBean.setMessageAuthentCode("11111111"); // MAC64
			IsoCommHandler comm = new IsoCommHandler();
			CouponsConvertRepealReversalBean ccrb = (CouponsConvertRepealReversalBean) comm
					.sendIsoMsg(couponsConvertRepealReversalBean); // 打包并发送报文
			return ccrb;
		}
	
	// 优惠券领用冲正
	public ReceiveCouponsReversalBean getReceiveCouponsReversalBean(String flownum) {
			Map<String, String> map = dbhandle.rawQueryOneRecord(
					"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
					new String[] { flownum });
			ReceiveCouponsReversalBean receiveCouponsReversalBean = new ReceiveCouponsReversalBean();
			receiveCouponsReversalBean.setMsgId("0400");// 消息id
			receiveCouponsReversalBean.setTrack2(map.get("TRACK2"));// 基卡卡号35
			receiveCouponsReversalBean.setProcessCode(map.get("PROCESS_CODE"));// 交易处理码3
			receiveCouponsReversalBean.setTransAmount(map.get("TRANS_AMOUNT").equals("")?"000000000000":map.get("TRANS_AMOUNT"));// 交易金额4
			receiveCouponsReversalBean
					.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// 受卡方系统跟踪号11
			receiveCouponsReversalBean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
			receiveCouponsReversalBean.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42
			receiveCouponsReversalBean.setOrganId(map.get("ORGAN_ID"));// 发卷机构标识
			receiveCouponsReversalBean.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// 发卷对象标识58
			receiveCouponsReversalBean.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// 批次号
			receiveCouponsReversalBean.setMessageAuthentCode("11111111"); // MAC64
			IsoCommHandler comm = new IsoCommHandler();
			ReceiveCouponsReversalBean rcrb = (ReceiveCouponsReversalBean) comm
					.sendIsoMsg(receiveCouponsReversalBean); // 打包并发送报文
			return rcrb;
		}
	
	// 消费撤销冲正
	public PaymentCancelReverseBean getPaymentCancelReverse(String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		PaymentCancelReverseBean paymentcancelresverse = new PaymentCancelReverseBean();
		paymentcancelresverse.setMsgId("0400");// 消息id
		paymentcancelresverse.setCardNo(map.get("CARD_NO_1"));// 支付卡号2
		paymentcancelresverse.setTrack2(map.get("TRACK2")); //基卡卡号
		paymentcancelresverse.setProcessCode(map.get("PROCESS_CODE"));// 交易处理码3
		paymentcancelresverse.setTransAmount(map.get("TRANS_AMOUNT"));// 交易金额4
		paymentcancelresverse.setDiscountAmount(map.get("DISCOUNT_AMOUNT"));//附加金额
		paymentcancelresverse.setSysTraceAuditNum(map
				.get("SYS_TRACE_AUDIT_NUM"));// 受卡方系统跟踪号11
		paymentcancelresverse
				.setDateExpired(map.get("DATE_EXPIRED").equals("") ? null : map
						.get("DATE_EXPIRED")); // 卡有效期14
		paymentcancelresverse
				.setServiceEntryMode(map.get("SERVICE_ENTRY_MODE")); // 服务点输入方式码22
		paymentcancelresverse.setServiceConditionMode(map
				.get("SERVICE_CONDITION_MODE"));// 服务点条件码25
		paymentcancelresverse.setAuthorIdentResp(map.get("AUTHOR_IDENT_RESP")
				.equals("") ? null : map.get("AUTHOR_IDENT_RESP"));// 授权码38
		paymentcancelresverse.setRespCode("98"); // 应答码39
		paymentcancelresverse.setCardAcceptIdentcode(map
				.get("CARD_ACCEPT_IDENTCODE"));// 受卡方标识码42
		paymentcancelresverse.setCardAcceptTermIdent(map
				.get("CARD_ACCEPT_TERM_IDENT"));// 受卡机终端标识码41
		paymentcancelresverse.setCurrencyTransCode(map
				.get("CURRENCY_TRANS_CODE")); // 交易货币代码49
		paymentcancelresverse.setSecurityRelatedControl(map.get(
				"SECURITY_RELATED_CONTROL").equals("") ? null : map
				.get("SECURITY_RELATED_CONTROL"));// 安全控制信息53
		paymentcancelresverse.setOrganId(map
				.get("ORGAN_ID"));//发券机构标识
		paymentcancelresverse
				.setSwapCode(map.get("SWAP_CODE_1").equals("") ? null : map
						.get("SWAP_CODE_1"));// 兑换码
		paymentcancelresverse.setReservedPrivate1(map.get("RESERVED_PRIVATE1")); // 应用标识59
		paymentcancelresverse.setReservedPrivate2("23"+DbHelp.getBatchNum()+"000"); // 批次号60
		paymentcancelresverse.setOriginalMessage(map.get("ORIGINAL_MESSAGE")); // 原始信息域61
		paymentcancelresverse.setMessageAuthentCode(map
				.get("MESSAGE_AUTHENT_CODE"));// MAC64
		IsoCommHandler comm = new IsoCommHandler();
		PaymentCancelReverseBean pclrb = (PaymentCancelReverseBean) comm
				.sendIsoMsg(paymentcancelresverse); // 打包并发送报文
		return pclrb;
	}

	// 积分撤销冲正
	public PointsCancelReversalBean getPointsCancelReversal(String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		PointsCancelReversalBean pointcancelreversal = new PointsCancelReversalBean();
		pointcancelreversal.setMsgId("0400");// 消息id
		pointcancelreversal.setTrack2(map.get("TRACK2"));// 基卡卡号35
		pointcancelreversal.setProcessCode(map.get("PROCESS_CODE"));// 交易处理码3
		pointcancelreversal.setTransAmount(map.get("TRANS_AMOUNT"));// 交易金额4
		pointcancelreversal.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// 受卡方系统跟踪号11
		pointcancelreversal
				.setDateExpired(map.get("DATE_EXPIRED").equals("") ? null : map
						.get("DATE_EXPIRED")); // 卡有效期14
		pointcancelreversal.setServiceEntryMode(map.get("SERVICE_ENTRY_MODE"));// 服务点输入方式码22
		pointcancelreversal.setServiceConditionMode(map
				.get("SERVICE_CONDITION_MODE"));// 服务点条件码25
		pointcancelreversal.setRespCode("98");// 应答码39
		pointcancelreversal.setCardAcceptTermIdent(map
				.get("CARD_ACCEPT_TERM_IDENT"));// 受卡机终端标识码41
		pointcancelreversal.setCardAcceptIdentcode(map
				.get("CARD_ACCEPT_IDENTCODE")); // 受卡方标识码42
		pointcancelreversal.setOrganId(map.get("ORGAN_ID"));// 发券机构标识
		pointcancelreversal
				.setCurrencyTransCode(map.get("CURRENCY_TRANS_CODE")); // 交易货币代码49
		pointcancelreversal.setSecurityRelatedControl(map.get(
				"SECURITY_RELATED_CONTROL").equals("") ? null : map
				.get("SECURITY_RELATED_CONTROL")); // 安全控制信息53
		pointcancelreversal.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// 发卷对象标识
		pointcancelreversal.setReservedPrivate2("53"+DbHelp.getBatchNum()+"000");// 批次号
		pointcancelreversal.setOriginalMessage(map.get("ORIGINAL_MESSAGE")); // 原始信息域61
		pointcancelreversal.setMessageAuthentCode("11111111"); // MAC64
		IsoCommHandler comm = new IsoCommHandler();
		PointsCancelReversalBean pcrb = (PointsCancelReversalBean) comm
				.sendIsoMsg(pointcancelreversal); // 打包并发送报文
		return pcrb;
	}
}