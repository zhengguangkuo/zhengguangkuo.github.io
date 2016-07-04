package com.mt.android.protocol;

import java.util.Map;

/**
 * <p>
 * ���ù��̵���������Ϣ
 * </p>
 * 
 * <p>
 * Contex�п��Դ洢�κ��뵱ǰ������ص��κ���Ϣ������ fromid, toid, socket, HttpServletRequest�ȵ�
 * ����һ����������Ҫ����(delocalized)������һ��������Ҫ���� (localized)
 * </p>
 */
public interface IContext {
	/**
	 * ���õ��ø�context���߳�
	 * @param therad
	 */
	public void setThread(Thread therad);
	/**
	 * ��ȡ���ø�context���߳�
	 * @return
	 */
	public Thread getThread();
	/**
	 * ���ñ���
	 * @param object
	 */
	public void setData(Object object);
	/**
	 * ��ȡ����
	 * @return
	 */
	public Object getData();
}