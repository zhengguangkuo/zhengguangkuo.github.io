package com.mt.app.padpayment.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import com.centerm.mid.imp.socketimp.DeviceFactory;
import com.centerm.mid.inf.TermOperationInf;
import com.landicorp.android.eptapi.utils.SystemInfomation;
import com.mt.android.R;
import com.mt.android.db.DbHandle;
import com.mt.android.db.DbHelper;
import com.mt.android.global.Globals;
import com.mt.android.protocol.BaseProtocolConfig;
import com.mt.android.protocol.manager.ExecutorManager;
import com.mt.android.sys.util.GlobalPathUtil;
import com.mt.android.view.common.ActivityID;
import com.mt.android.view.common.CommandID;
import com.mt.android.view.common.DialogManager;
import com.mt.android.view.common.Initializer;
import com.mt.app.padpayment.message.iso.trans.InitialiseBean;
import com.mt.app.padpayment.message.iso.trans.SignInBean;
import com.mt.app.padpayment.service.DBService;
import com.mt.app.padpayment.tools.PackUtil;
import com.mt.app.padpayment.tools.ProjectPublicObject;
import com.mt.app.padpayment.tools.SysConfig;
import com.mt.app.padpayment.tools.TransSequence;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class SysManager {
	private static Context context = null;
	private static DbHelper db = null;
	private static String sTotalSize = "";// 用于参数下载时查询应用列表
	private static boolean bError = true;
	private static boolean bRun = true;

	public static Intent intentservice = new Intent();

	private static DbHandle dbhandle = new DbHandle();

	// ---------------------------系统log输出参数设置-------------------------------
	// -----------------------------begin----------------------------------------
	private static String logToFileCommand = "logcat -v time -f "; // log输出到设置
	private static String logSrc = "/mnt/sdcard/log/"; // log输出到sdcard路径
	private static String logFileName = "PadPaymentLog"; // log输出生成的文件名
	private static String logLevel = " -s *:I"; // log打印级别控制 E为错误级别 W为警告级别
												// I为info级别依次类推
	// -----------------------------end-------------------------------------------

	private static ProjectPublicObject ppo = new ProjectPublicObject();

	public SysManager(Context context) {
		this.context = context;
		intentservice.setClass(context, DBService.class);
	}

	/**
	 * 启动工程进行初始化
	 * 
	 * @return
	 */
	public static boolean start() {
		// 临时存放测试数据

		/*
		 * GlobalParameters.g_map_para.put("cardSequenceNum", "000");// 应用类别 //
		 * (支付类应用000) GlobalParameters.g_map_para.put("cardAcceptTermIdent",
		 * "A1000001");// 受卡机终端标识码(终端号)
		 * GlobalParameters.g_map_para.put("cardAcceptIdentcode",
		 * "shdm12345678900");// 受卡方标识码
		 * GlobalParameters.g_map_para.put("batchNum", "pch100");// 设置批次号
		 * SysConfig.writeProperties("yyyy", "4");
		 */
		
		/**
		 *   建立configs文件夹
		 */
		String rootFilePath = GlobalPathUtil.getExternalStorageDirectory().getPath() + File.separator + "configs" ;
		File root = new File(rootFilePath) ;
		if(!root.exists()) {
			root.mkdir() ;
		}
		/**
		 *   建立文件
		 */
		String filePath = rootFilePath + File.separator + "SysConfig.properties" ;
		File file = new File(filePath);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 *  将assets中的文件写入sdcard中
		 */
		try {
			InputStream in = context.getAssets().open("file/SysConfig.properties");
			FileOutputStream out = new FileOutputStream(file);
			int len = -1;
			byte[] buffer = new byte[1024];
			while((len=in.read(buffer))!=-1){
				out.write(buffer,0,len);
				out.flush();
			}
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// 初始化设备
		initDevice();
		// 初始化数据库
		initDb();
		// 启动通讯模块
		StartCommModule();
		// 启动冲正线程
		//StartReversalThread();
		// 加载协议
		//LoadProtocolCfg();
		// 加载系统配置信息
		LoadSysCfgs();
		// 设备管理类交易
		sendManageTrans();
		// 输出日志-启动log4j日志功能
		// startlog4j();

		// log设置成sdcard输出
		// startSetSYSLog();

		// 启动日终线程

		startDayEnd();
		// 删除日前数据

		SimpleDateFormat df = new SimpleDateFormat("MMdd");// 设置日期格式
		dbhandle.delete("TBL_FlOW", "LOCAL_TRANS_DATE_1 !=?",
				new String[] { df.format(new Date()) });
		dbhandle.delete("TBL_TMPFlOW", "LOCAL_TRANS_DATE_1 !=?",
				new String[] { df.format(new Date()) });
		return true;
	}
	//初始化数据
	@Deprecated
	private static boolean setData(){
		Map map = new DbHandle().selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "batchNum" }, null, null, null);
		if (map != null && map.size() > 0) {
			GlobalParameters.g_map_para.put("batchNum",
					map.get("PARA_VALUE")+"");// 设置批次号
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * 停止工程
	 * 
	 * @return
	 */
	public static boolean stop() {

		if (intentservice != null) {
			context.stopService(intentservice);
		}
		return true;
	}

	// 初始化数据库
	private static boolean initDb() {
		ppo.sysLogOutputBegin("SysManager", "initDb()",
				"start padpayment app Init DB method");

		// 建立数据库
		db = DbHelper.getInstance(context, new DbInfoImpl());
		db.open();

		Cursor cursor = db.select("TBL_RESPONSE_CODE", new String[] { "_ID",
				"RESP_CODE", "MESSAGE" }, null, null, null, null, null);

		// 判断数据库是否已经初始化过
		if (cursor.moveToNext()) {
			cursor.close();
			db.close();
			return true;
		}
		// 初始化数据库
		try {
			// 初始化响应码对照表
			db.execSQL("insert into TBL_RESPONSE_CODE(RESP_CODE,MESSAGE) values (0,'成功')");

			// 初始化参数表
			db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('2','REVERSAL_FREQ','30000','冲正轮询时间')");
			db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('2','REVERSAL_AMOUNT','2','冲正次数')");
			db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('4','NOW_VERSION','V1.0','当前版本号')");
			db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('1','INITIALIZE','否','是否初始化过')");

			db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('3','LOGLEVEL','info','当前日志级别')");
			db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('3','LOGTIME','30','日志保存日期')");

			db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('3','batchNum','100000','批次号')");
			
			
			// 初始化临时参数表
			db.execSQL("insert into TBL_TMPPARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('1','BATCH_NUM','','批次号')");
			db.execSQL("insert into TBL_TMPPARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('1','CARD_SEQUENCE_NUM','','应用类别')");
			db.execSQL("insert into TBL_TMPPARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('1','CARD_ACCEPT_TERM_IDENT','','受卡机终端标识码(终端号)')");
			db.execSQL("insert into TBL_TMPPARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('1','CARD_ACCEPT_IDENTCODE','','受卡方标识码')");

			// 插入默认协议配置
			// 生产环境 
//			db.execSQL("insert into TBL_PROTOCL(ID,TYPE,ASYNMODE,MODE,HOST,PORT,ENCODING,POLICY,READTIMEOUT,SERVERSIDE,COOLPOOLSIZE,MAXINUMPOOLSIZE) values ('TCP_SHORT_1','TCP','syn','0','118.144.88.36','59999','UTF-8','length:unknown','10000', 'false','10','20')");
			// 测试环境
//			db.execSQL("insert into TBL_PROTOCL(ID,TYPE,ASYNMODE,MODE,HOST,PORT,ENCODING,POLICY,READTIMEOUT,SERVERSIDE,COOLPOOLSIZE,MAXINUMPOOLSIZE) values ('TCP_SHORT_1','TCP','syn','0','192.168.16.6','59999','UTF-8','length:unknown','10000', 'false','10','20')");
			// 拉卡拉环境 
			db.execSQL("insert into TBL_PROTOCL(ID,TYPE,ASYNMODE,MODE,HOST,PORT,ENCODING,POLICY,READTIMEOUT,SERVERSIDE,COOLPOOLSIZE,MAXINUMPOOLSIZE) values ('TCP_SHORT_1','TCP','syn','0','9.250.249.10','59999','UTF-8','length:unknown','10000', 'false','10','20')");
			
			// 在人员管理表中插入一条记录
			db.execSQL("insert into TBL_ADMIN(USER_ID,USER_NAME,PASSWORD,LIMITS) values ('001','王柜员','123','1')");
			db.execSQL("insert into TBL_ADMIN(USER_ID,USER_NAME,PASSWORD,LIMITS) values ('000','李主管','111','2')");
			
			//流水号
			db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values('1','CurrentVal','300000','流水号')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		ppo.sysLogOutputEnd("SysManager", "initDb()", "");
		return true;
	}

	// 初始化设备
	private static boolean initDevice() {
		// 读卡器初始化
		// 加密键盘初始化
		// 打印机初始化
		return true;
	}

	// 启动通讯模块
	private static boolean StartCommModule() {
		LoadProtocolCfg();
		return true;
	}

	// 启动冲正工程
	private static boolean StartReversalThread() {

		System.out.println("程序线程启动之前-----");

		context.startService(intentservice);
		System.out.println("程序线程启动完成-----");
		return true;
	}

	// 加载界面配置
	private static boolean LoadDialogCfg() {

		return true;
	}

	// 加载协议配置
	/*
	 * private static boolean LoadProtocolCfg() { List<BaseProtocolConfig> ls =
	 * new ArrayList<BaseProtocolConfig>();
	 * 
	 * try { SAXReader reader = new SAXReader(); InputStream in =
	 * context.getAssets() .open("file/ProtocolConfig.xml"); Document d =
	 * reader.read(in);
	 * 
	 * Element root = d.getRootElement(); List rootchilds = root.elements();
	 * 
	 * if (rootchilds.size() > 0) { for (Iterator it_root =
	 * rootchilds.iterator(); it_root .hasNext();) { Element dialogele =
	 * (Element) it_root.next(); if
	 * (dialogele.getName().toUpperCase().equals("PROTOCOL")) { List elements =
	 * dialogele.elements(); BaseProtocolConfig baseConfig = new
	 * BaseProtocolConfig();
	 * 
	 * for (Iterator it = elements.iterator(); it.hasNext();) { Element e =
	 * (Element) it.next(); String eName = e.getName().toUpperCase(); String
	 * eVal = e.getStringValue();
	 * 
	 * if (eName.equalsIgnoreCase("ID")) { baseConfig.setProtocolId(eVal); }
	 * else if (eName.equalsIgnoreCase("TYPE")) { baseConfig.setType(eVal); }
	 * else if (eName.equalsIgnoreCase("ASYNMODE")) {
	 * baseConfig.setAsynMode(eVal); } else if (eName.equalsIgnoreCase("MODE"))
	 * { if (eVal != null && !eVal.trim().equalsIgnoreCase("")) {
	 * baseConfig.setMode(Integer.valueOf(eVal)); } } else if
	 * (eName.equalsIgnoreCase("HOST")) { baseConfig.setHost(eVal); } else if
	 * (eName.equalsIgnoreCase("PORT")) { if (eVal != null &&
	 * !eVal.trim().equalsIgnoreCase("")) {
	 * baseConfig.setPort(Integer.valueOf(eVal)); } } else if
	 * (eName.equalsIgnoreCase("ENCODING")) { baseConfig.setEncoding(eVal); }
	 * else if (eName.equalsIgnoreCase("POLICY")) { baseConfig.setPolicy(eVal);
	 * } else if (eName.toUpperCase().equalsIgnoreCase( "READTIMEOUT")) { if
	 * (eVal != null && !eVal.trim().equalsIgnoreCase("")) {
	 * baseConfig.setReadTimeout(Integer .valueOf(eVal)); } } else if
	 * (eName.equalsIgnoreCase("SERVERSIDE")) { if (eVal != null &&
	 * eVal.equalsIgnoreCase("false")) { baseConfig.setServerSide(false); } else
	 * { baseConfig.setServerSide(true); } } else if
	 * (eName.equalsIgnoreCase("COOLPOOLSIZE")) {
	 * baseConfig.setCoolPoolSize(eVal); } else if (eName
	 * .equalsIgnoreCase("MAXINUMPOOLSIZE")) {
	 * baseConfig.setMaxiNumPoolSize(eVal); } }
	 * 
	 * ls.add(baseConfig); } } } } catch (IOException e) { e.printStackTrace();
	 * } catch (DocumentException e) { e.printStackTrace(); }
	 * BaseProtocolConfig[] cfgarr = new BaseProtocolConfig[ls.size()];
	 * 
	 * for (int i = 0; i < ls.size(); i++) { cfgarr[i] = (BaseProtocolConfig)
	 * ls.get(i); }
	 * 
	 * ExecutorManager.loadProtocolConfigs(cfgarr); return true; }
	 */

	private static boolean LoadProtocolCfg() {
		List<BaseProtocolConfig> ls = new ArrayList<BaseProtocolConfig>();

		try {
			// ID,TYPE,ASYNMODE,MODE,HOST,PORT,ENCODING,POLICY,READTIMEOUT,SERVERSIDE,COOLPOOLSIZE,MAXINUMPOOLSIZE
			List<Map<String, String>> list = dbhandle.rawQuery(
					"select * from TBL_PROTOCL ", null);
			for (int i = 0; i < list.size(); i++) {
				BaseProtocolConfig baseConfig = new BaseProtocolConfig();
				baseConfig.setProtocolId(list.get(i).get("ID"));
				baseConfig.setType(list.get(i).get("TYPE"));
				baseConfig.setAsynMode(list.get(i).get("ASYNMODE"));
				baseConfig.setMode(Integer.valueOf(list.get(i).get("MODE")));
				baseConfig.setHost(list.get(i).get("HOST"));
				baseConfig.setPort(Integer.valueOf(list.get(i).get("PORT")));
				baseConfig.setEncoding(list.get(i).get("ENCODING"));
				baseConfig.setPolicy(list.get(i).get("POLICY"));
				baseConfig.setReadTimeout(Integer.valueOf(list.get(i).get(
						"READTIMEOUT")));
				baseConfig.setConnectTimeout(30000) ;
				boolean flg = false;

				if (list.get(i).get("SERVERSIDE").equalsIgnoreCase("true")) {
					flg = true;
				}
				baseConfig.setServerSide(flg);
				baseConfig.setCoolPoolSize(list.get(i).get("COOLPOOLSIZE"));
				baseConfig.setMaxiNumPoolSize(list.get(i)
						.get("MAXINUMPOOLSIZE"));
				ls.add(baseConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		BaseProtocolConfig[] cfgarr = new BaseProtocolConfig[ls.size()];

		for (int i = 0; i < ls.size(); i++) {
			cfgarr[i] = (BaseProtocolConfig) ls.get(i);
		}

		ExecutorManager.loadProtocolConfigs(cfgarr);
		return true;
	}

	// 加载系统配置信息
	private static boolean LoadSysCfgs() {
		boolean flg = true;
		// 用于保存商户号等信息到内存
		Map<String, String> sysinfo = new HashMap<String, String>();
		Map<String, String> readmap = new HashMap<String, String>();
		try {
			Globals.setMap("DialogManager", new DialogManager(context));
			Globals.setMap("ActivityID", new ActivityID());
			Globals.setMap("CommandID", new CommandID());
			Globals.setMap("Initializer", new Initializer());
			Map<String, Integer> drawableIDMap = getDrawableIDMap();
			Globals.setMap("DrawableID", drawableIDMap);
			SysConfig.readProperties(readmap);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return flg;
	}

	// 发送管理类交易
	private static boolean sendManageTrans() {
		boolean flg = false;

		Map map = new DbHandle().selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "INITIALIZE" }, null, null, null);
		if (map != null && map.size() > 0 && map.get("PARA_VALUE").equals("否")) {
			if (!initialize()) {// 初始化交易
				return flg;
			}
		} 
		/*if (!signIn()) {// 签到交易
			return flg;
		}*/

		/*
		 * if (!loadParameters()) {// 参数下载 return flg; }
		 */

		flg = true;
		return flg;
	}
	
	//获取设备唯一编号
	public static String getSerialNumber()
		{
			String serial = null;
			try { 
				   Class<?> c = Class.forName("android.os.SystemProperties");
				   Method get = c.getMethod("get",String.class);
				   serial = (String)get.invoke(c, "ro.serialno");
				   System.out.println(serial);
		     }catch (Exception ignored){
		     
		     }  
			 return serial;
		} 

	// 初始化交易
	public static boolean initialize() {
		// 厂家标识 001 序列号PAD000000000001
		String prgversion = "0000000001";// pad程序版本号
		String factoryident = "001";// 终端厂家标识
		String terminalsn = "PAD000000000001";// 终端序列号sn
		String baseinfo = "1111111111";// 基站信息
		Map<String, String> readmap = new HashMap<String, String>();
		String terminalInitInfo = "";

		try {
			SysConfig.readProperties(readmap);
			prgversion = readmap.get("prgversion");
			
//			String mmm = getSerialNumber() ;
//			String mmm = "D1GSAOKJ00010" ;			// 升腾拉卡拉SN号
//			String mmm = "00L02M00037859" ;			// 联迪拉卡拉SN号	
			
//			String mmm = "LA" + SystemInfomation.getDeviceInfo().getSerialNo() ;	// 联迪拉卡拉SN号
			String mmm = null ;
			try {
				TermOperationInf t = DeviceFactory.getTermOperationInf();
				mmm = "LA" + t.readSerialNum();
			} catch (Exception e) {
				e.printStackTrace();
				mmm = "LA" + "D1GSAOKJ00010" ;
			}
			
			
			Log.i("---->", "设备号：" + mmm) ;
			
			factoryident = mmm.substring(0, 2) + " " ;
			terminalsn = PackUtil.fillField(mmm.substring(2, mmm.length()), 30, false, " ");
			
//			factoryident = readmap.get("factoryident");
//			terminalsn = PackUtil.fillField(readmap.get("terminalsn"), 30,
//					false, " ");
			
			baseinfo = readmap.get("baseinfo");
			terminalInitInfo = terminalInitInfo + prgversion;
			terminalInitInfo = terminalInitInfo + factoryident;
			terminalInitInfo = terminalInitInfo + terminalsn;
			terminalInitInfo = terminalInitInfo + baseinfo;

			InitialiseBean initTrans = new InitialiseBean();// 定义一个初始化交易
			initTrans.setMsgId("0800");
			initTrans.setReservedPrivate1(terminalInitInfo);
			String reservedPrivate2 = "00" + DbHelp.getBatchNum() +"004";//交易类型码 + 批次号 + 网络管理信息码
			initTrans.setReservedPrivate2(reservedPrivate2);//自定义域  
			IsoCommHandler comm = new IsoCommHandler();
			InitialiseBean respBean = (InitialiseBean) comm.sendIsoMsg(initTrans);
			if (respBean != null && respBean.getRespCode().equals("00")) {
				new DbHandle().update("TBL_PARAMETER", new String[] { "PARA_VALUE" }, new String[] { "是" }, "PARA_NAME = ?",
						new String[] { "INITIALIZE" });
			}
			GlobalParameters.g_map_para.put("cardSequenceNum", "000");// 应用类别
																		// (支付类应用000)
			GlobalParameters.g_map_para.put("cardAcceptTermIdent",
					respBean.getCardAcceptTermIdent());// 受卡机终端标识码(终端号)
			GlobalParameters.g_map_para.put("cardAcceptIdentcode",
					respBean.getCardAcceptIdentcode());// 受卡方标识码
			
			long result = 0;
			result = new DbHandle().insert("TBL_PARAMETER",
					new String[] { "PARA_NAME", "PARA_VALUE" }, new String[] {
							"cardSequenceNum", "000" });
			if (result < 0) {
				new DbHandle().update("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, new String[] { "否" },
						"PARA_NAME = ?", new String[] { "INITIALIZE" });
			}
			result = new DbHandle().insert(
					"TBL_PARAMETER",
					new String[] { "PARA_NAME", "PARA_VALUE" },
					new String[] { "cardAcceptTermIdent",
							respBean.getCardAcceptTermIdent() });
			if (result < 0) {
				new DbHandle().update("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, new String[] { "否" },
						"PARA_NAME = ?", new String[] { "INITIALIZE" });
			}
			result = new DbHandle().insert(
					"TBL_PARAMETER",
					new String[] { "PARA_NAME", "PARA_VALUE" },
					new String[] { "cardAcceptIdentcode",
							respBean.getCardAcceptIdentcode() });
			if (result < 0) {
				new DbHandle().update("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, new String[] { "否" },
						"PARA_NAME = ?", new String[] { "INITIALIZE" });
			}
			Log.i("初始化交易", "终端号:" + respBean.getCardAcceptTermIdent()
					+ ", 商户号:" + respBean.getCardAcceptIdentcode());
		} catch (Exception ex) {
			ex.printStackTrace();
			try{				
				new DbHandle().update("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, new String[] { "否" },
						"PARA_NAME = ?", new String[] { "INITIALIZE" });
			}catch(Exception e){
				e.printStackTrace();
			}
			return false;
		}

		return true;
	}

	// 签到交易
	@Deprecated
	private static boolean signIn() {
		byte[] pinKey = new byte[25];
		byte[] mainKey = new byte[25];
		byte[] transMainKey = new byte[25];
		// 定义一个签到交易
		SignInBean signInBean = new SignInBean();
		signInBean.setMsgId("0800");// 消息类型
		signInBean.setSysTraceAuditNum(TransSequence.getSysTraceAuditNum());
		signInBean.setCardAcceptTermIdent(DbHelp.getCardAcceptTermIdent());// 受卡机终端标识码41
		signInBean.setCardAcceptIdentcode(DbHelp.getCardAcceptIdentcode());// 受卡方标识码42

		IsoCommHandler comm = new IsoCommHandler();
		SignInBean respBean = (SignInBean) comm.sendIsoMsg(signInBean);

		if (respBean != null) {
			if (respBean.getRespCode().equals("00")) {
			
				System.arraycopy(respBean.getReservedPrivate3().getBytes(), 0,
						pinKey, 0, 25);
				// System.arraycopy(respBean.getReservedPrivate3().getBytes(),
				// 25,
				// mainKey, 0, 25);

				if (respBean.getReservedPrivate3().getBytes().length > 75) {
					System.arraycopy(respBean.getReservedPrivate3().getBytes(),
							50, transMainKey, 0, 25);
				}
			} else {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	// 启动日终线程
	private static boolean startDayEnd() {
		return true;
	}

	// 冲正数据插入
	public static void insert() {
		// dbhandle.insert("TBL_REVERSAL", new String[] { "MSG_ID", "CARD_NO",
		// "PROCESS_CODE", "TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM",
		// "DATE_EXPIRED", "SERVICE_ENTRY_MODE", "SERVICE_CONDITION_MODE",
		// "RESP_CODE", "CURRENCY_TRANS_CODE", "RESERVED_PRIVATE1",
		// "PCICODE", "RESERVED_PRIVATE3", "MESSAGE_AUTHENT_CODE",
		// "FLUSH_OCUNT", "FLUSH_RESULT" }, new String[] { "0400", "1234",
		// "100000", "1", "1", "1221", "123", "123", "12", "123", "12",
		// "12", "12", "12", "0", "" });
		// dbhandle.insert("TBL_REVERSAL", new String[] { "MSG_ID", "CARD_NO",
		// "PROCESS_CODE", "TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM",
		// "DATE_EXPIRED", "SERVICE_ENTRY_MODE", "SERVICE_CONDITION_MODE",
		// "AUTHOR_IDENT_RESP", "RESP_CODE", "CURRENCY_TRANS_CODE",
		// "RESERVED_PRIVATE1", "PCICODE", "MESSAGE_AUTHENT_CODE",
		// "FLUSH_OCUNT", "FLUSH_RESULT" }, new String[] { "0400", "1234",
		// "000000", "12", "2", "1221", "1223", "123", "123", "123",
		// "123", "123", "123", "123", "0", "" });
		// dbhandle.insert("TBL_REVERSAL", new String[] { "MSG_ID", "CARD_NO",
		// "PROCESS_CODE", "TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM",
		// "AUTHOR_IDENT_RESP", "MESSAGE_AUTHENT_CODE", "FLUSH_OCUNT",
		// "FLUSH_RESULT" }, new String[] { "0400", "1234", "020000",
		// "12", "3", "1221", "123", "0", "" });
		// dbhandle.insert("TBL_REVERSAL", new String[] { "MSG_ID", "CARD_NO",
		// "PROCESS_CODE", "TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM",
		// "DATE_EXPIRED", "SERVICE_ENTRY_MODE", "SERVICE_CONDITION_MODE",
		// "AUTHOR_IDENT_RESP", "RESP_CODE", "CURRENCY_TRANS_CODE",
		// "SECURITY_RELATED_CONTROL", "RESERVED_PRIVATE1", "PCICODE",
		// "MESSAGE_AUTHENT_CODE", "ORIGIN_FLOW_NUM", "ORIGIN_PCICODE",
		// "ORIGIN_BUSS_DATE", "FLUSH_OCUNT", "FLUSH_RESULT" },
		// new String[] { "0400", "1234", "200000", "1", "4", "1221",
		// "123", "123", "12", "123", "12", "12", "12", "12",
		// "12", "123", "123", "123", "0", "" });
		// dbhandle.insert("TBL_REVERSAL", new String[] { "MSG_ID", "CARD_NO",
		// "PROCESS_CODE", "TRANS_AMOUNT", "SYS_TRACE_AUDIT_NUM",
		// "DATE_EXPIRED", "SERVICE_ENTRY_MODE", "SERVICE_CONDITION_MODE",
		// "RESP_CODE", "CURRENCY_TRANS_CODE", "SECURITY_RELATED_CONTROL",
		// "RESERVED_PRIVATE1", "PCICODE", "RESERVED_PRIVATE3",
		// "MESSAGE_AUTHENT_CODE", "ORIGIN_FLOW_NUM", "ORIGIN_PCICODE",
		// "ORIGIN_BUSS_DATE", "FLUSH_OCUNT", "FLUSH_RESULT" },
		// new String[] { "0400", "1234", "300000", "1", "5", "1221",
		// "123", "123", "12", "123", "12", "12", "12", "12",
		// "12", "123", "123", "123", "0", "" });
		dbhandle.insert("TBL_FlOW", new String[] { "PROCESS_CODE", "CARD_NO",
				"SYS_TRACE_AUDIT_NUM", "RET_REFER_NUM", "LOCAL_TRANS_TIME" },
				new String[] { "1", "1", "1", "1", "1" });
	}

	/**
	 * 启动log4j输出日志功能
	 */
	public static void startlog4j() {
		LogConfigurator logConfigurator = new LogConfigurator();
		logConfigurator.setFileName(GlobalPathUtil.getExternalStorageDirectory()
				+ File.separator + "MyApp" + File.separator + "logs"
				+ File.separator + "log4j.txt");
		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("org.apache", Level.ERROR);
		logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
		//logConfigurator.setMaxFileSize(1024 * 1024 * 5);
		logConfigurator.setImmediateFlush(true);
		logConfigurator.configure();
	}

	/**
	 * 设置log日志按照自定义规则输出到sdcard上指定路径下 lzw
	 */
	public static void startSetSYSLog() {
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd_HHmmss");
		String nowStr = format.format(new Date());
		logFileName = logFileName + "_" + nowStr + ".txt";
		new File(logSrc).mkdirs();
		try {
			Runtime.getRuntime().exec(
					logToFileCommand + logSrc + logFileName + logLevel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将图片资源id与名字的对应关系存入map
	 * 
	 * @return
	 */
	public static Map<String, Integer> getDrawableIDMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Class clazz = R.drawable.class;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			try {
				map.put(field.getName(), (Integer) field.get(null));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}