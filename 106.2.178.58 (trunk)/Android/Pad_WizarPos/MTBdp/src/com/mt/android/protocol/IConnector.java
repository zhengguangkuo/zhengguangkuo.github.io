package com.mt.android.protocol;

import java.io.IOException;
import java.net.ConnectException;


/**
 * 协议连接器的公共接口, 负责协议收发数据
 * @author 
 *
 */
public interface IConnector {
	/**
	 * 设置该协议连接器的配置	 * <p>Set configuration of this connector
	 * @param config
	 */
	public void setConfig(IProtocolConfig config);
	
	/**
	 * 获取该协议连接器的配配置	 * <p>Get the configuration of this connector
	 * @return
	 */
	public IProtocolConfig getConfig();
	
	/**
	 * 启动该协议连接器
	 * <p>Start connector
	 * @throws ConnectException
	 */
	public void start() throws ConnectException;
	
	/**
	 * 停止该协议连接器
	 * <p>Stop connector
	 * @throws ConnectException
	 */
	public void stop() throws ConnectException;
	
	/**
	 * 获取协议连接器的状态
	 * @return
	 */
	public boolean isStarted();
	
	/**
	 * 获取该协议连接器的同异步模式
	 * <p>Get the protocol mode of this connector, e.g.: Synchronous, Asynchronous, and Pseudo-asynchronous 
	 * @see ProtocolMode
	 * @return
	 */
	public int getProtocolMode();
	
	/**
	 * 读取数据. 该方法对于异步模式协议连接器有可能不实现
	 * <p>Receive data. This method may not be implements for asynchronous mode. 
	 * @param context
	 * 	上下文环境IO资源例如socket, httpRequrest等会存储其中
	 * 	<p>The environment of the invocation, the IO resources like socket, http request are
	 * 	stored in context. 
	 * 
	 * @return
	 * 		读取的数据可以为任意格式
	 * 		<p>The received data from IO resources, may be byte array, string or any other types
	 * 
	 * @throws 
	 * 	IOException
	 * 		当读取数据错误时抛出此异常	 * 		<p>throw IOException when it fails to receive data
	 */
	public Object receive(IContext context) throws IOException;
	
	/**
	 * 发送数据. 该方法对于异步模式协议连接器有可能不实现
	 * <p>Send data. This method may not be implemented for asynchronous mode. 
	 * 
	 * @param context
	 * 	上下文环境资源以及要发送的数据都存储其context�?	 * 	<p>The environment of the invocation, the IO resources like socket, http request are
	 * 	stored in context. The data to be sent is also stored in context.
	 * @return 
	 * 	对于同步接出连接返回响应数据, 其他则返回空
	 * 	<p>response for synchronous client connector or null 
	 * 	for other server/client connectors in any protocol mode.
	 * @throws 
	 * 	IOException
	 * 	当发送失败时抛出此异常	
	 */
	public Object send(IContext context) throws IOException;
}
