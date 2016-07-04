package com.mt.android.protocol.util;

public class ProtocolRespCode {
	 //
	 public static final int SUCCESS_CODE = 0;
	 //任务请求超时
     public static final int TASK_REQ_TIMEOUT = -1;
     //协议连接超时
     public static final int PROTOCOL_CONNECT_TIMEOUT = -2;
     //协议读超时
     public static final int PROTOCOL_READ_TIMEOUT = -3;
     
     //协议不存在
     public static final int INVALIDE_PROTOCOL_NAME = -4;
     
     //其他
     public static final int UNKNOWN_ERROR = -100;
}
