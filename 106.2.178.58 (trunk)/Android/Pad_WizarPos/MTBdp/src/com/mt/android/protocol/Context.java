package com.mt.android.protocol;


public class Context implements IContext{
	private Object data;
	private Thread thread;
	@Override
	public void setData(Object object) {
		this.data = object;
		
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public void setThread(Thread thread) {
		this.thread = thread;
		
	}

	@Override
	public Thread getThread() {
		return thread;
	}
	

}
