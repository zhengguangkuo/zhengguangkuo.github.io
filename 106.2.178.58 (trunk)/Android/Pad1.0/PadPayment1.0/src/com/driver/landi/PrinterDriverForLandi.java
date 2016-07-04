package com.driver.landi;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.landicorp.android.eptapi.DeviceService;
import com.landicorp.android.eptapi.device.Printer;
import com.landicorp.android.eptapi.device.Printer.Format;
import com.landicorp.android.eptapi.exception.ReloginException;
import com.landicorp.android.eptapi.exception.ReqeustException;
import com.landicorp.android.eptapi.exception.ServiceOccupiedException;
import com.landicorp.android.eptapi.exception.UnsupportMultiProcess;
import com.mt.app.padpayment.driver.PrinterDriver;
import com.wizarpos.mt.PrintTag;
import com.wizarpos.mt.PurchaseBill;

public class PrinterDriverForLandi extends PrinterDriver {
	protected static final String TAG_LOG = PrinterDriverForLandi.class.getSimpleName() ;
	private Context ctx ;
	
	public void setCtx(Context context) {
		this.ctx = context ;
	}

	@Override
	public void printerPurchaseBill(final PurchaseBill bill) {
		try {
			login() ;
			new Printer.Progress() {					
				@Override
				public void doPrint(Printer printer) throws Exception {
					//Ĭ����ʵģʽ,�˴�����Ϊ��ģʽ
					printer.setMode(Printer.MODE_VIRTUAL);
					Format format = new Format();
					format.setAscScale(format.ASC_SC1x2) ;
					format.setHzScale(Format.HZ_SC1x2);						
					printer.setFormat(format);
					printer.printText("          " + PrintTag.PurchaseBillTag.PURCHASE_BILL_TITLE + "\n");
					format.setHzScale(Format.HZ_SC1x1);	
					format.setAscScale(format.ASC_SC1x1) ;
					printer.setFormat(format);
					printer.printText(PrintTag.PurchaseBillTag.MERCHANT_NAME_TAG + bill.getMerchantName() + "\n");
					printer.printText("\n");
					printer.printText(PrintTag.PurchaseBillTag.MERCHANT_NO_TAG + bill.getMerchantNo() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.TERMINAL_NO_TAG + bill.getTerminalNo() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.OPERATOR_TAG + bill.getOperator() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.CARD_NUMBER_TAG + bill.getBaseCardNumber() + "\n");
					printer.printText("- - - - - - - - - - - - - - - -\n");
					printer.printText(PrintTag.PurchaseBillTag.PREPAY_CARD_NAME + bill.getPrepayCardName() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.TXN_TYPE_TAG + bill.getTxnType() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.PREPAY_CARD_NO + bill.getPrepayCardNo() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.PRECARD_EXP_DATE + bill.getPrepayCardExpdate() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.COUPON_NO + bill.getCouponNo() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.COUPON_EXP_DATE + bill.getCouponExpdate() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.VOUCHER_NO_TAG + bill.getVoucherNo() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.BATCH_NO_TAG + bill.getBatchNo() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.DATE_TIME_TAG + bill.getDataTime() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.AUTH_NO_TAG + bill.getAuthNo() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.REF_NO_TAG + bill.getRefNo() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.COUPON_TYPE + bill.getCouponType() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.TRADE_AMOUT + bill.getCouponDeduce() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.PREPAY_CARD_DIS + bill.getPrepayCardDis() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.DIS_AMOUNT + bill.getDisAmount() + "\n");
					printer.printText(PrintTag.PurchaseBillTag.ACTUAL_AMOUNT + bill.getActuAmount() + "\n");
					printer.printText("- - - - - - - - - - - - - - - -\n");
					printer.printText(PrintTag.PurchaseBillTag.REFERENCE_TAG + bill.getReference() + "\n");
					printer.feedLine(3);
					printer.printText("- - - - - - - - - - - - - - - -\n");
					printer.printText(PrintTag.PurchaseBillTag.C_CARD_HOLDER_SIGNATURE_TAG + "\n");
					printer.feedLine(2);
					printer.printText("����ȷ�����Ͻ��ף�ͬ�⽫����뱾\n���˻�\n");
					printer.printText("\n\n");
				}
				
				@Override
				public void onCrash() {
					onServiceException() ;
				}
				
				@Override
				public void onFinish(int code) {
					switch(code) {							
					case Printer.ERROR_NONE:
						Log.i(TAG_LOG, "��ӡ����, ��ӡ�ɹ�!") ;
						break;
					default:
						Log.i(TAG_LOG, "��ӡʧ��!������� : " + code + " ["+getErrorDescription(code)+"]") ;
						break;								
					}
				}
				
				
				String getErrorDescription(int code){
					switch(code) {
//					case Printer.STATE_OK: return "����״̬���޴���";
//					case Printer.ERROR_NONE: return "����״̬���޴���";
					case Printer.ERROR_PAPERENDED: return "ȱֽ�����ܴ�ӡ"; 
					case Printer.ERROR_HARDERR: return "Ӳ������"; 
					case Printer.ERROR_OVERHEAT: return "��ӡͷ����"; 
					case Printer.ERROR_BUFOVERFLOW: return "����ģʽ����������λ�ó�����Χ"; 
					case Printer.ERROR_LOWVOL: return "��ѹ����"; 
					case Printer.ERROR_PAPERENDING: return "ֽ�Ž�Ҫ�þ����������ӡ*(������������з���ֵ)"; 
					case Printer.ERROR_MOTORERR: return "��ӡ��о����(������߹���)"; 
					case Printer.ERROR_PENOFOUND: return "�Զ���λû���ҵ�����λ��,ֽ�Żص�ԭ��λ��"; 
					case Printer.ERROR_PAPERJAM: return "��ֽ"; 
					case Printer.ERROR_NOBM: return "û���ҵ��ڱ�"; 
					case Printer.ERROR_BUSY: return "��ӡ������æ״̬"; 
					case Printer.ERROR_BMBLACK: return "�ڱ�̽������⵽��ɫ�ź�"; 
					case Printer.ERROR_WORKON: return "��ӡ����Դ���ڴ�״̬";
					case Printer.ERROR_LIFTHEAD: return "��ӡͷ̧��*(����������ӡ�����з���ֵ)"; 
					case Printer.ERROR_LOWTEMP: return "���±�����AD����*(����������ӡ�����з���ֵ)"; 
					}
					return "δ֪����";
				}
			}.start();
		} catch (ReqeustException e) {
			e.printStackTrace();
			onServiceException();
		}  
	}


	/**
     * �����쳣����
     */
    public void onServiceException(){
    	Log.i(TAG_LOG, "�豸�����쳣���������������Ժ�����!") ;
    	login() ;
    }
    
    /**
     * ��¼���豸����
     */
    public void login(){
    	try {
			DeviceService.login(ctx);
		} catch (ServiceOccupiedException e) {
			e.printStackTrace();
		} catch (ReloginException e) {
			e.printStackTrace();
		} catch (UnsupportMultiProcess e) {
			e.printStackTrace();
		} catch (ReqeustException e) {
			e.printStackTrace();
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					login();
				}				
			}, 100);
		}
    }
}
