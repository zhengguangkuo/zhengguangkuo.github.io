package com.mt.android.sys.mvc.command;

import org.apache.log4j.Logger;


public class ThreadPool
{
	private static final Logger log = Logger.getLogger(ThreadPool.class);

	private static final int MAX_THREADS_COUNT = 2;

	static private ThreadPool instance = new ThreadPool();
	private CommandThread threads[] = null;

	private boolean started = false;

	private ThreadPool()
	{
	}

	public static ThreadPool getInstance()
	{
		return instance;
	}

	public void start()
	{
		
		if(!started)
		{
			log.debug("ThreadPool::start");
			int threadCount = MAX_THREADS_COUNT;
			threads = new CommandThread[threadCount];

			for(int threadId = 0; threadId < threadCount; threadId++)
			{
				threads[threadId] = new CommandThread(threadId);
				threads[threadId].start();
			}
			started = true;
			log.debug("ThreadPool::started");
		}
	}

	public void shutdown()
	{
		if(started)
		{
			for(CommandThread thread : threads)
			{
				thread.stop();
			}
			threads = null;
			started = false;
		}
	}
}
