package com.wizarpos.mt;

public class PrintTag 
{

    /**
     * 签购单标签
     *//*
    static public final class PurchaseBillTag 
    {
    	static public final String PURCHASE_BILL_TITLE = "POS 签 购 单";

    	static public final String MERCHANT_NAME_TAG = "商户名称(MERCHANT NAME):";

    	static public final String MERCHANT_NO_TAG = "商户编号(MERCHANT NO):";

    	static public final String TERMINAL_NO_TAG = "终端编号(TERMINAL NO):";

    	static public final String OPERATOR_TAG = "操作员号(OPERATOR):";

    	static public final String CARD_NUMBER_TAG = "卡号(CARD NUMBER):";

    	static public final String ISS_NO_TAG = "发卡行号(ISS NO):";

    	static public final String ACQ_NO_TAG = "收单行号(ACQ NO):";

    	static public final String TXN_TYPE_TAG = "交易类别:";

    	static public final String EXP_DATE_TAG = "有效期(EXP DATE):";

    	static public final String BATCH_NO_TAG = "批次号(BATCH NO):";

    	static public final String VOUCHER_NO_TAG = "凭证号(VOUCHER NO):";

    	static public final String AUTH_NO_TAG = "授权码(AUTH NO):";

    	static public final String DATE_TIME_TAG = "日期/时间(DATE/TIME):";

    	static public final String REF_NO_TAG = "参考号(REF NO):";

    	static public final String AMOUT_TAG = "金额(AMOUT):";

    	static public final String TIPS_TAG = "小费(TIPS):";

    	static public final String TOTAL_TAG = "总计(TOTAL):";

    	static public final String REFERENCE_TAG = "备注(REFERENCE):";

    	static public final String C_CARD_HOLDER_SIGNATURE_TAG = "持卡人签名:";
    	static public final String E_CARD_HOLDER_SIGNATURE_TAG = "CARD HOLDER SIGNATURE";

    	static public final String C_AGREE_TRADE_TAG = "本人确认以上交易，同意将其记入本卡账户";
    	static public final String E_AGREE_TRADE_TAG = "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOOD";
    }
*/
	static public final class PurchaseBillTag 
    {
    	static public final String PURCHASE_BILL_TITLE = "POS 签 购 单";

    	static public final String MERCHANT_NAME_TAG =   "商户名称:     ";

    	static public final String MERCHANT_NO_TAG =     "商户编号:     ";

    	static public final String TERMINAL_NO_TAG =     "终端编号:     ";

    	static public final String OPERATOR_TAG =        "操作员号:     ";

    	static public final String CARD_NUMBER_TAG =     "基卡卡号:     ";

    	static public final String PREPAY_CARD_NAME =    "预付卡名称:   ";
    	
    	static public final String PREPAY_CARD_NO =      "预付卡号:     ";

    	static public final String TXN_TYPE_TAG =        "交易类别:     ";
    	
    	static public final String COUPON_NO =           "优惠券编号:   ";
    	
    	static public final String PRECARD_EXP_DATE =    "卡有效期:     ";
    	
    	static public final String COUPON_EXP_DATE =     "优惠券有效期: ";
    	
    	static public final String VOUCHER_NO_TAG =      "凭证号:       ";

    	static public final String BATCH_NO_TAG =        "批次号:       ";

    	static public final String DATE_TIME_TAG =       "日期/时间:    ";
    	
    	static public final String AUTH_NO_TAG =         "授权码:       ";

    	static public final String REF_NO_TAG =          "参考号:       ";
    	
    	static public final String COUPON_TYPE =         "优惠券类别:   ";
    	
    	static public final String TRADE_AMOUT =         "抵扣金额:     ";

    	static public final String PREPAY_CARD_DIS =     "支付卡折扣率: ";
    	
    	static public final String DIS_AMOUNT =          "折扣金额:     ";
    	
    	static public final String ACTUAL_AMOUNT =       "支付金额:     ";

    	static public final String REFERENCE_TAG =       "备注:         ";

    	static public final String C_CARD_HOLDER_SIGNATURE_TAG = "持卡人签名:";

    	static public final String C_AGREE_TRADE_TAG = "本人确认以上交易，同意将其记入本卡账户";

    }
	
	public interface SumBillTag {
		public static final String SUM_BILL_TITLE = "类型 笔数 卡交易额 券金额 券张数" ;
	}
}
