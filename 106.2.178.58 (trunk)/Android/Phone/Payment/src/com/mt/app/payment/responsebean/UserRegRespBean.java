package com.mt.app.payment.responsebean;

import com.mt.android.sys.bean.base.ResponseBean;

public class UserRegRespBean extends ResponseBean{
	private String busiInfo;// 0 ������֤��ķ��� 1 ��֤��Ϣ�ķ���  

	public String getBusiInfo() {
		return busiInfo;
	}

	public void setBusiInfo(String busiInfo) {
		this.busiInfo = busiInfo;
	}

}
