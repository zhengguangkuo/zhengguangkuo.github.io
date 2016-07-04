package com.wizarpos.mt;

import java.io.UnsupportedEncodingException;
import java.util.List;

import android.R.integer;
import android.util.Log;

import com.mt.app.padpayment.tools.MoneyUtil;
import com.wizarpos.apidemo.jniinterface.PrinterInterface;
import com.wizarpos.apidemo.printer.PrinterCommand;
import com.wizarpos.apidemo.printer.PrinterException;
import com.wizarpos.mt.PurchaseBill;

public class PrinterHelper 
{
	private static int high = 0;
	
	static long time = 0;
	
	/* �ȴ���ӡ����ˢ�µ�ʱ�� */
    private static final int PRINTER_BUFFER_FLUSH_WAITTIME = /*300*/150;

    private static PrinterHelper _instance;

    static {
  		System.loadLibrary("wizarpos_printer");
  	}
    
    private PrinterHelper() 
    {
    }

  
    
    synchronized public static PrinterHelper getInstance() 
    {
		if (null == _instance)
    		_instance = new PrinterHelper();
		return _instance;
    }

    /**
     * ��ӡǩ����
     * 
     * @throws PrinterException
     *//*
    synchronized public void printerPurchaseBill(PurchaseBill purchaseBill) throws PrinterException 
    {
    	int nTotal = 0;
		Log.i("APP","------------------printerPurchaseBill()------------------");
		try 
		{
   			PrinterInterface.PrinterOpen();
   			PrinterInterface.PrinterBegin();

   			if (!purchaseBill.checkValidity()) 
   			{
				throw new IllegalArgumentException("purchase bill check validity fail");
   			}

   			
		    ------------------------------------TOP------------------------------------
		     ��ʼ����ӡ��,�����ӡ�������� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEsc_(), PrinterCommand.getCmdEsc_().length);
		     ��ǰ��ֽ2�� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(2), PrinterCommand.getCmdEscDN(2).length);
		     ���ж��� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscAN(1), PrinterCommand.getCmdEscAN(1).length);
		     ����˫����ȣ�˫���߶ȣ��Ӵ� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEsc_N(Integer.parseInt("0111000", 2)),
			PrinterCommand.getCmdEsc_N(Integer.parseInt("0111000", 2)).length);
		    byte[] purchaseBillTitle = PrintTag.PurchaseBillTag.PURCHASE_BILL_TITLE.getBytes("GB2312");
		    nTotal +=PrinterInterface.PrinterWrite(purchaseBillTitle, purchaseBillTitle.length);
	
		     ��ǰ��ֽ2�� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(2), PrinterCommand.getCmdEscDN(2).length);
		     ��������� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscAN(0), PrinterCommand.getCmdEscAN(0).length);
		     �ָ�Ĭ������ 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEsc_N(Integer.parseInt("0000000", 2)),
			PrinterCommand.getCmdEsc_N(Integer.parseInt("0000000", 2)).length);
		    byte[] merchantNameTag = PrintTag.PurchaseBillTag.MERCHANT_NAME_TAG.getBytes("GB2312");
		    nTotal +=PrinterInterface.PrinterWrite(merchantNameTag, merchantNameTag.length);
	
		     ���� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		     ����Ӵ� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscEN(1), PrinterCommand.getCmdEscEN(1).length);
		    byte[] merchantNameValue = purchaseBill.getMerchantName().getBytes("GB2312");
		    nTotal +=PrinterInterface.PrinterWrite(merchantNameValue, merchantNameValue.length);
	
		     ���� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		     �ָ������ϸ 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscEN(0), PrinterCommand.getCmdEscEN(0).length);
		    byte[] merchantNoTag = PrintTag.PurchaseBillTag.MERCHANT_NO_TAG.getBytes("GB2312");
		    nTotal +=PrinterInterface.PrinterWrite(merchantNoTag, merchantNoTag.length);
	
		     ���� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] merchantNoValue = purchaseBill.getMerchantNo().getBytes();
		    nTotal +=PrinterInterface.PrinterWrite(merchantNoValue, merchantNoValue.length);
	
		     ���� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] terminalNoTag = PrintTag.PurchaseBillTag.TERMINAL_NO_TAG.getBytes("GB2312");
		    nTotal +=PrinterInterface.PrinterWrite(terminalNoTag, terminalNoTag.length);
	
		     ���� 
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] terminalValue = purchaseBill.getTerminalNo().getBytes();
		    nTotal +=PrinterInterface.PrinterWrite(terminalValue, terminalValue.length);
	
		     �ȴ���ӡbuffer flush 
		    Thread.sleep(PRINTER_BUFFER_FLUSH_WAITTIME);
		    Log.i("APP", String.format("nTotal = %d\n", nTotal));
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] operatorTagValue = (PrintTag.PurchaseBillTag.OPERATOR_TAG + purchaseBill.getOperator()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(operatorTagValue, operatorTagValue.length);
	
		    ------------------------------------MIDDLE------------------------------------
	
		     ��ǰ��ֽ2�� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(2), PrinterCommand.getCmdEscDN(2).length);
		    byte[] issNoTagValue = (PrintTag.PurchaseBillTag.ISS_NO_TAG + purchaseBill.getIssNo()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(issNoTagValue, issNoTagValue.length);
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] acqNoTagValue = (PrintTag.PurchaseBillTag.ACQ_NO_TAG + purchaseBill.getAcqNo()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(acqNoTagValue, acqNoTagValue.length);
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] cardNumberTag = PrintTag.PurchaseBillTag.CARD_NUMBER_TAG.getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(cardNumberTag, cardNumberTag.length);
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] cardNumberValue = purchaseBill.getCardNumber().getBytes();
		    nTotal += PrinterInterface.PrinterWrite(cardNumberValue, cardNumberValue.length);
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] txnTypeTagValue = (PrintTag.PurchaseBillTag.TXN_TYPE_TAG + purchaseBill.getTxnType()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(txnTypeTagValue, txnTypeTagValue.length);
	
		    if (!StringUtility.isEmpty(purchaseBill.getExpDate())) 
		    {
				 ���� 
		    		nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
					byte[] expDateTagValue = (PrintTag.PurchaseBillTag.EXP_DATE_TAG + purchaseBill.getExpDate()).getBytes("GB2312");
					nTotal += PrinterInterface.PrinterWrite(expDateTagValue, expDateTagValue.length);
		    }
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] batchNoTagValue = (PrintTag.PurchaseBillTag.BATCH_NO_TAG + purchaseBill.getBatchNo()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(batchNoTagValue, batchNoTagValue.length);
	
		     �ȴ���ӡbuffer flush 
		    Thread.sleep(PRINTER_BUFFER_FLUSH_WAITTIME);
		    Log.i("APP", String.format("nTotal = %d\n", nTotal));
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] voucherNoTagValue = (PrintTag.PurchaseBillTag.VOUCHER_NO_TAG + purchaseBill.getVoucherNo()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(voucherNoTagValue, voucherNoTagValue.length);
	
		    if (!StringUtility.isEmpty(purchaseBill.getAuthNo())) 
		    {
				 ���� 
		    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
				byte[] authNoTagValue = (PrintTag.PurchaseBillTag.AUTH_NO_TAG + purchaseBill.getAuthNo()).getBytes("GB2312");
				nTotal += PrinterInterface.PrinterWrite(authNoTagValue, authNoTagValue.length);
		    }
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] refNoTag = PrintTag.PurchaseBillTag.REF_NO_TAG.getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(refNoTag, refNoTag.length);
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] refNoValue = purchaseBill.getRefNo().getBytes();
		    nTotal += PrinterInterface.PrinterWrite(refNoValue, refNoValue.length);
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] dateTimeTag = PrintTag.PurchaseBillTag.DATE_TIME_TAG.getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(dateTimeTag, dateTimeTag.length);
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] dateTimeValue = purchaseBill.getDataTime().getBytes();
		    nTotal += PrinterInterface.PrinterWrite(dateTimeValue, dateTimeValue.length);
	
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		     ����Ӵ� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscEN(1), PrinterCommand.getCmdEscEN(1).length);
		    byte[] amoutTag = PrintTag.PurchaseBillTag.AMOUT_TAG.getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(amoutTag, amoutTag.length);
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] amoutValue = purchaseBill.getAmout().getBytes();
		    nTotal += PrinterInterface.PrinterWrite(amoutValue, amoutValue.length);
	
		    if (!StringUtility.isEmpty(purchaseBill.getTips())) 
		    {
				 ���� 
		    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
				byte[] tipsTag = PrintTag.PurchaseBillTag.TIPS_TAG.getBytes("GB2312");
				nTotal += PrinterInterface.PrinterWrite(tipsTag, tipsTag.length);
				 ���� 
				nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
				byte[] tipsValue = purchaseBill.getTips().getBytes();
				nTotal += PrinterInterface.PrinterWrite(tipsValue, tipsValue.length);
		    }
	
		     �ȴ���ӡbuffer flush 
		    Thread.sleep(PRINTER_BUFFER_FLUSH_WAITTIME);
		    Log.i("APP", String.format("nTotal = %d\n", nTotal));
	
		    if (!StringUtility.isEmpty(purchaseBill.getTotal())) 
		    {
		
				 ���� 
		    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
				byte[] totalTag = PrintTag.PurchaseBillTag.TOTAL_TAG.getBytes("GB2312");
				nTotal += PrinterInterface.PrinterWrite(totalTag, totalTag.length);
				 ���� 
				nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
				byte[] totalValue = purchaseBill.getTotal().getBytes();
				nTotal += PrinterInterface.PrinterWrite(totalValue, totalValue.length);
		    }
	
		    ------------------------------------BOTTOM------------------------------------
	
		     �ָ������ϸ 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscEN(0), PrinterCommand.getCmdEscEN(0).length);
		    if (!StringUtility.isEmpty(purchaseBill.getReference())) 
		    {
		
				 ��ǰ��ֽ2�� 
		    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(2), PrinterCommand.getCmdEscDN(2).length);
				byte[] referenceTag = PrintTag.PurchaseBillTag.REFERENCE_TAG.getBytes("GB2312");
				nTotal += PrinterInterface.PrinterWrite(referenceTag, referenceTag.length);
				 ���� 
				nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
				byte[] referenceValue = purchaseBill.getReference().getBytes("GB2312");
				nTotal += PrinterInterface.PrinterWrite(referenceValue, referenceValue.length);
		    }
	
		     ��ǰ��ֽ2�� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(2), PrinterCommand.getCmdEscDN(2).length);
		    byte[] cCardHolderSignatureTAG = PrintTag.PurchaseBillTag.C_CARD_HOLDER_SIGNATURE_TAG.getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(cCardHolderSignatureTAG, cCardHolderSignatureTAG.length);
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] eCardHolderSignatureTAG = PrintTag.PurchaseBillTag.E_CARD_HOLDER_SIGNATURE_TAG.getBytes();
		    nTotal += PrinterInterface.PrinterWrite(eCardHolderSignatureTAG, eCardHolderSignatureTAG.length);
		     ��ǰ��ֽ4�� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(4), PrinterCommand.getCmdEscDN(4).length);
		    byte[] cAgreeTradeTag = PrintTag.PurchaseBillTag.C_AGREE_TRADE_TAG.getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(cAgreeTradeTag, cAgreeTradeTag.length);
		     ���� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] eAgreeTradeTag = PrintTag.PurchaseBillTag.E_AGREE_TRADE_TAG.getBytes();
		    nTotal += PrinterInterface.PrinterWrite(eAgreeTradeTag, eAgreeTradeTag.length);
		     ��ǰ��ֽ2�� 
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(2), PrinterCommand.getCmdEscDN(2).length);
		    Log.i("APP", "end of the printing action!\n");
		    Log.i("APP", String.format("nTotal = %d\n", nTotal));
		    
		    PrinterInterface.PrinterEnd();
		    PrinterInterface.PrinterClose();

	} catch (UnsupportedEncodingException e) 
	{
	    throw new PrinterException("PrinterHelper.printerPurchaseBill():" + e.getMessage(), e);
	} 
	catch (InterruptedException e) 
	{
	    throw new PrinterException("PrinterHelper.printerPurchaseBill():" + e.getMessage(), e);
	}catch (IllegalArgumentException e) 
	{
	    throw new PrinterException(e.getMessage(), e);
	} finally 
	{
	    PrinterInterface.PrinterEnd();
	    PrinterInterface.PrinterClose();
	}
    }*/
  
    /**
     * ��ӡǩ����
     * 
     * @throws PrinterException
     */
    synchronized public void printerPurchaseBill(PurchaseBill purchaseBill) throws PrinterException 
    {
		if (time == 0) {					
			time =System.currentTimeMillis();
		} else {
			if (System.currentTimeMillis() - time < 16000) {
				return ;
			}
		}
		time =System.currentTimeMillis();
    	
    	int nTotal = 0;
		Log.i("APP","------------------printerPurchaseBill()------------------");
		try 
		{
   			int iret = PrinterInterface.PrinterOpen();
   			PrinterInterface.PrinterBegin();

   			if (!purchaseBill.checkValidity()) 
   			{
				throw new IllegalArgumentException("purchase bill check validity fail");
   			}

   			
		    /*------------------------------------TOP------------------------------------*/
		    /* ��ʼ����ӡ�� */
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEsc_(), PrinterCommand.getCmdEsc_().length);
		    /* ��ǰ��ֽ2�� */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(2), PrinterCommand.getCmdEscDN(2).length);
		    /* ���ж��� */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscAN(1), PrinterCommand.getCmdEscAN(1).length);
		    /* ����˫����ȣ�˫���߶ȣ��Ӵ� */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEsc_N(Integer.parseInt("0111000", 2)),
			PrinterCommand.getCmdEsc_N(Integer.parseInt("0111000", 2)).length);
		    byte[] purchaseBillTitle = PrintTag.PurchaseBillTag.PURCHASE_BILL_TITLE.getBytes("GB2312");
		    nTotal +=PrinterInterface.PrinterWrite(purchaseBillTitle, purchaseBillTitle.length);
	
		    /* ��ǰ��ֽ2�� */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(2), PrinterCommand.getCmdEscDN(2).length);
		    /* ��������� */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscAN(0), PrinterCommand.getCmdEscAN(0).length);
		    /* �ָ�Ĭ������ */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEsc_N(Integer.parseInt("0000000", 2)),
			PrinterCommand.getCmdEsc_N(Integer.parseInt("0000000", 2)).length);
		    byte[] merchantNameTag = (PrintTag.PurchaseBillTag.MERCHANT_NAME_TAG+purchaseBill.getMerchantName()).getBytes("GB2312");
		    nTotal +=PrinterInterface.PrinterWrite(merchantNameTag, merchantNameTag.length);
	
		    /* ���� */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    /* �ָ������ϸ */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscEN(0), PrinterCommand.getCmdEscEN(0).length);
		    byte[] merchantNoTag = (PrintTag.PurchaseBillTag.MERCHANT_NO_TAG + purchaseBill.getMerchantNo()).getBytes("GB2312");
		    nTotal +=PrinterInterface.PrinterWrite(merchantNoTag, merchantNoTag.length);
	
		    /* ���� */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] terminalNoTag = (PrintTag.PurchaseBillTag.TERMINAL_NO_TAG + purchaseBill.getTerminalNo()).getBytes("GB2312");
		    nTotal +=PrinterInterface.PrinterWrite(terminalNoTag, terminalNoTag.length);
		    /* ���� */
		    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] operatorTagValue = (PrintTag.PurchaseBillTag.OPERATOR_TAG + purchaseBill.getOperator()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(operatorTagValue, operatorTagValue.length);
		    
		    /*���� */
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] issNoTagValue = (PrintTag.PurchaseBillTag.CARD_NUMBER_TAG + purchaseBill.getBaseCardNumber()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(issNoTagValue, issNoTagValue.length);
		    
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] line = ("-----------------------------").getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(line, line.length);
		  	    
		    /* �ȴ���ӡbuffer flush */
		    Thread.sleep(PRINTER_BUFFER_FLUSH_WAITTIME);
		    Log.i("APP", String.format("nTotal = %d\n", nTotal));
	

	
		    /*------------------------------------MIDDLE------------------------------------*/
	

	
		    /* ���� */
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] prepayCardName = (PrintTag.PurchaseBillTag.PREPAY_CARD_NAME + purchaseBill.getPrepayCardName()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(prepayCardName, prepayCardName.length);
	
		    /* ���� */
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] txnTypeTag = (PrintTag.PurchaseBillTag.TXN_TYPE_TAG + purchaseBill.getTxnType()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(txnTypeTag, txnTypeTag.length);
	
	
		    /* ���� */
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] prepayValue = (PrintTag.PurchaseBillTag.PREPAY_CARD_NO + purchaseBill.getPrepayCardNo()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(prepayValue, prepayValue.length);
		    
		    /* ���� */
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] couponNo = (PrintTag.PurchaseBillTag.COUPON_NO + purchaseBill.getCouponNo()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(couponNo, couponNo.length);
		    
	
		    /* ���� */
    		nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] expDateTagValue = (PrintTag.PurchaseBillTag.PRECARD_EXP_DATE + purchaseBill.getPrepayCardExpdate()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(expDateTagValue, expDateTagValue.length);
	
		    /* ���� */
    		nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] couponExpdate = (PrintTag.PurchaseBillTag.COUPON_EXP_DATE + purchaseBill.getCouponExpdate()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(couponExpdate, couponExpdate.length);
		    
		    /* ���� */
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] voucherNo = (PrintTag.PurchaseBillTag.VOUCHER_NO_TAG + purchaseBill.getVoucherNo()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(voucherNo, voucherNo.length);
		    
		    /* ���� */
		    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
		    byte[] batchNoTagValue = (PrintTag.PurchaseBillTag.BATCH_NO_TAG + purchaseBill.getBatchNo()).getBytes("GB2312");
		    nTotal += PrinterInterface.PrinterWrite(batchNoTagValue, batchNoTagValue.length);
	
		    
		    /* �ȴ���ӡbuffer flush */
		    Thread.sleep(PRINTER_BUFFER_FLUSH_WAITTIME);
		    Log.i("APP", String.format("nTotal = %d\n", nTotal));
	
		    /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] dateTime = (PrintTag.PurchaseBillTag.DATE_TIME_TAG + purchaseBill.getDataTime()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(dateTime, dateTime.length);
			
			  /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] authNo = (PrintTag.PurchaseBillTag.AUTH_NO_TAG + purchaseBill.getAuthNo()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(authNo, authNo.length);
			
			  /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] refNo = (PrintTag.PurchaseBillTag.REF_NO_TAG + purchaseBill.getRefNo()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(refNo, refNo.length);
			
			  /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] couponType = (PrintTag.PurchaseBillTag.COUPON_TYPE + purchaseBill.getCouponType()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(couponType, couponType.length);
			
			  /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] couponDeduce = (PrintTag.PurchaseBillTag.TRADE_AMOUT + purchaseBill.getCouponDeduce()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(couponDeduce, couponDeduce.length);
			
			 /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] prepayDis = (PrintTag.PurchaseBillTag.PREPAY_CARD_DIS + purchaseBill.getPrepayCardDis()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(prepayDis, prepayDis.length);
			
			 /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] disAmount = (PrintTag.PurchaseBillTag.DIS_AMOUNT + purchaseBill.getDisAmount()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(disAmount, disAmount.length);
			
			 /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] actualAmount = (PrintTag.PurchaseBillTag.ACTUAL_AMOUNT + purchaseBill.getActuAmount()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(actualAmount, actualAmount.length);
			
			//��ӡֱ��
			nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			nTotal += PrinterInterface.PrinterWrite(line, line.length);
			
			 /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] reference = (PrintTag.PurchaseBillTag.REFERENCE_TAG + purchaseBill.getReference()).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(reference, reference.length);
			nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(3), PrinterCommand.getCmdEscDN(3).length);
			
			//��ӡֱ��
			nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			nTotal += PrinterInterface.PrinterWrite(line, line.length);
			
			
			 /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] cardHolder = (PrintTag.PurchaseBillTag.C_CARD_HOLDER_SIGNATURE_TAG).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(cardHolder, cardHolder.length);
			nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(1), PrinterCommand.getCmdEscDN(1).length);
			 /* ���� */
	    	nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
			byte[] agreeTrade = (PrintTag.PurchaseBillTag.C_AGREE_TRADE_TAG).getBytes("GB2312");
			nTotal += PrinterInterface.PrinterWrite(agreeTrade, agreeTrade.length);
			nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(5), PrinterCommand.getCmdEscDN(5).length);
            
		    Log.i("APP", "end of the printing action!\n");
		    Log.i("APP", String.format("nTotal = %d\n", nTotal));
		    
		    PrinterInterface.PrinterEnd();
		    PrinterInterface.PrinterClose();

	} catch (UnsupportedEncodingException e) 
	{
	    throw new PrinterException("PrinterHelper.printerPurchaseBill():" + e.getMessage(), e);
	} 
	catch (InterruptedException e) 
	{
	    throw new PrinterException("PrinterHelper.printerPurchaseBill():" + e.getMessage(), e);
	}catch (IllegalArgumentException e) 
	{
	    throw new PrinterException(e.getMessage(), e);
	} finally 
	{
	    PrinterInterface.PrinterEnd();
	    PrinterInterface.PrinterClose();
	}
    }
    
    
	/**
	 * ��ӡ���׻���
	 * 
	 * @throws PrinterException
	 */
	public static void printerSumBill(final List<SumBill> list) {
		
		if (time == 0) {					
			time =System.currentTimeMillis();
		} else {
			if (System.currentTimeMillis() - time < 16000) {
				return ;
			}
		}
		time =System.currentTimeMillis();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.i("APP", "------------------printerSumBill()------------------");
				int nTotal = 0;
				try 
				{
		   			int iret = PrinterInterface.PrinterOpen();
		   			int result = PrinterInterface.PrinterBegin();
		   			Log.i("---->", "PrinterInterface.PrinterBegin() == " + result) ;

				    /* ��ʼ����ӡ�� */
				    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdEsc_(), PrinterCommand.getCmdEsc_().length);
				    /* ��ǰ��ֽ2�� */
				    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscDN(2), PrinterCommand.getCmdEscDN(2).length);
				    /* ���ж��� */
//				    nTotal +=PrinterInterface.PrinterWrite(PrinterCommand.getCmdEscAN(1), PrinterCommand.getCmdEscAN(1).length);
				    nTotal += PrinterInterface.PrinterWrite(PrintTag.SumBillTag.SUM_BILL_TITLE.getBytes("GB2312"), PrintTag.SumBillTag.SUM_BILL_TITLE.length()+15) ;
				    nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
				    for (SumBill bill : list) {
						/* ���� */
						String type = bill.getType();
						String number = fillField(bill.getNumber(), 3, true, "  ");
						String amount = fillField(MoneyUtil.getMoney(bill.getAmount()), 5, true, "  ");
						String couponsAmount = fillField(MoneyUtil.getMoney(bill.getCouponsAmount()), 6, true, "  ");
						String couponsNum = fillField(bill.getCouponsNum(), 5, true, "  ");
						
						byte[] re = (type + number + amount + couponsAmount + couponsNum).getBytes("GB2312");
						nTotal += PrinterInterface.PrinterWrite(re, re.length);
						nTotal += PrinterInterface.PrinterWrite(PrinterCommand.getCmdLf(), PrinterCommand.getCmdLf().length);
				    }
				
				PrinterInterface.PrinterEnd();
			    PrinterInterface.PrinterClose();					
			    Log.i("---->", "PrinterInterface.PrinterClose()") ;
			} catch (Exception e) {
			    try {
					throw new PrinterException(e.getMessage(), e);
				} catch (PrinterException e1) {
					e1.printStackTrace();
				} finally {
				    PrinterInterface.PrinterEnd();
				    PrinterInterface.PrinterClose();
				}
			} finally {
			    PrinterInterface.PrinterEnd();
			    PrinterInterface.PrinterClose();
			}
				
				
				
//				PrinterManager printer = new PrinterManager();
//
//				int oResu = printer.prn_open();
//
//				if (oResu == 0) {
//					int sResu = printer.prn_setupPage(-1, -1);
//					if (sResu == 0) {
//						resetHigh();
//						/*------------------------------------TOP------------------------------------*/
//
//						/* ��ǰ��ֽ2�� ��ӡ */
//						printer.prn_drawTextEx(PrintTag.SumBillTag.SUM_BILL_TITLE, 0,
//								getHigh(40), -1, -1, "arial", 23, 0, 0, 0);
//
//						for (SumBill bill : list) {
//							/* ���� */
//							String type = bill.getType();
//							String number = fillField(bill.getNumber(), 5, true, "  ");
//							String amount = fillField(MoneyUtil.getMoney(bill.getAmount()), 10,
//									true, "  ");
//							String couponsAmount = fillField(
//									MoneyUtil.getMoney(bill.getCouponsAmount()), 7, true, "  ");
//							String couponsNum = fillField(bill.getCouponsNum(), 6, true, "  ");
//							printer.prn_drawTextEx(type + number + amount + couponsAmount
//									+ couponsNum, 0, getHigh(35), -1, -1, "arial", 23, 0, 0, 0);
//						}
//
//						printer.prn_drawTextEx(" ", 0, getHigh(160), -1, -1, "arial", 23, 0, 0,
//								0);
//
//						Log.i("APP", "end of the printing action!\n");
//
//						printer.prn_printPage(0);
//						
//						printer.prn_close();
//					}
//				}
				
			}
		}).start();
		
	}
	
	public static String fillField(String field, int length, boolean isLeft,
			String fillString) {

		if (field != null && field.length() > 0) {

			int fLength = field.length();
			if (fLength < length) {
				String str = "";
				for (int i = 0; i < length - fLength; i++) { // ��Ҫ����λ��
					str += fillString;
				}
				if (isLeft) {
					return str + field;
				}
				return field + str;
			}
		} else {
			field = null;
		}
		return field;
	}

	private static void resetHigh() {
		high = 0;
	}

	private static int getHigh(int addNum) {
		high += addNum;
		return high;
	}
}
