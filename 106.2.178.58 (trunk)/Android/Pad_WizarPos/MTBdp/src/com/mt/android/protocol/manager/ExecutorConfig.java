package com.mt.android.protocol.manager;

import java.util.concurrent.ExecutorService;

import com.mt.android.protocol.IProtocolConfig;

public class ExecutorConfig {
	private IProtocolConfig config = null;
	private ExecutorService executor = null;
	
	public IProtocolConfig getConfig() {
		return config;
	}

	public void setConfig(IProtocolConfig config) {
		this.config = config;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	
}
