package com.mt.android.protocol;

public class BaseProtocolConfig implements IProtocolConfig {
	private String protocolId;
	private String type;//TCP HTTP
	private String host;
	private int port;
	private String policy;
	private String encoding;
	private int readTimeout;
	private int connectTimeout;
	private String connectMode;
	private int action;
	private int mode;
	private boolean serverSide;
	private String coolPoolSize;
	private String maxiNumPoolSize;
	private String asynMode;

	public String getAsynMode() {
		return asynMode;
	}

	public void setAsynMode(String asynMode) {
		this.asynMode = asynMode;
	}

	@Override
	public String getProtocolId() {
		return protocolId;
	}

	public String getCoolPoolSize() {
		return coolPoolSize;
	}

	public void setCoolPoolSize(String coolPoolSize) {
		this.coolPoolSize = coolPoolSize;
	}

	public String getMaxiNumPoolSize() {
		return maxiNumPoolSize;
	}

	public void setMaxiNumPoolSize(String maxiNumPoolSize) {
		this.maxiNumPoolSize = maxiNumPoolSize;
	}

	@Override
	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String getPolicy() {
		return policy;
	}

	@Override
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	@Override
	public String getEncoding() {
		return encoding;
	}

	@Override
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public int getReadTimeout() {
		return readTimeout;
	}

	@Override
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	@Override
	public int getConnectTimeout() {
		return connectTimeout;
	}

	@Override
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	@Override
	public String getConnectMode() {
		return connectMode;
	}

	@Override
	public void setConnectMode(String connectMode) {
		this.connectMode = connectMode;
	}

	@Override
	public int getAction() {
		return action;
	}

	@Override
	public void setAction(int action) {
		this.action = action;
	}

	@Override
	public int getMode() {
		return mode;
	}

	@Override
	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public boolean getServerSide() {
		return serverSide;
	}

	@Override
	public void setServerSide(boolean serverSide) {
		this.serverSide = serverSide;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
