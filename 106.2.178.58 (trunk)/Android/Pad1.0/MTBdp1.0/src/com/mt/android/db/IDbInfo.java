package com.mt.android.db;

/**
 * �������Ľӿ�
 * 
 * @author dw
 * 
 */
public interface IDbInfo {
	/**
	 * ��ȡ���ݿ����
	 * 
	 * @return
	 */
	public String[] getTableNames();

	/**
	 * ��ȡ���ݿ��ֶ���
	 * 
	 * @return
	 */
	public String[][] getFieldNames();

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return
	 */
	public String[][] getFieldTypes();

}
