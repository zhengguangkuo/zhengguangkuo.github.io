package com.mt.android.db;

/**
 * 库表定义类的接口
 * 
 * @author dw
 * 
 */
public interface IDbInfo {
	/**
	 * 获取数据库表名
	 * 
	 * @return
	 */
	public String[] getTableNames();

	/**
	 * 获取数据库字段名
	 * 
	 * @return
	 */
	public String[][] getFieldNames();

	/**
	 * 获取数据库类型
	 * 
	 * @return
	 */
	public String[][] getFieldTypes();

}
