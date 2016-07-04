package com.driver.centerm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.util.Log;

import com.centerm.device.printer.PrintCmd;
import com.centerm.mid.exception.CentermApiException;
import com.centerm.mid.exception.CentermApiException.IndicationException;
import com.centerm.mid.exception.CentermApiException.PrinterException;
import com.centerm.mid.imp.socketimp.DeviceFactory;
import com.centerm.mid.inf.PrinterDevInf;
import com.mt.app.padpayment.driver.PrinterDriver;
import com.wizarpos.mt.PrintTag;
import com.wizarpos.mt.PurchaseBill;

public class PrinterDriverForCenterm extends PrinterDriver {

	private PrinterDevInf mPrinter;

	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception {
		mPrinter = DeviceFactory.getPrinterDev();
	}

	/**
	 * 打开
	 * 
	 * @throws Exception
	 */
	private void open() throws Exception {
		mPrinter.open();
	}

	/**
	 * 打印数据
	 * 
	 * @throws Exception
	 */
	private void print(byte[] data) throws Exception {
		mPrinter.print(data);
	}

	/**
	 * 关闭打印
	 * 
	 * @throws Exception
	 */
	private void close() throws Exception {
		mPrinter.close();
	}

	@Override
	public void printerPurchaseBill(final PurchaseBill purchaseBill) {
//		new Thread() {
//			public void run() {
				try {
					init();
					open();
					print(initData(purchaseBill));
					close();
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof CentermApiException.IndicationException) {
						int devId = ((IndicationException) e).getDevId();
						int eventId = ((IndicationException) e).getEventId();
						Log.d("", "打印机设备异常......" + "设备号 : " + devId + "错误码：" + eventId);
					}
					if (e instanceof PrinterException) {
						int eventId = ((PrinterException) e).getEventId();
						Log.d("", "打印机设备异常......" + "eventId" + eventId);
					} else {
						Log.d("", "打印机其他异常....." + e.toString());
					}
				}
//			}

//		}.start();
	}

	/**
	 * 签购单格式
	 * 
	 * @return
	 */
	private byte[] initData(PurchaseBill bill) {
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		try {
			ous.write(PrintCmd.PRINT_INIT);
			ous.write(PrintCmd.PRINT_ALIGN_CENTER);
			byte[] purchaseBillTitle = strToByteArray(PrintTag.PurchaseBillTag.PURCHASE_BILL_TITLE) ;
			ous.write(PrintCmd.PRINT_BOLD);
			ous.write(PrintCmd.PRINT_DOUBLE_SIZE);
			ous.write(purchaseBillTitle) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(PrintCmd.PRINT_CANCEL_BOLD) ;
			ous.write(PrintCmd.PRINT_CANCEL_DOUBLE_SIZE) ;
			byte[] merchantNameTag = strToByteArray(PrintTag.PurchaseBillTag.MERCHANT_NAME_TAG + bill.getMerchantName()) ;
			ous.write(PrintCmd.PRINT_ALIGN_LEFT);
			ous.write(merchantNameTag) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] merchantNoTag = strToByteArray(PrintTag.PurchaseBillTag.MERCHANT_NO_TAG + bill.getMerchantNo()) ;
			ous.write(merchantNoTag) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] terminalNoTag = strToByteArray(PrintTag.PurchaseBillTag.TERMINAL_NO_TAG + bill.getTerminalNo()) ;
			ous.write(terminalNoTag) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] operatorTagValue = strToByteArray(PrintTag.PurchaseBillTag.OPERATOR_TAG + bill.getOperator()) ;
			ous.write(operatorTagValue) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] issNoTagValue = strToByteArray(PrintTag.PurchaseBillTag.CARD_NUMBER_TAG + bill.getBaseCardNumber()) ;
			ous.write(issNoTagValue) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] line = strToByteArray("- - - - - - - - - - - - - - - -") ;
			ous.write(line) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] prepayCardName = strToByteArray(PrintTag.PurchaseBillTag.PREPAY_CARD_NAME + bill.getPrepayCardName()) ;
			ous.write(prepayCardName) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] txnTypeTag = strToByteArray(PrintTag.PurchaseBillTag.TXN_TYPE_TAG + bill.getTxnType()) ;
			ous.write(txnTypeTag) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] prepayValue = strToByteArray(PrintTag.PurchaseBillTag.PREPAY_CARD_NO + bill.getPrepayCardNo()) ;
			ous.write(prepayValue) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] couponNo = strToByteArray(PrintTag.PurchaseBillTag.COUPON_NO + bill.getCouponNo()) ;
			ous.write(couponNo) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] couponExpdate = strToByteArray(PrintTag.PurchaseBillTag.COUPON_EXP_DATE + bill.getCouponExpdate()) ;
			ous.write(couponExpdate) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] expDateTagValue = strToByteArray(PrintTag.PurchaseBillTag.PRECARD_EXP_DATE + bill.getPrepayCardExpdate()) ;
			ous.write(expDateTagValue) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] voucherNo = strToByteArray(PrintTag.PurchaseBillTag.VOUCHER_NO_TAG + bill.getVoucherNo()) ;
			ous.write(voucherNo) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] batchNoTagValue = strToByteArray(PrintTag.PurchaseBillTag.BATCH_NO_TAG + bill.getBatchNo()) ;
			ous.write(batchNoTagValue) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] dateTime = strToByteArray(PrintTag.PurchaseBillTag.DATE_TIME_TAG + bill.getDataTime()) ;
			ous.write(dateTime) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] authNo = strToByteArray(PrintTag.PurchaseBillTag.AUTH_NO_TAG + bill.getAuthNo()) ;
			ous.write(authNo) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] refNo = strToByteArray(PrintTag.PurchaseBillTag.REF_NO_TAG + bill.getRefNo()) ;
			ous.write(refNo) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] couponType = strToByteArray(PrintTag.PurchaseBillTag.COUPON_TYPE + bill.getCouponType()) ;
			ous.write(couponType) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] couponDeduce = strToByteArray(PrintTag.PurchaseBillTag.TRADE_AMOUT + bill.getCouponDeduce()) ;
			ous.write(couponDeduce) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] prepayDis = strToByteArray(PrintTag.PurchaseBillTag.PREPAY_CARD_DIS + bill.getPrepayCardDis()) ;
			ous.write(prepayDis) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] disAmount = strToByteArray(PrintTag.PurchaseBillTag.DIS_AMOUNT + bill.getDisAmount()) ;
			ous.write(disAmount) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] actualAmount = strToByteArray(PrintTag.PurchaseBillTag.ACTUAL_AMOUNT + bill.getActuAmount()) ;
			ous.write(actualAmount) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(line) ;			
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] reference = strToByteArray(PrintTag.PurchaseBillTag.REFERENCE_TAG + bill.getReference()) ;
			ous.write(reference) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;		
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;	
			ous.write(line) ;
			byte[] cardHolder = strToByteArray(PrintTag.PurchaseBillTag.C_CARD_HOLDER_SIGNATURE_TAG) ;
			ous.write(cardHolder) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			byte[] agreeTrade = strToByteArray(PrintTag.PurchaseBillTag.C_AGREE_TRADE_TAG) ;
			ous.write(agreeTrade) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
			ous.write(PrintCmd.PRINT_AND_NEWLINE) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ous.toByteArray();
	}

	private byte[] strToByteArray(String str) throws UnsupportedEncodingException {

		return new String(str).getBytes("gb2312");

	}

}
