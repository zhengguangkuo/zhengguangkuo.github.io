package com.mt.android.protocol.manager;

import com.mt.android.protocol.IConnector;
import com.mt.android.protocol.tcp.client.SynShortConsumer;

public class ConnectorFactory {
	 //同步长pool
	 //BlockingPool<> synLongTcpPool = null; 
	 //异步长pool
	 //BlockingPool<> aynLongTcpPool = null;
	 //初始化synLongTcpPool 与 aynLongTcpPool
	 public static void init(){
		 
	 }
     public static IConnector getProxyByMode( ){
         //TCP
    	   //长连接
    	       //同步
    	       //异步
    	   //短连接
    	       //同步
    	       //异步
    	//HTTP
    	   //长连接
	          //同步
	          //异步
	       //短连接
	          //同步
	          //异步
    	 SynShortConsumer synShortConsumer = new SynShortConsumer();
    	 return synShortConsumer;
     }
     
}
