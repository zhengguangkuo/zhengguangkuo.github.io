package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

/**
 * 

 * @Description:设置修改bean

 * @author:dw

 * @time:2013-8-6 下午1:55:28
 */
public class SettingReqBean extends Request{
	private String host;//协议ip
	private String port;//端口号
	private String logLevel;//日志级别
	private String logTime;//日志保存时间
	private String timeout;//协议超时时间
	private String reversalFreq;//冲正频率
	private String reversalAmount;//冲正次数
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
