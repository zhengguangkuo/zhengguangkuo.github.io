package com.wizarpos.mt;

public class PrintTag 
{

    /**
     * ǩ������ǩ
     *//*
    static public final class PurchaseBillTag 
    {
    	static public final String PURCHASE_BILL_TITLE = "POS ǩ �� ��";

    	static public final String MERCHANT_NAME_TAG = "�̻�����(MERCHANT NAME):";

    	static public final String MERCHANT_NO_TAG = "�̻����(MERCHANT NO):";

    	static public final String TERMINAL_NO_TAG = "�ն˱��(TERMINAL NO):";

    	static public final String OPERATOR_TAG = "����Ա��(OPERATOR):";

    	static public final String CARD_NUMBER_TAG = "����(CARD NUMBER):";

    	static public final String ISS_NO_TAG = "�����к�(ISS NO):";

    	static public final String ACQ_NO_TAG = "�յ��к�(ACQ NO):";

    	static public final String TXN_TYPE_TAG = "�������:";

    	static public final String EXP_DATE_TAG = "��Ч��(EXP DATE):";

    	static public final String BATCH_NO_TAG = "���κ�(BATCH NO):";

    	static public final String VOUCHER_NO_TAG = "ƾ֤��(VOUCHER NO):";

    	static public final String AUTH_NO_TAG = "��Ȩ��(AUTH NO):";

    	static public final String DATE_TIME_TAG = "����/ʱ��(DATE/TIME):";

    	static public final String REF_NO_TAG = "�ο���(REF NO):";

    	static public final String AMOUT_TAG = "���(AMOUT):";

    	static public final String TIPS_TAG = "С��(TIPS):";

    	static public final String TOTAL_TAG = "�ܼ�(TOTAL):";

    	static public final String REFERENCE_TAG = "��ע(REFERENCE):";

    	static public final String C_CARD_HOLDER_SIGNATURE_TAG = "�ֿ���ǩ��:";
    	static public final String E_CARD_HOLDER_SIGNATURE_TAG = "CARD HOLDER SIGNATURE";

    	static public final String C_AGREE_TRADE_TAG = "����ȷ�����Ͻ��ף�ͬ�⽫����뱾���˻�";
    	static public final String E_AGREE_TRADE_TAG = "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOOD";
    }
*/
	static public final class PurchaseBillTag 
    {
    	static public final String PURCHASE_BILL_TITLE = "POS ǩ �� ��";

    	static public final String MERCHANT_NAME_TAG =   "�̻�����:     ";

    	static public final String MERCHANT_NO_TAG =     "�̻����:     ";

    	static public final String TERMINAL_NO_TAG =     "�ն˱��:     ";

    	static public final String OPERATOR_TAG =        "����Ա��:     ";

    	static public final String CARD_NUMBER_TAG =     "��������:     ";

    	static public final String PREPAY_CARD_NAME =    "Ԥ��������:   ";
    	
    	static public final String PREPAY_CARD_NO =      "Ԥ������:     ";

    	static public final String TXN_TYPE_TAG =        "�������:     ";
    	
    	static public final String COUPON_NO =           "�Ż�ȯ���:   ";
    	
    	static public final String PRECARD_EXP_DATE =    "����Ч��:     ";
    	
    	static public final String COUPON_EXP_DATE =     "�Ż�ȯ��Ч��: ";
    	
    	static public final String VOUCHER_NO_TAG =      "ƾ֤��:       ";

    	static public final String BATCH_NO_TAG =        "���κ�:       ";

    	static public final String DATE_TIME_TAG =       "����/ʱ��:    ";
    	
    	static public final String AUTH_NO_TAG =         "��Ȩ��:       ";

    	static public final String REF_NO_TAG =          "�ο���:       ";
    	
    	static public final String COUPON_TYPE =         "�Ż�ȯ���:   ";
    	
    	static public final String TRADE_AMOUT =         "�ֿ۽��:     ";

    	static public final String PREPAY_CARD_DIS =     "֧�����ۿ���: ";
    	
    	static public final String DIS_AMOUNT =          "�ۿ۽��:     ";
    	
    	static public final String ACTUAL_AMOUNT =       "֧�����:     ";

    	static public final String REFERENCE_TAG =       "��ע:         ";

    	static public final String C_CARD_HOLDER_SIGNATURE_TAG = "�ֿ���ǩ��:";

    	static public final String C_AGREE_TRADE_TAG = "����ȷ�����Ͻ��ף�ͬ�⽫����뱾���˻�";

    }
	
	public interface SumBillTag {
		public static final String SUM_BILL_TITLE = "���� ���� �����׶� ȯ��� ȯ����" ;
	}
}
