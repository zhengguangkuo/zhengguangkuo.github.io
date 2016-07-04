package com.mt.android.protocol.tcp.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.mt.android.protocol.DataHandle;
import com.mt.android.protocol.IConnector;
import com.mt.android.protocol.IContext;
import com.mt.android.protocol.IProtocolConfig;
import com.mt.android.protocol.ProtocolConstants;
import com.mt.android.protocol.ProtocolTask;
import com.mt.android.protocol.SocketHandle;
import com.mt.android.protocol.BaseProtocolConfig;
import com.mt.android.protocol.util.ProtocolRespCode;


public class SynShortConsumer implements IConnector {
	private BaseProtocolConfig config = null;
	private boolean isStarted = false;
	private int protocolMode = ProtocolConstants.SYNCHRONOUS;
	private Socket socket = null;
	private static final Logger log = Logger.getLogger(SynShortConsumer.class);

	@Override
	public void setConfig(IProtocolConfig config) {
		this.config = (BaseProtocolConfig) config;
	}

	@Override
	public IProtocolConfig getConfig() {
		return config;
	}

	@Override
	public void start() throws ConnectException {
		try {
			socket = new SocketHandle().getSocket(config.getHost(),
					config.getPort(), config.getReadTimeout(),
					config.getConnectTimeout());  //建立socket连接
		} catch (SocketException e) {
			log.error("Error while starting protocol");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Error while starting protocol");
			e.printStackTrace();
		}                    
		log.debug("Start the socket client, ready to connect with"
					+ config.getHost() + ":" + config.getPort());
		isStarted = true;
	}

	@Override
	public void stop() throws ConnectException {
		if (!socket.isClosed())
			try {
				socket.close();
			} catch (IOException e) {
				log.error("Error while closing protocol");
				e.printStackTrace();
			}
		log.debug("Stopped the client socket  " + config.getHost() + ":"
					+ config.getPort());
		isStarted = false;

	}

	@Override
	public boolean isStarted() {
		return isStarted;
	}

	@Override
	public int getProtocolMode() {
		return protocolMode;
	}

	/**
	 * no need to implements this method for synchronous mode
	 */
	@Override
	public Object receive(IContext context) throws IOException {

		return null;
	}

	@Override
	public Object send(IContext context) throws IOException {
		if (!isStarted()) {
			String msg = "协议没有启动，发送数据失败";
			throw new IOException(msg);
		}
		DataHandle dataHandle = new DataHandle();
		try {  
			dataHandle.writeDateAndShutdown(socket, context);       //发送数据，并关闭socke
		} catch (IOException e) {
			if (socket != null && !socket.isClosed())
				socket.close();
			log.error("Error while send data");
			throw e;
		}
		log.debug("send finish");
		//同步协议，从socket读取数据
		byte[] bytes = null;
		try {
		  bytes = dataHandle.read(socket);
		} catch (IOException e) {
			((ProtocolTask)context.getData()).setRespCode(-1);
			((ProtocolTask)context.getData()).setErrorMsg("error1");
			log.error("Error while read data");
		} 
		//Object obj = bytes;
		if (bytes != null && bytes.length > 0) {
			
		//	obj = new String(bytes, config.getEncoding());
			((ProtocolTask)context.getData()).setRespCode(0);
			((ProtocolTask)context.getData()).setRespMsg(bytes);
				
		} else {
			((ProtocolTask)context.getData()).setRespCode(ProtocolRespCode.PROTOCOL_READ_TIMEOUT);
			((ProtocolTask)context.getData()).setErrorMsg("error2");
			throw new IOException("Received bytes is null");
		}
		return null;
	}
}
