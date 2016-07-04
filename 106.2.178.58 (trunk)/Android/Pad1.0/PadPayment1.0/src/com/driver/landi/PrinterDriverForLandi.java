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
					//默认是实模式,此处设置为虚模式
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
					printer.printText("本人确认以上交易，同意将其记入本\n卡账户\n");
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
						Log.i(TAG_LOG, "打印结束, 打印成功!") ;
						break;
					default:
						Log.i(TAG_LOG, "打印失败!错误代码 : " + code + " ["+getErrorDescription(code)+"]") ;
						break;								
					}
				}
				
				
				String getErrorDescription(int code){
					switch(code) {
//					case Printer.STATE_OK: return "正常状态，无错误";
//					case Printer.ERROR_NONE: return "正常状态，无错误";
					case Printer.ERROR_PAPERENDED: return "缺纸，不能打印"; 
					case Printer.ERROR_HARDERR: return "硬件错误"; 
					case Printer.ERROR_OVERHEAT: return "打印头过热"; 
					case Printer.ERROR_BUFOVERFLOW: return "缓冲模式下所操作的位置超出范围"; 
					case Printer.ERROR_LOWVOL: return "低压保护"; 
					case Printer.ERROR_PAPERENDING: return "纸张将要用尽，还允许打印*(单步进针打特有返回值)"; 
					case Printer.ERROR_MOTORERR: return "打印机芯故障(过快或者过慢)"; 
					case Printer.ERROR_PENOFOUND: return "自动定位没有找到对齐位置,纸张回到原来位置"; 
					case Printer.ERROR_PAPERJAM: return "卡纸"; 
					case Printer.ERROR_NOBM: return "没有找到黑标"; 
					case Printer.ERROR_BUSY: return "打印机处于忙状态"; 
					case Printer.ERROR_BMBLACK: return "黑标探测器检测到黑色信号"; 
					case Printer.ERROR_WORKON: return "打印机电源处于打开状态";
					case Printer.ERROR_LIFTHEAD: return "打印头抬起*(自助热敏打印机特有返回值)"; 
					case Printer.ERROR_LOWTEMP: return "低温保护或AD出错*(自助热敏打印机特有返回值)"; 
					}
					return "未知错误";
				}
			}.start();
		} catch (ReqeustException e) {
			e.printStackTrace();
			onServiceException();
		}  
	}


	/**
     * 服务异常处理
     */
    public void onServiceException(){
    	Log.i(TAG_LOG, "设备服务异常，正在重启，请稍后重试!") ;
    	login() ;
    }
    
    /**
     * 登录到设备服务
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
