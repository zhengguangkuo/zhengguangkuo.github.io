package com.mt.app.padpayment.common;

import java.sql.SQLException;
import java.util.Map;

import com.mt.android.db.DbHandle;

/**
 * 
 * 
 * @Description:�����ݿ��ж�ȡpad��Ϣ
 * 
 * @author:dw
 * 
 * @time:2013-9-25 ����4:16:24
 */
public class DbHelp {

	/**
	 * �õ�Ӧ�����
	 * 
	 * @return
	 */
	public static String getCardSequenceNum() {
		DbHandle db = new DbHandle();
		Map map = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "cardSequenceNum" }, null, null, null);

		if (map != null && map.get("PARA_VALUE") != null) {
			return (String) map.get("PARA_VALUE"); // Ӧ�����
		} else {
			if (SysManager.initialize()){
				map = db.selectOneRecord("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
						new String[] { "cardSequenceNum" }, null, null, null);
				if (map != null && map.get("PARA_VALUE") != null) {
					return (String) map.get("PARA_VALUE"); // Ӧ�����
				} else {
					return null;
				}
			} else {
				return null;
			}
		}

	}

	/**
	 * �ܿ����ն˱�ʶ��(�ն˺�)
	 * 
	 * @return
	 */
	public static String getCardAcceptTermIdent() {
		DbHandle db = new DbHandle();

		Map map = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "cardAcceptTermIdent" }, null, null, null);
		if (map != null && map.get("PARA_VALUE") != null) {
			return (String) map.get("PARA_VALUE");// �ܿ����ն˱�ʶ��(�ն˺�)
		} else {
			if (SysManager.initialize()){
				map = db.selectOneRecord("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
						new String[] { "cardAcceptTermIdent" }, null, null, null);
				if (map != null && map.get("PARA_VALUE") != null) {
					return (String) map.get("PARA_VALUE"); // �ܿ����ն˱�ʶ��(�ն˺�)
				} else {
					return null;
				}
			} else {
				return null;
			}
		}

	}

	/**
	 * �ܿ�����ʶ��
	 * 
	 * @return
	 */
	public static String getCardAcceptIdentcode() {
		DbHandle db = new DbHandle();

		Map map = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "cardAcceptIdentcode" }, null, null, null);
		if (map != null && map.get("PARA_VALUE") != null) {
			return (String) map.get("PARA_VALUE");// �ܿ�����ʶ��
		} 
		return null;
		/*else {
			if (SysManager.initialize()){
				map = db.selectOneRecord("TBL_PARAMETER",
						new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
						new String[] { "cardAcceptIdentcode" }, null, null, null);
				if (map != null && map.get("PARA_VALUE") != null) {
					return (String) map.get("PARA_VALUE"); // �ܿ�����ʶ��
				} else {
					return null;
				}
			} else {
				return null;
			}
		}*/

	}

	/**
	 * ���κ�
	 * 
	 * @return
	 */
	public static String getBatchNum() {
		DbHandle db = new DbHandle();
		Map map = db.selectOneRecord("TBL_PARAMETER",
				new String[] { "PARA_VALUE" }, "PARA_NAME = ?",
				new String[] { "batchNum" }, null, null, null);
		if (map != null && map.get("PARA_VALUE") != null) {
			return (String) map.get("PARA_VALUE");// �������κ�
		} else {
			try {
				db.execSQL("insert into TBL_PARAMETER(TYPE,PARA_NAME,PARA_VALUE,PARA_EXPLAIN) values ('3','batchNum','100000','���κ�')");
				return "100000";
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
