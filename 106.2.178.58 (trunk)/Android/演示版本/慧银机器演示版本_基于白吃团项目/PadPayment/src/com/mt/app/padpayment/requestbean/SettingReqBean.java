package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

/**
 * 

 * @Description:�����޸�bean

 * @author:dw

 * @time:2013-8-6 ����1:55:28
 */
public class SettingReqBean extends Request{
	private String host;//Э��ip
	private String port;//�˿ں�
	private String logLevel;//��־����
	private String logTime;//��־����ʱ��
	private String timeout;//Э�鳬ʱʱ��
	private String reversalFreq;//����Ƶ��
	private String reversalAmount;//��������
	public void setHost(String host) {
		this.host = host;
	}
	public String getHost() {
		return host;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPort() {
		return port;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getLogTime() {
		return logTime;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getReversalFreq() {
		return reversalFreq;
	}
	public void setReversalFreq(String reversalFreq) {
		this.reversalFreq = reversalFreq;
	}
	public String getReversalAmount() {
		return reversalAmount;
	}
	public void setReversalAmount(String reversalAmount) {
		this.reversalAmount = reversalAmount;
	}
	
}
