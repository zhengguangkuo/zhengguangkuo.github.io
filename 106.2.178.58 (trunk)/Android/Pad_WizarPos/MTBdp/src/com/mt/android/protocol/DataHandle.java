package com.mt.android.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.mt.android.sys.util.StringUtil;



public class DataHandle {
	private static final Logger log = Logger.getLogger(DataHandle.class);
	/**
	 * ������д��socket���ر�����
	 * @param socket
	 * @param context
	 * @throws IOException 
	 */
	public void writeDateAndShutdown(Socket socket, IContext context) throws IOException {
		byte[] bytes = null;
		if (context != null) {
			bytes = ((ProtocolTask)context.getData()).getReqMsg();	 
		}
		log.debug("send data length is  "+ ((ProtocolTask)context.getData()).getReqMsg().length);
		OutputStream os = socket.getOutputStream();
		os.write(bytes);

		os.flush();
		
		//socket.shutdownOutput();
	}
	
	/**
	 * ��socket��ȡ����
	 * @param socket
	 * @throws IOException 
	 */
	public byte[] read(Socket socket) throws IOException{
		byte[] receiveReply = null;
		InputStream inputStream = socket.getInputStream();
		int byteRead;
		byte[] tmp = new byte[1024*1000];  //��ʱ�������ֵΪ1024*1000
		ByteArrayOutputStream baos = null;
		
		byte[] bLen = new byte[2];
		int iLen = 0, vLen = 0;
		
		try {
			baos = new ByteArrayOutputStream();
			byteRead = inputStream.read(bLen);
			baos.write(bLen, 0, byteRead);
			iLen = StringUtil.byteToInt(bLen);
			
			while ((byteRead = inputStream.read(tmp)) != -1) {
				baos.write(tmp, 0, byteRead);
				vLen = vLen + byteRead;
				
				if (vLen == iLen){//����ֽڳ��ȶ������Ƴ�ѭ��
					break;
				}
			}

			receiveReply = baos.toByteArray();

		} finally {
			if (baos != null) {
				baos.close();
			}
		}
		log.debug("receive date finish");
		return receiveReply;
	}
}
