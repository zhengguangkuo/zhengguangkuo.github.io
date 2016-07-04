package com.mt.app.padpayment.driver;

import com.wizarpos.apidemo.printer.PrinterException;
import com.wizarpos.mt.PurchaseBill;

public abstract class PrinterDriver {
	
	/**
	 * ¥Ú”°«©π∫µ•
	 * @throws PrinterException 
	 */
	public abstract void printerPurchaseBill(PurchaseBill purchaseBill) throws PrinterException ;
	
}
