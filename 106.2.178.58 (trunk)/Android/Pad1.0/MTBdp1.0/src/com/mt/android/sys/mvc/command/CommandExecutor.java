package com.mt.android.sys.mvc.command;

import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.apache.log4j.Logger;
import com.mt.android.sys.mvc.common.Request;

public final class CommandExecutor
{
	private final HashMap<Integer, Class<? extends ICommand>> commands = new HashMap<Integer, Class<? extends ICommand>>();
	private static final Logger log=Logger.getLogger(CommandExecutor.class);
	private static final CommandExecutor instance = new CommandExecutor();
	private boolean initialized = false;
    private long lastCommandTime = 0, curCommandTime = 0;//为了控制按钮同时点击
	public static final int COMMAND_ID_IDENTITY = 1;
	public static final int COMMAND_ID_BASE = 1000;
	public static int currentId = COMMAND_ID_BASE;

	private CommandExecutor()
	{
		commands.put(COMMAND_ID_IDENTITY, IdentityCommand.class);
	}

	public static CommandExecutor getInstance()
	{
		return instance;
	}

	public void ensureInitialized()
	{
		if(!initialized)
		{
			initialized = true;
			log.debug("CommandExecutor::initialize");
			CommandQueueManager.getInstance().initialize();
			log.debug("CommandExecutor::initialized");
		}
	}

	public void terminateAll()
	{
		// TODO: Terminate or mark all commands as terminated
	}

	public void enqueueCommand(int commandId, Request request)
	{
		log.debug("[CommandExecutor::enqueueCommand] Retrieving Command");
		/*curCommandTime = System.currentTimeMillis();
		if(curCommandTime - lastCommandTime < 300){//两次命令的时间差必须大于300毫秒
			return ;
		}else{
			lastCommandTime = curCommandTime;
		}
		*/
		final ICommand cmd = getCommand(commandId);
		if(cmd != null)
		{
			cmd.setRequest(request);
			cmd.setResponseListener(request.getListener());
			CommandQueueManager.getInstance().enqueue(cmd);
		}
	}

	private ICommand getCommand(int commandId)
	{
		ICommand rv = null;

		if(commands.containsKey(commandId))
		{
			Class<? extends ICommand> cmd = commands.get(commandId);
			if(cmd != null)
			{
				int modifiers = cmd.getModifiers();
				if((modifiers & Modifier.ABSTRACT) == 0 && (modifiers & Modifier.INTERFACE) == 0)
				{
					try
					{
						rv = cmd.newInstance();
					} catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		return rv;
	}

	public void registerCommand(int commandId, Class<? extends ICommand> command)
	{
		if(command != null)
		{
			log.debug("registerCommand :"+ command.getName());
			commands.put(commandId, command);
		}
	}

	public void unregisterCommand(int commandId)
	{
		commands.remove(commandId);
	}
	
	//为每个Commond分配一个CommondId
    public static int IDistributer(){
			
			synchronized(CommandExecutor.class){
				return CommandExecutor.currentId++;
			}
			
		}
}
