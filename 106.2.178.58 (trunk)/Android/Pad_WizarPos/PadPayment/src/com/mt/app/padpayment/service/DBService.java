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
 * ���ݿ��������
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

		// �����߳�
		new Thread(new Runnable() {
			public boolean isRunning = true;

			/*
			 * db.execSQL(
			 * "insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('2','REVERSAL_FREQ','30000','������ѯʱ��')"
			 * ); db.execSQL(
			 * "insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('2','REVERSAL_AMOUNT','5','��������')"
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
						Thread.sleep(Long.parseLong(map1.get("PARA_VALUE")));// ��˯ʱ��
						// start polling method;
						// �˴���дpad��ѯ��Ҫ�ĳ���
						List<Map<String, String>> list = dbhandle
								.rawQuery(
										"select * from TBL_REVERSAL where FLUSH_RESULT !=? and FLUSH_OCUNT < ? ",
										new String[] { "00",
												map2.get("PARA_VALUE") });
						Log.i(TAG, "������ѯ...��⵽--" + list.size()
								+ "--����Ҫ����ĳ�����Ϣ");
						for (int i = 0; i < list.size(); i++) {
							// �������ѳ���
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("100000")) {
								AccuPointConsumeReversalBean apcrb = getAccuPointConsumeReversal(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (apcrb != null) {
									if (apcrb.getRespCode().equals("00")) {
										// �������ݿ�FLUSH_RESULT��������ֶ� Ϊ �����ɹ���ֵ
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("���صĻ������ѳ��������"
												+ apcrb.getRespCode());
									} else {
										// �������ݿ��ڳ��������ֶ�FLUSH_OCUNT�ϼ�1
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
										System.out.println("���صĻ������ѳ��������"
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
							// ���ѳ���
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("000000")) {
								ConsumeReversalBean crb = getConsumeReversal(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (crb != null) {
									if (crb.getRespCode().equals("00")) {
										// �������ݿ�FLUSH_RESULT��������ֶ� Ϊ �����ɹ���ֵ
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("���ص����ѳ��������"
												+ crb.getRespCode());
									} else {
										// �������ݿ��ڳ��������ֶ�FLUSH_OCUNT�ϼ�1

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
										System.out.println("���ص����ѳ��������"
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
						
							// �Ż�ȯ���ó���
							if (list.get(i).get("MSG_ID").equals("0220")
									&& list.get(i).get("PROCESS_CODE")
											.equals("022000")) {
								ReceiveCouponsReversalBean rcrb = getReceiveCouponsReversalBean(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (rcrb != null) {

									if (rcrb.getRespCode().equals("00")) {
										// �������ݿ�FLUSH_RESULT��������ֶ� Ϊ �����ɹ���ֵ
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"SYS_TRACE_AUDIT_NUM=?",
												new String[] { list
														.get(i).get("SYS_TRACE_AUDIT_NUM") });
										System.out.println("���ص����ó��������"
												+ rcrb.getRespCode());
									} else {
										// �������ݿ��ڳ��������ֶ�FLUSH_OCUNT�ϼ�1
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
										System.out.println("���ص��Ż݄����ó��������"
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
							// ���ѳ�������
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("200000")) {
								PaymentCancelReverseBean prb = getPaymentCancelReverse(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (prb != null) {
									if (prb.getRespCode().equals("00")) {
										// �������ݿ�FLUSH_RESULT��������ֶ� Ϊ �����ɹ���ֵ
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"PROCESS_CODE=?",
												new String[] { "200000" });
										System.out.println("���ص����ѳ������������"
												+ prb.getRespCode());
									} else {
										// �������ݿ��ڳ��������ֶ�FLUSH_OCUNT�ϼ�1
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
										System.out.println("���ص����ѳ������������"
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
							// ���ֳ�������
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("300000")) {
								PointsCancelReversalBean pcrb = getPointsCancelReversal(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (pcrb != null) {
									if (pcrb.getRespCode().equals("00")) {
										// �������ݿ�FLUSH_RESULT��������ֶ� Ϊ �����ɹ���ֵ
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"PROCESS_CODE=?",
												new String[] { "300000" });
										System.out.println("���صĻ��ֳ������������"
												+ pcrb.getRespCode());
									} else {
										// �������ݿ��ڳ��������ֶ�FLUSH_OCUNT�ϼ�1
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
										System.out.println("���صĻ��ֳ������������"
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
							// �һ�����
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("020000")) {
								CouponsConvertReverseBean ccrb = getCouponConvertReverse(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (ccrb != null) {
									if (ccrb.getRespCode().equals("00")) {
										// �������ݿ�FLUSH_RESULT��������ֶ� Ϊ �����ɹ���ֵ
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"PROCESS_CODE=?",
												new String[] { "020000" });
										System.out.println("���صĶһ����������"
												+ ccrb.getRespCode());
									} else {
										// �������ݿ��ڳ��������ֶ�FLUSH_OCUNT�ϼ�1
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
										System.out.println("���صĶһ����������"
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
							// �һ���������
							if (list.get(i).get("MSG_ID").equals("0200")
									&& list.get(i).get("PROCESS_CODE")
											.equals("021000")) {
								CouponsConvertRepealReversalBean ccrb = getCouponsConvertRepealReversalBean(list
										.get(i).get("SYS_TRACE_AUDIT_NUM"));
								if (ccrb != null) {
									if (ccrb.getRespCode().equals("00")) {
										// �������ݿ�FLUSH_RESULT��������ֶ� Ϊ �����ɹ���ֵ
										dbhandle.update(
												"TBL_REVERSAL",
												new String[] { "FLUSH_RESULT" },
												new String[] { "00" },
												"PROCESS_CODE=?",
												new String[] { "021000" });
										System.out.println("���صĶһ����������"
												+ ccrb.getRespCode());
									} else {
										// �������ݿ��ڳ��������ֶ�FLUSH_OCUNT�ϼ�1
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
										System.out.println("���صĶһ����������"
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
		// ִ�ж�ʱ��������
		// run();
		super.onStart(intent, startId);
	}

	// ָ��ʱ������ݿ����
	public void run() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23); // ����ʱ
		calendar.set(Calendar.MINUTE, 59); // ���Ʒ�
		calendar.set(Calendar.SECOND, 59); // ������
		Date time = calendar.getTime(); // �ó�ִ�������ʱ��,�˴�Ϊ�����23��59��59
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				dbhandle.delete("TBL_FlOW", null, null);
				dbhandle.delete("TBL_TMPFlOW", null, null);
			}
		}, time, 1000 * 60 * 60 * 24);// �����趨����ʱÿ��̶�ִ��
	}

	// �������ѳ���
	public AccuPointConsumeReversalBean getAccuPointConsumeReversal(
			String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		AccuPointConsumeReversalBean accupointconsumereversal = new AccuPointConsumeReversalBean();
		accupointconsumereversal.setMsgId("0400");// ��Ϣid
		accupointconsumereversal.setTrack2(map.get("TRACK2"));// ��������
		accupointconsumereversal.setProcessCode(map.get("PROCESS_CODE"));// ���״�����
		accupointconsumereversal.setTransAmount(map.get("TRANS_AMOUNT"));// ���׽��
		accupointconsumereversal.setSysTraceAuditNum(map
				.get("SYS_TRACE_AUDIT_NUM"));// �ܿ���ϵͳ���ٺ�
		accupointconsumereversal.setDateExpired(map.get("DATE_EXPIRED").equals(
				"") ? null : map.get("DATE_EXPIRED"));// ����Ч��
		accupointconsumereversal.setServiceEntryMode(map
				.get("SERVICE_ENTRY_MODE"));// ��������뷽ʽ��
		accupointconsumereversal.setServiceConditionMode(map
				.get("SERVICE_CONDITION_MODE"));// �����������
		accupointconsumereversal.setRespCode("98");// Ӧ����
		accupointconsumereversal.setCardAcceptIdentcode(map
				.get("CARD_ACCEPT_IDENTCODE"));// �ܿ����ն˱�ʶ��***
		accupointconsumereversal.setCardAcceptTermIdent(map
				.get("CARD_ACCEPT_TERM_IDENT"));// �ܿ�����ʶ��***
		accupointconsumereversal.setOrganId(map.get("ORGAN_ID"));// ��ȯ������ʶ
		accupointconsumereversal.setCurrencyTransCode(map
				.get("CURRENCY_TRANS_CODE"));// ���׻��Ҵ���
		accupointconsumereversal.setCouponsAdvertId(map
				.get("COUPONS_ADVERT_ID"));// ��������ʶ
		accupointconsumereversal.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// ���κ�
		accupointconsumereversal.setMessageAuthentCode("11111111");// mac
		IsoCommHandler comm = new IsoCommHandler();
		AccuPointConsumeReversalBean apcrl = (AccuPointConsumeReversalBean) comm
				.sendIsoMsg(accupointconsumereversal); // ��������ͱ���
		return apcrl;
	}

	// ���ѳ���
	public ConsumeReversalBean getConsumeReversal(String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		ConsumeReversalBean consumereversal = new ConsumeReversalBean();
		consumereversal.setMsgId("0400");// ��Ϣid
		consumereversal.setCardNo(map.get("CARD_NO_1").equals("")?null:map.get("CARD_NO_1"));// ֧������
		consumereversal.setTrack2(map.get("TRACK2"));//��������
		consumereversal.setProcessCode(map.get("PROCESS_CODE"));// ���״�����
		consumereversal.setTransAmount(map.get("TRANS_AMOUNT"));// ���׽��
		consumereversal.setDiscountAmount(map.get("DISCOUNT_AMOUNT"));//���ӽ��
		consumereversal.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// �ܿ���ϵͳ���ٺ�
		consumereversal
				.setDateExpired(map.get("DATE_EXPIRED").equals("") ? null : map
						.get("DATE_EXPIRED"));// ����Ч��
		consumereversal.setServiceEntryMode(map.get("SERVICE_ENTRY_MODE"));// ��������뷽ʽ��
		consumereversal.setServiceConditionMode(map
				.get("SERVICE_CONDITION_MODE"));// �����������
		consumereversal.setAuthorIdentResp(map.get("AUTHOR_IDENT_RESP").equals(
				"") ? null : map.get("AUTHOR_IDENT_RESP"));// ��Ȩ��
		consumereversal.setRespCode("98");// Ӧ����
		consumereversal
				.setCardAcceptIdentcode(map.get("CARD_ACCEPT_IDENTCODE"));// �ܿ����ն˱�ʶ��
		consumereversal.setCardAcceptTermIdent(map
				.get("CARD_ACCEPT_TERM_IDENT"));// �ܿ�����ʶ��
		consumereversal.setCurrencyTransCode(map.get("CURRENCY_TRANS_CODE"));// ���׻��Ҵ���
		consumereversal.setOrganId(map.get("ORGAN_ID").equals("") ? null
				: map.get("ORGAN_ID"));//��ȯ������ʶ
		consumereversal.setSwapCode(map.get("SWAP_CODE_1").equals("") ? null
				: map.get("SWAP_CODE_1"));// �һ���
		consumereversal.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID").equals("") ? null
				: map.get("COUPONS_ADVERT_ID"));//��ȯ�����ʶ
		consumereversal.setReservedPrivate1(map.get("RESERVED_PRIVATE1"));// Ӧ�ñ�ʶ
		
		consumereversal.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// ���κ�
		consumereversal.setMessageAuthentCode(null);// mac
		IsoCommHandler comm = new IsoCommHandler();
		ConsumeReversalBean crlb = (ConsumeReversalBean) comm
				.sendIsoMsg(consumereversal); // ��������ͱ���
		return crlb;
	}

	// �Ż�ȯ�һ�����
	public CouponsConvertReverseBean getCouponConvertReverse(String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		CouponsConvertReverseBean couponconvertreverse = new CouponsConvertReverseBean();
		couponconvertreverse.setMsgId("0400");// ��Ϣid
		couponconvertreverse.setTrack2(map.get("TRACK2"));// ��������35
		couponconvertreverse.setProcessCode(map.get("PROCESS_CODE"));// ���״�����3
		couponconvertreverse.setTransAmount(map.get("TRANS_AMOUNT").equals("")?"000000000000":map.get("TRANS_AMOUNT"));// ���׽��4
		couponconvertreverse.setDiscountAmount(map.get("DISCOUNT_AMOUNT"));//���ӽ��
		couponconvertreverse
				.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// �ܿ���ϵͳ���ٺ�11
		couponconvertreverse.setAuthorIdentResp(map.get("AUTHOR_IDENT_RESP")
				.equals("") ? null : map.get("AUTHOR_IDENT_RESP"));// ��Ȩ��38
		couponconvertreverse.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
		couponconvertreverse.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42
		couponconvertreverse.setOrganId(map.get("ORGAN_ID"));// ���������ʶ
		couponconvertreverse.setSwapCode(map.get("SWAP_CODE_1").equals("")?null:map.get("SWAP_CODE_1"));//�һ���
		couponconvertreverse.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// ��������ʶ58
		couponconvertreverse.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// ���κ�
		couponconvertreverse.setMessageAuthentCode("11111111"); // MAC64
		IsoCommHandler comm = new IsoCommHandler();
		CouponsConvertReverseBean ccrb = (CouponsConvertReverseBean) comm
				.sendIsoMsg(couponconvertreverse); // ��������ͱ���
		return ccrb;
	}
	// �Ż�ȯ�һ���������
	public CouponsConvertRepealReversalBean getCouponsConvertRepealReversalBean(String flownum) {
			Map<String, String> map = dbhandle.rawQueryOneRecord(
					"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
					new String[] { flownum });
			CouponsConvertRepealReversalBean couponsConvertRepealReversalBean = new CouponsConvertRepealReversalBean();
			couponsConvertRepealReversalBean.setMsgId("0400");// ��Ϣid
			couponsConvertRepealReversalBean.setTrack2(map.get("TRACK2"));// ��������35
			couponsConvertRepealReversalBean.setProcessCode(map.get("PROCESS_CODE"));// ���״�����3
			couponsConvertRepealReversalBean.setTransAmount(map.get("TRANS_AMOUNT").equals("")?"000000000000":map.get("TRANS_AMOUNT"));// ���׽��4
			couponsConvertRepealReversalBean.setDiscountAmount(map.get("DISCOUNT_AMOUNT"));//���ӽ��
			couponsConvertRepealReversalBean
					.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// �ܿ���ϵͳ���ٺ�11
			couponsConvertRepealReversalBean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
			couponsConvertRepealReversalBean.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42
			couponsConvertRepealReversalBean.setOrganId(map.get("ORGAN_ID"));// ���������ʶ
			couponsConvertRepealReversalBean.setSwapCode(map.get("SWAP_CODE_1").equals("")?null:map.get("SWAP_CODE_1"));//�һ���
			couponsConvertRepealReversalBean.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// ��������ʶ58
			couponsConvertRepealReversalBean.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// ���κ�
			couponsConvertRepealReversalBean.setOriginalMessage(map.get("ORIGINAL_MESSAGE"));//ԭʼ��Ϣ��
			couponsConvertRepealReversalBean.setMessageAuthentCode("11111111"); // MAC64
			IsoCommHandler comm = new IsoCommHandler();
			CouponsConvertRepealReversalBean ccrb = (CouponsConvertRepealReversalBean) comm
					.sendIsoMsg(couponsConvertRepealReversalBean); // ��������ͱ���
			return ccrb;
		}
	
	// �Ż�ȯ���ó���
	public ReceiveCouponsReversalBean getReceiveCouponsReversalBean(String flownum) {
			Map<String, String> map = dbhandle.rawQueryOneRecord(
					"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
					new String[] { flownum });
			ReceiveCouponsReversalBean receiveCouponsReversalBean = new ReceiveCouponsReversalBean();
			receiveCouponsReversalBean.setMsgId("0400");// ��Ϣid
			receiveCouponsReversalBean.setTrack2(map.get("TRACK2"));// ��������35
			receiveCouponsReversalBean.setProcessCode(map.get("PROCESS_CODE"));// ���״�����3
			receiveCouponsReversalBean.setTransAmount(map.get("TRANS_AMOUNT").equals("")?"000000000000":map.get("TRANS_AMOUNT"));// ���׽��4
			receiveCouponsReversalBean
					.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// �ܿ���ϵͳ���ٺ�11
			receiveCouponsReversalBean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// �ܿ����ն˱�ʶ��41
			receiveCouponsReversalBean.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// �ܿ�����ʶ��42
			receiveCouponsReversalBean.setOrganId(map.get("ORGAN_ID"));// ���������ʶ
			receiveCouponsReversalBean.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// ��������ʶ58
			receiveCouponsReversalBean.setReservedPrivate2(map.get("RESERVED_PRIVATE2"));// ���κ�
			receiveCouponsReversalBean.setMessageAuthentCode("11111111"); // MAC64
			IsoCommHandler comm = new IsoCommHandler();
			ReceiveCouponsReversalBean rcrb = (ReceiveCouponsReversalBean) comm
					.sendIsoMsg(receiveCouponsReversalBean); // ��������ͱ���
			return rcrb;
		}
	
	// ���ѳ�������
	public PaymentCancelReverseBean getPaymentCancelReverse(String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		PaymentCancelReverseBean paymentcancelresverse = new PaymentCancelReverseBean();
		paymentcancelresverse.setMsgId("0400");// ��Ϣid
		paymentcancelresverse.setCardNo(map.get("CARD_NO_1"));// ֧������2
		paymentcancelresverse.setTrack2(map.get("TRACK2")); //��������
		paymentcancelresverse.setProcessCode(map.get("PROCESS_CODE"));// ���״�����3
		paymentcancelresverse.setTransAmount(map.get("TRANS_AMOUNT"));// ���׽��4
		paymentcancelresverse.setDiscountAmount(map.get("DISCOUNT_AMOUNT"));//���ӽ��
		paymentcancelresverse.setSysTraceAuditNum(map
				.get("SYS_TRACE_AUDIT_NUM"));// �ܿ���ϵͳ���ٺ�11
		paymentcancelresverse
				.setDateExpired(map.get("DATE_EXPIRED").equals("") ? null : map
						.get("DATE_EXPIRED")); // ����Ч��14
		paymentcancelresverse
				.setServiceEntryMode(map.get("SERVICE_ENTRY_MODE")); // ��������뷽ʽ��22
		paymentcancelresverse.setServiceConditionMode(map
				.get("SERVICE_CONDITION_MODE"));// �����������25
		paymentcancelresverse.setAuthorIdentResp(map.get("AUTHOR_IDENT_RESP")
				.equals("") ? null : map.get("AUTHOR_IDENT_RESP"));// ��Ȩ��38
		paymentcancelresverse.setRespCode("98"); // Ӧ����39
		paymentcancelresverse.setCardAcceptIdentcode(map
				.get("CARD_ACCEPT_IDENTCODE"));// �ܿ�����ʶ��42
		paymentcancelresverse.setCardAcceptTermIdent(map
				.get("CARD_ACCEPT_TERM_IDENT"));// �ܿ����ն˱�ʶ��41
		paymentcancelresverse.setCurrencyTransCode(map
				.get("CURRENCY_TRANS_CODE")); // ���׻��Ҵ���49
		paymentcancelresverse.setSecurityRelatedControl(map.get(
				"SECURITY_RELATED_CONTROL").equals("") ? null : map
				.get("SECURITY_RELATED_CONTROL"));// ��ȫ������Ϣ53
		paymentcancelresverse.setOrganId(map
				.get("ORGAN_ID"));//��ȯ������ʶ
		paymentcancelresverse
				.setSwapCode(map.get("SWAP_CODE_1").equals("") ? null : map
						.get("SWAP_CODE_1"));// �һ���
		paymentcancelresverse.setReservedPrivate1(map.get("RESERVED_PRIVATE1")); // Ӧ�ñ�ʶ59
		paymentcancelresverse.setReservedPrivate2("23"+DbHelp.getBatchNum()+"000"); // ���κ�60
		paymentcancelresverse.setOriginalMessage(map.get("ORIGINAL_MESSAGE")); // ԭʼ��Ϣ��61
		paymentcancelresverse.setMessageAuthentCode(map
				.get("MESSAGE_AUTHENT_CODE"));// MAC64
		IsoCommHandler comm = new IsoCommHandler();
		PaymentCancelReverseBean pclrb = (PaymentCancelReverseBean) comm
				.sendIsoMsg(paymentcancelresverse); // ��������ͱ���
		return pclrb;
	}

	// ���ֳ�������
	public PointsCancelReversalBean getPointsCancelReversal(String flownum) {
		Map<String, String> map = dbhandle.rawQueryOneRecord(
				"select * from TBL_REVERSAL where SYS_TRACE_AUDIT_NUM = ?",
				new String[] { flownum });
		PointsCancelReversalBean pointcancelreversal = new PointsCancelReversalBean();
		pointcancelreversal.setMsgId("0400");// ��Ϣid
		pointcancelreversal.setTrack2(map.get("TRACK2"));// ��������35
		pointcancelreversal.setProcessCode(map.get("PROCESS_CODE"));// ���״�����3
		pointcancelreversal.setTransAmount(map.get("TRANS_AMOUNT"));// ���׽��4
		pointcancelreversal.setSysTraceAuditNum(map.get("SYS_TRACE_AUDIT_NUM"));// �ܿ���ϵͳ���ٺ�11
		pointcancelreversal
				.setDateExpired(map.get("DATE_EXPIRED").equals("") ? null : map
						.get("DATE_EXPIRED")); // ����Ч��14
		pointcancelreversal.setServiceEntryMode(map.get("SERVICE_ENTRY_MODE"));// ��������뷽ʽ��22
		pointcancelreversal.setServiceConditionMode(map
				.get("SERVICE_CONDITION_MODE"));// �����������25
		pointcancelreversal.setRespCode("98");// Ӧ����39
		pointcancelreversal.setCardAcceptTermIdent(map
				.get("CARD_ACCEPT_TERM_IDENT"));// �ܿ����ն˱�ʶ��41
		pointcancelreversal.setCardAcceptIdentcode(map
				.get("CARD_ACCEPT_IDENTCODE")); // �ܿ�����ʶ��42
		pointcancelreversal.setOrganId(map.get("ORGAN_ID"));// ��ȯ������ʶ
		pointcancelreversal
				.setCurrencyTransCode(map.get("CURRENCY_TRANS_CODE")); // ���׻��Ҵ���49
		pointcancelreversal.setSecurityRelatedControl(map.get(
				"SECURITY_RELATED_CONTROL").equals("") ? null : map
				.get("SECURITY_RELATED_CONTROL")); // ��ȫ������Ϣ53
		pointcancelreversal.setCouponsAdvertId(map.get("COUPONS_ADVERT_ID"));// ��������ʶ
		pointcancelreversal.setReservedPrivate2("53"+DbHelp.getBatchNum()+"000");// ���κ�
		pointcancelreversal.setOriginalMessage(map.get("ORIGINAL_MESSAGE")); // ԭʼ��Ϣ��61
		pointcancelreversal.setMessageAuthentCode("11111111"); // MAC64
		IsoCommHandler comm = new IsoCommHandler();
		PointsCancelReversalBean pcrb = (PointsCancelReversalBean) comm
				.sendIsoMsg(pointcancelreversal); // ��������ͱ���
		return pcrb;
	}
}