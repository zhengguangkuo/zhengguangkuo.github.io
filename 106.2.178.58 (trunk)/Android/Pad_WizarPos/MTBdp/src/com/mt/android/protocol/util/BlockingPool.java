package com.mt.android.protocol.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * A blocking pool for caching objects
 * 
 * @author chentao
 *
 *  Created on 2010-10-28, 2010
 */
public class BlockingPool<T> {
	private static final int DEFAULT_POOLSIZE = 50;//default size of the pool is 50
	private static final int DEFAULT_TIMEOUT = 1000;//default timeout is 10000 ms
	private BlockingQueue<T> pool;
	private int MAXSIZE; 
	private long TIMEOUT;
	
	/**
	 * A static method to create a new pool with default size and timeout
	 * @return
	 */
	public static BlockingPool open() {
		return new BlockingPool(DEFAULT_POOLSIZE, DEFAULT_TIMEOUT);
	}
	
	/**
	 * A static method to create a new pool with a specified size and default timeout
	 * @return
	 */
	public static BlockingPool open(int size) {
		return new BlockingPool(size, DEFAULT_TIMEOUT);
	}
	
	/**
	 * A static method to create a new pool with a specified size and timeout
	 * @return
	 */
	public static BlockingPool open(int size, long timeout) {
		return new BlockingPool(size, timeout);
	}
	
	/**
	 * Create a pool instance with specified size and timeout
	 * @param size
	 */
	private BlockingPool(int size, long time) {
		MAXSIZE = size;
		TIMEOUT = time;
		pool = new ArrayBlockingQueue<T>(MAXSIZE);
	}
	
	/**
	 * Put an element into the pool.
	 * If the pool is full, return false
	 * @param t
	 */
	public boolean put(T t) {
		boolean isSucceed = pool.offer(t);
//		if (!isSucceed) {
//			if (log.isDebugEnabled()) {
//				log.debug("The pool reaches its max size, ADD action is refused!");
//			}
//		}
//		else {
//			if (log.isDebugEnabled()) {
//				log.debug("Add element " + t + " to the pool successfully, the pool size is " + getSize());
//			}
//		}
		return isSucceed;
	}
	
	/**
	 * Remove an element.
	 * @param t
	 * @return true if succeed, or false if the pool does not contain this element
	 */
	public boolean remove(T t) {
		boolean isSucceed = pool.remove(t);
//		if (log.isDebugEnabled()) {
//			if (isSucceed) { 
//				log.debug("Remove element " + t + ", the pool size is " + getSize());
//			}
//			else {
//				log.error("This pool doesn't have element " + t + ", REMOVE action failed");
//			}
//		}
		return isSucceed;
	}
	
	/**
	 * Get an element from pool. 
	 * @return a cached element, or null If the pool is empty
	 */
	public T get() {
		T elem = null;
		try {
			elem = pool.poll(TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			elem = null;
		}
		return elem;
	}
	
	/**
	 * Get the pool size
	 * @return
	 */
	public int getSize() {
		return pool.size();
	}

	/**
	 * Clear the pool, and return all cached elements in array
	 * if the pool is empty, return an empty array
	 */
	public Object[] clear() {
		List<T> elems = new ArrayList<T>();
		T elem = null;
		synchronized (this) {
			while((elem = get()) != null) {
				elems.add(elem);
			}
		}
		return elems.toArray();
	}
}
