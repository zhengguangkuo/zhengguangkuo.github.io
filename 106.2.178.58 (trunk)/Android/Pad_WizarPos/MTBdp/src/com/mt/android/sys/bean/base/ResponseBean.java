package com.mt.android.sys.bean.base;

import java.io.Serializable;

public class ResponseBean implements Serializable{
	private String respcode = "0";// Ϊ��Ӧ�붨��Ĭ��ֵ����Ӧ��Ϣ����������ø�ֵ��Ĭ����Ӧ���ǳɹ�
	private String message;

	public String getRespcode() {
		return respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
