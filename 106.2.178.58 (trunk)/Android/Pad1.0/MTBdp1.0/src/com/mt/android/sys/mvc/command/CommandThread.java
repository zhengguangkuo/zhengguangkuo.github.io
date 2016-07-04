package com.mt.android.sys.mvc.command;

import org.apache.log4j.Logger;

import android.util.Log;

public class CommandThread implements Runnable
{
	private static final Logger log = Logger.getLogger(CommandThread.class);
	private int threadId;
	private Thread thread = null;
	private boolean running = false;
	private boolean stop = false;

	public CommandThread(int threadId)
	{
		log.debug("CommandThread::ctor");
		this.threadId = threadId;
		thread = new Thread(this);
	}

	public void run()
	{
		log.debug("CommandThread::run-enter");
		while(!stop)
		{
			log.debug("CommandThread::get-next-command");
			ICommand cmd = CommandQueueManager.getInstance().getNextCommand();
			//System.out.println("CommandThread::to-execute");
			Log.i("gocmd" , "command execute");
			cmd.execute();
			Log.i("gocmd" , "command execute finish");
			//System.out.println("CommandThread::executed");
		}
		log.debug("CommandThread::run-exit");
	}

	public void start()
	{
		thread.start();
		running = true;
	}

	public void stop()
	{
		stop = true;
		running = false;
	}

	public boolean isRunning()
	{
		return running;
	}

	public int getThreadId()
	{
		return threadId;
	}
}
