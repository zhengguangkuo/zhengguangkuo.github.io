package com.mt.android.protocol;

/**
 * 协议的配置类, 应包含该协议的所有必须配置
 * <p>
 * Configuration of protocol, records all necessary informations.

 */
public interface IProtocolConfig {

	/**
	 * 获取当前协议的同异步模式
	 * <p>
	 * get the protocol mode
	 * 
	 * @return
	 * 
	 * @see ProtocolMode
	 */
	public int getMode();

	/**
	 * 设置当前协议的同异步模式
	 * <p>
	 * Set the protocol mode
	 * 
	 * @see ProtocolMode
	 */
	void setMode(int mode);

	/**
	 * 获取该配置的id
	 * <p>
	 * Get id of this configuration
	 * 
	 * @return
	 */
	
	public void setServerSide(boolean isServerSide);

	/**
	 * 获取该配置是否是接入协议
	 * <p>
	 * Get server/client side this config belonged to
	 * 
	 * @return
	 */
	public boolean getServerSide();



	public String getProtocolId() ;
	public void setProtocolId(String protocolId);
	public String getHost() ;
	public void setHost(String host) ;
	public int getPort() ;
	public void setPort(int port) ;
	public String getPolicy() ;
	public void setPolicy(String policy);
	public String getEncoding() ;
	public void setEncoding(String encoding) ;
	public int getReadTimeout() ;
	public void setReadTimeout(int readTimeout);
	public int getConnectTimeout() ;
	public void setConnectTimeout(int connectTimeout) ;
	public String getConnectMode() ;
	public void setConnectMode(String connectMode);
	public int getAction() ;
	public void setAction(int action) ;

}
