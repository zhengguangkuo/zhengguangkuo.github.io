package com.mt.android.protocol;

import java.util.Map;

/**
 * <p>
 * 调用过程的上下文信息
 * </p>
 * 
 * <p>
 * Contex中可以存储任何与当前调用相关的任何信息，例如 fromid, toid, socket, HttpServletRequest等等
 * 其中一部分属性需要传输(delocalized)，而另一部分则不需要传输 (localized)
 * </p>
 */
public interface IContext {
	/**
	 * 设置调用该context的线程
	 * @param therad
	 */
	public void setThread(Thread therad);
	/**
	 * 获取调用该context的线程
	 * @return
	 */
	public Thread getThread();
	/**
	 * 设置报文
	 * @param object
	 */
	public void setData(Object object);
	/**
	 * 获取报文
	 * @return
	 */
	public Object getData();
}