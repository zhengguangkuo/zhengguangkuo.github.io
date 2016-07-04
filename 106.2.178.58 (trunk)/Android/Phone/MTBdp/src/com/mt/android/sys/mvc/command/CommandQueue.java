package com.mt.android.sys.mvc.command;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import android.util.Log;

public class CommandQueue
{
	private LinkedBlockingQueue<ICommand> theQueue = new LinkedBlockingQueue<ICommand>();
	private static final Logger log = Logger.getLogger(CommandQueue.class);

	public CommandQueue()
	{
		log.debug("CommandQueue::ctor");
	}

	public void enqueue(ICommand cmd)
	{
		log.debug("CommandQueue::enqueue");
		theQueue.add(cmd);
	}

	public synchronized ICommand getNextCommand()
	{
		log.debug("CommandQueue::get-next-command");
		ICommand cmd = null;
		try
		{
			log.debug("CommandQueue::to-take");
			Log.i("gocmd" , "command outqueue");
			cmd = theQueue.take();
			Log.i("gocmd" , "queue size:" + theQueue.size());
			Log.i("gocmd" , "command outqueue finish");
		} catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		log.debug("CommandQueue::return: " + cmd);
		return cmd;
	}

	public synchronized void clear()
	{
		log.debug("CommandQueue::clear");
		theQueue.clear();
	}
}
