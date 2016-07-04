package com.mt.android.protocol;

/**
 * Э���������, Ӧ������Э������б�������
 * <p>
 * Configuration of protocol, records all necessary informations.

 */
public interface IProtocolConfig {

	/**
	 * ��ȡ��ǰЭ���ͬ�첽ģʽ
	 * <p>
	 * get the protocol mode
	 * 
	 * @return
	 * 
	 * @see ProtocolMode
	 */
	public int getMode();

	/**
	 * ���õ�ǰЭ���ͬ�첽ģʽ
	 * <p>
	 * Set the protocol mode
	 * 
	 * @see ProtocolMode
	 */
	void setMode(int mode);

	/**
	 * ��ȡ�����õ�id
	 * <p>
	 * Get id of this configuration
	 * 
	 * @return
	 */
	
	public void setServerSide(boolean isServerSide);

	/**
	 * ��ȡ�������Ƿ��ǽ���Э��
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
