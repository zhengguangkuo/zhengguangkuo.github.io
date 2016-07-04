package com.mt.app.padpayment.common;

import org.apache.log4j.Logger;

public class BusiTypeCreator {
	private static Logger log = Logger.getLogger(BusiTypeCreator.class);

	public static BusiTypeStruct getBusiType(int ibusiType) {
		log.debug("getBusiType start");
		log.debug("ibusiType = " + ibusiType);
		BusiTypeStruct btStruct = new BusiTypeStruct();

		switch (ibusiType) {
		case Constants.USR_COUPONS: {
			btStruct.setDoUrl(Constants.URL_USR_COUPONS);
			btStruct.setJosonResultType(Constants.JSONTYPE_ARRAY);
			btStruct.setMessId(Constants.MESSID_USR_COUPONS);
			break;
		}
		case Constants.USR_COUPONS_APPLY: {
			btStruct.setDoUrl(Constants.URL_USR_COUPONS_APPLY);
			btStruct.setJosonResultType(Constants.JSONTYPE_ARRAY);
			btStruct.setMessId(Constants.MESSID_USR_COUPONS_APPLY);
			break;
		}
		case Constants.USR_PAY_APP: {
			btStruct.setDoUrl(Constants.URL_USR_PAY_APP);
			btStruct.setJosonResultType(Constants.JSONTYPE_ARRAY);
			btStruct.setMessId(Constants.MESSID_USR_PAY_APP);
			break;
		}
		case Constants.USR_COUPONS_APP: {
			btStruct.setDoUrl(Constants.URL_USR_COUPONS_APP);
			btStruct.setJosonResultType(Constants.JSONTYPE_ARRAY);
			btStruct.setMessId(Constants.MESSID_USR_COUPONS_APP);
			break;
		}
		case Constants.USR_CREDITS: {
			btStruct.setDoUrl(Constants.URL_USR_CREDITS);
			btStruct.setJosonResultType(Constants.JSONTYPE_ARRAY);
			btStruct.setMessId(Constants.MESSID_USR_CREDITS);
			break;
		}		
		case Constants.USR_REGISTER: {
			btStruct.setDoUrl(Constants.URL_USR_REGISTER);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_USR_REGISTER);
			break;
		}
		default: {
			break;
		}
		}
		log.debug("getBusiType end");
		return btStruct;
	}
}
