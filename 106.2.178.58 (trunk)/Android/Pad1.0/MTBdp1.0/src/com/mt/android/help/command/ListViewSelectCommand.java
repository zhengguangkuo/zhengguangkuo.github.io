package com.mt.android.help.command;

import org.apache.log4j.Logger;

import com.mt.android.help.listview.Person;
import com.mt.android.help.listview.HelpListViewBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.BaseActivityID;

public class ListViewSelectCommand extends AbstractCommand{

	private static Logger log = Logger.getLogger(ListViewSelectCommand.class);
	
	@Override
	protected void prepare() {
		log.debug("prepare");
	}

	@Override
	protected final void onBeforeExecute() {
		log.debug("onBeforeExecute");
	}

	@Override
	protected void go() {
		// TODO Auto-generated method stub
		log.debug("go");
		Response response = new Response();
        response.setError(false);
        setResponse(response);
        //OpenCard();
	}
	
	@Override
	protected void onAfterExecute()
	{
		log.debug("onAfterExecute");
		
		Response response = getResponse();
		response.setTargetActivityID(BaseActivityID.map.get("ACTIVITY_ID_LISTVIEW_SHOW"));
		/*CardReadBean cardRead = new CardReadBean();
		cardRead.setCardNo("123456");
		response.setData(cardRead);
		
		if(response.isError())
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else
		{
			
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			
		}*/
	}


}
