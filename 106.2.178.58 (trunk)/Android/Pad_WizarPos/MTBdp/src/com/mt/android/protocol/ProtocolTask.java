package com.mt.android.protocol;

public class ProtocolTask {

	//��������ʹ�õ�Э���id
	private String protocolId;
	//Э��������Ӧ��
	private int respCode;
	//������
	private byte[] reqMsg;
	//��Ӧ����
	private byte[] respMsg;
	//������Ϣ
	private String errorMsg;

	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	public int getRespCode() {
		return respCode;
	}

	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}

	public byte[] getReqMsg() {
		return reqMsg;
	}

	public void setReqMsg(byte[] reqMsg) {
		this.reqMsg = reqMsg;
	}

	public byte[] getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(byte[] respMsg) {
		this.respMsg = respMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
