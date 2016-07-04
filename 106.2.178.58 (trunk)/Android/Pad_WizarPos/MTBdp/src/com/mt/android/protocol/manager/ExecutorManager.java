package com.mt.android.protocol.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mt.android.protocol.BaseProtocolConfig;
import com.mt.android.protocol.Context;
import com.mt.android.protocol.IContext;
import com.mt.android.protocol.IProtocolConfig;
import com.mt.android.protocol.ProtocolTask;
import com.mt.android.protocol.thread.SynShortClientThread;
import com.mt.android.protocol.util.ProtocolRespCode;

public class ExecutorManager {
	// TCP同步短
	// int corePoolSize, int maximumPoolSize,long keepAliveTime, TimeUnit
	// unit,BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler
	private static Map<String, ExecutorConfig> protocolConfigs = new HashMap<String, ExecutorConfig>();

	// TCP同步长
	// TCP异步短
	// TCP异步长
	public static void dotask(ProtocolTask task) {
		ExecutorConfig  executorConfig = protocolConfigs.get(task.getProtocolId());
		
		if (executorConfig == null){
			task.setRespCode(ProtocolRespCode.INVALIDE_PROTOCOL_NAME);
		}
		
		IProtocolConfig  config = executorConfig.getConfig();
		IContext context = new Context();
		context.setData(task);
		
		Runnable thread = null;//根据配置获取对应协议使用的线程
		
		BaseProtocolConfig baseConfig = (BaseProtocolConfig)config;
		
		
		
		//TCP同步短连接
		if (getProtocolTypes(baseConfig) == 0){
			thread  = new SynShortClientThread(config, context);
		}
		
		
		FutureTask ftask = null;

		try {
			ftask = (FutureTask) executorConfig.getExecutor().submit(thread);
			ftask.get(60, TimeUnit.SECONDS);
		} catch (Exception ex) {
			ftask.cancel(true);
			task.setRespCode(ProtocolRespCode.TASK_REQ_TIMEOUT);
			ex.printStackTrace();
		}

	}
    private static int getProtocolTypes(BaseProtocolConfig tcpConfig){
    	String type,asynMode;
		boolean serverSide = false;
		int mode = 0;
		
		type = tcpConfig.getType();
		asynMode = tcpConfig.getAsynMode();
		serverSide = tcpConfig.getServerSide();
		mode = tcpConfig.getMode();
		
    	return  0;
    }
	//在通讯模块初始化时加载协议配置,如果有长连接则进行了连接
	public static void loadProtocolConfigs(IProtocolConfig[] configs){
		
		for(int i = 0; i < configs.length; i ++){
			BaseProtocolConfig proconfig = (BaseProtocolConfig)configs[i];
			LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(100);
			int coolPoolSize = Integer.valueOf(proconfig.getCoolPoolSize());
			int maxiNumSize = Integer.valueOf(proconfig.getMaxiNumPoolSize());
			
			ExecutorService executor = new ThreadPoolExecutor(coolPoolSize, maxiNumSize,60, TimeUnit.SECONDS, workQueue);
			ExecutorConfig execuConfig = new ExecutorConfig();
			
			execuConfig.setConfig(proconfig);
			execuConfig.setExecutor(executor);
			protocolConfigs.put(proconfig.getProtocolId(), execuConfig);
		}
		
	}
	public static void main(String[] args) {
		// mangager.dotask("第二个任务");
		// System.out.println("请求已发送");
	}
}
