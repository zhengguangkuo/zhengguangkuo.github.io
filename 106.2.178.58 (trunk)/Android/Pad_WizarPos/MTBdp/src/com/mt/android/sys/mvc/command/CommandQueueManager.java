package com.mt.android.sys.mvc.command;

import org.apache.log4j.Logger;

import android.util.Log;

public final class CommandQueueManager
{

	private static CommandQueueManager instance = new CommandQueueManager();
	private static final Logger log = Logger.getLogger(CommandQueueManager.class);
	private boolean initialized = false;
	private ThreadPool pool;
	private CommandQueue queue;

	private CommandQueueManager()
	{
	}

	public static CommandQueueManager getInstance()
	{
		return instance;
	}

	public void initialize()
	{
		log.debug("CommandQueueManager::initialize");
		if(!initialized)
		{
			log.debug("CommandQueueManager::initializing");
			queue = new CommandQueue();
			pool = ThreadPool.getInstance();
			log.debug("CommandQueueManager::initialized");

			pool.start();
			initialized = true;
		}
		log.debug("CommandQueueManager::initialize - done");
	}

	/**
	 * This acts as the consumer for the queue...
	 * 
	 * @return
	 */
	public ICommand getNextCommand()
	{
		log.debug("CommandQueueManager::getNextCommand");
		ICommand cmd = queue.getNextCommand();
		log.debug("CommandQueueManager::getNextCommand - done: " + cmd);
		return cmd;
	}

	/**
	 * This acts as the producer for the queue...
	 * Generally used by command executor to enqueue
	 * 
	 * @param cmd
	 */
	public void enqueue(ICommand cmd)
	{
		Log.i("gocmd" , "command enqueue");
		queue.enqueue(cmd);
		Log.i("gocmd" , "command enqueue finish");
	}

	public void clear()
	{
		queue.clear();
	}

	public void shutdown()
	{
		if(initialized)
		{
			queue.clear();
			pool.shutdown();
			initialized = false;
		}
	}
}
