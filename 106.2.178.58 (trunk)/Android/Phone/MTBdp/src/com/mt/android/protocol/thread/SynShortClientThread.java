package com.mt.android.protocol.thread;

import java.io.IOException;
import java.net.ConnectException;

import org.apache.log4j.Logger;


import com.mt.android.protocol.IConnector;
import com.mt.android.protocol.IContext;
import com.mt.android.protocol.IProtocolConfig;
import com.mt.android.protocol.manager.ConnectorFactory;
import com.mt.android.protocol.tcp.client.SynShortConsumer;

public class SynShortClientThread implements Runnable {
	private IProtocolConfig config;
	private IConnector connector = null;
	private IContext context;
	private static final Logger log = Logger.getLogger(SynShortClientThread.class);

	public IProtocolConfig getConfig() {
		return config;
	}

	public void setConfig(IProtocolConfig config) {
		this.config = config;
	}

	public IConnector getConnector() {
		return connector;
	}

	public void setConnector(IConnector connector) {
		this.connector = connector;
	}

	public IContext getContext() {
		return context;
	}

	public void setContext(IContext context) {
		this.context = context;
	}

	public SynShortClientThread(IProtocolConfig config, IContext context) {
		this.config = config;
		this.context = context;
	}

	@Override
	public void run() {
		
		log.debug("start protocolThread, protocolId:  "+config.getProtocolId());
		
		//TODO 从工厂获取协议
		connector = ConnectorFactory.getProxyByMode();
		connector.setConfig(config);
		
		try {
			connector.start();   //启动协议
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		if (connector.isStarted()) { // 如果启动协议成功
			try {
			    connector.send(context);    //同步协议,发送数据,并且接受数据.  将收到的数据放入context的date中
				connector.stop();                     //停止协议
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.debug("end protocolThread, protocolId:  "+config.getProtocolId());
	}
	/*public static void main(String[] args){
		TCPProtocolConfig tcpConfig = new TCPProtocolConfig();
		tcpConfig.setHost("127.0.0.1");
		tcpConfig.setPort(4700);
		Context myContext= new Context();
		myContext.setDate("测试测试");
		SynShortClientThread ss=new SynShortClientThread(tcpConfig, myContext);
		Thread thread =new Thread(ss);
		thread.start();
	}*/
}
