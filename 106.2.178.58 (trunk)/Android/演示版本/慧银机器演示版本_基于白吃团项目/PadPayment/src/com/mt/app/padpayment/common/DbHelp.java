package com.mt.app.padpayment.common;

import java.sql.SQLException;
import java.util.Map;

import com.mt.android.db.DbHandle;

/**
 * 
 * 
 * @Description:从数据库中读取pad信息
 * 
 * @author:dw
 * 
 * @time:2013-9-25 下午4:16:24
 */
public class DbHelp {

	/**
	 * 得到应用类别
	 * 
	 * @return
	 */
	public static String getCardSequenceNum() {
		DbHandle db = new DbHandle();
		Map map = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "cardSequenceNum" }, null, null, null);

		if (map != null && map.get("PARA_VALUE") != null) {
			return (String) map.get("PARA_VALUE"); // 应用类别
		} else {
			if (SysManager.initialize()){
				map = db.selectOneRecord("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
						new String[] { "cardSequenceNum" }, null, null, null);
				if (map != null && map.get("PARA_VALUE") != null) {
					return (String) map.get("PARA_VALUE"); // 应用类别
				} else {
					return null;
				}
			} else {
				return null;
			}
		}

	}

	/**
	 * 受卡机终端标识码(终端号)
	 * 
	 * @return
	 */
	public static String getCardAcceptTermIdent() {
		DbHandle db = new DbHandle();

		Map map = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "cardAcceptTermIdent" }, null, null, null);
		if (map != null && map.get("PARA_VALUE") != null) {
			return (String) map.get("PARA_VALUE");// 受卡机终端标识码(终端号)
		} else {
			if (SysManager.initialize()){
				map = db.selectOneRecord("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
						new String[] { "cardAcceptTermIdent" }, null, null, null);
				if (map != null && map.get("PARA_VALUE") != null) {
					return (String) map.get("PARA_VALUE"); // 受卡机终端标识码(终端号)
				} else {
					return null;
				}
			} else {
				return null;
			}
		}

	}

	/**
	 * 受卡方标识码
	 * 
	 * @return
	 */
	public static String getCardAcceptIdentcode() {
		DbHandle db = new DbHandle();

		Map map = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "cardAcceptIdentcode" }, null, null, null);
		if (map != null && map.get("PARA_VALUE") != null) {
			return (String) map.get("PARA_VALUE");// 受卡方标识码
		} 
		return null;
		/*else {
			if (SysManager.initialize()){
				map = db.selectOneRecord("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
						new String[] { "cardAcceptIdentcode" }, null, null, null);
				if (map != null && map.get("PARA_VALUE") != null) {
					return (String) map.get("PARA_VALUE"); // 受卡方标识码
				} else {
					return null;
				}
			} else {
				return null;
			}
		}*/

	}

	/**
	 * 批次号
	 * 
	 * @return
	 */
	public static String getBatchNum() {
		DbHandle db = new DbHandle();
		Map map = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "batchNum" }, null, null, null);
		if (map != null && map.get("PARA_VALUE") != null) {
			return (String) map.get("PARA_VALUE");// 设置批次号
		} else {
			try {
				db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('3','batchNum','100000','批次号')");
				return "100000";
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
