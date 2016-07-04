package com.mt.app.padpayment.activity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.TextView;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.padpayment.common.DbInfoImpl;
import com.mt.app.padpayment.driver.PrinterDriver;
import com.mt.app.padpayment.tools.MoneyUtil;
import com.wizarpos.mt.PurchaseBill;
/**
 *  
 * @author Administrator
 *
 */
public class ApppaySuccessActivity extends DemoSmartActivity {
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView("APPPAYSUCCESS.SCREEN");
		
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		String msgInfo = bundle.getString("msgInfo");
		TextView tv = (TextView) findViewById("APPPAYSUCCESSTEXT");
		tv.setText(msgInfo);
		
		
		if (msgInfo.contains("成功")) {
			
			String num = bundle.getString("sysnum");     //交易流水号
			if(!(num.equals(""))){
				
				
				try{
					PurchaseBill purchaseBill = new PurchaseBill();
					
					DbHandle db = new DbHandle();
					
					List<Map<String , String>> listmap = db.rawQuery("select * from TBL_MANAGE", null);
					try {
						String merchName = listmap.get(0).get("CARD_ACCEPT_LOCAL");
						if(merchName != null && !(merchName.equals(""))){
							purchaseBill.setMerchantName(merchName);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					Map<String , String> map = db.selectOneRecord("TBL_FlOW", DbInfoImpl.FieldNames[1] , "SYS_TRACE_AUDIT_NUM = ?"
							, new String[]{num}, null, null, null);
					
					if(map != null && map.size() != 0){
						
						purchaseBill.setMerchantNo(map.get("CARD_ACCEPT_IDENTCODE_1"));     //商户编号    CARD_ACCEPT_IDENTCODE 
					    purchaseBill.setTerminalNo(map.get("CARD_ACCEPT_TERM_IDENT_1"));    //终端编号     CARD_ACCEPT_TERM_IDENT
					    purchaseBill.setOperator(map.get("USER_ID"));                     //操作员号     USER_ID
					    purchaseBill.setBaseCardNumber(map.get("TRACK2_1"));	              //基卡卡号     TRACK2			 
					    purchaseBill.setPrepayCardName(" ");      //预付卡名称      RESERVED_PRIVATE3
					    if(map.get("MSG_ID_1").equals("0210")){
					    	purchaseBill.setTxnType("消费");                                 //交易类别       MSG_ID
					    }
					    purchaseBill.setPrepayCardNo(map.get("CARD_NO_1"));                 //预付卡卡号      CARD_NO
					    purchaseBill.setCouponNo(map.get("COUPONS_IDS"));                 //优惠券编号      COUPONS_IDS
					    purchaseBill.setPrepayCardExpdate(map.get("DATE_EXPIRED_1"));       //卡有效期      DATE_EXPIRED
					    purchaseBill.setVoucherNo(map.get("SYS_TRACE_AUDIT_NUM_1"));        //凭证号（流水号）   SYS_TRACE_AUDIT_NUM
					    purchaseBill.setBatchNo(map.get("RESERVED_PRIVATE2_1"));            //批次号           RESERVED_PRIVATE2
					    
					    String time = map.get("LOCAL_TRANS_DATE_1") ;
					    String time1 = time.substring(0, 2);
					    String time2 = time.substring(2, 4);
					    purchaseBill.setDataTime(time1 + "/" + time2);                   //日期时间      LOCAL_TRANS_DATE
					    
					    purchaseBill.setAuthNo(map.get("AUTHOR_IDENT_RESP_1"));             //授权码          AUTHOR_IDENT_RESP
					    purchaseBill.setRefNo(map.get("RET_REFER_NUM_1"));                  //参考号         RET_REFER_NUM
					    purchaseBill.setCouponType(map.get("COUPONS_TYPES"));             //优惠券类别        COUPONS_TYPES
					    
					    String mon = map.get("DISCOUNT_AMOUNT_1") ;
					    String realmon = mon.substring(12, 24);
					    purchaseBill.setCouponDeduce( "RMB  " + MoneyUtil.getMoney(realmon));         //抵扣金额        DISCOUNT_AMOUNT
					    
					    purchaseBill.setPrepayCardDis(map.get("APP_DISCOUNT"));                 //支付卡折扣率        APP_DISCOUNT
					    purchaseBill.setDisAmount( "RMB  " + map.get("VIP_AMOUNT"));                 //折扣金额        VIP_AMOUNT
					    purchaseBill.setActuAmount( "RMB  " + MoneyUtil.getMoney(map.get("TRANS_AMOUNT_1")));    //支付金额        TRANS_AMOUNT
						
					}
				    
			
				    purchaseBill.setReference("重打印凭证/DUPLICATD");        //备注
				    /*-----------demo data-----------*/
				    Class<PrinterDriver> clazz = (Class<PrinterDriver>) Class.forName((String) Controller.session.get("PrinterDriver")) ;
//				    Method method = clazz.getDeclaredMethod("getInstance") ;
//				    PrinterDriver driver = (PrinterDriver) method.invoke(clazz) ;
				    PrinterDriver driver = clazz.newInstance() ;
				    driver.printerPurchaseBill(purchaseBill) ;
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				
			}
			
			
		}
		
		
	}

	@Override
	public Request getRequestByCommandName(String commandIDName) {
		Request request = new Request();
		return request;

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
