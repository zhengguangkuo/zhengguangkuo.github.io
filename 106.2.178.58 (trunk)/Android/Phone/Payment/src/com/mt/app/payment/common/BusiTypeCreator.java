package com.mt.app.payment.common;

import org.apache.log4j.Logger;

public class BusiTypeCreator {
	private static Logger log = Logger.getLogger(BusiTypeCreator.class);

	public static BusiTypeStruct getBusiType(int ibusiType) {
		log.debug("getBusiType start");
		log.debug("ibusiType = " + ibusiType);
		BusiTypeStruct btStruct = new BusiTypeStruct();

		switch (ibusiType) {
		case Constants.USR_REGISTER: {
			btStruct.setDoUrl(Constants.URL_USR_REGISTER);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_USR_REGISTER);
			break;
		}
		case Constants.USR_LOGIN: {
			btStruct.setDoUrl(Constants.URL_USR_LOGIN);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_USR_LOGIN);
			break;
		}
		case Constants.USR_GET_REGTEXT: {
			btStruct.setDoUrl(Constants.URL_GET_REGTEXT);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_GET_REGTEXT);
			break;
		}
		case Constants.USR_QUERY_PACKAGE: {
			btStruct.setDoUrl(Constants.URL_QUERY_PACKAGE);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_QUERY_PACKAGE);
			break;
		}
		case Constants.USR_CARD_APP_BIND: {
			btStruct.setDoUrl(Constants.URL_CARD_APP_BIND);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_CARD_APP_BIND);
			break;
		}
		case Constants.USR_CARD_APP_UNBIND: {
			btStruct.setDoUrl(Constants.URL_CARD_APP_UNBIND);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_CARD_APP_UNBIND);
			break;
		}
		case Constants.USR_BIND_PACKAGE: {
			btStruct.setDoUrl(Constants.URL_BIND_PACKAGE);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_BIND_PACKAGE);
			break;
		}
		case Constants.USR_BASE_CARD_BIND: {
			btStruct.setDoUrl(Constants.URL_BASE_CARD_BIND);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_BASE_CARD_BIND);
			break;
		}
		case Constants.USR_BASE_CARD_UNBIND: {
			btStruct.setDoUrl(Constants.URL_BASE_CARD_UNBIND);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_BASE_CARD_UNBIND);
			break;
		}
		case Constants.USR_CARD_APP_QUERY: {
			btStruct.setDoUrl(Constants.URL_CARD_APP_QUERY);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_CARD_APP_QUERY);
			break;
		}
		case Constants.USR_QUERY_CARD: {
			btStruct.setDoUrl(Constants.URL_QUERY_CARD);
			btStruct.setJosonResultType(Constants.JSONTYPE_ARRAY);
			btStruct.setMessId(Constants.MESSID_QUERY_CARD);
			break;
		}
		case Constants.USR_UPDATE_PASS: {
			btStruct.setDoUrl(Constants.URL_UPDATE_PASS);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_UPDATE_PASS);
			break;
		}
		case Constants.USR_UPDATE_CARD: {
			btStruct.setDoUrl(Constants.URL_UPDATE_CARD);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_UPDATE_CARD);
			break;
		}
		case Constants.USR_GET_VERIFY: {
			btStruct.setDoUrl(Constants.URL_GET_VERIFY);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_GET_VERIFY);
			break;
		}
		case Constants.USR_OBTAIN_DIS_QUERY: {
			btStruct.setDoUrl(Constants.URL_OBTAIN_DIS_QUERY);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_OBTAIN_DIS_QUERY);
			break;
		}
		case Constants.USR_COUPON_LIST: {
			btStruct.setDoUrl(Constants.URL_COUPON_LIST);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_COUPON_LIST);
			break;
		}
		case Constants.USR_COUPON_DETAILS: {
			btStruct.setDoUrl(Constants.URL_COUPON_DETAILS);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_COUPON_DETAILS);
			break;
		}
		case Constants.USR_SALE_LIST: {
			btStruct.setDoUrl(Constants.URL_SALE_LIST);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_SALE_LIST);
			break;
		}
		case Constants.USR_SALE_DETAILS: {
			btStruct.setDoUrl(Constants.URL_SALE_DETAILS);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_SALE_DETAILS);
			break;
		}
		case Constants.USR_APPLY_COUPON: {
			btStruct.setDoUrl(Constants.URL_APPLY_COUPON);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_APPLY_COUPON);
			break;
		}
		case Constants.USR_QUERY_PONIT: {
			btStruct.setDoUrl(Constants.URL_QUERY_PONIT);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_QUERY_PONIT);
			break;
		}
		case Constants.USR_CARD_APP_BIND_QUERY: {
			btStruct.setDoUrl(Constants.URL_CARD_APP_BIND_QUERY);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_CARD_APP_BIND_QUERY);
			break;
		}
		case Constants.USR_APP_BIND_DETAIL_QUERY: {
			btStruct.setDoUrl(Constants.URL_APP_BIND_DETAIL_QUERY);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_APP_BIND_DETAIL_QUERY);
			break;
		}
		case Constants.USR_INFO_QUERY: {
			btStruct.setDoUrl(Constants.URL_INFO_QUERY);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_INFO_QUERY);
			break;
		}
		case Constants.USR_COUPON_DETAILS_HAVE: {
			btStruct.setDoUrl(Constants.URL_COUPON_DETAILS_HAVE);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_COUPON_DETAILS_HAVE);
			break;
		}
		case Constants.USR_USER_INFO_UPDATE: {
			btStruct.setDoUrl(Constants.URL_USER_INFO_UPDATE);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_USER_INFO_UPDATE);
			break;
		}
		case Constants.USR_NEAR_MERCHANT: {
			btStruct.setDoUrl(Constants.URL_NEAR_MERCHANT);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_NEAR_MERCHANT);
			break;
		}
		case Constants.USR_MERCHANT_COUPON_LOGIN: {
			btStruct.setDoUrl(Constants.URL_MERCHANT_COUPON_LOGIN);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_MERCHANT_COUPON_LOGIN);
			break;
		}
		case Constants.USR_MERCHANT_COUPON_NOLOGIN: {
			btStruct.setDoUrl(Constants.URL_MERCHANT_COUPON_NOLOGIN);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_MERCHANT_COUPON_NOLOGIN);
			break;
		}
		case Constants.USR_MERCHANT_DIS: {
			btStruct.setDoUrl(Constants.URL_MERCHANT_DIS);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_MERCHANT_DIS);
			break;
		}
		case Constants.USR_MERCHANT_LSIT_CARE: {
			btStruct.setDoUrl(Constants.URL_MERCHANT_LSIT_CARE);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_MERCHANT_LSIT_CARE);
			break;
		}
		case Constants.USR_MERCHANT_OPT_CARE: {
			btStruct.setDoUrl(Constants.URL_MERCHANT_OPT_CARE);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_MERCHANT_OPT_CARE);
			break;
		}
		case Constants.USR_SUBMIT_VERIFY: {
			btStruct.setDoUrl(Constants.URL_SUBMIT_VERIFY);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_SUBMIT_VERIFY);
			break;
		}
		case Constants.USR_COUPONS_SEARCH: {
			btStruct.setDoUrl(Constants.URL_COUPONS_SEARCH);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_COUPONS_SEARCH);
			break;
		}
		case Constants.USR_MERCH_SRARCH: {
			btStruct.setDoUrl(Constants.URL_MERCH_SRARCH);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_MERCH_SRARCH);
			break;
		}
		case Constants.USR_APP_UPDATE: {
			btStruct.setDoUrl(Constants.URL_APP_UPDATE);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_APP_UPDATE);
			break;
		}
		case Constants.USR_FORGOTPWD: {
			btStruct.setDoUrl(Constants.URL_FORGOTPWD);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_FORGOTPWD);
			break;
		}
		case Constants.USR_AD: {
			btStruct.setDoUrl(Constants.URL_AD);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_AD);
			break;
		}
		case Constants.USR_COUPONR_RETURN: {
			btStruct.setDoUrl(Constants.URL_COUPONR_RETURN);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_COUPONR_RETURN);
			break;
		}
		case Constants.USR_MERCHANT_DETAILS: {
			btStruct.setDoUrl(Constants.URL_MERCHANT_DETAILS);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_MERCHANT_DETAILS);
			break;
		}
		case Constants.USR_APP_DEFAULT_SET : {
			btStruct.setDoUrl(Constants.URL_APP_DEFAULT_SET);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_APP_DEFAULT_SET);
			break;
		}
		case Constants.USR_CHANGEPHONE : {
			btStruct.setDoUrl(Constants.URL_CHANGEPHONE);
			btStruct.setJosonResultType(Constants.JSONTYPE_OBJECT);
			btStruct.setMessId(Constants.MESSID_CHANGEPHONE);
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
