package com.mt.android.protocol.manager;

import com.mt.android.protocol.IConnector;
import com.mt.android.protocol.tcp.client.SynShortConsumer;

public class ConnectorFactory {
	 //ͬ����pool
	 //BlockingPool<> synLongTcpPool = null; 
	 //�첽��pool
	 //BlockingPool<> aynLongTcpPool = null;
	 //��ʼ��synLongTcpPool �� aynLongTcpPool
	 public static void init(){
		 
	 }
     public static IConnector getProxyByMode( ){
         //TCP
    	   //������
    	       //ͬ��
    	       //�첽
    	   //������
    	       //ͬ��
    	       //�첽
    	//HTTP
    	   //������
	          //ͬ��
	          //�첽
	       //������
	          //ͬ��
	          //�첽
    	 SynShortConsumer synShortConsumer = new SynShortConsumer();
    	 return synShortConsumer;
     }
     
}
