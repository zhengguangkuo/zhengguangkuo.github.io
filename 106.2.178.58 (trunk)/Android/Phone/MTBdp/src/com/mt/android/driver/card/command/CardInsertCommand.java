package com.mt.android.driver.card.command;

import org.apache.log4j.Logger;

import com.mt.android.driver.card.CardReadBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;


//处理读卡器读卡返回事件
public class CardInsertCommand extends AbstractCommand {
    private Logger log = Logger.getLogger(CardInsertCommand.class);
	@Override
	protected void go() {
		// TODO Auto-generated method stub
		Response response = new Response();
		CardReadBean cardBean = new CardReadBean();
		cardBean.setCardNo("6555 1112 6121 1112");
		response.setData(cardBean);
		response.setListener(Controller.currentActivity.listener);
		response.setError(false);
	}
    
	@Override
	protected void onAfterExecute()
	{
		log.debug("onAfterExecute");
		
		Response response = getResponse();
		if(response.isError())
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			//response.setTargetActivityID(ActivityID.ACTIVITY_ID_MAIN);
		}
	}
}
