package com.mt.android.protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

public class SocketHandle {
	private static final Logger log = Logger.getLogger(SocketHandle.class);
	/**
	 * ����socket����
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
		 * ����/���� TCP_NODELAY������/���� Nagle �㷨���� �ر� Nagle �㷨��ֱ�ӷ���
		 */

		socket.setTcpNoDelay(true);

		/**
		 * ���� Socket �� SO_RCVBUF ѡ������Ϊָ����ֵ�� ƽ̨���������Ӵ��뽫 SO_RCVBUF ѡ���������õײ����� I/O
		 * ����Ĵ�С����ʾ�� ������ջ����С��������������ӵ����� I/O �����ܣ�����С�������ڼ��ٴ������ݵ� backlog��
		 * SO_RCVBUF ��ֵ���������ù�����Զ��ͬλ��� TCP ���մ��ڡ�һ������£��������׽���ʱ������������ʱ����Ĵ��ڴ�С��
		 * Ȼ���������Ҫ�Ľ��մ��ڴ��� 64K��������ڽ��׽������ӵ�Զ��ͬλ��֮ǰ������������Ҫ֪�������������
		 */
		socket.setReceiveBufferSize(64 * 1024);
		/**
		 * ���� Socket �� SO_SNDBUF ѡ������Ϊָ����ֵ�� ƽ̨���������Ӵ��뽫 SO_SNDBUF ѡ���������õײ����� I/O
		 * ����Ĵ�С����ʾ��
		 */
		socket.setSendBufferSize(64 * 1024);
		// ����ͨѶ���ճ�ʱʱ��
		socket.setSoLinger(false, 0);

		socket.setSoTimeout(readTimeout); // ��ʾ��û�����������׳��쳣

		// ���ӳ�ʱʱ��
		socket.connect(inetSocketAddress, connTimeout);
		
		log.debug("socket build");
		
		return socket;
	}
}
