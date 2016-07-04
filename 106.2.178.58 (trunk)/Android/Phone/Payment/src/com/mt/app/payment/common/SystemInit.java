package com.mt.app.payment.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.mt.android.db.DbHandle;
import com.mt.android.db.DbHelper;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class SystemInit {
	private Context context;
	private static DbHelper db = null;
	public static HashMap mockMap = new HashMap();
	// -----------------------------begin----------------------------------------
		private static String logToFileCommand = "logcat -v time -f "; // log输出到设置
		private static String logSrc = "/mnt/sdcard/log/"; // log输出到sdcard路径
		private static String logFileName = "PadPaymentLog"; // log输出生成的文件名
		private static String logLevel = " -s *:I"; // log打印级别控制 E为错误级别 W为警告级别
													// I为info级别依次类推
	// -----------------------------end-------------------------------------------
	public SystemInit(Context context) {
		this.context = context;
	}

	/**
	 * 初始化
	 */
	public void init() {
		// 加载挡板报文
		// loadRespMess();
		startSetSYSLog();
		initDb();
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
	public void loadRespMess() {

		InputStream in = null;
		BufferedReader dr = null;
		try {
			in = context.getAssets().open("file/message.txt");
			dr = new BufferedReader(new InputStreamReader(in, "GBK"));
			String str;
			while ((str = dr.readLine()) != null) {

				if (str.trim().equalsIgnoreCase("")) {
					continue;
				}

				this.mockMap.put(str.split("=")[0], str.split("=")[1]);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (dr != null) {
				try {
					dr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// 初始化数据库
	private boolean initDb() {

		// 建立数据库
		db = DbHelper.getInstance(context, new DbInfoImpl());
		db.open();

		Cursor cursor = db.select("AREA_CODE", new String[] { "_ID",
				"AREA_CODE", "AREA_LEVEL" }, null, null, null, null, null);
		//检查数据库是否有更新
		updateAreaInfos();
		// 判断数据库是否已经初始化过
		if (cursor.moveToNext()) {
			cursor.close();
			db.close();
			return true;
		}

		// 初始化数据库
		try {
			// 初始化地区表
			loadAreaInfos();
			// 初始化业务类型表
			loadTypeInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();

		return true;
	}
    //控制数据库更新
	public void updateAreaInfos() {
		InputStream in = null;
		BufferedReader dr = null;
		try {
			in = context.getAssets().open("file/area_code.sql");
			dr = new BufferedReader(new InputStreamReader(in, "GBK"));
			DbHelper db = DbHelper.getInstance();
			String update = "";
				if ((update = dr.readLine()) != null
						&&update.equalsIgnoreCase("update")){
					 db.mDb.execSQL("delete from AREA_CODE");
					 loadAreaInfos();
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
			}
	// 向数据库导入地区信息
	public void loadAreaInfos() {
		InputStream in = null;
		BufferedReader dr = null;
		try {
			long time1 = System.currentTimeMillis();
			in = context.getAssets().open("file/area_code.sql");
			dr = new BufferedReader(new InputStreamReader(in, "GBK"));
			Log.i("=========busi read time======", System.currentTimeMillis()
					- time1 + "");
			String sql = "", str1 = "", str2 = "", str = "",str3="";
			long time2 = System.currentTimeMillis();

			DbHelper db = DbHelper.getInstance();
			// 开启事务
			db.mDb.beginTransaction();
			try {
				while ((str = dr.readLine()) != null) {
					if (str.trim().equalsIgnoreCase("")) {
						continue;
					}
					if (str1.equalsIgnoreCase("")) {
						str1 = str;
						if(str1.equals("update")){
							str1="";
							str="";
						}
					}else {
						sql = str1 + str;
						str1 = "";
						db.mDb.execSQL(sql);
					}
				}
				// 设置事务标志为成功，当结束事务时就会提交事务
				db.mDb.setTransactionSuccessful();
			} finally {
				// 结束事务
				db.mDb.endTransaction();
			}

			Log.i("=========insert time======", System.currentTimeMillis()
					- time2 + "");
			DbHandle dbhandle = new DbHandle();
			List<Map<String, String>> list = dbhandle.rawQuery(
					"select count(*) as COUNT from AREA_CODE", null);
			String count = "";
			for (int i = 0; i < list.size(); i++) {
				count = list.get(i).get("COUNT");
				System.out.println("插入地区数据总计:" + count + "条");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (dr != null) {
				try {
					dr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// 向数据库导入分类信息
	public void loadTypeInfo() {
		InputStream in = null;
		BufferedReader dr = null;
		try {
			long time1 = System.currentTimeMillis();
			in = context.getAssets().open("file/busi_type.sql");
			dr = new BufferedReader(new InputStreamReader(in, "GBK"));
			Log.i("=========busi read time======", System.currentTimeMillis()
					- time1 + "");
			String sql = "", str1 = "", str2 = "", str = "";
			long time2 = System.currentTimeMillis();

			DbHelper db = DbHelper.getInstance();
			// 开启事务
			db.mDb.beginTransaction();
			try {

				while ((str = dr.readLine()) != null) {

					if (str.trim().equalsIgnoreCase("")) {
						continue;
					}

					if (str1.equalsIgnoreCase("")) {
						str1 = str;
					} else {
						sql = str1 + str;
						str1 = "";
						db.mDb.execSQL(sql);
					}
				}
				// 设置事务标志为成功，当结束事务时就会提交事务
				db.mDb.setTransactionSuccessful();
			} finally {
				// 结束事务
				db.mDb.endTransaction();
			}
			Log.i("=========insert time======", System.currentTimeMillis()
					- time2 + "");
			DbHandle dbhandle = new DbHandle();
			List<Map<String, String>> list = dbhandle.rawQuery(
					"select count(*) as COUNT from MERCHANT_BUSSINESS_TYPE",
					null);
			String count = "";
			for (int i = 0; i < list.size(); i++) {
				count = list.get(i).get("COUNT");
				System.out.println("插入类型数据总计:" + count + "条");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (dr != null) {
				try {
					dr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
