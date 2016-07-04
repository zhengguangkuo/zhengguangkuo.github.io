package com.mt.android.protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

public class SocketHandle {
	private static final Logger log = Logger.getLogger(SocketHandle.class);
	/**
	 * 创建socket连接
	 * 
	 * @param host
	 * @param port
	 * @param readTimeout
	 * @param connTimeout
	 * @return socket
	 * @throws IOException
	 * @throws SocketException
	 */
	public Socket getSocket(String host, int port, int readTimeout,
			int connTimeout) throws SocketException, IOException {
		Socket socket = null;
		socket = new Socket();

		InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);

		/**
		 * 启用/禁用 TCP_NODELAY（启用/禁用 Nagle 算法）。 关闭 Nagle 算法，直接发包
		 */

		socket.setTcpNoDelay(true);

		/**
		 * 将此 Socket 的 SO_RCVBUF 选项设置为指定的值。 平台的网络连接代码将 SO_RCVBUF 选项用作设置底层网络 I/O
		 * 缓存的大小的提示。 增大接收缓存大小可以增大大量连接的网络 I/O 的性能，而减小它有助于减少传入数据的 backlog。
		 * SO_RCVBUF 的值还用于设置公布到远程同位体的 TCP 接收窗口。一般情况下，当连接套接字时，可以在任意时间更改窗口大小。
		 * 然而，如果需要的接收窗口大于 64K，则必须在将套接字连接到远程同位体之前请求。下面是需要知道的两种情况：
		 */
		socket.setReceiveBufferSize(64 * 1024);
		/**
		 * 将此 Socket 的 SO_SNDBUF 选项设置为指定的值。 平台的网络连接代码将 SO_SNDBUF 选项用作设置底层网络 I/O
		 * 缓存的大小的提示。
		 */
		socket.setSendBufferSize(64 * 1024);
		// 设置通讯接收超时时间
		socket.setSoLinger(false, 0);

		socket.setSoTimeout(readTimeout); // 表示秒没读上数据则抛出异常

		// 链接超时时间
		socket.connect(inetSocketAddress, connTimeout);
		
		log.debug("socket build");
		
		return socket;
	}
}
