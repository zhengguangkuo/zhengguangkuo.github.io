package com.mt.app.padpayment.tools;

import java.util.Map;

import com.mt.android.db.DbHandle;


public class TransSequence {
	
	private static DbHandle db = new DbHandle();
	public static String getSysTraceAuditNum() {
		
		synchronized (db) {
			//将流水号保存到数据库
			
			
			Map<String,String> map = db.selectOneRecord("TBL_PARAMETER", new String[]{"PARA_VALUE"}, "PARA_NAME = ?", new String[]{"CurrentVal"}, null, null, null);
			if (map != null&&map.get("PARA_VALUE")!=null) {
				String currentVal = map.get("PARA_VALUE").toString();
				currentVal = (Integer.valueOf(currentVal) + 1) + "";
				
				if(currentVal.equalsIgnoreCase("999999")){//达到最大值后重新开始
					currentVal = "100000";
				}
				
				db.update("TBL_PARAMETER", new String[]{"PARA_VALUE"}, new String[]{currentVal},"PARA_NAME = ?", new String[]{"CurrentVal"});
				return currentVal;
			} else {
				db.update("TBL_PARAMETER", new String[]{"PARA_VALUE"}, new String[]{"100000"},"PARA_NAME = ?", new String[]{"CurrentVal"});
				return "100000";
			}
			
			
			
		}
	}
	/*public static String getSysTraceAuditNum() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");	
		synchronized (sdf) {
			String date = sdf.format(new Date());	
			return date;
		}
	}*/
}
