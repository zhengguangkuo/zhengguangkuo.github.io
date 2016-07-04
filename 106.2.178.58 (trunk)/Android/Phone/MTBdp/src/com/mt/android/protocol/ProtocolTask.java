package com.mt.android.protocol;

public class ProtocolTask {

	//此任务所使用的协议的id
	private String protocolId;
	//协议请求响应码
	private int respCode;
	//请求报文
	private byte[] reqMsg;
	//响应报文
	private byte[] respMsg;
	//错误信息
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
